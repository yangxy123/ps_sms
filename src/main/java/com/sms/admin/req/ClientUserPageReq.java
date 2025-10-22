package com.sms.admin.req;

import com.sms.base.req.BasePageReq;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* @author yangxy
* @version 创建时间：2025年10月22日 上午10:47:01 
*/
@Data
public class ClientUserPageReq extends BasePageReq {
	@ApiModelProperty(value = "电话号码")
	private String telPhone;

	@ApiModelProperty(value = "昵称")
	private String nickeName;

	@ApiModelProperty(value = "是否管理员(1是，0否)")
	private Integer isManger;
}
