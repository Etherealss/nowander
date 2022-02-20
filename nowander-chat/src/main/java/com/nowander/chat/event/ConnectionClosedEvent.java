package com.nowander.chat.event;

import lombok.Getter;

/**
 * WebSocket 连接断开事件
 */
@Getter
public class ConnectionClosedEvent extends ConnectionEvent {

    public ConnectionClosedEvent(String terminalId, String enterpriseCode) {
        super(terminalId, enterpriseCode);
    }

}