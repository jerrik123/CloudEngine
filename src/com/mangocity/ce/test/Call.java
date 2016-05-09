package com.mangocity.ce.test;

import java.io.IOException;
import java.net.UnknownHostException;

import com.mangocity.ce.bean.EngineBean;
import com.mangocity.ce.remote.ClientCall;

public class Call {
	public static void main(String[] args) {
		EngineBean b = new EngineBean();
		b.setAdjustCode("aaa");
		try {
			ClientCall.instance().call("127.0.0.1", 1120, b);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
