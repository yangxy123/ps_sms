package com.sms.client.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sms.base.resp.ApiResp;
import com.sms.client.entity.ClientUserEntity;
import com.sms.client.mapper.ClientUserMapper;
import com.sms.client.req.ClientLoginReq;
import com.sms.client.req.ClientUserRegisterReq;
import com.sms.client.req.ClientUserUpdateWordReq;
import com.sms.client.resp.LoginResp;
import com.sms.client.service.ClientUserService;
import com.sms.dto.LoginUserDto;
import com.sms.dto.MsgDto;
import com.sms.enums.RedisKeyEnums;
import com.sms.exception.ApiBussException;
import com.sms.exception.AuthorityException;
import com.sms.exception.BusinessException;
import com.sms.exception.ParamException;
import com.sms.util.JWTTokenUtil;
import com.sms.util.RedisUtils;
import com.sms.util.SecurityFrameworkUtils;
import com.sms.websocket.NettyWebSocketHandler;

/** 
* @author yangxy
* @version 创建时间：2025年10月22日 上午9:18:01 
*/
@Service
public class ClientUserServiceImpl extends ServiceImpl<ClientUserMapper, ClientUserEntity> implements ClientUserService {
	@Autowired
	private RedisUtils redisUtils;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private NettyWebSocketHandler nettyWebSocketHandler;
	
	@Override
	@SuppressWarnings("unchecked")
	public ApiResp<LoginResp> login(ClientLoginReq clientLoginReq) {
		// TODO Auto-generated method stub
		ClientUserEntity clientUserEntity = getOne(new QueryWrapper<ClientUserEntity>().lambda().eq(ClientUserEntity::getTelPhone, clientLoginReq.getTelPhone()));
		if(ObjectUtils.isEmpty(clientUserEntity)) {
			throw new ApiBussException("用户不存在");
		}
		
		boolean matches = bCryptPasswordEncoder.matches(clientLoginReq.getPassWord(),clientUserEntity.getPassWord());

        if(!matches){
            throw new BusinessException("用户名或密码错误");
        }
		
        LoginUserDto loginUserDto = new LoginUserDto();
        loginUserDto.setType(1);
        BeanUtils.copyProperties(clientUserEntity, loginUserDto);
        String accessToken = JWTTokenUtil.createAccessToken(loginUserDto);
        LoginResp loginResp = new LoginResp();
        loginResp.setAccessToken(accessToken);
        redisUtils.set(RedisKeyEnums.CLIENT_LOGIN_TOKEN.key+loginUserDto.getTelPhone(), accessToken,2*60*60);
        return ApiResp.sucess(loginResp);
	}

	@Override
	@SuppressWarnings("unchecked")
	public ApiResp<String> logout() {
		// TODO Auto-generated method stub
		LoginUserDto loginUser = SecurityFrameworkUtils.getLoginUser();
		redisUtils.del(RedisKeyEnums.CLIENT_LOGIN_TOKEN.key+loginUser.getTelPhone());
		return ApiResp.sucess();
	}

	@Override
	@SuppressWarnings("unchecked")
	public ApiResp<String> register(ClientUserRegisterReq clientUserRegisterReq) {
		// TODO Auto-generated method stub
//		if(!redisUtils.hasKey(RedisKeyEnums.CAPTCHA.key+clientUserRegisterReq.getTelPhone())) {
//			throw new BusinessException("验证码已过期");
//		}
//		
//		String code = (String) redisUtils.get(RedisKeyEnums.CAPTCHA.key+clientUserRegisterReq.getTelPhone());
//		if(!clientUserRegisterReq.getCode().equals(code)) {
//			throw new BusinessException("验证码错误");
//		}
		
		ClientUserEntity checkClientUserEntity = getOne(new QueryWrapper<ClientUserEntity>().lambda().eq(ClientUserEntity::getTelPhone, clientUserRegisterReq.getTelPhone()));
		if(!ObjectUtils.isEmpty(checkClientUserEntity)) {
			throw new BusinessException("用户已存在");
		}
		
		ClientUserEntity clientUserEntity = new ClientUserEntity();
		BeanUtils.copyProperties(clientUserRegisterReq, clientUserEntity);
		String password = bCryptPasswordEncoder.encode(clientUserRegisterReq.getPassWord());
		clientUserEntity.setPassWord(password);
		save(clientUserEntity);
		return ApiResp.sucess();
	}

	@Override
	@SuppressWarnings("unchecked")
	public ApiResp<String> updateWord(ClientUserUpdateWordReq clientUserUpdateWordReq) {
		// TODO Auto-generated method stub
		if(!clientUserUpdateWordReq.getNewPassWord().equals(clientUserUpdateWordReq.getNewPassWordConfirm())) {
			throw new ParamException("两次密码不一致");
		}
		
		LoginUserDto loginUser = SecurityFrameworkUtils.getLoginUser();
		ClientUserEntity clientUserEntity = getById(loginUser.getUserId());
		boolean matches = bCryptPasswordEncoder.matches(clientUserUpdateWordReq.getOldPassWord(),clientUserEntity.getPassWord());
		if(!matches){
            throw new BusinessException("原密码错误");
        }
		String password = bCryptPasswordEncoder.encode(clientUserUpdateWordReq.getNewPassWord());
		clientUserEntity.setPassWord(password);
		updateById(clientUserEntity);
		return ApiResp.sucess();
	}

	@Override
	@SuppressWarnings("unchecked")
	public ApiResp<String> mute(Long id) {
		// TODO Auto-generated method stub
		LoginUserDto loginUser = SecurityFrameworkUtils.getLoginUser();
		if(!redisUtils.hasKey(RedisKeyEnums.CLIENT_MANAGER.key+loginUser.getUserId())) {
			throw new AuthorityException("没有权限");
		}
		
		ClientUserEntity clientUserEntity = getById(id);
		redisUtils.set(RedisKeyEnums.MUTE+clientUserEntity.getNickeName(), clientUserEntity,60*60);
		nettyWebSocketHandler.sendToUser(clientUserEntity.getId() + "_" + clientUserEntity.getNickeName(), JSON.toJSONString(MsgDto.sysMst("您已被禁言一小时")));
		return ApiResp.sucess();
	}

}
