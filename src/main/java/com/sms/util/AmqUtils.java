package com.sms.util;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

/** 
* @author yangxy
* @version 创建时间：2023年9月4日 上午11:06:09 
*/
@Component
public class AmqUtils {
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	/**
	* 发送消息（广播模式）
	 @param exchange 交换机名称
	* @param msg 消息内容
	* @author yangxy
	* @version 创建时间：2023年9月4日 上午11:14:58 
	 */
	public void sendMqMsg(String exchange, Object msg) {
		String msgStr = JSON.toJSONString(msg);
		rabbitTemplate.convertAndSend(exchange, "", msgStr);
	}

	/**
	 * 发送消息（点对点）
	* @param exchange 交换机名称
	* @param routingKey 路由key
	* @param msg 消息内容
	* @author yangxy
	* @version 创建时间：2023年9月4日 上午11:15:19 
	 */
	public void sendMqMsg(String exchange, String routingKey, Object msg) {
		// TODO Auto-generated method stub
		if(msg.getClass().equals(String.class)) {
			rabbitTemplate.convertAndSend(exchange, routingKey, msg);
			return;
		}
		String msgStr = JSON.toJSONString(msg);//将对象转换为字符串
		rabbitTemplate.convertAndSend(exchange, routingKey, msgStr);
	}

	
	/**
	 * 发送消息到延迟队列
	 * 
	 * @param exchange 交换机
	 * @param routingKey 路由key
	 * @param msg  消息体
	 * @param time 延迟时间（秒）
	 */
	public void sendDelayedMsg(String exchange,String routingKey,Object msg, int time) {
		rabbitTemplate.convertAndSend(exchange, routingKey, msg, message -> {
			// 设置延迟时间
			message.getMessageProperties().setDelay(time * 1000);
			return message;
		});
	}

}
