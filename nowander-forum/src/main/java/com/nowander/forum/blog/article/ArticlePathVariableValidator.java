package com.nowander.forum.blog.article;

import com.nowander.infrastructure.interceptor.pathvariable.PathVariableValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 检验articleId是否存在
 * @author wtk
 * @date 2022-04-26
 */
@Component
@AllArgsConstructor
public class ArticlePathVariableValidator implements PathVariableValidator {
    private final ArticleService articleService;
    @Override
    public boolean validate(String... args) {
        Objects.requireNonNull(args[0]);
        return articleService.getById(Integer.valueOf(args[0])) != null;
    }

    @Override
    public String validateTarget() {
        return "articleId";
    }
}
