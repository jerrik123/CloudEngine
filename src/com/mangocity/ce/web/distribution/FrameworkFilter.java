/**
 * 
 */
package com.mangocity.ce.web.distribution;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.mangocity.ce.bean.EngineBean;
import com.mangocity.ce.deploy.ConfigManage;
import com.mangocity.ce.exception.ExceptionAbstract;
import com.mangocity.ce.util.CommonUtils;
import com.mangocity.ce.web.prosess.ServerRequestProcess;

/**
 * @Description : filter基类
 * @author YangJie
 * @email <a href='yangjie_software@163.com'>yangjie</a>
 * @date 2015-8-12
 */
public  class FrameworkFilter implements Filter {
	private static final Logger log = Logger.getLogger(FrameworkFilter.class);

	/** 编码 **/
	private String encode;

	/** 默认编码 **/
	private static final String DEFAUL_ENCODE = "UTF-8";

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		configurationResolver();
		if (log.isDebugEnabled()) {
			log.debug("FrameworkFilter init begin...");
		}
		encode = filterConfig.getInitParameter("encode");
		if (log.isDebugEnabled()) {
			log.debug("web.xml filter init-parm 【encode】is: "
					+ (CommonUtils.isBlank(encode) ? "null，系统自动设置默认UTF-8编码"
							: encode));
		}
		if (CommonUtils.isBlank(encode)) {
			encode = DEFAUL_ENCODE;
		}
	}

	private void configurationResolver() {
		ConfigManage.instance();
	}

	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) req;
		HttpServletResponse httpResponse = (HttpServletResponse) resp;
		doDispatcher(httpRequest, httpResponse);
	}

	/**
	 * 默认处理分发操作 并将json数据输出到页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	protected void doDispatcher(HttpServletRequest request,
			HttpServletResponse response) {
		ServerRequestProcess reqProcess = ServerRequestProcess.getInstance();
		EngineBean eb = new EngineBean();
		Object obj = null;
		eb.setRequest(request);
		eb.setResponse(response);
		try {
			reqProcess.setCharacterEncoding(eb);
			reqProcess.transformRequestParam(eb);
			obj = reqProcess.distribution(eb);
			reqProcess.responseProcess(obj, response);
		} catch (ExceptionAbstract e) {
			eb.setResultCode(e.getErrorCode());
			eb.setResultMsg(e.getErrorMsg());
			reqProcess.responseProcess(eb, response);
		} catch (IOException e) {
			eb.setResultCode("IO");
			eb.setResultMsg(e.getMessage());
			reqProcess.responseProcess(eb, response);
		}
	}

	/**
	 * 服务端跳转
	 * 
	 * @param url
	 * @param request
	 * @param response
	 */
	protected void dispatcher(String url, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			request.getRequestDispatcher(url).forward(request, response);
		} catch (ServletException e) {
			log.error("FrameworkFilter dispatcher error: " + e.getMessage(), e);
		} catch (IOException e) {
			log.error("FrameworkFilter dispatcher error: " + e.getMessage(), e);
		}
	}

	/**
	 * 重定向
	 * 
	 * @param url
	 * @param request
	 * @param response
	 */
	protected void redirect(String url, HttpServletResponse response) {
		try {
			response.sendRedirect(url);
		} catch (IOException e) {
			log.error("FrameworkFilter redirect error: " + e.getMessage(), e);
		}
	}

	@Override
	public void destroy() {
		if (log.isDebugEnabled()) {
			log.debug("FrameworkFilter destroy begin...");
		}
	}
	
}
