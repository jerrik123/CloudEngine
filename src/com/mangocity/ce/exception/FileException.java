package com.mangocity.ce.exception;

/**
 * 
* @ClassName: FileException 
* @Description: (文件处理级异常类) 
* @author 刘春元
* @date 2015年6月16日 下午1:48:06 
*
 */
public class FileException  extends ExceptionAbstract {

	/**
	 * 
	 */
	private static final long serialVersionUID = 189185142114L;
	
	public FileException(Object currentObj,String errorCode, String errorMsg) {
		super(currentObj,errorCode, errorMsg,"FILE");
	}
	
	public FileException(Object currentObj,Exception e){
		super(currentObj,e);
	}
	public FileException(Object currentObj,String errorCode){
		super(currentObj,errorCode, "","FILE");
	}
}
