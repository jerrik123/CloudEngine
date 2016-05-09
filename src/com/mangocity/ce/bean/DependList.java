package com.mangocity.ce.bean;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * 
* @ClassName: DependList 
* @Description: TODO(系统配置BEAN) 
* @author Syungen
* @date 2015年8月25日 下午2:23:35 
*
 */
public class DependList {
	private static DependList uniqueInstance = null;
	private static final Logger LOG = Logger.getLogger(DependList.class);
	private static final SingleDepend single = new SingleDepend();
	private static final Map<String, Depend> dependList = new HashMap<String, DependList.Depend>();

	/**
	 * 全局值公用对像
	 * 
	 * @return ApplicationCacheManage
	 */
	public final static DependList instance() {
		if (uniqueInstance == null) {
			LOG.debug("new DependList() begin.....");
			uniqueInstance = new DependList();
			LOG.debug("new DependList() end .....");
		}
		return uniqueInstance;
	}

	public void putSingle(String key, DependBean bean) {
		single.put(key, new Depend(bean));
	}

	public void putDepend(String key, DependBean bean) {
		dependList.put(key, new Depend(bean));
	}

	public Depend get(String key) {
		return null == dependList.get(key) ? single.get(key) : dependList
				.get(key);
	}

	public static class SingleDepend {

		private Map<String, Depend> singleDepend = new HashMap<String, Depend>();

		SingleDepend() {
		};

		public void put(String key, Depend de) {
			singleDepend.put(key, de);
		}

		public Depend get(String key) {
			return singleDepend.get(key);
		}

	}

	public class Depend {
		@SuppressWarnings("unused")
		private Map<String, String> paramList;
		private Object depend = null;
		private boolean isNew;
		private String className = "";
		private String key;
		private Map<String, Depend> dependList = new HashMap<String, Depend>();

		Depend(DependBean depend) {
			paramList = depend.getParamList();
			this.key = depend.getId();
			if (this.key != null && !this.key.trim().equals("")) {
				return;
			}
			if (isNew) {
				className = depend.getClassName();
			} else {
				try {
					depend = (DependBean) Class.forName(depend.getClassName())
							.newInstance();
				} catch (Exception e) {
					LOG.error(e);
				}
				if (null != depend.getDependList()
						&& !depend.getDependList().isEmpty()) {
					Set<String> set = depend.getDependList().keySet();
					Iterator<String> it = set.iterator();
					while (it.hasNext()) {
						String k = it.next();
						dependList.put(k, new Depend(depend.getDepend(k)));
					}
				}
			}
		}

		public Object get() {
			if (isNew) {
				try {
					return Class.forName(className).newInstance();
				} catch (Exception e) {
					LOG.error(e);
				}
			}
			if (this.key != null && !this.key.trim().equals("")) {
				return single.get(key);
			}
			return depend;
		}
	}
}
