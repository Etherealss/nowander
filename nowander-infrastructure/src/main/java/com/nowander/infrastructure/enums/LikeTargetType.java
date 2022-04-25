package com.nowander.infrastructure.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.nowander.infrastructure.exception.EnumIllegalException;
import com.nowander.infrastructure.pojo.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 点赞目标类型
 * @author wtk
 * @date 2022-01-27
 */
@Getter
@AllArgsConstructor
public enum LikeTargetType implements BaseEnum {
    ARTICLE(1, "article"),
    POSTS(2, "posts"),
    COMMENT(3, "comment")
    ;
    private final int code;
    private final String name;

    /**
     * {@code @JsonCreator} 在 {@code @ReuqestBody} 时会自动通过该注解的方法创建枚举
     * @param name
     * @return
     */
    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static LikeTargetType fromName(@JsonProperty("name") Object name) {
        for (LikeTargetType object : LikeTargetType.class.getEnumConstants()) {
            if (name.equals(object.getName())) {
                return object;
            }
        }
        throw new EnumIllegalException(LikeTargetType.class, name);
    }
}
