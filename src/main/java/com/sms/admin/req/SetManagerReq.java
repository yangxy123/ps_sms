package com.sms.admin.req;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* @author yangxy
* @version 创建时间：2025年10月22日 上午10:50:38 
*/
@Data
public class SetManagerReq {
	@NotNull(message = "客户端用户ID不能为空")
	@ApiModelProperty(value = "客户端用户ID",required = true)
	private Long userId;
	
	@NotNull(message = "操作类型不能为空")
	@ApiModelProperty(value = "操作类型(1设置为管理员，2取消管理员)",required = true)
	private Integer type;
}
