package com.sms.admin.service;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.sms.admin.req.ClientUserPageReq;
import com.sms.admin.req.SetManagerReq;
import com.sms.base.resp.ApiResp;
import com.sms.base.resp.PageResp;
import com.sms.client.entity.ClientUserEntity;

import io.swagger.annotations.ApiOperation;

/** 
* @author yangxy
* @version 创建时间：2025年10月22日 上午10:44:00 
*/
public interface ClientUserMangeService {
	@ApiOperation("分页查询")
	/**
	 * 分页查询
	* @author yangxy
	* @version 创建时间：2025年10月22日 上午10:53:51 
	* @param clientUserPageReq
	* @return
	 */
	public ApiResp<PageResp<ClientUserEntity>> page(ClientUserPageReq clientUserPageReq);
	
	/**
	 * 设置/取消管理员
	* @author yangxy
	* @version 创建时间：2025年10月22日 上午10:53:48 
	* @param setManagerReq
	* @return
	 */
	public ApiResp<String> setManger(@RequestBody @Valid SetManagerReq setManagerReq);
}
