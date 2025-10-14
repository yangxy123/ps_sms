package com.sms.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sms.contants.MsgContants;
import com.sms.dto.MsgDto;
import com.sms.util.RedisUtils;

/** 
* @author yangxy
* @version 创建时间：2025年10月10日 上午9:03:00 
*/
@Configuration
public class MsgCacheConsumer {
	@Autowired
	private RedisUtils redisUtils;
	
	@RabbitListener(queues = "#{delayMsgCacheQueue.name}", concurrency = "10")
	public void delayOrderCancelQueueListener(String message) {
		MsgDto msgDto = JSONObject.toJavaObject(JSON.parseObject(message),MsgDto.class);
		redisUtils.removeList(MsgContants.msgRedisKeySuff, msgDto);
	}
}
