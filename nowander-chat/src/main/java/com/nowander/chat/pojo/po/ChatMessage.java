package com.nowander.chat.pojo.po;

import com.nowander.infrastructure.pojo.IdentifiedEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wang tengkun
 * @date 2022/3/3
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ChatMessage extends IdentifiedEntity {
    private Integer senderId;
    private Integer receverId;
    private String senderUsername;
    private String receverUsername;
    private String content;
}
