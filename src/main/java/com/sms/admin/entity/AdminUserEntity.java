package com.sms.admin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.sms.base.entity.BaseEntity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* @author yangxy
* @version 创建时间：2025年10月22日 下午12:18:53 
*/
@Data
@TableName("ps_admin_user")
public class AdminUserEntity extends BaseEntity {
	@ApiModelProperty(value = "用户名")
	private String userName;

	@ApiModelProperty(value = "密码")
	private String passWord;

	@ApiModelProperty(value = "联系电话")
	private String telPhone;

	@ApiModelProperty(value = "超级管理员（1是，0否）")
	private Integer isAdmin;
}
