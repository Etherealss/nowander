package com.nowander.infrastructure.exception.service;


import com.nowander.infrastructure.enums.ApiInfo;
import com.nowander.infrastructure.exception.BaseException;

/**
 * @author wtk
 * @description
 * @date 2021-08-12
 */
public class ExistException extends BaseException {
    public ExistException(Class<?> clazz, String message) {
        super(ApiInfo.EXIST, message);
    }

    public ExistException(Class<?> clazz) {
        super(ApiInfo.EXIST, "对应的" + clazz.getSimpleName() + "已存在");
    }
}
