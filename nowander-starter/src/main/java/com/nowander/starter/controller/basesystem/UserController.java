package com.nowander.starter.controller.basesystem;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nowander.basesystem.user.SysUser;
import com.nowander.basesystem.user.security.anonymous.annotation.rest.AnonymousGetMapping;
import com.nowander.infrastructure.pojo.Msg;
import com.nowander.basesystem.user.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author wtk
 * @since 2022-01-05
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @AnonymousGetMapping("/exist/username/{username}")
    public Msg<Boolean> usernameExist(@PathVariable String username) {
        log.trace("查看用户名是否已存在：{}", username);
        int count = userService.count(new QueryWrapper<SysUser>().eq("username", username));
        return Msg.ok(count == 1);
    }

    @AnonymousGetMapping("/exist/email/{email}")
    public Msg<Boolean> emailExist(@PathVariable String email) {
        log.trace("查看用户名是否已存在：{}", email);
        int count = userService.count(new QueryWrapper<SysUser>().eq("email", email));
        return Msg.ok(count == 1);
    }
}

