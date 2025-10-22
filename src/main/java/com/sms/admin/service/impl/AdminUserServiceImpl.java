package com.sms.admin.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sms.admin.entity.AdminUserEntity;
import com.sms.admin.mapper.AdminUserMapper;
import com.sms.admin.req.AdminCreateUserReq;
import com.sms.admin.req.AdminLoginReq;
import com.sms.admin.req.AdminUpdatePwdReq;
import com.sms.admin.req.AdminUserPageReq;
import com.sms.admin.req.AdminUserSetAdminReq;
import com.sms.admin.service.AdminUserService;
import com.sms.base.resp.ApiResp;
import com.sms.base.resp.PageResp;
import com.sms.client.entity.ClientUserEntity;
import com.sms.client.resp.LoginResp;
import com.sms.dto.LoginUserDto;
import com.sms.enums.RedisKeyEnums;
import com.sms.exception.BusinessException;
import com.sms.exception.ParamException;
import com.sms.util.JWTTokenUtil;
import com.sms.util.RedisUtils;
import com.sms.util.SecurityFrameworkUtils;

/** 
* @author yangxy
* @version 创建时间：2025年10月22日 下午12:21:39 
*/
@Service
public class AdminUserServiceImpl extends ServiceImpl<AdminUserMapper, AdminUserEntity> implements AdminUserService {
	@Autowired
	private RedisUtils redisUtils;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	@SuppressWarnings("unchecked")
	public ApiResp<PageResp<AdminUserEntity>> page(AdminUserPageReq adminUserPageReq) {
		// TODO Auto-generated method stub
		LambdaQueryWrapper<AdminUserEntity> lambda = new QueryWrapper<AdminUserEntity>().lambda();
		
		if(!StringUtils.isEmpty(adminUserPageReq.getTelPhone())) {
			lambda.like(AdminUserEntity::getTelPhone, adminUserPageReq.getTelPhone());
		}
		
		if(!StringUtils.isEmpty(adminUserPageReq.getUserName())) {
			lambda.like(AdminUserEntity::getUserName, adminUserPageReq.getUserName());
		}
		
		if(!ObjectUtils.isEmpty(adminUserPageReq.getIsAdmin())) {
			lambda.eq(AdminUserEntity::getIsAdmin, adminUserPageReq.getIsAdmin());
		}
		lambda.orderByDesc(AdminUserEntity::getCreatedAt);
		
		PageHelper.startPage(adminUserPageReq.getPageNo(), adminUserPageReq.getPageSize());
		Page<AdminUserEntity> page = (Page<AdminUserEntity>) list(lambda);
		return ApiResp.page(page);
	}

	@Override
	@SuppressWarnings("unchecked")
	public ApiResp<LoginResp> login(AdminLoginReq adminLoginReq) {
		// TODO Auto-generated method stub
		AdminUserEntity adminUserEntity = getOne(new QueryWrapper<AdminUserEntity>().lambda().eq(AdminUserEntity::getUserName, adminLoginReq.getUserName()));
		
		if(ObjectUtils.isEmpty(adminUserEntity)) {
			throw new BusinessException("用户不存在");
		}
		
		boolean matches = bCryptPasswordEncoder.matches(adminLoginReq.getPassWord(),adminUserEntity.getPassWord());

        if(!matches){
            throw new BusinessException("用户名或密码错误");
        }
		
        LoginUserDto loginUserDto = new LoginUserDto();
        loginUserDto.setType(2);
        loginUserDto.setUserId(adminUserEntity.getId());
        loginUserDto.setTelPhone(adminUserEntity.getTelPhone());
        String accessToken = JWTTokenUtil.createAccessToken(loginUserDto);
        LoginResp loginResp = new LoginResp();
        loginResp.setAccessToken(accessToken);
        redisUtils.set(RedisKeyEnums.ADMIN_LOGIN_TOKEN.key+loginUserDto.getTelPhone(), accessToken,2*60*60);
        return ApiResp.sucess(loginResp);
	}

