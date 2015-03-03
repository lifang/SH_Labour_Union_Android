package com.example.cityguild.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.examlpe.ices.util.StringUtil;
import com.examlpe.ices.util.TitleMenuUtil;
import com.example.cityguild.BaseActivity;
import com.example.cityguild.R;
import com.example.cityguild.adapter.PostOtherAdapter;
import com.example.cityguild.adapter.PostOtherAdapter.ViewHolder;
import com.example.cityguild.entity.PostEntity;

public class PostRecordActivity extends BaseActivity {
	private ListView list;
	private String[] mlist = {};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fristpostarea);
		new TitleMenuUtil(this, "ËÑË÷¼ÇÂ¼").init();
		initData();
		initView();
		
	}

	private void initData() {
		SharedPreferences mySharedPreferences =getSharedPreferences("test11",
				Activity.MODE_PRIVATE);
		String record = mySharedPreferences.getString("record7", "");
		if(!record.equals("")){
		mlist= record.split("@");
		
		}
	}

	private void initView() {
		list = (ListView)findViewById(R.id.list);
		list.setAdapter(new myAdapter(this, mlist));
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				position=mlist.length-position-1;
				Intent i=new Intent(PostRecordActivity.this, PostSearchActivity.class);
				//Log.i("zz", mlist[position].toString());
			
				String str[]=mlist[position].split("\\+");
				
				
				i.putExtra("type", str[0].replace("+", ""));
				
				
				i.putExtra("shouxuan", str[1].replace("+", ""));
				//Log.i("str[1]", str[1]);
				
			
				i.putExtra("cixuan", str[2].replace("+", ""));
				//Log.i("str[2]", str[2]);
				
				if(str.length==4){
				i.putExtra("text", str[3].replace("+", ""));
				//Log.i("str[3]", str[3]);
				}
				Log.i("typett", str[0]);
				startActivity(i);
				
			}
		});
		
	}
	class myAdapter extends BaseAdapter {

		private Context c;

		private String[] mlist = {};
		private ViewHolder holder = null;

		public myAdapter(Context c,  String[] mlist) {
			this.c = c;
			this.mlist=mlist;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mlist.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mlist[position];
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View view, ViewGroup arg2) {
			position=mlist.length-position-1;
			LayoutInflater inflater = LayoutInflater.from(c);
			if (view == null) {

				view = inflater.inflate(R.layout.postohteritem, null);
				holder = new ViewHolder();
				holder.name = (TextView) view.findViewById(R.id.other);

				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}

			holder.name.setText(StringUtil.replace(mlist[position]));
			return view;
		}

		public final class ViewHolder {
			public TextView name;

		}
	}

}
