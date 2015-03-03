package com.example.cityguild.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.cityguild.R;
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

public class PostOtherAdapter extends BaseAdapter {

	private Context c;

	List<PostEntity> myList = new ArrayList<PostEntity>();
	private ViewHolder holder = null;

	public PostOtherAdapter(Context c, List<PostEntity> myList) {
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

			view = inflater.inflate(R.layout.postohteritem, null);
			holder = new ViewHolder();
			holder.name = (TextView) view.findViewById(R.id.other);

			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		holder.name.setText(myList.get(position).getJob_name());
		

		return view;
	}

	public final class ViewHolder {
		public TextView name;

	}
}
