package com.nowander.basesystem.captcha;

import cn.hutool.captcha.AbstractCaptcha;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.core.util.StrUtil;
import com.nowander.infrastructure.enums.ApiInfo;
import com.nowander.infrastructure.exception.internal.BugException;
import com.nowander.infrastructure.exception.service.CaptchaException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;
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
    private final Integer captchaTimeoutSecond;

    public CaptchaService(RedisTemplate<String, String> redis,
                          @Value("${app.captcha.captcha-cache-key}") String captchacCacheKey,
                          @Value("${app.captcha.timeout-second}") Integer captchaTimeoutSecond) {
        this.redis = redis;
        this.captchacCacheKey = captchacCacheKey;
        this.captchaTimeoutSecond = captchaTimeoutSecond;
        Objects.requireNonNull(captchacCacheKey);
        Objects.requireNonNull(captchaTimeoutSecond);
        if (captchaTimeoutSecond <= 0) {
            throw new BugException("验证码失效时间必须大于0");
        }
    }

    /**
     * 获取并缓存
     * @return
     */
    public AbstractCaptcha getAndCacheCaptcha(String captchaId) {
        AbstractCaptcha captcha = CaptchaUtil.createLineCaptcha(200, 100);
        setCaptchaCache(captcha, captchaId);
        return captcha;
    }

    /**
     * 缓存到 Redis 中，5分钟超时
     * @param captcha
     * @param captchaId
     */
    private void setCaptchaCache(AbstractCaptcha captcha, String captchaId) {
        String code = captcha.getCode();
        log.debug("缓存验证码，id：{}，code：{}", captchacCacheKey + captchaId, code);
        redis.opsForValue().set(captchacCacheKey + captchaId,
                code, captchaTimeoutSecond, TimeUnit.SECONDS);
    }

    public void validateCaptcha(String userInputCaptcha, String captchaId) {
        String code = redis.opsForValue().getAndDelete(captchacCacheKey + captchaId);

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