	@Override
	@SuppressWarnings("unchecked")
	public ApiResp<String> logout() {
		// TODO Auto-generated method stub
		LoginUserDto loginUser = SecurityFrameworkUtils.getLoginUser();
		redisUtils.del(RedisKeyEnums.ADMIN_LOGIN_TOKEN.key+loginUser.getTelPhone());
		return ApiResp.sucess();
	}

	@Override
	@SuppressWarnings("unchecked")
	public ApiResp<String> updatePwd(AdminUpdatePwdReq adminUpdatePwdReq) {
		// TODO Auto-generated method stub
		if(!adminUpdatePwdReq.getNewPassWord().equals(adminUpdatePwdReq.getNewPassWordConfirm())) {
			throw new ParamException("两次密码不一致");
		}
		
		LoginUserDto loginUser = SecurityFrameworkUtils.getLoginUser();
		AdminUserEntity adminUserEntity = getById(loginUser.getUserId());
		boolean matches = bCryptPasswordEncoder.matches(adminUpdatePwdReq.getOldPassWord(),adminUserEntity.getPassWord());
		if(!matches){
            throw new BusinessException("原密码错误");
        }
		String password = bCryptPasswordEncoder.encode(adminUpdatePwdReq.getNewPassWord());
		adminUserEntity.setPassWord(password);
		updateById(adminUserEntity);
		return ApiResp.sucess();
	}

	@Override
	@SuppressWarnings("unchecked")
	public ApiResp<String> setAdmin(AdminUserSetAdminReq adminUserSetAdminReq) {
		// TODO Auto-generated method stub
		AdminUserEntity adminUserEntity = getById(adminUserSetAdminReq.getUserId());
		if(ObjectUtils.isEmpty(adminUserEntity)) {
			throw new BusinessException("用户不存在");
		}
		if(adminUserSetAdminReq.getType() == 1) {//操作类型（1设置超级用户，2取消超级用户）
			adminUserEntity.setIsAdmin(0);
		}else if(adminUserSetAdminReq.getType() == 2) {
			adminUserEntity.setIsAdmin(1);
		}else {
			throw new ParamException("操作类型错误");
		}
		updateById(adminUserEntity);
		return ApiResp.sucess();
	}

	@Override
	@SuppressWarnings("unchecked")
	public ApiResp<String> del(Long id) {
		// TODO Auto-generated method stub
		AdminUserEntity adminUserEntity = getById(id);
		if(ObjectUtils.isEmpty(adminUserEntity)) {
			throw new BusinessException("用户不存在");
		}
		
		if(adminUserEntity.getIsAdmin() == 1) {
			throw new BusinessException("超级管理员不能删除");
		}
		removeById(id);
		return ApiResp.sucess();
	}

	@Override
	@SuppressWarnings("unchecked")
	public ApiResp<String> createUser(AdminCreateUserReq adminCreateUserReq) {
		// TODO Auto-generated method stub
		AdminUserEntity checkUserName = getOne(new QueryWrapper<AdminUserEntity>().lambda().eq(AdminUserEntity::getUserName, adminCreateUserReq.getUserName()));
		if(!ObjectUtils.isEmpty(checkUserName)) {
			throw new BusinessException("用户名已存在");
		}
		
		AdminUserEntity checkTelPhone = getOne(new QueryWrapper<AdminUserEntity>().lambda().eq(AdminUserEntity::getTelPhone, adminCreateUserReq.getTelPhone()));
		if(!ObjectUtils.isEmpty(checkTelPhone)) {
			throw new BusinessException("联系电话已存在");
		}
		String password = bCryptPasswordEncoder.encode(adminCreateUserReq.getPassWord());
		AdminUserEntity adminUserEntity = new AdminUserEntity();
		BeanUtils.copyProperties(adminCreateUserReq, adminUserEntity);
		adminUserEntity.setPassWord(password);
		
		save(adminUserEntity);
		return ApiResp.sucess();
	}

}
