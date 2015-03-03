package com.example.cityguild.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import com.example.cityguild.R;
import com.example.cityguild.activity.MerchantsMap;
import com.example.cityguild.adapter.MerchantsDetailListAdapter.ViewHolder;
import com.example.cityguild.dao.getTestData;
import com.example.cityguild.entity.MerchantsEntity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MerchantsListAdapter extends BaseAdapter {
	Context context;
	List<MerchantsEntity> myList = new ArrayList<MerchantsEntity>();
	private ViewHolder holder = null;
	private TextView name;
	private TextView adress;
	private RelativeLayout rl_more;
	
	public MerchantsListAdapter(Context context,List<MerchantsEntity> list) {
		this.myList=list;
		this.context = context;
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
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(context);
		if (convertView == null) {
			
			holder=new ViewHolder();
			convertView=inflater.inflate(R.layout.merchantlistitem, null);
			holder.name = (TextView)convertView.findViewById(R.id.name);
			holder.adress = (TextView)convertView.findViewById(R.id.adress);
			//holder.rl_more = (RelativeLayout)convertView.findViewById(R.id.rl_more);
			convertView.setTag(holder);
			
		}else {
			holder = (ViewHolder) convertView.getTag();		
		}
		holder.name.setText(myList.get(position).getName());
		holder.adress.setText(myList.get(position).getAddr());
		/*holder.rl_more.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				context.startActivity(new Intent(context,MerchantsMap.class));
				
			}
		});*/
		
		return convertView;
	}

	public final class ViewHolder {
		public TextView adress, name;
		
		public RelativeLayout rl_more;

	}
}
