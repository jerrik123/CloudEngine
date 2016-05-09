package com.mangocity.ce.validation;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mangocity.ce.exception.IllegalParamException;
import com.mangocity.ce.util.AssertUtils;
import com.mangocity.ce.util.New;

public class CheckLevelResolver {

	/** 分割符 */
	private static final String SEPRATOR_CHAR = "\\.";
	
	public static final String VALUE_KEY = "_val";
	
	public static final String NULL_VALUE_KEY = "_nullValCount";

	/**
	 * 解析Map中的level {"headMap":{"passList":["":""]}}
	 * 
	 * @param headMap
	 * @param level
	 * @param fieldName
	 * @return
	 * @throws IllegalParamException
	 */
	public  Map<String,Object> resolve(Map<String, Object> headMap, String level,String fieldName)
			throws IllegalParamException {
		AssertUtils.assertNull(headMap);
		AssertUtils.assertBlank(level);
		AssertUtils.assertBlank(fieldName);
		StringBuilder sb =  new StringBuilder(level).append(".").append(fieldName);
		List<String> outList = New.list();
		Map<String,Object> resultMap = New.map();
		AtomicLong nullValCounter = new AtomicLong(0);
		outList = recursiveJSON((JSONObject)headMap, sb.toString().split(SEPRATOR_CHAR), outList,nullValCounter);
		sb = new StringBuilder();
		for(Object obj : outList){
			sb.append(String.valueOf(obj));
			sb.append("`");
		}
		if(sb.length()>1){
			sb.deleteCharAt(sb.length()-1);
		}
		resultMap.put(VALUE_KEY, sb.toString());
		resultMap.put(NULL_VALUE_KEY, nullValCounter);
		return resultMap;
	}

	public static List<String> recursiveJSON(JSONObject jsonObj, String[] strArr,List<String> resultList,AtomicLong nullValCounter) {
		Object val = jsonObj.get(strArr[0]);
		JSONArray jsonArr = null;
		String[] subArr = null;
		if (strArr.length != 1) {
			subArr = new String[strArr.length - 1];
			System.arraycopy(strArr, 1, subArr, 0, strArr.length - 1);
		}
		if (val instanceof JSONArray) {
			jsonArr = (JSONArray) val;
			for (int i = 0, len = jsonArr.size(); i < len; i++) {
				recursiveJSON(jsonArr.getJSONObject(i), subArr,resultList,nullValCounter);
			}
		} else if (val instanceof JSONObject) {
			recursiveJSON((JSONObject) val, subArr,resultList,nullValCounter);
		} else if (null == val) {
			nullValCounter.incrementAndGet();
		} else {
			resultList.add(String.valueOf(val));
		}
		return resultList;
	}

}
