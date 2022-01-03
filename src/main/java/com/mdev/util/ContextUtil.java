package com.mdev.util;

import org.springframework.security.core.context.SecurityContextHolder;

public class ContextUtil {

    public static String getAuthorizedUserName(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
