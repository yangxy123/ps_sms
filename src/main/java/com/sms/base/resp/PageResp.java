package com.sms.base.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @param <T>
 * @author yangxy
 * @version 创建时间：2023年8月14日 下午12:46:05
 */
@Data
public class PageResp<T> {
	@ApiModelProperty(value = "总条数")
	private Integer total;

	@ApiModelProperty(value = "显示条数")
	private Integer pageSize;

	@ApiModelProperty(value = "当前页码")
	private Integer pageNo;

	@ApiModelProperty(value = "分页结果集")
	private T data;
}
