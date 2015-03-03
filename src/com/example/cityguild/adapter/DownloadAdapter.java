package com.example.cityguild.adapter;

import java.util.ArrayList;
import java.util.List;

import com.examlpe.ices.util.ImageCacheUtil;
import com.example.cityguild.R;
import com.example.cityguild.activity.jigouDetailActivity;
import com.example.cityguild.adapter.MerchantsListAdapter.ViewHolder;
import com.example.cityguild.entity.DownloadEntity;
import com.example.cityguild.entity.jigouEntity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.sax.StartElementListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DownloadAdapter extends BaseAdapter {
	List<DownloadEntity> myList = new ArrayList<DownloadEntity>();
	Context context;
	private ViewHolder holder;
	private RatingBar tb;

	public DownloadAdapter(Context context, List<DownloadEntity> myList) {
		this.context = context;
		this.myList = myList;

	}

	@Override
	public int getCount() {
		//
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

	@SuppressLint("NewApi") @Override
	public View getView(final int position, View view, ViewGroup arg2) {

		LayoutInflater inflater = LayoutInflater.from(context);
		if (view == null) {
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.downloadltem, null);
			holder.name = (TextView) view.findViewById(R.id.name);
			holder.size = (TextView) view.findViewById(R.id.size);
			holder.sp = (ImageView) view.findViewById(R.id.sp);
			holder.description = (TextView) view.findViewById(R.id.tv_detail);
			holder.ll_down = (LinearLayout) view.findViewById(R.id.ll_down);
			holder.ll_down.setTag(R.id.tag_type, holder.sp);
			holder.bt_xiazai=(Button)view.findViewById(R.id.bt_xiazai);
			holder.ll_detail = (LinearLayout) view.findViewById(R.id.ll_detail);
			holder.ll_down.setTag(holder.ll_detail);
			holder.img = (ImageView) view.findViewById(R.id.imageView1);

			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		if (myList.get(position).isIsshow()) {
			holder.ll_detail.setVisibility(View.VISIBLE);
		} else {
			holder.ll_detail.setVisibility(View.GONE);
		}
		holder.description.setText(myList.get(position).getDescription());
		holder.name.setText(myList.get(position).getName());
		holder.size.setText("ด๓ะก" + myList.get(position).getAppSize() + "MB");
		tb = (RatingBar)view.findViewById(R.id.ratingBar);
		tb.setRating(Float.parseFloat(myList.get(position).getScore()));
		ImageCacheUtil.IMAGE_CACHE.get(myList.get(position).getLogoFilePath(),
				holder.img);
		holder.bt_xiazai.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
                //intent.setAction("android.intent.action.VIEW");    
                //Uri content_url = Uri.parse(myList.get(position).getAndroidDownloadPath());   
                //intent.setData(content_url);           
                //intent.setClassName("com.android.browser","com.android.browser.BrowserActivity"); 
				 final Uri uri = Uri.parse(myList.get(position).getAndroidDownloadPath());
				 Intent intent= new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(intent);
				
			}
		});
		holder.ll_down.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				LinearLayout nll = ((LinearLayout) view.getTag());
				if (myList.get(position).isIsshow()) {
					myList.get(position).setIsshow(false);
					((ImageView) view.getTag(R.id.tag_type))
							.setImageResource(R.drawable.zhankai);
					nll.setVisibility(view.GONE);
				} else {
					myList.get(position).setIsshow(true);
					((ImageView) view.getTag(R.id.tag_type))
							.setImageResource(R.drawable.shouqi);
					nll.setVisibility(view.VISIBLE);
				}
			}
		});
		return view;
	}

	public final class ViewHolder {
		public TextView name, size, description;
		public ImageView img, sp;
		public Button bt_xiazai;
		public LinearLayout ll_down, ll_detail;

	}
}
