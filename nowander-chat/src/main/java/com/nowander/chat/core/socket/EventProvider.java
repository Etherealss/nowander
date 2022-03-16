package com.nowander.chat.core.socket;

import cn.hutool.json.JSONObject;
import com.nowander.chat.domain.event.ChatEvent;
import com.nowander.common.pojo.DomainEvent;
import com.nowander.common.pojo.po.User;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * @author wang tengkun
 * @date 2022/3/16
 */
public interface EventProvider {
    String support();
    ChatEvent get(WebSocketSession session, JSONObject attrs, User user);
}
