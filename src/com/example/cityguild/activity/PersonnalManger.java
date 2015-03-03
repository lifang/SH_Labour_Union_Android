package com.example.cityguild.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cityguild.BaseActivity;
import com.example.cityguild.Config;
import com.example.cityguild.R;
import com.example.cityguild.entity.UserEntity;

public class PersonnalManger extends BaseActivity {
	private TextView title;
	private LinearLayout ll_left;
	private LinearLayout ll_right;
	private TextView tv_right;
	private TextView name;
	private TextView email;
	private TextView ghh;
	private TextView xgpassword;
	private TextView tel;
	private LinearLayout ll_xgpassword;
	private LinearLayout ll_xgtel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personalmanger);
		initView();
	}

	private void initView() {
		title = (TextView)findViewById(R.id.titleStr);
		tv_right = (TextView)findViewById(R.id.tv_right);
		tv_right.setText("编辑");
		ll_left = (LinearLayout)findViewById(R.id.ll_left);
		ll_left.setOnClickListener(this);
		ll_right = (LinearLayout)findViewById(R.id.ll_right);
		ll_right.setOnClickListener(this);
		title.setText("个人信息管理");
		
		name = (TextView)findViewById(R.id.name);
		email = (TextView)findViewById(R.id.email);
		ghh = (TextView)findViewById(R.id.ghh);
		xgpassword = (TextView)findViewById(R.id.xgpassword);
		tel = (TextView)findViewById(R.id.tel);
		UserEntity ue=Config.ue;
		if(ue.getNickName()!=null)
		name.setText(ue.getNickName());
		if(ue.getEmail()!=null)
			email.setText(ue.getEmail());
		if(ue.getLabourUnionCode()!=null)
			ghh.setText(ue.getLabourUnionCode());
		xgpassword.setText("******");
		if(ue.getPhone()!=null){
			/*if(ue.getPhone().length()==11){
				String st=ue.getPhone().substring(0, 3);
				String end=ue.getPhone().substring(7, 11);
				tel.setText(st+"****"+end);
			}else{*/
				tel.setText(ue.getPhone());
			//}
			
		}
			
		
		ll_xgpassword = (LinearLayout)findViewById(R.id.ll_xgpassword);
		ll_xgtel = (LinearLayout)findViewById(R.id.ll_xgtel);
		
		ll_xgpassword.setOnClickListener(this);
		ll_xgtel.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		if(v.getId()==R.id.ll_left){
			this.finish();
		}
		if(v.getId()==R.id.ll_right){
			Intent i=new Intent(this,PersonnalXiugai.class);
			i.putExtra("type", "1");
			startActivity(i);
		}
		if(v.getId()==R.id.ll_xgpassword){
			Intent i=new Intent(this,PersonnalXiugai.class);
			i.putExtra("type", "2");
			startActivity(i);
		}
		if(v.getId()==R.id.ll_xgtel){
			Intent i=new Intent(this,PersonnalXiugai.class);
			i.putExtra("type", "3");
			startActivity(i);
		}
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		UserEntity ue=Config.ue;
		if(ue.getNickName()!=null)
		name.setText(ue.getNickName());
		if(ue.getEmail()!=null)
			email.setText(ue.getEmail());
		if(ue.getLabourUnionCode()!=null)
			ghh.setText(ue.getLabourUnionCode());
		xgpassword.setText("******");
		if(ue.getPhone()!=null)
			tel.setText(ue.getPhone());
	}
}
