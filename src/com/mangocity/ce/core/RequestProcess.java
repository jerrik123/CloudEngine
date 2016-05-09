package com.mangocity.ce.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.BufferOverflowException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.mangocity.ce.bean.EngineBean;
import com.mangocity.ce.book.ErrorBook;
import com.mangocity.ce.deploy.ConfigManage;
import com.mangocity.ce.exception.BusinessException;
import com.mangocity.ce.exception.ExceptionAbstract;
import com.mangocity.ce.exception.SystemException;
import com.mangocity.ce.util.CommonUtils;

/**
 * 
 * @ClassName: RequestProcess
 * @Description: (核心派发业务处理)
 * @author 刘春元
 * @date 2015年7月31日 上午8:53:28
 *
 */
public class RequestProcess {
	private static RequestProcess uniqueInstance = null;
	private static final Logger LOG = Logger.getLogger(RequestProcess.class);

	/**
	 * 文件配置管理类 取得实例
	 * 
	 * @return DeployFileManage
	 */
	public static RequestProcess instance() {
		if (uniqueInstance == null) {
			LOG.debug("new FinallyProcess() begin.....");
			uniqueInstance = new RequestProcess();
			LOG.debug("new FinallyProcess() end .....");
		}
		return uniqueInstance;
	}

	private RequestProcess() {
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void process(EngineBean result,EngineBean bean) throws ExceptionAbstract {
		Object obj = null;
		if(null != bean.getIsNosql() && "o".equals(bean.getIsNosql()))
			obj = ConfigManage.instance().getWebAction(bean.getCommand()+"_nosql");
		else
			obj = ConfigManage.instance().getWebAction(bean.getCommand());
		if (null == obj) {
			 throw new SystemException(this,ErrorBook.NOT_CLASS_ERROR,"找不到"+bean.getCommand()+"该服务");
		}
		Method method = null;
		Class classSys = null;
		try {
			classSys = Class.forName((String) obj);
			method = classSys.getDeclaredMethod(bean.getCommand(),
					EngineBean.class);
			obj = method.invoke(Class.forName((String) obj).newInstance(),
					new Object[] { bean });
			bean.setBody("result", obj);
		} catch (SecurityException e) {
			definitionException(e);
		} catch (ClassNotFoundException e) {
			definitionException(e);
		} catch (NoSuchMethodException e) {
			definitionException(e);
		} catch (IllegalAccessException e) {
			definitionException(e);
		} catch (IllegalArgumentException e) {
			definitionException(e);
		} catch (InvocationTargetException e) {
			definitionException(e);
		} catch (InstantiationException e) {
			definitionException(e);
		}
	}
	
	//抛出自定义异常 并且能够暴露隐藏底层抛出的异常
	private void definitionException(Throwable e) throws SystemException{
		LOG.error(e.getMessage(), e);
		Throwable error = e.getCause();
		if(error instanceof ExceptionAbstract){
			ExceptionAbstract exception = (ExceptionAbstract)error;
			LOG.error("errorCode: " + exception.getErrorCode() + " ,errorMsg: " + exception.getErrorMsg());
			throw new SystemException(this,exception.getErrorCode(),exception.getErrorMsg());
		}else{
			LOG.error(error.getMessage());
			throw new SystemException(this,ErrorBook.OTHER_ERROR,CommonUtils.strToStr(error.getMessage(), "数据引擎出现异常"));
		}
	}
}