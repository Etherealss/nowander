package com.nowander.like.controller;


import com.nowander.like.pojo.po.LikeCount;
import com.nowander.like.service.LikeService;
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
@RequestMapping("/likeCount")
@Slf4j
@AllArgsConstructor
public class LikeCountController {

    private LikeService likeService;

    /**
     *
     * @param targetType
     * @param targetId
     * @return
     */
    @GetMapping("/{targetType}/{targetId}")
    public Integer getLikeCount(@PathVariable Integer targetType, @PathVariable Integer targetId){
        LikeCount likeCount = new LikeCount();
        likeCount.setTargetId(targetId);
        likeCount.setTargetType(targetType);
        return likeService.getTotalLikeCount(likeCount).getCount();
    }
}

