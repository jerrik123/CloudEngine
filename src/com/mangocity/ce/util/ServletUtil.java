package com.mangocity.ce.util;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;


public class ServletUtil {
	public static String getOS(String userAgent) throws Exception {
		if(userAgent.indexOf("Windows NT 5.1") > 0)  return "Windows XP";
        if(userAgent.indexOf("Windows 98") > 0)  return "Windows 98";
        if(userAgent.indexOf("Windows NT 5.0") > 0)  return "Windows 2000";
        if(userAgent.indexOf("Windows NT 10.0") > 0)  return "Windows 10";
        if(userAgent.indexOf("Linux") > 0)   return "Linux";
        if(userAgent.indexOf("Unix") > 0)    return "Unix";
        if(userAgent.indexOf("Mac OS") > 0)    return "Mac OS";
        return "unKnown";
	}
	 /**
     * @param userAgent
     * @return 客户端浏览器信息
     */
	public static String getNavigator(String userAgent) {
        if(userAgent.indexOf("TencentTraveler") > 0) return "Tencent browser";
        if(userAgent.indexOf("Maxthon") > 0) return "Maxthon browser";
        if(userAgent.indexOf("MyIE2") > 0)   return "MyIE2 browser";
        if(userAgent.indexOf("Firefox") > 0) return "Firefox browser";
        if(userAgent.indexOf("MSIE") > 0)    return "IE browser";
        return "unKnown browser";
    }
	  /**
     * @param accept
     * @return 客户端浏览器接受的文件类型
     */
	public static String getAccept(String accept){
        StringBuffer buffer = new StringBuffer();
        if(accept.contains("image/gif"))    buffer.append("GIF File, ");
        if(accept.contains("image/x-xbitmap"))  buffer.append("BMP File, ");
        if(accept.contains("image/jpeg"))   buffer.append("JPG File, ");
        if(accept.contains("application/vnd.ms-excel")) buffer.append("Excel File, ");
        if(accept.contains("application/vnd.ms-powerpoint"))    buffer.append("PPT File, ");
        if(accept.contains("application/msword"))   buffer.append("Word File, ");
        return buffer.toString().replaceAll(", $", "");
    }
    /**
     * @param locale
     * @return 语言环境名称
     */
	public static String getLocale(Locale locale) {
        if(Locale.SIMPLIFIED_CHINESE.equals(locale))    return "Simple Chinese";
        if(Locale.TRADITIONAL_CHINESE.equals(locale))   return "Traditional Chinese";
        if(Locale.ENGLISH.equals(locale))               return "English";
        if(Locale.JAPANESE.equals(locale))              return "Japanese";
        return "unKnown language";
    }
	
	public static String getRemoteAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
 
   
}
