package com.nowander.common.enums;

/**
 * @author wtk
 * @date 2022-01-26
 */
public class RedisKeyPrefix {
    /**
     * 新点赞记录
     */
    public static final String RECENT_LIKE_RECORD = "recentLikeRecord";
    /**
     * 已有点赞记录
     */
    public static final String LIKE_RECORD = "likeRecord";
    /**
     * 新增点赞数
     */
    public static final String RECENT_LIKE_COUNT = "recentLikeCount";

    public static final String LIKE_COUNT = "likeCount";
    /**
     * 点赞数增量
     */
    public static final String LIKE_COUNT_INC = "likeCountInr";

    public static final String USER_TOKEN_BLACKLIST = "token_blacklist_";

    public static final String WACTH_LOCK = "LOCK";
    public static final String LOCK_LIKE = "LOCK_like";


}
