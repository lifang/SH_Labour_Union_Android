package com.example.cityguild.activity;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.examlpe.ices.util.TitleMenuUtil;
import com.examlpe.ices.util.Tools;
import com.example.cityguild.AlertDialog;
import com.example.cityguild.BaseActivity;
import com.example.cityguild.Config;
import com.example.cityguild.R;
import com.example.cityguild.entity.NewestDetailEntity;
import com.example.cityguild.entity.jigouEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class jigouDetailActivity extends BaseActivity{
	private RelativeLayout rl_map;
	private String id;
	private TextView name;
	private TextView tel;
	private TextView adress;
	private TextView jianjie;
	private RelativeLayout rl_call;
	private AlertDialog ad;
	private TextView worktime;
	private TextView jgxq;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jigou_detail);
		new TitleMenuUtil(this, "机构详情").init();
		id=getIntent().getStringExtra("id");
		initView();
		getData();
	}
	
	private void initView() {
		rl_map = (RelativeLayout)findViewById(R.id.map_adress);
		rl_map.setOnClickListener(this);
		name = (TextView)findViewById(R.id.name);
		tel = (TextView)findViewById(R.id.tel);
		adress = (TextView)findViewById(R.id.adress);
		worktime = (TextView)findViewById(R.id.worktime);
		jgxq = (TextView)findViewById(R.id.jgxq);
		jianjie = (TextView)findViewById(R.id.jianjie);
		rl_call = (RelativeLayout)findViewById(R.id.rl_call);
		rl_call.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		if(v.getId()==R.id.map_adress){
		
			if (Tools.isConnect(jigouDetailActivity.this)) {
				Intent i=new Intent(jigouDetailActivity.this,MerchantsMap.class);
				i.putExtra("adress", adress.getText().toString());
				i.putExtra("name", name.getText().toString());
				startActivity(i);
			}else{
				Toast.makeText(getApplicationContext(), "请检查网络连接!", 1000).show();
			}
		}
		if(v.getId()==R.id.rl_call){
			ad = new AlertDialog(this);
			ad.setTitle("提示");
			ad.setMessage(tel.getText().toString());
			ad.setPositiveButton("取消", new OnClickListener() {				
				@Override
				public void onClick(View arg0) {
					ad.dismiss();				
				}
			});
			ad.setNegativeButton("确定", new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					ad.dismiss();
				    Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+tel.getText().toString()));				     
				    startActivity(intent);
				}
			});
		}
	}
	private void getData() {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		AsyncHttpClient client = new AsyncHttpClient(); // 创建异步请求的客户端对象
		RequestParams params = new RequestParams();
		params.put("id", id); 
		Log.i("ID", id);
		//params.setUseJsonStreamer(true);
		AsyncHttpClient c = new AsyncHttpClient();
		
		c.post(Config.JIGOUDETAIL,params,new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] responseBody) {
				// TODO Auto-generated method stub
				String userMsg = new String(responseBody).toString();
	 
				Log.i("ljp", userMsg);
				Gson gson = new Gson();
				//EventEntity
				JSONObject jsonobject = null;
				int code = 0;
				try {
					jsonobject = new JSONObject(userMsg); 
					code = jsonobject.getInt("code");					
					if(code==-2){
						
					}else if(code==1){ 
						jigouEntity jigou = gson.fromJson(jsonobject.getString("result"),
	 		 					new TypeToken<jigouEntity>() {
	 							}.getType());
						if(jsonobject.getString("result")!=null){
						if(jigou.getName()!=null)
						name.setText(jigou.getName());
						if(jigou.getTel()!=null)
						tel.setText(jigou.getTel());
						if(jigou.getAddr()!=null)
						adress.setText(jigou.getAddr());
						
						if(jigou.getWorktime()!=null){
							if(jigou.getWorktime().equals("")){
								worktime.setText("工作时间");
							}else{
								worktime.setText(jigou.getWorktime());
							}
							
						}else{
							worktime.setText("工作时间");
						}
							
						}
					}else{
						Toast.makeText(jigouDetailActivity.this, jsonobject.getString("message"),
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
				Log.i("sb","sb");
				
			}
		});

	
	}
}
