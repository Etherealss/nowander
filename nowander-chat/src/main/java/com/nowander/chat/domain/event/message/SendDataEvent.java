package com.nowander.chat.domain.event.message;

import com.nowander.infrastructure.pojo.DomainEvent;

/**
 * @author wtk
 * @date 2022-03-05
 */
public abstract class SendDataEvent extends DomainEvent {
    public SendDataEvent(Integer userId) {
        super(userId, userId);
    }
}
