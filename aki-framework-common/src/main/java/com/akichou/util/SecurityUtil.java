package com.akichou.util;

import com.akichou.domain.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Security Utility
 *
 * @author Aki Chou
 * @date 2024/06/19 Wed.
 */
public class SecurityUtil {

    public static LoginUser getLoginUser() {

        return (LoginUser) getAuthentication().getPrincipal();
    }

    public static Authentication getAuthentication() {

        return SecurityContextHolder.getContext().getAuthentication();
    }

    // User type 1 -> ADMIN
    public static Boolean isAdmin(){

        String type = getLoginUser().getUser().getType() ;
        return type.equals("1");
    }

    public static Long getUserId() {

        return getLoginUser().getUser().getId();
    }
}
