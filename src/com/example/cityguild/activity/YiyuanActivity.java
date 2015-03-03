package com.example.cityguild.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.examlpe.ices.util.Tools;
import com.examlpe.ices.util.XListView;
import com.examlpe.ices.util.XListView.IXListViewListener;
import com.example.cityguild.BaseActivity;
import com.example.cityguild.Config;
import com.example.cityguild.MyApplication;
import com.example.cityguild.R;
import com.example.cityguild.adapter.YiyuanAdapter;
import com.example.cityguild.entity.CityEntity;
import com.example.cityguild.entity.PostEntity;
import com.example.cityguild.entity.yiyuanEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class YiyuanActivity extends BaseActivity implements IXListViewListener {
	private XListView list;
	List<yiyuanEntity> myList = new ArrayList<yiyuanEntity>();
	List<yiyuanEntity> moreList = new ArrayList<yiyuanEntity>();
	private int page =0;
	private boolean onRefresh_number = true;
	private YiyuanAdapter adapter;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				onLoad();
				adapter.notifyDataSetChanged();
				onRefresh_number = true;
				break;
			case 1:
				Toast.makeText(YiyuanActivity.this, (String) msg.obj,
						Toast.LENGTH_SHORT).show();
				break;
			case 2: // 网络有问题
				Toast.makeText(YiyuanActivity.this, "没有网络连接!", Toast.LENGTH_SHORT)
						.show();
				break;
			case 3:
				Toast.makeText(YiyuanActivity.this, "没有更多数据!", 1000).show();
				break;
			case 4:
				Toast.makeText(YiyuanActivity.this, "没有数据!", 1000).show();
				break;
			}
		}
	};
	private LinearLayout ll_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.yiyuanlist);
		initView();
		getData();
	}

	private void initView() {
		list = (XListView) findViewById(R.id.list);
		adapter = new YiyuanAdapter(this, myList);
		list.setAdapter(adapter);
		list.setPullLoadEnable(true);
		list.setXListViewListener(this);
		list.setDivider(null);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				Intent i = new Intent();
				Bundle b = new Bundle();
				b.putString("yiyuan", myList.get(position-1).getHospitalname());
				b.putString("cpid", myList.get(position-1).getCpid());
				b.putString("hospitalid", myList.get(position-1).getHospitalid());
				//Log.i("cccccccc", myList.get(position-1).getHospitalid());
				i.putExtras(b);
				Config.hospitalid=myList.get(position-1).getHospitalid();
				YiyuanActivity.this.setResult(YiyuanActivity.this.RESULT_OK, i);
				YiyuanActivity.this.finish();
				
			}
		});
		ll_back = (LinearLayout)findViewById(R.id.back);
		ll_back.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
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
		params.put("offset", page);
		params.put("locate", Config.area_id);
		Log.i("cccc",Config.area_id);
		Log.i("page",page+"");
		MyApplication.getInstance().getClient()
				.post(Config.FINDYIYUAN,params, new AsyncHttpResponseHandler() {

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
										new TypeToken<List<yiyuanEntity>>() {
										}.getType());
								
								// Log.i(myList.get(0).getName(),"111");
								myList.addAll(moreList);
								if(myList.size()==0){
									handler.sendEmptyMessage(4);
								}else{
									if(moreList.size()==0)
									handler.sendEmptyMessage(3);
								}
								handler.sendEmptyMessage(0);
							} else {
								Toast.makeText(YiyuanActivity.this,
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
