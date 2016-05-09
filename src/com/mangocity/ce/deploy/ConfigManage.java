package com.mangocity.ce.deploy;

import java.util.Map;

import org.apache.log4j.Logger;

import com.mangocity.ce.book.CacheBook;
import com.mangocity.ce.cache.ApplicationManage;
import com.mangocity.ce.cache.operate.ActionCacheOperate;
import com.mangocity.ce.cache.operate.CheckCacheOperate;
import com.mangocity.ce.cache.operate.DistributionCacheOperate;
import com.mangocity.ce.cache.operate.RemoteCacheOperate;
import com.mangocity.ce.cache.operate.ServletCacheOperate;
import com.mangocity.ce.cache.operate.SystemConfigCacgeOperate;
import com.mangocity.ce.exception.ExceptionAbstract;

/**
 * 
 * @ClassName: ConfigManage
 * @Description: (数据配置管理类)
 * @author 刘春元
 * @date 2015年6月18日 上午9:27:11
 *
 */
public class ConfigManage {

	private static ConfigManage uniqueInstance = null;
	private static final Logger LOG = Logger.getLogger(ConfigManage.class);

	/**
	 * 文件配置管理类 取得实例
	 * 
	 * @return DeployFileManage
	 */
	public static ConfigManage instance() {
		if (uniqueInstance == null) {
			LOG.debug("new ConfigManage() begin.....");
			uniqueInstance = new ConfigManage();
			uniqueInstance.initSystem();
			LOG.debug("new ConfigManage() end .....");
		}
		return uniqueInstance;
	}

	private ConfigManage() {
	}

	/**
	 * 得到系统级配置
	 * 
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public final String getSysConfig(String key) {
		return ((Map<String, String>) ApplicationManage.instance().get(
				CacheBook.SYS_CACHE_BOOK)).get(key);
	}

	public final void initSystem() {
		SystemConfigCacgeOperate.instance().readerSysConfig();
		try {
			// ActionCacheOperate.instance().initAction();
			// ActionCacheOperate.instance().initDepend();
			// CheckTreeOperate.instance().initCheckTree();
			ActionCacheOperate.instance().initActionCache();
			RemoteCacheOperate.instance().initRemote();
			CheckCacheOperate.instance().initCheckCache();
			DistributionCacheOperate.instance().initDistributionCache();
			//ServletCacheOperate.instance().initServletCache();
		} catch (ExceptionAbstract e) {
			LOG.error(e.getErrorMsg());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("rawtypes")
	public final Object getWebAction(String actionid) {
		return ((Map) ApplicationManage.instance().get(
				CacheBook.ACTION_CACHE_BOOK)).get(actionid);
	}
	
	@SuppressWarnings("rawtypes")
	public final Object getWebServlet(String mappingId) {
		return ((Map) ApplicationManage.instance().get(
				CacheBook.SERVLET_CACHE_BOOK)).get(mappingId);
	}
	
	public final Object getCheckStyle(String mangoId) {
		return ((Map) ApplicationManage.instance().get(
				CacheBook.CHECK_CACHE_BOOK)).get(mangoId);
	}
	
	/**
	 * 是否包含校验Key
	 * @param key
	 * @return
	 */
	public final boolean containsCheckKey(String key){
		return ((Map)(ApplicationManage.instance().get(
				CacheBook.SYS_CACHE_BOOK))).containsKey(key);
	}
	/**
	 * 得到处理对像
	 * 
	 * @param key
	 * @return
	 */
	/*
	 * public final ActionList.Action getAction(String key){ return
	 * ((ActionList)
	 * (ApplicationManage.instance().get(CacheBook.ACTION_CACHE_BOOK
	 * ))).getAction(key); }
	 *//**
	 * 方法处理对像
	 * 
	 * @param key
	 * @return
	 */
	/*
	 * public final ActionList.Entry getMethod(String key){ return
	 * ((ActionList)(
	 * ApplicationManage.instance().get(CacheBook.ACTION_CACHE_BOOK
	 * ))).getMethod(key); }
	 */
	/**
	 * 得到得到处理对像
	 * 
	 * @param key
	 * @return
	 */
	/*
	 * public final String getSqlStr(String actionId){ return ((Map<String,
	 * SqlBean
	 * >)ApplicationManage.instance().get(CacheBook.SQL_STR_CACHE_BOOK)).get
	 * (key) == null?"": ((Map<String,
	 * SqlBean>)ApplicationManage.instance().get(
	 * CacheBook.SQL_STR_CACHE_BOOK)).get(key).getMangoClass(); }
	 */

}
