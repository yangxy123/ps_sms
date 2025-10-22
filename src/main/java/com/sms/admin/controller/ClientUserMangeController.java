package com.sms.admin.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sms.admin.req.ClientUserPageReq;
import com.sms.admin.req.SetManagerReq;
import com.sms.admin.service.ClientUserMangeService;
import com.sms.annotations.HasLogin;
import com.sms.base.resp.ApiResp;
import com.sms.base.resp.PageResp;
import com.sms.client.entity.ClientUserEntity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
* @author yangxy
* @version 创建时间：2025年10月22日 上午10:42:03 
*/
@RestController
@RequestMapping("/admin/clientUser")
@Api(tags = "后台客户端用户管理")
public class ClientUserMangeController {
	@Autowired
	private ClientUserMangeService clientUserMangeService;
	
	@HasLogin
	@GetMapping("/page")
	@ApiOperation("分页查询")
	public ApiResp<PageResp<ClientUserEntity>> page(ClientUserPageReq clientUserPageReq){
		return clientUserMangeService.page(clientUserPageReq);
	}
	
	@HasLogin
	@PostMapping("/setManger")
	@ApiOperation("设置/取消管理员")
	public ApiResp<String> setManger(@RequestBody @Valid SetManagerReq setManagerReq){
		return clientUserMangeService.setManger(setManagerReq);
	}
}
