package com.example.cityguild;

import com.example.cityguild.entity.UserEntity;

public class Config {

	//public static String PATH = "http://192.168.0.250:7070/shanghaiunion"; 
	
	//public static String PATH = "http://192.168.0.206:8080/shanghaiunion"; 
	public static String PATH = "http://180.166.148.170:8080"; 
	public static String FINDSHOUYE=PATH+"/api/activity/findAll";
	
	public static String NEWEST=PATH+"/api/news/findTopNews";
	
	public static String NEWESTTEXT=PATH+"/api/news/findNews";
	
	public static String NEWESTDETAIL=PATH+"/api/news/findById";
	
	public static String JIGOUCHAXUN=PATH+"/api/service/center/worker/page";
	
	public static String JIGOUCHAXUN1=PATH+"/api/service/center/law/page";
	
	public static String JIGOUDETAIL=PATH+"/api/service/center/info";
	
	public static String POSTTYPE=PATH+"/api/job/findAllRI";
	
	public static String POSTAREA=PATH+"/api/job/findAllAddr";
	
	public static String POSTNEST=PATH+"/api/job/findNewJob";
	
	public static String POSTDETAIL=PATH+"/api/job/findById";
	
	public static String POSTOTHER=PATH+"/api/job/findOtherJobById";
	
	public static String POSTSEARCH=PATH+"/api/job/search";
	
	public static String MERCHANTS=PATH+"/api/merchant/findAll";
	
	public static String MERCHANTSDATIL=PATH+"/api/merchant/findById";
	
	public static String MERFINDOTHER=PATH+"/api/merchant/findOtherMerchants";
	
	public static String ABOUTFG=PATH+"/api/news/findLaws";
	
	public static String ABOUTFGDT=PATH+"/api/news/findLawsById";
	
	public static String ABOUTHZ=PATH+"/api/mutualAid/findAll";
	public static String ABOUTHZ1=PATH+"/api/mutualAid/search";
	
	public static String ABOUTHZDT=PATH+"/api/mutualAid/findById";
	
	public static String ABOUTHZSEAR=PATH+"/api/mutualAid/search";
	
	public static String DOWN=PATH+"/api/download/list";
	
	public static String YZM=PATH+"/api/user/registfcode";
	
	public static String REG=PATH+"/api/user/regist";
	
	public static String WANSHAN=PATH+"/api/user/update";
	
	public static String LAND=PATH+"/api/user/login";
	
	public static String XGPASS=PATH+"/api/user/changePassword";
	
	public static String XGTELCODE=PATH+"/api/user/getPhoneCode";
	
	public static String XGTEL=PATH+"/api/user/changePhone";
	
	public static String FINDPASS=PATH+"/api/user/findPwd";
	
	public static String WEIQUAN=PATH+"/api/protect/regist";
	
	public static String LOGINOUT=PATH+"/api/user/loginOut";
	
	public static String FINDCITY=PATH+"/api/health/findAllCity";
	
	public static String FINDYIYUAN=PATH+"/api/health/findHospital";
	
	public static String FINDKESHI=PATH+"/api/health/findSection";
	
	public static String FINDDOCTOR=PATH+"/api/health/findDoctorByDeptId";
	
	public static String SEARCHDOCTOR=PATH+"/api/health/findDoctor";
	public static String checkVersion=PATH+"/api/download/getVersion";
	public static String QUESTTYPE="http://192.168.0.250:7070/shanghaiunion/api/protect/getType";
	public static UserEntity ue=null;
	public static String token=null;
	public static String password=null;
	public static String city=null;
	public static String sheng=null;
	public static String area_id="";
	public static Boolean isFrist=true;
	public static String cpid="";
	public static String hospitalid="";
	public static String yiyuanv="";
	public static String keshiva="";
}
