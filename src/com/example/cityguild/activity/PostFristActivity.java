package com.example.cityguild.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.examlpe.ices.util.TitleMenuUtil;
import com.example.cityguild.BaseActivity;
import com.example.cityguild.Config;
import com.example.cityguild.MyApplication;
import com.example.cityguild.R;
import com.example.cityguild.adapter.PostFristAdapter;
import com.example.cityguild.adapter.PostTypeAdapter;
import com.example.cityguild.entity.PostEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class PostFristActivity extends BaseActivity {
	private ListView lv;
	List<PostEntity> myList = new ArrayList<PostEntity>();
	List<PostEntity> moreList = new ArrayList<PostEntity>();
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				adapter.notifyDataSetChanged();

				break;
			case 1:
				Toast.makeText(PostFristActivity.this, (String) msg.obj,
						Toast.LENGTH_SHORT).show();
				break;
			case 2: // 网络有问题
				Toast.makeText(PostFristActivity.this, "没有网络连接!",
						Toast.LENGTH_SHORT).show();
				break;
			case 3:

				break;
			case 4:

				break;
			}
		}
	};
	private PostFristAdapter adapter;
	private String weiquan;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fristpostarea);

		weiquan = getIntent().getStringExtra("weiquan");
		if (weiquan != null) {
			new TitleMenuUtil(this, "问题类别").init();
		} else {
			new TitleMenuUtil(this, "首选工作区域").init();
		}

		initView();
		getData();
	}

	private void initView() {
		lv = (ListView) findViewById(R.id.list);
		adapter = new PostFristAdapter(this, this, myList);
		lv.setAdapter(adapter);

	}

	private void getData() {
		RequestParams params = new RequestParams();
		params.put("token", MyApplication.getToken());
		// params.setUseJsonStreamer(true);
		String url = "";
		if (weiquan != null) {
			//url = Config.POSTAREA;
		} else {
			url = Config.POSTAREA;
		}
		AsyncHttpClient c = new AsyncHttpClient();

		c.post(url, new AsyncHttpResponseHandler() {

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
								new TypeToken<List<PostEntity>>() {
								}.getType());
						// Log.i(myList.get(0).getName(),"111");
						myList.addAll(moreList);
						handler.sendEmptyMessage(0);
					} else {
						Toast.makeText(PostFristActivity.this,
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
