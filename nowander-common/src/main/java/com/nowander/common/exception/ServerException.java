package com.nowander.common.exception;

import com.nowander.common.enums.ApiInfo;

/**
 * 服务端后台运行异常，如 反射异常
 * @author wang tengkun
 * @date 2022/2/23
 */
public class ServerException  extends BaseException {
    public ServerException() {
        super(ApiInfo.SERVER_ERROR);
    }

    public ServerException(String message) {
        super(ApiInfo.SERVER_ERROR, message);
    }

    public ServerException(String message, Exception e) {
        super(ApiInfo.SERVER_ERROR, message, e);
    }
}
