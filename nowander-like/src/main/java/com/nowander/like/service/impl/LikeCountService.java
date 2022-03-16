package com.nowander.like.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nowander.like.pojo.po.LikeCount;
import com.nowander.like.mapper.LikeCountMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wtk
 * @since 2022-01-28
 */
@Service
public class LikeCountService extends ServiceImpl<LikeCountMapper, LikeCount> implements com.nowander.like.service.LikeCountService {

}
