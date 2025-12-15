package com.datamarket.backend.security.util;

import com.datamarket.backend.entity.User;
import com.datamarket.backend.exception.CustomException;
import com.datamarket.backend.exception.ErrorCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
    public static User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null || auth.getPrincipal() == null) {
            throw new CustomException(ErrorCode.AUTH_002);
        }
        return (User) auth.getPrincipal();
    }
    public static Long getCurrentUserId() {
        return getCurrentUser().getId();
    }

}
