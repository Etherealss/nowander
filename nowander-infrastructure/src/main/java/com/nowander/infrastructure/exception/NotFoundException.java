package com.nowander.infrastructure.exception;


import com.nowander.infrastructure.enums.ApiInfo;

/**
 * @author wtk
 * @description
 * @date 2021-08-12
 */
public class NotFoundException extends BaseException {

    public NotFoundException(Class<?> clazz, String identification) {
        super(ApiInfo.NOT_FOUND, identification + "对应的" + clazz.getSimpleName() + "不存在");
    }
}
