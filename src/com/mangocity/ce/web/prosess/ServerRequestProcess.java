package com.mangocity.ce.web.prosess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.mangocity.ce.bean.EngineBean;
import com.mangocity.ce.book.ErrorBook;
import com.mangocity.ce.deploy.ConfigManage;
import com.mangocity.ce.exception.ExceptionAbstract;
import com.mangocity.ce.exception.IllegalParamException;
import com.mangocity.ce.exception.SystemException;
import com.mangocity.ce.util.CommonUtils;
import com.mangocity.ce.util.JsonUtil;
import com.mangocity.ce.validation.ParamProcessor;

/**
 * 
 * @ClassName: RequestProcess
 * @Description: (web请求处理类)
 * @author 刘春元
 * @date 2015年6月17日 上午9:42:36
 *
 */
public class ServerRequestProcess {

	private static final Logger logger = Logger
			.getLogger(ServerRequestProcess.class);
	private static ServerRequestProcess uniqueInstance = null;

	private ServerRequestProcess() {
		logger.debug("init RequestProcess ...");
	}

	public static ServerRequestProcess getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new ServerRequestProcess();
		}
		return uniqueInstance;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void transformRequestParam(EngineBean bean) throws IOException, IllegalParamException {
		BufferedReader bf = bean.getRequest().getReader();
		StringBuffer str = new StringBuffer();
		String readLine = "";
		while (null != (readLine = bf.readLine())) {
			str.append(readLine);
		}
		Map inMap = JsonUtil.decodeCmd(str.toString());
		String servletPath = bean.getRequest().getServletPath();
		bean.setCommand(servletPath.substring(servletPath.lastIndexOf("/") + 1,
				servletPath.lastIndexOf(".")));
		bean.setAdjustCode((String) inMap.get("adjustCode"));
		bean.setAppId((String) inMap.get("appId"));
		bean.setIsNosql((String) inMap.get("isNosql"));
		Map m = (Map) inMap.get("headMap");
		bean.setHeadMap(m);
	}

	public void setCharacterEncoding(EngineBean bean) throws SystemException, UnsupportedEncodingException {
		bean.getResponse().setCharacterEncoding("UTF-8");
		bean.getRequest().setCharacterEncoding("UTF-8");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object distribution(EngineBean bean) throws ExceptionAbstract {
		String actionId = bean.getCommand();
		Object obj = null;
		if(null != bean.getIsNosql() && "o".equals(bean.getIsNosql()))
			obj = ConfigManage.instance().getWebAction(actionId+"_nosql");
		else
			obj = ConfigManage.instance().getWebAction(actionId);
		if (null == obj) {
			// throw new
			// SystemException(this,ErrorBook.NOT_CLASS_ERROR,"找不到"+bean.getCommand()+"该服务");
		}
		// 接口入参处理器
		ParamProcessor paramProcessor = ParamProcessor.instance();
		paramProcessor.setEngineBean(bean);
		paramProcessor.validate();

		// 校验不通过,则直接返回给客户端,不继续执行下面的逻辑
		if (CommonUtils.isNotBlank(bean.getResultCode())
				&& !"00000".equals(bean.getResultCode())) {
			return bean;
		}
		Method method = null;
		Class classSys = null;
		try {
			classSys = Class.forName((String) obj);
			method = classSys.getDeclaredMethod(actionId, EngineBean.class);
			obj = method.invoke(Class.forName((String) obj).newInstance(),
					new Object[] { bean });
		} catch (Exception e) {//#desc: 当客户端调用出现异常,统一返回错误码  而不是直接出现异常错误
			logger.error(e.getMessage(), e);
			EngineBean eb = new EngineBean();
			eb.setResultCode(ErrorBook.CLIENT_CALL_ERROR);
			eb.setResultMsg(CommonUtils.strToStr(e.getMessage(), "客户端调用错误"));
			return eb;
		}
		return obj;
	}

	public void responseProcess(Object bean, HttpServletResponse response) {
		HttpServletResponse resp = response;
		resp.setContentType("text/plain; charset=UTF-8");
		resp.setHeader("Pragma", "No-cache");
		resp.setHeader("Cache-Control", "no-cache");
		resp.setDateHeader("Expires", 0);
		PrintWriter write;
		try {
			write = resp.getWriter();
			SimplePropertyPreFilter filter = new SimplePropertyPreFilter(
					bean.getClass(), new String[] { "resultCode", "resultMsg",
							"bodyMap" });
			String resultString = JSON.toJSONString(bean, filter,
					SerializerFeature.WriteDateUseDateFormat);
			write.print(resultString);
			write.flush();
			write.close();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
}
