package com.mangocity.ce.bean;
/**
 * 
* @ClassName: RemoteConfigBean 
* @Description: TODO(远程配置线程bean) 
* @author Syungen
* @date 2015年8月25日 下午2:24:28 
*
 */
public class RemoteConfigBean {
	private int port;
	private String remoteName;
	private boolean automationStart;
	private boolean stop;
	private int threadNumber = 5;
	private String className="";
	private Object instance;

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getRemoteName() {
		return remoteName;
	}

	public void setRemoteName(String remoteName) {
		this.remoteName = remoteName;
	}

	public boolean isAutomationStart() {
		return automationStart;
	}

	public void setAutomationStart(boolean automationStart) {
		this.automationStart = automationStart;
	}

	public boolean isStop() {
		return stop;
	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}

	public int getThreadNumber() {
		return threadNumber;
	}

	public void setThreadNumber(int threadNumber) {
		this.threadNumber = threadNumber;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Object getInstance() {
		return instance;
	}

	public void setInstance(Object instance) {
		this.instance = instance;
	}
	
	
	
	

}
