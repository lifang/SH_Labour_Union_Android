package com.example.cityguild.activity;

import java.security.acl.LastOwnerException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.examlpe.ices.util.TitleMenuUtil;
import com.example.cityguild.BaseActivity;
import com.example.cityguild.Config;
import com.example.cityguild.MyApplication;
import com.example.cityguild.R;
import com.example.cityguild.adapter.PostFristAdapter;
import com.example.cityguild.adapter.PostTypeAdapter;
import com.example.cityguild.adapter.PostFristAdapter.ViewHolder;
import com.example.cityguild.entity.CityEntity;
import com.example.cityguild.entity.PostEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class AllCityActivity extends BaseActivity {
	private ListView lv;
	List<CityEntity> myList = new ArrayList<CityEntity>();
	List<CityEntity> chidlist = new ArrayList<CityEntity>();
	List<CityEntity> moreList = new ArrayList<CityEntity>();
	private int lastclick = -1;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			
			case 0:
				LinearLayout ll_load = (LinearLayout)findViewById(R.id.load);
				ll_load.setVisibility(View.GONE);
				adapter.notifyDataSetChanged();

				break;
			case 1:
				Toast.makeText(AllCityActivity.this, (String) msg.obj,
						Toast.LENGTH_SHORT).show();
				break;
			case 2: // 网络有问题
				Toast.makeText(AllCityActivity.this, "没有网络连接!",
						Toast.LENGTH_SHORT).show();
				break;
			case 3:

				break;
			case 4:

				break;
			}
		}
	};
	private MyAdapter adapter;
	private ListView lv1;
	private String sheng = "";
	private String city;
	private String shengv;
	private LinearLayout ll_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.citylist);
		sheng = getIntent().getStringExtra("sheng");
		if(sheng!=null){
		if (sheng.equals(""))
			lastclick = 0;
		}
		city = getIntent().getStringExtra("city");
		initView();
		getData();
	}

	private void initView() {
		lv = (ListView) findViewById(R.id.list);
		lv1 = (ListView) findViewById(R.id.list1);
		adapter = new MyAdapter(this, "0");
		lv.setAdapter(adapter);
		lv1.setAdapter(new MyAdapter(this, "1"));
		ll_back = (LinearLayout) findViewById(R.id.back);
		ll_back.setOnClickListener(this);
		getData();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		if(v.getId()==R.id.back){
			this.finish();
		}
	}

	private void getData() {
		RequestParams params = new RequestParams();
		params.put("token", MyApplication.getToken());
		MyApplication.getInstance().getClient()
				.post(Config.FINDCITY, new AsyncHttpResponseHandler() {

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
								myList.clear();
								moreList = gson.fromJson(
										jsonobject.getString("result"),
										new TypeToken<List<CityEntity>>() {
										}.getType());

								// Log.i(myList.get(0).getName(),"111");
								myList.addAll(moreList);
								myList.get(0).setIslight(true);
								handler.sendEmptyMessage(0);
							} else {
								Toast.makeText(AllCityActivity.this,
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

	class MyAdapter extends BaseAdapter {

		private Context c;
		private String type;
		private TextView tv;
		private ImageView im;
		private int index;
		private ViewHolder holder = null;

		public MyAdapter(Context c, String type) {
			this.c = c;
			this.type = type;
		}

		public MyAdapter(Context c, String type, int index) {
			this.c = c;
			this.type = type;
			this.index = index;
			chidlist = myList.get(index).getChildrens();
		}

		@Override
		public int getCount() {
			if (type.equals("0")) {
				return myList.size();
			} else {
				return chidlist.size();
			}
		}

		@Override
		public Object getItem(int position) {

			if (type.equals("0")) {
				return myList.get(position);
			} else {
				return chidlist.get(position);
			}
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

				view = inflater.inflate(R.layout.citylistitem, null);
				holder = new ViewHolder();
				holder.name = (TextView) view.findViewById(R.id.name);
				holder.ll_city = (LinearLayout) view
						.findViewById(R.id.ll_citylist);
				holder.ll_city.setId(position);
				myList.get(position).setIslight(false);

				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}
			if (chidlist.size() == 0) {
				if (myList.get(position).getCity_name().equals(sheng))
					chidlist = myList.get(position).getChildrens();
			}
			holder.ll_city.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.citybg1));
			if (type.equals("0")) {

				if (position == lastclick) {

					// Log.i("last",lastclick+"");
					holder.ll_city.setBackgroundDrawable(getResources()
							.getDrawable(R.drawable.citybg2));
				}

				if (myList.get(position).getCity_name().equals(sheng)) {
					holder.ll_city.setBackgroundDrawable(getResources()
							.getDrawable(R.drawable.citybg2));
				}
				holder.name.setText(myList.get(position).getCity_name());
				// holder.ll_city.setBackgroundResource(R.drawable.citybg1);
				holder.ll_city.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						v.setBackgroundResource(R.drawable.citybg2);
						myList.get(v.getId()).setIslight(true);
						lastclick = v.getId();
						if (myList.get(lastclick).getChildrens().size() == 0) {
							Intent i = new Intent();
							Bundle b = new Bundle();
							b.putString("city", myList.get(lastclick)
									.getCity_name());
							b.putString("sheng", myList.get(lastclick)
									.getCity_name());
							b.putString("area_id", myList.get(lastclick)
									.getCity_area_id());
							Log.i("111111", myList.get(lastclick)
									.getCity_name());
							i.putExtras(b);
							AllCityActivity.this.setResult(
									AllCityActivity.this.RESULT_OK, i);
							AllCityActivity.this.finish();
						} else {
							lv1.setAdapter(new MyAdapter(c, "1", v.getId()));
						}

						// shengv=myList.get(v.getId()).getCity_name();
						adapter.notifyDataSetChanged();
						sheng = "111";

						// Log.i("size",myList.get(v.getId()).getChildrens().size()+"");
					}
				});
			}
			if (type.equals("1")) {

				if (chidlist.get(position).getArea_name().equals(city)) {
					holder.ll_city.setBackgroundDrawable(getResources()
							.getDrawable(R.drawable.citybg2));
				}
				if(index==0){
					shengv=sheng;
				}else{
					shengv = myList.get(index).getCity_name();
				}
				
				holder.name.setText(chidlist.get(position).getArea_name());
				holder.ll_city.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						v.setBackgroundResource(R.drawable.citybg2);
						Intent i = new Intent();
						Bundle b = new Bundle();
						b.putString("city", chidlist.get(v.getId())
								.getArea_name());
						b.putString("sheng", shengv);
						b.putString("area_id", chidlist.get(v.getId()).getArea_id());
						// Log.i("111111",String.valueOf(v.getTag(R.id.tag_type)));
						i.putExtras(b);
						AllCityActivity.this.setResult(
								AllCityActivity.this.RESULT_OK, i);
						AllCityActivity.this.finish();
					}
				});
			}

			return view;
		}

		public final class ViewHolder {
			public TextView name;
			public LinearLayout ll_city;

		}
	}

}
