package com.nowander.common.utils;

import com.nowander.common.pojo.po.User;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author wang tengkun
 * @date 2022/3/1
 */
public class SecurityUtil {

    public static User getLoginUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User) {
            return (User) principal;
        }
        return null;
    }
}
