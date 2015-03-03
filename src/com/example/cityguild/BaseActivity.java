package com.example.cityguild;

 
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;


public class BaseActivity extends Activity implements OnClickListener{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}
	@Override
	protected void onDestroy() {
	
		//getRequests().cancelAll(this);
		super.onDestroy();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	//	StatService.onPause(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
	//	StatService.onResume(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}
