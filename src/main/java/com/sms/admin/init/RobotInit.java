package com.sms.admin.init;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sms.admin.entity.AdminRobotEntity;
import com.sms.admin.service.AdminRobotService;
import com.sms.enums.RedisKeyEnums;
import com.sms.util.RedisUtils;

import lombok.extern.slf4j.Slf4j;

/** 
* @author yangxy
* @version 创建时间：2025年10月22日 下午1:49:24 
*/
@Slf4j
@Component
public class RobotInit implements CommandLineRunner {
	@Autowired
	private RedisUtils redisUtils;
	@Autowired
	private AdminRobotService adminRobotService;

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		List<AdminRobotEntity> list = adminRobotService.list(new QueryWrapper<AdminRobotEntity>().lambda().eq(AdminRobotEntity::getStatus, 1));
		redisUtils.pushAllList(RedisKeyEnums.ADMIN_ROBOT.key, list);
		log.info("机器人关键词初始化");
	}

}
