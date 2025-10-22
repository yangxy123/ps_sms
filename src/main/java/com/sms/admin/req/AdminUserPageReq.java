package com.sms.admin.req;

import com.sms.base.req.BasePageReq;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* @author yangxy
* @version 创建时间：2025年10月22日 下午12:23:15 
*/
@Data
public class AdminUserPageReq extends BasePageReq {
	@ApiModelProperty(value = "用户名")
	private String userName;

	@ApiModelProperty(value = "联系电话")
	private String telPhone;

	@ApiModelProperty(value = "超级管理员（1是，0否）")
	private Integer isAdmin;
}
