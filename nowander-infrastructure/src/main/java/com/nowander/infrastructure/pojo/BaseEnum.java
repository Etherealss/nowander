package com.nowander.infrastructure.pojo;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;

/**
 * @author wtk
 * @date 2022-04-21
 */
public interface BaseEnum {
    /**
     * 用于显示的枚举名
     * @return
     */
    String getName();

    /**
     * 存储到数据库的枚举值
     * @return
     */
    @JsonValue
    int getCode();

    /**
     * 按枚举的code获取枚举实例
     */
    static <T extends BaseEnum> T fromCode(Class<T> enumType, int code) {
        for (T object : enumType.getEnumConstants()) {
            if (code == object.getCode()) {
                return object;
            }
        }
        throw new IllegalArgumentException("No enum code '" + code + "' of '" + enumType.getCanonicalName() + "'");
    }

    /**
     * 按枚举的name获取枚举实例
     */
    static <T extends BaseEnum> T fromName(Class<T> enumType, String name) {
        for (T object : enumType.getEnumConstants()) {
            if (name.equals(object.getName())) {
                return object;
            }
        }
        throw new IllegalArgumentException("No enum name '" + name + "' of '" + enumType.getCanonicalName() + "'");
    }
}
