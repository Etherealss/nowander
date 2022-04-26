package com.nowander.forum.blog.article;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nowander.forum.blog.ArticleEsService;
import com.nowander.forum.blog.DocEsDTO;
import com.nowander.forum.blog.NoWanderBlogMapper;
import com.nowander.forum.blog.NoWanderBlogService;
import com.nowander.forum.blog.article.content.ArticleContent;
import com.nowander.forum.blog.article.content.ArticleContentService;
import com.nowander.infrastructure.exception.service.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wtk
 * @date 2022-01-05
 */
@Slf4j
@Service
public class ArticleService extends NoWanderBlogService<Article> {

    private final ArticleContentService articleContentService;
    private final ArticleMapper articleMapper;
    private final ArticleEsService articleEsService;

    public ArticleService(NoWanderBlogMapper<Article> noWanderBlogMapper,
                          ArticleContentService articleContentService,
                          ArticleMapper articleMapper,
                          ArticleEsService articleEsService) {
        super(noWanderBlogMapper);
        this.articleContentService = articleContentService;
        this.articleMapper = articleMapper;
        this.articleEsService = articleEsService;
    }

    public ArticleContent getContentById(Integer id) {
        return articleContentService.getById(id);
    }

    public ArticleDetailDTO getDetailById(Integer id) {
        Article article = articleMapper.selectById(id);
        if (article == null) {
            throw new NotFoundException(Article.class, id.toString());
        }
        ArticleContent articleContent = articleContentService.getById(id);
        return ArticleDetailDTO.build(article, articleContent);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer save(ArticleDetailCommand command) {
        Article article = new Article();
        BeanUtils.copyProperties(command, article);
        articleMapper.insert(article);
        articleContentService.save(article.getId(), command);
        // articleEsManage.save(buildDocEsDTO(entity));
        return article.getId();
    }

    public void update(Integer articleId, ArticleDetailCommand command) {
        if (!StrUtil.isBlank(command.getContent())) {
            articleContentService.updateContent(articleId, command);
        }
        Article article = command.toEntity();
        articleMapper.updateById(article);
    }

    public IPage<Article> searchByHighLigh(String word, int curPage, int size) {
        return articleEsService.searchByHighLigh(word, curPage, size);
    }

    public List<String> searchTips(String prefixWord, String indexName, int size) {
        return articleEsService.searchTips(prefixWord, indexName, size);
    }

    private DocEsDTO buildDocEsDTO(ArticleDetailCommand detail) {
        DocEsDTO docEsDTO = new DocEsDTO();
        BeanUtils.copyProperties(docEsDTO, detail);
        return docEsDTO;
    }

    /**
     * 分页，包含内容
     */
    public IPage<ArticleDetailDTO> pageDetails(int curPage, int size, String orderBy) {
        IPage<Article> page = super.page(curPage, size, orderBy);
        List<ArticleDetailDTO> articleDetails = page.getRecords().stream().map(article -> {
            ArticleContent articleContent = articleContentService.getById(article.getId());
            return ArticleDetailDTO.build(article, articleContent);
        }).collect(Collectors.toList());
        Page<ArticleDetailDTO> detailPage = new Page<>(page.getCurrent(), page.getSize(), page.getTotal(), page.isSearchCount());
        detailPage.setRecords(articleDetails);
        return detailPage;
    }
}
