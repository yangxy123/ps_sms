package com.sms.admin.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sms.admin.entity.AdminRobotEntity;
import com.sms.admin.req.AdminRobotAddReq;
import com.sms.admin.req.AdminRobotEditReq;
import com.sms.admin.req.AdminRobotPageReq;
import com.sms.admin.service.AdminRobotService;
import com.sms.annotations.HasLogin;
import com.sms.base.resp.ApiResp;
import com.sms.base.resp.PageResp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/** 
* @author yangxy
* @version 创建时间：2025年10月22日 下午1:01:22 
*/
@RestController
@RequestMapping("/admin")
@Api(tags = "后台机器人管理")
public class AdminRobotController {
	@Autowired
	private AdminRobotService adminRobotService;
	
	@HasLogin
	@GetMapping("/page")
	@ApiOperation("分页查询")
	public ApiResp<PageResp<AdminRobotEntity>> page(AdminRobotPageReq adminRobotPageReq){
		return adminRobotService.page(adminRobotPageReq);
	}

	@HasLogin
	@PostMapping("/add")
	@ApiOperation("新增")
	public ApiResp<String> add(@RequestBody @Valid AdminRobotAddReq adminRobotAddReq){
		return adminRobotService.add(adminRobotAddReq);
	}

	@HasLogin
	@PostMapping("/edit")
	@ApiOperation("修改")
	public ApiResp<String> edit(@RequestBody @Valid AdminRobotEditReq adminRobotEditReq){
		return adminRobotService.edit(adminRobotEditReq);
	}

	@HasLogin
	@GetMapping("/del/{id}")
	@ApiOperation("删除")
	@ApiImplicitParam(name = "id",value = "用户ID",required = true)
	public ApiResp<String> del(@PathVariable("id") Long id){
		return adminRobotService.del(id);
	}

	@HasLogin
	@PostMapping("/refreshCache")
	@ApiOperation("刷新机器人缓存")
	public ApiResp<String> refreshCache(){
		return adminRobotService.refreshCache();
	}
}
