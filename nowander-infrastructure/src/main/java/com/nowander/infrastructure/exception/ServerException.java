package com.nowander.infrastructure.exception;

import com.nowander.infrastructure.enums.ApiInfo;

/**
 * 服务端后台运行异常，如 反射异常
 * @author wang tengkun
 * @date 2022/2/23
 */
public class ServerException  extends BaseException {
    public ServerException(String message) {
        super(ApiInfo.SERVER_ERROR, message);
    }

    public ServerException(String message, Exception e) {
        super(ApiInfo.SERVER_ERROR, message, e);
    }
}
