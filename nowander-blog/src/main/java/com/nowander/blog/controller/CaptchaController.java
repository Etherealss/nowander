package com.nowander.blog.controller;


import cn.hutool.captcha.AbstractCaptcha;
import cn.hutool.captcha.CaptchaUtil;
import com.nowander.common.enums.AppAttribute;
import com.nowander.common.pojo.vo.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author wtk
 * @description
 * @date 2021-10-05
 */
@RestController("/captchas")
public class CaptchaController {

    @Autowired
    private RedisTemplate<String, Object> redis;

    /**
     * 获取验证码 字节流传输
     * @param response
     * @param timestamp 使用时间作为key来判断用户
     * @throws IOException
     */
    @GetMapping("/byte/{timestamp}")
    public void captcha(HttpServletResponse response, @PathVariable Date timestamp,
                        @RequestParam(value = "useBase64", defaultValue = "false") Boolean useBase64) throws IOException {
        AbstractCaptcha captcha = getAndCacheCaptcha(timestamp);
        // 关闭浏览器的缓存
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        captcha.write(response.getOutputStream());
    }

    /**
     * 获取验证码 返回Base64
     * @param response
     * @param timestamp
     * @return Base64
     * @throws IOException
     */
    @GetMapping("/string/{timestamp}")
    public Msg<String> captcha4Base64(HttpServletResponse response, @PathVariable Date timestamp) throws IOException {
        return Msg.ok(getAndCacheCaptcha(timestamp).getImageBase64());
    }

    /**
     * 获取并缓存
     * @param timestamp
     * @return
     */
    private AbstractCaptcha getAndCacheCaptcha(Date timestamp) {
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
