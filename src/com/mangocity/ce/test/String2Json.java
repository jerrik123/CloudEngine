package com.mangocity.ce.test;

import java.util.Set;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSONObject;
import com.mangocity.ce.exception.IllegalParamException;
import com.mangocity.ce.util.JsonUtil;

public class String2Json {

	public static void main(String[] args) throws IllegalParamException {
		String str = "{\"headMap\":{\"passList\":[\"12\",\"322\",[\"234\",\"222\"]]},\"adjustCode\":\"23423423\"}";
		/*
		 * Map<String,Object> map = JsonUtil.decodeCmd(str);
		 * 
		 * Object head = map.get("headMap"); System.out.println(head); if(head
		 * instanceof List){ List list = (List)head; System.out.println(list);
		 * }else if(head instanceof Map){ System.out.println("map: " + head);
		 * Map map1 = (Map)head; System.out.println("========" +
		 * map1.get("passList")); }
		 */

		/*JSONObject json = JSONObject.parseObject(str);
		Set<String> set = json.keySet();
		for (String key : set) {
			System.out.println("key==" + key);
		}

		String strs = "sdfs.xdf.s.abc";
		String[] strArr = strs.split("\\.");
		for (String string : strArr) {
			System.out.println(string);
		}*/
		
		System.out.println(JsonUtil.json2Map(str));
		
		String strss = (String)null;
		System.out.println(strss);

		
	}
}
