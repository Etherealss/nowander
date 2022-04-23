package com.nowander.basesystem.captcha;

import cn.hutool.captcha.AbstractCaptcha;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.core.util.StrUtil;
import com.nowander.infrastructure.enums.ApiInfo;
import com.nowander.infrastructure.exception.CaptchaException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author wang tengkun
 * @date 2022/2/23
 */
@Service
@Slf4j
public class CaptchaService {

    private final RedisTemplate<String, String> redis;
    private final String captchacCacheKey;

    public CaptchaService(RedisTemplate<String, String> redis,
                          @Value("app.captcha.captcha-cache-key") String captchacCacheKey) {
        this.redis = redis;
        this.captchacCacheKey = captchacCacheKey;
    }

    /**
     * 获取并缓存
     * @param timestamp
     * @return
     */
    public AbstractCaptcha getAndCacheCaptcha(Date timestamp) {
        AbstractCaptcha captcha = CaptchaUtil.createLineCaptcha(200, 100);
        setCaptchaCache(captcha, timestamp);
        return captcha;
    }

    /**
     * 缓存到 Redis 中，5分钟超时
     * @param captcha
     * @param timestamp
     */
    private void setCaptchaCache(AbstractCaptcha captcha, Date timestamp) {
        redis.opsForValue().set(captchacCacheKey + timestamp.getTime(),
                captcha.getCode(), 5, TimeUnit.MINUTES);
    }

    public void validateCaptcha(String userInputCaptcha, Date timestamp) {
        String code = redis.opsForValue().getAndDelete(captchacCacheKey + timestamp);

        //验证码是否为空
        if (StrUtil.isBlank(userInputCaptcha)) {
            throw new CaptchaException(ApiInfo.CAPTCHA_MISSING);
        }

        // TODO 用于测试
        if ("1234".equals(userInputCaptcha)) {
            return;
        }

        // 验证码失效
        if (code == null) {
            throw new CaptchaException(ApiInfo.CAPTCHA_INVALID);
        }

        // 验证码不匹配
        if (!code.equals(userInputCaptcha)) {
            throw new CaptchaException(ApiInfo.CAPTCHA_NOT_MATCH);
        }
    }

}
