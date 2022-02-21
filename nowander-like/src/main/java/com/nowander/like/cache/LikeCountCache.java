package com.nowander.like.cache;

import com.nowander.common.enums.RedisKey;
import com.nowander.like.pojo.po.LikeCount;
import com.nowander.like.pojo.po.LikeRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author wtk
 * @date 2022-01-27
 */
@Component
public class LikeCountCache {
    private HashOperations<String, String, Integer> hash;

    @Autowired
    public LikeCountCache(RedisTemplate<String, Long> redis) {
        hash = redis.opsForHash();
    }


    /**
     * 增加某个目标的点赞数
     */
    public void increRecentLike(LikeRecord likeRecord, Boolean isLike) {
        hash.increment(RedisKey.RECENT_LIKE_COUNT, likeRecord.getLikeCountKey(), (isLike ? 1L : -1L));
    }

    /**
     * 缓存某个目标的点赞数
     */
    public void setLikeCount(LikeCount likeCount) {
        hash.put(RedisKey.LIKE_COUNT, likeCount.getLikeCountKey(), likeCount.getCount());
    }

    /**
     * 获取某个目标的点赞数缓存
     */
    public Integer getLikeCount(LikeCount likeCount) {
        return hash.get(RedisKey.LIKE_COUNT, likeCount.getLikeCountKey());
    }

    /**
     * 获取某个目标的新增点赞数缓存
     */
    public Integer getRecentLikeCount(LikeCount likeCount) {
        return hash.get(RedisKey.RECENT_LIKE_COUNT, likeCount.getLikeCountKey());
    }

    public Set<String> getAllKeys() {
        return hash.keys(RedisKey.LIKE_COUNT);
    }

    /**
     * 获取并删除
     * @param key
     * @return 如果key获取不到count，则返回null
     */
    public LikeCount getAndDelRecentLikeCount(String key) {
        Integer count = hash.get(RedisKey.RECENT_LIKE_COUNT, key);
        if (count != null) {
            // 拿了立马删，因为可能有数据丢失的风险
            hash.delete(RedisKey.RECENT_LIKE_COUNT, key);
            return new LikeCount(key, count);
        }
        return null;
    }
}