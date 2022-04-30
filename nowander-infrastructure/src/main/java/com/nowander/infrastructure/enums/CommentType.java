package com.nowander.infrastructure.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nowander.infrastructure.exception.rest.EnumIllegalException;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wtk
 * @date 2022-04-26
 */
@AllArgsConstructor
@Getter
public enum CommentType implements BaseEnum {

    COMMENT(0, "comment"),
    REPLY_COMMENT(1, "reply"),
    SUB_REPLY(2, "subreply"),

    ;
    private final int code;
    private final String name;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static CommentType fromName(@JsonProperty("name") Object name) {
        for (CommentType object : CommentType.class.getEnumConstants()) {
            if (name.equals(object.getName())) {
                return object;
            }
        }
        throw new EnumIllegalException(CommentType.class, name);
    }
}
