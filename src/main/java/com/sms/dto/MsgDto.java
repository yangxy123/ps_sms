package com.sms.dto;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* @author yangxy
* @version 创建时间：2025年9月29日 下午2:38:22 
*/
@Data
public class MsgDto {
	@ApiModelProperty(value = "消息类型(1文字，2图片，3文字和图片，4系统消息，5心跳消息)")
	private Integer type;
	
	@ApiModelProperty(value = "文字内容（消息类型为1、3时必填）")
	private String msg;
	
	@ApiModelProperty(value = "图片内容base64（消息类型为2,3时必填）")
	private String picture;

	@ApiModelProperty(value = "发送时间")
	private Date sendTime;

	@ApiModelProperty(value = "发送人")
	private String sender;
	

	private MsgDto(Integer type, String msg, String picture) {
		super();
		this.type = type;
		this.msg = msg;
		this.picture = picture;
	}
	
	/**
	 * 系统消息
	* @author yangxy
	* @version 创建时间：2025年9月29日 下午2:50:37 
	* @param msg
	* @return
	 */
	public static MsgDto sysMst(String msg) {
		return new MsgDto(4,msg,null);
	}

	public MsgDto() {
		super();
	}
}
