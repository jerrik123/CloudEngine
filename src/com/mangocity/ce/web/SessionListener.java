package com.mangocity.ce.web;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import com.mangocity.ce.book.CacheBook;
import com.mangocity.ce.cache.SessionManage;
import com.mangocity.ce.cache.ThreadManage;
import com.mangocity.ce.deploy.ConfigManage;

/**
 * 
 * @ClassName: SessionListener
 * @Description: (监听session)
 * @author 刘春元
 * @date 2015年6月16日 上午9:29:59
 *
 */
public class SessionListener implements HttpSessionBindingListener {

	static {
		ConfigManage.instance();
	}
	@Override
	public void valueBound(HttpSessionBindingEvent event) {
		System.out.println(22222);
	}

	@Override
	public void valueUnbound(HttpSessionBindingEvent event) {
		System.out.println(33333);
		ThreadManage.instance().put(CacheBook.SESSION_KEY_CACHE_BOOK,
				event.getSession().getId());
		SessionManage.instance().removeNowSesson();
	}

}
