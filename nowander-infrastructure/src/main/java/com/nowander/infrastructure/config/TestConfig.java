package com.nowander.infrastructure.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author wtk
 * @date 2022-05-02
 */
@AllArgsConstructor
@Configuration
@Slf4j
public class TestConfig {
    @Autowired
    StringEncryptor stringEncryptor;

    @PostConstruct
    public void encryptorTest() {
        /*你的密码*/
        String passwd = stringEncryptor.encrypt("baotaredis123456");
        log.info("加密密码是：" + passwd);
        log.info("解密密码是："+stringEncryptor.decrypt(passwd));
    }
}
