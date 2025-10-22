package com.sms.client.req;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* @author yangxy
* @version 创建时间：2025年10月22日 上午9:23:48 
*/
@Data
public class ClientLoginReq {
	@NotBlank(message = "电话号码不能为空")
	@ApiModelProperty(value = "电话号码",required = true)
	private String telPhone;

	@NotBlank(message = "密码不能为空")
	@ApiModelProperty(value = "密码",required = true)
	private String passWord;
}
