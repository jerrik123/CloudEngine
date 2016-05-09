package com.mangocity.ce.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import com.mangocity.ce.exception.IllegalParamException;

public class ValidationUtil {

	/**
	 * 如果numericA数字大于numericB,则返回1
	 * 等于则返回0
	 * 小于则返回-1
	 * 如果存在非数字类型的参数,则返回-2
	 * @param numericA
	 * @param numericB
	 * @return
	 */
	public static long compareNumericStr(String numericA,String numericB){
		if(CommonUtils.isBlank(numericA) || CommonUtils.isBlank(numericB)){
			return -2;
		}
		if(!CommonUtils.isNumeric(numericA) || !CommonUtils.isNumeric(numericB)){
			return -2;
		}
		long numberA = Long.parseLong(numericA);
		long numberB = Long.parseLong(numericB);
		return (numberA-numberB);
	}
	
	/**
	 * 长度在是否在指定范围
	 * @param val for:loginPwd
	 * @param range for:[6,12]
	 * @throws IllegalParamException 
	 */
	public static boolean isRangeLenFor(List<Object> paramList,String val,String range) throws IllegalParamException{
		AssertUtils.assertBlank(val);
		AssertUtils.assertBlank(range);
		if(!range.startsWith("[") || !range.endsWith("]")){//抛出不合法异常
			return false;
		}
		range = range.replace("[", "").replace("]", "");
		String[] ranges = range.split(",");
		long beginNum = 0;
		long endNum = 0;
		int len = val.length();
		if(ranges.length == 2 && !range.startsWith(",")){
			beginNum = Long.parseLong(ranges[0]);
			endNum = Long.parseLong(ranges[1]);
			paramList.add(beginNum);
			paramList.add(endNum);
			return len>=beginNum && len<=endNum;
		}else if(ranges.length == 2 && range.startsWith(",")){
			endNum = Long.parseLong(ranges[1]);
			paramList.add("-∞");
			paramList.add(endNum);
			return (val.length()<=endNum);
		}else if(ranges.length == 1 && range.endsWith(",")){
			beginNum = Long.parseLong(ranges[0]);
			paramList.add(beginNum);
			paramList.add("+∞");
			if(len>=beginNum){
				return true;
			}else{
				return false;
			}
		}
		return false;
	}
	
	/**
	 * 只能取指定值
	 * @param val A
	 * @param range for:A,B,C
	 * 例如:String registerWay = "AC";
		String range = "[A,B,C,BC,ABC]";
		System.out.println(isRangeFor(registerWay, range));//false
	 * @return
	 * @throws IllegalParamException 
	 */
	public static boolean isRangeFor(String val,String range) throws IllegalParamException{
		val = CommonUtils.nullToEmpty(val);
		AssertUtils.assertBlank(range);
		if(!range.startsWith("[") || !range.endsWith("]")){//抛出不合法异常
			return false;
		}
		range = range.replace("[", "").replace("]", "");
		String[] ranges = range.split(",");
		Set<String> set = new HashSet<String>(Arrays.asList(ranges));
		if(set.contains(val)){
			return true;
		}
		return false;
	}
	
	/**
	 * 没有使用!isMoreThan,因为边界数值被包含进来
	 * @param val
	 * @param value
	 * @return
	 * @throws IllegalParamException
	 */
	public static boolean isLessThan(String val,String value) throws IllegalParamException{
		AssertUtils.assertBlank(val);
		AssertUtils.assertBlank(value);
		long num = Long.parseLong(val);
		long values = Long.parseLong(value);
		return (num<=values?true:false);
	}
	
	public static boolean isMoreThan(String param,String value) throws IllegalParamException{
		AssertUtils.assertBlank(param);
		AssertUtils.assertBlank(value);
		Long num = Long.parseLong(param);
		Long values =  Long.parseLong(value);
		return (num>=values?true:false);
	}
	
	public static boolean isMaxLen(String param,String value) throws IllegalParamException{
		AssertUtils.assertBlank(param);
		AssertUtils.assertBlank(value);
		return param.length()<=Long.parseLong(value);
	}
	
	public static boolean isMinLen(String param,String value) throws IllegalParamException{
		AssertUtils.assertBlank(param);
		AssertUtils.assertBlank(value);
		return param.length()>=Long.parseLong(value);
	}
	
	/**
	 * 正则匹配 
	 * @param paramValue
	 * @param checkValue
	 * @return
	 * @throws IllegalParamException
	 */
	public static boolean regexMatch(String paramValue,String checkValue) throws IllegalParamException{
		AssertUtils.assertBlank(paramValue);
		AssertUtils.assertBlank(checkValue);
		Pattern pattern = Pattern.compile(checkValue);
		if(null == pattern){
			throw new RuntimeException(checkValue + " is not avalid regex.");
		}
		return pattern.matcher(paramValue).matches();
	}
	
}
