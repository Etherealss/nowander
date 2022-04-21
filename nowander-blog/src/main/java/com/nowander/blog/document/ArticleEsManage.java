package com.nowander.blog.document;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nowander.blog.document.article.Article;
import com.nowander.blog.document.DocEsDTO;
import com.nowander.blog.document.EsSearchProperties;
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
 * @author wang tengkun
 * @date 2022/3/8
 */
@Service
@Slf4j
public class ArticleEsManage {
    private EsSearchProperties properties;
    private ElasticsearchRestTemplate es;

    public void save(DocEsDTO doc) {
        es.save(doc);
    }

    /**
     * 搜索补全
     * @param prefixWord
     * @param indexName ES索引名
     * @param size 数量
     * @return
     */
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
     * 高亮、多字段 搜索
     * ES高亮查询参数：https://blog.csdn.net/numbbe/article/details/109826032
     * SpringDataES高亮查询：https://blog.csdn.net/cpongo5/article/details/96153415
     * @param word
     * @param curPage 起始记录
     * @param size 记录数
     * @return
     */
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
