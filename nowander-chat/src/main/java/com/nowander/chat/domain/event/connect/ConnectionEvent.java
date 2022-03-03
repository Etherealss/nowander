package com.nowander.chat.domain.event.connect;

import com.nowander.chat.domain.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * WebSocket 连接事件
 * @author wtk
 */
@Getter
public abstract class ConnectionEvent extends DomainEvent {

    private final Integer userId;
    private final String username;

    public ConnectionEvent(Integer userId, String username) {
        super(userId);
        this.userId = userId;
        this.username = username;
    }
}
