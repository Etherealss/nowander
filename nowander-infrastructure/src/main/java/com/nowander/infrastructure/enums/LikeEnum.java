package com.nowander.infrastructure.enums;

/**
 * @author 寒洲
 * @description 点赞相关的枚举
 * @date 2020/10/20
 */
public class LikeEnum {

	/**
	 * redis主键(key)
	 */
	public static final String KEY_LIKE_RECORD = "likeRecord";

	/**
	 * 点赞数
	 */
	public static final String KEY_LIKE_COUNT = "likeCount";

	/** 已点赞 */
	public static final String HAVE_LIKED = "1";

	/** 未点赞 */
	public static final String HAVE_NOT_LIKED = "0";

}
