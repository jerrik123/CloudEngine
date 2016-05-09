package com.mangocity.ce.book;

/**
 * 
* @ClassName: SysBook 
* @Description: (系统级字典) 
* @author 刘春元
* @date 2015年6月18日 上午9:25:25 
*
 */
public class SysBook {
	/*
	 * 配置文件相关
	 */
	public static final String ANDROID = "Android";
	public static final String IOS = "iOS";
	public static final String APPID = "appid";
	//各种开关
	public static final String ON = "on";
	public static final String OFF = "off";
	// 取中间的字符串"(?=[^//]+$).+(?=\\.)"
	public static final String REX_STRSPLIT = "(?=[^/|\\\\]+$).+(?=\\.)";
//	public static final String REX_STRSPLIT = "(?=[^\\\\]+$).+(?=\\.)";
	//public static final String REX_STRSPLIT = "(?=[^//]+$).+(?=\\.)";
	// 取中间的字符串"(?=[^//]+$).+(?=\\.)"
	//public static final String REX_STRHTML = "(?<=\\>)(.+?)(?=\\<)";
	public static final String REX_STRHTML = "(?<=\\>)(.+?)(?=\\<)";
	//<img src="  ">
	public static final String REX_STRHTMLIMG = "(?<=\\<img src=\")(.+?)(?=\"\\>)";
	//指令
	public static final String C_CONF = "moblie_c.+.xml";
	//指令获取
	public static final String C_CONF_KEY = "C_CONF_KEY";
	//流程
	public static final String W_CONF = "moblie_w.+.xml";
	//流程获取
	public static final String W_CONF_KEY = "W_CONF_KEY";
	//WEB分发
	public static final String A_CONF = "moblie_a.+.xml";
	//WEB分发获取
	public static final String A_CONF_KEY = "A_CONF_KEY";
	
	//属性文件一一对应
	public static final String DB = "db";
	
	public static final String MQTT = "mqtt";
	
	public static final String SYSTEM = "system";
	public static final String BUSINESS = "business";
	public static final String DATACONVERTPLUGIN="dataconvertplugin";
	public static final String RECEIVEPLUGIN="receiveplugin";
	/*
	 * 请求处理相关
	 */
	//http请求
	public static final String STRING = "string";
	//http请求
	public static final String HEAD = "head";
	//http请求
	public static final String DATA = "data";
	//失败与否
	public static final String SUCCESS = "00000";
	//自定义异常错误代码
	public static final String CODE = "code";
	//自定义异常错误描述
	public static final String DESC = "desc";
	/*
	 * 数据库相关
	 */
	//增删改查
	public static final String C = "C";
	public static final String U = "U";
	public static final String R = "R";
	public static final String D = "D";
}
