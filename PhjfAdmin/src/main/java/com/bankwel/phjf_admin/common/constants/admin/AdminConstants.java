package com.bankwel.phjf_admin.common.constants.admin;


import com.bankwel.framework.third.alisms.AliSmsClient;
import com.bankwel.framework.third.hl95.Hl95SmsClient;
import com.bankwel.phjf_admin.support.PhjfAdminAesKit;
import com.jfinal.kit.PropKit;

import java.util.HashMap;
import java.util.Map;

/**
 *@Description:后台管理常量类
 *@Author:liqing
 *@Since:2014年10月8日
 */
public class AdminConstants {
	public static final String PLATFORM = "PhjfAdmin";//平台
	//管理员SESSION的key
	public static final String USER_SESSION = "USER_SESSION";
	//请求所属导航标识参数，作为请求的特别参数
	public static final String AUTH_NAV_ID = "spec_navid";
	public static final String SYSPARAMS_KEY = "sysParams";
	public static final String INITIALIZEPASSWORD = "123456";
	//
	public static final int PlatFormInitId=0;
	//语言
	public static final String ZH_SIMP = "ZH_SIMP";//中文简体
	public static final String ZH_UEY = "ZH_UEY";//维吾尔语

	/**
	 * 文件上传
	 */
	public static String FILEUPLOAD_APP_MAINCLIENT = "test/phjf/app/mainclient";
	public static String FILEUPLOAD_APP_MERCHCLIENT = "test/phjf/app/merchclient";
	public static String FILEUPLOAD_IMG = "test/phjf/img";

	public static String FILEUPLOAD_IMGTYPE_APK = "apk";
	public static String FILEUPLOAD_IMGTYPE_IMG = "jpeg";

	/*字典类型*/
	public static String Datalibrary_SYS_isBanner = "sys_isBanner";//
	public static String Datalibrary_SYS_isRecom = "sys_isRecom";//
	public static String Datalibrary_SYS_yesNo = "sys_yesNo";//
	public static String Datalibrary_SYS_isShow = "sys_isShow";//
	public static String Datalibrary_SYS_articleStatus = "sys_articleStatus";//
	public static String Datalibrary_SYS_bank_business_1 = "bank_institution";//

	public static String Sms_Ali_Product = PropKit.use("config.properties").get("sms.ali.product");
	public static String Sms_Ali_Domain = PropKit.use("config.properties").get("sms.ali.domain");
	public static String Sms_Ali_AccessKeyId = PropKit.use("config.properties").get("sms.ali.accessKeyId");
	public static String Sms_Ali_AccessKeySecret = PropKit.use("config.properties").get("sms.ali.accessKeySecret");
	public static String Sms_Ali_SignName = PropKit.use("config.properties").get("sms.ali.signName");
	public static String Sms_Ali_TCode_SMS_1143380179 = "SMS_114380179";//短信验证码
	public static String Sms_Ali_TCode_SMS_113450626 = "SMS_113450626";//获取完整卡号验证码
	public static com.bankwel.framework.third.alisms.AliSmsClient AliSmsClient = new AliSmsClient(Sms_Ali_Product, Sms_Ali_Domain, Sms_Ali_AccessKeyId, Sms_Ali_AccessKeySecret, Sms_Ali_SignName);

	public static String Sms_Hl95_Url = PropKit.use("config.properties").get("sms.hl95.url");
	public static String Sms_Hl95_Username = PropKit.use("config.properties").get("sms.hl95.username");
	public static String Sms_Hl95_Password = PhjfAdminAesKit.aes.decode(PropKit.use("config.properties").get("sms.hl95.password"));
	//    public static String Sms_Hl95_Password = "";
	public static String Sms_Hl95_Epid = PropKit.use("config.properties").get("sms.hl95.epid");
	public static com.bankwel.framework.third.hl95.Hl95SmsClient Hl95SmsClient = new Hl95SmsClient(Sms_Hl95_Url, Sms_Hl95_Username, Sms_Hl95_Password, Sms_Hl95_Epid, "sms");

	public static String Sms_Type_IPLOGINCHECK = "ipLoginCheck"; //该ip第一次登录验证


	/**
	 *@Description:上传文件大小限制及说明
	 *@Author:liqing
	 *@Since:2014年10月8日
	 */
	public enum UploadFile_MaxSize {
		SCROLLPIC(5242880L, "上传轮播图最大5M"),
		TITLEIMG(5242880L,"5M"),
		GoodsDoc(5242880L,"5M"),
		INVESTORIMG(3145728L,"3M");
		public long value;
		public String desc;

		private UploadFile_MaxSize(long value, String desc) {
			this.value = value;
			this.desc = desc;
		}
	}

