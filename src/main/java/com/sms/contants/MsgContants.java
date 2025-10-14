package com.sms.contants;

import java.util.List;

import com.google.common.collect.Lists;

/** 
* @author yangxy
* @version 创建时间：2025年9月29日 下午2:56:00 
*/
public interface MsgContants {
	/**
	 * 消息类型
	 */
	public static List<Integer> typeList = Lists.newArrayList(1,2,3,4,5);
	
	/**
	 * 消息缓存前缀
	 */
	public static String msgRedisKeySuff = "msg:cahe";
}
