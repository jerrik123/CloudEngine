package com.mangocity.ce.book;
/**
 * 
* @ClassName: FileBook 
* @Description: (取文件的匹配配置字典) 
* @author 刘春元
* @date 2015年6月18日 上午9:25:15 
*
 */
public class FileBook {
	/**
     * sys文件 配置regex
     */
    public static final String SYS_REGEX_BOOK="sys_.+.properties";
    
    /**
     * ACTION文件 配置regex
     */
    public static final String SYS_ACTION_REGEX_BOOK="SYS_ACTION_REGEX_BOOK";
    
    /**
     * 拦截器文件 配置regex
     */
    public static final String SYS_INTERCEPTOR_REGEX_BOOK="SYS_INTERCEPTOR_REGEX_BOOK";
    
    /**
     * Depend文件 配置regex
     */
    public static final String SYS_DEPEND_REGEX_BOOK="SYS_DEPEND_REGEX_BOOK";
    
    /**
     * 校验文件 配置regex
     */
    public static final String SYS_CHECK_REGEX_BOOK ="SYS_CHECK_REGEX_BOOK";
    
    /**
     * 校验文件 配置regex
     */
    public static final String SYS_SQL_REGEX_BOOK ="SYS_SQL_REGEX_BOOK";
    
    /**
     * sql转换协议 配置regex
     */
    public static final String SYS_SQLCVERTER_REGEX_BOOK ="SYS_SQLCVERTER_REGEX_BOOK";
    
    /**
     * 远程调用转换协议 配置regex
     */
    public static final String SYS_REMOTE_REGEX_BOOK ="SYS_REMOTE_REGEX_BOOK";
    
    /**
     * 请求分发后缀 配置regex
     */
    public static final String SYS_DISTRIBUTION_REGEX_BOOK ="SYS_DISTRIBUTION_REGEX_BOOK";
    
    /**
     * servlet分发 配置regex
     */
    public static final String SERVLET_REGEX_BOOK="servlet_.+.properties";
}
