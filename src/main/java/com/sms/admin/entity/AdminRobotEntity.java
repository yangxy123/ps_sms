package com.sms.admin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.sms.base.entity.BaseEntity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* @author yangxy
* @version 创建时间：2025年10月22日 下午1:05:34 
*/
@Data
@TableName("ps_admin_robot")
public class AdminRobotEntity extends BaseEntity {
	@ApiModelProperty(value = "关键词")
	private String keyWord;

	@ApiModelProperty(value = "回复内容")
	private String reply;

	@ApiModelProperty(value = "状态（1启用，0停用）")
	private Integer status;
}
