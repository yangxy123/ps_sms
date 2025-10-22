package com.sms.admin.req;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* @author yangxy
* @version 创建时间：2025年10月22日 下午12:26:12 
*/
@Data
public class AdminLoginReq {
	@NotBlank(message = "用户名不能为空")
	@ApiModelProperty(value = "用户名",required = true)
	private String userName;

	@NotBlank(message = "密码不能为空")
	@ApiModelProperty(value = "密码",required = true)
	private String passWord;
}
