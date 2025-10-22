package com.sms.admin.req;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* @author yangxy
* @version 创建时间：2025年10月22日 下午1:11:37 
*/
@Data
public class AdminRobotAddReq {
	@NotBlank(message = "关键词不能为空")
	@ApiModelProperty(value = "关键词")
	@Size(max = 100,message = "关键词最大长度不能超过100个字符")
	private String keyWord;

	@NotBlank(message = "回复内容不能为空")
	@ApiModelProperty(value = "回复内容")
	private String reply;

	@NotNull(message = "状态不能为空")
	@ApiModelProperty(value = "状态（1启用，0停用）")
	private Integer status;
}
