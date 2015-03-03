package com.examlpe.ices.util;

import com.example.cityguild.R;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TitleMenuUtil {
	private Activity activity;
	private String titleStr;
	private LinearLayout btn_back;
	private TextView title;
	private LinearLayout ll_right;
	private TextView tv_right;
	public TitleMenuUtil(Activity activity,String titleStr) {
		this.activity=activity;
		this.titleStr=titleStr;
	}
	
	public void init(){
		title = (TextView)activity.findViewById(R.id.titleStr);
		btn_back = (LinearLayout)activity.findViewById(R.id.ll_left);
		title.setText(titleStr);
		btn_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				activity.finish();				
			}
		});
	}


}
