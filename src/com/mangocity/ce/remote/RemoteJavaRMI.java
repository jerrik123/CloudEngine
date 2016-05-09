package com.mangocity.ce.remote;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.mangocity.ce.bean.EngineBean;
import com.mangocity.ce.bean.RemoteConfigBean;
import com.mangocity.ce.book.CommonBook;
import com.mangocity.ce.book.ErrorBook;
import com.mangocity.ce.core.ActionCore;
import com.mangocity.ce.exception.ExceptionAbstract;
import com.mangocity.ce.exception.SystemException;
import com.mangocity.ce.util.CommonUtils;
/**
 * 
* @ClassName: RemoteJavaRMI 
* @Description: TODO(远程调用并分发) 
* @author Syungen
* @date 2015年8月25日 下午2:49:12 
*
 */
public class RemoteJavaRMI {
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
			EngineBean remoteBean = (EngineBean) SerializableRemoteBean
					.unSerializable(reqString);
			if(remoteBean.getAdjustCode() == null || remoteBean.getCommand() == null ){
				 throw new  SystemException(ErrorBook.PARAM_ESS_ERROR,"Naming or adjustid is null");
			}
			pb = ActionCore.instance().process(remoteBean);
			Object result = pb.getBody("result");
			if(CommonUtils.isEmpty(result)){
				pb.setErrorType("");
				pb.setResultCode(ErrorBook.PARAM_ESS_ERROR);
				pb.setResultMsg("没有返回数据");
			}else if(result instanceof List){
				List list = (List)result;
				if(CommonUtils.isNotEmpty(list) && list.size()==0){
					pb.setErrorType("");
					pb.setResultCode(ErrorBook.PARAM_ESS_ERROR);
					pb.setResultMsg("没有返回数据");
				}else{
					pb.setErrorType("");
					pb.setResultCode("00000");
					pb.setResultMsg("SUCCESS");
				}
			}else if(result instanceof Map){
				Map map = (Map)result;
				if(CommonUtils.isNotEmpty(map) && map.isEmpty()){
					pb.setErrorType("");
					pb.setResultCode(ErrorBook.PARAM_ESS_ERROR);
					pb.setResultMsg("没有返回数据");
				}else{
					pb.setErrorType("");
					pb.setResultCode("00000");
					pb.setResultMsg("SUCCESS");
				}
			}else if(result instanceof String){
				String str = (String)result;
				if(CommonUtils.isBlank(str)){
					pb.setErrorType("");
					pb.setResultCode(ErrorBook.PARAM_ESS_ERROR);
					pb.setResultMsg("没有返回数据");
				}else{
					pb.setErrorType("");
					pb.setResultCode("00000");
					pb.setResultMsg("SUCCESS");
				}
			}else{
				pb.setErrorType("");
				pb.setResultCode("00000");
				pb.setResultMsg("SUCCESS");
			}
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
}
