package com.example.cityguild.adapter;

import java.util.ArrayList;
import java.util.List;

import com.baidu.location.f;
import com.baidu.platform.comapi.map.m;
import com.examlpe.ices.util.ImageCacheUtil;
import com.example.cityguild.R;
import com.example.cityguild.activity.PostType;
import com.example.cityguild.adapter.MerchantsListAdapter.ViewHolder;
import com.example.cityguild.entity.DoctorEntity;
import com.example.cityguild.entity.PostEntity;
import com.example.cityguild.entity.yiyuanEntity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DoctorAdapter extends BaseAdapter {

	private Context c;
	private String yiyuan;
	private String keshi;
	private List<DoctorEntity> myList = new ArrayList<DoctorEntity>();
	private ViewHolder holder = null;
	private SharedPreferences mySharedPreferences;
	private SharedPreferences.Editor editor;
	private String sclists[];

	public DoctorAdapter(Context c, List<DoctorEntity> myList, String yiyaun,
			String keshi) {
		this.c = c;
		this.myList = myList;
		this.yiyuan = yiyaun;
		this.keshi = keshi;
		mySharedPreferences = c.getSharedPreferences("test",
				Activity.MODE_PRIVATE);
		editor = mySharedPreferences.edit();
		sclists = mySharedPreferences.getString("sc", "").split("@");
		editor.commit();
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

			view = inflater.inflate(R.layout.yishengitem, null);
			holder = new ViewHolder();
			holder.docname = (TextView) view.findViewById(R.id.name);
			holder.doclevel = (TextView) view.findViewById(R.id.level);
			holder.yiyuan = (TextView) view.findViewById(R.id.yiyuan);
			holder.keshi = (TextView) view.findViewById(R.id.keshi);
			holder.im = (ImageView) view.findViewById(R.id.im);
			holder.tv = (TextView) view.findViewById(R.id.tv);
			holder.ll_sc = (LinearLayout) view.findViewById(R.id.ll_sc);
			holder.ll_sc.setTag(myList.get(position).getDocid());
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		sclists = mySharedPreferences.getString("sc", "").split("@");
		String newstr="";
		for(int i=0;i<sclists.length;i++){
			if(!sclists[i].equals(myList.get(position).getDocid())){
				newstr=sclists[i]+"@"+newstr;
				Log.i("----",newstr);
			}
		}
		holder.ll_sc.setOnClickListener(new OnClickListener() {

			@SuppressLint("ResourceAsColor")
			@Override
			public void onClick(View v) {
				if (v.getBackground().getConstantState() ==c.getResources().getDrawable(
						R.drawable.gh_bt1).getConstantState()) {
					v.setBackgroundResource(R.drawable.gh_bt2);
					holder.im.setVisibility(View.GONE);
					holder.tv.setText("取消收藏");
					holder.tv.setTextColor(R.color.white);
							
					String recordlast = mySharedPreferences.getString("sc", "");
					String sc=recordlast+"@"+v.getTag().toString();
					editor.putString("sc", sc);
					editor.commit();
					
				}else{
					v.setBackgroundResource(R.drawable.gh_bt1);
					holder.im.setVisibility(View.VISIBLE);
					holder.tv.setText("收藏");
					holder.tv.setTextColor(R.color.sc);
					sclists = mySharedPreferences.getString("sc", "").split("@");
					String newstr="";
					for(int i=0;i<sclists.length;i++){
						if(!sclists[i].equals(v.getTag())){
							newstr=sclists[i]+"@"+newstr;
							Log.i("----",newstr);
						}
					}
					editor.putString("sc", newstr);
					editor.commit();
				}
			}
		});
		ImageCacheUtil.IMAGE_CACHE.get(myList.get(position).getDocimageurl(),
				holder.im);
		holder.docname.setText(myList.get(position).getDocname());
		holder.doclevel.setText(myList.get(position).getDoclevel());
		holder.yiyuan.setText(yiyuan);
		holder.keshi.setText(keshi);
		
		return view;
	}

	public final class ViewHolder {
		public TextView doclevel, docname, yiyuan, keshi, tv;
		public LinearLayout ll_sc;
		public ImageView im;
	}
}
