package com.nowander.common.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author wtk
 * @since 2022-01-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("like_record")
public class LikeRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 点赞用户id
     */
      private Integer userId;

    /**
     * 点赞的文章的id
     */
    private Integer targetId;

    /**
     * 文章为0，问贴为1，评论为2
     */
    private Integer type;


}
