package com.nowander.starter.controller;


import com.nowander.chat.service.FriendshipService;
import com.nowander.basesystem.user.SysUser;
import com.nowander.infrastructure.web.JsonParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wtk
 * @since 2022-03-08
 */
@RestController
@RequestMapping("/friendship")
public class FriendshipController {
    private FriendshipService friendshipService;

    @GetMapping
    public List<SysUser> getFriends(SysUser sysUser) {
        return friendshipService.getFriends(sysUser);
    }

    @PostMapping
    public void requestAddFriend(SysUser sysUser, @JsonParam("targetId") Integer targetId, @JsonParam("message") String message) {

    }
}

