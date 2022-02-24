package com.nowander.like.controller;


import com.nowander.common.pojo.po.User;
import com.nowander.common.web.JsonParam;
import com.nowander.like.pojo.po.LikeRecord;
import com.nowander.like.service.LikeService;
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
     * @param user
     * @param targetId
     * @param targetType
     * @param isLike
     * @return
     */
    @PostMapping("/do")
    public void doLike(User user, @JsonParam Integer targetId, @JsonParam Integer targetType,
                         @JsonParam Boolean isLike) {
        likeService.likeOrUnlike(new LikeRecord(user.getId(), targetId, targetType), isLike);
    }

    /**
     * 是否已点赞
     * @param user
     * @param targetId
     * @param targetType
     * @return
     */
    @GetMapping("/check")
    public Boolean checkHasLike(User user, @JsonParam Integer targetId, @JsonParam Integer targetType) {
        return likeService.checkHasLiked(new LikeRecord(user.getId(), targetId, targetType));
    }
}

