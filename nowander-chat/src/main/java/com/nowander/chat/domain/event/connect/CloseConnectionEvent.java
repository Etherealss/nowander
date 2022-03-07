package com.nowander.chat.domain.event.connect;

import lombok.Getter;

/**
 * WebSocket 连接断开事件
 * @author wtk
 */
@Getter
public class CloseConnectionEvent extends ConnectionEvent {

    public CloseConnectionEvent(Integer userId, String username) {
        super(userId, username);
    }

    @Override
    public String supportType() {
        return "close_connection";
    }
}