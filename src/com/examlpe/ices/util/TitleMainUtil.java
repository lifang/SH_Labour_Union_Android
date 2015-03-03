package com.examlpe.ices.util;

import com.example.cityguild.Config;
import com.example.cityguild.R;
import com.example.cityguild.activity.LandPersonalCenterActivity;
import com.example.cityguild.activity.MainActivity;
import com.example.cityguild.activity.PersonalCenterActivity;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class TitleMainUtil {
	private Activity activity;
	private String titleStr;
	private LinearLayout btn_sil;
	private TextView title;
	private LinearLayout btn_personal;
	private View view;
	public TitleMainUtil(Activity activity,String titleStr,View view) {
		this.activity=activity;
		this.titleStr=titleStr;
		this.view=view;
	}
	
	public void init(){
		title = (TextView)view.findViewById(R.id.titletext);
		btn_sil = (LinearLayout)view.findViewById(R.id.ll_left);
		btn_personal = (LinearLayout)view.findViewById(R.id.ll_right);
		title.setText(titleStr);
		
		btn_sil.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				MainActivity.mSlidingMenu.toggle();				
			}
		});

		btn_personal.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(Config.ue==null){
					activity.startActivity(new Intent(activity,PersonalCenterActivity.class));
				}else{
					activity.startActivity(new Intent(activity,LandPersonalCenterActivity.class));
				}
				
				
			}
		});
	}

}
