package com.mangocity.ce.cache.operate;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.mangocity.ce.book.CacheBook;
import com.mangocity.ce.book.FileBook;
import com.mangocity.ce.cache.ApplicationManage;
import com.mangocity.ce.deploy.DeployFileManage;

/**
 * 
 * @ClassName: SystemConfigCacgeOperate
 * @Description: (系统字典)
 * @author 刘春元
 * @date 2015年6月18日 上午9:26:19
 *
 */
public class SystemConfigCacgeOperate {
	private static SystemConfigCacgeOperate uniqueInstance = null;
	private static final Logger LOG = Logger
			.getLogger(SystemConfigCacgeOperate.class);

	/**
	 * 文件配置管理类 取得实例
	 * 
	 * @return DeployFileManage
	 */
	public static SystemConfigCacgeOperate instance() {
		if (uniqueInstance == null) {
			LOG.debug("new SystemConfigCacgeOperate() begin.....");
			uniqueInstance = new SystemConfigCacgeOperate();
			LOG.debug("new SystemConfigCacgeOperate() end .....");
		}
		return uniqueInstance;
	}

	/**
	 * 系统配置读取
	 */
	public final void readerSysConfig() {
		LOG.debug("readerSysConfig begin...");
		DeployFileManage deploy = DeployFileManage.instance();
		HashMap<String, String> map = new HashMap<String, String>();
		ArrayList<String> pathList = deploy.seekAllFilePath(
				deploy.seekConfigPath(), FileBook.SYS_REGEX_BOOK, null);
		for (String path : pathList) {
			deploy.readerProperties(path, map);
		}
		// 进入全局缓存
		ApplicationManage.instance().put(CacheBook.SYS_CACHE_BOOK, map);
		LOG.debug("readerSysConfig end.");
	}
}
