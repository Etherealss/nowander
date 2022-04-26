package com.nowander.forum.blog.article;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nowander.forum.blog.article.content.ArticleContent;
import com.nowander.forum.blog.article.content.ArticleContentMapper;
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
    public Integer save(ArticleDetailCommand command) {
        Article article = new Article();
        BeanUtils.copyProperties(command, article);
        articleMapper.insert(article);
        ArticleContent articleContent = new ArticleContent();
        articleContent.setArticleId(article.getId());
        articleContent.setContent(command.getContent());
        articleContentMapper.insert(articleContent);
        return article.getId();
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateById(ArticleDetailCommand command) {
        articleMapper.updateById(buildArticle(command));
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateContent(Integer articleId, ArticleDetailCommand command) {
        ArticleContent entity = new ArticleContent();
        entity.setArticleId(articleId);
        entity.setContent(command.getContent());
        articleContentMapper.updateById(entity);
    }

    private Article buildArticle(ArticleDetailCommand vo) {
        Article article = new Article();
        BeanUtils.copyProperties(article, vo);
        return article;
    }
}
