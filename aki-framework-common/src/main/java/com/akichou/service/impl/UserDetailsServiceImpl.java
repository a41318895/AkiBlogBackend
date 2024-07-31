package com.akichou.service.impl;

import com.akichou.customEnum.AppHttpCodeEnum;
import com.akichou.domain.LoginUser;
import com.akichou.domain.entity.User;
import com.akichou.exception.SystemException;
import com.akichou.repository.UserRepository;
import com.akichou.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.akichou.constant.SystemConstants.ADMIN_USER_TYPE;

/**
 * User Details Service Implementation
 *
 * @author Aki Chou
 * @date 2024/07/24 Wed.
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository ;
    private final MenuService menuService ;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Database Process
        Optional<User> userOptional = userRepository.findByUserName(username) ;

        // Check null or not
        if(userOptional.isEmpty()) {
            throw new SystemException(AppHttpCodeEnum.USER_NOT_FOUND) ;
        }

        // Check isAdmin
        User user = userOptional.get();
        if(user.getType().equals(ADMIN_USER_TYPE)) {

            List<String> perms = menuService.getPermsByUserId(user.getId());

            return new LoginUser(user, perms) ;
        }

        return new LoginUser(user, null);
    }
}
