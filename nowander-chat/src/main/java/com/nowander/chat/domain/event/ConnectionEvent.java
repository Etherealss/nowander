package com.nowander.chat.domain.event;

import com.nowander.chat.domain.DomainEvent;
import lombok.Getter;

/**
 * WebSocket 连接事件
 * @author wtk
 */
@Getter
public abstract class ConnectionEvent extends DomainEvent {

    private final String terminalId;
    private final String enterpriseCode;

    public ConnectionEvent(String terminalId, String enterpriseCode) {
        super(terminalId);
        this.enterpriseCode = enterpriseCode;
        this.terminalId = terminalId;
    }

}