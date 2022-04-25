package com.nowander.infrastructure.pojo;

import com.nowander.infrastructure.exception.InfrastructureException;
import com.nowander.infrastructure.utils.ReflectUtil;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.ParameterizedType;
import java.util.Objects;

/**
 * @author wtk
 * @date 2022-04-25
 */
public interface InputConverter<DOMAIN> {

    /**
     * 将当前对象转为泛型类
     * @return 对应的泛型类
     */
    @SuppressWarnings("unchecked")
    default DOMAIN toEntity() {
        // 获取泛型
        ParameterizedType currentType = ReflectUtil.getParameterizedType(InputConverter.class, this.getClass());
        Objects.requireNonNull(currentType, "无法获取泛型");

        Class<DOMAIN> domainClass = (Class<DOMAIN>) currentType.getActualTypeArguments()[0];
        try {
            DOMAIN domain = domainClass.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(this, domain);
            return domain;
        } catch (Exception e) {
            throw new InfrastructureException("无法获取" + domainClass.getSimpleName() + "的无参构造器：" + e.getMessage());
        }
    }

    /**
     * 通过传入的参数更新当前对象
     * @param domain 需要更新的数据值
     */
    default void update(DOMAIN domain) {
        BeanUtils.copyProperties(this, domain);
    }
}

