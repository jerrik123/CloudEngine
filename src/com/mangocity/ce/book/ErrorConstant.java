package com.mangocity.ce.book;
/**
* @ClassName: ErrorConstant 
* @Description: (错误常量.公共错误码以1开头,业务错误码以其他数字开头) 
* @author 刘春元
* @date 2015年8月11日 下午3:42:43 
 */
public class ErrorConstant {
	/**"成功状态"*/
	public static final String SUCCESS = "000000";
	
	/**"参数为空"*/
	public static final String ERROR_PARAM_NULL_10000 = "10000";
	
	/**"插入数据失败"*/
	public static final String ERROR_INSERT_DATA_FAIL_10001 = "10001";
	
	/**"更新数据失败"*/
	public static final String ERROR_UPDATE_DATA_FAIL_10002 = "10002";
	
	/**"数据转换失败"*/
	public static final String ERROR_PARAM_PARSE_FAIL_10003 = "10003";
	
	/**"查询不到数据"*/
	public static final String ERROR_NO_RESULT_DATA = "10004";
	
	/**"删除数据失败"*/
	public static final String ERROR_DELETE_DATA_FAIL_10005 = "10005";
	
	/**"事务回滚"*/
	public static final String ERROR_TRANSACTION_ROLL_BACK_10006 = "10006";
	
	/**"暂时不支持操作"*/
	public static final String ERROR_UNSUPPORTED_OPERATION_10007 = "10007";
	
	/**"参数不合法"*/
	public static final String ERROR_PARAM_INVALID_10008 = "10008";
	
	/**"查询数据失败"*/
	public static final String ERROR_QUERY_FAILED_10009 = "10009";
	
	/**"数据异常"*/
	public static final String ERROR_DATA_EXCEPTION_10010 = "10010";
	
	public static class Point{
		/**"状态无效"*/
		public static final String ERROR_STUS_INVALID_20001 = "20001";
		
		/**"积分账户不存在"*/
		public static final String ERROR_ACCOUNT_NOT_EXIST_20002 = "20002";
		
		/**"积分余额不足"*/
		public static final String ERROR_INSUFFICIENT_BALANCE_20003 = "20003";
		
		/**"积分扣减失败"*/
		public static final String ERROR_CUT_POINT_FAIL_20004 = "20004";
		
		/**"积分账户已冻结"*/
		public static final String ERROR_POINT_ACCOUNT_FREEZE_20005 = "20005";
		
		/**"积分账户已注销"*/
		public static final String ERROR_POINT_ACCOUNT_Logout_20006 = "20006";
		
		/**"积分账户未激活"*/
		public static final String ERROR_POINT_ACCOUNT_NOT_ACCTIVATED_20007 = "20007";
		
		/**"扣减积分数少于0"*/
		public static final String ERROR_CUT_POINTS_LESS_THAN_ZERO_20008 = "20008";
		
		/**"操作积分余额失败"*/
		public static final String ERROR_OPER_POINT_BALANCE_FAIL_20009 = "20009";
		
		/**"积分已经奖励"*/
		public static final String ERROR_POINT_AWARDED_20010 = "20010";
		
		/**"退还积分大于扣减"*/
		public static final String ERROR_POINT_REFUND_MORETHAN_CUT_20011 = "20011";
		
		/**"集团积分不足"*/
		public static final String ERROR_POINT_CRM_POINTS_NOT_ENOUGH_20012 = "20012";
	}
	
	public static class Mbr{
		/**"会员账户不存在"*/
		public static final String ERROR_MBR_NOT_EXIST_30001 = "30001";
		/**"会员账户已经存在"*/
		public static final String ERROR_MBR_IS_EXIST_30002 = "30002";
		/**"会员账户已经冻结"*/
		public static final String ERROR_MBR_IS_FREEZE_30003 = "30003";
		/**"会员账户冲突"*/
		public static final String ERROR_MBR_IS_CONFLICT_30004 = "30004";
		/**"会员账户已经注销"*/
		public static final String ERROR_MBR_IS_LOGOUT_30005 = "30005";
		/**"多条相同会员记录"*/
		public static final String ERROR_MBR_DUPLICATE_RECORD_30006 = "30006";
		/**"会员没有绑定手机号"*/
		public static final String ERROR_MBR_NOT_BINDED_MOBILNO_30007 = "30007";
		/**"集团会员暂不支持积分扣减"*/
		public static final String ERROR_CRM_MBR_NOT_SUPPORT_POINT_CUT_30008 = "30008";
		/**"会员没激活"*/
		public static final String ERROR_MBR_NOT_ACTIVATED_30009 = "30009";
		/**会员数据库字段无效(不在维护范围之内)*/
		public static final String ERROR_MBR_FIELD_INVALID_30010 = "30010";
		/**该会员已经绑定了万里通*/
		public static final String ERROR_MBR_IS_BINDED_WLT_30011 = "30011";
		/**该会员不是集团会员*/
		public static final String ERROR_MBR_IS_NOT_CRM_MBR_30012 = "30012";
		/**账号或密码错误*/
		public static final String ERROR_MBR_USERNAME_OR_PASSWORD_ERROR_30013 = "30013";
	}
	
	public static class Sms{
		/**"短信校验失败"*/
		public static final String ERROR_SMS_VALIDATE_FAIL = "40001";
		/**"短信超时"*/
		public static final String ERROR_SMS_OVERTIME_FAIL = "40002";
	}
	
	public static class Thirdparty{
		/**"第三方服务已绑定"*/
		public static final String ERROR_OPENID_IS_BINDED = "50001";
	}
	
	//t_mbr_user
	public static class User{
		/**"管理员用户不存在"*/
		public static final String ERROR_USER_IS_NOT_EXIST = "60001";
		
		/**"管理员用户被注销"*/
		public static final String ERROR_USER_IS_LOGOUT = "60002";
		
		/**"管理员用户存在多条记录"*/
		public static final String ERROR_USER_EXIST_MULTI_RECORDS = "60003";
	}
	
	public static class UserDevice{
		/**"用户设备信息不存在"*/
		public static final String ERROR_USER_DEVICE_IS_NOT_EXIST = "70001";
		
	}
	
	public static class Passenger{
		/**"旅客不存在"*/
		public static final String ERROR_PASSENGER_IS_NOT_EXIST = "80001";
		
		/**"旅客信息已失效"*/
		public static final String ERROR_PASSENGER_INVALID = "80002";
		
		/**"证件信息不存在"*/
		public static final String ERROR_PASSENGER_CERTIFY_IS_NOT_EXIST = "80003";
		
		/**"证件信息已失效"*/
		public static final String ERROR_PASSENGER_CERTIFY_INVALID = "80004";
	}
	
	public static class Mbrship{
		/**"会籍stus不是激活状态"*/
		public static final String ERROR_MBRSHIP_IS_NOT_ACTIVED = "90001";
	}
	
	//积分互换
	public static class PointExchange{
		/**"万里通充值流水已经存在"*/
		public static final String ERROR_WANLIT_FILL_TRANS_IS_EXISTED = "100001";
		
		/**"万里通用户没有绑定芒果用户"*/
		public static final String ERROR_WLTACCOUT_IS_NOT_BINDED = "100002";
		
		/**"万里通用户已经绑定了芒果用户"*/
		public static final String ERROR_WLTACCOUT_IS_BINDED = "100003";
		
		/**"万里通用户绑定芒果失败"*/
		public static final String ERROR_WLTACCOUT_BIND_FAILURE = "100004";
		
		/**"万里通充值流水不存在"*/
		public static final String ERROR_WLTORDER_IS_NOT_EXIST = "100005";
	}
}
