package com.example.cityguild.activity;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
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
import com.example.cityguild.entity.UserEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class LandActivity extends BaseActivity {
	private TextView title;
	private TextView tv_right;
	private TextView tv_findpass;
	private LinearLayout ll_right;
	private EditText username;
	private EditText password;
	private String usernamev;
	private String passwordv;
	private LinearLayout ll_land;
	private boolean flag=true;
	private LinearLayout ll_left;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.land);

		initView();
	}

	private void initView() {

		ll_land = (LinearLayout) findViewById(R.id.ll_land);
		ll_land.setOnClickListener(this);

		ll_right = (LinearLayout) findViewById(R.id.ll_right);
		ll_left = (LinearLayout) findViewById(R.id.ll_left);
		ll_left.setOnClickListener(this);
		title = (TextView) findViewById(R.id.titleStr);
		tv_right = (TextView) findViewById(R.id.tv_right);
		tv_findpass = (TextView) findViewById(R.id.tv_findPass);
		title.setText("µ«¬º");
		tv_right.setText("◊¢≤·");
		ll_right.setOnClickListener(this);
		tv_findpass.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(LandActivity.this,
						FindPassActivity.class));

			}
		});
		username = (EditText) findViewById(R.id.username);
		password = (EditText) findViewById(R.id.password);
		//username.setInputType( InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
	}

	private void getData() {

		RequestParams params = new RequestParams();
		params.put("username", usernamev);
		params.put("password", passwordv);

		//AsyncHttpClient c = new AsyncHttpClient();

		MyApplication.getInstance().getClient().post(Config.LAND, params, new AsyncHttpResponseHandler() {

			private String token;

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
					token = jsonobject.getString("token");
					if (code == -2) {

					} else if (code == 1) {
						UserEntity ue = gson.fromJson(
								jsonobject.getString("result"),
								new TypeToken<UserEntity>() {
								}.getType());
						ue.setToken(token);
						Config.token=token;
						Config.ue=ue;
						Intent i = new Intent(LandActivity.this, LandPersonalCenterActivity.class);
						i.putExtra("name",ue.getNickName());
						Log.i("tt1",Config.ue.getToken());
						i.putExtra("id", ue.getId());
						startActivity(i);
						if(ue.getId()!=null){
							Config.password=password.getText().toString();
							Log.i("cccccc",Config.password);
						}
						LandActivity.this.finish();
					} else {
						Toast.makeText(LandActivity.this,
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
				

			}
		});

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		if (v.getId() == R.id.ll_right) {
			Intent i = new Intent(this, RegisterActivity.class);
			startActivity(i);
		}
		if (v.getId() == R.id.ll_left) {
			this.finish();
		}
		if (v.getId() == R.id.ll_land) {
			usernamev = username.getText().toString().trim();
			passwordv = password.getText().toString();

			if (usernamev.equals("")) {
				Toast.makeText(this, "«Î ‰»Î”√ªß√˚", 1000).show();
				return;
			}
			if (passwordv.equals("")) {
				Toast.makeText(this, "«Î ‰»Î√‹¬Î", 1000).show();
				return;
			}
			getData();

		}
	}
}
