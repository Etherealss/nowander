package com.nowander.infrastructure.web;

import com.nowander.infrastructure.enums.BaseEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

/**
 * 将Controller中的将参数转为对应的BaseEnum
 * @author wtk
 * @date 2022-04-24
 */
public class IntegerEnumConverter implements ConverterFactory<Integer, BaseEnum> {
    @Override
    public <T extends BaseEnum> Converter<Integer, T> getConverter(Class<T> targetType) {
        return new StringToEnumConverter<>(targetType);
    }

    public static class StringToEnumConverter<T extends BaseEnum> implements Converter<Integer, T> {
        private final Class<T> targetType;

        public StringToEnumConverter(Class<T> targetType) {
            this.targetType = targetType;
        }

        @Override
        public T convert(Integer source) {
            return BaseEnum.fromCode(targetType, source);
        }
    }
}
