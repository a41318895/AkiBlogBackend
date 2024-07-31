package com.akichou.service;

import com.akichou.domain.ResponseResult;
import com.akichou.domain.dto.MenuAddDto;
import com.akichou.domain.dto.MenuEditDto;
import com.akichou.domain.vo.*;

import java.util.List;

/**
 * Menu Service Interface
 *
 * @author Aki Chou
 * @date 2024/06/24 Mon.
 */
public interface MenuService {

    List<String> getPermsByUserId(Long id);

    List<MenuVo> getMenuRoutersInfo(Long userId);

    ResponseResult<List<MenuPageSelectVo>> listAllMenu(String menuName, Integer status);

    ResponseResult<Object> addMenu(MenuAddDto menuAddDto);

    ResponseResult<MenuInfoVo> getMenu(Long menuId);

    ResponseResult<Object> editMenu(MenuEditDto menuEditDto);

    ResponseResult<Object> deleteMenu(Long menuId);

    ResponseResult<List<MenuTreeSelectVo>> treeSelect();

    ResponseResult<RoleMenuTreeSelectVo> roleMenuTreeSelect(Long roleId);
}
