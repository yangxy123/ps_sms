package com.sms.admin.req;

import com.sms.base.req.BasePageReq;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* @author yangxy
* @version 创建时间：2025年10月22日 下午1:10:05 
*/
@Data
public class AdminRobotPageReq extends BasePageReq {
	@ApiModelProperty(value = "关键词")
	private String keyWord;

	@ApiModelProperty(value = "状态（1启用，0停用）")
	private Integer status;
}
