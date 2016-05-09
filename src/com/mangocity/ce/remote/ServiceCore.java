package com.mangocity.ce.remote;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.Socket;

import org.apache.log4j.Logger;

import com.mangocity.ce.bean.RemoteConfigBean;

/**
 * 
* @ClassName: ServiceCore 
* @Description: (远程分发) 
* @author 刘春元
* @date 2015年7月29日 下午9:30:10 
*
 */
public class ServiceCore {
	private static ServiceCore uniqueInstance = null;
	private static final Logger LOG = Logger.getLogger(ServiceCore.class);

	/**
	 * 文件配置管理类 取得实例
	 * 
	 * @return DeployFileManage
	 */
	public static ServiceCore instance() {
		if (uniqueInstance == null) {
			LOG.debug("new ServiceCore() begin.....");
			uniqueInstance = new ServiceCore();
			LOG.debug("new ServiceCore() end .....");
		}
		return uniqueInstance;
	}
	
	public void process(BufferedReader request,PrintWriter resp,RemoteConfigBean remote,Socket socket){
		try {
			Method m = remote.getInstance().getClass().getMethod("process", BufferedReader.class,PrintWriter.class,RemoteConfigBean.class,Socket.class);
			m.invoke(remote.getInstance(), request,resp,remote,socket);
		} catch (Exception e) {
			LOG.error("method name not 'process(BufferedReader ,PrintWriter)' ");
	        return;
		}
	}

}
