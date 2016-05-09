package com.mangocity.ce.exception;

/**
 * 
* @ClassName: DatabaseException 
* @Description: (数据库处理级异常类 ) 
* @author 刘春元
* @date 2015年6月16日 下午1:47:45 
*
 */
public class DatabaseException extends ExceptionAbstract{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1898542114L;
	
	public DatabaseException(Object currentObj,String errorCode, String errorMsg) {
		super(currentObj,errorCode, errorMsg,"DATABASE");
	}
	
	public DatabaseException(Object currentObj,Exception e){
		super(currentObj,e);
	}
	public DatabaseException(Object currentObj,String errorCode){
		super(currentObj,errorCode, "","DATABASE");
	}




}
