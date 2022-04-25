package com.nowander.starter.controller;


import com.nowander.basesystem.user.SysUser;
import com.nowander.basesystem.user.security.SecurityUtil;
import com.nowander.infrastructure.enums.LikeTargetType;
import com.nowander.infrastructure.web.ResponseAdvice;
import com.nowander.like.likerecord.LikeRecordCommand;
import com.nowander.like.likerecord.LikeRecord;
import com.nowander.like.LikeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 前端控制器
 * @author wtk
 * @since 2022-01-05
 */
@Slf4j
@RestController
@ResponseAdvice
@RequestMapping("/likes/record")
@AllArgsConstructor
public class LikeRecordController {

    private LikeService likeService;

    /**
     * 点赞或取消点赞
     * @return
     */
    @PostMapping
    public void doLike(@RequestBody @Validated LikeRecordCommand likeRecordCommand, SysUser user) {
        likeService.likeOrUnlike(likeRecordCommand, user);
    }

    /**
     * 是否已点赞
     * @return
     */
    @GetMapping("/{targetType}/{targetId}")
    public Boolean checkHasLike(@PathVariable("targetType") LikeTargetType targetType,
                                @PathVariable("targetId") Integer targetId,
                                SysUser user) {
        return likeService.checkHasLiked(new LikeRecord(targetType, targetId, user.getId()));
    }
}

