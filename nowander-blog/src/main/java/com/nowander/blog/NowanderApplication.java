package com.nowander.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 寒洲
 */
@MapperScan("com.nowander.**.mapper") // 指定要扫描的Mapper类的包的路径
@ComponentScan("com.nowander") // 扫描其他子模块的组件
@SpringBootApplication()
public class NowanderApplication {
    public static void main(String[] args) {
        SpringApplication.run(NowanderApplication.class, args);
    }
}
