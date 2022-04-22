package com.nowander.starter.controller;

import com.nowander.basesystem.user.SysUser;
import com.nowander.basesystem.user.security.login.LoginSuccessHandler;
import com.nowander.basesystem.user.TokenService;
import com.nowander.basesystem.user.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wtk
 * @date 2022-01-05
 */
@Slf4j
@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class SignController {

    private UserService userService;

    private TokenService tokenService;

    /**
     * 具体业务见 {@link LoginSuccessHandler }
     */
    @PostMapping("/login")
    public SysUser login() {
        log.info("用户登录");
        SysUser sysUser = new SysUser();
        sysUser.setUsername("qlejqleqlejl");
        return sysUser;
    }

    @GetMapping("/logout")
    public void logout(HttpServletRequest request) {
        userService.logout(request);
    }

    @GetMapping("/reflesh")
    public String reflesh(HttpServletRequest request) {
        return tokenService.refleshToken(request);
    }
}
