package com.mangocity.ce.book;

public class ConstantArgs {

	public static String OPERATE_CRM_ERROR = "服务异常,暂时不能进行该操作!";

	// P平安万里通商旅会员(0000000099) -- P平安万里通普通会员(0000000100)
	public static String PINGAN_WANLITONG_SHANGLV_MBR = "0000000099";
	// P平安万里通普通会员(0000000100)
	public static String PINGAN_WANLITONG_PUTONG_MBR = "0000000100";

	// 积分接口--积分调整类型
	public static final String POINT_INT_ADJUSTTYPE = "C";
	// 积分接口--积分交易状态
	public static final String POINT_INT_TRANSSTATUS = "P";

	// 积分交易属性：本地
	public static final String POINT_TRANS_ATTRIBUTE_MANGOCTIY = "0";
	// 积分交易属性：上传集团成功
	public static final String POINT_TRANS_ATTRIBUTE_CRM_SUCCESS = "1";
	// 积分交易属性：上传集团失败
	public static final String POINT_TRANS_ATTRIBUTE_CRM_FAIL = "2";

	// 积分操作类型_奖励
	public static final String POINT_TRANS_TYPE_AWARD = "A";
	// 积分操作类型_消费
	public static final String POINT_TRANS_TYPE_ADJUSTMENT = "C";
	// 积分操作类型_调整
	public static final String POINT_TRANS_TYPE_ADJUST = "J";

	// 积分操作类型_兑换
	public static final String POINT_TRANS_TYPE_CONVERT = "I";

	// 积分操作类型_充值
	public static final String POINT_TRANS_TYPE_RECHARGE = "F";

	// 积分账户状态_冻结
	public static final String POINT_AOCCUNT_STUS_FREEZE = "2";
	// 积分账户状态_作废
	public static final String POINT_AOCCUNT_STUS_CANCELLATION = "3";

	// 会员属性：港中旅会员
	public static final String MBR_ATTRIBUTE_MBR = "1";
	// 会员属性：普通客户
	public static final String MBR_ATTRIBUTE_CUSTOMER = "0";

	public static final String MBR_POINTNAME = "港中旅积分";

	public static final String MBR_PROGRAMNAME = "港中旅集团忠诚度计划";

	public static final String OPER_POINT_STATE = "Processed";

	// 集团已使用积分 --> 芒果网消费积分，主流水表类型为C
	public static final String CTS_USER_POINT_SUB_TYPE = "已使用积分";

	public static final String CTS_RECHARGE_POINT_SUB_TYPE = "购买积分";

	public static final String CTS_AWARD_POINT_SUB_TYPE = "奖励积分";

	public static final String CTS_CONVERT_POINT_SUB_TYPE = "互换积分";

	public static final String CTS_CONSUME_POINT_SUB_TYPE = "消费积分";

	// 默认芒果网会籍-(简体网站)
	public static final String DEFULT_MBRSHIP = "0100100001";
	// 默认芒果网会籍-(繁体网站)
	public static final String DEFULT_TRADITIONAL_MBRSHIP = "0500100001";

	// 积分余额状态
	public static final String POINT_BALANCE_STUS_USERLESS = "A";

	// 交易类型：消费
	public static final Long Transact_TYPE_ADJUSTMENT = Long.valueOf(1130);
	// 交易类型：退回
	public static final Long Transact_TYPE_EXIT = Long.valueOf(1131);

	// 会员卡类型：标准卡
	public static final String LOY_MEMBER_CARD_TYPE_STANDARD = "A00000";

	// 交易类型：应记(增加积分)
	public static final String LOY_TXN_TYPE_CD_ACCRUAL = "ACCRUAL";
	// 交易类型：偿还(扣减积分)
	public static final String LOY_TXN_TYPE_CD_REDEMPTION = "REDEMPTION";

	// 合作方编码：默认芒果网
	public static final String LOY_PARTNER_LOCATION_MANGO = "66";

	// 积分操作成功
	public static final Long POINT_OPER_SUCCESS = Long.valueOf(1);
	// 积分操作失败
	public static final Long POINT_OPER_FAILT = Long.valueOf(0);
	// 积分不足
	public static final Long POINT_OPER_EMPTY = Long.valueOf(-1);
	// 积分操作时集团出错
	public static final Long POINT_OPER_ERROR = Long.valueOf(-2);

