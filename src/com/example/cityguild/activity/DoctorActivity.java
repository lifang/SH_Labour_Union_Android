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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.examlpe.ices.util.Tools;
import com.examlpe.ices.util.XListView;
import com.examlpe.ices.util.XListView.IXListViewListener;
import com.example.cityguild.BaseActivity;
import com.example.cityguild.Config;
import com.example.cityguild.MyApplication;
import com.example.cityguild.R;
import com.example.cityguild.adapter.DoctorAdapter;
import com.example.cityguild.entity.DoctorEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class DoctorActivity extends BaseActivity implements IXListViewListener{
	private XListView list;
	private String yiyuan;
	private String keshi;
	private int page =0;
	private boolean onRefresh_number = true;
	List<DoctorEntity> myList = new ArrayList<DoctorEntity>();
	List<DoctorEntity> moreList = new ArrayList<DoctorEntity>();
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				onLoad();
				adapter.notifyDataSetChanged();
				onRefresh_number = true;
			
				break;
			case 1:
				Toast.makeText(DoctorActivity.this, (String) msg.obj,
						Toast.LENGTH_SHORT).show();
				break;
			case 2: // 网络有问题
				Toast.makeText(DoctorActivity.this, "没有网络连接!", Toast.LENGTH_SHORT)
						.show();
				break;
			case 3:
				Toast.makeText(DoctorActivity.this, "列表为空!", Toast.LENGTH_SHORT)
				.show();
				break;
			case 4:
				Toast.makeText(DoctorActivity.this, "没有更多数据!", Toast.LENGTH_SHORT)
				.show();
				break;
			}
		}
	};
	private DoctorAdapter adapter;
	private String hospitalid;
	private String deptidv;
	private String cpid;
	private LinearLayout ll_back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.yishenglist);
		yiyuan = getIntent().getStringExtra("yiyuan");
		keshi = getIntent().getStringExtra("keshi");
		hospitalid = getIntent().getStringExtra("hospitalid");
		deptidv = getIntent().getStringExtra("deptidv");
		cpid = getIntent().getStringExtra("cpid");
		initView();
		getData();
	}

	private void initView() {
		list = (XListView) findViewById(R.id.list);
		adapter = new DoctorAdapter(this, myList, yiyuan, keshi);
		list.setAdapter(adapter);
		list.setPullLoadEnable(true);
		list.setXListViewListener(this);
		list.setDivider(null);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent i=new Intent(DoctorActivity.this,JumpActivity.class);
				startActivity(i);
				
			}
		});
		ll_back = (LinearLayout)findViewById(R.id.back);
		ll_back.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		super.onClick(v);
		if(v.getId()==R.id.back){
			this.finish();
		}
	}
	private void onLoad() {
		list.stopRefresh();
		list.stopLoadMore();
		list.setRefreshTime(Tools.getHourAndMin());
	}

	private void getData() {
		RequestParams params = new RequestParams();
	/*	params.put("offset", page);
		params.put("hospitalid", hospitalid);
		params.put("deptid", deptidv);
		params.put("cpid", cpid);*/
		//params.put("offset", page);
		params.put("offset", page);
		params.put("hospitalid",hospitalid);
		params.put("deptid", deptidv);
		params.put("cpid", cpid);
		params.put("locate", Config.area_id);
		Log.i("cpid",cpid);
		Log.i("hospitalid",hospitalid);
		MyApplication.getInstance().getClient()
				.post(Config.FINDDOCTOR,params, new AsyncHttpResponseHandler() {

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
								moreList.clear();
								moreList = gson.fromJson(
										jsonobject.getString("result"),
										new TypeToken<List<DoctorEntity>>() {
										}.getType());

								// Log.i(myList.get(0).getName(),"111");
								myList.addAll(moreList);
								if(myList.size()==0){
									handler.sendEmptyMessage(3);
								}else{
									if(moreList.size()==0)
										handler.sendEmptyMessage(4);
								}
							
								handler.sendEmptyMessage(0);
							} else {
								/*Toast.makeText(DoctorActivity.this,
										jsonobject.getString("message"),
										Toast.LENGTH_SHORT).show();*/
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

	@Override
	public void onRefresh() {
		page = 0;
		myList.clear();
		getData();

	}

	@Override
	public void onLoadMore() {
		if (onRefresh_number) {

			if (Tools.isConnect(this)) {
				onRefresh_number = false;
				page = page + 1;
				getData();

			} else {
				onRefresh_number = true;
				handler.sendEmptyMessage(2);
			}
		} else {
			handler.sendEmptyMessage(3);
		}

	}
}
