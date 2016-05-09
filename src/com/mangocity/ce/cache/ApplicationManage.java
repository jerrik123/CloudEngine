package com.mangocity.ce.cache;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * 
 * @ClassName: ApplicationManage
 * @Description: (全局值对像管理类)
 * @author 刘春元
 * @date 2015年6月18日 上午9:26:29
 *
 */
public class ApplicationManage {
	private static final Map<String, Object> THREAD_MAP = new HashMap<String, Object>();
	private static ApplicationManage uniqueInstance = null;
	private static final Logger LOG = Logger.getLogger(ApplicationManage.class);

	/**
	 * 全局值公用对像
	 * 
	 * @return ApplicationCacheManage
	 */
	public final static ApplicationManage instance() {
		if (uniqueInstance == null) {
			LOG.debug("new ApplicationManage() begin.....");
			uniqueInstance = new ApplicationManage();
			LOG.debug("new ApplicationManage() end .....");
		}
		return uniqueInstance;
	}

	private ApplicationManage() {
	};

	public final void put(String key, Object value) {
		THREAD_MAP.put(key, value);
	}

	public final Object get(String key) {
		return THREAD_MAP.get(key);
	}
}
