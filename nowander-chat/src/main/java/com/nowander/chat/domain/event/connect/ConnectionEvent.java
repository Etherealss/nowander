package com.nowander.chat.domain.event.connect;

import com.nowander.chat.domain.DomainEvent;
import lombok.Getter;

/**
 * WebSocket 连接事件
 * @author wtk
 */
@Getter
public abstract class ConnectionEvent extends DomainEvent {
    public ConnectionEvent(Integer userId, String username) {
        super(userId, userId, username);
    }
}
