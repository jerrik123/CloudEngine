package com.mangocity.ce.exception;
/**
 * 
* @ClassName: BusinessException 
* @Description: (业务处理级异常类) 
* @author 刘春元
* @date 2015年6月16日 下午1:47:34 
*
 */
public class BusinessException extends ExceptionAbstract {

	/**
	 * 
	 */
	private static final long serialVersionUID = 18985142114L;
	
	public BusinessException(Object currentObj,String errorCode, String errorMsg) {
		super(currentObj,errorCode, errorMsg,"BUSINESS");
	}
	
	public BusinessException(Object currentObj,Exception e){
		super(currentObj,e);
	}
	public BusinessException(Object currentObj,String errorCode){
		super(currentObj,errorCode, "","BUSINESS");
	}
}
