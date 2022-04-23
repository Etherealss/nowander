package com.nowander.infrastructure.pojo;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author wtk
 * @date 2022-04-21
 */
public interface BaseEnum {
    /**
     * 用于显示的枚举名
     *
     * @return
     */
    String display();

    /**
     * 按枚举的value获取枚举实例
     *
     * @param enumType
     * @param value
     * @param <T>
     * @return
     */
    static <T extends BaseEnum> T fromValue(Class<T> enumType, int value) {
        for (T object : enumType.getEnumConstants()) {
            if (value == object.value()) {
                return object;
            }
        }
        throw new IllegalArgumentException("No enum value " + value + " of " + enumType.getCanonicalName());
    }

    /**
     * 存储到数据库的枚举值
     *
     * @return
     */
    @JsonValue
    int value();
}
