package com.mangocity.ce.deploy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.mangocity.ce.exception.ExceptionAbstract;
import com.mangocity.ce.util.StringUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * 
* @ClassName: DeployFileManage 
* @Description: (文件配置管理类) 
* @author 刘春元
* @date 2015年6月18日 上午9:27:23 
*
 */
public class DeployFileManage {
	private static DeployFileManage uniqueInstance = null;
	private static final Logger LOG = Logger.getLogger(DeployFileManage.class);

	/**
	 * 文件配置管理类 取得实例
	 * 
	 * @return DeployFileManage
	 */
	public static DeployFileManage instance() {
		if (uniqueInstance == null) {
			LOG.debug("new DeployFileManage() begin.....");
			uniqueInstance = new DeployFileManage();
			LOG.debug("new DeployFileManage() end .....");
		}
		return uniqueInstance;
	}

	private DeployFileManage() {
	}

	/**
	 * 读取配置文件
	 * 
	 * @param path
	 * @param reag
	 * @return
	 * @throws ExceptionAbstract
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Object> readDeployFile(String path, String reag,XStream xs)
			throws ExceptionAbstract {
		LOG.debug("readDeployFile filepath:"+ path +" begin.....");
		ArrayList<Object> list = new ArrayList<Object>();
		ArrayList<String> pathList = seekAllFilePath(path,reag,null);
		 FileInputStream fis = null;
		 InputStreamReader isr =null;
		for(String filePath : pathList){
			try{
				fis = new FileInputStream(new File(filePath));
				isr = new InputStreamReader(fis,Charset.forName("UTF-8"));
				ArrayList<Object> objList = (ArrayList<Object>) xs.fromXML(isr);
				for(Object obj : objList){
				    list.add(obj);
				}
			}catch(Exception e){
				LOG.error(e);
			}finally{
				if(null != isr){
					try {
						isr.close();
					} catch (IOException e) {LOG.error(e);
					}
				}
				if(null != fis){
					try {
						fis.close();
					} catch (IOException e) {LOG.error(e);
					}
				}
				System.gc();
			}
		}
		LOG.debug("readDeployFile filepath:"+ path +" end.....");
		return list;
	}

	/**
	 * 写入配置文件
	 * 
	 * @param path
	 * @param reag
	 * @return
	 * @throws ExceptionAbstract
	 */
	public void writeDeployFile(String file,XStream xs,Object xmlBean)
			throws ExceptionAbstract {
		 LOG.debug("writeDeployFile fineName:"+ file +" begin.....");
		 FileOutputStream fos = null;
		 OutputStreamWriter osr =null;
		try {
			fos = new FileOutputStream(new File(file));
			osr = new OutputStreamWriter(fos,Charset.forName("UTF-8"));   
			xs.toXML(xmlBean, osr);
		} catch (Exception e) {
		     LOG.error(e);
		}finally{
			if(null != osr){
				try {
					osr.close();
				} catch (IOException e) {LOG.error(e);
				}
			}
			if(null != fos){
				try {
					fos.close();
				} catch (IOException e) {LOG.error(e);
				}
			}
			System.gc();
		}
		LOG.debug("writeDeployFile fineName:"+ file +" end.....");
	}

	/**
	 * 
	 * @param rootPaht
	 *            根路径
	 * @param regex
	 *            文件目录下的正则匹配
	 * @return 所有文件的根路径
	 */
	public ArrayList<String> seekAllFilePath(String rootPaht, String regex,
			ArrayList<String> returnFilePath) {
		LOG.debug("seek file path:" + rootPaht + " regex:" + regex
				+ " begin...");
		File file = new File(rootPaht);
		returnFilePath = null == returnFilePath ? new ArrayList<String>()
				: returnFilePath;
		if (!file.isDirectory()) {
			if (null == regex || "".equals(regex)
					|| StringUtil.stringFilter(file.getName(), regex)) {
				returnFilePath.add(file.getPath());
				LOG.debug("add path:" + file.getPath());
			}
		} else if (file.isDirectory()) {

			String[] filelist = file.list();
			for (int i = 0; i < filelist.length; i++) {
				file = new File(rootPaht + "/" + filelist[i]);
				if (!file.isDirectory()) {

					if (null == regex || "".equals(regex)
							|| StringUtil.stringFilter(file.getName(), regex)) {
						returnFilePath.add(file.getPath());
						LOG.debug("add path:" + file.getPath());
					}
				} else if (file.isDirectory()) {
					seekAllFilePath(rootPaht + "/" + filelist[i], regex,
							returnFilePath);
				}
			}

		}
		LOG.debug("seek file path:" + rootPaht + " regex:" + regex + " end...");
		return returnFilePath;
	}
	/**
	 * 得到xstream 的操作类
	 * @param xstream
	 * @return
	 */
	public XStream getXStreamInstance(XStream xstream){
		return xstream == null ?new XStream(new DomDriver()):xstream;
	}
	
	/**
     * 读取Properties文件
     * @param path
     * @param map
     */
    public void readerProperties(String path,Map<String,String> map){
    	LOG.debug("reader properties:"+path +" begin...");
    	Properties p = new Properties();
    	try {
    		File pFile = new File(path);
    		FileInputStream pInStream=new FileInputStream(pFile );
			p.load(pInStream);
		} catch (IOException e) {
			LOG.error(e);
			return;
		}
    	Enumeration enu = p.propertyNames();
    	while( enu.hasMoreElements()){
    		String key = enu.nextElement().toString();
    		map.put(key,p.getProperty(key));
    		LOG.debug("reader properties:"+path +" key:"+key+"="+p.getProperty(key));
    	}
    	LOG.debug("reader properties:"+path +" end...");
    }
    /**
     * 得到文件的配置路径
     * @return
     */
    public String seekConfigPath(){
    	return DeployFileManage.class.getResource("/").getPath()+ "config/";
    }
}
