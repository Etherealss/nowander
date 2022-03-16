package com.nowander.like.event;

import com.nowander.common.pojo.DomainEvent;
import com.nowander.like.pojo.po.LikeRecord;
import lombok.Data;
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
    public LikeSaveBatchEvent(Integer userId, String username) {
        super(userId, username);
    }
    private List<LikeRecord> toSave;
}
