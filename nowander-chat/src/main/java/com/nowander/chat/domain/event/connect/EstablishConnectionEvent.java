package com.nowander.chat.domain.event.connect;


import cn.hutool.json.JSONObject;
import com.nowander.basesystem.user.SysUser;
import org.springframework.web.socket.WebSocketSession;

/**
 * WebSocket 连接建立事件
 * @author wtk
 */
public class EstablishConnectionEvent extends ConnectionEvent {

    public EstablishConnectionEvent(WebSocketSession session,  JSONObject attrs, SysUser sysUser) {
        super(session, attrs, sysUser);
    }
}
