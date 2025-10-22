package com.sms.enums;

/**
 * redis缓存key枚举
 * @author yangxy
 * @version 创建时间：2023年10月26日 下午5:12:00
 */
public enum RedisKeyEnums {
	/**
	 * 机器人关键词缓存
	 */
	ADMIN_ROBOT("admin:robot",1),
	/**
	 * 前台管理员缓存前缀
	 */
	CLIENT_MANAGER("client:manager:",0),
	/**
	 * 禁言用户存储前缀
	 */
	MUTE("client:mute:",0),
	
	/**
	 * 手机验证码存储前缀
	 */
	CAPTCHA("client:captcha:",0),
	
	/**
	 * 后台登录token存储前缀
	 */
	ADMIN_LOGIN_TOKEN("admin:login:",0),
	
	/**
	 * 前台登录token存储前缀
	 */
	CLIENT_LOGIN_TOKEN("client:login:",0);
	/**
	 * redisKey
	 */
	public String key;
	/**
	 * 数据类型类型（0 string;1 list;2 zset;3 set;4 hash）
	 */
	public Integer dateType;

	private RedisKeyEnums(String key, Integer dateType) {
		this.key = key;
		this.dateType = dateType;
	}
}
