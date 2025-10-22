package com.sms.admin.req;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* @author yangxy
* @version 创建时间：2025年10月22日 下午12:31:11 
*/
@Data
public class AdminUserSetAdminReq {
	@NotNull(message = "用户ID不能为空")
	@ApiModelProperty(value = "用户ID",required = true)
	private Long userId;

	@NotNull(message = "操作类型不能为空")
	@ApiModelProperty(value = "操作类型（1设置超级用户，2取消超级用户）",required = true)
	private Integer type;
}
