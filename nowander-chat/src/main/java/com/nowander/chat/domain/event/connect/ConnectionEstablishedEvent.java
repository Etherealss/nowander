package com.nowander.chat.domain.event.connect;

import lombok.Getter;

/**
 * WebSocket 连接建立事件
 */
@Getter
public class ConnectionEstablishedEvent extends ConnectionEvent {

    public ConnectionEstablishedEvent(Integer userId, String username) {
        super(userId, username);
    }

}
