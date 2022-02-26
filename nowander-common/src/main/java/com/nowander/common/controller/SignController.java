package com.nowander.common.controller;

import com.nowander.common.service.TokenService;
import com.nowander.common.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/login")
    public void login() {
        log.info("用户登录");
    }

    @GetMapping("/login")
    public void logout() {
        log.info("用户退出登录");
    }
}
