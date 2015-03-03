package com.example.cityguild.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Application;
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

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.examlpe.ices.util.TitleMenuUtil;
import com.example.cityguild.BaseActivity;
import com.example.cityguild.Config;
import com.example.cityguild.MyApplication;
import com.example.cityguild.R;
import com.example.cityguild.adapter.MerchantsListAdapter;
import com.example.cityguild.entity.MerchantsEntity;
import com.example.cityguild.entity.jigouEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class MerchantListActivity extends BaseActivity {

	private ListView shList;
	private MerchantsListAdapter mladapter;
	private Boolean isFrist = true;
	List<MerchantsEntity> myList = new ArrayList<MerchantsEntity>();
	List<MerchantsEntity> moreList = new ArrayList<MerchantsEntity>();
	private String id;
	private LatLng ll;

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				mladapter.notifyDataSetChanged();
				
				break;
			case 1:
				Toast.makeText(MerchantListActivity.this, (String) msg.obj,
						Toast.LENGTH_SHORT).show();
				break;
			case 2: // 网络有问题
				Toast.makeText(MerchantListActivity.this, "没有网络连接!",
						Toast.LENGTH_SHORT).show();
				break;
			case 4:
				Toast.makeText(MerchantListActivity.this, " 未查询到数据",
						Toast.LENGTH_SHORT).show();
				break;
			case 5:
				Toast.makeText(MerchantListActivity.this, " 没有更多数据",
						Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.merchantlist);
		id=getIntent().getStringExtra("id");
		LocationClient mLocClient = new LocationClient(this);
		mLocClient.registerLocationListener(new MyLocationListenner());
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000);
		option.setAddrType("all");
		mLocClient.setLocOption(option);
		mLocClient.start();
		initView();
	
	}

	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			if (isFrist) {
				Log.i("22", "22");
				ll = new LatLng(location.getLatitude(),
						location.getLongitude());
				Log.i("222", ll.toString());
				getData();
				MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
				isFrist=false;
			}
		}

		public void onReceivePoi(BDLocation poiLocation) {
		}
	}

	private void getData() {

		RequestParams params = new RequestParams();
		params.put("id", id);
		params.put("per_lon", String.valueOf(ll.longitude));
		params.put("per_lat",String.valueOf(ll.latitude));
		MyApplication
				.getInstance()
				.getClient()
				.post(Config.MERFINDOTHER, params,
						new AsyncHttpResponseHandler() {

							@Override
							public void onSuccess(int statusCode,
									Header[] headers, byte[] responseBody) {
								// TODO Auto-generated method stub
								String userMsg = new String(responseBody)
										.toString();

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
										moreList.clear();
										moreList = gson.fromJson(
												jsonobject.getString("result"),
												new TypeToken<List<MerchantsEntity>>() {
												}.getType());
										myList.addAll(moreList);
										handler.sendEmptyMessage(0);

									} else {
										Toast.makeText(
												MerchantListActivity.this,
												jsonobject.getString("message"),
												Toast.LENGTH_SHORT).show();
									}
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}

							@Override
							public void onFailure(int statusCode,
									Header[] headers, byte[] responseBody,
									Throwable error) {
								//Log.i("sb", "sb");

							}
						});

	}

	private void initView() {
		new TitleMenuUtil(this, "商户列表").init();
		shList = (ListView) findViewById(R.id.shanghulist);
		mladapter = new MerchantsListAdapter(this,myList);
		shList.setAdapter(mladapter);
		shList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				Intent i=new Intent(MerchantListActivity.this,MerchantsMap.class);
				i.putExtra("name", myList.get(position).getName());
				i.putExtra("adress", myList.get(position).getAddr());
				startActivity(i);
				//startActivity(new Intent(MerchantListActivity.this,
					//	MerchantDetailActivity.class));
			}
		});

	}

}
