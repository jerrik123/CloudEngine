package com.mangocity.ce.core;

import org.apache.log4j.Logger;

import com.mangocity.ce.bean.EngineBean;
import com.mangocity.ce.book.ErrorBook;
import com.mangocity.ce.exception.ExceptionAbstract;
import com.mangocity.ce.exception.SystemException;
/**
 * 
* @ClassName: ActionCore 
* @Description: (处理核心派发类) 
* @author 刘春元
* @date 2015年7月30日 下午6:08:42 
*
 */
public class ActionCore {
    //Action process
	private static ActionCore uniqueInstance = null;
	
	private static final Logger LOG = Logger.getLogger(ActionCore.class);

	/**
	 * 得到核心处理类实例
	 * 
	 * @return DeployFileManage
	 */
	public static ActionCore instance() {
		if (uniqueInstance == null) {
			LOG.debug("new FinallyProcess() begin.....");
			uniqueInstance = new ActionCore();
			LOG.debug("new FinallyProcess() end .....");
		}
		return uniqueInstance;
	}

	private ActionCore() {
	}
	
	public EngineBean process(EngineBean bean) throws ExceptionAbstract{
		EngineBean result = null;
		try{
			InitProcess.instance().process(bean);//初始化处理
			RequestProcess.instance().process(result,bean);//请求业务处理
			ResponseProcess.instance().process(result);//返回值对像处理
		}finally{
			FinallyProcess.instance().process();
		}
		return bean;
	}
}
