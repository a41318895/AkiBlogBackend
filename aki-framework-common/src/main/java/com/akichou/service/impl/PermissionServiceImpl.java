package com.akichou.service.impl;

import com.akichou.service.PermissionService;
import com.akichou.util.SecurityUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Permission Service Implementation
 *
 * @author Aki Chou
 * @date 2024/07/24 Wed.
 */
@Service("ps")
public class PermissionServiceImpl implements PermissionService {

    @Override
    public boolean hasPermission(String permission) {

        // Handle Admin Situation
        if(SecurityUtil.isAdmin()) {

            return true;
        }

        // Handle Other Situation
        List<String> permissions = SecurityUtil.getLoginUser().getPermissions();

        return permissions.contains(permission);
    }
}
