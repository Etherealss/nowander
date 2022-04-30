package com.nowander.starter.controller.basesystem;

import com.nowander.basesystem.user.avatar.AvatarService;
import com.nowander.basesystem.user.security.anonymous.annotation.rest.AnonymousGetMapping;
import com.nowander.basesystem.user.security.anonymous.annotation.rest.AnonymousPostMapping;
import com.nowander.infrastructure.web.ResponseAdvice;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author wtk
 * @date 2022-04-30
 */
@ResponseAdvice
@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/users/avatars")
public class UserAvatarController {

    private final AvatarService avatarService;

    @AnonymousGetMapping("/{userAvatarPathAndName}")
    public String logout(@PathVariable String userAvatarPathAndName) {
        return avatarService.getAvatarUrl(userAvatarPathAndName);
    }

    @AnonymousPostMapping
    public String testUpload() throws Exception {
        return avatarService.testUploal();
    }
}
