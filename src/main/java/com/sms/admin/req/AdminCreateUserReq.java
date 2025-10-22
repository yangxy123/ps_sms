package com.sms.admin.req;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* @author yangxy
* @version 创建时间：2025年10月22日 下午12:55:03 
*/
@Data
public class AdminCreateUserReq {
	@NotBlank(message = "用户名不能为空")
	@ApiModelProperty(value = "用户名")
	private String userName;

	@NotBlank(message = "密码不能为空")
	@ApiModelProperty(value = "密码")
	private String passWord;

	@NotBlank(message = "联系电话不能为空")
	@ApiModelProperty(value = "联系电话")
	private String telPhone;

	@NotNull(message = "超级管理员不能为空")
	@ApiModelProperty(value = "超级管理员（1是，0否）")
	private Integer isAdmin;
}
