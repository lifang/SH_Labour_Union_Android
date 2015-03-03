package com.example.cityguild.activity;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cityguild.BaseActivity;
import com.example.cityguild.Config;
import com.example.cityguild.MyApplication;
import com.example.cityguild.R;
import com.example.cityguild.entity.PostEntity;
import com.example.cityguild.entity.UserEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class OtherInfoActivity extends BaseActivity {
	private TextView title;
	private TextView tv_right;
	private TextView tv_findpass;
	private String id;
	private LinearLayout ll_left;
	private LinearLayout ll_right;
	private LinearLayout ll_land;
	private EditText zgid;
	private EditText email;
	private UserEntity ue;
	
	private String emailv;
	private String zgidv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ohterinfo);
		id = getIntent().getStringExtra("id");
		initView();
	}

	private void initView() {
		title = (TextView) findViewById(R.id.titleStr);
		tv_right = (TextView) findViewById(R.id.tv_right);

		title.setText("其他信息填写");
		tv_right.setText("跳过");
		ll_left = (LinearLayout) findViewById(R.id.ll_left);
		ll_left.setOnClickListener(this);
		ll_right = (LinearLayout) findViewById(R.id.ll_right);
		ll_right.setOnClickListener(this);

		ll_land = (LinearLayout) findViewById(R.id.ll_land);
		ll_land.setOnClickListener(this);

		zgid = (EditText) findViewById(R.id.zgid);
		email = (EditText) findViewById(R.id.email);

	}

	private void getData() {

		RequestParams params = new RequestParams();
		params.put("id", id);
		if(!emailv.equals(""))
		params.put("email", emailv);
		if(!zgidv.equals(""))
		params.put("labourUnionCode", zgidv);
		params.put("token", Config.token);
		//AsyncHttpClient c = new AsyncHttpClient();

		MyApplication.getInstance().getClient().post(Config.WANSHAN, params, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] responseBody) {
				// TODO Auto-generated method stub
				String userMsg = new String(responseBody).toString();

				Log.i("ljp", userMsg);
				Log.i("id", id);
				Log.i("id", Config.token);
				Gson gson = new Gson();
				// EventEntity
				JSONObject jsonobject = null;
				int code = 0;
				try {
					jsonobject = new JSONObject(userMsg);

					code = jsonobject.getInt("code");

					if (code == -2) {

					} else if (code == 1) {

						ue = gson.fromJson(jsonobject.getString("result"),
								new TypeToken<UserEntity>() {
								}.getType());
						Intent i = new Intent(OtherInfoActivity.this, LandActivity.class);
						startActivity(i);
						OtherInfoActivity.this.finish();

					} else {
						Toast.makeText(OtherInfoActivity.this,
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
				Toast.makeText(OtherInfoActivity.this, "更新信息失败!", 1000).show();

			}
		});

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		if (v.getId() == R.id.ll_left) {
			Intent i = new Intent(OtherInfoActivity.this, LandActivity.class);
			startActivity(i);
			this.finish();
		}
		if (v.getId() == R.id.ll_right) {
			Intent i = new Intent(OtherInfoActivity.this, LandActivity.class);
			startActivity(i);
			this.finish();
		}
		if (v.getId() == R.id.ll_land) {
			zgidv = zgid.getText().toString().trim();
			emailv = email.getText().toString().trim();
			
			if(emailv.equals("")&&zgidv.equals("")){
				startActivity(new Intent(this,LandActivity.class));
				return;
			}
			getData();
		
		}
	}
}
