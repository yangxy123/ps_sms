package com.sms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import com.sms.util.DefaultProfileUtil;

import lombok.extern.slf4j.Slf4j;

/** 
* @author yangxy
* @version 创建时间：2025年9月27日 下午2:13:01 
*/
@Slf4j
@SpringBootApplication
public class SmsApplication {
	public static void main( String[] args )
    {
    	SpringApplication app = new SpringApplication(SmsApplication.class);
	      DefaultProfileUtil.addDefaultProfile(app);
	      Environment env = app.run(args).getEnvironment(); 
	      log.info("\n----------------------------------------------------------\n\t" +
	                  "系统服务{} 启动成功! " +
	                  "端口号{}\n\t" +
	                  "运行环境{}\n\t"+
	                  "----------------------------------------------------------",
	            env.getProperty("spring.application.name"),
	            env.getProperty("server.port"),
	            env.getActiveProfiles().length == 0 ? env.getDefaultProfiles():env.getActiveProfiles());
    }
}
