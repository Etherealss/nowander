package com.nowander.infrastructure.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nowander.infrastructure.exception.rest.EnumIllegalException;
import com.nowander.infrastructure.pojo.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 表示文章下的评论还是问贴下的评论
 * @author wtk
 * @date 2022-04-26
 */
@AllArgsConstructor
@Getter
public enum CommentParentType implements BaseEnum {

    ARTICLE(0, "article"),
    POSTS(1, "posts"),

    ;
    private final int code;
    private final String name;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static CommentParentType fromName(@JsonProperty("name") Object name) {
        for (CommentParentType object : CommentParentType.class.getEnumConstants()) {
            if (name.equals(object.getName())) {
                return object;
            }
        }
        throw new EnumIllegalException(CommentParentType.class, name);
    }
}
