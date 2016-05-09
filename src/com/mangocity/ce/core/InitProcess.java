package com.mangocity.ce.core;

import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.mangocity.ce.bean.EngineBean;
import com.mangocity.ce.exception.ExceptionAbstract;

/**
 * 
* @ClassName: InitProcess 
* @Description: TODO(处理核心派发类初始化对像) 
* @author Syungen
* @date 2015年8月25日 下午2:48:16 
*
 */
public class InitProcess {
	private static InitProcess uniqueInstance = null;
	private static final Logger LOG = Logger.getLogger(InitProcess.class);

	/**
	 * 文件配置管理类 取得实例
	 * 
	 * @return DeployFileManage
	 */
	public static InitProcess instance() {
		if (uniqueInstance == null) {
			LOG.debug("new FinallyProcess() begin.....");
			uniqueInstance = new InitProcess();
			LOG.debug("new FinallyProcess() end .....");
		}
		return uniqueInstance;
	}

	private InitProcess() {
	}
	public void process(EngineBean bean) throws ExceptionAbstract{
		if(bean.getHeadMap() instanceof JSONObject){
			bean.setHeadMap(JSONObject.toJavaObject((JSONObject)bean.getHeadMap(), Map.class));
		}
	}
}
