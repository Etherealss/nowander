package com.nowander.like.cache;

import com.nowander.common.enums.RedisKey;
import com.nowander.common.pojo.po.LikeCount;
import com.nowander.common.pojo.po.LikeRecord;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author wtk
 * @date 2022-01-27
 */
@Component
public class LikeCountCache {
    private HashOperations<String, String, Long> hash;

    @Autowired
    public LikeCountCache(RedisTemplate<String, Long> redis) {
        hash = redis.opsForHash();
    }

    private String key(LikeRecord likeRecord) {
        return likeRecord.getLikeCountKey();
    }

    /**
     * 缓存某个目标的当前点赞数
     */
    public void likeOrUnlike(LikeRecord likeRecord, Boolean isLike) {
        hash.increment(RedisKey.LIKE_COUNT, key(likeRecord), (isLike ? 1L : -1L));
    }

    public int getLikeCountCache(LikeRecord likeRecord) {
        Object value = hash.get(RedisKey.LIKE_COUNT, key(likeRecord));
        if (value == null) {
            return 0;
        }
        return Integer.parseInt(value.toString());
    }

    public List<LikeCount> getAndDelAllLikeCount() {
        Set<String> keys = hash.keys(RedisKey.LIKE_COUNT);
        List<LikeCount> res = new ArrayList<>(keys.size());
        for (String key : keys) {
            Long count = hash.get(RedisKey.LIKE_COUNT, key);
            // 拿了立马删，因为可能有数据丢失的风险
            hash.delete(RedisKey.LIKE_COUNT, keys);
            if (count != null) {
                LikeCount likeCount = new LikeCount(key, count.intValue());
                res.add(likeCount);
            }
        }
        return res;
    }
}