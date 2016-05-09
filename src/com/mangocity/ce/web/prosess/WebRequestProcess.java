package com.mangocity.ce.web.prosess;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.mangocity.ce.bean.EngineBean;
import com.mangocity.ce.book.ErrorBook;
import com.mangocity.ce.book.SysBook;
import com.mangocity.ce.deploy.ConfigManage;
import com.mangocity.ce.exception.ExceptionAbstract;
import com.mangocity.ce.exception.SystemException;
import com.mangocity.ce.util.CommonUtils;
import com.mangocity.ce.validation.ParamProcessor;

/**
 * 
 * @ClassName: RequestProcess
 * @Description: (web请求处理类)
 * @author 刘春元
 * @date 2015年6月17日 上午9:42:36
 *
 */
public class WebRequestProcess {

	private static final Logger logger = Logger
			.getLogger(WebRequestProcess.class);
	private static WebRequestProcess uniqueInstance = null;

	private WebRequestProcess() {
		logger.debug("init RequestProcess ...");
	}

	public static WebRequestProcess getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new WebRequestProcess();
		}
		return uniqueInstance;
	}

	private static final Map<String, String> REGISTRY_MAP = new ConcurrentHashMap<String, String>();

	static {
		REGISTRY_MAP.put("plain", "text/plain; charset=UTF-8");
		REGISTRY_MAP.put("json", "text/json; charset=UTF-8");
		REGISTRY_MAP.put("javascript", "text/javascript; charset=UTF-8");
		REGISTRY_MAP.put("html", "text/html; charset=UTF-8");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void transformRequestParam(EngineBean bean) {
		HttpServletRequest req = bean.getRequest();
		req.getParameterNames();
		Enumeration enu = req.getParameterNames();
		while (enu.hasMoreElements()) {
			String key = enu.nextElement().toString();
			if ("classID".equals(key))
				bean.setCommand(req.getParameter(key));
			else
				bean.setHead(key, req.getParameter(key));
		}
	}

	public void setCharacterEncoding(EngineBean bean) throws SystemException {
		HttpServletRequest req = bean.getRequest();
		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 核心分发方法 如果指定分发处理类抛出自定义异常,则转换自定义异常code和msg返回到前端 如果是系统级异常,则抛出ES99 和 对应异常名
	 * 
	 * @param bean
	 * @return
	 * @throws ExceptionAbstract
	 */
	public Object distribution(EngineBean bean) throws ExceptionAbstract {
		String classID = bean.getCommand();
		Object obj = null;
		if(null != bean.getIsNosql() && "o".equals(bean.getIsNosql()))
			obj = ConfigManage.instance().getWebAction(classID+"_nosql");
		else
			obj = ConfigManage.instance().getWebAction(classID);
		if (null == obj) {
			throw new SystemException(this, ErrorBook.OTHER_ERROR, "没有该服务");
		}

		// 接口入参处理器
		ParamProcessor paramProcessor = ParamProcessor.instance();
		paramProcessor.setEngineBean(bean);
		paramProcessor.validate();

		// 校验不通过,则直接返回给客户端,不继续执行下面的逻辑
		if (CommonUtils.isNotBlank(bean.getResultCode())
				&& !SysBook.SUCCESS.equals(bean.getResultCode())) {
			logger.info("参数校验不通过: " + bean.getResultMsg());
			return bean;
		}

		Method method = null;
		Class classSys = null;
		try {
			classSys = Class.forName((String) obj);
			method = classSys.getDeclaredMethod(classID, EngineBean.class);
			obj = method.invoke(Class.forName((String) obj).newInstance(),
					new Object[] { bean });
			// Map map = new HashMap();
			// map.put("result", obj);
			// bean.setBodyMap(map);
			// bean.setResultCode(SysBook.SUCCESS);
			// bean.setResultMsg("请求成功.");

			// 处理
		} catch (SecurityException e) {
			logger.info("SecurityException...");
			definitionException(bean, e);
		} catch (NoSuchMethodException e) {
			logger.info("NoSuchMethodException...");
			definitionException(bean, e);
		} catch (IllegalArgumentException e) {
			logger.info("IllegalArgumentException...");
			definitionException(bean, e);
		} catch (IllegalAccessException e) {
			logger.info("IllegalAccessException...");
			definitionException(bean, e);
		} catch (InvocationTargetException e) {
			Throwable t = e.getTargetException();
			if (t instanceof ExceptionAbstract) {
				logger.info("操作失败...生成对应状态码");
				bean.setResultCode(((ExceptionAbstract) t).getErrorCode());
				bean.setResultMsg(((ExceptionAbstract) t).getErrorMsg());
			} else {
				bean.setResultCode(ErrorBook.OTHER_ERROR);
				bean.setResultMsg(t.getMessage());
				logger.error(e.getMessage(), e);
			}
			return bean;
		} catch (InstantiationException e) {
			logger.info("InstantiationException...");
			definitionException(bean, e);
		} catch (ClassNotFoundException e) {
			logger.info("ClassNotFoundException...");
			definitionException(bean, e);
		}
		return obj;
	}

	public void responseProcess(Object obj, HttpServletResponse response) {
		response.setContentType("application/json; charset=UTF-8");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		PrintWriter write;
		try {
			write = response.getWriter();
			SimplePropertyPreFilter filter = new SimplePropertyPreFilter(
					obj.getClass(), new String[] { "resultCode", "resultMsg",
							"bodyMap" });
			String resultString = JSON.toJSONString(obj, filter,
					SerializerFeature.WriteDateUseDateFormat);
			write.print(resultString);
			write.flush();
			write.close();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	private void definitionException(EngineBean bean, Exception e) {
		logger.info("definitionException...");
		bean.setResultCode(ErrorBook.OTHER_ERROR);
		bean.setResultMsg("系统错误...请等待");
		logger.error(e.getMessage(), e);
	}

}
