package com.nowander.blog.controller;

import com.nowander.common.exception.ServiceException;
import com.nowander.common.pojo.po.Article;
import com.nowander.common.pojo.vo.Msg;
import com.nowander.framework.annotation.ResponseAdvice;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wtk
 * @date 2022/2/21
 */
@Slf4j
@RestController
@RequestMapping("/resp")
@AllArgsConstructor
@ResponseAdvice
public class TestRespController {

    @GetMapping("void")
    public void test1() {
        log.debug("{}", "void");
    }

    @GetMapping("article")
    public Article test2() {
        log.debug("{}", "resp");
        Article article = new Article();
        article.setId(111);
        return article;
    }

    @GetMapping("msg")
    public Msg<Void> est3() {
        log.debug("{}", "msg<void>");
        return Msg.ok();
    }

    @GetMapping("exception")
    public Article test4() {
        log.debug("{}", "exception");
        throw new ServiceException("test");
    }

    @GetMapping("exception_msg")
    public Msg<Void> test5() {
        log.debug("{}", "exception_msg");
        throw new ServiceException("test_msg");
    }

    @GetMapping("msg_article")
    public Msg<Article> test6() {
        log.debug("{}", "msg_article");
        Article article = new Article();
        article.setId(111);
        return Msg.ok(article);
    }
}
