package com.mangocity.ce.exception;

import org.apache.log4j.Logger;

/**
 * 
* @ClassName: ExceptionAbstract 
* @Description: (异常抽像类) 
* @author 刘春元
* @date 2015年6月16日 下午1:47:55 
*
 */
public class ExceptionAbstract extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3456789009898L;
	
	private static final Logger LOG = Logger.getLogger(ExceptionAbstract.class);
	
	private String errorCode = "ES1";
	private String errorMsg = "system error.";
	private  String errorType="OTHER";
	{
		
	}

	public ExceptionAbstract(Object currentObj,String errorCode, String errorMsg,String errorType) {
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
		this.errorType = errorType;
		LOG.error("["+currentObj.getClass().getName()+"] [errorCode: " + errorCode + ",errorMsg: " + errorMsg + ",errorTyp: " + errorType +"]");
	}
	
	public ExceptionAbstract(Object currentObj,Exception e) {
		if (e instanceof ExceptionAbstract) {
			this.errorCode = ((ExceptionAbstract) e).errorCode;
			this.errorMsg = ((ExceptionAbstract) e).errorMsg;
		} else {
			this.errorMsg = e.getMessage();
			this.errorCode = "E99";
		}
		this.errorType="OTHER";
		LOG.error("["+currentObj.getClass().getName()+"] [errorCode: " + errorCode + ",errorMsg: " + errorMsg + ",errorTyp: " + errorType +"]");
	}
	

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getErrorType() {
		return errorType;
	}

	public  void setErrorType(String type){
		this.errorType = type;
	}


}
