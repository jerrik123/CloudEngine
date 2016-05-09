package com.mangocity.ce.cache.operate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.mangocity.ce.bean.ActionBean;
import com.mangocity.ce.bean.CheckBean;
import com.mangocity.ce.bean.CheckBean.SubParam;
import com.mangocity.ce.bean.CheckBean.SubParam.Rule;
import com.mangocity.ce.book.CacheBook;
import com.mangocity.ce.book.FileBook;
import com.mangocity.ce.cache.ApplicationManage;
import com.mangocity.ce.deploy.ConfigManage;
import com.mangocity.ce.deploy.DeployFileManage;
import com.mangocity.ce.exception.ExceptionAbstract;
import com.thoughtworks.xstream.XStream;

/**
 * @ClassName: CheckCacheOperate
 * @Description: 规则树
 * @author 杨洁
 * @date 2015年10月21日 上午9:25:50
 */
public class CheckCacheOperate {
	private static CheckCacheOperate uniqueInstance = null;
	private static final Logger LOG = Logger
			.getLogger(CheckCacheOperate.class);
	
	private CheckCacheOperate(){}
	public static CheckCacheOperate instance() {
		if (uniqueInstance == null) {
			LOG.debug("new CheckCacheOperate() begin.....");
			uniqueInstance = new CheckCacheOperate();
			LOG.debug("new CheckCacheOperate() end .....");
		}
		return uniqueInstance;
	}

	public void initCheckCache() throws ExceptionAbstract {
		DeployFileManage deploy = DeployFileManage.instance();
		XStream xstream = deploy.getXStreamInstance(null);
		setSqlXml(xstream);
		ArrayList<Object> checkList = deploy.readDeployFile(
				deploy.seekConfigPath(),
				ConfigManage.instance().getSysConfig(
						FileBook.SYS_CHECK_REGEX_BOOK), xstream);
		Map<String, CheckBean> map = new HashMap<String, CheckBean>();
		CheckBean bean = null;
		for (Object obj : checkList) {
			bean = (CheckBean) obj;
			try {
				map.put(bean.getMangoId(),bean);
			} catch (Exception e) {
				LOG.error("读取check._.xml转换失败",e);
			}
		}
		ApplicationManage.instance().put(CacheBook.CHECK_CACHE_BOOK, map);
	}

	/**
	 * 全局依赖设置
	 * 
	 * @param xstream
	 */
	private void setSqlXml(XStream xstream) {
		xstream.alias("check-list", ArrayList.class);
		xstream.alias("check", CheckBean.class);
		xstream.alias("sub-param", CheckBean.SubParam.class);
		xstream.alias("rule", CheckBean.SubParam.Rule.class);
		xstream.aliasField("param-list", CheckBean.class, "paramList");
		xstream.aliasField("rule-list", SubParam.class, "ruleList");
		xstream.useAttributeFor(Rule.class, "key");
		xstream.useAttributeFor(Rule.class, "value");
		xstream.useAttributeFor(Rule.class, "hints");
		xstream.useAttributeFor(CheckBean.class,"mangoId");
		xstream.useAttributeFor(SubParam.class,"name");
		xstream.useAttributeFor(SubParam.class,"level");
	}
}
