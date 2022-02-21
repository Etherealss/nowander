package com.nowander.chat.domain.event;

import lombok.Getter;

/**
 * WebSocket 连接建立事件
 */
@Getter
public class ConnectionEstablishedEvent extends ConnectionEvent {

    public ConnectionEstablishedEvent(String terminalId, String enterpriseCode) {
        super(terminalId, enterpriseCode);
    }

}
