package com.sms.exception;
/** 
 * 解密异常
* @author yangxy
* @version 创建时间：2023年9月6日 下午6:56:58 
*/
public class DecryptException extends RuntimeException {
	private String errCode;
	private String msg;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public DecryptException(String errCode) {
		super(errCode);
		this.errCode = errCode;
	}

	public DecryptException(String msg, String errCode) {
		super(msg);
		this.msg = msg;
		this.errCode = errCode;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}
}
