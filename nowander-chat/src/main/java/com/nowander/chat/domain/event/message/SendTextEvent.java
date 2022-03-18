package com.nowander.chat.domain.event.message;

import cn.hutool.json.JSONObject;
import com.nowander.chat.domain.event.ChatEvent;
import com.nowander.common.pojo.po.User;
import lombok.*;
import org.springframework.web.socket.WebSocketSession;

import javax.validation.constraints.NotBlank;

/**
 * @author wtk
 * @date 2022-03-05
 */
@Getter
@Setter
@ToString
public class SendTextEvent extends ChatEvent {

    private Integer receverUserId;
    @NotBlank
    private String receverUsername;
    @NotBlank
    private String content;

    public SendTextEvent(WebSocketSession session, JSONObject attrs, User user) {
        super(session, attrs, user);
    }
}
