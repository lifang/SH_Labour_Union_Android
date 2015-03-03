package com.example.cityguild.adapter;

import java.util.ArrayList;
import java.util.List;

import com.baidu.location.f;
import com.example.cityguild.R;
import com.example.cityguild.activity.PostType;
import com.example.cityguild.adapter.MerchantsListAdapter.ViewHolder;
import com.example.cityguild.entity.PostEntity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PostTuiAdapter extends BaseAdapter {

	private Context c;
	private static int flag = -1;
	List<PostEntity> myList = new ArrayList<PostEntity>();
	private TextView tv;
	private ImageView im;
	private Activity a;
	private ViewHolder holder = null;

	public PostTuiAdapter(Context c,List<PostEntity> myList) {
		this.c = c;
		this.myList=myList;
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
		holder=new ViewHolder();
		holder.postname = (TextView)view.findViewById(R.id.postname);
		holder.mername = (TextView)view.findViewById(R.id.mername);
		
		view.setTag(holder);
		}else{
			holder=(ViewHolder) view.getTag();
		}
		holder.postname.setText(myList.get(position).getJob_name());
		holder.mername.setText(myList.get(position).getUnit_name());
		return view;
	}

	public final class ViewHolder {
		public TextView postname,mername;

	}
}
