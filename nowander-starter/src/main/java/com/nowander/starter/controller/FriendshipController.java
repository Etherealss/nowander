package com.nowander.starter.controller;


import com.nowander.chat.service.FriendshipService;
import com.nowander.common.pojo.po.User;
import com.nowander.common.web.JsonParam;
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
    public List<User> getFriends(User user) {
        return friendshipService.getFriends(user);
    }

    @PostMapping
    public void requestAddFriend(User user, @JsonParam("targetId") Integer targetId, @JsonParam("message") String message) {

    }
}

