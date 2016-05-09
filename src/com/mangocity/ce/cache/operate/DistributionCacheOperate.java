package com.mangocity.ce.cache.operate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.mangocity.ce.bean.ActionBean;
import com.mangocity.ce.bean.DistributionBean;
import com.mangocity.ce.book.CacheBook;
import com.mangocity.ce.book.FileBook;
import com.mangocity.ce.cache.ApplicationManage;
import com.mangocity.ce.deploy.ConfigManage;
import com.mangocity.ce.deploy.DeployFileManage;
import com.mangocity.ce.exception.ExceptionAbstract;
import com.thoughtworks.xstream.XStream;

/**
 * @ClassName: DistributionCacheOperate
 * @Description: (拦截请求后缀)
 * @author YangJie
 * @date 2015年6月18日 上午9:25:50
 */
public class DistributionCacheOperate {

	private static DistributionCacheOperate uniqueInstance = null;
	private static final Logger LOG = Logger
			.getLogger(DistributionCacheOperate.class);

	public static DistributionCacheOperate instance() {
		if (uniqueInstance == null) {
			LOG.debug("new DistributionCacheOperate() begin.....");
			uniqueInstance = new DistributionCacheOperate();
			LOG.debug("new ActionCacheOperate() end .....");
		}
		return uniqueInstance;
	}

	public void initDistributionCache() throws ExceptionAbstract {
		DeployFileManage deploy = DeployFileManage.instance();
		XStream xstream = deploy.getXStreamInstance(null);
		setSqlXml(xstream);
		ArrayList<Object> actionList = deploy.readDeployFile(
				deploy.seekConfigPath(),
				ConfigManage.instance().getSysConfig(
						FileBook.SYS_DISTRIBUTION_REGEX_BOOK), xstream);
		Map<String, String> map = new HashMap<String, String>();
		ActionBean bean = null;
		for (Object obj : actionList) {
			bean = (ActionBean) obj;
			try {
				if(bean.getMangoId().split(",").length>1){
					for (int i = 0; i < bean.getMangoId().split(",").length; i++) {
						map.put(bean.getMangoId().split(",")[i], bean.getMangoClass());
					}
				}else{
					map.put(bean.getMangoId(), bean.getMangoClass());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		ApplicationManage.instance().put(CacheBook.DISTRIBUTION_CACHE_BOOK, map);
	}

	/**
	 * 全局依赖设置
	 * @param xstream
	 */
	private void setSqlXml(XStream xstream) {
		xstream.alias("mangocity", ArrayList.class);
		xstream.alias("Request", DistributionBean.class);
		xstream.useAttributeFor(DistributionBean.class, "suffix");
		xstream.useAttributeFor(DistributionBean.class, "handler");
	}
}
