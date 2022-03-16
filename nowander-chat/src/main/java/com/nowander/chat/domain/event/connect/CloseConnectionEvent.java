package com.nowander.chat.domain.event.connect;

import cn.hutool.json.JSONObject;
import com.nowander.common.pojo.po.User;
import lombok.Getter;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * WebSocket 连接断开事件
 * @author wtk
 */
@Getter
public class CloseConnectionEvent extends ConnectionEvent {

    public CloseConnectionEvent(WebSocketSession session, JSONObject attrs, User user) {
        super(session, attrs, user);
    }
}