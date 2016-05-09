package com.mangocity.ce.remote;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.mangocity.ce.bean.RemoteConfigBean;
import com.mangocity.ce.book.CacheBook;
import com.mangocity.ce.cache.ApplicationManage;
import com.mangocity.ce.exception.SystemException;

/**
 * 
* @ClassName: Service 
* @Description: (远程调用) 
* @author 刘春元
* @date 2015年7月29日 下午9:27:59 
*
 */
public class Service {
	private static final Logger LOG = Logger.getLogger(Service.class);

	private static Service uniqueInstance = null;

	/**
	 * 文件配置管理类 取得实例
	 * 
	 * @return DeployFileManage
	 */
	public static Service instance() {
		if (uniqueInstance == null) {
			LOG.debug("new Service() begin.....");
			uniqueInstance = new Service();
			LOG.debug("new ServiceCore() end .....");
		}
		return uniqueInstance;
	}

	// 服务器开启
	@SuppressWarnings("unchecked")
	public void bindRemote() throws SystemException, IOException {
		// 得到所有运程调用端口
		Map<String, RemoteConfigBean> list = (Map<String, RemoteConfigBean>) ApplicationManage
				.instance().get(CacheBook.REMOTE_SERVICE_NAMELIST_BOOK);

		if (null != list && !list.isEmpty()) {
			LOG.debug(list);
			Set<String> ks = list.keySet();
			Iterator<String> it = ks.iterator();
			RemoteConfigBean bean = null;
			ExecutorService es = Executors.newFixedThreadPool(list.size());
			while (it.hasNext()) {
				bean = list.get(it.next());
				es.execute(new ServicePool(bean));
				//new ThreadStart().start(bean);
			}
		} else {
			LOG.debug("remote config is null!");
		}

	}
	
	private class ServicePool implements Runnable {
		RemoteConfigBean bean =null;
		public ServicePool(RemoteConfigBean bean) {
			this.bean = bean;
		}

		public void run() {
			start(bean);
		}
		public void start(RemoteConfigBean bean) {
			ServerSocket service = null;
			try {
				service = new ServerSocket(bean.getPort());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			LOG.debug("start service port:" + bean.getPort());
			ExecutorService es = Executors.newFixedThreadPool(bean
					.getThreadNumber());

			while (true) {
				if (bean.isStop())
					break;
				Socket socket = null;
				try {
					socket = service.accept();
					es.execute(new ServerHandler(socket, bean));
				} catch (IOException e) {
					LOG.error(e);
				}

			}
		}

	}

	private class ServerHandler implements Runnable {
		private Socket socket;
		private InputStream in;
		private RemoteConfigBean bean;

		public ServerHandler(Socket socket, RemoteConfigBean bean) {
			this.socket = socket;
			this.bean = bean;
		}

		public void run() {
			PrintWriter os = null;
			BufferedReader is = null;
			try {
				os = new PrintWriter(socket.getOutputStream());
				is = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));
				ServiceCore.instance().process(is, os, bean, socket);

			} catch (IOException e) {
				LOG.error(e);
			} finally {
				if (null != is) {
					try {
						is.close();
					} catch (IOException e) {
						LOG.error(e);
					}
				}
				if (null != os) {
					os.close();
				}
				if (null != socket && !socket.isClosed()) {
					try {
						socket.close();
					} catch (IOException e) {
						LOG.error(e);
					}
				}

			}
		}

	}

}
