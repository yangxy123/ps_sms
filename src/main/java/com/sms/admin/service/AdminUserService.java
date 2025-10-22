package com.sms.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sms.admin.entity.AdminUserEntity;
import com.sms.admin.req.AdminCreateUserReq;
import com.sms.admin.req.AdminLoginReq;
import com.sms.admin.req.AdminUpdatePwdReq;
import com.sms.admin.req.AdminUserPageReq;
import com.sms.admin.req.AdminUserSetAdminReq;
import com.sms.base.resp.ApiResp;
import com.sms.base.resp.PageResp;
import com.sms.client.resp.LoginResp;

import io.swagger.annotations.ApiOperation;

/** 
* @author yangxy
* @version 创建时间：2025年10月22日 下午12:21:09 
*/
public interface AdminUserService extends IService<AdminUserEntity> {
	/**
	 * 分页查询
	* @author yangxy
	* @version 创建时间：2025年10月22日 下午12:35:56 
	* @param adminUserPageReq
	* @return
	 */
	public ApiResp<PageResp<AdminUserEntity>> page(AdminUserPageReq adminUserPageReq);
	
	/**
	 * 登录
	* @author yangxy
	* @version 创建时间：2025年10月22日 下午12:35:59 
	* @param adminLoginReq
	* @return
	 */
	public ApiResp<LoginResp> login(AdminLoginReq adminLoginReq);

	/**
	 * 登出
	* @author yangxy
	* @version 创建时间：2025年10月22日 下午12:36:03 
	* @return
	 */
	public ApiResp<String> logout();

	/**
	 * 修改密码
	* @author yangxy
	* @version 创建时间：2025年10月22日 下午12:36:24 
	* @param adminUpdatePwdReq
	* @return
	 */
	public ApiResp<String> updatePwd(AdminUpdatePwdReq adminUpdatePwdReq);
	

	/**
	 * 设置/取消超级管理员
	* @author yangxy
	* @version 创建时间：2025年10月22日 下午12:36:27 
	* @param adminUserSetAdminReq
	* @return
	 */
	public ApiResp<String> setAdmin(AdminUserSetAdminReq adminUserSetAdminReq);
	
	/**
	 * 删除用户
	* @author yangxy
	* @version 创建时间：2025年10月22日 下午12:36:31 
	* @param id
	* @return
	 */
	public ApiResp<String> del(Long id);
	
	/**
	 * 创建用户
	* @author yangxy
	* @version 创建时间：2025年10月22日 下午12:56:27 
	* @param adminCreateUserReq
	* @return
	 */
	public ApiResp<String> createUser(AdminCreateUserReq adminCreateUserReq);
}
