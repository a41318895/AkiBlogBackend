package com.akichou.service.impl;

import com.akichou.customEnum.AppHttpCodeEnum;
import com.akichou.domain.ResponseResult;
import com.akichou.domain.dto.MenuAddDto;
import com.akichou.domain.dto.MenuEditDto;
import com.akichou.domain.entity.Menu;
import com.akichou.domain.entity.RoleMenu;
import com.akichou.domain.entity.User;
import com.akichou.domain.vo.*;
import com.akichou.exception.SystemException;
import com.akichou.repository.MenuRepository;
import com.akichou.repository.RoleMenuRepository;
import com.akichou.repository.UserRepository;
import com.akichou.repository.UserRoleRepository;
import com.akichou.service.MenuService;
import com.akichou.util.BeanCopyUtil;
import com.akichou.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.akichou.constant.SystemConstants.*;

/**
 * Menu Service Implementation
 *
 * @author Aki Chou
 * @date 2024/06/24 Mon.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository ;
    private final UserRoleRepository userRoleRepository ;
    private final RoleMenuRepository roleMenuRepository ;
    private final UserRepository userRepository ;

    @Override
    public List<String> getPermsByUserId(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new SystemException(AppHttpCodeEnum.USER_NOT_FOUND));

        // If super admin user, get all perms
        String userType = user.getType();
        if(userType.equals(ADMIN_USER_TYPE)){

            return menuRepository.findPermsByMenuTypes(Arrays.asList(TYPE_MENU, TYPE_BUTTON), MENU_NORMAL_STATUS);
        }

        // Selecting all role ids binding with user ids
        List<Long> roleIds = userRoleRepository.findRoleIdsByUserId(id);

        // Selecting all menu ids binding with role ids
        List<Long> menuIds = roleMenuRepository.findMenuIdsByRoleIds(roleIds);

        List<Menu> menus =
                menuRepository.findByIdAndMenuTypes(menuIds, MENU_NORMAL_STATUS, Arrays.asList(TYPE_MENU, TYPE_BUTTON));

        return menus.stream()
                .map(Menu::getPerms)
                .toList() ;
    }

    @Override
    public List<MenuVo> getMenuRoutersInfo(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new SystemException(AppHttpCodeEnum.USER_NOT_FOUND));

        // If super admin user, get all perms
        String userType = user.getType();
        if(userType.equals(ADMIN_USER_TYPE)){

            List<Menu> menus =
                    menuRepository.findByMenuTypesOrdered(MENU_NORMAL_STATUS, Arrays.asList(TYPE_MENU, TYPE_BUTTON)) ;

            return buildMenuTree(menus) ;
        }

        List<Long> roleIds = userRoleRepository.findRoleIdsByUserId(userId);
        List<Long> menuIds = roleMenuRepository.findMenuIdsByRoleIds(roleIds);

        List<Menu> menus =
                menuRepository.findByIdAndMenuTypes(menuIds, MENU_NORMAL_STATUS, Arrays.asList(TYPE_MENU, TYPE_CATALOG));

        return buildMenuTree(menus);
    }

    @Override
    public ResponseResult<List<MenuPageSelectVo>> listAllMenu(String menuName, Integer status) {

        // Database Process
        List<Menu> menus = menuRepository.findAllByMenuNameAndStatusOrdered(menuName, status);

        // VO Encapsulation
        List<MenuPageSelectVo> menuPageSelectVos = BeanCopyUtil.copyBeanList(menus, MenuPageSelectVo.class);

        return ResponseResult.okResult(menuPageSelectVos);
    }

    @Override
    public ResponseResult<Object> addMenu(MenuAddDto menuAddDto) {

        Menu addedMenu = mapMenuAddDtoToMenu(menuAddDto) ;

        menuRepository.save(addedMenu);

        return ResponseResult.okResult();
    }

    private Menu mapMenuAddDtoToMenu(MenuAddDto menuAddDto) {

        return Menu.builder()
                .menuName(menuAddDto.getMenuName())
                .parentId(menuAddDto.getParentId())
                .orderNum(menuAddDto.getOrderNum())
                .path(menuAddDto.getPath())
                .component(menuAddDto.getComponent())
                .menuType(menuAddDto.getMenuType())
                .visible(menuAddDto.getVisible())
                .status(menuAddDto.getStatus())
                .perms(menuAddDto.getPerms())
                .icon(menuAddDto.getIcon())
                .isFrame(NOT_FRAME_MENU)
                .remark(menuAddDto.getRemark())
                .createBy(SecurityUtil.getUserId())
                .createTime(new Date())
                .updateBy(SecurityUtil.getUserId())
                .updateTime(new Date())
                .delFlag(DEL_FLAG_EXIST)
                .build();
    }

    @Override
    public ResponseResult<MenuInfoVo> getMenu(Long menuId) {

        // Database Process
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new SystemException(AppHttpCodeEnum.MENU_NOT_FOUND));

        // Vo Encapsulation
        MenuInfoVo menuInfoVo = BeanCopyUtil.copyBean(menu, MenuInfoVo.class);

        return ResponseResult.okResult(menuInfoVo);
    }

    @Override
    public ResponseResult<Object> editMenu(MenuEditDto menuEditDto) {

        Menu menu = menuRepository.findById(menuEditDto.getId())
                        .orElseThrow(() -> new SystemException(AppHttpCodeEnum.MENU_NOT_FOUND));

        menuRepository.save(setAttributesForMenuEditing(menu, menuEditDto));

        return ResponseResult.okResult() ;
    }

    private Menu setAttributesForMenuEditing(Menu menu, MenuEditDto menuEditDto) {

        menu.setMenuName(menuEditDto.getMenuName());
        menu.setParentId(menuEditDto.getParentId());
        menu.setMenuType(menuEditDto.getMenuType());
        menu.setOrderNum(menuEditDto.getOrderNum());
        menu.setIcon(menuEditDto.getIcon());
        menu.setComponent(menuEditDto.getComponent());
        menu.setPath(menuEditDto.getPath());
        menu.setPerms(menuEditDto.getPerms());
        menu.setStatus(menuEditDto.getStatus());
        menu.setVisible(menuEditDto.getVisible());
        menu.setUpdateBy(SecurityUtil.getUserId());
        menu.setUpdateTime(new Date());

        return menu ;
    }

    @Override
    public ResponseResult<Object> deleteMenu(Long menuId) {

        List<Long> parentIds = menuRepository.findAllParentIds() ;

        if (parentIds.contains(menuId)) {
            return ResponseResult.errorResult(500, "Children menu is existing, so can't be deleted !") ;
        }

        Menu menu = menuRepository.findByIdAndStatusAndDelFlag(menuId, MENU_NORMAL_STATUS, DEL_FLAG_EXIST);
        menu.setDelFlag(DEL_FLAG_DELETED);
        menuRepository.save(menu);

        return ResponseResult.okResult() ;
    }

    @Override
    public ResponseResult<List<MenuTreeSelectVo>> treeSelect() {

        List<Menu> menus = menuRepository.findAll();

        // Copy properties from Menu to MenuTreeSelectVo and initialize children list
        Map<Long, MenuTreeSelectVo> map = menus.stream()
                .collect(Collectors.toMap(Menu::getId,
                        menu -> {
                            MenuTreeSelectVo vo = BeanCopyUtil.copyBean(menu, MenuTreeSelectVo.class);
                            vo.setChildren(new ArrayList<>()); // Initialize with empty list
                            return vo;
                        }));

        List<MenuTreeSelectVo> menuTreeSelectVos = new ArrayList<>();

        // Build the tree structure
        map.values().forEach(menu -> {
            if (Objects.equals(menu.getParentId(), ROOT_MENU_PARENT_ID)) {
                menuTreeSelectVos.add(menu);
            } else {
                MenuTreeSelectVo parentMenu = map.get(menu.getParentId());
                if (parentMenu != null) {
                    parentMenu.getChildren().add(menu);
                }
            }
        });

        return ResponseResult.okResult(menuTreeSelectVos);
    }

    @Override
    public ResponseResult<RoleMenuTreeSelectVo> roleMenuTreeSelect(Long roleId) {

        List<MenuTreeSelectVo> menuTreeSelectVos = treeSelect().getData();

        List<RoleMenu> roleMenus = roleMenuRepository.findByRoleId(roleId) ;
        List<Long> menuIds = roleMenus.stream()
                .map(roleMenu -> roleMenu.getMenu().getId())
                .toList();

        return ResponseResult.okResult(new RoleMenuTreeSelectVo(menuTreeSelectVos, menuIds));
    }

    private List<MenuVo> buildMenuTree(List<Menu> menus) {

        List<MenuVo> menuVos = BeanCopyUtil.copyBeanList(menus, MenuVo.class) ;

        return menuVos.stream()
                .filter(menuVo -> menuVo.getParentId().equals(ROOT_MENU_ID))
                .map(menuVo -> menuVo.setChildren(getChildren(menuVo, menuVos)))
                .toList() ;
    }

    private List<MenuVo> getChildren(MenuVo menuVo, List<MenuVo> menuVos) {

        return menuVos.stream()
                .filter(menuVo1 -> menuVo1.getParentId().equals(menuVo.getId()))
                .map(menuVo1 -> menuVo1.setChildren(getChildren(menuVo1, menuVos)))
                .toList() ;
    }
}