	// 积分账户不可用
	public static final Long POINT_ACCOUNT_ERROR = Long.valueOf(-3);
	// 来源板块公司；来源；1：芒果网；2：港中旅集团；3;中旅社；4：中酒
	// 数据库值
	public static final String SRC_MANGO = "1"; // 1：芒果网
	public static final String SRC_GANGZHONG_BLOC = "2"; // 2：港中旅集团
	public static final String SRC_ZHONG_HOSTEL = "3"; // 3：中旅社
	public static final String SRC_ZHONGJIU = "4"; // 4：中酒
	// 所显示值
	public static final String SRC_MANGO_VALUE = "芒果网"; // 1：芒果网
	public static final String SRC_GANGZHONG_BLOC_VALUE = "港中旅集团"; // 2：港中旅集团
	public static final String SRC_ZHONG_HOSTEL_VALUE = "中旅社"; // 3：中旅社
	public static final String SRC_ZHONGJIU_VALUE = "中酒"; // 4：中酒

	// 历史配送地址 配送方式 1;免费邮寄；2：快递到付；3：机场自取；4：营业部自取；5：上门送票
	// 数据库值
	public static final Long SEND_TYP_FREEDELIVERY = Long.valueOf(1); // 免费邮寄
	public static final Long SEND_TYP_EXPRESS_PAYMENT = Long.valueOf(2); // 快递到付
	public static final Long SEND_TYP_AIRPORT_TICKET = Long.valueOf(3); // 机场自取
	public static final Long SEND_TYP_SALESDEPARTMENT_TICKET = Long.valueOf(4); // 营业部自取
	public static final Long SEND_TYP_DROPIN_TICKET = Long.valueOf(5); // 上门送票
	// 所显示值
	public static final String SEND_TYP_FREEDELIVERY_VALUE = "免费邮寄"; // 1:免费邮寄
	public static final String SEND_TYP_EXPRESS_PAYMENT_VALUE = "快递到付"; // 2:快递到付
	public static final String SEND_TYP_AIRPORT_TICKET_VALUE = "机场自取"; // 3:机场自取
	public static final String SEND_TYP_SALESDEPARTMENT_TICKET_VALUE = "营业部自取"; // 4:营业部自取
	public static final String SEND_TYP_DROPIN_TICKET_VALUE = "上门送票"; // 5:上门送票

	// 联名卡表 状态
	// 数据库值
	public static final String ALIAS_CARD_STUS = "A"; // 联名卡状态 A表有效
	// 所显示值
	public static final String ALIAS_CARD_STUS_EFFECTIVE = "有效"; // 有效
	public static final String ALIAS_CARD_STUS_INEFFICACY = "无效"; // 无效

	// 历史联系人/历史出行人 类型
	// 数据库值
	public static final String TICKET_HOTEL = "1"; // 类型：1;机票乘机人/酒店入住人
	public static final String TICKET_HOTEL_LINKMAN = "2"; // 类型：2:机票订单联系人/酒店订单联系人
	// 所显示值
	public static final String TICKET_HOTEL_VALUE = "机票乘机人/酒店入住人"; // 类型：1;机票乘机人/酒店入住人
	public static final String TICKET_HOTEL_LINKMAN_VALUE = "机票订单联系人/酒店订单联系人"; // 类型：2:机票订单联系人/酒店订单联系人

	// 现金余额状态 0： 无效 1： 正常
	// 数据库值
	public static final String INEFFICACY_VALUE = "0";
	public static final String UP_TO_SNUFF_VALUE = "1";
	// 所显示值
	public static final String INEFFICACY = "无效";
	public static final String UP_TO_SNUFF = "正常";

	// 现金余额操作
	public static final String MINUS = "minus"; // 表：减小现金余额
	public static final String ADD = "add"; // 表：增加现金余额

	public static final String domain = ".mangocity.com"; // cookie 域名

	// -----------CC 积分调整 常量配置---------------
	public static final String TRANSACTION_SUB_TYPE = "Product";// 交易子类型

	public static final String CC_TRANSACTION_CHANNEL = "12";// 交易渠道
	public static final String CC_CTSTRANSACTION_CHANNELSN = "661201000000";// /交易渠道序列号
	public static final String CC_CTSTRANSACTION_ORGCODE = "660000000000";// 交易机构代码
	public static final String CC_PART_NUMBER = "8866070000";// 三级产品目录

	// ---网站积分充值-----
	public static final String WEB_TRANSACTION_CHANNEL = "11";// 交易渠道

	public static final String WEB_CTSTRANSACTION_CHANNELSN = "661101000000"; // /交易渠道序列号

