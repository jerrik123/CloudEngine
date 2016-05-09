/**
 * 
 */
package com.mangocity.ce.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @Package com.mangocity.ce.bean
 * @Description : 规则树
 * @author YangJie
 * @email <a href='yangjie_software@163.com'>yangjie</a>
 * @date 2015-10-21
 */
public class CheckBean implements Serializable {
	/**对应的接口服务名id*/
	private String mangoId;
	/**接口参数列表*/
	private List<SubParam> paramList;
	
	public CheckBean() {
	}
	
	public class SubParam implements Serializable {
		/**参数名*/
		private String name;
		/**层级关系*/
		private String level;
		/**规则列表*/
		private List<Rule> ruleList;

		public SubParam() {
		}

		public class Rule implements Serializable {
			/**规则key*/
			private String key;
			/**规则对应的value*/
			private String value;
			
			private String hints;

			public Rule() {
			}

			public void setKey(String key) {
				this.key = key;
			}

			public void setValue(String value) {
				this.value = value;
			}

			public String getKey() {
				return key;
			}

			public String getValue() {
				return value;
			}
			
			public String getHints() {
				return hints;
			}

			public void setHints(String hints) {
				this.hints = hints;
			}

			@Override
			public String toString() {
				return "Rule [key=" + key + ", value=" + value + ", hints="
						+ hints + "]";
			}

		}

		public void setName(String name) {
			this.name = name;
		}

		public void setRuleList(List<Rule> ruleList) {
			this.ruleList = ruleList;
		}

		public String getName() {
			return name;
		}
		
		public String getLevel() {
			return level;
		}

		public void setLevel(String level) {
			this.level = level;
		}

		public List<Rule> getRuleList() {
			return ruleList;
		}

		@Override
		public String toString() {
			return "SubParam [name=" + name + ", level=" + level
					+ ", ruleList=" + ruleList + "]";
		}
	}

	public void setMangoId(String mangoId) {
		this.mangoId = mangoId;
	}

	public void setParamList(List<SubParam> paramList) {
		this.paramList = paramList;
	}

	public String getMangoId() {
		return mangoId;
	}

	public List<SubParam> getParamList() {
		return paramList;
	}

	@Override
	public String toString() {
		return "CheckBean [mangoId=" + mangoId + ", paramList=" + paramList + "]";
	}
	
}
