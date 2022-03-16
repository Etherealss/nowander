package com.nowander.chat.domain.event.connect;


import cn.hutool.json.JSONObject;
import com.nowander.common.pojo.po.User;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * WebSocket 连接建立事件
 * @author wtk
 */
public class EstablishConnectionEvent extends ConnectionEvent {

    public EstablishConnectionEvent(WebSocketSession session, TextMessage message, JSONObject attrs, User user) {
        super(session, message, attrs, user);
    }
}
