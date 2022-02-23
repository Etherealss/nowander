package com.nowander.common.controller;


import cn.hutool.captcha.AbstractCaptcha;
import cn.hutool.captcha.CaptchaUtil;
import com.nowander.common.enums.AppAttribute;
import com.nowander.common.pojo.vo.Msg;
import com.nowander.common.service.CaptchaService;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class CaptchaController {

    private final CaptchaService captchaService;

    /**
     * 获取验证码 字节流传输
     * @param response
     * @param timestamp 使用时间作为key来判断用户，key由前端传输并保存
     * @throws IOException
     */
    @GetMapping("/byte/{timestamp}")
    public void captcha(HttpServletResponse response, @PathVariable Date timestamp,
                        @RequestParam(value = "useBase64", defaultValue = "false") Boolean useBase64) throws IOException {
        AbstractCaptcha captcha = captchaService.getAndCacheCaptcha(timestamp);
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
    public Msg<String> captcha4Base64(HttpServletResponse response, @PathVariable Date timestamp) {
        return Msg.ok(captchaService.getAndCacheCaptcha(timestamp).getImageBase64());
    }


}
