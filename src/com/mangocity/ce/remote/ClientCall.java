package com.mangocity.ce.remote;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

import com.mangocity.ce.bean.EngineBean;
import com.mangocity.ce.book.CommonBook;
/**
 * 
* @ClassName: ClientCall 
* @Description: TODO(远程调用) 
* @author Syungen
* @date 2015年8月25日 下午2:48:58 
*
 */
public class ClientCall {
	private static final Logger LOG = Logger.getLogger(Service.class);

	private static ClientCall uniqueInstance = null;

	/**
	 * 文件配置管理类 取得实例
	 * 
	 * @return DeployFileManage
	 */
	public static ClientCall instance() {
		if (uniqueInstance == null) {
			LOG.debug("new Service() begin.....");
			uniqueInstance = new ClientCall();
			LOG.debug("new ServiceCore() end .....");
		}
		return uniqueInstance;
	}
	
	public EngineBean call(String ip,int port,EngineBean bean) throws UnknownHostException, IOException{
		String string = SerializableRemoteBean.serializable(bean);
		Socket socket=new Socket(ip,port);
		
		PrintWriter os=new PrintWriter(socket.getOutputStream());
		os.println(string);
		os.println(CommonBook.REMOTE_SKIP_BOOK);
		os.flush();
		BufferedReader is=new BufferedReader(new InputStreamReader(socket.getInputStream()));
		String reqString = "";
		String tem = "";
		//EngineBean pb = new EngineBean();
		try{
			while ((tem = is.readLine()) != null && !CommonBook.REMOTE_SKIP_BOOK.equals(tem)) {
				reqString += tem;
			}
			bean = (EngineBean) SerializableRemoteBean
					.unSerializable(reqString);
			System.err.println(bean);
		}catch(Exception e){
			LOG.error(e);
		}finally{
			os.close();
			socket.close();
		}
		return bean;
	}

}
