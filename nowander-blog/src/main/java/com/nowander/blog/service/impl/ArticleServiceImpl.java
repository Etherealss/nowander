package com.nowander.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nowander.blog.properties.EsSearchProperties;
import com.nowander.blog.mapper.ArticleEsDao;
import com.nowander.blog.mapper.ArticleMapper;
import com.nowander.blog.service.ArticleService;
import com.nowander.blog.pojo.po.Article;
import com.nowander.common.pojo.vo.Msg;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wtk
 * @date 2022-01-05
 */
@Slf4j
@Service
@AllArgsConstructor
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    private ArticleMapper articleMapper;
    private ArticleEsDao articleEsDao;
    private EsSearchProperties properties;

    private ElasticsearchRestTemplate es;

    @Override
    public IPage<Article> page(int curPage, int size, String orderBy) {
        IPage<Article> page;
        Page<Article> p = new Page<>(curPage, size);
        switch (orderBy) {
            case "time":
                page = articleMapper.selectPage(p,
                        new QueryWrapper<Article>().orderByDesc("create_time"));
                break;
            case "like":
                page = articleMapper.pageByLike(p);
                break;
            default:
                page = this.page(p);
        }
        return page;
    }

    @Override
    public List<String> searchTips(String prefixWord, String indexName, int size) {
        CompletionSuggestionBuilder suggestionBuilder = SuggestBuilders
                .completionSuggestion("title")
                .prefix(prefixWord)
                .size(size);
        SuggestBuilder suggestion = new SuggestBuilder()
                .addSuggestion("title_completion_suggestion", suggestionBuilder);
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withSuggestBuilder(suggestion).build();
        SearchHits<Article> searchHits = es.search(query, Article.class);
        List<String> list = searchHits.stream()
                .map(searchHit -> searchHit.getContent().getTitle())
                .collect(Collectors.toList());
        return list;
    }

    /**
     * ES高亮查询参数：https://blog.csdn.net/numbbe/article/details/109826032
     * SpringDataES高亮查询：https://blog.csdn.net/cpongo5/article/details/96153415
     * @param word
     * @param curPage
     * @param size 记录数
     * @return
     */
    @Override
    public IPage<Article> searchByHighLigh(String word, int curPage, int size) {
        // fragmentSize 高亮字段内容长度，去除html样式标签，统计字数
        // numOfFragment 高亮关键字数量，优先显示，当numOfFragment设置为0时，fragmentSize失效，会返回全部内容。默认为5。
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        // 循环设置field
        properties.getArticleFileds().keySet().forEach(highlightBuilder::field);
        // 设置高亮文本的前缀、后缀
        highlightBuilder.preTags(properties.getArticlePreTags())
                .postTags(properties.getArticlePostTags());

        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withHighlightBuilder(highlightBuilder)
                .withQuery(new MultiMatchQueryBuilder(word)
                        .fields(properties.getArticleFileds()))
                // 分页
                .withPageable(PageRequest.of(curPage, size))
                .build();
        SearchHits<Article> hits = es.search(query, Article.class);
        // 获取Article实体
        List<Article> articles = hits.stream()
                .map(SearchHit::getContent).collect(Collectors.toList());

        // 将返回的结果包装在Page对象中，方便前端使用
        IPage<Article> page = new Page<>(curPage, size);
        page.setRecords(articles);
        long totalHits = hits.getTotalHits();
        page.setTotal(totalHits);
        // 通过总记录数和每页显示数 计算总页数
        long totalPages = totalHits / size;
        totalPages = totalHits % size == 0 ? totalPages : totalPages + 1;
        page.setPages(totalPages);
        return page;
    }
}
