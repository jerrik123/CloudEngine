/**
 * 
 */
package com.mangocity.ce.validation;

import java.util.List;

import org.apache.log4j.Logger;

import com.mangocity.ce.bean.CheckBean;
import com.mangocity.ce.bean.CheckBean.SubParam;
import com.mangocity.ce.bean.CheckBean.SubParam.Rule;
import com.mangocity.ce.bean.EngineBean;
import com.mangocity.ce.deploy.ConfigManage;
import com.mangocity.ce.exception.IllegalParamException;
import com.mangocity.ce.util.CommonUtils;

/**
 * @Package com.mangocity.ce.validation
 * @Description : 参数校验器
 * @author YangJie
 * @email <a href='yangjie_software@163.com'>yangjie</a>
 * @date 2015-10-21
 */
public class ParamProcessor {

	private static ParamProcessor uniqueInstance = null;
	
	private EngineBean engineBean;

	private static final Logger log = Logger.getLogger(ParamProcessor.class);

	public static ParamProcessor instance() {
		if (uniqueInstance == null) {
			log.debug("new ParamProcessor() begin.....");
			uniqueInstance = new ParamProcessor();
			log.debug("new ParamProcessor() end .....");
		}
		return uniqueInstance;
	}
	
	private ParamProcessor() {}
	
	public void setEngineBean(EngineBean engineBean) {
		this.engineBean = engineBean;
	}
	/**
	 * 核心校验方法
	 * @throws IllegalParamException 
	 */
	public void validate() throws IllegalParamException{
		if(null == engineBean)
			return;
		doValidate();
	}
	
	/**
	 * 开始校验
	 * @throws IllegalParamException 
	 */
	private void doValidate() throws IllegalParamException {
		String command = engineBean.getCommand();
		log.debug("doValidate begin...command: " + command);
		CheckBean checkBean = (CheckBean)ConfigManage.instance().getCheckStyle(command);
		if(null == checkBean){
			return;
		}
		List<SubParam> paramList = checkBean.getParamList();
		String key = "";
		String keys[] = null;
		String value = "";
		String hint = "";//提示信息
		String paramName = "";
		String level = "";//校验参数的层级关系
		loopOuter:for(SubParam param : paramList){
			paramName = param.getName();
			level = param.getLevel();
			if(CommonUtils.isBlank(paramName))
				continue;
			loopIn:for(Rule rule : param.getRuleList()){
				key = rule.getKey();//规则key
				value = rule.getValue();
				if(CommonUtils.isBlank(key)) 
					continue;
				if(key.contains("|")){//<rule key="email|mobile"/> 校验规则用|分开
					keys = key.split("\\|");
					for(int i=0,len = keys.length;i<len;i++){
						if(!ConfigManage.instance().containsCheckKey(keys[i])){ 
							log.info("不存在"+keys[i]+"校验key");
							continue;
						}
						hint = ConfigManage.instance().getSysConfig(keys[i]);
						Validator validator = new Validator.Builder().checkKey(keys[i]).
								checkValue(value).paramName(paramName).level(level).hint(hint).engineBean(engineBean).build();
						if(!validator.process()){//如果没有处理成功,则返回错误码给客户端
							if(i != len-1){//如果不是最后一个校验规则
								engineBean.setResultCode(null);
								engineBean.setResultMsg(null);
							}
							continue;
						}else{
							break loopIn;
						}
						
					}
				}else{
					if(!ConfigManage.instance().containsCheckKey(key)){ 
						log.info("不存在"+key+"校验key");
						continue;
					}
					if(key.equals("regex")){//如果是正则,则直接输出配置文件中的hints属性
						hint = rule.getHints();
					}else{//否则取se_validate.properties中的提示信息
						hint = ConfigManage.instance().getSysConfig(key);
					}
					Validator validator = new Validator.Builder().checkKey(key).
							checkValue(value).paramName(paramName).level(level).hint(hint).engineBean(engineBean).build();
					if(!validator.process()){//如果没有处理成功,则返回错误码
						break loopOuter;
					}
				}
				log.debug("doValidate 参数名: " + paramName + " ,规则key: " + key + " ,规则value: " + value);
			}
		}
	}

	public void reset(){
		log.debug("ParamProcessor reset");
		this.engineBean = null;
	}
}
