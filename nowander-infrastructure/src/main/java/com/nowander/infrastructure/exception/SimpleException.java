package com.nowander.infrastructure.exception;


import com.nowander.infrastructure.enums.ApiInfo;

/**
 * @author wtk
 * @description 自定义 普通异常（HttpStatus返回200）
 * @date 2021-08-12
 */
public class SimpleException extends BaseException {
    public SimpleException(ApiInfo apiInfo) {
        super(apiInfo);
    }
}
