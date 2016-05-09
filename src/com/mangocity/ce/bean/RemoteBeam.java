package com.mangocity.ce.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
/**
 * 
* @ClassName: RemoteBeam 
* @Description: TODO(远程配置bean) 
* @author Syungen
* @date 2015年8月25日 下午2:24:10 
*
 */
public class RemoteBeam implements Serializable {

	
	private static final long serialVersionUID = 69885581L;
	
	private String adjustID;
	private String Naming;
	private String resultCode;
	private String resutlMsg;
	private String errorType;
	
	private Map<String, Object> resultList = new HashMap<String, Object>();
	public String getAdjustID() {
		return adjustID;
	}
	public void setAdjustID(String adjustID) {
		this.adjustID = adjustID;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResutlMsg() {
		return resutlMsg;
	}
	public void setResutlMsg(String resutlMsg) {
		this.resutlMsg = resutlMsg;
	}
	public String getErrorType() {
		return errorType;
	}
	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}
	public Map<String, Object> getResultList() {
		return resultList;
	}
	public void setResultList(Map<String, Object> resultList) {
		this.resultList = resultList;
	}
	public String getNaming() {
		return Naming;
	}
	public void setNaming(String naming) {
		Naming = naming;
	}
	
}
