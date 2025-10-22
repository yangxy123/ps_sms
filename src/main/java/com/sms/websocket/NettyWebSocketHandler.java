package com.sms.websocket;

import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.sms.admin.entity.AdminRobotEntity;
import com.sms.contants.MsgContants;
import com.sms.dto.LoginUserDto;
import com.sms.dto.MsgDto;
import com.sms.enums.AmqEnums;
import com.sms.enums.RedisKeyEnums;
import com.sms.util.AmqUtils;
import com.sms.util.JWTTokenUtil;
import com.sms.util.RedisUtils;
import com.sms.util.SpringUtil;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

/**
 * @author yangxy
 * @version 创建时间：2023年3月7日 上午9:50:15
 */
@Slf4j
@Component
public class NettyWebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
	private RedisUtils redisUtils;
	
	private AmqUtils amqUtils;
	
	private void init() {
		if(ObjectUtils.isEmpty(redisUtils)) {
			this.redisUtils = SpringUtil.getBean("redisUtils",RedisUtils.class);
			this.amqUtils = SpringUtil.getBean("amqUtils",AmqUtils.class);
		}
	}
	
	@Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
		init();
        //添加到channelGroup通道组
        NettyChannelHandlerPool.channelGroup.add(ctx.channel());
        log.info("与客户端建立连接，通道开启！当前通道数量:{}",NettyChannelHandlerPool.channelGroup.size());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		init();
        String token = NettyServer.ChannelIdToUserMap.get(ctx.channel().id().asLongText());
        if(StringUtils.isEmpty(token)) {
        	return ;
        }
        NettyServer.userChannelMap.remove(token);
        NettyServer.ChannelIdToUserMap.remove(ctx.channel().id().asLongText());
        //移除channelGroup 通道组
        NettyChannelHandlerPool.channelGroup.remove(ctx.channel());
		log.info("与客户端断开连接，通道关闭！当前通道数量:{}",NettyChannelHandlerPool.channelGroup.size());
    }
    
    /**
     * 发生异常触发
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		init();
    	String token = NettyServer.ChannelIdToUserMap.get(ctx.channel().id().asLongText());
    	if(StringUtils.isEmpty(token)) {
            ctx.close();
            return;
    	}
        NettyServer.userChannelMap.remove(token);
        NettyServer.ChannelIdToUserMap.remove(ctx.channel().id().asLongText());
        //移除channelGroup 通道组
        NettyChannelHandlerPool.channelGroup.remove(ctx.channel());
        log.error("账号:{}连接出现异常,异常:{}",token,cause.getMessage());
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		init();
        //首次连接是FullHttpRequest，处理参数 by zhengkai.blog.csdn.net
		boolean loginFlag = false;
        if (null != msg && msg instanceof FullHttpRequest) {
            FullHttpRequest request = (FullHttpRequest) msg;
            String uri = request.uri();
            Map paramMap=getUrlParams(uri);
            //如果url包含参数，需要处理
            if(uri.contains("/websocket?")){
                String newUri=uri.substring(0,uri.indexOf("?"));
                request.setUri(newUri);
                String token = (String)paramMap.get("token");
                LoginUserDto loginUserDto = JWTTokenUtil.decodeToke(token);
                if(ObjectUtils.isEmpty(loginUserDto)) {//游客模式
                	NettyServer.userChannelMap.put(token, ctx.channel());
                    NettyServer.ChannelIdToUserMap.put(ctx.channel().id().asLongText(), token);
                }else {
                	log.info(JSON.toJSONString(loginUserDto));
                	log.info(loginUserDto.getUserId()+"_"+loginUserDto.getNickeName());
                	NettyServer.userChannelMap.put(loginUserDto.getUserId()+"_"+loginUserDto.getNickeName(), ctx.channel());
                    NettyServer.ChannelIdToUserMap.put(ctx.channel().id().asLongText(), loginUserDto.getUserId()+"_"+loginUserDto.getNickeName());
                }
                
                loginFlag = true;
            }else {
            	super.channelRead(ctx, msg);
            	ctx.channel().writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(MsgDto.sysMst("连接错误"))));
            	ctx.channel().close();
            }

        }else if(msg instanceof TextWebSocketFrame){
            //正常的TEXT消息类型
            TextWebSocketFrame frame=(TextWebSocketFrame)msg;
        }
        super.channelRead(ctx, msg);
        if(loginFlag) {
            List<Object> allList = redisUtils.getAllList(MsgContants.msgRedisKeySuff);
            if(!allList.isEmpty()) {
            	ctx.channel().writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(allList)));
            }
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame textWebSocketFrame) throws Exception {
		init();
		String sender = NettyServer.ChannelIdToUserMap.get(ctx.channel().id().asLongText());
		String[] split = sender.split("_");
		if(redisUtils.hasKey(RedisKeyEnums.MUTE+split[0])) {
			sendToUser(split[1], JSON.toJSONString(MsgDto.sysMst("您已被禁言一小时")));
			return;
		}
    	MsgDto msgDto = null;
    	try {
			msgDto = JSON.toJavaObject(JSON.parseObject(textWebSocketFrame.text()), MsgDto.class);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			ctx.channel().writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(MsgDto.sysMst("消息格式错误"))));
			return ;
		}
    	
    	if(!MsgContants.typeList.contains(msgDto.getType())) {
    		ctx.channel().writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(MsgDto.sysMst("消息类型错误"))));
			return ;
    	}
    	
    	if(msgDto.getType() == 5) {//心跳消息不处理
    		return;
    	}

    	if(msgDto.getType() == 1 || msgDto.getType() == 2 || msgDto.getType() == 3 ) {
    		msgDto.setSendTime(new Date());
    		msgDto.setSender(split.length == 2?split[1]:sender);
    		redisUtils.pushList(MsgContants.msgRedisKeySuff, msgDto);
    		amqUtils.sendDelayedMsg(AmqEnums.MSG_CACHE_DELAYED.exchangeName, AmqEnums.MSG_CACHE_DELAYED.routeKey, JSON.toJSONString(msgDto), 60*10);
    		sendAllMessage(JSON.toJSONString(msgDto));
    	}

    	if(msgDto.getType() == 1) {

    		if(redisUtils.hasKey(RedisKeyEnums.ADMIN_ROBOT.key)) {

        		List<Object> allList = redisUtils.getAllList(RedisKeyEnums.ADMIN_ROBOT.key);
        		List<?> list = allList;
        		List<AdminRobotEntity> reList = (List<AdminRobotEntity>) list;

        		String msg = msgDto.getMsg();
        		String reply = reList.stream().map(AdminRobotEntity::getKeyWord).filter(vo->msg.indexOf(vo) >= 0).max(Comparator.comparingInt(String::length)).orElse(null);
        		if(!StringUtils.isEmpty(reply)) {
        			sendAllMessage(JSON.toJSONString(MsgDto.sysMst(reply)));
        		}
    		}
    	}
    	
    }
    
    /**
     * 给所有人发消息
    * @author yangxy
    * @version 创建时间：2023年3月8日 上午10:16:35 
    * @param message
     */
    public void sendAllMessage(String message){
        NettyChannelHandlerPool.channelGroup.writeAndFlush(new TextWebSocketFrame(message));
    }


    private static Map getUrlParams(String url){
        Map<String,String> map = new HashMap<>();
        url = url.replace("?",";");
        if (!url.contains(";")){
            return map;
        }
        if (url.split(";").length > 0){
            String[] arr = url.split(";")[1].split("&");
            for (String s : arr){
                String key = s.split("=")[0];
                String value = s.split("=")[1];
                map.put(key,value);
            }
            return  map;

        }else{
            return map;
        }
    }
    
    public void sendToUser(String user,String msg) {
    	log.info(user);
    	Channel channel = NettyServer.userChannelMap.get(user);
    	if(!ObjectUtils.isEmpty(channel)) {
    		log.info("1");
    		channel.writeAndFlush(new TextWebSocketFrame(msg));
    	}
    }
}
