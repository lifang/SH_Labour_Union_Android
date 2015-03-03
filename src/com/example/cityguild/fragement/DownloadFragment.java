package com.example.cityguild.fragement;


import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.examlpe.ices.util.TitleMainUtil;
import com.examlpe.ices.util.TitleMenuUtil;
import com.example.cityguild.Config;
import com.example.cityguild.MyApplication;
import com.example.cityguild.R;
import com.example.cityguild.activity.MainActivity;
import com.example.cityguild.adapter.DownloadAdapter;
import com.example.cityguild.entity.DownloadEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
public class DownloadFragment extends Fragment {
	List<DownloadEntity> myList = new ArrayList<DownloadEntity>();
	List<DownloadEntity> moreList = new ArrayList<DownloadEntity>();
	private View view;
	private MainActivity mActivity;
	private ListView lv;
	private DownloadAdapter adapter;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				LinearLayout ll_load = (LinearLayout)view.findViewById(R.id.load);
				ll_load.setVisibility(View.GONE);
				Log.i("0","0");
				adapter.notifyDataSetChanged();
				lv.postInvalidate();
				break;
			case 1:
				Toast.makeText(mActivity, (String) msg.obj,
						Toast.LENGTH_SHORT).show();
				break;
			case 2: // 网络有问题
				Toast.makeText(mActivity, "没有网络连接!",
						Toast.LENGTH_SHORT).show();
				break;
			case 3:
			 
				break;
			case 4:
			 
				break;
			}
		}
	};
	private Button btn_back;
	private LinearLayout ll_back;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	@Override
	public void onAttach(Activity activity) {
		mActivity = (MainActivity) activity;
		
		super.onAttach(activity);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.download, null);
			initView();
			getData();
			
			
		}
		return view;
	}
	private void initView() {
		ll_back = (LinearLayout)view.findViewById(R.id.ll_left);
		ll_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				mActivity.finish();
				
				
			}
		});
		
		lv = (ListView)view.findViewById(R.id.list);
		adapter = new DownloadAdapter(mActivity, myList);
		lv.setAdapter(adapter);
		
	}
	private void getData() {
		RequestParams params = new RequestParams();
		params.put("token", MyApplication.getToken()); 
		//params.setUseJsonStreamer(true);
		
		System.out.println("url"+"/api/download/list");
		System.out.println("MyApplication.getToken()---"+MyApplication.getToken());
		AsyncHttpClient c = new AsyncHttpClient();
		
		c.post(Config.DOWN,  new AsyncHttpResponseHandler() {

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
	 		 					new TypeToken<List<DownloadEntity>>() {
	 							}.getType());
						//Log.i(myList.get(0).getName(),"111");
						myList.addAll(moreList);
						handler.sendEmptyMessage(0);
					}else{
						Toast.makeText(mActivity, jsonobject.getString("message"),
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
