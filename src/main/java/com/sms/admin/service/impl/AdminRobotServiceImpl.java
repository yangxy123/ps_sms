package com.sms.admin.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sms.admin.entity.AdminRobotEntity;
import com.sms.admin.mapper.AdminRobotMapper;
import com.sms.admin.req.AdminRobotAddReq;
import com.sms.admin.req.AdminRobotEditReq;
import com.sms.admin.req.AdminRobotPageReq;
import com.sms.admin.service.AdminRobotService;
import com.sms.base.resp.ApiResp;
import com.sms.base.resp.PageResp;
import com.sms.enums.RedisKeyEnums;
import com.sms.exception.BusinessException;
import com.sms.util.RedisUtils;

/** 
* @author yangxy
* @version 创建时间：2025年10月22日 下午1:08:34 
*/
@Service
public class AdminRobotServiceImpl extends ServiceImpl<AdminRobotMapper, AdminRobotEntity> implements AdminRobotService {
	@Autowired
	private RedisUtils redisUtils;
	
	@Override
	@SuppressWarnings("unchecked")
	public ApiResp<PageResp<AdminRobotEntity>> page(AdminRobotPageReq adminRobotPageReq) {
		// TODO Auto-generated method stub
		LambdaQueryWrapper<AdminRobotEntity> lambda = new QueryWrapper<AdminRobotEntity>().lambda();
		
		if(!StringUtils.isEmpty(adminRobotPageReq.getKeyWord())) {
			lambda.like(AdminRobotEntity::getKeyWord, adminRobotPageReq.getKeyWord());
		}
		
		if(!ObjectUtils.isEmpty(adminRobotPageReq.getStatus())) {
			lambda.eq(AdminRobotEntity::getStatus, adminRobotPageReq.getStatus());
		}
		lambda.orderByDesc(AdminRobotEntity::getCreatedAt);
		
		PageHelper.startPage(adminRobotPageReq.getPageNo(),adminRobotPageReq.getPageSize());
		Page<AdminRobotEntity> page = (Page<AdminRobotEntity>) list(lambda);
		return ApiResp.page(page);
	}

	@Override
	@SuppressWarnings("unchecked")
	public ApiResp<String> add(AdminRobotAddReq adminRobotAddReq) {
		// TODO Auto-generated method stub
		AdminRobotEntity checkKeyWord = getOne(new QueryWrapper<AdminRobotEntity>().lambda().eq(AdminRobotEntity::getKeyWord, adminRobotAddReq.getKeyWord()));
		if(!ObjectUtils.isEmpty(checkKeyWord)) {
			throw new BusinessException("关键词已存在");
		}
		
		AdminRobotEntity adminRobotEntity = new AdminRobotEntity();
		BeanUtils.copyProperties(adminRobotAddReq, adminRobotEntity);
		save(adminRobotEntity);
		return ApiResp.sucess();
	}

	@Override
	@SuppressWarnings("unchecked")
	public ApiResp<String> edit(AdminRobotEditReq adminRobotEditReq) {
		// TODO Auto-generated method stub
		AdminRobotEntity adminRobotEntity = getById(adminRobotEditReq.getId());
		if(ObjectUtils.isEmpty(adminRobotEntity)) {
			throw new BusinessException("信息不存在");
		}
		
		AdminRobotEntity checkKeyWord = getOne(new QueryWrapper<AdminRobotEntity>().lambda().eq(AdminRobotEntity::getKeyWord, adminRobotEditReq.getKeyWord()));
		if(!ObjectUtils.isEmpty(checkKeyWord)) {
			throw new BusinessException("关键词已存在");
		}
		
		BeanUtils.copyProperties(adminRobotEditReq, adminRobotEntity);
		return ApiResp.sucess();
	}

	@Override
	@SuppressWarnings("unchecked")
	public ApiResp<String> del(Long id) {
		// TODO Auto-generated method stub
		removeById(id);
		return ApiResp.sucess();
	}

	@Override
	@SuppressWarnings("unchecked")
	public ApiResp<String> refreshCache() {
		// TODO Auto-generated method stub
		List<AdminRobotEntity> list = list(new QueryWrapper<AdminRobotEntity>().lambda().eq(AdminRobotEntity::getStatus, 1));
		redisUtils.pushAllList(RedisKeyEnums.ADMIN_ROBOT.key, list);
		return ApiResp.sucess();
	}

}
