package com.nowander.blog.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nowander.blog.service.UserService;
import com.wanderfour.nowander.pojo.po.User;
import com.wanderfour.nowander.pojo.vo.Msg;
import com.wanderfour.nowander.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wtk
 * @date 2022-01-05
 */
@Slf4j
@RestController
@RequestMapping("/users")
public class SignController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Msg<User> login(HttpServletRequest request) {
        log.info("用户登录，获取信息");
        return Msg.ok(TokenUtil.getUserByToken(request));
    }

    @GetMapping("is_exist/{email}")
    public Msg<Boolean> usernameExist(@PathVariable String email) {
        log.trace("查看用户名是否已存在：{}", email);
        int count = userService.count(new QueryWrapper<User>().eq("email", email));
        return Msg.ok(count == 1);
    }

}
