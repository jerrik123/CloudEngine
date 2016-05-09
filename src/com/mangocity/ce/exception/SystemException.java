package com.mangocity.ce.exception;

/**
 * 
* @ClassName: SystemException 
* @Description: (系统级处理级异常类 ) 
* @author 刘春元
* @date 2015年6月16日 下午1:48:25 
*
 */
public class SystemException  extends ExceptionAbstract {

	/**
	 * 
	 */
	private static final long serialVersionUID = 189185142114L;
	
	public SystemException(Object currentObj,String errorCode, String errorMsg) {
		super(currentObj,errorCode, errorMsg,"SYS");
	}
	
	public SystemException(Object currentObj,Exception e){
		super(currentObj,e);
	}
	public SystemException(Object currentObj,String errorCode){
		super(currentObj,errorCode, "","SYS");
	}
}
