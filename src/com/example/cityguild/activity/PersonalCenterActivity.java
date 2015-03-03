package com.example.cityguild.activity;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.examlpe.ices.util.CancleUpdate;
import com.examlpe.ices.util.ClientUpdate;
import com.examlpe.ices.util.TitleMenuUtil;
import com.examlpe.ices.util.UpdateDialog;
import com.example.cityguild.BaseActivity;
import com.example.cityguild.Config;
import com.example.cityguild.MyApplication;
import com.example.cityguild.R;
import com.example.cityguild.entity.VersionEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class PersonalCenterActivity extends BaseActivity {
	private LinearLayout ll_land;
	private LinearLayout ll_zhuce;
	private LinearLayout ll_update;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personalcenter);
		new TitleMenuUtil(this, "个人中心").init();
		
		initView();
	}

	private void initView() {
		ll_land = (LinearLayout) findViewById(R.id.ll_denglu);
		ll_zhuce = (LinearLayout) findViewById(R.id.ll_zhuce);
		ll_update = (LinearLayout)findViewById(R.id.ll_update);
		ll_update.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				ClientUpdate update=new ClientUpdate(PersonalCenterActivity.this);
				update.check();
				
			}
		});
		ll_land.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if(Config.ue==null){
					PersonalCenterActivity.this.finish();
					startActivity(new Intent(PersonalCenterActivity.this,LandActivity.class));
				}else{
					Toast.makeText(PersonalCenterActivity.this, "您的账号已经登录!", 1000).show();
				}
				

			}
		});
		ll_zhuce.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(PersonalCenterActivity.this,RegisterActivity.class));


			}
		});

	}
	// 发送下载消息
	private void sendNotification() {
		NotificationManager mNotificationManager = (NotificationManager) PersonalCenterActivity.this.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = new Notification(R.drawable.aboutus, "CITYGUILD", System.currentTimeMillis());
		notification.flags = Notification.FLAG_NO_CLEAR;
		Intent i = new Intent(PersonalCenterActivity.this, CancleUpdate.class);
 		i.putExtra("quxiao", "Cancle");
			PendingIntent pendingintent = PendingIntent.getActivity( PersonalCenterActivity.this, 0, i, 0);
			RemoteViews remoteviews = new RemoteViews( PersonalCenterActivity.this.getPackageName(), R.layout.my_notification);
		remoteviews.setOnClickPendingIntent(R.id.notification_cancle, pendingintent);
		remoteviews.setImageViewResource(R.id.logo_image, R.drawable.aboutus);
		notification.contentView = remoteviews;
		mNotificationManager.notify(88888, notification);
		/*new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					File file = new File(getFilePath());
					mUpdate = mUpdate_AsyncTask;
					mUpdate_AsyncTask.execute(new URL(DOWN_PATH));
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}
		}).start();*/
	}
	// 获得应用下载路径
	public String getFilePath() {
		boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist) // 如果SD卡存在，则获取跟目录
		{
			return Environment.getExternalStorageDirectory().toString() + File.separator + "CITYGUILD.apk";// 获取跟目录
		} else {
			return PersonalCenterActivity.this.getCacheDir().getAbsolutePath() + File.separator + "CITYGUILD.apk";// 获取跟目录
		}
	}
	
	
}
