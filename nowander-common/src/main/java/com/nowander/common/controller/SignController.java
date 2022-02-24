package com.nowander.common.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nowander.common.pojo.po.User;
import com.nowander.common.pojo.vo.Msg;
import com.nowander.common.service.TokenService;
import com.nowander.common.service.UserService;
import com.nowander.common.utils.TokenUtil;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class SignController {

    private UserService userService;

    private TokenService tokenService;
    @PostMapping("/login")
    public void login(HttpServletRequest request) {
        log.info("用户登录");
    }
}
