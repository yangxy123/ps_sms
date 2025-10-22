package com.sms.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* @author yangxy
* @version 创建时间：2025年10月22日 上午8:39:19 
*/
@Data
public class LoginUserDto {
	private Long userId;
	
	@ApiModelProperty(value = "昵称")
	private String nickName;

	@ApiModelProperty(value = "电话号码")
	private String telPhone;

	@ApiModelProperty(value = "是否管理员(1是，0否)")
	private Integer isManger;
	
	@ApiModelProperty(value = "用户类型(1客户端用户，2管理端用户)")
	private Integer type;
}
