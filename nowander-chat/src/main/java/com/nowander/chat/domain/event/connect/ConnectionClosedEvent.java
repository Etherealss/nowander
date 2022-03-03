package com.nowander.chat.domain.event.connect;

import lombok.Getter;

/**
 * WebSocket 连接断开事件
 */
@Getter
public class ConnectionClosedEvent extends ConnectionEvent {

    public ConnectionClosedEvent(Integer userId,String username) {
        super(userId, username);
    }
}