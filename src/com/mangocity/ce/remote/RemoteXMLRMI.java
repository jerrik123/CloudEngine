package com.mangocity.ce.remote;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import org.apache.log4j.Logger;

import com.mangocity.ce.bean.EngineBean;
import com.mangocity.ce.bean.RemoteConfigBean;
import com.mangocity.ce.book.CommonBook;
import com.mangocity.ce.book.ErrorBook;
import com.mangocity.ce.core.ActionCore;
import com.mangocity.ce.deploy.DeployFileManage;
import com.mangocity.ce.exception.ExceptionAbstract;
import com.mangocity.ce.exception.SystemException;
import com.mangocity.ce.util.MapCustomConverter;
import com.thoughtworks.xstream.XStream;
/**
 * 
* @ClassName: RemoteXMLRMI 
* @Description: TODO(远程调用分发) 
* @author Syungen
* @date 2015年8月25日 下午2:49:28 
*
 */
public class RemoteXMLRMI {
	private static final Logger LOG = Logger.getLogger(RemoteJavaRMI.class);

	public void process(BufferedReader request, PrintWriter resp,
			RemoteConfigBean bean, Socket socket) {
		String reqString = "";
		String tem = "";
		EngineBean pb = new EngineBean();
		try{
			while ((tem = request.readLine()) != null && !CommonBook.REMOTE_SKIP_BOOK.equals(tem)) {
				reqString += tem;	
			}
			DeployFileManage deploy = DeployFileManage.instance();
			XStream xstream = deploy.getXStreamInstance(null);
			setXML(xstream);
			EngineBean remoteBean = (EngineBean) xstream.fromXML(reqString);
			if(remoteBean.getAdjustCode() == null || remoteBean.getCommand() != null ){
				 throw new  SystemException(ErrorBook.PARAM_ESS_ERROR,"Naming or adjustid is null");
			}
			pb = ActionCore.instance().process(remoteBean);
			pb.setErrorType("");
			pb.setResultCode("0000");
			pb.setResultMsg("SUCCESS");
		}
		catch (ExceptionAbstract e){
			LOG.error(e);
			pb.setErrorType(e.getErrorType());
			pb.setResultCode(e.getErrorCode());
			pb.setResultMsg(e.getErrorMsg());
		}catch (Exception e) {
			LOG.error(e);
			pb.setResultCode(ErrorBook.OTHER_ERROR);
			pb.setResultMsg("system error!");
		}
		try {
			String str = SerializableRemoteBean.serializable(pb);
			resp.println(str);
			resp.println(CommonBook.REMOTE_SKIP_BOOK);
			resp.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void setXML(XStream xstream){
		  // xstream.alias("action-list", ArrayList.class);
		   xstream.alias("businessInter",EngineBean.class);
		   xstream.registerConverter(new MapCustomConverter(null));
	   }
}
