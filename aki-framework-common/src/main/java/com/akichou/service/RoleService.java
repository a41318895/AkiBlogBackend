package com.akichou.service;

import com.akichou.domain.ResponseResult;
import com.akichou.domain.dto.RoleAddDto;
import com.akichou.domain.dto.RoleChangeStatusDto;
import com.akichou.domain.dto.RoleEditDto;
import com.akichou.domain.dto.RoleSelectConditionsDto;
import com.akichou.domain.entity.Role;
import com.akichou.domain.vo.PageVo;
import com.akichou.domain.vo.RoleListVo;
import com.akichou.domain.vo.RolePageSelectVo;
import com.akichou.domain.vo.RoleInfoVo;

import java.util.List;

/**
 * Role Service Interface
 *
 * @author Aki Chou
 * @date 2024/06/24 Mon.
 */
public interface RoleService {

    List<String> getRoleKeysByUserId(Long id);

    ResponseResult<PageVo<RolePageSelectVo>> selectRoles(Integer pageNum, Integer pageSize, RoleSelectConditionsDto roleSelectConditionsDto);

    ResponseResult<Object> changeRoleStatus(RoleChangeStatusDto roleChangeStatusDto);

    ResponseResult<Object> addRole(RoleAddDto roleAddDto);

    ResponseResult<RoleInfoVo> getRoleInfo(Long roleId);

    ResponseResult<Object> editRole(RoleEditDto roleEditDto);

    ResponseResult<Object> deleteRole(Long roleId);

    ResponseResult<List<RoleListVo>> listAllRoles();

    List<Role> listExistRoles();
}
