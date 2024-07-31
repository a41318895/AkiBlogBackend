package com.akichou.service.impl;

import com.akichou.customEnum.AppHttpCodeEnum;
import com.akichou.domain.ResponseResult;
import com.akichou.domain.dto.RoleAddDto;
import com.akichou.domain.dto.RoleChangeStatusDto;
import com.akichou.domain.dto.RoleEditDto;
import com.akichou.domain.dto.RoleSelectConditionsDto;
import com.akichou.domain.entity.*;
import com.akichou.domain.vo.PageVo;
import com.akichou.domain.vo.RoleListVo;
import com.akichou.domain.vo.RolePageSelectVo;
import com.akichou.domain.vo.RoleInfoVo;
import com.akichou.exception.SystemException;
import com.akichou.repository.*;
import com.akichou.service.RoleService;
import com.akichou.util.BeanCopyUtil;
import com.akichou.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

import static com.akichou.constant.SystemConstants.*;

/**
 * Role Service Implementation
 *
 * @author Aki Chou
 * @date 2024/07/24 Wed.
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final RoleRepository roleRepository;
    private final MenuRepository menuRepository;
    private final RoleMenuRepository roleMenuRepository;

    @Override
    public List<String> getRoleKeysByUserId(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new SystemException(AppHttpCodeEnum.USER_NOT_FOUND));

        // If super admin user, add admin roleKey
        String userType = user.getType();
        if(userType.equals(ADMIN_USER_TYPE)){

            return List.of("admin");
        }

        List<Long> roleIds = userRoleRepository.findRoleIdsByUserId(id) ;
        List<Role> roles = roleRepository.findByIdInAndStatus(roleIds, ROLE_NORMAL_STATUS) ;

        return roles.stream()
                .map(Role::getRoleKey)
                .toList() ;
    }

    @Override
    public ResponseResult<PageVo<RolePageSelectVo>> selectRoles(Integer pageNum, Integer pageSize,
                                                                RoleSelectConditionsDto roleSelectConditionsDto) {

        // Extract parameters
        String roleName = Optional.ofNullable(roleSelectConditionsDto.getRoleName()).filter(StringUtils::hasText).orElse(null);
        String status = Optional.ofNullable(roleSelectConditionsDto.getStatus()).filter(StringUtils::hasText).orElse(null);

        // Database Process
        PageRequest pageRequest = PageRequest.of(pageNum - 1, pageSize);
        Page<Role> page = roleRepository.findAllByRoleNameAndStatus(
                roleName,
                status,
                pageRequest) ;

        // Vo Encapsulation
        List<RolePageSelectVo> rolePageSelectVos = BeanCopyUtil.copyBeanList(page.getContent(), RolePageSelectVo.class);

        return ResponseResult.okResult(new PageVo<>(rolePageSelectVos, page.getTotalElements())) ;
    }

    @Override
    public ResponseResult<Object> changeRoleStatus(RoleChangeStatusDto roleChangeStatusDto) {

        Role role = roleRepository.findById(roleChangeStatusDto.getId())
                        .orElseThrow(() -> new SystemException(AppHttpCodeEnum.ROLE_NOT_FOUND));

        role.setStatus(roleChangeStatusDto.getStatus());
        roleRepository.save(role) ;

        return ResponseResult.okResult() ;
    }

    @Override
    public ResponseResult<Object> addRole(RoleAddDto roleAddDto) {

        // Save New Role
        Role addedRole = mapRoleAddDtoToRole(roleAddDto) ;
        Role savedRole = roleRepository.save(addedRole);

        // Get menu ids
        List<Long> menuIds = roleAddDto.getMenuIds();

        if(menuIds == null || menuIds.isEmpty()) {

            return ResponseResult.okResult() ;
        }

        List<Menu> menus = menuRepository.findAllById(menuIds);
        if (menus.size() != menuIds.size()) {
            throw new SystemException(AppHttpCodeEnum.SOME_MENU_NOT_FOUND) ;
        }

        // Erecting RoleMenu
        List<RoleMenu> roleMenus = menus.stream()
                .map(menu -> {
                    RoleMenu roleMenu = new RoleMenu();
                    roleMenu.setRole(savedRole);
                    roleMenu.setMenu(menu);
                    return roleMenu;
                })
                .toList();

        // Database Process - Saving RoleMenus
        roleMenuRepository.saveAll(roleMenus);

        return ResponseResult.okResult() ;
    }

    private Role mapRoleAddDtoToRole(RoleAddDto roleAddDto) {

        return Role.builder()
                .roleName(roleAddDto.getRoleName())
                .roleKey(roleAddDto.getRoleKey())
                .roleSort(roleAddDto.getRoleSort())
                .status(roleAddDto.getStatus())
                .remark(roleAddDto.getRemark())
                .createBy(SecurityUtil.getUserId())
                .createTime(new Date())
                .updateBy(SecurityUtil.getUserId())
                .updateTime(new Date())
                .delFlag(DEL_FLAG_EXIST)
                .build();
    }

    @Override
    public ResponseResult<RoleInfoVo> getRoleInfo(Long roleId) {

        // Database Process
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new SystemException(AppHttpCodeEnum.ROLE_NOT_FOUND));

        // VO Encapsulation
        RoleInfoVo roleInfoVo = BeanCopyUtil.copyBean(role, RoleInfoVo.class);

        return ResponseResult.okResult(roleInfoVo) ;
    }

    @Override
    @Transactional
    public ResponseResult<Object> editRole(RoleEditDto roleEditDto) {

        Role role = roleRepository.findById(roleEditDto.getId())
                .orElseThrow(() -> new SystemException(AppHttpCodeEnum.ROLE_NOT_FOUND));
        Role editedRole = roleRepository.save(setAttributesForRoleEditing(role, roleEditDto));

        // Get menu ids
        List<Long> menuIds = roleEditDto.getMenuIds();

        if(menuIds == null || menuIds.isEmpty()) {

            roleMenuRepository.deleteByRole(editedRole) ;

            return ResponseResult.okResult() ;
        }

        roleMenuRepository.deleteByRole(editedRole) ;

        List<Menu> menus = menuRepository.findAllById(menuIds);
        if(menus.size() != menuIds.size()) {
            throw new SystemException(AppHttpCodeEnum.SOME_MENU_NOT_FOUND) ;
        }

        List<RoleMenu> roleMenus = menus.stream()
                .map(menu -> {
                    RoleMenu roleMenu = new RoleMenu();
                    roleMenu.setRole(editedRole);
                    roleMenu.setMenu(menu);
                    return roleMenu;
                }).toList();

        roleMenuRepository.saveAll(roleMenus);

        return ResponseResult.okResult() ;
    }

    private Role setAttributesForRoleEditing(Role role, RoleEditDto roleEditDto) {

        role.setRoleName(roleEditDto.getRoleName());
        role.setRoleKey(roleEditDto.getRoleKey());
        role.setRoleSort(roleEditDto.getRoleSort());
        role.setStatus(roleEditDto.getStatus());
        role.setUpdateBy(SecurityUtil.getUserId());
        role.setUpdateTime(new Date());
        role.setRemark(roleEditDto.getRemark());

        return role ;
    }

    @Override
    public ResponseResult<Object> deleteRole(Long roleId) {

        Role role = roleRepository.findById(roleId)
                        .orElseThrow(() -> new SystemException(AppHttpCodeEnum.ROLE_NOT_FOUND));

        role.setDelFlag(DEL_FLAG_DELETED);
        roleRepository.save(role) ;

        return ResponseResult.okResult() ;
    }

    @Override
    public ResponseResult<List<RoleListVo>> listAllRoles() {

        // Database Process
        List<Role> roles = roleRepository.findAll();

        // VO Encapsulation
        List<RoleListVo> roleListVos = BeanCopyUtil.copyBeanList(roles, RoleListVo.class);

        return ResponseResult.okResult(roleListVos) ;
    }

    @Override
    public List<Role> listExistRoles() {

        return roleRepository.findByStatusAndDelFlag(ROLE_NORMAL_STATUS, DEL_FLAG_EXIST) ;
    }
}
