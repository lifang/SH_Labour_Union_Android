package com.example.cityguild;

 

import java.util.LinkedList;
import java.util.List;


import com.baidu.frontia.FrontiaApplication;
import com.baidu.mapapi.SDKInitializer;

import com.loopj.android.http.AsyncHttpClient;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.app.Activity;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
 

public class MyApplication extends FrontiaApplication{
	

	private static MyApplication  mInstance=null;
	//private ArrayList<Order> orderList = new ArrayList<Order>();
	/**
	 * ��֤��Ϣtoken
	 */
	private static  String versionCode="1.0";
	private static int notifyId=0;
	
	public static int getNotifyId() {
		return notifyId;
	}
	public static void setNotifyId(int notifyId) {
		MyApplication.notifyId = notifyId;
	}
	public static String getVersionCode() {
		return versionCode;
	}
	public static void setVersionCode(String versionCode) {
		MyApplication.versionCode = versionCode;
	}
	/*public static User getCurrentUser() {
		return currentUser;
	}
	public static void setCurrentUser(User currentUser) {
		MyApplication.currentUser = currentUser;
	}
*/

	private static String token="";
	AsyncHttpClient client = new AsyncHttpClient(); //  
	
	public AsyncHttpClient getClient() {
		//client.setTimeout(6000);
		return client;
	}
	public void setClient(AsyncHttpClient client) {
		this.client = client;
	}
	public static String getToken() {
		return token;
	}
	public static void setToken(String token) {
		MyApplication.token = token;
	}


	/**
	 * �洢��ǰ�û�������Ϣ,����welcome��ʼ���û���Ϣ
	 */
	//public static User currentUser = new User();
	//����list��������ÿһ��activity�ǹؼ�   
    private List<Activity> mList = new LinkedList<Activity>();   
 // add Activity     
    public void addActivity(Activity activity) {    
        mList.add(activity);    
    }    
    //�ر�ÿһ��list�ڵ�activity   
    public void exit() {    
        try {    
            for (Activity activity:mList) {    
                if (activity != null)    
                    activity.finish();    
            }    
        } catch (Exception e) {    
            e.printStackTrace();    
        } 
        
    }  
 

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;		 
		initImageLoader(getApplicationContext());
		SDKInitializer.initialize(this);
		  PackageManager packageManager = getPackageManager();
          // getPackageName()���㵱ǰ��İ�����0�����ǻ�ȡ�汾��Ϣ
          PackageInfo packInfo;
		try {
			packInfo = packageManager.getPackageInfo(getPackageName(),0);
			  int version = packInfo.versionCode;
			 setVersionCode(version+"");
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void initImageLoader(Context context) {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).memoryCache(new LruMemoryCache(2 * 1024 * 1024))
				.memoryCacheSize(2 * 1024 * 1024)
				.discCacheSize(50 * 1024 * 1024).discCacheFileCount(100)
				.build();
		ImageLoader.getInstance().init(config);
	}
	public static MyApplication getInstance() {
		return mInstance;
	}
	
 
}
