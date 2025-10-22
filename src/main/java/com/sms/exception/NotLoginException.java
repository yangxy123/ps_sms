package com.sms.exception;
/** 
 * 业务操作异常
* @author yangxy
* @version 创建时间：2023年7月25日 下午3:45:35 
*/
public class NotLoginException extends RuntimeException {
	private String msg;

	public NotLoginException(String msg) {
		super(msg);
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
