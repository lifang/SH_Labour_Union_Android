package com.example.cityguild.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.cityguild.AlertDialog;
import com.example.cityguild.R;
import com.example.cityguild.activity.jigouDetailActivity;

import com.example.cityguild.entity.jigouEntity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class jigouAdapter extends BaseAdapter {
	List<jigouEntity> myList = new ArrayList<jigouEntity>();
	Context context;
	private ViewHolder holder;
	AlertDialog ad;
	String telnum;
	public jigouAdapter(Context context,List<jigouEntity> myList) {
		this.context=context;
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
		LayoutInflater inflater = LayoutInflater.from(context);
		if(view==null){
			holder=new ViewHolder();
			view=inflater.inflate(R.layout.jigou_listitem, null);
			holder.name=(TextView)view.findViewById(R.id.jigouname);
			holder.ll_call=(LinearLayout)view.findViewById(R.id.ll_call);
			holder.ll_call.setTag(myList.get(position).getTel());
			holder.adress=(TextView)view.findViewById(R.id.jigouadress);
			holder.telphone=(TextView)view.findViewById(R.id.jigouphone);
			holder.ll_jigou=(LinearLayout)view.findViewById(R.id.ll_jigou);
			holder.ll_jigou.setTag(myList.get(position).getId());
			view.setTag(holder);
		}else{
			holder = (ViewHolder) view.getTag();	
		}
		holder.ll_call.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				telnum=v.getTag().toString();
				ad = new AlertDialog(context);
				ad.setTitle("提示");
				ad.setMessage(telnum);
				ad.setPositiveButton("取消", new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						ad.dismiss();
						
					}
				});
				ad.setNegativeButton("确定", new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						ad.dismiss();
					    Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+telnum));				     
					    context.startActivity(intent);
					}
				});
				
			}
		});
		holder.name.setText(myList.get(position).getName());
		holder.adress.setText(myList.get(position).getAddr());
		holder.telphone.setText(myList.get(position).getTel());
		/*holder.ll_jigou.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				//Intent i=new Intent(context,jigouDetailActivity.class);
				//Log.i("ccccccccccccc",view.getTag().toString());
				//i.putExtra("id", view.getTag().toString());
				//context.startActivity(i);
			}
		});*/
		return view;
	}
	public final class ViewHolder {
		public TextView adress, name,telphone;
		
		public LinearLayout ll_jigou,ll_call;

	}
}
