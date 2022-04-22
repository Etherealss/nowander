package com.nowander.basesystem.user.security;

import com.nowander.basesystem.user.SysUser;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author wang tengkun
 * @date 2022/3/1
 */
public class SecurityUtil {

    public static SysUser getLoginUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof SysUser) {
            return (SysUser) principal;
        }
        return null;
    }
}
