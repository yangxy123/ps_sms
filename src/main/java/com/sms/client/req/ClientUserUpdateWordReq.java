package com.sms.client.req;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* @author yangxy
* @version 创建时间：2025年10月22日 上午9:36:08 
*/
@Data
public class ClientUserUpdateWordReq {
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
