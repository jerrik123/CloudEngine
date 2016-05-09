package com.mangocity.ce.core;

import org.apache.log4j.Logger;

import com.mangocity.ce.bean.EngineBean;
import com.mangocity.ce.exception.ExceptionAbstract;

/**
 * 
* @ClassName: ResponseProcess 
* @Description: TODO(处理核心派发类返回派发对像) 
* @author Syungen
* @date 2015年8月25日 下午2:48:31 
*
 */
public class ResponseProcess {
	private static ResponseProcess uniqueInstance = null;
	private static final Logger LOG = Logger.getLogger(ResponseProcess.class);

	/**
	 * 文件配置管理类 取得实例
	 * 
	 * @return DeployFileManage
	 */
	public static ResponseProcess instance() {
		if (uniqueInstance == null) {
			LOG.debug("new FinallyProcess() begin.....");
			uniqueInstance = new ResponseProcess();
			LOG.debug("new FinallyProcess() end .....");
		}
		return uniqueInstance;
	}

	private ResponseProcess() {
	}
	public void process(EngineBean bean) throws ExceptionAbstract{
		//bean.setResultList(resultList);
	}
}
