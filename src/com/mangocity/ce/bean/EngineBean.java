package com.mangocity.ce.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName: EngineBean
 * @Description: TODO(引擎bean)
 * @author Syungen
 * @date 2015年8月25日 下午2:23:44
 */
public class EngineBean implements Serializable {

	private static final long serialVersionUID = 698852581L;
	private String appId;
	private String command;
	private String isNosql;
	private String adjustCode;
	private String resultCode;
	private String resultMsg;
	private String errorType;
	private Map<String, Object> baseMap = new HashMap<String, Object>();
	private Map<String, Object> headMap = new HashMap<String, Object>();
	private Map<String, Object> bodyMap = new HashMap<String, Object>();

	private transient HttpServletRequest request;
	private transient HttpServletResponse response;

	public EngineBean(EngineBean eb) {
		this.headMap = new HashMap<String, Object>(eb.headMap);
		this.baseMap = new HashMap<String, Object>(eb.baseMap);
		this.bodyMap = new HashMap<String, Object>(eb.bodyMap);
		this.request = eb.request;
		this.response = eb.response;
		this.appId=eb.getAppId();
		this.command=eb.getCommand();
		this.isNosql=eb.getIsNosql();
		this.adjustCode=eb.getAdjustCode();
	}

	public EngineBean() {
	}

	public Map<String, Object> getBaseMap() {
		return baseMap;
	}

	public void setBaseMap(Map<String, Object> baseMap) {
		this.baseMap = baseMap;
	}

	public void setBase(String key, Object value) {
		baseMap.put(key, value);
	}

	public Object getBase(String key) {
		return baseMap.get(key);
	}

	public String getIsNosql() {
		return isNosql;
	}

	public void setIsNosql(String isNosql) {
		this.isNosql = isNosql;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	/**
	 * getCommand(这里用一句话描述这个方法的作用)
	 * 
	 * @Title: getCommand
	 * @Description: TODO
	 * @param @return 设定文件
	 * @return String 返回类型
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * @Title: setCommand
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param command 参数说明
	 * @return void 返回类型
	 * @throws
	 */
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

	public void setHead(String key, Object value) {
		headMap.put(key, value);
	}

	public Object getHead(String key) {
		return headMap.get(key);
	}

	public void setBody(String key, Object value) {
		bodyMap.put(key, value);
	}

	public Object getBody(String key) {
		if (bodyMap.containsKey(key)) {
			return bodyMap.get(key);
		} else {
			Object obj = bodyMap.get("result");
			if (obj instanceof Map) {
				return ((Map) (bodyMap.get("result"))).get(key);
			}
		}
		return "";
	}

	public Map<String, Object> getHeadMap() {
		return headMap;
	}

	public Map<String, Object> getBodyMap() {
		return bodyMap;
	}

	public void setHeadMap(Map<String, Object> headMap) {
		this.headMap = new HashMap<String, Object>(headMap);
	}

	public void setBodyMap(Map<String, Object> bodyMap) {
		this.bodyMap = bodyMap;
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

	/**
	 * 重写toString,只输出关键数据
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("EngineBean [appId=").append(appId).append(", resultCode=")
				.append(resultCode).append(", resultMsg=").append(resultMsg)
				.append(", bodyMap=").append(bodyMap).append("]");
		return sb.toString();
	}
}
