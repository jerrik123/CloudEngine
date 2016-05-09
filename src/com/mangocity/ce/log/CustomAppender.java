package com.mangocity.ce.log;

import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Priority;

/**   
 * @Package com.mangocity.ce.log 
 * @Description :  自定义log4j Appender,按级别输出到不同log文件
 * @author YangJie
 * @email <a href='yangjie_software@163.com'>yangjie</a>
 * @date 2015-8-11
 */
public class CustomAppender extends DailyRollingFileAppender {

	@Override
	public boolean isAsSevereAsThreshold(Priority priority) {
		// 只判断是否相等，而不判断优先级
		return this.getThreshold().equals(priority);
	}
}