	/**
	 * 系统参数key liqing 2014年4月4日
	 */
	public enum SysParamKeys {
		UPLOADCONTEXT("SYS_UPLOADCONTEXT","访问上传文件上下文key"),
		UPLOADROOT("SYS_UPLOADROOT","文件上传根目录key"),
		UPLOADDOMAIN("SYS_UPLOAD_DOMAIN","上传服务器域名"),
		SMS_CODE_LEN("SYS_SMS_CODE_LEN","短信验证码位数"),
		SMS_CODE_DICT("SYS_SMS_CODE_DICT","短信验证码字典"),
		SMS_TERM("SYS_SMS_TERM","短信有效期");

		public String value;
		public String desc;

		private SysParamKeys(String value, String desc) {
			this.value = value;
			this.desc = desc;
		}
	}

	
	/**
	 *@Description:后台管理通用返回状态码
	 *@Author:Administrator
	 *@Since:2014年10月8日
	 */
	public enum AdminResultCode {
		SUCCESS(0, "成功"), SERVER_ERR(-1, "服务器错误"), DISABLE_OPERATE(-101, "您没有进行此操作的权限，请联系管理员！");
		public Integer value;
		public String desc;

		private AdminResultCode(int value, String desc) {
			this.value = value;
			this.desc = desc;
		}
	}





	/**
	 *@Description:“是否”相关属性枚举
	 *@Author:xugebing
	 *@Since:2015年10月21日
	 */
	public enum AdminWhetherState{
		NO(0,"否"),YES(1,"是");
		
		public int value;
		public String desc;
		
		private AdminWhetherState(int value, String desc) {
			this.value = value;
			this.desc = desc;
		}
	}

	public enum UploadFolderName{
		Default("filePath","默认地址"),Article("article","用户上传的文档地址");
		public String desc;
		public String name;
		UploadFolderName(String name,String desc){
			this.desc = desc;
			this.name = name;
		}

	}



	public enum  LanguageType{
		ZH_SIMP("简体中文","ZH_SIMP"),ZH_UEY("维吾尔语","ZH_UEY");

		public String desc;
		public String value;

		LanguageType(String desc,String value){
			this.value = value;
			this.desc = desc;
		}

		public  static  String getDescByValue(String language) {
			String descStr ="";
			if (language.equals("ZH_SIMP")){
				descStr = ZH_SIMP.desc;
			}else if(language.equals("ZH_UEY")){
				descStr = ZH_UEY.desc;
			}
			return descStr;
		}
	}

	public enum  DictItemType {
		NEWS_LABEL("新闻标签分类", "NEWS_LABEL"), LOAN_LABEL("贷款产品分类", "LOAN_LABEL");

		public String desc;
		public String value;

		DictItemType(String desc, String value) {
			this.value = value;
			this.desc = desc;
		}

	}

	public enum  ProductStatus {
		INVALID("无效", 0), PREDEPLOY("待发布", 1),DEPLOYED("已发布",2),UNDER_SHEFL("已下架",3);

		public String desc;
		public Integer value;

		ProductStatus(String desc, Integer value) {
			this.value = value;
			this.desc = desc;
		}

	}
	public static String Datalibrary_Type_SysSmsCodeType = "sys_smsCodeType";//短信发送类型
	public static String Sms_Type_Reg = "reg"; //用户注册
	public static String Sms_Type_FP  = "fg";  //忘记密码
	public static String Sms_Type_AP  = "ap";  //修改密码
	public static String Sms_Type_MM  = "mm";  //商户修改手机号
	public static String Sms_Type_FL  = "fl";  //快速登录
	public static String Sms_Type_BA  = "ba";  //预约开户
	public static String Sms_Type_AL  = "al";  //申请贷款
	public static String Sms_Type_GFB = "gfb"; //获取完整卡号
	public static String Sms_Type_ACI = "aci"; //添加一类卡验证
	public static String Sms_Type_IDA = "ida"; //添加一类卡验证
	public static String Sms_Type_UM = "um"; //用户修改手机号
	public static String Sms_Type_SendCNum = "SendCNum"; //发送完整卡号至手机
	public static String Sms_Type_UINVI = "uinvi"; //用户发邀请信息
	public static String Sms_Type_ipLoginCheck="ipLoginCheck";//用户在第一次ip登录记录的地方需要手机验证码

	public static final Map<String,String> Sms_Type_INAuth = new HashMap<String,String>();
	static{
		Sms_Type_INAuth.put(Sms_Type_AP, Sms_Type_AP);
		Sms_Type_INAuth.put(Sms_Type_MM, Sms_Type_MM);
		Sms_Type_INAuth.put(Sms_Type_BA, Sms_Type_BA);
		Sms_Type_INAuth.put(Sms_Type_AL, Sms_Type_AL);
		Sms_Type_INAuth.put(Sms_Type_GFB, Sms_Type_GFB);
		Sms_Type_INAuth.put(Sms_Type_ACI, Sms_Type_ACI);
		Sms_Type_INAuth.put(Sms_Type_IDA, Sms_Type_IDA);
		Sms_Type_INAuth.put(Sms_Type_UM, Sms_Type_UM);
		Sms_Type_INAuth.put(Sms_Type_ipLoginCheck, Sms_Type_ipLoginCheck);
	}
}
