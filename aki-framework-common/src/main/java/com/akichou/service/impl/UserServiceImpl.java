package com.akichou.service.impl;

import com.akichou.converter.UserConverter;
import com.akichou.customEnum.AppHttpCodeEnum;
import com.akichou.domain.LoginUser;
import com.akichou.domain.ResponseResult;
import com.akichou.domain.dto.*;
import com.akichou.domain.entity.*;
import com.akichou.domain.vo.*;
import com.akichou.exception.SystemException;
import com.akichou.repository.RoleRepository;
import com.akichou.repository.UserRepository;
import com.akichou.repository.UserRoleRepository;
import com.akichou.service.MailService;
import com.akichou.service.UserService;
import com.akichou.util.BeanCopyUtil;
import com.akichou.util.RedisCache;
import com.akichou.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.akichou.constant.SystemConstants.*;

/**
 * User Service Implementation
 *
 * @author Aki Chou
 * @date 2024/07/24 Wed.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final RedisCache redisCache;
    private final UserRepository userRepository ;
    private final RoleRepository roleRepository ;
    private final UserRoleRepository userRoleRepository;
    private final MailService mailService ;
    private final PasswordEncoder passwordEncoder ;

    @Override
    public ResponseResult<UserInfoVo> getUserInfo() {

        // Get current userId
        Long userId = SecurityUtil.getUserId() ;

        // Get user info from redis cache
        LoginUser loginUser = redisCache.getCacheObject(BLOG_LOGIN_REDIS_KEY_PRE + userId, LoginUser.class);

        if (loginUser == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_USER_GET_FAIL);
        }

        // Vo Encapsulation
        UserInfoVo userInfoVo = BeanCopyUtil.copyBean(loginUser.getUser(), UserInfoVo.class) ;

        return ResponseResult.okResult(userInfoVo);
    }

    @Override
    public ResponseResult<Object> updateUserInfo(UserInfoRequest userInfoRequest) {

        User user = userRepository.findById(userInfoRequest.getId())
                .orElseThrow(() -> new SystemException(AppHttpCodeEnum.USER_NOT_FOUND)) ;

        userRepository.save(setAttributesForUserEditing(user, userInfoRequest));

        return ResponseResult.okResult();

    }

    private User setAttributesForUserEditing(User user, UserInfoRequest userInfoRequest) {

        user.setNickName(userInfoRequest.getNickName());
        user.setAvatar(userInfoRequest.getAvatar());
        user.setSex(userInfoRequest.getSex());
        user.setEmail(userInfoRequest.getEmail());
        user.setUpdateBy(SecurityUtil.getUserId());
        user.setUpdateTime(new Date());

        return user ;
    }

    @Override
    public ResponseResult<Object> register(UserRegisterRequest userRegisterRequest) {

        // is userName duplicated
        if(isUserNameExist(userRegisterRequest.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST) ;
        }

        userRepository.save(UserConverter.mapRegisterUserRequestToUser(userRegisterRequest)) ;

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult<PageVo<UserPageSelectVo>> selectUsers(Integer pageNum, Integer pageSize, UserSelectConditionsDto userSelectConditionsDto) {

        // Database Process
        PageRequest pageRequest = PageRequest.of(pageNum - 1, pageSize);

        // Extract parameters
        String userName = Optional.ofNullable(userSelectConditionsDto.getUserName()).filter(StringUtils::hasText).orElse(null);
        String phoneNumber = Optional.ofNullable(userSelectConditionsDto.getPhoneNumber()).filter(StringUtils::hasText).orElse(null);
        String status = Optional.ofNullable(userSelectConditionsDto.getStatus()).filter(StringUtils::hasText).orElse(null);

        Page<User> page = userRepository.findAllByUserNameAndPhoneAndStatusExisting(
                userName,
                phoneNumber,
                status,
                pageRequest);

        // VO Encapsulation
        List<UserPageSelectVo> userPageSelectVos = BeanCopyUtil.copyBeanList(page.getContent(), UserPageSelectVo.class);

        return ResponseResult.okResult(new PageVo<>(userPageSelectVos, page.getTotalElements()));
    }

    @Override
    public ResponseResult<Object> addUser(UserAddDto userAddDto) {

        if (userAddDto.getUserName() == null) {
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME) ;
        }
        if (isUserNameExist(userAddDto.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST) ;
        }
        if (isPhoneNumberExist(userAddDto.getPhoneNumber())) {
            throw new SystemException(AppHttpCodeEnum.PHONE_NUMBER_EXIST) ;
        }
        if (isEmailExist(userAddDto.getEmail())) {
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST) ;
        }

        User user = UserConverter.mapAddUserDtoToUser(userAddDto);
        userRepository.save(user);

        // Get role ids
        List<Long> roleIds = userAddDto.getRoleIds();

        if(roleIds == null || roleIds.isEmpty()) {
            return ResponseResult.okResult();
        }

        List<Role> roles = roleRepository.findAllById(roleIds);

        if(roles.size() != roleIds.size()) {
            throw new SystemException(AppHttpCodeEnum.SOME_ROLE_NOT_FOUND) ;
        }

        // Erect relationship between user and role
        List<UserRole> userRoles = roles.stream()
                .map(role -> {
                    UserRole userRole = new UserRole();
                    userRole.setUser(user);
                    userRole.setRole(role);
                    return userRole;
                }).toList() ;

        userRoleRepository.saveAll(userRoles);

        return ResponseResult.okResult() ;
    }

    @Override
    public ResponseResult<Object> deleteUser(Long userId) {

        User user = userRepository.findById(userId)
                        .orElseThrow(() -> new SystemException(AppHttpCodeEnum.USER_NOT_FOUND)) ;

        user.setDelFlag(DEL_FLAG_DELETED);
        userRepository.save(user);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult<UserInfoAndRoleListVo> getUser(Long userId) {

        // Get roleIds Binding
        List<Long> roleIds = userRoleRepository.findRoleIdsByUserId(userId);

        // Get role list And VO Encapsulation
        List<Role> roles = roleRepository.findAll();
        List<RoleListVo> roleListVos = BeanCopyUtil.copyBeanList(roles, RoleListVo.class);

        // Get the user And VO Encapsulation
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new SystemException(AppHttpCodeEnum.USER_NOT_FOUND)) ;
        UserGetInfoVo userGetInfoVo = BeanCopyUtil.copyBean(user, UserGetInfoVo.class);

        return ResponseResult.okResult(new UserInfoAndRoleListVo(roleIds, roleListVos, userGetInfoVo)) ;
    }

    @Override
    @Transactional
    public ResponseResult<Object> editUser(UserEditDto userEditDto) {

        User user = userRepository.findById(userEditDto.getId())
                .orElseThrow(() -> new SystemException(AppHttpCodeEnum.USER_NOT_FOUND)) ;
        User editedUser = userRepository.save(setAttributesForUserEditing(user, userEditDto));

        // Get role ids
        List<Long> roleIds = userEditDto.getRoleIds();

        if(roleIds == null || roleIds.isEmpty()) {

            userRoleRepository.deleteByUser(editedUser);

            return ResponseResult.okResult();
        }

        userRoleRepository.deleteByUser(editedUser);

        List<Role> roles = roleRepository.findAllById(roleIds);
        if (roles.size() != roleIds.size()) {
            throw new SystemException(AppHttpCodeEnum.SOME_ROLE_NOT_FOUND) ;
        }

        // Erect relationship between user and role
        List<UserRole> userRoles = roles.stream()
                .map(role -> {
                    UserRole userRole = new UserRole();
                    userRole.setUser(editedUser);
                    userRole.setRole(role);
                    return userRole;
                })
                .toList();

        userRoleRepository.saveAll(userRoles);

        return ResponseResult.okResult() ;
    }

    private User setAttributesForUserEditing(User user, UserEditDto userEditDto) {

        user.setUserName(userEditDto.getUserName());
        user.setNickName(userEditDto.getNickName());
        user.setEmail(userEditDto.getEmail());
        user.setSex(userEditDto.getSex());
        user.setStatus(userEditDto.getStatus());
        user.setUpdateBy(SecurityUtil.getUserId());
        user.setUpdateTime(new Date());
        user.setDelFlag(DEL_FLAG_EXIST);

        return user ;
    }

    @Override
    public ResponseResult<Object> changeUserStatus(UserChangeStatusDto userChangeStatusDto) {

        User user = userRepository.findById(userChangeStatusDto.getId())
                        .orElseThrow(() -> new SystemException(AppHttpCodeEnum.USER_NOT_FOUND));

        user.setStatus(userChangeStatusDto.getStatus());
        userRepository.save(user);

        return ResponseResult.okResult() ;
    }

    @Override
    public List<User> listExistUsers() {

        return userRepository.findByStatusAndDelFlag(USER_TYPE_NORMAL, DEL_FLAG_EXIST) ;
    }

    @Override
    public ResponseResult<Object> forgotPassword(CheckInfoDto checkInfoDto) {

        try {
            String userName = checkInfoDto.getUserName();
            String nickName = checkInfoDto.getNickName();
            String email = checkInfoDto.getEmail();
            String phoneNumber = checkInfoDto.getPhoneNumber();

            Optional<User> userOptional = userRepository.findByUserName(userName);

            if (userOptional.isEmpty()) {
                throw new SystemException(AppHttpCodeEnum.USER_NOT_FOUND) ;
            }

            User user = userOptional.get();

            if (!user.getNickName().equals(nickName) || !user.getEmail().equals(email) || !user.getPhoneNumber().equals(phoneNumber)) {
                return handleUserInformationNotMatch() ;
            }

            redisCache.setCacheObject(BEING_VERIFIED_USER_USERNAME_REDIS_KEY, userName, 2, TimeUnit.MINUTES);

            mailService.sendMailForForgettingPassword(email);

            return ResponseResult.okResult(AppHttpCodeEnum.PASS_INFO_CHECK);

        } catch (Exception e) {
            log.error("Error occurred during password forgot process", e);
            return ResponseResult.errorResult(AppHttpCodeEnum.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseResult<Object> handleUserInformationNotMatch() {
        throw new SystemException(AppHttpCodeEnum.USER_INFO_NOT_MATCH);
    }

    @Override
    public ResponseResult<Object> verifyCode(String verificationCode) {

        try {
            String numberUsedOnceCode = redisCache.getCacheObject(FORGOT_PASSWORD_VERIFY_CODE_REDIS_KEY, String.class);

            if(numberUsedOnceCode == null) {
                return handleExpiredVerificationCode() ;
            }

            if(!verificationCode.equals(numberUsedOnceCode)) {
                return handleFailedVerificationCode() ;
            }

            // Remove code from redis when verified ( Whichever success or fail )
            redisCache.deleteObject(FORGOT_PASSWORD_VERIFY_CODE_REDIS_KEY);

            String beingVerifiedUserName = redisCache.getCacheObject(BEING_VERIFIED_USER_USERNAME_REDIS_KEY, String.class);
            redisCache.setCacheObject(PASS_CODE_VERIFICATION_REDIS_KEY, PASS_CODE_VERIFICATION_REDIS_VALUE_PRE + beingVerifiedUserName, 2, TimeUnit.MINUTES);
            redisCache.deleteObject(BEING_VERIFIED_USER_USERNAME_REDIS_KEY) ;

            return ResponseResult.okResult(AppHttpCodeEnum.PASS_VERIFICATION_CODE_CHECK) ;
        } catch (Exception e) {
            log.error("Error occurred during code verify process", e);
            return ResponseResult.errorResult(AppHttpCodeEnum.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseResult<Object> handleExpiredVerificationCode() {
        throw new SystemException(AppHttpCodeEnum.VERIFICATION_CODE_EXPIRED);
    }

    private ResponseResult<Object> handleFailedVerificationCode() {
        redisCache.deleteObject(FORGOT_PASSWORD_VERIFY_CODE_REDIS_KEY);
        throw new SystemException(AppHttpCodeEnum.FAIL_VERIFICATION_CODE_CHECK);
    }

    @Override
    public ResponseResult<Object> resetPassword(PasswordResetRequest passwordResetRequest) {

        try {
            String passCodeVerificationRedisValue = redisCache.getCacheObject(PASS_CODE_VERIFICATION_REDIS_KEY, String.class);

            if (passCodeVerificationRedisValue == null) {
                return handleExpiredPassCodeVerification() ;
            }

            if (!passCodeVerificationRedisValue.equals(PASS_CODE_VERIFICATION_REDIS_VALUE_PRE + passwordResetRequest.getUserName())) {
                return handleMisMatchedPassCodeVerificationValue() ;
            }

            redisCache.deleteObject(PASS_CODE_VERIFICATION_REDIS_KEY);

            Optional<User> userOptional = userRepository.findByUserName(passwordResetRequest.getUserName());
            if (userOptional.isEmpty()) {
                throw new SystemException(AppHttpCodeEnum.USER_NOT_FOUND);
            }

            User user = userOptional.get();
            String newPassword = passwordResetRequest.getNewPassword();
            String encodedNewPassword = passwordEncoder.encode(newPassword);
            user.setPassword(encodedNewPassword);

            userRepository.save(user);

            return ResponseResult.okResult(AppHttpCodeEnum.RESET_PASSWORD_FINISHED);
        } catch (Exception e) {
            log.error("Error occurred during reset password process", e);
            return ResponseResult.errorResult(AppHttpCodeEnum.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseResult<Object> handleExpiredPassCodeVerification() {
        throw new SystemException(AppHttpCodeEnum.RESET_PASSWORD_OVERTIME);
    }

    private ResponseResult<Object> handleMisMatchedPassCodeVerificationValue() {
        redisCache.deleteObject(PASS_CODE_VERIFICATION_REDIS_KEY);
        throw new SystemException(AppHttpCodeEnum.RESET_PASSWORD_VERIFY_FAIL);
    }

    private boolean isUserNameExist(String userName) {

        return userRepository.existsByUserName(userName) ;
    }

    private boolean isPhoneNumberExist(String phoneNumber) {

        return userRepository.existsByPhoneNumber(phoneNumber) ;
    }

    private boolean isEmailExist(String email) {

        return userRepository.existsByEmail(email) ;
    }
}
