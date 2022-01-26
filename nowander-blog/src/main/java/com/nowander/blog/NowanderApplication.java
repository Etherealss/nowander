package com.nowander.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * @author 寒洲
 */
@EnableGlobalMethodSecurity(securedEnabled = true)
@SpringBootApplication()
public class NowanderApplication {
    public static void main(String[] args) {
        SpringApplication.run(NowanderApplication.class, args);
    }
}
