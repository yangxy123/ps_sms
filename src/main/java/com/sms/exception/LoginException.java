package com.sms.exception;
/** 
 * 登录异常
* @author yangxy
* @version 创建时间：2023年7月25日 下午3:41:29 
*/
public class LoginException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String msg;

	public LoginException(String msg) {
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
