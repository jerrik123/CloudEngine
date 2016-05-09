package com.mangocity.ce.cache;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * 
 * @ClassName: ThreadManage
 * @Description: (线程值对像管理类)
 * @author 刘春元
 * @date 2015年6月18日 上午9:26:58
 *
 */
public class ThreadManage {
	private static final Map<String, Map<String, Object>> THREAD_MAP = new HashMap<String, Map<String, Object>>();
	private static ThreadManage uniqueInstance = null;
	private static final Logger LOG = Logger.getLogger(ThreadManage.class);

	/**
	 * 线程公用对像
	 * 
	 * @return ThreadCacheManage
	 */
	public static ThreadManage instance() {
		if (uniqueInstance == null) {
			LOG.debug("new ThreadManage() begin.....");
			uniqueInstance = new ThreadManage();
			LOG.debug("new ThreadManage() end .....");
		}
		return uniqueInstance;
	}

	private ThreadManage() {
	};

	/**
	 * 得到当前线程的ID
	 * 
	 * @return
	 */
	private final String getThreadID() {
		Thread thread = Thread.currentThread();
		return thread.getId() + "key";
	}

	/**
	 * 把值放入线程对像
	 * 
	 * @param key
	 * @param obj
	 */
	public final void put(String key, Object obj) {

		Map<String, Object> map = null;
		if (null == THREAD_MAP.get(getThreadID())) {
			LOG.debug("add thread value map begin...");
			map = new HashMap<String, Object>();
			THREAD_MAP.put(getThreadID(), map);
			LOG.debug("add thread value map end");
		} else {
			map = THREAD_MAP.get(getThreadID());
		}
		map.put(key, obj);
	}

	/**
	 * 取出线程中的值
	 * 
	 * @param key
	 * @return
	 */
	public final Object get(String key) {
		return null == THREAD_MAP.get(getThreadID()) ? null : THREAD_MAP.get(
				getThreadID()).get(key);
	}

	/**
	 * 删除当前线程的值对像
	 */
	public final void removeNowThread() {
		LOG.debug("remove thread value map begin...");
		THREAD_MAP.remove(getThreadID());
		System.gc();
		LOG.debug("remove thread value map end...");
	}
}
