package com.nowander.chat.domain.event.message;

import com.nowander.chat.domain.DomainEvent;

/**
 * @author wtk
 * @date 2022-03-05
 */
public abstract class SendDataEvent extends DomainEvent {
    public SendDataEvent(Integer userId, String username) {
        super(userId, userId, username);
    }
}
