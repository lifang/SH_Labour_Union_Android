package com.example.cityguild.activity;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.examlpe.ices.util.TitleMenuUtil;
import com.example.cityguild.AlertDialog;
import com.example.cityguild.BaseActivity;
import com.example.cityguild.Config;
import com.example.cityguild.MyApplication;
import com.example.cityguild.R;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class LandPersonalCenterActivity extends BaseActivity {


	private LinearLayout ll_ws;
	private String name="";
	private TextView nickname;
	private LinearLayout ll_tuichu;
	private AlertDialog ad;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personalland);
		new TitleMenuUtil(this, "个人中心").init();
		
		
		initView();
	}

	private void initView() {
		ll_ws = (LinearLayout) findViewById(R.id.ll_ws);
		ll_ws.setOnClickListener(this);
		nickname = (TextView)findViewById(R.id.name);
		if(Config.ue.getNickName()!=null){
			nickname.setText(Config.ue.getNickName());
		}
		ll_tuichu = (LinearLayout) findViewById(R.id.ll_tuichu);
		ll_tuichu.setOnClickListener(this);
	}
	private void getData() {

		RequestParams params = new RequestParams();
		params.put("token", Config.token);

		AsyncHttpClient c = new AsyncHttpClient();

		MyApplication.getInstance().getClient().post(Config.LOGINOUT, params, new AsyncHttpResponseHandler() {

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
						Toast.makeText(LandPersonalCenterActivity.this,
								jsonobject.getString("message"),
								Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(LandPersonalCenterActivity.this,
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
		if(v.getId()==R.id.ll_ws){
			Intent i=new Intent(this,PersonnalManger.class);
			startActivity(i);
		}
		if(v.getId()==R.id.ll_tuichu){
			ad = new AlertDialog(this);
			ad.setTitle("提示");
			ad.setMessage("你确定要退出应用吗?");
			ad.setPositiveButton("取消", new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					ad.dismiss();
					
				}
			});
			ad.setNegativeButton("确定", new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					Config.ue=null;
					Config.token=null;
					Intent i=new Intent(LandPersonalCenterActivity.this,LandActivity.class);
					startActivity(i);
					getData();
					LandPersonalCenterActivity.this.finish();
				}
			});
		}
		
	}
}
