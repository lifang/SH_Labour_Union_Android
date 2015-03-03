package com.example.cityguild.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.cityguild.R;
import com.example.cityguild.activity.NewestDetail;
import com.example.cityguild.entity.NewestEntity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class listadapter extends BaseAdapter {
	private Context c;
	private TextView tv;
	private TextView time;
	List<NewestEntity> myList = new ArrayList<NewestEntity>();
	private LinearLayout ll_newest;
    public listadapter(Context c,List<NewestEntity> myList) {
    	this.myList=myList;
		this.c=c;
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
		
		view=inflater.inflate(R.layout.zuixinlvitem, null);
		ll_newest = (LinearLayout)view.findViewById(R.id.ll_newest);
		ll_newest.setTag(myList.get(position).getId());
		ll_newest.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i=new Intent(c,NewestDetail.class);
				i.putExtra("id", v.getTag().toString());
				c.startActivity(i);
				
			}
		});
		tv = (TextView)view.findViewById(R.id.list_zuixin);
		time = (TextView)view.findViewById(R.id.list_time);
		tv.setText(myList.get(position).getTitle());
		time.setText(myList.get(position).getTime());
		Log.i("zz","zz");
		return view;
	}

}
