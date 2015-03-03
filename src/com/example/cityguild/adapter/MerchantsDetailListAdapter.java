package com.example.cityguild.adapter;

import java.util.List;

import com.examlpe.ices.util.ImageCacheUtil;
import com.example.cityguild.AlertDialog;
import com.example.cityguild.Config;
import com.example.cityguild.R;
import com.example.cityguild.activity.MerchantDetailActivity;
import com.example.cityguild.entity.MerchantsEntity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

public class MerchantsDetailListAdapter extends BaseAdapter {
	private Context context;
	private List<MerchantsEntity> list;
	private LayoutInflater inflater;
	private ViewHolder holder = null;
	
	public MerchantsDetailListAdapter(Context context, List<MerchantsEntity> list) {
		this.list = list;
		this.context = context;
	}

	@Override
	public int getCount() {

		return list.size();
	}

	@Override
	public Object getItem(int position) {

		return list.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		inflater = LayoutInflater.from(context);
		if (convertView == null) {
			
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.merchants_item, null);
			holder.rl_call=(RelativeLayout)convertView.findViewById(R.id.rephone);
			holder.rl_call.setTag(list.get(position).getTel());
			holder.ll_main = (LinearLayout) convertView
					.findViewById(R.id.ll_main);
			holder.ll_main.setTag(list.get(position).getId());
			holder.ll_more = (LinearLayout) convertView
					.findViewById(R.id.ll_more);
			holder.ll_more.setId(position);
			holder.name = (TextView) convertView
					.findViewById(R.id.merchantname);
			holder.adress = (TextView) convertView
					.findViewById(R.id.merchantadress);
			holder.phone = (TextView) convertView
					.findViewById(R.id.merchantphone);

			holder.img_more=(ImageView)convertView.findViewById(R.id.merchant_more);
			
			holder.img_merchants = (ImageView) convertView
					.findViewById(R.id.merchant_img);
			holder.img_call = (ImageView) convertView
					.findViewById(R.id.merchant_call);
			holder.Details=(TextView)convertView.findViewById(R.id.details);
			
			holder.rl_more=(RelativeLayout)convertView.findViewById(R.id.rl_more);
			holder.rl_more.setTag(holder.ll_more);
			holder.rl_more.setTag(R.id.tag_type,holder.img_more);
			convertView.setTag(holder);
			
			holder.Introduction=(TextView)convertView.findViewById(R.id.Introduction);
		} else {
			holder = (ViewHolder) convertView.getTag();
			
		}

		// ImageCacheUtil.IMAGE_CACHE.get( Config.IMAGE_PATH+
		// list.get(position).getPictureSmallFilePath(),
		// holder.img_merchants );
		
		if(list.get(position).getIsshow()){
			holder.ll_more.setVisibility(View.VISIBLE);
			holder.img_more.setImageResource(R.drawable.sp1);
		}else{
			holder.ll_more.setVisibility(View.GONE);
			holder.img_more.setImageResource(R.drawable.spread1);
		}
		holder.rl_call.setOnClickListener(new OnClickListener() {
			
			private AlertDialog ad;
			private String telnum;

			@Override
			public void onClick(View v) {
				telnum = v.getTag().toString();
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
					   
					    Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+telnum));				     
					    context.startActivity(intent);
					}
				});
				
			}
		});
		/*holder.ll_main.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				Intent i=new Intent(context,MerchantDetailActivity.class);
				i.putExtra("id", String.valueOf(view.getTag()));
				Log.i("",String.valueOf(view.getTag()));
				context.startActivity(i);
			}
		});*/
		holder.name.setText(list.get(position).getName());
		holder.adress.setText(list.get(position).getAddr());
		holder.phone.setText(list.get(position).getTel());
		holder.Introduction.setText(list.get(position).getAbout());
		ImageCacheUtil.IMAGE_CACHE.get(
				list.get(position).getLogo(),
				holder.img_merchants);
		
		holder.rl_more.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				LinearLayout nll=((LinearLayout) view.getTag());
				
//				if (nll.getVisibility() == view.VISIBLE) {
//					nll.setVisibility(view.GONE);					
//					Log.i(String.valueOf(nll.getId()), "GONE");
//				}else if (nll.getVisibility() == view.GONE) {
//					(nll).setVisibility(view.VISIBLE);
//					Log.i(String.valueOf(nll.getId()), "VISIBLE");
//				}
				if(list.get(position).getIsshow()){
					list.get(position).setIsshow(false);
					((ImageView) view.getTag(R.id.tag_type)).setImageResource(R.drawable.spread1);
					nll.setVisibility(view.GONE);	
				}else{
					list.get(position).setIsshow(true);
					((ImageView) view.getTag(R.id.tag_type)).setImageResource(R.drawable.sp1);
					nll.setVisibility(view.VISIBLE);
				}
			}
		});
		
		 holder.Details.setText(list.get(position).getAbout_detail());
		return convertView;
	}

	public final class ViewHolder {
		public TextView adress, name, phone, Introduction, Details;
		public ImageView img_merchants, img_more, img_call;
		public RelativeLayout rl_more,rl_call;
		public LinearLayout ll_more,ll_main;

	}
}
