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
import com.example.cityguild.MyApplication;
import com.example.cityguild.R;
import com.example.cityguild.activity.MainActivity;
import com.example.cityguild.activity.MerchantDetailActivity;
import com.example.cityguild.adapter.MerchantsDetailListAdapter;
import com.example.cityguild.entity.DownloadEntity;
import com.example.cityguild.entity.MerchantsEntity;
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
import android.renderscript.Type;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MerchantFragment extends Fragment implements IXListViewListener,
		OnClickListener {
	private XListView lv;
	private int page = 1;
	private int page1 = 1;
	private LinearLayout eva_nodata;
	private boolean onRefresh_number = true;
	private boolean isSystem = true;
	// private AroundAdapter myAdapter;
	List<MerchantsEntity> myList = new ArrayList<MerchantsEntity>();
	List<MerchantsEntity> moreList = new ArrayList<MerchantsEntity>();
	List<MerchantsEntity> myList1 = new ArrayList<MerchantsEntity>();
	List<MerchantsEntity> moreList1 = new ArrayList<MerchantsEntity>();
	private View view;
	private MerchantsDetailListAdapter adapter = null;
	private MerchantsDetailListAdapter adapter1 = null;
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
				load.setVisibility(View.GONE);
				adapter.notifyDataSetChanged();
				adapter1.notifyDataSetChanged();
				if (myList.size() == 0) {
					// norecord_text_to.setText("您没有相关的商品");
					// lv.setVisibility(View.GONE);
					// eva_nodata.setVisibility(View.VISIBLE);
				}
				onRefresh_number = true;
				// myAdapter.notifyDataSetChanged();
				break;
			case 1:
				Toast.makeText(mActivity, "没有更多数据!", Toast.LENGTH_SHORT)
						.show();

				break;
			case 2: // 网络有问题
				Toast.makeText(mActivity, "没有网络连接!",
						Toast.LENGTH_SHORT).show();
				break;
			case 3:
				
				break;
			}
		}
	};
	private LinearLayout load;

	private void onLoad() {
		lv.stopRefresh();
		lv.stopLoadMore();
		lv.setRefreshTime(Tools.getHourAndMin());
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if (view == null) {
			view = inflater.inflate(R.layout.merchantinfo, null);
		}
		adapter = new MerchantsDetailListAdapter(mActivity, myList);
		adapter1 = new MerchantsDetailListAdapter(mActivity, myList1);
		initView();
		getSystemData();
		
		new TitleMainUtil(mActivity, "商户查询", view).init();
		return view;

	}

	@Override
	public void onAttach(Activity activity) {
		mActivity = (MainActivity) activity;
		super.onAttach(activity);
	}

	private void getSystemData() {
		// TODO Auto-generated method stub
		// Toast.makeText(getApplicationContext(), "getData", 1000).show();
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
		load = (LinearLayout)view.findViewById(R.id.load);
		load.setVisibility(View.GONE);
		
		eva_nodata = (LinearLayout) view.findViewById(R.id.eva_nodata);
		lv = (XListView) view.findViewById(R.id.lv);
		lv.setPullLoadEnable(true);
		lv.setXListViewListener(this);
		lv.setDivider(null);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i=new Intent(mActivity,MerchantDetailActivity.class);
				if(isSystem){
					i.putExtra("id", myList.get(position-1).getId());
				}else{
					i.putExtra("id", myList1.get(position-1).getId());
				}
				
				Log.i("",String.valueOf(view.getTag()));
				mActivity.startActivity(i);

			}
		});
		lv.setAdapter(adapter);
		ll_tt = (LinearLayout) view.findViewById(R.id.ll_tt);
		ll_left = (LinearLayout) view.findViewById(R.id.tt_left);
		ll_right = (LinearLayout) view.findViewById(R.id.tt_right);

		ll_left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isSystem == false) {
					lv.setAdapter(adapter);
					isSystem = true;
					ll_tt.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.navshnghu1));
					getSystemData();
				}

			}
		});
		ll_right.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isSystem = true) {
					
					ll_tt.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.navshanghu2));
					
					
					lv.setAdapter(adapter1);
					isSystem = false;
					getGroupData();
				}
			}
		});

	}

	private void getData(final String type) {

		RequestParams params = new RequestParams();
		if (type.equals("1")) {
			params.put("offset", page);
			params.put("typeId", type);
			Log.i("offset", page + "");
		} else {
			params.put("offset", page1);
			params.put("typeId", type);
			Log.i("offset1", page1 + "");
		}
		// params.setUseJsonStreamer(true);

		AsyncHttpClient c = new AsyncHttpClient();

		c.post(Config.MERCHANTS, params, new AsyncHttpResponseHandler() {

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
						
						if (type.equals("1")) {
							moreList.clear();
							moreList = gson.fromJson(
									jsonobject.getString("result"),
									new TypeToken<List<MerchantsEntity>>() {
									}.getType());
							
							
							if(moreList.size()==0){
								handler.sendEmptyMessage(1);
								
							}
							// Log.i(myList.get(0).getName(),"111");
							myList.addAll(moreList);
							handler.sendEmptyMessage(0);
							
						} else {
							moreList1.clear();
							moreList1 = gson.fromJson(
									jsonobject.getString("result"),
									new TypeToken<List<MerchantsEntity>>() {
									}.getType());
							if(moreList1.size()==0){
								handler.sendEmptyMessage(1);
							}
							myList1.addAll(moreList1);
							handler.sendEmptyMessage(0);
						}
					} else {
						Toast.makeText(mActivity,
								"没有更多数据!",
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
		
		
		// Toast.makeText(getApplicationContext(), "onRefresh", 1000).show();
		if (isSystem) {
			page = 1;
			myList.clear();
			getSystemData();
		} else {
			page1=1;
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

	@Override
	public void onClick(View view) {
		if (view.getId() == R.id.titlebar_img_left) {
			MainActivity.mSlidingMenu.toggle();
		}

	}

}
