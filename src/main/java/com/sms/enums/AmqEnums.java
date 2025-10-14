package com.sms.enums;

/** 
* @author yangxy
* @version 创建时间：2023年10月24日 下午7:59:57 
*/
public enum AmqEnums {
	
	/**
	 * 消息缓存延迟队列
	 */
	MSG_CACHE_DELAYED("msg_cache_queue","msg_cache_routing","msg_cache_exchange",3)
	;
	
	/**
	 * 队列名称
	 */
	public String queueName;
	
	/**
	 * 路由key
	 */
	public String routeKey;
	
	/**
	 * 交换机名称
	 */
	public String exchangeName;
	
	/**
	 * 队列类型（1 普通队列，2广播队列，3延迟队列,4死信队列）
	 */
	public int type;

	private AmqEnums(String queueName, String routeKey, String exchangeName, int type) {
		this.queueName = queueName;
		this.routeKey = routeKey;
		this.exchangeName = exchangeName;
		this.type = type;
	}

}
