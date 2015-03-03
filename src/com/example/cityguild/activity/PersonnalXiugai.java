package com.example.cityguild.activity;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cityguild.BaseActivity;
import com.example.cityguild.Config;
import com.example.cityguild.MyApplication;
import com.example.cityguild.R;
import com.example.cityguild.R.string;
import com.example.cityguild.entity.UserEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class PersonnalXiugai extends BaseActivity {
	private TextView title;
	private TextView tv_right;
	private LinearLayout ll_left;
	private LinearLayout ll_right;
	private EditText name;
	private EditText email;
	private EditText hygh;
	private String namev;
	private String emailv;
	private String hyghv;
	private TextView tv_name;
	private TextView tv_email;
	private TextView tv_ghh;
	private String type;
	private Button bt_yzm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personalxg);
		type = getIntent().getStringExtra("type");
		initView();
	}

	private void initView() {
		bt_yzm = (Button) findViewById(R.id.bt_yzm);
		bt_yzm.setOnClickListener(this);
		title = (TextView) findViewById(R.id.titleStr);
		tv_right = (TextView) findViewById(R.id.tv_right);
		tv_right.setText("保存");
		ll_left = (LinearLayout) findViewById(R.id.ll_left);
		ll_left.setOnClickListener(this);
		ll_right = (LinearLayout) findViewById(R.id.ll_right);
		ll_right.setOnClickListener(this);
		title.setText("个人信息管理");

		name = (EditText) findViewById(R.id.name);
		email = (EditText) findViewById(R.id.email);
		hygh = (EditText) findViewById(R.id.hygh);
		
		
		hygh.setInputType( InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD);
		if(type.equals("1")){
			name.setText(Config.ue.getNickName());
			email.setText(Config.ue.getEmail());
			hygh.setText(Config.ue.getLabourUnionCode());
		}

		tv_name = (TextView) findViewById(R.id.tv_name);
		tv_email = (TextView) findViewById(R.id.tv_email);
		tv_ghh = (TextView) findViewById(R.id.tv_ghh);

		if (type.equals("3")) {
			bt_yzm.setVisibility(View.VISIBLE);
			title.setText("更换手机号码");
			tv_name.setText("原手机号码");
			tv_email.setText("验证码");
			tv_ghh.setText("新手机号码");
		 	
			
		}
		if (type.equals("2")) {
			title.setText("修改密码");
			tv_name.setText("原始密码");
			tv_email.setText("新密码");
			tv_ghh.setText("确认新密码");
			name.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
			email.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
			hygh.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
		}

	}

	private void getData() {
		String url = "";
		RequestParams params = new RequestParams();
		if (type.equals("1")) {
			url = Config.WANSHAN;
			params.put("id", Config.ue.getId());
			params.put("nickName", namev);
			params.put("email", emailv);
			params.put("labourUnionCode", hyghv);
			params.put("token", Config.token);
		}
		if (type.equals("2")) {
			url = Config.XGPASS;
			params.put("id", Config.ue.getId());
			params.put("password", name.getText().toString());
			params.put("newpwd", email.getText().toString());
			params.put("token", Config.token);
			
		}
		if (type.equals("3")) {
			url = Config.XGTEL;
			
			params.put("id", Config.ue.getId());
			params.put("phone", hygh.getText().toString());
			params.put("verify_code", email.getText().toString());
			params.put("token", Config.token);
			
		}
		//AsyncHttpClient c = new AsyncHttpClient();

		MyApplication.getInstance().getClient().post(url, params, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] responseBody) {
				// TODO Auto-generated method stub
				String userMsg = new String(responseBody).toString();
				Gson gson = new Gson();
				// EventEntity

				Log.i("id",Config.ue.getId());
				Log.i("ljp", userMsg);
				JSONObject jsonobject = null;
				int code = 0;
				try {
					jsonobject = new JSONObject(userMsg);

					code = jsonobject.getInt("code");

					if (code == -2) {

					} else if (code ==1) {

						Config.ue= gson.fromJson(
								jsonobject.getString("result"),
								new TypeToken<UserEntity>() {
								}.getType());
						Toast.makeText(getApplicationContext(), "修改信息成功!", 1000)
								.show();
						PersonnalXiugai.this.finish();

					} else {
						Toast.makeText(PersonnalXiugai.this,
								jsonobject.getString("message"),
								Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] responseBody, Throwable error) {
				Toast.makeText(PersonnalXiugai.this, "更新信息失败!", 1000).show();

			}
		});

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		if (v.getId() == R.id.ll_left) {
			this.finish();
		}
		if (v.getId() == R.id.ll_right) {
			namev = name.getText().toString();
			emailv = email.getText().toString();
			hyghv = hygh.getText().toString();
			if (type.equals("1")) {
				if(namev.equals("")){
					Toast.makeText(getApplicationContext(), "会员名不能为空!", 1000).show();
					return;
				}
				if(emailv.equals("")){
					Toast.makeText(getApplicationContext(), "邮箱不能为空!", 1000).show();
					return;
				}
				if(hyghv.equals("")){
					Toast.makeText(getApplicationContext(), "工会会员号不能为空!", 1000).show();
					return;
				}
				
			
				getData();
			}
			if (type.equals("2")) {
			
				if(namev.equals("")){
					Toast.makeText(getApplicationContext(), "原始密码不能为空!", 1000).show();
					return;
				}
				if(emailv.equals("")){
					Toast.makeText(getApplicationContext(), "新密码不能为空!", 1000).show();
					return;
				}
				if(hyghv.equals("")){
					Toast.makeText(getApplicationContext(), "确认新密码不能为空!", 1000).show();
					return;
				}
				if(!emailv.equals(hyghv)){
					Toast.makeText(getApplicationContext(), "两次密码不一致!", 1000).show();
					return;
				}
				if(!namev.equals(Config.password)){
					Toast.makeText(getApplicationContext(), "原始密码不正确!", 1000).show();
					return;
				}
				getData();
			}
			if (type.equals("3")) {
				if(namev.equals("")){
					Toast.makeText(getApplicationContext(), "原手机号码不能为空!", 1000).show();
					return;
				}
				if(emailv.equals("")){
					Toast.makeText(getApplicationContext(), "验证码不能为空!", 1000).show();
					return;
				}
				if(hyghv.equals("")){
					Toast.makeText(getApplicationContext(), "新手机号不能为空!", 1000).show();
					return;
				}
				
			
				getData();
			}
		}
		if (v.getId() == R.id.bt_yzm) {
			
			getCode();
		}
	}

	private void getCode() {
		String url = Config.XGTELCODE;
		RequestParams params = new RequestParams();
		params.put("phone", name.getText().toString());
		Log.i("phone",name.getText().toString());
		MyApplication.getInstance().getClient().post(url, params, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] responseBody) {
				// TODO Auto-generated method stub
				String userMsg = new String(responseBody).toString();
				Gson gson = new Gson();
				// EventEntity
				JSONObject jsonobject = null;
				int code = 0;
				try {
					jsonobject = new JSONObject(userMsg);

					code = jsonobject.getInt("code");

					if (code == -2) {

					} else if (code == 1) {

						String codestr= gson.fromJson(
								jsonobject.getString("result"),
								new TypeToken<String>() {
								}.getType());
						email.setText(codestr);
						

					} else {
						Toast.makeText(PersonnalXiugai.this,
								jsonobject.getString("message"),
								Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] responseBody, Throwable error) {
				Toast.makeText(PersonnalXiugai.this, "更新信息失败!", 1000).show();

			}
		});

	}
	
}
