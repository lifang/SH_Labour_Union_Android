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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.examlpe.ices.util.RegisterCodeTimer;
import com.examlpe.ices.util.RegisterCodeTimerService;
import com.examlpe.ices.util.TitleMenuUtil;
import com.example.cityguild.BaseActivity;
import com.example.cityguild.Config;
import com.example.cityguild.R;
import com.example.cityguild.entity.UserEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class FindPassActivity extends BaseActivity {
	private EditText name;
	private EditText password;
	private EditText qrpassword;
	private EditText phone;
	private EditText yzm;
	private static Button bt_yzm;
	private LinearLayout ll_land;
	private String namev;
	private String passwordv;
	private String qrpasswordv;
	private String phonev;
	private String yzmv="";
	private String fhyzm="   ";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.findpassword);
		new TitleMenuUtil(this, "找回密码").init();
		initView();
	}

	private void initView() {
		name = (EditText) findViewById(R.id.name);
		password = (EditText) findViewById(R.id.password);
		qrpassword = (EditText) findViewById(R.id.qrpassword);
		phone = (EditText) findViewById(R.id.phone);
		yzm = (EditText) findViewById(R.id.yzm);

		bt_yzm = (Button) findViewById(R.id.bt_yzm);
		bt_yzm.setOnClickListener(this);
		ll_land = (LinearLayout) findViewById(R.id.ll_land);
		ll_land.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {

		super.onClick(v);
		if (v.getId() == R.id.bt_yzm) {
			phonev = phone.getText().toString();

			getData("1");
			
		}
	
		if (v.getId() == R.id.ll_land) {
			namev = name.getText().toString().trim();
			passwordv = password.getText().toString().trim();
			qrpasswordv = qrpassword.getText().toString().trim();
			phonev = phone.getText().toString().trim();
			yzmv = yzm.getText().toString().trim();
			
			if(namev.equals("")){
				Toast.makeText(getApplicationContext(), "用户名不能为空!", 1000).show();
				return;
			}
			if(passwordv.equals("")){
				Toast.makeText(getApplicationContext(), "密码不能为空!", 1000).show();
				return;
			}
			if(qrpasswordv.equals("")){
				Toast.makeText(getApplicationContext(), "确认密码不能为空!", 1000).show();
				return;
			}
			if(!passwordv.equals(qrpasswordv)){
				Toast.makeText(getApplicationContext(), "两次密码不一致!", 1000).show();
			}
			if(phonev.trim().equals("")){
				Toast.makeText(getApplicationContext(), "手机号不能为空!", 1000).show();
				return;
			}
			if(yzmv.trim().equals("")){
				Toast.makeText(getApplicationContext(), "验证码不能为空!", 1000).show();
				return;
			}
			if (!yzm.getText().toString().trim().equals(fhyzm)) {
				Toast.makeText(this, "验证码错误!", 1000).show();
				return;
			}
			getData("2");
		}

	}
	static Handler mCodeHandler1 = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == RegisterCodeTimer.IN_RUNNING1) {// 正在倒计时
				bt_yzm.setText(msg.obj.toString());
				Log.i("nnnnn","nnnnnn");
			} else if (msg.what == RegisterCodeTimer.END_RUNNING1) {// 完成倒计时
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
			params.put("phone", phonev);
			url = Config.XGTELCODE;
		} else {
			if (type.equals("2")) {
				url = Config.FINDPASS;
				params.put("username", namev);
				params.put("password", passwordv);
				params.put("phone", phonev);
				params.put("inputCode", yzmv);	
			}
		}
		AsyncHttpClient c = new AsyncHttpClient();

		c.post(url, params, new AsyncHttpResponseHandler() {

			

			

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

					if (code == -2) {

					} else if (code == 1) {
						if(type.equals("1")){
							RegisterCodeTimerService.setHandler1(mCodeHandler1);
							Intent mIntent = new Intent(FindPassActivity.this, RegisterCodeTimerService.class);
							mIntent.putExtra("fpass", "fpass");
								
									bt_yzm.setEnabled(false);
									startService(mIntent);
						fhyzm = gson.fromJson(jsonobject.getString("result"),
								new TypeToken<String>() {
								}.getType());
						
						
						}else if(type.equals("2")){
						
							UserEntity ue = gson.fromJson(jsonobject.getString("result"),
									new TypeToken<UserEntity>() {
									}.getType());	

							Intent i = new Intent(FindPassActivity.this,
									LandActivity.class);
							
							startActivity(i);
							FindPassActivity.this.finish();
							
						}
					} else {
						Toast.makeText(FindPassActivity.this,
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
				//Toast.makeText(FindPassActivity.this, "找回密码失败!", 1000).show();
			}
		});

	}
}
