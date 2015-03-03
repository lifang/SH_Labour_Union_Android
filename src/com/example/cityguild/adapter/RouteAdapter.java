package com.example.cityguild.adapter;

import java.util.ArrayList;
import java.util.List;

import com.examlpe.ices.util.ImageCacheUtil;
import com.example.cityguild.R;
import com.example.cityguild.activity.jigouDetailActivity;
import com.example.cityguild.adapter.MerchantsListAdapter.ViewHolder;
import com.example.cityguild.entity.DownloadEntity;
import com.example.cityguild.entity.RouteEntity;
import com.example.cityguild.entity.jigouEntity;

import android.content.Context;
import android.content.Intent;
import android.sax.StartElementListener;
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

public class RouteAdapter extends BaseAdapter {
	List<RouteEntity> myList = new ArrayList<RouteEntity>();
	Context context;
	private ViewHolder holder;

	public RouteAdapter(Context context, List<RouteEntity> myList) {
		this.context = context;
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
	public View getView(final int position, View view, ViewGroup arg2) {

		LayoutInflater inflater = LayoutInflater.from(context);
		if (view == null) {
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.routeitem, null);
			holder.id=(TextView) view.findViewById(R.id.id);
			holder.title=(TextView) view.findViewById(R.id.title);
			holder.time=(TextView) view.findViewById(R.id.time);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
			
		}
		holder.id.setText(myList.get(position).getId());
		holder.title.setText(myList.get(position).getTitle());
		holder.time.setText(myList.get(position).getTime());
		//Log.i("title",myList.get(position).getTitle());
		return view;
	}

	public final class ViewHolder {
		public TextView id, title, time;

	}
}
