package com.nowander.infrastructure.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nowander.infrastructure.exception.rest.EnumIllegalException;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wang tengkun
 * @date 2022/4/26
 */
@Getter
@AllArgsConstructor
public enum BlogType implements BaseEnum {
    ARTICLE(0, "article"),
    POSTS(1, "posts")
    ;
    private final int code;
    private final String name;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static BlogType fromName(@JsonProperty("name") Object name) {
        for (BlogType object : BlogType.class.getEnumConstants()) {
            if (name.equals(object.getName())) {
                return object;
            }
        }
        throw new EnumIllegalException(BlogType.class, name);
    }
}
