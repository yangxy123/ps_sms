package com.sms.base.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* @author yangxy
* @version 创建时间：2023年7月25日 下午5:10:09 
*/
@Data
public class BaseEntity {
	@TableId(type = IdType.ASSIGN_ID)
    private Long id;
	
	@ApiModelProperty(value = "创建时间")
	@TableField(fill=FieldFill.INSERT)
	private Date createdAt;
	
	@ApiModelProperty(value = "更新时间")
	@TableField(fill= FieldFill.INSERT_UPDATE)
	private Date updatedAt;
}
