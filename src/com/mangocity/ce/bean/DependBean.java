package com.mangocity.ce.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * 
* @ClassName: DependBean 
* @Description: TODO(系统配置BEAN) 
* @author Syungen
* @date 2015年8月25日 下午2:23:24 
*
 */
public class DependBean {
	private String className;
	private boolean isNew;
	private String id;
	private Map<String, DependBean> dependList = new HashMap<String, DependBean>();
	private Map<String, String> paramList = new HashMap<String, String>();

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Map<String, DependBean> getDependList() {
		return dependList;
	}

	public void setDependList(Map<String, DependBean> dependList) {
		this.dependList = dependList;
	}

	public void putDepend(String key, DependBean value) {
		this.dependList.put(key, value);
	}

	public DependBean getDepend(String key) {
		return this.dependList.get(key);
	}

	public boolean isNew() {
		return isNew;
	}

	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}

	public Map<String, String> getParamList() {
		return paramList;
	}

	public void setParamList(Map<String, String> paramList) {
		this.paramList = paramList;
	}

	public void putParam(String key, String val) {
		this.paramList.put(key, val);
	}

}
