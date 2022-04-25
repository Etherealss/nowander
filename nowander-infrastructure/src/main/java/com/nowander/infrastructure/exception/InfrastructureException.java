package com.nowander.infrastructure.exception;

import com.nowander.infrastructure.enums.ApiInfo;

/**
 * 基础架构层异常，一般是有bug
 * @author wtk
 * @date 2022-04-25
 */
public class InfrastructureException extends BaseException {
    public InfrastructureException(String message) {
        super(ApiInfo.SERVER_ERROR, message);
    }
}
