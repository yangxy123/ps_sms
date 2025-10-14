package com.sms.websocket;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/** 
 * netty通道组池
* @author yangxy
* @version 创建时间：2023年3月7日 上午9:49:22 
*/
public class NettyChannelHandlerPool {
	public static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
}
