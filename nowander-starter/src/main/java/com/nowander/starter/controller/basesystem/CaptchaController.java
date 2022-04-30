package com.nowander.starter.controller.basesystem;


import cn.hutool.captcha.AbstractCaptcha;
import com.nowander.basesystem.user.security.anonymous.annotation.rest.AnonymousGetMapping;
import com.nowander.infrastructure.pojo.Msg;
import com.nowander.basesystem.user.security.IgnoreLogin;
import com.nowander.basesystem.captcha.CaptchaService;
import com.nowander.infrastructure.utils.UUIDUtil;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wtk
 * @description
 * @date 2021-10-05
 */
@RestController
@RequestMapping("/captchas")
@AllArgsConstructor
public class CaptchaController {

    private final CaptchaService captchaService;

    /**
     * 获取验证码 字节流传输
     * @param response
     * @param uuid 使用全局唯一的值作为key来判断用户
     * @throws IOException
     */
    @AnonymousGetMapping("/byte/{uuid}")
    public void captcha(HttpServletResponse response, @PathVariable String uuid,
                        @RequestParam(value = "useBase64", defaultValue = "false") Boolean useBase64) throws IOException {
        AbstractCaptcha captcha = captchaService.getAndCacheCaptcha(uuid);
        // 关闭浏览器的缓存
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        captcha.write(response.getOutputStream());
    }

    /**
     * 获取验证码 返回Base64
     * @param response
     * @return Base64
     * @throws IOException
     */
    @AnonymousGetMapping("/string")
    public Msg<Map<String, String>> captcha4Base64(HttpServletResponse response) {
        String uuid = UUIDUtil.getUuid();
        String imageBase64 = captchaService.getAndCacheCaptcha(uuid).getImageBase64();
        Map<String, String> data = new HashMap<>(4);
        data.put("iamge", imageBase64);
        data.put("key", uuid);
        return Msg.ok(data);
    }
}
