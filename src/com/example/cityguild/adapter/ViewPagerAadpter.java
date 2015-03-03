package com.example.cityguild.adapter;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import com.examlpe.ices.util.ImageCacheUtil;
import com.example.cityguild.R;
import com.example.cityguild.activity.NewestDetail;
import com.example.cityguild.entity.NewestEntity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ViewPagerAadpter extends PagerAdapter {
	private List<String> mImageUrlList;
	private List<View> list = new ArrayList<View>();
	private LayoutInflater inflater;
	private ImageView image;
	private View view;
	private Drawable drawable;
	Context context;
	private LinearLayout llPoints;
	private ViewPager mainvp;
	private int previousSelectPosition = 0;
	List<NewestEntity> myList = new ArrayList<NewestEntity>();
	public ViewPagerAadpter(List<String> imageViewList, Context context,List<NewestEntity> myList) {
		super();
		this.mImageUrlList = imageViewList;
		this.context=context;
		this.myList=myList;
		inflater = LayoutInflater.from(context);
		for (int i = 0; i < mImageUrlList.size(); i++) {
			View item = inflater.inflate(R.layout.item, null);
			
			list.add(item);
		}

	}

	/**
	 * 该方法将返回所包含的 Item总个数。为了实现一种循环滚动的效果，返回了基本整型的最大值，这样就会创建很多的Item,
	 * 其实这并非是真正的无限循环。
	 */
	@Override
	public int getCount() {
		return list.size();
	}

	/**
	 * 判断出去的view是否等于进来的view 如果为true直接复用
	 */
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	/**
	 * 销毁预加载以外的view对象, 会把需要销毁的对象的索引位置传进来，就是position，
	 * 因为mImageViewList只有五条数据，而position将会取到很大的值， 所以使用取余数的方法来获取每一条数据项。
	 */
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(list.get(position % list.size()));
	}

	/**
	 * 创建一个view，
	 */
	@Override
	public Object instantiateItem(ViewGroup container, final int position) {
		view = list.get(position);
		image = ((ImageView) view.findViewById(R.id.image));
		image.setTag(myList.get(position).getId());
		Log.i("ccccc",myList.get(position).getId()+"");
		ImageCacheUtil.IMAGE_CACHE.get(mImageUrlList.get(position), image);
		image.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i=new Intent(context,NewestDetail.class);
				i.putExtra("id", String.valueOf(v.getTag()));
				context.startActivity(i);
				
			}
		});
		// new DownloadImageTask().execute(mImageUrlList.get(position));
		container.addView(list.get(position % list.size()));
		return list.get(position % list.size());
	}

	@SuppressLint("NewApi")
	private class DownloadImageTask extends AsyncTask<String, Void, Drawable> {

		protected Drawable doInBackground(String... urls) {
			return loadImageFromNetwork(urls[0]);
		}

		protected void onPostExecute(Drawable result) {
			image.setBackground(result);
		}
	}

	private Drawable loadImageFromNetwork(String imageUrl) {
		Drawable drawable = null;
		try {
			// 可以在这里通过文件名来判断，是否本地有此图片
			drawable = Drawable.createFromStream(
					new URL(imageUrl).openStream(), "image.jpg");
		} catch (IOException e) {
			Log.d("test", e.getMessage());
		}
		if (drawable == null) {
			Log.d("test", "null drawable");
		} else {
			Log.d("test", "not null drawable");
		}

		return drawable;
	}

}
