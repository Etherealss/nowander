package com.nowander.chat.enums;

/**
 * @author 寒洲
 * @description
 * @date 2020/11/18
 */
public enum InfoType {
	/** 新用户进入 */
	NEW_USER_ENTER,

	/** 请求发送文本 */
	SEND_TXT,
	/** 请求发送文件 */
	SEND_FILE,
	/** 请求发送图片 */
	SEND_IMAGE,

	/** 发送消息成功 */
	SEND_SUCCESS,
	/** 发送消息失败 */
	SNED_FAIL,

	/** 同意接收文件 */
	RECEIVE_FILE_ACCEPT,
	/** 拒绝接收文件 */
	RECEIVE_FILE_REFUSE,
	/** 文件发送完毕 */
	SEND_FILE_SUCCESS,

	/** 可以发送图片 */
	RECEIVE_IMAGE_OK,
	/** 成功发送图片 */
	SEND_IMAGE_SUCCESS,

	/** 请求获取好友列表 */
	FRIEND_DATA,
	/** 请求添加好友 */
	FRIEND_ADD,
	/** 成功添加好友 */
	FRIEND_ADD_OK,
	/** 请求删除好友 */
	FRIEND_REMOVE,

	/** 服务器提醒刷新好友列表 */
	REFRESH,
	/** 服务器关闭，强制下线 */
	CLOSE,

	;
}
