package com.nowander.common.user.controller;

import com.nowander.common.pojo.po.User;
import com.nowander.common.user.service.TokenService;
import com.nowander.common.user.service.UserService;
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
     * 具体业务见 {@link com.nowander.common.security.LoginSuccessHandlerImpl }
     */
    @PostMapping("/login")
    public User login() {
        log.info("用户登录");
        User user = new User();
        user.setUsername("qlejqleqlejl");
        return user;
    }

    @GetMapping("/login")
    public void logout(HttpServletRequest request) {
        userService.logout(request);
    }

    @GetMapping("/reflesh")
    public String reflesh(HttpServletRequest request) {
        return tokenService.refleshToken(request);
    }
}