	public static final String WEB_CTSTRANSACTION_ORGCODE = "660000000000"; // 交易机构代码（芒果网公司）

	public static final String WEB_PART_NUMBER = "8866160000"; // 积分充值卡

	// ---网站积分互换
	public static final String WEB_CTSTRANSACTION_ORGCODE_MARKET = "661060000000"; // 芒果网市场营销部

	public static final String WEB_PART_NUMBER_IN = "8866130000";// 8866130000合作伙伴转入积分

	public static final String WEB_PART_NUMBER_OUT = "8866140000";// 8866140000积分转出至合作伙伴

	// 上传积分充值卡文件
	public static final String FILE_AMOUNT_OVERSIZE = "一次导入的数据量过大,导入失败！";

	public static final String FILE_REQ_FAILT = "此文件内容不合符规范不能导入!";

	public static final String FILE_IMPORT_ERROR = "导入数据发生异常！";
	// 充值卡状态：已领用
	public static final String RECHARGECARD_STUS_HOLD = "H";
	// 充值卡状态：已激活
	public static final String RECHARGECARD_STUS_ACTIVE = "AAS";
	// 充值卡状态：已冻结
	public static final String RECHARGECARD_STUS_FREEZE = "F";
	// 充值卡状态：已作废
	public static final String RECHARGECARD_STUS_NULLIFY = "N";
	// 充值卡状态：已充值
	public static final String RECHARGECARD_STUS_USERED = "RAU";

	// 充值卡操作状态：
	// 已激活
	public static final String RECHARGECARD_OPER_ACTIVE = "Active And Sale";
	// 已激活成功代码(从已领用激活)
	public static final String RECHARGECARD_OPER_ACTIVE_SUCCESS = "607";
	// 已激活成功代码(从已冻结激活)
	public static final String RECHARGECARD_OPER_ACTIVE_FROM_FREEZE_SUCCESS = "611";
	// 已冻结
	public static final String RECHARGECARD_OPER_FREEZE = "Freeze";
	// 已冻结成功代码
	public static final String RECHARGECARD_OPER_FREEZE_SUCCESS = "608";
	// 已充值
	public static final String RECHARGECARD_OPER_RECHARGED_AND_USED = "Recharged And Used";
	// 已充值成功代码
	public static final String RECHARGECARD_OPER_RECHARGED_AND_USED_SUCCESS = "610";

