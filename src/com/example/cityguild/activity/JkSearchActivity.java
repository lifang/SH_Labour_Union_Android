package com.example.cityguild.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.examlpe.ices.util.Tools;
import com.examlpe.ices.util.XListView;
import com.examlpe.ices.util.XListView.IXListViewListener;
import com.example.cityguild.BaseActivity;
import com.example.cityguild.Config;
import com.example.cityguild.MyApplication;
import com.example.cityguild.R;
import com.example.cityguild.adapter.YiyuanAdapter;
import com.example.cityguild.entity.DoctorEntity;
import com.example.cityguild.entity.yiyuanEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class JkSearchActivity extends BaseActivity implements
		IXListViewListener {
	private int type;
	private int page = 0;
	private int page1 = 0;
	private boolean onRefresh_number = true;
	List<yiyuanEntity> myList = new ArrayList<yiyuanEntity>();
	List<yiyuanEntity> moreList = new ArrayList<yiyuanEntity>();
	List<DoctorEntity> myList1 = new ArrayList<DoctorEntity>();
	List<DoctorEntity> moreList1 = new ArrayList<DoctorEntity>();
	private XListView list;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				ll_load.setVisibility(View.GONE);
				onLoad();
				adapter1.notifyDataSetChanged();
				adapter.notifyDataSetChanged();
				onRefresh_number = true;
				break;
			case 1:
				Toast.makeText(JkSearchActivity.this, (String) msg.obj,
						Toast.LENGTH_SHORT).show();
				break;
			case 2: // 网络有问题
				Toast.makeText(JkSearchActivity.this, "没有网络连接!",
						Toast.LENGTH_SHORT).show();
				break;
			case 3:
				Toast.makeText(JkSearchActivity.this, "没有更多数据",
						Toast.LENGTH_SHORT).show();
				break;
			case 4:

				break;
			}
		}
	};
	private YiyuanAdapter adapter;
	private TextView tv_yiyuan;
	private TextView tv_doctor;
	private EditText ed;
	private String url;
	private LinearLayout ll_nav;
	private LinearLayout ll_adress;
	private String city;
	private String sheng;
	private TextView tv_adress;
	private YiyuanAdapter adapter2;
	private MyDoctor adapter1;
	private LinearLayout ll_load;
	private LinearLayout ll_nocode;
	private String area_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.guhaosearch);
		city=getIntent().getStringExtra("city");
		sheng=getIntent().getStringExtra("sheng");
		initView();
		tv_adress.setText(city);
	}

	@Override
	public void onRefresh() {
		if(type==0){
			page = 0;
			myList.clear();
			getData(0);
		}
		if(type==1){
			page1 = 0;
			myList1.clear();
			getData(1);
		}

	}

	private void initView() {
		ll_nocode = (LinearLayout)findViewById(R.id.in_no_record);
		list = (XListView) findViewById(R.id.list);
		adapter = new YiyuanAdapter(this, myList);

		list.setAdapter(adapter);
		list.setPullLoadEnable(true);
		list.setXListViewListener(this);
		list.setDivider(null);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				position=position-1;
				if(type==0){
					
					Intent i=new Intent(JkSearchActivity.this,KeshiActivity.class);
					i.putExtra("cpid", myList.get(position).getCpid());
					i.putExtra("flag", "flag");
					i.putExtra("hospitalid", myList.get(position).getHospitalid());
					Config.yiyuanv=myList.get(position).getHospitalname();
						startActivity(i);
				}else{
					Intent i=new Intent(JkSearchActivity.this,JumpActivity.class);
					startActivity(i);
				}
			
			}
		});

		tv_yiyuan = (TextView) findViewById(R.id.sear_yy);
		tv_doctor = (TextView) findViewById(R.id.sear_ys);
		tv_yiyuan.setOnClickListener(this);
		tv_doctor.setOnClickListener(this);
		ll_nav = (LinearLayout)findViewById(R.id.ll_nav);
		ed = (EditText) findViewById(R.id.search);
		
		ll_adress = (LinearLayout)findViewById(R.id.ll_adress);
		ll_adress.setOnClickListener(this);
		tv_adress = (TextView)findViewById(R.id.adress);
		LinearLayout ll_caidan = (LinearLayout)findViewById(R.id.ll_caidan);
		ll_caidan.setOnClickListener(this);
		adapter1 = new MyDoctor();
		list.setAdapter(adapter1);
		ll_load = (LinearLayout)findViewById(R.id.load);
	}

	@SuppressLint("ResourceAsColor") @Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		if (v.getId() == R.id.sear_yy) {
			ll_nocode.setVisibility(View.GONE);
			ll_load.setVisibility(View.VISIBLE);
			myList.clear();
			ll_nav.setBackgroundResource(R.drawable.gh_nav1);
			page=0;
			type=0;
			tv_yiyuan.setTextColor(getResources().getColor(R.color.white));
			tv_doctor.setTextColor(getResources().getColor(R.color.yishentv));
			getData(type);
			list.setAdapter(adapter);
		}
		if (v.getId() == R.id.sear_ys) {
			ll_nocode.setVisibility(View.GONE);
			ll_load.setVisibility(View.VISIBLE);
			myList1.clear();
			ll_nav.setBackgroundResource(R.drawable.gh_nav2);
			page1=0;
			type=1;
			getData(type);
			tv_yiyuan.setTextColor(getResources().getColor(R.color.yishentv));
			tv_doctor.setTextColor(getResources().getColor(R.color.white));
			list.setAdapter(adapter1);
		}
		if(v.getId()==R.id.ll_adress){
			Intent i=new Intent(this,AllCityActivity.class);
			i.putExtra("sheng", sheng);
			i.putExtra("city", city);
			startActivityForResult(i, 0);
		}
		if(v.getId()==R.id.ll_caidan){
			this.finish();
		}
		
	}

	private void onLoad() {
		list.stopRefresh();
		list.stopLoadMore();
		list.setRefreshTime(Tools.getHourAndMin());
	}

	private void getData(final int type) {
		RequestParams params = new RequestParams();
		if(type==0){
			params.put("offset", page);
			params.put("keyword", ed.getText().toString().trim());
			url = Config.FINDYIYUAN;
		}
		if(type==1){
			params.put("offset", page1);
			params.put("keyword", ed.getText());
			url = Config.SEARCHDOCTOR;
		}
		params.put("pageSize", "10");
		params.put("locate", Config.area_id); 
		Log.i("offset",page1+"");
		Log.i("keyword",ed.getText().toString().trim());
		MyApplication
				.getInstance()
				.getClient()
				.post(url, params,
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
										if(type==0){
										moreList.clear();
										moreList = gson.fromJson(
												jsonobject.getString("result"),
												new TypeToken<List<yiyuanEntity>>() {
												}.getType());

										// Log.i(myList.get(0).getName(),"111");
										if(moreList.size()==0){
											if(myList.size()==0){
												ll_nocode.setVisibility(View.VISIBLE);
											}else{
												handler.sendEmptyMessage(3);
											}
											
										}
										myList.addAll(moreList);
									
										}
										if(type==1){
											
											moreList1.clear();
											moreList1 = gson.fromJson(
													jsonobject.getString("result"),
													new TypeToken<List<DoctorEntity>>() {
													}.getType());
											if(moreList1.size()==0){
												if(myList1.size()==0){
													ll_nocode.setVisibility(View.VISIBLE);
												}else{
													handler.sendEmptyMessage(3);
												}
											}
											// Log.i(myList.get(0).getName(),"111");
											myList1.addAll(moreList1);
									
											}
										handler.sendEmptyMessage(0);
										
									} else {
										Toast.makeText(
												JkSearchActivity.this,
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
								Log.i("sb","sb");

							}
						});

	}

	@Override
	public void onLoadMore() {
		if (onRefresh_number) {

			if (Tools.isConnect(this)) {
				onRefresh_number = false;
				if(type==0){
				page = page + 1;
				getData(0);
				}
				if(type==1){
					page1 = page1 + 1;
					getData(1);
					}

			} else {
				onRefresh_number = true;
				handler.sendEmptyMessage(2);
			}
		} else {
			handler.sendEmptyMessage(3);
		}

	}

	class MyDoctor extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return myList1.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return myList1.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View view, ViewGroup arg2) {
			// TODO Auto-generated method stub
			LayoutInflater inflater = LayoutInflater.from(JkSearchActivity.this);
			view=inflater.inflate(R.layout.doctorscitem, null);
			
			TextView name=(TextView)view.findViewById(R.id.name);
			name.setText(myList1.get(position).getDocname());
			return view;
		}
		
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (data != null) {
			Bundle b = data.getExtras();			
			city = b.getString("city");
			sheng = b.getString("sheng");
			area_id = b.getString("area_id");
			if (city != null){
				tv_adress.setText(city);
				Config.city=city;
			}
			if(sheng!=null)
				Config.sheng=sheng;
			if(area_id!=null)
				Config.area_id=area_id;
		}
	}
	
}
