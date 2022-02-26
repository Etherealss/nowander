package com.nowander.common.interceptor.pathvariable;

import java.util.UUID;
import java.util.function.Function;

/**
 * @author wang tengkun
 * @date 2022/2/25
 */
public class UUIDMapper implements Function<String, UUID> {
    @Override
    public UUID apply(String s) {
        return UUID.fromString(s);
    }
}
