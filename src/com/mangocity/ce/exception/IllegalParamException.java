package com.mangocity.ce.exception;

/**
* @ClassName: IllegalParamException 
* @Description: (不合法参数异常 ) 
* @author 杨洁
* @date 2015年9月08日 上午10:20:25 
 */
public class IllegalParamException  extends ExceptionAbstract {

	private static final long serialVersionUID = 189185142114L;
	
	public IllegalParamException(Object currentObj,String errorCode, String errorMsg) {
		super(currentObj,errorCode, errorMsg,"ILLEGAL PARAMETER");
	}
	
	public IllegalParamException(Object currentObj,Exception e){
		super(currentObj,e);
	}
	
	public IllegalParamException(Object currentObj,String errorCode){
		super(currentObj,errorCode, "","ILLEGAL PARAMETER");
	}
}
