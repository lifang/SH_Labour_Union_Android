package com.example.cityguild.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.examlpe.ices.util.TitleMenuUtil;
import com.examlpe.ices.util.Tools;
import com.examlpe.ices.util.XListView;
import com.examlpe.ices.util.XListView.IXListViewListener;
import com.example.cityguild.BaseActivity;
import com.example.cityguild.Config;
import com.example.cityguild.MyApplication;
import com.example.cityguild.R;
import com.example.cityguild.adapter.PostFristAdapter;
import com.example.cityguild.adapter.PostTuiAdapter;
import com.example.cityguild.adapter.PostOtherAdapter.ViewHolder;
import com.example.cityguild.entity.PostEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class PostSearchActivity extends BaseActivity implements
		IXListViewListener {
	private XListView list;
	private String type = "��ҵ���";
	private String shouxuan = "��ѡ��������";
	private String cixuan = "��ѡ��������";
	private int page = 1;
	private boolean onRefresh_number = true;
	List<PostEntity> myList = new ArrayList<PostEntity>();
	List<PostEntity> moreList = new ArrayList<PostEntity>();
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				onLoad();
				adapter.notifyDataSetChanged();
				onRefresh_number = true;
				break;
			case 1:
				Toast.makeText(PostSearchActivity.this, (String) msg.obj,
						Toast.LENGTH_SHORT).show();
				break;
			case 2: // ����������
				Toast.makeText(PostSearchActivity.this, "û����������!",
						Toast.LENGTH_SHORT).show();
				break;
			case 4:
				Toast.makeText(PostSearchActivity.this, " δ��ѯ������",
						Toast.LENGTH_SHORT).show();
				break;
			case 5:
				Toast.makeText(PostSearchActivity.this, " û�и�������",
						Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};
	private myAdapter adapter;
	private String text;
	private TextView size;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.postsearchre);
		new TitleMenuUtil(this, "�������").init();
		if (getIntent().getStringExtra("type") != null)
			type = getIntent().getStringExtra("type");
		if (getIntent().getStringExtra("shouxuan") != null)
			shouxuan = getIntent().getStringExtra("shouxuan");
		if (getIntent().getStringExtra("cixuan") != null)
			cixuan = getIntent().getStringExtra("cixuan");
		if (getIntent().getStringExtra("text") != null)
			text = getIntent().getStringExtra("text");
		Log.i("text",type);
		Log.i("shouxuan",shouxuan);
		initView();
		getData();
	}

	private void initView() {
		size = (TextView) findViewById(R.id.postsize);
		list = (XListView) findViewById(R.id.list);
		list.setPullLoadEnable(true);
		list.setXListViewListener(this);
		list.setDivider(null);
		adapter = new myAdapter(this, myList);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				Intent i = new Intent(PostSearchActivity.this, PostDetail.class);
				i.putExtra("id", myList.get(position-1).getId());
				startActivity(i);
			}
		});

	}
	private void onLoad() {
		list.stopRefresh();
		list.stopLoadMore();
		list.setRefreshTime(Tools.getHourAndMin());
	}
	class myAdapter extends BaseAdapter {

		private Context c;

		List<PostEntity> myList = new ArrayList<PostEntity>();
		private ViewHolder holder = null;

		public myAdapter(Context c, List<PostEntity> myList) {
			this.c = c;
			this.myList = myList;
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

				view = inflater.inflate(R.layout.posttuijianitem, null);
				holder = new ViewHolder();
				holder.name = (TextView) view.findViewById(R.id.postname);
				holder.unit = (TextView) view.findViewById(R.id.mername);

				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}

			holder.name.setText(myList.get(position).getJob_name());
			holder.unit.setText(myList.get(position).getUnit_name());

			return view;
		}

		public final class ViewHolder {
			public TextView name, unit;

		}
	}

	private void getData() {

		RequestParams params = new RequestParams();

		if (!type.equals("��ҵ���"))
			params.put("job_type", type);
		if (!shouxuan.equals("��ѡ��������"))
			params.put("Job_locate1", shouxuan);
		if (!cixuan.equals("��ѡ��������"))
			params.put("Job_locate2", cixuan);
		params.put("offset", page);
		params.put("q", text);
		// params.setUseJsonStreamer(true);
		if(text!=null)
		Log.i("qqqq", text);
		AsyncHttpClient c = new AsyncHttpClient();

		c.post(Config.POSTSEARCH, params, new AsyncHttpResponseHandler() {

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
								new TypeToken<List<PostEntity>>() {
								}.getType());
						// Log.i(myList.get(0).getName(),"111");
						
						myList.addAll(moreList);
						if(myList.size()==0){
   							handler.sendEmptyMessage(4);
   						}else{
   							if(moreList.size()==0)
   	   	   						handler.sendEmptyMessage(5);
   						}
						size.setText("��Ϊ���ҵ�" + String.valueOf(jsonobject.getString("total"))
								+ "ְλ");
						handler.sendEmptyMessage(0);
						
					} else {
						Toast.makeText(PostSearchActivity.this,
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
		page = 1;
		myList.clear();
		getData();

	}

	@Override
	public void onLoadMore() {
		if (onRefresh_number) {

			if (Tools.isConnect(PostSearchActivity.this)) {
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
