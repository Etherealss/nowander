package com.nowander.common.pojo;

import com.nowander.common.utils.MyBeanUtil;
import com.nowander.common.utils.ReflectUtil;

import java.lang.reflect.ParameterizedType;
import java.util.Objects;

/**
 * @author wtk
 * @date 2022/2/23
 */
public interface CommandConverter<D> {

    /**
     * 将接口实现类转换为接口泛型类
     * @return
     */
    @SuppressWarnings("unchecked")
    default D convertTo() {
        ParameterizedType currentType = ReflectUtil.getParameterizedType(CommandConverter.class, this.getClass());
        Objects.requireNonNull(currentType, "无法获取参数化类型");

        Class<D> domainClass = (Class<D>) currentType.getActualTypeArguments()[0];

        return MyBeanUtil.transformFrom(this, domainClass);
    }

    /**
     * 使用当前接口实现类的属性值更新入参
     * @param domain 要更新的对象
     */
    default void update(D domain) {
        MyBeanUtil.copyProperties(this, domain);
    }
}
