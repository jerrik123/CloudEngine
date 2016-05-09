package com.mangocity.ce.book;
/**
 * 
* @ClassName: ErrorBook 
* @Description: (错误编码字典定义类) 
* @author 刘春元
* @date 2015年6月18日 上午9:25:01 
*
 */
public class ErrorBook {
	/**
	 * 系统级其它异常
	 */
    public static final String OTHER_ERROR="ES99";
    /**
     * 读取文件异常
     */
    public static final String READ_FILE_ERROR="ES01";
    
    /**
     * 写入文件异常
     */
    public static final String WRITE_FILE_ERROR="ES02";
    
    /**
     * 没有权限异常
     */
    public static final String NOT_TO_RULE_ERROR="ESR01";
    
    /**
     * 类加载异常
     */
    public static final String NOT_CLASS_ERROR="ES04";
    
    /**
     * 计算公式异常
     */
    public static final String EVAL_MATH_ERROR="ES05";
    
    /**
     * 参数异常
     */
    public static final String PARAM_ESS_ERROR="ES06";
    
    /**
     * 客户端调用方错误
     */
    public static final String CLIENT_CALL_ERROR="ES07";
}
