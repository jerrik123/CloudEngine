package com.mangocity.ce.cache;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.mangocity.ce.book.CacheBook;

/**
 * 
* @ClassName: SessionManage 
* @Description: (SESSON对像管理类) 
* @author 刘春元
* @date 2015年6月18日 上午9:26:44 
*
 */
public class SessionManage {
	    private static final Map<String, Map<String,Object>> SESSON_MAP = new HashMap<String, Map<String,Object>>();
		private static SessionManage uniqueInstance = null;
		private static final Logger LOG = Logger.getLogger(SessionManage.class);

		/**
		 * SESSOION公用对像
		 * @return SessionManage
		 */
		public static SessionManage instance() {
			if (uniqueInstance == null) {
				LOG.debug("new SessionManage() begin.....");
				uniqueInstance = new SessionManage();
				LOG.debug("new SessionManage() end .....");
			}
			return uniqueInstance;
		}
	    private SessionManage(){};
	    /**
	     * 得到当前Session的ID
	     * @return
	     */
	    private  final String getSessionID(){
	    	return (String)ThreadManage.instance().get(CacheBook.SESSION_KEY_CACHE_BOOK);
	    }
	    /**
	     * 把值放入线程对像
	     * @param key
	     * @param obj
	     */
	    public final void put(String key,Object obj){
	    	
	    	Map<String,Object> map =null;
	    	if(null == SESSON_MAP.get(getSessionID())){
	    		LOG.debug("add session value map begin...");
	    		map = new HashMap<String,Object>();
	    		SESSON_MAP.put(getSessionID(), map);
	    		LOG.debug("add session value map end");
	    	}else{
	    		map = SESSON_MAP.get(getSessionID());
	    	}
	    	map.put(key, obj);
	    }
	    /**
	     * 取出SESSOION中的值
	     * @param key
	     * @return
	     */
	    public final Object get(String key){
	    	return null == SESSON_MAP.get(getSessionID())?null:SESSON_MAP.get(getSessionID()).get(key);
	    }
	    /**
	     * 删除当前SESSOION的值对像
	     */
	    public final void removeNowSesson(){
	    	LOG.debug("remove thread value map begin...");
	    	SESSON_MAP.remove(getSessionID());
	    	System.gc();
	    	LOG.debug("remove thread value map end...");
	    }
}
