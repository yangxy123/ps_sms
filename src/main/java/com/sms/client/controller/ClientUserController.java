package com.sms.client.controller;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sms.annotations.HasLogin;
import com.sms.base.resp.ApiResp;
import com.sms.client.req.ClientLoginReq;
import com.sms.client.req.ClientUserRegisterReq;
import com.sms.client.req.ClientUserUpdateWordReq;
import com.sms.client.resp.LoginResp;
import com.sms.client.service.ClientUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/** 
* @author yangxy
* @version 创建时间：2025年10月22日 上午9:14:31 
*/
@RestController
@RequestMapping("/client/user")
@Api(tags = "客户端用户相关接口")
public class ClientUserController {
	@Autowired
	private ClientUserService clientUserService;
	
	@PostMapping("/login")
	@ApiOperation("用户登录")
	public ApiResp<LoginResp> login(@RequestBody @Valid ClientLoginReq clientLoginReq){
		return clientUserService.login(clientLoginReq);
	}
	
	@HasLogin
	@PostMapping("/logout")
	@ApiOperation("用户登出")
	public ApiResp<String> logout(){
		return clientUserService.logout();
	}
	
	@PostMapping("/register")
	@ApiOperation("用户注册")
	public ApiResp<String> register(@RequestBody @Valid ClientUserRegisterReq clientUserRegisterReq){
		return clientUserService.register(clientUserRegisterReq);
	}
	
	@HasLogin
	@PostMapping("/updateWord")
	@ApiOperation("修改密码")
	public ApiResp<String> updateWord(@RequestBody @Valid ClientUserUpdateWordReq clientUserUpdateWordReq){
		return clientUserService.updateWord(clientUserUpdateWordReq);
	}
	
	@HasLogin
	@GetMapping("/mute/{id}")
	@ApiOperation("用户禁言")
	@ApiImplicitParam(name = "id",value = "用户ID",required = true)
	public ApiResp<String> mute(@PathVariable("id") Long id){
		return clientUserService.mute(id);
	}
}
