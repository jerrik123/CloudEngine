/**
 * 
 */
package com.mangocity.ce.validation;

import java.text.MessageFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.mangocity.ce.bean.EngineBean;
import com.mangocity.ce.book.ErrorBook;
import com.mangocity.ce.exception.IllegalParamException;

import static com.mangocity.ce.util.CommonUtils.*;

import com.mangocity.ce.util.CommonUtils;
import com.mangocity.ce.util.ValidationUtil;

/**
 * @Package com.mangocity.ce.validation
 * @Description : 校验key解析器
 * @author YangJie
 * @email <a href='yangjie_software@163.com'>yangjie</a>
 * @date 2015-10-21
 */
public class Validator {
	private static Validator uniqueInstance = null;
	private static final Logger LOG = Logger.getLogger(Validator.class);
	
	/**参数值*/
	private final String paramValue;
	
	/**参数名*/
	private final String paramName;
	
	/**提示错误*/
	private final String hint;
	
	/**校验key*/
	private final String checkKey;
	
	/**校验value*/
	private final String checkValue;
	
	/**层级关系*/
	private final String level;
	
	/**空值数量*/
	private final long nullValCount;
	
	private List<Object> paramList = new ArrayList<Object>(8);
	
	private final EngineBean engineBean;
	
	/**给定层级关系 获得该层级关系下的所有value,和空值的数量*/
	private final static CheckLevelResolver levelResolver = new CheckLevelResolver();
	
	private String[] arrs;
	
	private Validator(Builder builder){
		paramValue = builder.paramValue;
		paramName = builder.paramName;
		hint = builder.hint;
		checkKey = builder.checkKey;
		checkValue = builder.checkValue;
		level = builder.level;
		nullValCount = builder.nullValCount;
		engineBean = builder.engineBean;
	}
	
	public static class Builder{
		private  String paramValue;
		
		private  String paramName;
		
		private  String checkKey;
		
		private  String checkValue;
		
		private  String hint;
		
		private String level;
		
		private long nullValCount;
		
		private  EngineBean engineBean;
		
		public Builder paramValue(String paramValue){
			this.paramValue = paramValue;
			return this;
		}
		
		public Builder paramName(String paramName){
			this.paramName = paramName;
			return this;
		}
		
		public Builder hint(String hint){
			this.hint = hint;
			return this;
		}
		
		public Builder checkKey(String checkKey){
			this.checkKey = checkKey;
			return this;
		}
		
		public Builder checkValue(String checkValue){
			this.checkValue = checkValue;
			return this;
		}
		
		public Builder level(String level){
			this.level = level;
			return this;
		}
		
		public Builder engineBean(EngineBean engineBean){
			this.engineBean = engineBean;
			return this;
		}
		
		public Validator build() throws IllegalParamException{
			if(isBlank(this.paramValue)){
				if(isBlank(level)){//如果没有设置层级关系,则直接取headMap中的key
					this.paramValue = (String)engineBean.getHead(this.paramName);
				}else{//否则解析level,取子层key
					LOG.info("check tree exist [level] property begin regex...");
					Map<String,Object> checkMap = levelResolver.resolve(engineBean.getHeadMap(),level,paramName);
					this.paramValue = (String) checkMap.get(CheckLevelResolver.VALUE_KEY);//该层级下的value
					this.nullValCount = CommonUtils.objectToLong(checkMap.get(CheckLevelResolver.NULL_VALUE_KEY), -1L);//该层级下的空值数量  如果大于1 说明同一层级下存在没填的情况  用于校验深层必填选项 link:line 161
					}
			}
			return new Validator(this);
		}
	}
	
	public boolean process() throws IllegalParamException{
		if(!isValidatedSucc()){
			response(ErrorBook.PARAM_ESS_ERROR, paramName);
			return false;
		}
		return true;
	}

	/**
	 * 如果不满足校验key,则返回false
	 * @param checkKey
	 * @return
	 * @throws IllegalParamException 
	 */
	public boolean isValidatedSucc() throws IllegalParamException {
		if(CommonUtils.isBlank(paramValue)){//解决参数没传进来 paramValue为空,导致空指针异常
			if("required".equals(checkKey)){
				paramList.add(paramName);
				return false;
			}
		}
		arrs = paramValue.split("`");
		if("required".equals(checkKey)){//必填校验
			paramList.add(paramName);
			if(nullValCount>0){//如果存在空值,则直接返回错误
				return false;
			}
			return MultiValidator.isNotBlank(arrs);
		}else if("email".equals(checkKey)){//邮箱
			paramList.add(paramName);
			if(isBlank(paramValue)) return true;//在进行校验之前,如果不是required 如果填了 就校验 否则不校验
			return MultiValidator.isEmail(arrs);
		}else if("mobile".equals(checkKey)){//手机
			paramList.add(paramName);
			if(isBlank(paramValue)) return true;
			return MultiValidator.isMobilePhone(arrs);
		}else if("date".equals(checkKey)){//日期格式校验
			paramList.add(paramName);
			if(isBlank(paramValue)) return true;
			return MultiValidator.parseDate(arrs);
		}else if("number".equals(checkKey)){//数字校验
			paramList.add(paramName);
			return MultiValidator.isNumeric(arrs);
		}else if("maxlength".equals(checkKey)){
			paramList.add(paramName);
			paramList.add(checkValue);
			if(isBlank(paramValue)) return true;
			return MultiValidator.isMaxLen(arrs,checkValue);
		}else if("minlength".equals(checkKey)){//最小长度校验
			paramList.add(paramName);
			paramList.add(checkValue);
			if(isBlank(paramValue)) return true;
			return MultiValidator.isMinLen(arrs,checkValue);
		}else if("rangelength".equals(checkKey)){//长度只能在指定范围
			paramList.add(paramName);
			if(isBlank(paramValue)) return true;
			return MultiValidator.isRangeLenFor(paramList,arrs,checkValue);
		}else if("range".equals(checkKey)){//只能取指定值
			paramList.add(paramName);
			paramList.add(checkValue);
			if(isBlank(paramValue)) return true;
			return MultiValidator.isRangeFor(arrs,checkValue);
		}else if("max".equals(checkKey)){//最大校验
			paramList.add(paramName);
			paramList.add(checkValue);
			if(isBlank(paramValue)) return true;
			return MultiValidator.isLessThan(arrs,checkValue);
		}else if("min".equals(checkKey)){//最小校验
			paramList.add(paramName);
			paramList.add(checkValue);
			if(isBlank(paramValue)) return true;
			return MultiValidator.isMoreThan(arrs,checkValue);
		}else if("regex".equals(checkKey)){//正则校验
			paramList.add(paramName);
			paramList.add(checkValue);
			if(isBlank(paramValue)) return true;
			return MultiValidator.regexMatch(arrs,checkValue);
		}
		return false;
	}
	
	@SuppressWarnings("unused")
	public void response(String resultCode,String paramName){
		if(null == engineBean) return;
		engineBean.setResultCode(resultCode);
		engineBean.setResultMsg(MessageFormat.format(hint, paramList.toArray()));
		paramList.clear();
	}
	
}
