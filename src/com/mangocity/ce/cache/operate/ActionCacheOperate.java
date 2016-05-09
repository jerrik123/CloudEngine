package com.mangocity.ce.cache.operate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.mangocity.ce.bean.ActionBean;
import com.mangocity.ce.book.CacheBook;
import com.mangocity.ce.book.FileBook;
import com.mangocity.ce.cache.ApplicationManage;
import com.mangocity.ce.deploy.ConfigManage;
import com.mangocity.ce.deploy.DeployFileManage;
import com.mangocity.ce.exception.ExceptionAbstract;
import com.thoughtworks.xstream.XStream;

/**
 * 
 * @ClassName: ActionCacheOperate
 * @Description: (web配置处理对应)
 * @author 刘春元
 * @date 2015年6月18日 上午9:25:50
 *
 */
public class ActionCacheOperate {

	private static ActionCacheOperate uniqueInstance = null;
	private static final Logger LOG = Logger
			.getLogger(ActionCacheOperate.class);

	/**
	 * 文件配置管理类 取得实例
	 * 
	 * @return DeployFileManage
	 */
	public static ActionCacheOperate instance() {
		if (uniqueInstance == null) {
			LOG.debug("new ActionCacheOperate() begin.....");
			uniqueInstance = new ActionCacheOperate();
			LOG.debug("new ActionCacheOperate() end .....");
		}
		return uniqueInstance;
	}

	public void initActionCache() throws ExceptionAbstract {
		DeployFileManage deploy = DeployFileManage.instance();
		XStream xstream = deploy.getXStreamInstance(null);
		setSqlXml(xstream);
		ArrayList<Object> actionList = deploy.readDeployFile(
				deploy.seekConfigPath(),
				ConfigManage.instance().getSysConfig(
						FileBook.SYS_ACTION_REGEX_BOOK), xstream);
		Map<String, String> map = new HashMap<String, String>();
		ActionBean bean = null;
		for (Object obj : actionList) {
			bean = (ActionBean) obj;
			try {
				if(bean.getMangoId().split(",").length>1){
					for (int i = 0; i < bean.getMangoId().split(",").length; i++) {
						map.put(bean.getMangoId().split(",")[i], bean.getMangoClass());
						if(null != bean.getMongoClass())
						map.put(bean.getMangoId().split(",")[i]+"_nosql", bean.getMongoClass());
					}
				}else{
					map.put(bean.getMangoId(), bean.getMangoClass());
					if(null != bean.getMongoClass())
						map.put(bean.getMangoId()+"_nosql", bean.getMongoClass());
				}
				
				// Class classSys = Class.forName((String) obj);
				// map.put(bean.getMangoId(),
				// classSys.getDeclaredMethod(bean.getMangoId(),BusinessBean.class));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		ApplicationManage.instance().put(CacheBook.ACTION_CACHE_BOOK, map);
	}

	/**
	 * 全局依赖设置
	 * 
	 * @param xstream
	 */
	private void setSqlXml(XStream xstream) {
		xstream.alias("mangocity", ArrayList.class);
		xstream.alias("action", ActionBean.class);
		xstream.useAttributeFor(ActionBean.class, "mangoId");
		xstream.useAttributeFor(ActionBean.class, "mangoClass");
		xstream.useAttributeFor(ActionBean.class, "mongoClass");
	}
}