	// 会员注册常量
	// 手机号86-开头 MOBILE_EIGHTSIX手机区号
	public static final String EIGHTSIX = "86-";
	public static final String MOBILE_EIGHTSIX = "86";
	// 手机86开头后的符号"-" 验证中英文名是否有传入"-"
	public static final String SYMBOL = "-";
	// 表手机号已存在
	public static final String OBILENO_EXIST = "mobileNoExist";
	// 集团系统异常
	public static final String BLOC_EXIST = "blocExist";
	// Email已存在
	public static final String EMAILADDR_EXIST = "emailAddrExist";
	// 用户名已存在
	public static final String MBR_NET_NAME_EXIST = "mbrNetNameExist";
	// 验证用户名已存在的类型值N 表示用户 M表示手机 E表示Email 联名卡类型B66 C表示会籍卡号 A00000表示标准卡 C66合作卡类型
	public static final String N = "N";
	public static final String M = "M";
	public static final String E = "E";
	public static final String MOBILE = "mobile";
	public static final String EMAIL = "email";
	public static final String LOGIN_NAME = "loginName";
	public static final String C = "C"; // 合作卡
	public static final String B = "B"; // 联名卡
	public static final String A = "A"; // 标准卡
	public static final String C_VALUE = "合作卡"; // 合作卡
	public static final String B_VALUE = "联名卡"; // 联名卡
	public static final String A_VALUE = "标准卡"; // 标准卡
	public static final String CARD_TYPE_B66 = "B66";
	public static final String CARD_TYPE_C66 = "C66";
	// 验证用户名集团所返回的错误编码132 表示用户名已存在 133表示用户名可用
	public static final String BLOC_ERROR_NO = "132";
	public static final String BLOC_ERROR_NO_USER_OK = "133";
	public static final String BLOC_ERROR_NO_FAIL = "154";
	// 证件号已存在
	public static final String CERT_NO_IS_EXIST = "certNoIsExist";
	// 卡号已存在
	public static final String CARD_NO_EXIST = "CardNoExist";
	// 登录时注册IsLoginType给的默认值 bizRegister表示商旅注册
	public static final String IS_LOGIN_TYPE = "login";
	public static final String IS_REGISTER_BIZ = "bizRegister";
	// 会籍类型 此处固定为芒果网会籍 555M芒果会籍-芒果网站 556M芒果会籍-呼叫中心
	public static final String MANGO_MBRSHIP_TYPE = "555";
	public static final String MANGO_CC_MBRSHIP_TYPE = "556";
	// 登录时注册同步集团的Attribute的默认值
	public static final Integer ATTRIBUTE_ONE = 1;
	public static final Integer ATTRIBUTE_TWO = 2;
	// 判断为集团登录
	public static final String LOGIN_REGISTER = "loginRegister";
	public static final String IS_LOGIN_TYPE_REGISTER = "register";
	// 注册来源板块公司；来源；66：芒果网；88：港中旅集团；3;中旅社；4：中酒
	public static final String SRC_SIX = "66";
	// 注册来源版块 661201000000表示CC注册版块 661101000000表示WEB注册版块
	public static final String REGISTER_SRC_ID_CC = "661201000000";
	public static final String REGISTER_SRC_ID_WEB = "661101000000";
	public static final String REGISTER_SRC_ID_BIG5 = "661102000000";
	// 登录时注册 将集团的数据注册到会员3.0中 创建人为System
	public static final String CREATE_BY_SYSTEM = "System";
	// 会员状态 1表示正常
	public static final String MBR_STUS_ONE = "1";
	// 会员更新是区分是CC 更新还是WEB 更新
	public static final String CC = "CC";
	public static final String WEB = "WEB";
	public static final String BROADCAST = "BROADCAST";
	// 注册同步集团失败
	public static final String BLOC_FAILURE = "blocFailure";
	// 集团异常
	public static final String BLOC_SYSTEM_ERROR = "blocSystemError";
	// 集团中英文名为空时给定的默认
	public static final String NBSP = "nbsp;";
	public static final String _NBSP = "-";
	public static final String NOT_AVAILABLE = "暂缺";
	// 兴趣 推广活动形式 饮食 选中值为1表示选中 Y表示选中的值传给集团为Y N表示没选中的值传入集团为N
	public static final String CHOICE_ONE = "1";
	public static final String CHOICE_Y = "Y";
	public static final String CHOICE_N = "N";
	// 客户信息在集团中 客户状态 CTSContactStatus为Active表示正常 Duplicate表示重复会员
	public static final String CTSCONTACT_STATUS_ACTIVE = "Active";
	public static final String CTSCONTACT_STATUS_DUPLICATE = "Duplicate";
	// 客户类型 0： Potential表示为潜在客户 1：Official表示为正式客户 CTS_Excellent优质客户
	// 2:Active则集团中表示为Active 表活动 3表示为沉默客户 则集团中表示为CTS_Silent
	public static final String CUSTOMER_VALUE_ZERO = "0";
	public static final String CUSTOMER_VALUE_POTENTIAL = "Potential";
	public static final String CUSTOMER_VALUE_ONE = "1";
	public static final String CUSTOMER_VALUE_OFFICIAL = "Official";
	public static final String STATUS_CTS_EXCELLENT = "CTS_Excellent";
	public static final String STATUS_TWO = "2";
	public static final String STATUS_ACTIVE = "Active";
	public static final String STATUS_THREE = "3";
	public static final String STATUS_CTS_SILENT = "CTS_Silent";
	public static final String STATUS_FOUR = "4";
	public static final String STATUS_CTS_LOSE = "CTS_Lose";
	// 会籍状态
	public static final String STATUS_ISSUED = "Issued"; // Issued表示已发卡
	public static final String STATUS_INVALID = "Invalid"; // Invalid表示已作废
	// 注册来源 CTSCardSegment 12表示呼叫中心 11表示网站
	public static final String CTS_CARD_SEGMENT_12 = "12";
	public static final String CTS_CARD_SEGMENT_11 = "11";
	// 状态 注册表中状态的值为0 则表示注销 集团中表非活动 1表示正常 集团中表示活动
	public static final String ACTIVE_FLAG_N = "N";
	public static final String ACTIVE_FLAG_Y = "Y";
	public static final String ACTIVE_FLAG_ZERO = "0";
	public static final String ACTIVE_FLAG_ONE = "1";
	// 集团的几种卡类型 会员卡类型 默认为标准卡 A00000 A表示标准卡类型 B表示联名卡类型 C表示合作卡类型
	public static final String CARD_TYPE_A00000 = "A00000";
	// 集团员工卡
	public static final String CARD_TYPE_E00000 = "E00000";
	public static final String A_CARD_TYPE = "A";
	public static final String B_CARD_TYPE = "B";
	public static final String C_CARD_TYPE = "C";
	// 注册渠道
	public static final String ENROLLMENTCHANNEL_WEB_11 = "11";
	// 会员类型 Individual表示个人会员 Account表示公司会员
	public static final String MBR_TYP_INDIVIDUAL = "Individual";
	public static final String MBR_TYP_ACCOUNT = "Account";
	// 会员级别 默认1级
	public static final String MBR_LOWEST_LEVEL_ONE = "1";
	// 身份证件类型
	public static final String ID_CARD_TYPE = "11";
	// 集团会籍添加到会员系统3.0时所用的标识符
	public static final String BLOC_MBRSHIP_ID = "blocMbrhipID";
	// 成功标识
	public static final String SUCCESS = "SUCCESS";
	// 注册时会籍默认状态
	public static final String DEFAULT_MBRSHIP_STATUS = "Active";
	// 地址类型 1、Office Address:办公地址 ; 2、Family Address：家庭地址; 3、Post Address： 配送地址
	// ; 4、Other：其他地址; 5、Register Address：注册地址(默认).
	public static final String ADDRESS_TYPE_OFFICE_ADDRESS = "Office Address";
	public static final String ADDRESS_TYPE_FAMILY_ADDRESS = "Family Address";
	public static final String ADDRESS_TYPE_POST_ADDRESS = "Post Address";
	public static final String ADDRESS_TYPE_OTHER_ADDRESS = "Other";
	public static final String ADDRESS_TYPE_REGISTER_ADDRESS = "Register Address";
	// 验证传入的邮箱或证件号的状态是否有重复的
	public static final String EMAIL_TYPE = "Email邮箱地址";
	public static final String CERT_TYPE = "证件号";
	// 个人地址状态 Valid表示有效
	public static final String ADDRESS_STUS_VALID = "Valid";
	public static final String ADDRESS_STUS_INVALID = "Invalid";

