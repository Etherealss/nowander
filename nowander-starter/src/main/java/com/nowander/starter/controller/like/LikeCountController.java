package com.nowander.starter.controller.like;


import com.nowander.basesystem.user.security.anonymous.annotation.rest.AnonymousGetMapping;
import com.nowander.infrastructure.enums.LikeTargetType;
import com.nowander.infrastructure.web.ResponseAdvice;
import com.nowander.like.likecount.LikeCount;
import com.nowander.like.LikeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wtk
 * @since 2022-01-28
 */
@RestController
@RequestMapping("/likes/count")
@ResponseAdvice
@Slf4j
@AllArgsConstructor
public class LikeCountController {

    private LikeService likeService;

    /**
     * 获取点赞数
     * @param targetType
     * @param targetId
     * @return
     */
    @AnonymousGetMapping("/{targetType}/{targetId}")
    public Integer getLikeCount(@PathVariable LikeTargetType targetType,
                                @PathVariable Integer targetId){
        LikeCount likeCount = new LikeCount();
        likeCount.setTargetId(targetId);
        likeCount.setTargetType(targetType);
        return likeService.getTotalLikeCount(likeCount).getCount();
    }
}

