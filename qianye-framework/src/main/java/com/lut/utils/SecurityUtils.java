package com.lut.utils;

import com.lut.pojo.entity.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @Author 浅夜
 * @Description 用于获取用户等字段信息的工具类
 * @DateTime 2024/2/17 23:51
 **/
public class SecurityUtils {
    /**
     * 获取用户
     **/
    public static LoginUser getLoginUser()
    {
        Object principal = getAuthentication().getPrincipal();
        return (principal != null && principal instanceof LoginUser) ? (LoginUser) principal : null;
    }

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        return authentication;
    }

    public static Boolean isAdmin(){
        Long id = getLoginUser().getUser().getId();
        return id != null && 1L == id;
    }

    public static Long getUserId() {
        if(getLoginUser() == null) return 1L;
        return getLoginUser().getUser().getId();
    }
}
