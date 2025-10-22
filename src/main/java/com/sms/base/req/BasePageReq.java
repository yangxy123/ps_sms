package com.sms.base.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/** 
* @author yangxy
* @version 创建时间：2023年7月27日 下午6:00:51 
*/
@Data
public class BasePageReq {
	@NotNull(message = "thenumberofpagescannotbeempty")
	@ApiModelProperty(value = "页数", required = true)
	private Integer pageNo = 1;

	@NotNull(message = "thenumberofdisplayeditemsonthecurrentpagecannotbeempty")
	@ApiModelProperty(value = "当前页面显示条数", required = true)
	private Integer pageSize = 10;
}
