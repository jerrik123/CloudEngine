package com.mangocity.ce.core;

import org.apache.log4j.Logger;

import com.mangocity.ce.cache.ThreadManage;


/**
 * 
* @ClassName: FinallyProcess 
* @Description: TODO(必需要的派发对像) 
* @author Syungen
* @date 2015年8月25日 下午2:47:59 
*
 */
public class FinallyProcess {
	private static FinallyProcess uniqueInstance = null;
	private static final Logger LOG = Logger.getLogger(FinallyProcess.class);

	/**
	 * 文件配置管理类 取得实例
	 * @return DeployFileManage
	 */
	public static FinallyProcess instance() {
		if (uniqueInstance == null) {
			LOG.debug("new FinallyProcess() begin.....");
			uniqueInstance = new FinallyProcess();
			LOG.debug("new FinallyProcess() end .....");
		}
		return uniqueInstance;
	}

	private FinallyProcess() {
	}
	public void process(){
		ThreadManage.instance().removeNowThread();//移除当前线程的数据
	}
}
