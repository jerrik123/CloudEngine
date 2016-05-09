package com.mangocity.ce.bean;

import com.mangocity.ce.book.SysBook;

/**
 * 
* @ClassName: ResultBean 
* @Description: TODO(返回bean) 
* @author Syungen
* @date 2015年8月25日 下午2:24:50 
*
 */
public class ResultBean {
	private String resultCode = SysBook.SUCCESS;// 默认成功
	private Object result = "";
	private String errorType = "-1";
	private String errorMag = "-1";

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public String getErrorType() {
		return errorType;
	}

	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}

	public String getErrorMag() {
		return errorMag;
	}

	public void setErrorMag(String errorMag) {
		this.errorMag = errorMag;
	}

}