	// 积分调整 读取txt文件时 所设 的字符编码
	public static final String POINT_AJUST_CHARACTER_ENCODING = "GBK";

	/**
	 * 积分永久有效的日期
	 */
	public static final String FULFILLMENT_EXPIRY_DATE = "29991231";

	/**
	 * 积分购买的原因代码
	 */
	public static final String ADJUSTREASONCODE_POINT_RECHARGE = "1140";

	// 芒果网本地对接集团系统业务编码

	// 会员基本资料模块
	public static final String MBR = "MBR";
	// 会籍模块
	public static final String MBRSHIP = "MBRSHIP";
	// 积分模块
	public static final String POINT = "POINT";
	// 上传服务内部接口
	public static final String CRMMBRUPLOAD = "CRMMBRUPLOAD";
	public static final String CRMMBRMANUAL = "CRMMBRMANUAL";

	public static final String CC_ALERT = "CC_ALERT";
	// 会员注册
	public static final String CTS_MBR_REGISTER = "CTS_MBR_REGISTER";
	// 会员更新
	public static final String CTS_MBR_UPDATE = "CTS_MBR_UPDATE";
	// 修改密码
	public static final String CTS_PASSWORD_UPDATE = "CTS_PASSWORD_UPDATE";
	// 忘记密码
	public static final String CTS_PASSWORD_FORGET = "CTS_PASSWORD_FORGET";
	// 会员状态设置
	public static final String CTS_MBR_STUS_SET = "CTS_MBR_STUS_SET";
	// 会员批量注册
	public static final String CTS_MBR_BATCH_REGISTER = "CTS_MBR_BATCH_REGISTER";
	// 新增会籍
	public static final String CTS_MBRSHIP_ADD = "CTS_MBRSHIP_ADD";
	// 会籍状态设置
	public static final String CTS_MBRSHIP_STUS_SET = "CTS_MBRSHIP_STUS_SET";
	// 添加个性需求
	public static final String CTS_MBR_SPEC_REQ = "CTS_MBR_SPEC_REQ";
	// 单个奖励积分
	public static final String CTS_POINT_AWARD = "CTS_POINT_AWARD";
	// 单个调整积分
	public static final String CTS_POINT_ADJUSTMENT = "CTS_POINT_ADJUSTMENT";
	// 积分操作
	public static final String CTS_POINT_OPER = "CTS_POINT_OPER";
	// 批量调整积分
	public static final String CTS_POINT_BATCH_ADJUSTMENT = "CTS_POINT_BATCH_ADJUSTMENT";
	// 各业务线积分交易
	public static final String CTS_POINT_TRANS = "CTS_POINT_TRANS";
	// 积分充值
	public static final String CTS_POINT_CARD = "CTS_POINT_CARD";
	// 积分查询
	public static final String CTS_POINT_QUERY = "CTS_POINT_QUERY";
	// CC查询
	public static final String CTS_CC_ALERT = "CTS_CC_ALERT";

