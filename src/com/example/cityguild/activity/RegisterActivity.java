package com.example.cityguild.activity;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.examlpe.ices.util.RegisterCodeTimer;
import com.examlpe.ices.util.RegisterCodeTimerService;
import com.example.cityguild.BaseActivity;
import com.example.cityguild.Config;
import com.example.cityguild.MyApplication;
import com.example.cityguild.R;
import com.example.cityguild.entity.NewestDetailEntity;
import com.example.cityguild.entity.UserEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class RegisterActivity extends BaseActivity {
	private TextView title;
	private TextView tv_right;
	private TextView tv_findpass;
	private LinearLayout ll_newxt;
	private LinearLayout ll_land;
	private EditText username;
	private EditText password;
	private EditText qrpassword;
	private EditText tel;
	private EditText yzm;
	private static Button bt_yzm;
	private String telv;
	private String fhyzm="  ";
	private boolean yzmflag = false;
	private boolean flag = false;
	private String usernamev;
	private String passwwordv;
	private String qrpasswordv;
	private String telv2;
	private String yzmv;
	private String id;
	private LinearLayout ll_left;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);

		initView();
	}

	private void initView() {
		ll_left = (LinearLayout)findViewById(R.id.ll_left);
		ll_left.setOnClickListener(this);
		ll_land = (LinearLayout) findViewById(R.id.ll_right);
		ll_land.setOnClickListener(this);
		title = (TextView) findViewById(R.id.titleStr);
		tv_right = (TextView) findViewById(R.id.tv_right);
		title.setText("注册");
		tv_right.setText("登录");
		ll_newxt = (LinearLayout) findViewById(R.id.ll_next);
		ll_newxt.setOnClickListener(this);

		username = (EditText) findViewById(R.id.username);
		password = (EditText) findViewById(R.id.password);
		qrpassword = (EditText) findViewById(R.id.qrpassword);
		tel = (EditText) findViewById(R.id.tel);
		yzm = (EditText) findViewById(R.id.yzm);

		bt_yzm = (Button) findViewById(R.id.bt_yzm);
		bt_yzm.setText("获取验证码");
		bt_yzm.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		if (v.getId() == R.id.ll_next) {

			usernamev = username.getText().toString().trim();
			passwwordv = password.getText().toString();
			qrpasswordv = qrpassword.getText().toString();
			telv2 = tel.getText().toString();
			yzmv = yzm.getText().toString();

			if (usernamev.equals("")) {
				Toast.makeText(this, "用户名不能为空!", 1000).show();
				return;
			}
			if (passwwordv.equals("")) {
				Toast.makeText(this, "密码不能为空!", 1000).show();
				return;
			}
			if (passwwordv.equals("")) {
				Toast.makeText(this, "请再次输入密码!", 1000).show();
				return;
			}
			if (!passwwordv.equals(qrpasswordv)) {
				Toast.makeText(this, "两次密码不一致!", 1000).show();
				return;
			}
			if (telv2.equals("")) {
				Toast.makeText(this, "手机号码不能为空!", 1000).show();
				return;
			}
			if (yzmv.equals("")) {
				Toast.makeText(this, "验证码不能为空!", 1000).show();
				return;
			}
			if (yzmflag==false) {
				Toast.makeText(this, "未发送验证码!", 1000).show();
				return;
			}
			
			if (!yzm.getText().toString().trim().equals(fhyzm)) {
				Toast.makeText(this, "验证码错误!", 1000).show();
				return;
			}
			getData("2");
			
		
			
			/*if (yzmv.equals(fhyzm)) {
				Intent i = new Intent(RegisterActivity.this,
						OtherInfoActivity.class);

				startActivity(i);
			} else {
				Toast.makeText(this, "验证码错误!", 1000).show();
			}*/

		}
		if (v.getId() == R.id.ll_right) {
			if(Config.ue==null){
				Intent i = new Intent(RegisterActivity.this, LandActivity.class);
				startActivity(i);
			}else{
				Toast.makeText(this, "您的账号已经登录!", 1000).show();
			}
			
		}
		if (v.getId() == R.id.ll_left) {
			this.finish();
		}
		if (v.getId() == R.id.bt_yzm) {
		
			telv = tel.getText().toString();
			if (!telv.equals("")) {
				
		
				
				getData("1");
			} else {
				Toast.makeText(this, "请输入手机号!", 1000);
			}
		}
	}
	/**
	 * 倒计时Handler
	 */

	static Handler mCodeHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == RegisterCodeTimer.IN_RUNNING) {// 正在倒计时
				
				Log.i("cccc","cccc");
				bt_yzm.setText(msg.obj.toString());
				
			} else if (msg.what == RegisterCodeTimer.END_RUNNING) {// 完成倒计时
				bt_yzm.setEnabled(true);
				bt_yzm.setText(msg.obj.toString());
			}
		};
	};
	private void getData(final String type) {

		RequestParams params = new RequestParams();
		
		// params.setUseJsonStreamer(true);
		String url = "";
		if (type.equals("1")) {
			params.put("phone", telv);
			url = Config.YZM;
		} else {
			if (type.equals("2")) {
				url = Config.REG;
				params.put("username", usernamev);
				params.put("password", passwwordv);
				params.put("phone", telv);
				params.put("verify_code", yzmv);	
			}
		}
		

		MyApplication.getInstance().getClient().post(url, params, new AsyncHttpResponseHandler() {

			

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] responseBody) {
				// TODO Auto-generated method stub
				String userMsg = new String(responseBody).toString();

				Log.i("ljp", userMsg);
				Gson gson = new Gson();
				// EventEntity
				JSONObject jsonobject = null;
				int code = 0;
				try {
					jsonobject = new JSONObject(userMsg);
					code = jsonobject.getInt("code");
					if (code ==-2) {
					} else if (code == 1) {
						if(type.equals("1")){
							RegisterCodeTimerService.setHandler(mCodeHandler);
							Intent mIntent = new Intent(RegisterActivity.this, RegisterCodeTimerService.class);
							
									Log.i("zzz","zzz");
									bt_yzm.setEnabled(false);
									startService(mIntent);
						fhyzm = gson.fromJson(jsonobject.getString("result"),
								new TypeToken<String>() {
								}.getType());
						
						yzmflag = true;
						
						//yzm.setText(fhyzm);
						}else if(type.equals("2")){
							Config.token=jsonobject.getString("token");
							Log.i("wsw",Config.token);
							UserEntity ue = gson.fromJson(jsonobject.getString("result"),
									new TypeToken<UserEntity>() {
									}.getType());	

							Intent i = new Intent(RegisterActivity.this,
									OtherInfoActivity.class);
							i.putExtra("id", ue.getId());
							startActivity(i);
							RegisterActivity.this.finish();
							
						}
					} else {
						Toast.makeText(RegisterActivity.this,
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
				//Toast.makeText(RegisterActivity.this, "注册失败!", 1000).show();
			}
		});

	}
}
