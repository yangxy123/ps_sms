package com.sms.exception;
/** 
 * jwt异常
* @author yangxy
* @version 创建时间：2023年7月26日 上午10:46:03 
*/
public class JWTExcepiton extends RuntimeException {
	public String msg;

	public JWTExcepiton(String msg) {
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
