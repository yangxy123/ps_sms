package com.sms.admin.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sms.admin.entity.AdminUserEntity;
import com.sms.admin.req.AdminCreateUserReq;
import com.sms.admin.req.AdminLoginReq;
import com.sms.admin.req.AdminUpdatePwdReq;
import com.sms.admin.req.AdminUserPageReq;
import com.sms.admin.req.AdminUserSetAdminReq;
import com.sms.admin.service.AdminUserService;
import com.sms.annotations.HasLogin;
import com.sms.base.resp.ApiResp;
import com.sms.base.resp.PageResp;
import com.sms.client.resp.LoginResp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/** 
* @author yangxy
* @version 创建时间：2025年10月22日 上午11:07:24 
*/
@RestController
@RequestMapping("/amdin/user")
@Api(tags = "后台用户管理")
public class AdminUserController {
	@Autowired
	private AdminUserService adminUserService;
	
	@HasLogin
	@GetMapping("/page")
	@ApiOperation("分页查询")
	public ApiResp<PageResp<AdminUserEntity>> page(AdminUserPageReq adminUserPageReq){
		return adminUserService.page(adminUserPageReq);
	}
	
	@PostMapping("/login")
	@ApiOperation("登录")
	public ApiResp<LoginResp> login(@RequestBody @Valid AdminLoginReq adminLoginReq){
		return adminUserService.login(adminLoginReq);
	}

	@HasLogin
	@GetMapping("logout")
	@ApiOperation("登出")
	public ApiResp<String> logout(){
		return adminUserService.logout();
	}

	@HasLogin
	@PostMapping("/updatePwd")
	@ApiOperation("修改密码")
	public ApiResp<String> updatePwd(@RequestBody @Valid AdminUpdatePwdReq adminUpdatePwdReq){
		return adminUserService.updatePwd(adminUpdatePwdReq);
	}

	@HasLogin
	@PostMapping("/setAdmin")
	@ApiOperation("设置/取消超级管理员")
	public ApiResp<String> setAdmin(@RequestBody @Valid AdminUserSetAdminReq adminUserSetAdminReq){
		return adminUserService.setAdmin(adminUserSetAdminReq);
	}
	
	@HasLogin
	@GetMapping("/del/{id}")
	@ApiOperation("删除用户")
	@ApiImplicitParam(name = "id",value = "用户ID",required = true)
	public ApiResp<String> del(@PathVariable("id") Long id){
		return adminUserService.del(id);
	}
	
//	@HasLogin
	@PostMapping("/createUser")
	@ApiOperation("创建用户")
	public ApiResp<String> createUser(@RequestBody @Valid AdminCreateUserReq adminCreateUserReq){
		return adminUserService.createUser(adminCreateUserReq);
	}
}
