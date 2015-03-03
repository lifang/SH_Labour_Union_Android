package com.example.cityguild.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.platform.comapi.map.t;
import com.examlpe.ices.util.TitleMenuUtil;
import com.example.cityguild.BaseActivity;
import com.example.cityguild.Config;
import com.example.cityguild.MyApplication;
import com.example.cityguild.R;
import com.example.cityguild.adapter.PostTuiAdapter;
import com.example.cityguild.entity.PostEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class PostDetail extends BaseActivity{
	private RelativeLayout rl_tuijian;
	private TextView jobname;
	private TextView unitname;
	private TextView zprs;
	private TextView lxfs;
	private TextView unitabout;
	private TextView jobabout;
	private TextView adress;
	
	private String id;
	PostEntity pe = new PostEntity();
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
		
				
				break;
			case 1:
				Toast.makeText(PostDetail.this, (String) msg.obj,
						Toast.LENGTH_SHORT).show();
				break;
			case 2: // 网络有问题
				Toast.makeText(PostDetail.this, "没有网络连接!",
						Toast.LENGTH_SHORT).show();
				break;
			case 3:
			 
				break;
			case 4:
			 
				break;
			}
		}
	};
	private TextView gsname;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.postdetail);
		new TitleMenuUtil(this, "详情").init();
		id=getIntent().getStringExtra("id");
		initView();
		getData();
	}
	
	private void initView() {
		rl_tuijian = (RelativeLayout)findViewById(R.id.tuijian);
		rl_tuijian.setOnClickListener(this);
		
		jobname = (TextView)findViewById(R.id.jobname);
		unitname = (TextView)findViewById(R.id.unitname);
		zprs = (TextView)findViewById(R.id.zprs);
		lxfs = (TextView)findViewById(R.id.lxfs);
		unitabout = (TextView)findViewById(R.id.unitabout);
		jobabout = (TextView)findViewById(R.id.jobabout);
		adress = (TextView)findViewById(R.id.adress);
		gsname = (TextView)findViewById(R.id.gsname);		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		if(v.getId()==R.id.tuijian){
			Intent i=new Intent(PostDetail.this,PostOtherActivity.class);
			i.putExtra("id", id);
			startActivity(i);
		}
	}
	private void getData() {
	
		RequestParams params = new RequestParams();
		params.put("id", id); 
	
		AsyncHttpClient c = new AsyncHttpClient();
		
		c.post(Config.POSTDETAIL,params, new AsyncHttpResponseHandler() {

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
						
						pe = gson.fromJson(jsonobject.getString("result"),
	 		 					new TypeToken<PostEntity>() {
	 							}.getType());
						//Log.i(myList.get(0).getName(),"111");
						
						jobname.setText(pe.getJob_name());
						unitname.setText(pe.getUnit_name());
						zprs.setText(pe.getRs());
						lxfs.setText(pe.getLxfs());
						unitabout.setText(pe.getUnit_about());
						jobabout.setText(pe.getJob_about());
						adress.setText(pe.getLocate());
						gsname.setText(pe.getUnit_name());
						handler.sendEmptyMessage(0);
					}else{
						Toast.makeText(PostDetail.this, jsonobject.getString("message"),
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
				// TODO Auto-generated method stub
				
			}
		});
		
	}
}
