package com.nowander.like.likerecord.event;

import com.nowander.like.LikeService;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author wang tengkun
 * @date 2022/3/9
 */
@Component
@AllArgsConstructor
public class LikeEventListener {
    private final LikeService likeService;

    @EventListener(LikeSaveBatchEvent.class)
    public void handleEvent(LikeSaveBatchEvent event) {
        likeService.saveBatch(event.getToSave());
    }

    @EventListener(LikeDeleteBatchEvent.class)
    public void handleEvent(LikeDeleteBatchEvent event) {
        likeService.saveBatch(event.getToDel());
    }
}
