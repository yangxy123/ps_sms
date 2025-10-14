package com.sms;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.sms.websocket.NettyServer;

import lombok.extern.slf4j.Slf4j;

/** 
 * 启动nettyserver服务
* @author yangxy
* @version 创建时间：2023年3月8日 上午9:39:34 
*/
@Slf4j
@Component
public class NettyServerStart implements CommandLineRunner{
	@Value("${netty.port:-1}")
    private Integer port;
	
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		if(port != -1) {
			try {
				new NettyServer(port).start();
			} catch (Exception e) {
				log.error("netty服务器启动失败:{}",e.getMessage());
			}
		}
	}
}
