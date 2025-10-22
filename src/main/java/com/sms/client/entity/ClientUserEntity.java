package com.sms.client.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.sms.base.entity.BaseEntity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* @author yangxy
* @version 创建时间：2025年10月22日 上午9:09:09 
*/
@Data
@TableName("ps_client_user")
public class ClientUserEntity extends BaseEntity{
	@ApiModelProperty(value = "电话号码")
	private String telPhone;

	@ApiModelProperty(value = "昵称")
	private String nickeName;

	@ApiModelProperty(value = "是否管理员(1是，0否)")
	private Integer isManger;
	
	@ApiModelProperty(value = "密码")
	private String passWord;
}
