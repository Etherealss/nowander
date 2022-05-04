package com.nowander.starter;


import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.LinkedList;
import java.util.List;

/**
 * @author 寒洲
 */
// 指定要扫描的Mapper类的包的路径，需要加上Mapper注解，否则会把访问到的接口都当成Mapper类
@MapperScan(value = "com.nowander", annotationClass = Mapper.class)
@ComponentScan("com.nowander") // 扫描其他子模块的组件
@SpringBootApplication()
public class NowanderApplication {
    public static void main(String[] args) {
        SpringApplication.run(NowanderApplication.class, args);
    }
}
