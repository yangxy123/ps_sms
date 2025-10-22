package com.sms.client.service;

import org.springframework.web.bind.annotation.PathVariable;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sms.base.resp.ApiResp;
import com.sms.client.entity.ClientUserEntity;
import com.sms.client.req.ClientLoginReq;
import com.sms.client.req.ClientUserRegisterReq;
import com.sms.client.req.ClientUserUpdateWordReq;
import com.sms.client.resp.LoginResp;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/** 
* @author yangxy
* @version 创建时间：2025年10月22日 上午9:16:13 
*/
public interface ClientUserService extends IService<ClientUserEntity> {
	/**
	 * 用户登录
	* @author yangxy
	* @version 创建时间：2025年10月22日 上午9:39:36 
	* @param clientLoginReq
	* @return
	 */
	public ApiResp<LoginResp> login(ClientLoginReq clientLoginReq);
	
	/**
	 * logout
	* @author yangxy
	* @version 创建时间：2025年10月22日 上午9:39:42 
	* @return
	 */
	public ApiResp<String> logout();
	
	/**
	 * 用户注册
	* @author yangxy
	* @version 创建时间：2025年10月22日 上午9:39:46 
	* @param clientUserRegisterReq
	* @return
	 */
	public ApiResp<String> register(ClientUserRegisterReq clientUserRegisterReq);
	
	/**
	 * 修改密码
	* @author yangxy
	* @version 创建时间：2025年10月22日 上午9:39:50 
	* @param clientUserUpdateWordReq
	* @return
	 */
	public ApiResp<String> updateWord(ClientUserUpdateWordReq clientUserUpdateWordReq);
	
	/**
	 * 用户禁言
	* @author yangxy
	* @version 创建时间：2025年10月22日 上午10:17:10 
	* @param id
	* @return
	 */
	public ApiResp<String> mute(@PathVariable("id") Long id);
}
