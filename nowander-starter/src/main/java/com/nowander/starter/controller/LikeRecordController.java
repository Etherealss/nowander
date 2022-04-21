package com.nowander.starter.controller;


import com.nowander.common.web.JsonParam;
import com.nowander.like.likerecord.LikeRecord;
import com.nowander.like.LikeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 前端控制器
 * @author wtk
 * @since 2022-01-05
 */
@Slf4j
@RestController
@RequestMapping("/likes")
@AllArgsConstructor
public class LikeRecordController {

    private LikeService likeService;

    /**
     * 点赞或取消点赞
     * @return
     */
    @PostMapping("/do")
    public void doLike(@JsonParam Integer userId, @JsonParam Integer targetId, @JsonParam Integer targetType,
                         @JsonParam Boolean isLike) {
        likeService.likeOrUnlike(new LikeRecord(userId, targetId, targetType), isLike);
    }

    /**
     * 是否已点赞
     * @return
     */
    @GetMapping("/check")
    public Boolean checkHasLike(@JsonParam Integer userId, @JsonParam Integer targetId, @JsonParam Integer targetType) {
        return likeService.checkHasLiked(new LikeRecord(userId, targetId, targetType));
    }
}

