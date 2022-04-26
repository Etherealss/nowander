package com.nowander.like.likerecord.event;

import com.nowander.infrastructure.pojo.DomainEvent;
import com.nowander.like.likerecord.LikeRecord;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author wang tengkun
 * @date 2022/3/9
 */
@Setter
@Getter
public class LikeSaveBatchEvent extends DomainEvent {
    public LikeSaveBatchEvent(Integer userId) {
        super(userId);
    }
    private List<LikeRecord> toSave;
}
