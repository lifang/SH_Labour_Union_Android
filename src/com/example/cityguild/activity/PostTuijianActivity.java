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
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.examlpe.ices.util.TitleMenuUtil;
import com.example.cityguild.BaseActivity;
import com.example.cityguild.Config;
import com.example.cityguild.MyApplication;
import com.example.cityguild.R;
import com.example.cityguild.adapter.PostLastAdapter;
import com.example.cityguild.adapter.PostTuiAdapter;
import com.example.cityguild.entity.PostEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class PostTuijianActivity extends BaseActivity {
	List<PostEntity> myList = new ArrayList<PostEntity>();
	List<PostEntity> moreList = new ArrayList<PostEntity>();
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				adapter.notifyDataSetChanged();
				
				break;
			case 1:
				Toast.makeText(PostTuijianActivity.this, (String) msg.obj,
						Toast.LENGTH_SHORT).show();
				break;
			case 2: // 网络有问题
				Toast.makeText(PostTuijianActivity.this, "没有网络连接!",
						Toast.LENGTH_SHORT).show();
				break;
			case 3:
			 
				break;
			case 4:
			 
				break;
			}
		}
	};
	private PostTuiAdapter adapter;
	private ListView list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.posttype);
		new TitleMenuUtil(this, "最新职位").init();
		initView();
		getData();
	}

	private void initView() {
		list = (ListView)findViewById(R.id.list);
		adapter=new PostTuiAdapter(this,myList);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				Intent i=new Intent(PostTuijianActivity.this,PostDetail.class);
				i.putExtra("id", myList.get(position).getId());
				startActivity(i);
				
			}
		});
		
	}
	private void getData() {
		RequestParams params = new RequestParams();
		params.put("token", MyApplication.getToken()); 
		//params.setUseJsonStreamer(true);
		
		System.out.println("url"+"/api/download/list");
		System.out.println("MyApplication.getToken()---"+MyApplication.getToken());
		AsyncHttpClient c = new AsyncHttpClient();
		
		c.post(Config.POSTNEST,  new AsyncHttpResponseHandler() {

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
						myList.clear();
						moreList = gson.fromJson(jsonobject.getString("result"),
	 		 					new TypeToken<List<PostEntity>>() {
	 							}.getType());
						//Log.i(myList.get(0).getName(),"111");
						myList.addAll(moreList);
						handler.sendEmptyMessage(0);
					}else{
						Toast.makeText(PostTuijianActivity.this, jsonobject.getString("message"),
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
