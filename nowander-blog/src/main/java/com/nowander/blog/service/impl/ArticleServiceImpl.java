package com.nowander.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nowander.blog.mapper.ArticleMapper;
import com.nowander.blog.service.ArticleService;
import com.nowander.common.pojo.po.Article;
import org.springframework.stereotype.Service;

/**
 * @author wtk
 * @date 2022-01-05
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
}
