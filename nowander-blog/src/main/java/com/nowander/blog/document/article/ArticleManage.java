package com.nowander.blog.document.article;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wang tengkun
 * @date 2022/3/8
 */
@Service
@Slf4j
@AllArgsConstructor
class ArticleManage extends ServiceImpl<ArticleMapper, Article> {
    private ArticleMapper articleMapper;
    private ArticleContentMapper articleContentMapper;

    public ArticleContent findContentById(Integer id) {
        return articleContentMapper.selectById(id);
    }

    public ArticleDetailDTO findDetailById(Integer id) {
        Article article = articleMapper.selectById(id);
        ArticleContent articleContent = articleContentMapper.selectById(id);
        ArticleDetailDTO dto = new ArticleDetailDTO();
        BeanUtils.copyProperties(dto, article);
        BeanUtils.copyProperties(dto, articleContent);
        return dto;
    }

    @Transactional(rollbackFor = Exception.class)
    public void save(ArticleDetailVO vo) {
        Article article = new Article();
        BeanUtils.copyProperties(article, vo);
        articleMapper.insert(article);
        articleContentMapper.insert(buildArticleContent(vo));
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateById(ArticleDetailVO vo) {
        articleMapper.updateById(buildArticle(vo));
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateContent(ArticleDetailVO vo) {
        articleContentMapper.updateById(buildArticleContent(vo));
    }

    private Article buildArticle(ArticleDetailVO vo) {
        Article article = new Article();
        BeanUtils.copyProperties(article, vo);
        return article;
    }

    private ArticleContent buildArticleContent(ArticleDetailVO vo) {
        ArticleContent articleContent = new ArticleContent();
        BeanUtils.copyProperties(articleContent, vo);
        return articleContent;
    }
}
