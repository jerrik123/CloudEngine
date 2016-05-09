package com.mangocity.ce.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @ClassName: WebBean
 * @Description: (业务bean)
 * @author 刘春元
 * @date 2015年6月18日 上午9:23:08
 *
 */
public class WebBean implements Serializable {
	private static final long serialVersionUID = 698852581L;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private String command;
	private String adjustCode;
	private String resultCode;
	private String resultMsg;
	private String errorType;
	private Map<String, Object> headMap = new HashMap<String, Object>();
	private Map<String, Object> bodyMap = new HashMap<String, Object>();
	public void setHParam(String key, String value) {
		headMap.put(key, value);
	}

	public String getHParam(String key) {
		return (String) headMap.get(key);
	}
	public HttpServletRequest getRequest() {
		return request;
	}
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	public HttpServletResponse getResponse() {
		return response;
	}
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public String getAdjustCode() {
		return adjustCode;
	}
	public void setAdjustCode(String adjustCode) {
		this.adjustCode = adjustCode;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultMsg() {
		return resultMsg;
	}
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
	public String getErrorType() {
		return errorType;
	}
	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}
	public Map<String, Object> getHeadMap() {
		return headMap;
	}
	public void setHeadMap(Map<String, Object> headMap) {
		this.headMap = headMap;
	}
	public Map<String, Object> getBodyMap() {
		return bodyMap;
	}
	public void setBodyMap(Map<String, Object> bodyMap) {
		this.bodyMap = bodyMap;
	}
	
	
}
