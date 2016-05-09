package com.mangocity.ce.cache.operate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.mangocity.ce.bean.ActionBean;
import com.mangocity.ce.bean.ServletBean;
import com.mangocity.ce.book.CacheBook;
import com.mangocity.ce.book.FileBook;
import com.mangocity.ce.cache.ApplicationManage;
import com.mangocity.ce.deploy.ConfigManage;
import com.mangocity.ce.deploy.DeployFileManage;
import com.mangocity.ce.exception.ExceptionAbstract;
import com.thoughtworks.xstream.XStream;

/**
 * 
 * @ClassName: ServletCacheOperate
 * @Description: (servlet配置处理)
 * @author 杨洁
 * @date 2015年8月12日 上午9:25:50
 *
 */
public class ServletCacheOperate {

	private static ServletCacheOperate uniqueInstance = null;
	private static final Logger LOG = Logger
			.getLogger(ServletCacheOperate.class);

	/**
	 * 文件配置管理类 取得实例
	 * 
	 * @return DeployFileManage
	 */
	public static ServletCacheOperate instance() {
		if (uniqueInstance == null) {
			LOG.debug("new ServletCacheOperate() begin.....");
			uniqueInstance = new ServletCacheOperate();
			LOG.debug("new ServletCacheOperate() end .....");
		}
		return uniqueInstance;
	}

	public void initServletCache() throws ExceptionAbstract {
		DeployFileManage deploy = DeployFileManage.instance();
		XStream xstream = deploy.getXStreamInstance(null);
		setServletXml(xstream);
		ArrayList<Object> servletList = deploy.readDeployFile(
				deploy.seekConfigPath(),
				ConfigManage.instance().getSysConfig(
						FileBook.SERVLET_REGEX_BOOK), xstream);
		Map<String, String> map = new HashMap<String, String>();
		ServletBean bean = null;
		for (Object obj : servletList) {
			bean = (ServletBean) obj;
			try {
				map.put(bean.getMappingId(), bean.getMappingClass());
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}
		}
		ApplicationManage.instance().put(CacheBook.SERVLET_CACHE_BOOK, map);
	}

	/**
	 * 全局依赖设置
	 * 
	 * @param xstream
	 */
	private void setServletXml(XStream xstream) {
		xstream.alias("mango-mappings", ArrayList.class);
		xstream.alias("mango-mapping", ServletBean.class);
		xstream.useAttributeFor(ServletBean.class, "mappingId");
		xstream.useAttributeFor(ServletBean.class, "mappingClass");
	}
}
