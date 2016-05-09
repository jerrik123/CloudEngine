package com.mangocity.ce.util;

import org.apache.log4j.Logger;

import com.mangocity.ce.exception.IllegalParamException;

/**
 * 参数容错校验工具类
 */
public class AssertUtils {

	private static Logger log = Logger.getLogger(AssertUtils.class);
	
	public static void assertNull(Object object, String message) throws IllegalParamException {
		if (null == object) {
			log.debug("object: " + object);
			if(CommonUtils.isBlank(message)){
				message = "[AssertUtils failed] - the object argument must not be null";
			}
			log.debug(message);
			throw new IllegalParamException("","Assert Code-01",message);
		}
	}

	public static void assertNull(Object object) throws IllegalParamException {
		assertNull(object, "[AssertUtils failed] - the object argument must not be null");
	}
	
	public static void assertNullArray(Object[] arr,String message) throws IllegalParamException {
		if(null != arr){
			for (Object obj : arr) {
				assertNull(obj,message);
			}
		}else{
			assertNull("Object Array", "[AssertUtils failed] - the object array argument must not be null");
		}
	}

	public static void assertEmpty(Object[] array, String message) throws IllegalParamException {
		if (null == array || array.length == 0 ) {
			log.debug("Object[]: " + array);
			log.debug(message);
			throw new IllegalParamException("","Assert Code-02",message);
		}
	}

	public static void assertEmpty(Object[] array) throws IllegalParamException {
		assertEmpty(array, "[AssertUtils failed] - this array must not be empty: it must contain at least 1 element");
	}
	
	public static void assertBlank(String str, String message) throws IllegalParamException {
		if (null == str || "".equals(str.trim())) {
			log.debug("String: " + str);
			log.debug(message);
			throw new IllegalParamException("","Assert Code-03",message);
		}
	}

	public static void assertBlank(String str) throws IllegalParamException {
		assertBlank(str, "[AssertUtils failed] - this String must not be blank");
	}
	
	public static void main(String[] args) throws IllegalParamException {
		assertNull(null,"参数不能为空");
	}
}
