package com.nowander.common.enums;

/**
 * @author wtk
 * @date 2022-01-26
 */
public class RedisKey {
    /**
     * 新点赞记录
     */
    public static final String RECENT_LIKE_RECORD = "recentLikeRecord";
    /**
     * 已有点赞记录
     */
    public static final String LIKE_RECORD = "likeRecord";

    public static final String LIKE_COUNT = "likeCount";
    /**
     * 点赞数增量
     */
    public static final String LIKE_COUNT_INC = "likeCountInr";



    public static final String WACTH_LOCK = "LOCK";


}
