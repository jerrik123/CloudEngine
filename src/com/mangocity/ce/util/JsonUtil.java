package com.mangocity.ce.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.mangocity.ce.book.ErrorBook;
import com.mangocity.ce.exception.IllegalParamException;

/**
 * 
 * @ClassName: JsonUtil
 * @Description: TODO(json工具类)
 * @author Syungen
 * @date 2015年8月25日 下午2:50:54
 * 
 */
public class JsonUtil {
	private static final Logger logger = Logger.getLogger(JsonUtil.class
			.getName());

	/**
	 * json转map
	 * 
	 * @param cmd_string
	 * @return 作者:刘春元 2013-5-13
	 * @throws IllegalParamException
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> decodeCmd(String cmd_string)
			throws IllegalParamException {
		Map<String, Object> valueMap = new HashMap<String, Object>();
		try {
			valueMap = json2Map(cmd_string);
		} catch (Exception e) {
			logger.error("string to map  error" + e.getMessage());
			throw new IllegalParamException("", ErrorBook.PARAM_ESS_ERROR,
					"请填写正确格式的请求参数(JSON)");
		}
		return valueMap;
	}

	public static JSONObject getJSON(String jsonString)
			throws IllegalParamException {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = jsonObject.parseObject(jsonString);
		} catch (Exception e) {
			logger.error("string to json object error" + e.getMessage());
			throw new IllegalParamException("", ErrorBook.PARAM_ESS_ERROR,
					"请填写正确格式的请求参数(JSON)");
		}
		return jsonObject;
	}

	/**
	 * map转json
	 * 
	 * @param cmd
	 * @return 作者:刘春元 2013-5-13
	 */
	public static String encodeCmd(Map<String, Object> cmd) {
		String json = "";
		if (null != cmd) {
			json = JSONObject.toJSON(cmd).toString();
		}
		return json;
	}

	public static Map<String, Object> json2Map(String jsonStr) {
		return (Map<String, Object>) JSON.parseObject(jsonStr);
	}

	public static void main(String[] args) throws IllegalParamException {
		String jsonStr = "{\"ok\":\"yeah\",\"map\":{\"map1\":{\"list\":[{\"hello\":\"world\"}]}}}";
		Map<String, Object> map = json2Map(jsonStr);
		System.out.println(decodeCmd(jsonStr));
	}
}
