package com.mangocity.ce.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.mangocity.ce.bean.EngineBean;
import com.mangocity.ce.deploy.ConfigManage;
import com.mangocity.ce.exception.IllegalParamException;
import com.mangocity.ce.safe.SafeUtil;
import com.mangocity.ce.util.JsonUtil;

public class MbrPost {
	private static final Logger log = Logger.getLogger(MbrPost.class);

	/**
	 * Post Request
	 * @return
	 * @throws Exception
	 */
	public static String doPost(String url, EngineBean pb) throws Exception {
		pb.setAppId(ConfigManage.instance().getSysConfig("mbrserver.appid"));
		pb.setAdjustCode(SafeUtil.MD5(SafeUtil.MD5(pb.getAppId() + url.split("\\.")[0])
				+ ConfigManage.instance().getSysConfig("mbrserver.appkey")));
		String userAgent = pb.getRequest().getHeader("user-agent");
		log.info("userAgent..." + userAgent);
		pb.setBase("deviceInfo", ServletUtil.getOS(userAgent));
		pb.setBase("IP", ServletUtil.getRemoteAddress(pb.getRequest()));
		return doURLConnection(url, JSON.toJSONString(pb));
	}

	/**
	 * doPost重载方法(调用其他项目的HTTP服务)
	 * @param url
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public static String doPost(String url, String json) throws Exception {
		return doURLConnection(url, json);
	}

	private static String doURLConnection(String url, String json) throws MalformedURLException, IOException,
			ProtocolException, Exception, UnsupportedEncodingException {
		AssertUtils.assertBlank(url);
		AssertUtils.assertBlank(json);
		URL localURL = new URL(url);
		URLConnection connection = localURL.openConnection();
		HttpURLConnection httpURLConnection = (HttpURLConnection) connection;

		httpURLConnection.setDoOutput(true);
		httpURLConnection.setRequestMethod("POST");
		httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
		httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
		httpURLConnection.setRequestProperty("Content-Length", String.valueOf(json.length()));

		OutputStream outputStream = null;
		OutputStreamWriter outputStreamWriter = null;
		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader reader = null;
		StringBuffer resultBuffer = new StringBuffer();
		String tempLine = null;

		try {
			outputStream = httpURLConnection.getOutputStream();
			outputStreamWriter = new OutputStreamWriter(outputStream, Charset.forName("UTF-8"));

			outputStreamWriter.write(json.toString());
			outputStreamWriter.flush();

			if (httpURLConnection.getResponseCode() >= 300) {
				throw new Exception("HTTP Request is not success, Response code is "
						+ httpURLConnection.getResponseCode());
			}

			inputStream = httpURLConnection.getInputStream();
			inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			reader = new BufferedReader(inputStreamReader);

			while ((tempLine = reader.readLine()) != null) {
				resultBuffer.append(tempLine);
			}

		} finally {

			if (outputStreamWriter != null) {
				outputStreamWriter.close();
			}

			if (outputStream != null) {
				outputStream.close();
			}
			if (reader != null) {
				reader.close();
			}
			if (inputStreamReader != null) {
				inputStreamReader.close();
			}
			if (inputStream != null) {
				inputStream.close();
			}

		}

		return resultBuffer.toString();
	}

	@SuppressWarnings({ "unchecked"})
	public static EngineBean getEBByString(String str, EngineBean eb) throws IllegalParamException {
		Map<String, Object> map = JsonUtil.decodeCmd(str);//json转Map
		Set<String> set = new CopyOnWriteArraySet<String>(map.keySet());
		for (Object key : set) {
			if (((String) key).contains("resultMsg")) {
				eb.setResultMsg((String) map.get(key));
			}
			if (((String) key).contains("resultCode")) {
				eb.setResultCode((String) map.get(key));
			}
			if (((String) key).contains("bodyMap")) {
				eb.setBodyMap((Map<String, Object>) map.get(key));
			}
		}
		return eb;
	}
}
