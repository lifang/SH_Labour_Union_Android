package com.example.cityguild.fragement;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.baidu.platform.comapi.map.m;
import com.examlpe.ices.util.TitleMainUtil;
import com.examlpe.ices.util.Tools;
import com.examlpe.ices.util.XListView;
import com.examlpe.ices.util.XListView.IXListViewListener;
import com.example.cityguild.Config;
import com.example.cityguild.R;
import com.example.cityguild.activity.AboutDetailActivity;
import com.example.cityguild.activity.MainActivity;
import com.example.cityguild.adapter.PostOtherAdapter.ViewHolder;
import com.example.cityguild.entity.AboutEntity;
import com.example.cityguild.entity.MerchantsEntity;
import com.example.cityguild.entity.PostEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.content.Context;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class AboutFragment extends Fragment implements IXListViewListener{
	private String rad_id="1";
	private int tap = 1;
	private View view;
	private LinearLayout ll_nav;
	private TextView tv_nav1;
	private TextView tv_nav2;
	private TextView tv_nav3;
	private LinearLayout nav1;
	private LinearLayout nav2;
	private LinearLayout nav3;
	private MainActivity mActivity;
	List<AboutEntity> myList = new ArrayList<AboutEntity>();
	List<AboutEntity> moreList = new ArrayList<AboutEntity>();
	List<AboutEntity> myList1 = new ArrayList<AboutEntity>();
	List<AboutEntity> moreList1 = new ArrayList<AboutEntity>();
	private int page = 1;
	private int page1 = 1;
	private int page2 = 1;
	private int page3= 1;
	private boolean onRefresh_number = true;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				onLoad();
				
				adapter.notifyDataSetChanged();
				adapter1.notifyDataSetChanged();
				if (myList.size() == 0) {
					// norecord_text_to.setText("您没有相关的商品");
					// lv.setVisibility(View.GONE);
					// eva_nodata.setVisibility(View.VISIBLE);
					Toast.makeText(mActivity, "列表为空!", Toast.LENGTH_SHORT)
					.show();
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
				Toast.makeText(mActivity, " refresh too much",
						Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};
	private XListView list;
	private XListView list1;
	private MyAdapter adapter;
	private MyAdapter adapter1;
	private Button fgsc;
	private Button hzsc;
	private EditText ed;
	private EditText ed1;
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
			view = inflater.inflate(R.layout.about, null);
			new TitleMainUtil(mActivity, "相关查询", view).init();
			initView();
			initData();

		}
		return view;
	}
	private void initData() {
		
		getData("1");
		getData("2");
		
	}

	@Override
	public void onAttach(Activity activity) {
		mActivity = (MainActivity) activity;
		super.onAttach(activity);
	}
	private void initView() {
		ed = (EditText)view.findViewById(R.id.ed);
		ed1 = (EditText)view.findViewById(R.id.ed1);
		list = (XListView)view.findViewById(R.id.list);
		list1 = (XListView)view.findViewById(R.id.listView2);
		adapter = new MyAdapter(mActivity, myList,"1");
		adapter1 = new MyAdapter(mActivity, myList1,"2");
		list.setAdapter(adapter);
		list1.setAdapter(adapter1);
		list.setPullLoadEnable(true);
		list.setXListViewListener(this);
		list.setDivider(null);
		list1.setPullLoadEnable(true);
		list1.setXListViewListener(this);
		list1.setDivider(null);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				//Log.i("CCCC",position+"");
				Intent i=new Intent(mActivity,AboutDetailActivity.class);
				i.putExtra("id", myList.get(position-1).getId());
				i.putExtra("type", "1");
				startActivity(i);
				
			}
		});
		
		list1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				Intent i=new Intent(mActivity,AboutDetailActivity.class);
				i.putExtra("id", myList1.get(position-1).getId());
				i.putExtra("type", "2");
				startActivity(i);
				
			}
		});
		
		fgsc = (Button)view.findViewById(R.id.fgsc);
		fgsc.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				tap=4;
			 String str=ed.getText().toString().trim();
		
				 getData("3");
				
			}
		});
		hzsc = (Button)view.findViewById(R.id.hzsc);
		 RadioGroup group=(RadioGroup)view.findViewById(R.id.rad);
		 group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				 int radioButtonId = arg0.getCheckedRadioButtonId();
				 RadioButton rb = (RadioButton)view.findViewById(radioButtonId);
				 if(rb.getText().equals("在职")){
					 rad_id="1";
				 }
				 if(rb.getText().equals("退休")){
					 rad_id="0";
				 }
			}
		});
		hzsc.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				tap=5;
				
				 String str=ed1.getText().toString().trim();
					 getData("4");
				 
				
			}
		});
		nav1 = (LinearLayout) view.findViewById(R.id.nav1);
		nav2 = (LinearLayout) view.findViewById(R.id.nav2);
		nav3 = (LinearLayout) view.findViewById(R.id.nav3);

		ll_nav = (LinearLayout) view.findViewById(R.id.ll_nav);
		tv_nav1 = (TextView) view.findViewById(R.id.tv1);
		tv_nav2 = (TextView) view.findViewById(R.id.tv2);
		tv_nav3 = (TextView) view.findViewById(R.id.tv3);

		tv_nav1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				tap = 1;
				InputMethodManager imm =  (InputMethodManager)mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);   
				if(imm != null) { 
					imm.hideSoftInputFromWindow(mActivity.getWindow().getDecorView().getWindowToken(),    
		                       0);   
		                     }  
				ll_nav.setBackgroundResource(R.drawable.nav_31);
				nav1.setVisibility(View.VISIBLE);
				nav2.setVisibility(View.GONE);
				nav3.setVisibility(View.GONE);
				tv_nav1.setTextColor(getResources().getColor(R.color.white));
				tv_nav2.setTextColor(getResources().getColor(R.color.jigoutv));
				tv_nav3.setTextColor(getResources().getColor(R.color.jigoutv));
			}
		});
		tv_nav2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				tap = 2;
				//page3=1;
				InputMethodManager imm =  (InputMethodManager)mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);   
				if(imm != null) { 
					imm.hideSoftInputFromWindow(mActivity.getWindow().getDecorView().getWindowToken(),    
		                       0);   
		                     }  
				ll_nav.setBackgroundResource(R.drawable.nav_32);
				nav2.setVisibility(View.VISIBLE);
				nav1.setVisibility(View.GONE);
				nav3.setVisibility(View.GONE);
				tv_nav2.setTextColor(getResources().getColor(R.color.white));
				tv_nav1.setTextColor(getResources().getColor(R.color.jigoutv));
				tv_nav3.setTextColor(getResources().getColor(R.color.jigoutv));
			}
		});
		tv_nav3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				tap = 3;
				InputMethodManager imm =  (InputMethodManager)mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);   
				if(imm != null) { 
					imm.hideSoftInputFromWindow(mActivity.getWindow().getDecorView().getWindowToken(),    
		                       0);   
		                     }  
				ll_nav.setBackgroundResource(R.drawable.nav_33);
				nav3.setVisibility(View.VISIBLE);
				nav1.setVisibility(View.GONE);
				nav2.setVisibility(View.GONE);
				tv_nav3.setTextColor(getResources().getColor(R.color.white));
				tv_nav1.setTextColor(getResources().getColor(R.color.jigoutv));
				tv_nav2.setTextColor(getResources().getColor(R.color.jigoutv));
			}
		});
	}
	
	private void getData(final String type) {
		String url="";
		RequestParams params = new RequestParams();
		if (type.equals("1")) {
			url=Config.ABOUTFG;
			params.put("offset", page);
			Log.i("page",page+"");
		} else if(type.equals("2")){
			url=Config.ABOUTHZ;
			params.put("offset", page1);
			Log.i("page1",page1+"");
		}else if(type.equals("3")){
			url=Config.ABOUTFG;
			params.put("title", ed.getText().toString().trim());
			params.put("offset", page2);
			Log.i("page2",page2+"");
		}else if(type.equals("4")){
			url=Config.ABOUTHZ1;
			params.put("name", ed1.getText().toString().trim());
			params.put("type", rad_id);
			//params.put("offset", page3);
			Log.i("page3",page3+"");
			Log.i("tttttt",ed1.getText().toString());
			
		}
		// params.setUseJsonStreamer(true);

		AsyncHttpClient c = new AsyncHttpClient();

		c.post(url,params,new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] responseBody) {
				// TODO Auto-generated method stub
				String userMsg = new String(responseBody).toString();

				Log.i("ljp", userMsg);
				Log.i("tap", tap+"");
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
						if (type.equals("1")) {
							moreList = gson.fromJson(
									jsonobject.getString("result"),
									new TypeToken<List<AboutEntity>>() {
									}.getType());
							if(moreList.size()==0){
								handler.sendEmptyMessage(1);
							}
							// Log.i(myList.get(0).getName(),"111");
							myList.addAll(moreList);
							handler.sendEmptyMessage(0);
						} else if(type.equals("2")){
							moreList1.clear();
							moreList1 = gson.fromJson(
									jsonobject.getString("result"),
									new TypeToken<List<AboutEntity>>() {
									}.getType());
							if(moreList1.size()==0){
								handler.sendEmptyMessage(1);
							}
							myList1.addAll(moreList1);
							handler.sendEmptyMessage(0);
						}else if(type.equals("3")){
							myList.clear();
							moreList = gson.fromJson(
									jsonobject.getString("result"),
									new TypeToken<List<AboutEntity>>() {
									}.getType());
							if(moreList.size()==0){
								handler.sendEmptyMessage(1);
							}
							// Log.i(myList.get(0).getName(),"111");
							myList.addAll(moreList);
							handler.sendEmptyMessage(0);
						}else if(type.equals("4")){
							myList1.clear();
							moreList1 = gson.fromJson(
									jsonobject.getString("result"),
									new TypeToken<List<AboutEntity>>() {
									}.getType());
							if(moreList1.size()==0){
								handler.sendEmptyMessage(1);
							}
							myList1.addAll(moreList1);
							handler.sendEmptyMessage(0);
							Log.i("mylist1",myList1.size()+"");
						}
					} else {
						myList1.clear();
						Toast.makeText(mActivity,
								"列表为空!",
								Toast.LENGTH_SHORT).show();
						handler.sendEmptyMessage(0);
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
	
	class MyAdapter extends BaseAdapter {

		private Context c;

		List<AboutEntity> myList = new ArrayList<AboutEntity>();
		private ViewHolder holder = null;
		private String type;

		public MyAdapter(Context c, List<AboutEntity> myList,String type) {
			this.c = c;
			this.myList=myList;
			this.type=type;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return myList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return myList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View view, ViewGroup arg2) {

			LayoutInflater inflater = LayoutInflater.from(c);
			if (view == null) {

				view = inflater.inflate(R.layout.postohteritem, null);
				holder = new ViewHolder();
				holder.name = (TextView) view.findViewById(R.id.other);

				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}
			
			if(type.equals("2")){
				holder.name.setText(myList.get(position).getName());
			}else{
				holder.name.setText(myList.get(position).getTitle());
			}

			return view;
		}

		
		public final class ViewHolder {
			public TextView name;

		}
	}
	private void onLoad() {
		list.stopRefresh();
		list.stopLoadMore();
		list.setRefreshTime(Tools.getHourAndMin());
		list1.stopRefresh();
		list1.stopLoadMore();
		list1.setRefreshTime(Tools.getHourAndMin());
	}
	@Override
	public void onRefresh() {
		if (tap==1) {
			page = 1;
			myList.clear();
			getData("1");
		} else if (tap==2) {
			page1=1;
			myList1.clear();
			getData("2");
		}else if (tap==4) {
			page2=1;
			myList1.clear();
			getData("3");
		}else if (tap==5) {
			page3=1;
			myList1.clear();
			getData("4");
		}
		
	}

	@Override
	public void onLoadMore() {
if (onRefresh_number) {
			
			if (Tools.isConnect(mActivity)) {
				onRefresh_number = false;
				if (tap==1) {
					page = page + 1;
					getData("1");
				} else if (tap==2){
					page1 = page1 + 1;
					getData("2");
				}
				else if (tap==4){
					page2 = page2 + 1;
					getData("3");
				}
				else if (tap==5){
					page3 = page3+ 1;
					getData("4");
				}
			} else {
				onRefresh_number = true;
				handler.sendEmptyMessage(2);
			}
		} else {
			handler.sendEmptyMessage(3);
		}
		
	}
}
