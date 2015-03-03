package com.example.cityguild.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.examlpe.ices.util.ImageCacheUtil;
import com.examlpe.ices.util.Tools;
import com.example.cityguild.BaseActivity;
import com.example.cityguild.Config;
import com.example.cityguild.R;
import com.example.cityguild.entity.PicEntity;
import com.example.cityguild.fragement.MerchantFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class MainconActivity extends BaseActivity implements
		OnPageChangeListener {

	private LinearLayout llPoints;
	private int previousSelectPosition = 0;
	private int autoPosition;
	private ViewPager mainvp;
	private boolean isLoop = true;
	private Button btshanghu;
	private MerchantFragment merFragement;
	private MainActivity fca;
	private ArrayList<PicEntity> pe = new ArrayList<PicEntity>();
	private ArrayList<PicEntity> pem = new ArrayList<PicEntity>();
	private myAadpter adapter;
	private Intent i;
	boolean flag=true;
	private ScheduledExecutorService scheduledExecutorService;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				// prepareData();
				break;
			case 1:
				Toast.makeText(MainconActivity.this, (String) msg.obj,
						Toast.LENGTH_SHORT).show();
				break;
			case 2: // 网络有问题
				Toast.makeText(MainconActivity.this, "没有网络连接!",
						Toast.LENGTH_SHORT).show();
				break;
			case 3:
				
				mainvp.setCurrentItem(autoPosition);

				break;
			case 4:

				break;
			}
		}
	};
	private LinearLayout ll_zuixin;
	private LinearLayout ll_weiquan;
	private LinearLayout ll_gangwei;
	private LinearLayout ll_shanghu;
	private LinearLayout ll_jiankang;
	private LinearLayout ll_xiangguang;
	private LinearLayout ll_xiazai;
	private LinearLayout ll_jigou;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mainactivity1);
		initView();
		getData();
	}

	private class ScrollTask implements Runnable {

		public void run() {
			synchronized (mainvp) {
				
				autoPosition=previousSelectPosition;
				if(flag){
					autoPosition = (autoPosition + 1);
				
				}else{
					autoPosition=autoPosition-1;
			
					if(autoPosition==0)
						flag=true;
				}
				if(autoPosition>=(pe.size()-1)){
					flag=false;
					
				}
				 
				// 切换选中的点,把前一个点置为normal状态
				//llPoints.getChildAt(previousSelectPosition).setBackgroundResource(
					//	R.drawable.indicator);
				handler.sendEmptyMessage(3); // 通过Handler切换图片
			}
		}
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		super.onClick(view);
		switch (view.getId()) {

		case R.id.ll_dongtai: //
			i = new Intent(this, MainActivity.class);
			i.putExtra("index", "2");
			Log.i("11", "11");

			startActivity(i);

			break;
		case R.id.ll_weiquan://
			i = new Intent(this, MainActivity.class);
			i.putExtra("index", "3");

			startActivity(i);

			break;
		case R.id.ll_jigou://
			i = new Intent(this, MainActivity.class);
			i.putExtra("index", "4");

			startActivity(i);

			break;
		case R.id.ll_gangwei://
			i = new Intent(this, MainActivity.class);
			i.putExtra("index", "5");

			startActivity(i);

			break;
		case R.id.ll_shanghu://
			i = new Intent(this, MainActivity.class);
			i.putExtra("index", "6");

			startActivity(i);

			break;
		case R.id.ll_xiangguan://
			i = new Intent(this, MainActivity.class);
			i.putExtra("index", "8");

			startActivity(i);

			break;
		case R.id.ll_xiazai://
			i = new Intent(this, MainActivity.class);
			i.putExtra("index", "9");

			startActivity(i);

			break;
		case R.id.ll_jiankang://
			i = new Intent(this, MainActivity.class);
			i.putExtra("index", "10");

			startActivity(i);

			break;
		default:
			Toast.makeText(this, "此模块正在开发", 1000).show();
			break;
		}

	}

	private void initViewPager() {
		mainvp = (ViewPager) findViewById(R.id.mainvp);
		llPoints = (LinearLayout) findViewById(R.id.ll_points);
		prepareData();
		adapter = new myAadpter(pe, this);
		mainvp.setAdapter(adapter);
		mainvp.setOnPageChangeListener(this);
		// if(pe.size()!=0)
		llPoints.getChildAt(previousSelectPosition).setEnabled(true);

		llPoints.getChildAt(0).setBackgroundResource(
				R.drawable.indicator_focused);
		mainvp.setCurrentItem(0);
	}

	private void prepareData() {

		ImageView iv;
		View view;
		for (int i = 0; i < pe.size(); i++) {
			// iv = new ImageView(this);
			// iv.setBackgroundResource(imageResIDs[i]);
			// imageViewList.add(getImageUrl()[i]);

			// 添加点view对象
			view = new View(this);
			view.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.indicator));
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(16, 16);
			lp.leftMargin = 8;
			view.setLayoutParams(lp);
			view.setEnabled(false);
			llPoints.addView(view);
		}
	}

	private void initView() {

		ll_zuixin = (LinearLayout) findViewById(R.id.ll_dongtai);
		ll_weiquan = (LinearLayout) findViewById(R.id.ll_weiquan);
		ll_jigou = (LinearLayout) findViewById(R.id.ll_jigou);
		ll_gangwei = (LinearLayout) findViewById(R.id.ll_gangwei);
		ll_shanghu = (LinearLayout) findViewById(R.id.ll_shanghu);
		ll_jiankang = (LinearLayout) findViewById(R.id.ll_jiankang);
		ll_xiangguang = (LinearLayout) findViewById(R.id.ll_xiangguan);
		ll_xiazai = (LinearLayout) findViewById(R.id.ll_xiazai);

		ll_zuixin.setOnClickListener(this);
		ll_weiquan.setOnClickListener(this);
		ll_jigou.setOnClickListener(this);
		ll_gangwei.setOnClickListener(this);
		ll_shanghu.setOnClickListener(this);
		ll_jiankang.setOnClickListener(this);
		ll_xiangguang.setOnClickListener(this);
		ll_xiazai.setOnClickListener(this);

	}

	class myAadpter extends PagerAdapter {
		private List<PicEntity> mImageUrlList;
		private List<View> list = new ArrayList<View>();
		private LayoutInflater inflater;
		private ImageView image;
		private View view;
		private Drawable drawable;

		public myAadpter(List<PicEntity> imageViewList, Context context) {
			super();
			this.mImageUrlList = imageViewList;
			inflater = LayoutInflater.from(context);
			for (int i = 0; i < mImageUrlList.size(); i++) {
				View item = inflater.inflate(R.layout.item, null);

				list.add(item);
			}

		}

		/**
		 * 该方法将返回所包含的 Item总个数。为了实现一种循环滚动的效果，返回了基本整型的最大值 ，这样就会创建很多的Item,
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
			ImageCacheUtil.IMAGE_CACHE.get(mImageUrlList.get(position)
					.getSmallImg(), image);
			image.setId(position);
			image.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					if (Tools.isConnect(MainconActivity.this)) {
						Intent i = new Intent(MainconActivity.this,
								ImageActivity.class);
						i.putExtra("path", mImageUrlList.get(position)
								.getBigImg());
						startActivity(i);
					} else {
						Toast.makeText(getApplicationContext(), "请检查网络连接!",
								1000).show();
					}
				}
			});
			// DownloadImageTask().execute(mImageUrlList.get(position));
			container.addView(list.get(position % list.size()));
			return list.get(position % list.size());
		}
	}
	private void getData() {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub

		AsyncHttpClient c = new AsyncHttpClient();

		c.post(Config.FINDSHOUYE, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] responseBody) {
				// TODO Auto-generated method stub
				String userMsg = new String(responseBody).toString();

				// Log.i("ljp", userMsg);
				Gson gson = new Gson();
				// EventEntity
				JSONObject jsonobject = null;
				int code = 0;
				try {
					jsonobject = new JSONObject(userMsg);

					code = jsonobject.getInt("code");

					if (code == -2) {

					} else if (code == 1) {
						pe.clear();
						pem = gson.fromJson(jsonobject.getString("result"),
								new TypeToken<List<PicEntity>>() {
								}.getType());
						pe.addAll(pem);
						initViewPager();
						 scheduledExecutorService =
								 Executors.newSingleThreadScheduledExecutor();
								// 当Activity显示出来后，每两秒钟切换一次图片显示
								 scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 3,
								 TimeUnit.SECONDS);
						handler.sendEmptyMessage(0);
					} else {
						Toast.makeText(MainconActivity.this,
								jsonobject.getString("message"),
								Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] responseBody, Throwable error) {
				Log.i("失败", "失败");

			}
		});

	}

	@Override
	public void onPageSelected(int position) {
		
		// 切换选中的点,把前一个点置为normal状态
		llPoints.getChildAt(previousSelectPosition).setBackgroundResource(
				R.drawable.indicator);
		// 把当前选中的position对应的点置为enabled状态
		llPoints.getChildAt(position % pe.size()).setBackgroundResource(
				R.drawable.indicator_focused);
		previousSelectPosition = position % pe.size();
		
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	private long exitTime = 0;
	private int index = 0;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == event.KEYCODE_BACK) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(getApplicationContext(), "再按一次退出程序",
						Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				finish();
				System.exit(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onStart() {
	
		super.onStart();
	}

	@Override
	protected void onStop() {
		//scheduledExecutorService.shutdown();
		super.onStop();
	}
}
