package com.nowander.common.service;

import cn.hutool.captcha.AbstractCaptcha;
import cn.hutool.captcha.CaptchaUtil;
import com.nowander.common.enums.AppAttribute;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author wang tengkun
 * @date 2022/2/23
 */
@Service
@AllArgsConstructor
@Slf4j
public class CaptchaService {

    @Autowired
    private RedisTemplate<String, Object> redis;

    /**
     * 获取并缓存
     * @param timestamp
     * @return
     */
    public AbstractCaptcha getAndCacheCaptcha(Date timestamp) {
        AbstractCaptcha captcha = CaptchaUtil.createLineCaptcha(200, 100);
        captchaCache(captcha, timestamp);
        return captcha;
    }

    /**
     * 缓存到 Redis 中，5分钟超时
     * @param captcha
     * @param timestamp
     */
    private void captchaCache(AbstractCaptcha captcha, Date timestamp) {
        redis.opsForValue().set(AppAttribute.CAPTCHAC_CACHE + timestamp.getTime(),
                captcha.getCode(), 5, TimeUnit.MINUTES);
    }

}
