package com.mangocity.ce.cache.operate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.mangocity.ce.bean.RemoteConfigBean;
import com.mangocity.ce.book.CacheBook;
import com.mangocity.ce.book.FileBook;
import com.mangocity.ce.cache.ApplicationManage;
import com.mangocity.ce.deploy.ConfigManage;
import com.mangocity.ce.deploy.DeployFileManage;
import com.mangocity.ce.exception.ExceptionAbstract;
import com.thoughtworks.xstream.XStream;

/**
 * 
* @ClassName: RemoteCacheOperate 
* @Description: TODO(远程服务配置管理类) 
* @author Syungen
* @date 2015年8月25日 下午2:47:35 
*
 */
public class RemoteCacheOperate {
	private static RemoteCacheOperate uniqueInstance = null;
	private static final Logger LOG = Logger
			.getLogger(RemoteCacheOperate.class);

	/**
	 * 文件配置管理类 取得实例
	 * 
	 * @return DeployFileManage
	 */
	public static RemoteCacheOperate instance() {
		if (uniqueInstance == null) {
			LOG.debug("new RemoteCacheOperate() begin.....");
			uniqueInstance = new RemoteCacheOperate();
			LOG.debug("new RemoteCacheOperate() end .....");
		}
		return uniqueInstance;
	}

	public void initRemote() throws ExceptionAbstract {
		DeployFileManage deploy = DeployFileManage.instance();
		XStream xstream = deploy.getXStreamInstance(null);
		setRemoteXml(xstream);
		ArrayList<Object> bean = deploy.readDeployFile(deploy.seekConfigPath(),
				ConfigManage.instance().getSysConfig(FileBook.SYS_REMOTE_REGEX_BOOK), xstream);
		HashMap<String, RemoteConfigBean> map = new HashMap<String, RemoteConfigBean>();
		if (null == bean)
			return;
		RemoteConfigBean t = null;
		for (Object o : bean) {
			t = (RemoteConfigBean) o;

			try {
				t.setInstance(Class.forName(t.getClassName()).newInstance());
			} catch (Exception e) {
				LOG.error(e);
			}
			map.put(t.getRemoteName(), t);
		}
		ApplicationManage.instance().put(
				CacheBook.REMOTE_SERVICE_NAMELIST_BOOK, map);
		if(null != map){
			try {
				com.mangocity.ce.remote.Service.instance().bindRemote();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void setRemoteXml(XStream xstream) {
		xstream.alias("remote-config", RemoteConfigBean.class);
		xstream.useAttributeFor(RemoteConfigBean.class, "remoteName");
		xstream.useAttributeFor(RemoteConfigBean.class, "port");
		xstream.useAttributeFor(RemoteConfigBean.class, "automationStart");
		xstream.useAttributeFor(RemoteConfigBean.class, "threadNumber");
	}
}
