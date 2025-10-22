package com.sms.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sms.admin.entity.AdminRobotEntity;
import com.sms.admin.req.AdminRobotAddReq;
import com.sms.admin.req.AdminRobotEditReq;
import com.sms.admin.req.AdminRobotPageReq;
import com.sms.base.resp.ApiResp;
import com.sms.base.resp.PageResp;

import io.swagger.annotations.ApiOperation;

/** 
* @author yangxy
* @version 创建时间：2025年10月22日 下午1:08:11 
*/
public interface AdminRobotService extends IService<AdminRobotEntity> {
	/**
	 * 分页查询
	* @author yangxy
	* @version 创建时间：2025年10月22日 下午1:17:55 
	* @param adminRobotPageReq
	* @return
	 */
	public ApiResp<PageResp<AdminRobotEntity>> page(AdminRobotPageReq adminRobotPageReq);
	
	/**
	 * 新增
	* @author yangxy
	* @version 创建时间：2025年10月22日 下午1:17:58 
	* @param adminRobotAddReq
	* @return
	 */
	public ApiResp<String> add(AdminRobotAddReq adminRobotAddReq);
	
	/**
	 * 修改
	* @author yangxy
	* @version 创建时间：2025年10月22日 下午1:18:02 
	* @param adminRobotEditReq
	* @return
	 */
	public ApiResp<String> edit(AdminRobotEditReq adminRobotEditReq);
	
	/**
	 * 删除
	* @author yangxy
	* @version 创建时间：2025年10月22日 下午1:18:06 
	* @param id
	* @return
	 */
	public ApiResp<String> del(Long id);
	
	/**
	 * 刷新机器人缓存
	* @author yangxy
	* @version 创建时间：2025年10月22日 下午1:28:03 
	* @return
	 */
	public ApiResp<String> refreshCache();
}
