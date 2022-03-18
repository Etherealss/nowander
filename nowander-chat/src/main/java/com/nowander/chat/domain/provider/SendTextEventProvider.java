package com.nowander.chat.domain.provider;

import cn.hutool.json.JSONObject;
import com.nowander.chat.core.socket.EventProvider;
import com.nowander.chat.domain.event.ChatEvent;
import com.nowander.chat.domain.event.message.SendTextEvent;
import com.nowander.common.pojo.po.User;
import org.springframework.web.socket.WebSocketSession;

/**
 * @author wang tengkun
 * @date 2022/3/16
 */
public class SendTextEventProvider implements EventProvider {
    @Override
    public String supportType() {
        return "sendText";
    }

    @Override
    public ChatEvent get(WebSocketSession session, JSONObject attrs, User user) {
        SendTextEvent sendTextEvent = new SendTextEvent(session, attrs, user);
        sendTextEvent.setContent(attrs.get("text", String.class));
        sendTextEvent.setReceverUserId(attrs.get("receverUserId", Integer.class));
        sendTextEvent.setReceverUsername(attrs.get("receverUsername", String.class));
        return sendTextEvent;
    }

}
