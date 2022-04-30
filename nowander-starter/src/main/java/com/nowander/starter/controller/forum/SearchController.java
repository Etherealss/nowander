package com.nowander.starter.controller.forum;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nowander.basesystem.user.security.anonymous.annotation.rest.AnonymousGetMapping;
import com.nowander.forum.blog.NoWanderBlogEsEntity;
import com.nowander.forum.blog.NoWanderBlogEsService;
import com.nowander.infrastructure.exception.rest.ErrorParamException;
import com.nowander.infrastructure.web.ResponseAdvice;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author wang tengkun
 * @date 2022/4/26
 */
@Slf4j
@RestController
@RequestMapping("/search")
@AllArgsConstructor
@ResponseAdvice
public class SearchController {
    private final NoWanderBlogEsService noWanderBlogEsService;
    private final int TIPS_SIZE = 10;
    private final int HIGHLIGHT_SIZE = 10;

    @AnonymousGetMapping("/tips/{prefixWord}")
    public List<String> searchTip(@PathVariable String prefixWord) {
        return noWanderBlogEsService.searchTips(prefixWord, TIPS_SIZE);
    }

    @AnonymousGetMapping("/highlight/pages/{curPage}")
    public IPage<NoWanderBlogEsEntity> searchHighlight(@PathVariable Integer curPage, @RequestParam String word) {
        String decode;
        try {
            decode = URLDecoder.decode(word, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            throw new ErrorParamException("文本无法解码为UTF-8格式：" + e.getMessage());
        }
        return noWanderBlogEsService.searchByHighLigh(decode, curPage, HIGHLIGHT_SIZE);
    }
}
