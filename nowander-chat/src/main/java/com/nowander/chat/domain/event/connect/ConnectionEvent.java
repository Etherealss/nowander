package com.nowander.chat.domain.event.connect;

import cn.hutool.json.JSONObject;
import com.nowander.chat.domain.event.ChatEvent;
import com.nowander.common.pojo.DomainEvent;
import com.nowander.common.pojo.po.User;
import lombok.Getter;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * WebSocket 连接事件
 * @author wtk
 */
@Getter
public abstract class ConnectionEvent extends ChatEvent {
    public ConnectionEvent(WebSocketSession session, TextMessage message, JSONObject attrs, User user) {
        super(session, message, attrs, user);
    }
}
