package com.mangocity.ce.exception;

/**
 * 
* @ClassName: InterException 
* @Description: (接口处理级异常类 ) 
* @author 刘春元
* @date 2015年6月16日 下午1:48:15 
*
 */
public class InterException  extends ExceptionAbstract {

	/**
	 * 
	 */
	private static final long serialVersionUID = 189185142114L;
	
	public InterException(Object currentObj,String errorCode, String errorMsg) {
		super(currentObj,errorCode, errorMsg,"INTER");
	}
	
	public InterException(Object currentObj,Exception e){
		super(currentObj,e);
	}
	public InterException(Object currentObj,String errorCode){
		super(currentObj,errorCode, "","INTER");
	}
}
