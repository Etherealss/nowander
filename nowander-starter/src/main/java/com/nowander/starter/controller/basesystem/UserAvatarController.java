package com.nowander.starter.controller.basesystem;

import com.nowander.basesystem.user.SysUser;
import com.nowander.basesystem.user.UserService;
import com.nowander.basesystem.user.security.anonymous.annotation.rest.AnonymousGetMapping;
import com.nowander.infrastructure.web.ResponseAdvice;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


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

    private final UserService userService;

    /**
     *
     * @param avatarFile
     * @param user
     * @return 用户头像的保存路径
     * @throws Exception
     */
    @PostMapping
    public String upload(@RequestParam("avatarFile") MultipartFile avatarFile,
                         SysUser user) throws Exception {
        return userService.uploadAndSetUserAvatar(avatarFile, user);
    }
}
