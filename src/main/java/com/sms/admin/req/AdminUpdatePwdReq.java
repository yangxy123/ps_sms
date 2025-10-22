package com.sms.admin.req;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* @author yangxy
* @version 创建时间：2025年10月22日 下午12:28:53 
*/
@Data
public class AdminUpdatePwdReq {
	@NotBlank(message = "旧密码不能为空")
	@ApiModelProperty(value = "旧密码",required = true)
	private String oldPassWord;

	@NotBlank(message = "新密码不能为空")
	@ApiModelProperty(value = "新密码",required = true)
	private String newPassWord;

	@NotBlank(message = "新密码确认不能为空")
	@ApiModelProperty(value = "新密码确认",required = true)
	private String newPassWordConfirm;
}
