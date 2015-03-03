package com.example.cityguild.fragement;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.examlpe.ices.util.TitleMainUtil;
import com.examlpe.ices.util.Tools;
import com.examlpe.ices.util.XListView;
import com.examlpe.ices.util.XListView.IXListViewListener;
import com.example.cityguild.Config;
import com.example.cityguild.R;
import com.example.cityguild.activity.MainActivity;
import com.example.cityguild.activity.jigouDetailActivity;
import com.example.cityguild.adapter.jigouAdapter;
import com.example.cityguild.entity.MerchantsEntity;
import com.example.cityguild.entity.jigouEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class jigouFragment extends Fragment implements IXListViewListener,
		OnClickListener {
	private XListView lv;
	private int page = 1;
	private int page1 = 1;
	private LinearLayout eva_nodata;
	private boolean onRefresh_number = true;
	private boolean isSystem = true;
	private int i = 0;
	private int j = 0;
	// private AroundAdapter myAdapter;
	List<jigouEntity> myList = new ArrayList<jigouEntity>();
	List<jigouEntity> moreList = new ArrayList<jigouEntity>();
	List<jigouEntity> myList1 = new ArrayList<jigouEntity>();
	List<jigouEntity> moreList1 = new ArrayList<jigouEntity>();
	private View view;
	private jigouAdapter adapter = null;
	private jigouAdapter adapter1 = null;
	private Button img_left;
	private MainActivity mActivity;
	private LinearLayout ll_tt;
	private LinearLayout ll_left;
	private LinearLayout ll_right;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				onLoad();
				LinearLayout ll_load = (LinearLayout)view.findViewById(R.id.load);
				ll_load.setVisibility(View.GONE);
			/*	if (myList.size() == 0) {
					 //norecord_text_to.setText("您没有相关的商品");
					 lv.setVisibility(View.GONE);
					 eva_nodata.setVisibility(View.VISIBLE);
				}*/
				onRefresh_number = true;
				adapter.notifyDataSetChanged();
				adapter1.notifyDataSetChanged();
				break;
			case 1:
				Toast.makeText(mActivity, "没有更多数据!",1000).show();

				break;
			case 2: // 网络有问题
				Toast.makeText(mActivity, "没有网络连接!",
						Toast.LENGTH_SHORT).show();
				break;
			case 3:
				Toast.makeText(mActivity, " refresh too much",
						Toast.LENGTH_SHORT).show();
				break;
			case 4:
				Toast.makeText(mActivity, " 列表为空!",
						Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};
	private TextView tv_left;
	private TextView tv_right;
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
		// TODO Auto-generated method stub
		if (view == null) {
			view = inflater.inflate(R.layout.jigou_main, null);
			new TitleMainUtil(mActivity, "机构查询", view).init();
			initView();
			getSystemData();
		}
		return view;

	}
	private void onLoad() {
		lv.stopRefresh();
		lv.stopLoadMore();
		lv.setRefreshTime(Tools.getHourAndMin());
	}
	private void getSystemData() {
		getData("1");

		handler.sendEmptyMessage(0);
	}

	private void getGroupData() {
		// TODO Auto-generated method stub
		// Toast.makeText(getApplicationContext(), "getData", 1000).show();
		getData("2");
		handler.sendEmptyMessage(0);
	}

	private void initView() {
		tv_left = (TextView)view.findViewById(R.id.tv_left);
		tv_right = (TextView)view.findViewById(R.id.tv_right);
		img_left = (Button) view.findViewById(R.id.titlebar_img_left);
		img_left.setOnClickListener(this);
		eva_nodata = (LinearLayout) view.findViewById(R.id.eva_nodata);
		lv = (XListView) view.findViewById(R.id.lv);
		lv.setPullLoadEnable(true);
		lv.setXListViewListener(this);
		lv.setDivider(null);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 跳转页面
				Intent i=new Intent(mActivity,jigouDetailActivity.class);
				Log.i("ccccccccccccc",view.getTag().toString());
				if(isSystem){
					i.putExtra("id", myList.get(position-1).getId());
				}else{
					i.putExtra("id", myList1.get(position-1).getId());
				}
				
				startActivity(i);
			}
		});
		adapter = new jigouAdapter(mActivity, myList);
		adapter1 = new jigouAdapter(mActivity, myList1);
		lv.setAdapter(adapter);
		ll_tt = (LinearLayout) view.findViewById(R.id.ll_tt);
		ll_left = (LinearLayout) view.findViewById(R.id.tt_left);
		ll_right = (LinearLayout) view.findViewById(R.id.tt_right);

		ll_left.setOnClickListener(new OnClickListener() {

			@SuppressLint("ResourceAsColor") @Override
			public void onClick(View v) {
				if (isSystem == false) {
					lv.setAdapter(adapter);
					isSystem = true;
					tv_left.setBackgroundResource(R.drawable.jigounav);
					tv_right.setBackgroundResource(R.drawable.bt_bg);
					//ll_tt.setBackgroundDrawable(getResources().getDrawable(R.drawable.tt_bg));
					tv_left.setTextColor(getResources().getColor(R.color.white));
					tv_right.setTextColor(getResources().getColor(R.color.jigoutv));
					getSystemData();
				}

			}
		});
		ll_right.setOnClickListener(new OnClickListener() {

			@SuppressLint("ResourceAsColor") @Override
			public void onClick(View v) {
				if (isSystem = true) {
					if (adapter1 == null) {
						adapter1 = new jigouAdapter(mActivity, myList1);

					}
					//ll_tt.setBackgroundDrawable(getResources().getDrawable(R.drawable.tt_bg1));
					tv_left.setBackgroundResource(R.drawable.bt_bg);
					tv_right.setBackgroundResource(R.drawable.jigounav);
					tv_left.setTextColor(getResources().getColor(R.color.jigoutv));
					tv_right.setTextColor(getResources().getColor(R.color.white));

					lv.setAdapter(adapter1);
					isSystem = false;
					getGroupData();
				}
			}
		});

	}
	@Override
	public void onClick(View view) {
		if (view.getId() == R.id.titlebar_img_left) {
			MainActivity.mSlidingMenu.toggle();
		}

	}

	@Override
	public void onRefresh() {
		
		i = 1;
		// Toast.makeText(getApplicationContext(), "onRefresh", 1000).show();
		if (isSystem) {
			
			page = 1;
			myList.clear();
			getSystemData();
		} else {
			page1 = 1;
			myList1.clear();
			getGroupData();
		}

	}

	@Override
	public void onLoadMore() {
		if (onRefresh_number) {
			
			if (Tools.isConnect(mActivity)) {
				onRefresh_number = false;
				if (isSystem) {
					page = page + 1;
					getSystemData();
				} else {
					page1 = page1 + 1;
					getGroupData();
				}
			} else {
				onRefresh_number = true;
				handler.sendEmptyMessage(2);
			}
		} else {
			handler.sendEmptyMessage(3);
		}

	}
	private void getData(final String type) {
		String url="";	
		RequestParams params = new RequestParams();
		if (type.equals("1")) {
			params.put("page", page);
			
			url=Config.JIGOUCHAXUN;
			Log.i("offset", page + "");
		} else {
			url=Config.JIGOUCHAXUN1;
			params.put("page", page1);	
			Log.i("offset1", page1 + "");
		}
		 //params.setUseJsonStreamer(true);

		AsyncHttpClient c = new AsyncHttpClient();

		c.post(url, params, new AsyncHttpResponseHandler() {

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
						
						String content=jsonobject.getString("result");
						jsonobject = new JSONObject(content);
						
						if (type.equals("1")) {
							moreList.clear();
							moreList = gson.fromJson(
									
									jsonobject.getString("content"),
									new TypeToken<List<jigouEntity>>() {
									}.getType());
							Log.i("xxx",moreList.size()+"");
							// Log.i(myList.get(0).getName(),"111");
						
							myList.addAll(moreList);
							if(myList.size()==0){
								handler.sendEmptyMessage(4);
							}else{
								if(moreList.size()==0)
								handler.sendEmptyMessage(1);
							}
							handler.sendEmptyMessage(0);
						} else {
							moreList1.clear();
							moreList1 = gson.fromJson(
									jsonobject.getString("content"),
									new TypeToken<List<jigouEntity>>() {
									}.getType());
							 
							myList1.addAll(moreList1);
							if(myList1.size()==0){
								handler.sendEmptyMessage(4);
							}else{
								if(moreList1.size()==0)
								handler.sendEmptyMessage(1);
							}
							
						}
					} else {
						Toast.makeText(mActivity,
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
}
