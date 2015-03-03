package com.example.cityguild.fragement;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.examlpe.ices.util.ImageCacheUtil;
import com.example.cityguild.MyApplication;
import com.example.cityguild.R;
import com.example.cityguild.activity.MainActivity;
import com.example.cityguild.adapter.ViewPagerAadpter;
import com.example.cityguild.entity.NewestEntity;
import com.example.cityguild.entity.PicEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainFragment extends Fragment implements OnPageChangeListener,
		OnClickListener {
	private View rootView;// 缓存Fragment view
	private FragmentManager fragmentManager;
	private NewestFragment newestFragment;
	private WeiquanFragment serchFragment;
	private jigouFragment jigouFragment;
	private PostFragement post;
	private MainActivity mainActivity;
	private MainFragment mainFragment;
	private Button zuixin;
	private Button weiquan;
	private Button jigou;
	private Button gangwei;
	private Button shanghu;
	private Button jiankang;
	private Button xiangguang;
	private Button xiazai;

	private LinearLayout llPoints;
	private int previousSelectPosition = 0;
	private ViewPager mainvp;
	private boolean isLoop = true;
	private Button btshanghu;
	private MerchantFragment merFragement;
	private MainActivity fca;
	private ArrayList<PicEntity> pe = new ArrayList<PicEntity>();
	private ArrayList<PicEntity> pem = new ArrayList<PicEntity>();
	private myAadpter adapter;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				//prepareData();
				break;
			case 1:
				Toast.makeText(mainActivity, (String) msg.obj,
						Toast.LENGTH_SHORT).show();
				break;
			case 2: // 网络有问题
				Toast.makeText(mainActivity, "没有网络连接!", Toast.LENGTH_SHORT)
						.show();
				break;
			case 3:

				break;
			case 4:

				break;
			}
		}
	};
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (rootView == null) {
			rootView = inflater.inflate(R.layout.mainactivity1, null);
		}
		initView();
		getData();
		// initViewPager();

		return rootView;
	}

	private void initViewPager() {
		mainvp = (ViewPager) rootView.findViewById(R.id.mainvp);
		

		llPoints = (LinearLayout) rootView.findViewById(R.id.ll_points);

		prepareData();

		adapter = new myAadpter(pe,mainActivity);
		mainvp.setAdapter(adapter);
		mainvp.setOnPageChangeListener(this);
		if(pe.size()!=0)
		llPoints.getChildAt(previousSelectPosition).setEnabled(true);



		mainvp.setCurrentItem(0);
	}

	private void prepareData() {
		

		ImageView iv;
		View view;
		for (int i = 0; i < pe.size(); i++) {
			// iv = new ImageView(this);
			// iv.setBackgroundResource(imageResIDs[i]);
			//imageViewList.add(getImageUrl()[i]);

			// 添加点view对象
			view = new View(mainActivity);
			view.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.indicator));
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(10, 10);
			lp.leftMargin = 4;
			view.setLayoutParams(lp);
			view.setEnabled(false);
			llPoints.addView(view);
		}
	}



	private void initView() {
		zuixin = (Button) rootView.findViewById(R.id.btdongtai);
		weiquan = (Button) rootView.findViewById(R.id.btweiquan);
		jigou = (Button) rootView.findViewById(R.id.btjigou);
		gangwei = (Button) rootView.findViewById(R.id.btgangwei);
		shanghu = (Button) rootView.findViewById(R.id.btshanghu);
		jiankang = (Button) rootView.findViewById(R.id.btjiankang);
		xiangguang = (Button) rootView.findViewById(R.id.btxiangguan);
		xiazai = (Button) rootView.findViewById(R.id.btxiazai);

		zuixin.setOnClickListener(this);
		weiquan.setOnClickListener(this);
		jigou.setOnClickListener(this);
		gangwei.setOnClickListener(this);
		shanghu.setOnClickListener(this);
		jiankang.setOnClickListener(this);
		xiangguang.setOnClickListener(this);
		xiazai.setOnClickListener(this);
	}
	private void getData() {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
	
	
		AsyncHttpClient c = new AsyncHttpClient();
		
		c.post("http://192.168.0.250:7070/shanghaiunion/api/activity/findAll",  new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] responseBody) {
				// TODO Auto-generated method stub
				String userMsg = new String(responseBody).toString();
	 
				Log.i("ljp", userMsg);
				Gson gson = new Gson();
				//EventEntity
				JSONObject jsonobject = null;
				int code = 0;
				try {
					jsonobject = new JSONObject(userMsg);
					 
					 
					code = jsonobject.getInt("code");
					
					if(code==-2){
						
					}else if(code==0){ 
						pe.clear();
						pem = gson.fromJson(jsonobject.getString("result"),
	 		 					new TypeToken<List<PicEntity>>() {
	 							}.getType());
						pe.addAll(pem);
						initViewPager();
						handler.sendEmptyMessage(0);
					}else{
						Toast.makeText(mainActivity, jsonobject.getString("message"),
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
				Log.i("失败","失败");
				
			}
		});

	
	}
	@Override
	public void onClick(View view) {
		fca = (MainActivity) getActivity();

		switch (view.getId()) {

		case R.id.btdongtai: //
			fca.switchContent(2);
			break;
		case R.id.btweiquan://
			fca.switchContent(3);

			break;
		case R.id.btjigou://
			fca.switchContent(4);
			break;
		case R.id.btgangwei://
			fca.switchContent(5);
			break;
		case R.id.btshanghu://
			fca.switchContent(6);
			break;
		case R.id.btxiazai://
			fca.switchContent(9);
			break;
		default:
			Toast.makeText(mainActivity, "此模块正在开发", 1000).show();
			break;
		}

	}

	@Override
	public void onAttach(Activity activity) {
		mainActivity = (MainActivity) activity;
		super.onAttach(activity);
	}

	private void hideFragments(FragmentTransaction transaction) {
		if (mainFragment != null) {
			transaction.hide(mainFragment);
		}
		if (newestFragment != null) {
			transaction.hide(newestFragment);
		}
		if (serchFragment != null) {
			transaction.hide(serchFragment);
		}
		if (jigouFragment != null) {
			transaction.hide(jigouFragment);
		}
		if (post != null) {
			transaction.hide(post);
		}
		if (merFragement != null) {
			transaction.hide(merFragement);
		}
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int position) {

		// 切换选中的点,把前一个点置为normal状态
		llPoints.getChildAt(previousSelectPosition).setBackgroundResource(
				R.drawable.indicator);
		// 把当前选中的position对应的点置为enabled状态
		llPoints.getChildAt(position % pe.size())
				.setBackgroundResource(R.drawable.indicator_focused);
		previousSelectPosition = position % pe.size();
	}

	
	
	
	class myAadpter extends PagerAdapter {
		private List<PicEntity> mImageUrlList;
		private List<View> list = new ArrayList<View>();
		private LayoutInflater inflater;
		private ImageView image;
		private View view;
		private Drawable drawable;

		public myAadpter(
				List<PicEntity> imageViewList,
				Context context) {
			super();
			this.mImageUrlList = imageViewList;
			inflater = LayoutInflater.from(context);
			for (int i = 0; i < mImageUrlList
					.size(); i++) {
				View item = inflater.inflate(
						R.layout.item, null);

				list.add(item);
			}

		}

		/**
		 * 该方法将返回所包含的
		 * Item总个数。为了实现一种循环滚动的效果，返回了基本整型的最大值
		 * ，这样就会创建很多的Item, 其实这并非是真正的无限循环。
		 */
		@Override
		public int getCount() {
			return list.size();
		}

		/**
		 * 判断出去的view是否等于进来的view 如果为true直接复用
		 */
		@Override
		public boolean isViewFromObject(View arg0,
				Object arg1) {
			return arg0 == arg1;
		}

		/**
		 * 销毁预加载以外的view对象,
		 * 会把需要销毁的对象的索引位置传进来，就是position，
		 * 因为mImageViewList只有五条数据，而position将会取到很大的值，
		 * 所以使用取余数的方法来获取每一条数据项。
		 */
		@Override
		public void destroyItem(
				ViewGroup container, int position,
				Object object) {
			container.removeView(list.get(position
					% list.size()));
		}

		/**
		 * 创建一个view，
		 */
		@Override
		public Object instantiateItem(
				ViewGroup container,
				final int position) {
			view = list.get(position);
			image = ((ImageView) view
					.findViewById(R.id.image));
			ImageCacheUtil.IMAGE_CACHE.get(
					mImageUrlList.get(position).getSmallImg(),
					image);

			// new
			// DownloadImageTask().execute(mImageUrlList.get(position));
			container.addView(list.get(position
					% list.size()));
			return list.get(position % list.size());
		}

	}
}
