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
import com.example.cityguild.adapter.KeshiAdapter;
import com.example.cityguild.adapter.YiyuanAdapter;
import com.example.cityguild.entity.CityEntity;
import com.example.cityguild.entity.KeshiEntity;
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
import android.widget.TextView;
import android.widget.Toast;

public class KeshiActivity extends BaseActivity implements IXListViewListener {
	private XListView list;
	List<KeshiEntity> myList = new ArrayList<KeshiEntity>();
	List<KeshiEntity> moreList = new ArrayList<KeshiEntity>();
	private int page = 0;
	private boolean onRefresh_number = true;
	private KeshiAdapter adapter;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				onLoad();
				adapter.notifyDataSetChanged();
				onRefresh_number = true;
				break;
			case 1:
				Toast.makeText(KeshiActivity.this, (String) msg.obj,
						Toast.LENGTH_SHORT).show();
				break;
			case 2: // 网络有问题
				Toast.makeText(KeshiActivity.this, "没有网络连接!", Toast.LENGTH_SHORT)
						.show();
				break;
			}
		}
	};
	private String hospitalid;
	private String cpid;
	private LinearLayout ll_back;
	private String flag="";
	private TextView title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.yiyuanlist);
		hospitalid = getIntent().getStringExtra("hospitalid");
		cpid = getIntent().getStringExtra("cpid");
		flag = getIntent().getStringExtra("flag");
		initView();
		getData();
	}

	private void initView() {
		title = (TextView)findViewById(R.id.title);
		title.setText("科室列表");
		list = (XListView) findViewById(R.id.list);
		adapter = new KeshiAdapter(this, myList);
		list.setAdapter(adapter);
		list.setPullLoadEnable(true);
		list.setXListViewListener(this);
		list.setDivider(null);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				if(flag!=null){
					Intent i=new Intent(KeshiActivity.this,DoctorActivity.class);
					i.putExtra("hospitalid", hospitalid);
					i.putExtra("deptidv", myList.get(position-1).getDeptid());
					i.putExtra("cpid", myList.get(position-1).getCpid());
					i.putExtra("yiyuan", Config.yiyuanv);
					i.putExtra("keshi", myList.get(position-1).getDeptname());
					startActivity(i);
				}else{
					Intent i = new Intent();
					Bundle b = new Bundle();
					b.putString("keshi", myList.get(position-1).getDeptname().toString());
					b.putString("deptid", myList.get(position-1).getDeptid().toString());
					i.putExtras(b);
					KeshiActivity.this.setResult(KeshiActivity.this.RESULT_OK, i);
					KeshiActivity.this.finish();
				}
						
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
		params.put("cpid", cpid);
		params.put("hospitalid", hospitalid);
		params.put("locate", Config.area_id);
		MyApplication.getInstance().getClient()
				.post(Config.FINDKESHI,params, new AsyncHttpResponseHandler() {

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
										new TypeToken<List<KeshiEntity>>() {
										}.getType());

								// Log.i(myList.get(0).getName(),"111");
								myList.addAll(moreList);

								handler.sendEmptyMessage(0);
							} else {
								Toast.makeText(KeshiActivity.this,
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
