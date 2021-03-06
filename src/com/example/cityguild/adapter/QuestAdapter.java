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

public class QuestAdapter extends BaseAdapter {

	private Context c;
	private static int flag = -1;

	private TextView tv;
	private ImageView im;
	private Activity a;
	private ViewHolder holder = null;
	List<PostEntity> myList = new ArrayList<PostEntity>();
	public QuestAdapter(Context c, Activity a,List<PostEntity> myList) {
		this.c = c;
		this.a = a;
		this.myList=myList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return myList.size();
	}

	@Override
	public Object getItem(int position) {
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
			
		view = inflater.inflate(R.layout.posttypeitem, null);
		holder=new ViewHolder();
		holder.name = (TextView)view.findViewById(R.id.posttype);
		holder.img = (ImageView)view.findViewById(R.id.xuanzheim);
		holder.img.setVisibility(View.GONE);
		holder.rl_type = (RelativeLayout)view.findViewById(R.id.rl_type);
		holder.rl_type.setId(position);
		holder.rl_type.setTag(R.id.tag_type, position);
		holder.rl_type.setTag(holder.img);
		
		view.setTag(holder);
		}else{
			holder=(ViewHolder) view.getTag();
		}
		holder.name.setText(myList.get(position).getName());
		
		Log.i("flag",String.valueOf(flag));
	if(position==flag){
		holder.img.setVisibility(View.VISIBLE);
	}else {
		holder.img.setVisibility(View.GONE);
	}
			
		
		holder.rl_type.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			    if(((View) v.getTag()).getVisibility()==View.GONE){
				((View) v.getTag()).setVisibility(View.VISIBLE);
				Intent i = new Intent();
				Bundle b = new Bundle();
				int index=(Integer) v.getTag(R.id.tag_type);
				b.putString("name", myList.get(index).getName());
				b.putString("code", myList.get(index).getCode());
				//Log.i("111111",String.valueOf(v.getTag(R.id.tag_type)));
				i.putExtras(b);
				a.setResult(a.RESULT_OK, i);
				a.finish();
		}else{
			((View) v.getTag()).setVisibility(View.GONE);
			flag=-11;
			Intent i = new Intent();
			Bundle b = new Bundle();
			b.putString("shouxuan", "首选工作区域");
			i.putExtras(b);
			a.setResult(a.RESULT_OK, i);
			a.finish();
			return;
		}
				flag=v.getId();
				
				
				
			}
		});
		return view;
	}

	public final class ViewHolder {
		public TextView name;
		public ImageView img;
		public RelativeLayout rl_type;

	}
}
