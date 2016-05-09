package com.mangocity.ce.validation;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.mangocity.ce.exception.IllegalParamException;
import com.mangocity.ce.util.CommonUtils;
import com.mangocity.ce.util.ValidationUtil;

public class MultiValidator {

	/**
	 * 是否为手机号码
	 * 
	 * @param mobileNo
	 * @return true or false
	 */
	public static boolean isMobilePhone(String[] mobileNoArr) {
		boolean flag = false;
		for (String mobileNo : mobileNoArr) {
			flag = CommonUtils.isMobilePhone(mobileNo);
			if(!flag) return flag;
		}
		return true;
	}

	/**
	 * 是否为IP
	 * 
	 * @param ip
	 * @return true or false
	 */
	public static boolean isIP(String[] ipArr) {
		boolean flag = false;
		for (String ip : ipArr) {
			flag = CommonUtils.isIP(ip);
			if(!flag) return flag;
		}
		return true;
	}

	/**
	 * 邮箱是否合法
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String[] emailArr) {
		boolean flag = false;
		for (String email : emailArr) {
			flag = CommonUtils.isEmail(email);
			if(!flag) return flag;
		}
		return true;
	}
	
	/**
	 * 判断是否为空白
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @return <code>true</code> if the String is null, empty or whitespace
	 */
	public static boolean isNotBlank(String[] strArr) {
		boolean flag = false;
		for (String str : strArr) {
			flag = CommonUtils.isNotBlank(str);
			if(!flag) return flag;
		}
		return true;
	}

	/**
	 * 是否是数字字符串(可正可负)
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String[] strArr) {
		boolean flag = false;
		for (String str : strArr) {
			flag = CommonUtils.isNumeric(str);
			if(!flag) return flag;
		}
		return true;
	}
	
	public static boolean parseDate(String[] strArr){
		Date date = null;
		boolean flag = false;
		for (String str : strArr) {
			try {
				date = CommonUtils.parseDate(str,"yyyy-MM-dd");
			} catch (ParseException e) {
				flag = true;
			}
			if(flag) return false;
		}
		return true;
	}
	
	public static boolean isRangeFor(String[] val,String range) throws IllegalParamException{
		boolean flag = false;
		for (String str : val) {
			flag = ValidationUtil.isRangeFor(str,range);
			if(!flag) return flag;
		}
		return true;
	}
	
	/**
	 * 没有使用!isMoreThan,因为边界数值被包含进来
	 * @param val
	 * @param value
	 * @return
	 * @throws IllegalParamException
	 */
	public static boolean isLessThan(String[] val,String value) throws IllegalParamException{
		boolean flag = false;
		for (String str : val) {
			flag = ValidationUtil.isLessThan(str,value);
			if(!flag) return flag;
		}
		return true;
	}
	
	public static boolean isMoreThan(String[] val,String value) throws IllegalParamException{
		boolean flag = false;
		for (String str : val) {
			flag = ValidationUtil.isMoreThan(str,value);
			if(!flag) return flag;
		}
		return true;
	}
	
	public static boolean isMaxLen(String[] val,String value) throws IllegalParamException{
		boolean flag = false;
		for (String str : val) {
			flag = ValidationUtil.isMaxLen(str,value);
			if(!flag) return flag;
		}
		return true;
	}
	
	public static boolean isMinLen(String[] val,String value) throws IllegalParamException{
		boolean flag = false;
		for (String str : val) {
			flag = ValidationUtil.isMinLen(str,value);
			if(!flag) return flag;
		}
		return true;
	}
	
	/**
	 * 正则匹配 
	 * @param paramValue
	 * @param checkValue
	 * @return
	 * @throws IllegalParamException
	 */
	public static boolean regexMatch(String[] val,String checkValue) throws IllegalParamException{
		boolean flag = false;
		for (String str : val) {
			flag = ValidationUtil.regexMatch(str,checkValue);
			if(!flag) return flag;
		}
		return true;
	}
	
	public static boolean isRangeLenFor(List<Object> paramList,String[] val,String range) throws IllegalParamException{
		boolean flag = false;
		for (String str : val) {
			flag = ValidationUtil.isRangeLenFor(paramList,str,range);
			if(!flag) return flag;
		}
		return true;
	}

}
