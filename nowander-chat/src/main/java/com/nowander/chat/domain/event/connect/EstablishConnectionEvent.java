package com.nowander.chat.domain.event.connect;


/**
 * WebSocket 连接建立事件
 * @author wtk
 */
public class EstablishConnectionEvent extends ConnectionEvent {

    public EstablishConnectionEvent(Integer userId, String username) {
        super(userId, username);
    }

    @Override
    public String supportType() {
        return "establish_connection";
    }
}