	public static final String CTS_THREAD_STUTS_NO = "0";
	public static final String CTS_THREAD_STUTS_YES = "1";
	// 系统同步集团状态编码

	// 等待处理
	public static final String CTS_STUS_WAIT = "WAIT";
	// 挂起
	public static final String CTS_STUS_HANG = "HANG";
	// 人工处理
	public static final String CTS_STUS_MANUAL = "MANUAL";
	// 上传成功
	public static final String CTS_STUS_SUCCESS = "SUCCESS";
	// 上传失败
	public static final String CTS_STUS_FAIL = "FAIL";
	// 重复业务
	public static final String CTS_STUS_DUPLICATE = "DUPLICATE";

	// 上传服务执行成功
	public static final String CRM_UPLOAD_SUCCESS = "1";
	// 上传服务执行失败
	public static final String CRM_UPLOAD_FAIL = "-1";
	// 上传服务执行冲突
	public static final String CRM_UPLOAD_MANUAL = "0";
	// 上传服务执行NULL
	public static final String CRM_UPLOAD_NULL = "NULL";

	// 现金账户状态
	public static final Integer MBR_CASH_ACCOUNT_STATUS_LIVE = 1;

	public static final Integer MBR_CASH_ACCOUNT_STATUS_DIE = 0;

	// 芒果网通信配置

	/**
	 * 注册发送短信模板
	 */
	public static final String RSMC = "registerSendMobileC";

	/**
	 * 注册邮件模板
	 */
	public static final String RFNC = "registerFileNameC";

	/**
	 * 添加会籍发送短信模板
	 */
	public static final String ASS = "addShipSMS";

	/**
	 * 添加会籍发送短信模板(批量)
	 */
	public static final String BASS = "batchAddShipSMS";

	/**
	 * 添加会籍邮件模板
	 */
	public static final String ASE = "addShipEmail";

	/**
	 * 添加会籍邮件模板(批量)
	 */
	public static final String BASE = "batchAddShipEmail";

	// 赠送优惠券发送短信模板
	public static final String CSMC = "couponSendMobileC";
	// 注册赠送优惠券邮件模板
	public static final String RCNC = "registerCouponNameC";

	public static final String SMS_FROM = "59002";
	public static final String APP_NAME = "member";
	public static final String ELE_NAME = "member";
	public static final String EMAIL_FROM = "cs@mangocity.com";
	public static final String USER_LOGIN_IN = "test_MEMBER";
	public static final String DEF_SUBJECT = "芒果网会员";
	public static final String V_SUBJECT = "芒果网会员注册赠送代金券";

	// 集团会籍注册到芒果网统一指 G港中旅集团网站 会籍对应的ID
	public static final String CRM_PUBLIC_WEB_MBRSHIP_ID = "5020226";
	public static final String CRM_PUBLIC_WEB_MBRSHIP_CD = "9000600001";
	public static final String CTS_MBRSHIP_CATEGORY_CD = "A00000";
	// 集团员工会籍
	public static final String CRM_STAFF_MBRSHIP_ID = "12629888";
	public static final String CRM_STAFF_MBRSHIP_CD = "9315300001";
	public static final String CTS_STAFF_CATEGORY_CD = "E00000";

	// 会员数据更新相关类型
	/**
	 * 会员注册
	 */
	public static final String MBR_REGISTER = "01";
	/**
	 * 会员数据相关更新
	 */
	public static final String MBR_UPDATE_NORMALINFO = "02";
	/**
	 * 会员状态变更
	 */
	public static final String MBR_UPDATE_MBRSTATUS = "04";
	/**
	 * 添加/修改会籍
	 */
	public static final String MBR_UPDATE_MBRSHIP = "03";
	/**
	 * 会籍状态修改
	 */
	public static final String MBR_UPDATE_MBRSHIPSTATUS = "05";
}
