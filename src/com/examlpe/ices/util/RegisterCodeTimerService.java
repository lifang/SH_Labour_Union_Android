package com.examlpe.ices.util;



import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

/**
 * 注册验证码计时服务
 * 
 * @author zihao
 * 
 */
public class RegisterCodeTimerService extends Service {

	private static Handler mHandler;
	private static Handler mHandler1;
	private static RegisterCodeTimer mCodeTimer;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		String fpass=intent.getStringExtra("fpass");
		if(fpass!=null){
			mCodeTimer = new RegisterCodeTimer(60000, 1000, mHandler1,1);
			mCodeTimer.start();	
			Log.i("fpass","start");
		}else{
			mCodeTimer = new RegisterCodeTimer(60000, 1000, mHandler,0);
			mCodeTimer.start();
			Log.i("reg","start");
		}
		
		
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	/**
	 * 设置Handler
	 */
	public static void setHandler(Handler handler) {
		mHandler = handler;
	}
	public static void setHandler1(Handler handler) {
		mHandler1 = handler;
	}

}
