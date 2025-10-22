package com.sms.exception;
/** 
 * api参数异常
* @author yangxy
* @version 创建时间：2023年9月7日 上午10:02:14 
*/
public class ParamException extends RuntimeException {
	private String msg;
	
	public ParamException(String msg) {
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
