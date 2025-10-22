package com.sms.exception;

import lombok.Getter;

/** 
* @author yangxy
* @version 创建时间：2023年9月11日 上午10:09:56 
*/
@Getter
public class ApiBussException extends RuntimeException {
	private String msg;

	public ApiBussException(String msg) {
		super(msg);
		this.msg = msg;
	}
}
