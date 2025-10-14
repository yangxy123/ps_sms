package com.sms.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sms.enums.AmqEnums;

/** 
* @author yangxy
* @version 创建时间：2025年10月10日 上午8:55:13 
*/
@Configuration
public class MsgCacheAmqConfig {
	/**
	 * 创建订单取消延迟队列交换机
	* @author yangxy
	* @version 创建时间：2025年1月6日 上午9:40:23 
	* @return
	 */
	@Bean
	public CustomExchange delayMsgCacheExchange() {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("x-delayed-type", "direct");
		return new CustomExchange(AmqEnums.MSG_CACHE_DELAYED.exchangeName, "x-delayed-message", true, false, args);
	}
	
	/**
     * 创建订单取消延迟队列
     * @return
     */
    @Bean
    public Queue delayMsgCacheQueue() {
        return new Queue(AmqEnums.MSG_CACHE_DELAYED.queueName, true);
    }

    /**
     * 绑定订单取消延迟队列
    * @author yangxy
    * @version 创建时间：2023年9月4日 上午10:59:52 
    * @return
     */
    @Bean
    public Binding deplyMsgCacheBinding() {
        return BindingBuilder.bind(delayMsgCacheQueue()).to(delayMsgCacheExchange()).with(AmqEnums.MSG_CACHE_DELAYED.routeKey).noargs();
    }
}
