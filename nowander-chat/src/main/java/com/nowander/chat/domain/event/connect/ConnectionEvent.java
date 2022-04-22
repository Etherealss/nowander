package com.nowander.chat.domain.event.connect;

import cn.hutool.json.JSONObject;
import com.nowander.chat.domain.event.ChatEvent;
import com.nowander.basesystem.user.SysUser;
import lombok.Getter;
import org.springframework.web.socket.WebSocketSession;

/**
 * WebSocket 连接事件
 * @author wtk
 */
@Getter
public abstract class ConnectionEvent extends ChatEvent {
    public ConnectionEvent(WebSocketSession session, JSONObject attrs, SysUser sysUser) {
        super(session, attrs, sysUser);
    }
}
