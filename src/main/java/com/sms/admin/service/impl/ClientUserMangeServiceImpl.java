package com.sms.admin.service.impl;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.util.StringUtil;
import com.sms.admin.req.ClientUserPageReq;
import com.sms.admin.req.SetManagerReq;
import com.sms.admin.service.ClientUserMangeService;
import com.sms.base.resp.ApiResp;
import com.sms.base.resp.PageResp;
import com.sms.client.entity.ClientUserEntity;
import com.sms.client.service.ClientUserService;
import com.sms.enums.RedisKeyEnums;
import com.sms.exception.BusinessException;
import com.sms.exception.ParamException;
import com.sms.util.RedisUtils;

/** 
* @author yangxy
* @version 创建时间：2025年10月22日 上午10:44:30 
*/
@Service
public class ClientUserMangeServiceImpl implements ClientUserMangeService {
	@Autowired
	private RedisUtils redisUtils;
	@Autowired
	private ClientUserService clientUserService;

	@Override
	@SuppressWarnings("unchecked")
	public ApiResp<PageResp<ClientUserEntity>> page(ClientUserPageReq clientUserPageReq) {
		// TODO Auto-generated method stub
		LambdaQueryWrapper<ClientUserEntity> lambda = new QueryWrapper<ClientUserEntity>().lambda();
		
		if(!StringUtils.isEmpty(clientUserPageReq.getTelPhone())) {
			lambda.like(ClientUserEntity::getTelPhone, clientUserPageReq.getTelPhone());
		}
		
		if(!StringUtils.isEmpty(clientUserPageReq.getNickeName())) {
			lambda.like(ClientUserEntity::getNickeName, clientUserPageReq.getNickeName());
		}
		
		if(!ObjectUtils.isEmpty(clientUserPageReq.getIsManger())) {
			lambda.eq(ClientUserEntity::getIsManger, clientUserPageReq.getIsManger());
		}
		lambda.orderByDesc(ClientUserEntity::getCreatedAt);
		PageHelper.startPage(clientUserPageReq.getPageNo(), clientUserPageReq.getPageSize());
		Page<ClientUserEntity> page = (Page<ClientUserEntity>) clientUserService.list(lambda);
		return ApiResp.page(page);
	}

	@Override
	public ApiResp<String> setManger(@Valid SetManagerReq setManagerReq) {
		// TODO Auto-generated method stub
		ClientUserEntity clientUserEntity = clientUserService.getById(setManagerReq.getUserId());
		if(ObjectUtils.isEmpty(clientUserEntity)) {
			throw new BusinessException("会员不存在");
		}
		
		if(setManagerReq.getType() == 1){//1设置为管理员，2取消管理员
			clientUserEntity.setIsManger(1);
			clientUserService.updateById(clientUserEntity);
			redisUtils.set(RedisKeyEnums.CLIENT_MANAGER.key+clientUserEntity.getId(), clientUserEntity );
		}else if(setManagerReq.getType() == 2) {
			clientUserEntity.setIsManger(0);
			clientUserService.updateById(clientUserEntity);
			redisUtils.del(RedisKeyEnums.CLIENT_MANAGER.key+clientUserEntity.getId());
		}else {
			throw new ParamException("操作类型错误");
		}
		return ApiResp.sucess();
	}

}
