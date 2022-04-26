package com.nowander.forum.blog.article;

import com.nowander.infrastructure.enums.Module;
import com.nowander.infrastructure.pojo.InputConverter;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author wang tengkun
 * @date 2022/3/3
 */
@Data
public class ArticleDetailCommand implements InputConverter<ArticleEntity> {

    /**
     * 分类
     */
    @NotNull
    private Integer category;

    /**
     * 分区
     */
    @NotNull
    private Module module;

    /**
     * 标题
     */
    @NotBlank
    @Length(min = 5, max = 30)
    private String title;

    /**
     * 文章内容
     */
    @NotNull
    @Length(min = 10)
    private String content;

    /**
     * 标签
     */
    private List<String> labels;

}
