package com.example.cityguild.fragement;




import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import com.examlpe.ices.util.Tools;
import com.example.cityguild.Config;
import com.example.cityguild.MyApplication;
import com.example.cityguild.R;
import com.example.cityguild.activity.AllCityActivity;
import com.example.cityguild.activity.DoctorActivity;
import com.example.cityguild.activity.JkSearchActivity;
import com.example.cityguild.activity.KeshiActivity;
import com.example.cityguild.activity.MainActivity;
import com.example.cityguild.activity.MerchantsMap;
import com.example.cityguild.activity.YiyuanActivity;
import com.example.cityguild.activity.jigouDetailActivity;
import com.example.cityguild.adapter.ViewPagerAadpter;
import com.example.cityguild.entity.CityEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class JiankangFragment extends Fragment implements OnClickListener,OnPageChangeListener{

	
	private MainActivity mActivity;
	private View view;
	private LinearLayout ll_adress;
	private TextView tv_city;
	private Button hg_btl;
	private RelativeLayout ll_yiyuan;
	private RelativeLayout ll_keshi;
	private TextView tv_yiyuan;
	private TextView tv_keshi;
	private Button gu_btr;
	private String keshiv;
	private String yiyuanv;
	private String cpid;
	private String hospitalid;
	private String deptidv;
	private Boolean isFrist = true;
	private String yiyuan;
	private String keshiva;
	private LinearLayout ll_search;
	private String sheng="";
	private String city="";
	private LinearLayout ll_caidan;
	private String area_id;
	List<CityEntity> myList = new ArrayList<CityEntity>();
	List<CityEntity> chidlist = new ArrayList<CityEntity>();
	List<CityEntity> moreList = new ArrayList<CityEntity>();
	private List<String> imageViewList = new ArrayList<String>();  
	private LocationClient mLocClient;
	 private int[] imageResId = new int[] {R.drawable.jiankangsy1,R.drawable.jiankangsy2 };
	private ViewPager mainvp;
	private LinearLayout llPoints;  
	private int previousSelectPosition = 0;
	 private List<ImageView> imageViews;
	 boolean flag=true;
	 private int autoPosition;
	 private ScheduledExecutorService scheduledExecutorService;
	 private Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
		
				case 0:
					mainvp.setCurrentItem(autoPosition);
					break;
				}
			}
		};
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
	}
	@Override
	public void onAttach(Activity activity) {
		mActivity = (MainActivity) activity;
		super.onAttach(activity);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.guahao, null);
			initView();
			mLocClient = new LocationClient(mActivity);
			mLocClient.registerLocationListener(new MyLocationListenner());
			LocationClientOption option = new LocationClientOption();
			option.setOpenGps(true);// 打开gps
			option.setCoorType("bd09ll"); // 设置坐标类型
			option.setScanSpan(1000);
			option.setAddrType("all"); 
			mLocClient.setLocOption(option);
			mLocClient.start();
			
			
		}
		initViewPager();
		return view;
	}
private class ScrollTask implements Runnable {
		
		public void run() {
			synchronized (mainvp) {
				
				autoPosition=previousSelectPosition;
				if(flag){
					autoPosition = (autoPosition + 1);
					//Log.i("+++++","+++++");
				}else{
					autoPosition=autoPosition-1;
			//Log.i("----","-------");
					if(autoPosition==0)
						flag=true;
				}
				if(autoPosition>=(imageResId.length-1)){
					flag=false;
					//Log.i("cccc",myList.size()+"");
				}
				 System.out.println("currentItem: " + previousSelectPosition);
				 
				// 切换选中的点,把前一个点置为normal状态
				//llPoints.getChildAt(previousSelectPosition).setBackgroundResource(
					//	R.drawable.indicator);
				handler.sendEmptyMessage(0); // 通过Handler切换图片
			}
		}

	}
	 private void prepareData() {  
         
		 
         ImageView iv;  
         View view;  
         imageViews = new ArrayList<ImageView>();
         for (int i = 0; i <  imageResId.length; i++) {  
            // iv = new ImageView(this);  
             //iv.setBackgroundResource(imageResIDs[i]);  
             //imageViewList.add(getImageUrl()[i]);  
        	 ImageView imageView = new ImageView(mActivity);  
	            imageView.setImageResource(imageResId[i]);  
	            imageView.setScaleType(ScaleType.CENTER_CROP);  
	            imageViews.add(imageView); 
             // 添加点view对象  
             view = new View(mActivity);  
             view.setBackgroundDrawable(getResources().getDrawable(R.drawable.indicator));  
             LinearLayout.LayoutParams lp = new 	LinearLayout.LayoutParams(30,30);
             lp.leftMargin = 10; 
             view.setLayoutParams(lp);  
             view.setEnabled(false);  
             llPoints.addView(view);  
         }  
     }  
	private void initViewPager() {
	
	  
	          
	    
		mainvp = (ViewPager)view.findViewById(R.id.mainvp);
		
		llPoints = (LinearLayout)view.findViewById(R.id.ll_points);        
	        prepareData();  	          
	       //adapter = new ViewPagerAadpter(imageViewList,mActivity,myList);  
	        mainvp.setAdapter(new MyAdapter());;  
	        mainvp.setOnPageChangeListener(this);  
	       // llPoints.getChildAt(previousSelectPosition).setEnabled(true); 
	        //llPoints.getChildAt(0);
			//.setBackgroundResource(R.drawable.indicator_focused);
	        llPoints.getChildAt(0).setBackgroundResource(R.drawable.indicator_focused);  
	        mainvp.setCurrentItem(0);  
			MainActivity.mSlidingMenu.addIgnoredView(mainvp);
	}
	public class MyLocationListenner implements BDLocationListener {

		private String cityname;

		@Override
		public void onReceiveLocation(BDLocation location) {

			if (Tools.isConnect(mActivity)) {
				if (isFrist&&Config.isFrist) {
					cityname = location.getCity();
					//Log.i("cityname",location.getCity());
					sheng=location.getProvince();
					tv_city.setText(cityname);
					Config.city=cityname;
					Config.sheng=sheng;
					isFrist=false;
					Config.isFrist=false;
					getData();
				}
			}else{
				//Toast.makeText(mActivity, "请检查网络连接!", 1000).show();
			}
			
		}

		public void onReceivePoi(BDLocation poiLocation) {
		}
	}
	private void getData() {
		RequestParams params = new RequestParams();
		params.put("token", MyApplication.getToken());
		MyApplication.getInstance().getClient()
				.post(Config.FINDCITY, new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							byte[] responseBody) {
						// TODO Auto-generated method stub
						String userMsg = new String(responseBody).toString();

						//Log.i("ljp", userMsg);
						Gson gson = new Gson();
						// EventEntity
						JSONObject jsonobject = null;
						int code = 0;
						try {
							jsonobject = new JSONObject(userMsg);

							code = jsonobject.getInt("code");

							if (code == -2) {

							} else if (code == 1) {
								myList.clear();
								moreList = gson.fromJson(
										jsonobject.getString("result"),
										new TypeToken<List<CityEntity>>() {
										}.getType());

								// Log.i(myList.get(0).getName(),"111");
								myList.addAll(moreList);
								myList.get(0).setIslight(true);
								initAdress();
							} else {
								Toast.makeText(mActivity,
										jsonobject.getString("message"),
										Toast.LENGTH_SHORT).show();
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

					private void initAdress() {
						for(int i=0;i<myList.size();i++){
							if(myList.get(i).getCity_name().equals(Config.sheng)){
								int j=myList.get(i).getChildrens().size();
								chidlist=myList.get(i).getChildrens();
								if(chidlist.size()==0){
									Config.area_id=myList.get(i).getCity_area_id();
								}else{
									for(int a=0;a<j;a++){
										if(chidlist.get(a).getArea_name().equals(tv_city.getText())){
											Config.area_id=chidlist.get(a).getArea_id();
										}
									}
								}
							
							}
						}
						
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							byte[] responseBody, Throwable error) {
						// TODO Auto-generated method stub

					}
				});

	}

	private void initView() {
	ll_adress = (LinearLayout)view.findViewById(R.id.ll_adress);
	ll_adress.setOnClickListener(this);
	tv_city = (TextView)view.findViewById(R.id.adress);
	tv_yiyuan = (TextView)view.findViewById(R.id.tv_yiyuan);
	tv_keshi = (TextView)view.findViewById(R.id.tv_keshi);
	
	hg_btl = (Button)view.findViewById(R.id.hg_btl);
	gu_btr = (Button)view.findViewById(R.id.gu_btr);
	gu_btr.setOnClickListener(this);
	hg_btl.setOnClickListener(this);
	
	ll_yiyuan = (RelativeLayout)view.findViewById(R.id.ll_yiyuan);
	ll_keshi = (RelativeLayout)view.findViewById(R.id.ll_keshi);
	ll_yiyuan.setOnClickListener(this);
	ll_keshi.setOnClickListener(this);
	ll_search = (LinearLayout)view.findViewById(R.id.ll_search);
	ll_search.setOnClickListener(this);
	ll_caidan = (LinearLayout)view.findViewById(R.id.ll_caidan);
	ll_caidan.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.ll_adress){
			Intent i=new Intent(mActivity,AllCityActivity.class);
			String city=tv_city.getText().toString();
			if(city.equals("北京")||city.equals("天津")||city.equals("上海")||city.equals("重庆")){
				i.putExtra("sheng", city);
				i.putExtra("city", city);
			}else{
				//Log.i("sheng",sheng);
				i.putExtra("sheng", sheng);
				i.putExtra("city", city);
			}
			
			startActivityForResult(i, 0);
		}
		if(v.getId()==R.id.hg_btl){
			if(!tv_city.equals("")){
				Intent i=new Intent(mActivity,AllCityActivity.class);
				i.putExtra("sheng", sheng);
				i.putExtra("city", city);
				startActivityForResult(i, 0);
			}
			
		}
		if(v.getId()==R.id.ll_yiyuan){
			Intent i=new Intent(mActivity,YiyuanActivity.class);
			startActivityForResult(i, 0);
		}
		if(v.getId()==R.id.ll_keshi){
			if(tv_yiyuan.getText().toString().equals("请选择医院")){
				Toast.makeText(getActivity(), "请先选择医院!", 1000).show();
				return;
			}
			Intent i=new Intent(mActivity,KeshiActivity.class);
			if(cpid!=null)
				i.putExtra("cpid", cpid);
			if(hospitalid!=null)
				i.putExtra("hospitalid", hospitalid);
			startActivityForResult(i, 0);
		}
		if(v.getId()==R.id.gu_btr){
			yiyuanv = tv_yiyuan.getText().toString();
			keshiv = tv_keshi.getText().toString();
			if(yiyuanv.equals("请选择医院")){
				Toast.makeText(getActivity(), "请先选择医院!", 1000).show();
				return;
			}
			if(keshiv.equals("请选择科室")){
				Toast.makeText(getActivity(), "请选择科室!", 1000).show();
				return;
			}
			Intent i=new Intent(mActivity,DoctorActivity.class);
			i.putExtra("hospitalid", Config.hospitalid);
			i.putExtra("deptidv", deptidv);
			i.putExtra("cpid", Config.cpid);
			i.putExtra("yiyuan", yiyuanv);
			i.putExtra("keshi", keshiva);
			startActivity(i);
		}
		if(v.getId()==R.id.ll_search){
			
			Intent i=new Intent(mActivity,JkSearchActivity.class);
			i.putExtra("city",tv_city.getText());
			i.putExtra("sheng",sheng);
			startActivity(i);
		}
		if(v.getId()==R.id.ll_caidan){
			mActivity.mSlidingMenu.toggle();
		}
		
	}
	
	@Override
	public void onResume() {
		super.onResume();
		mLocClient.start();
		sheng=Config.sheng;
		city=Config.city;
		tv_city.setText(city);
		
	}
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (data != null) {
			Bundle b = data.getExtras();			
			city = b.getString("city");
			sheng = b.getString("sheng");
			
			yiyuan = b.getString("yiyuan");
			keshiva = b.getString("keshi");
			cpid = b.getString("cpid");
			hospitalid = b.getString("hospitalid");
			deptidv = b.getString("deptid");
			area_id = b.getString("area_id");

			if (city != null){
				tv_city.setText(city);
				Config.city=city;
			}
			Log.i("cpid",Config.hospitalid);
			if(sheng!=null)
				Config.sheng=sheng;
			if(area_id!=null)
				Config.area_id=area_id;
			if (yiyuan != null)
				tv_yiyuan.setText(yiyuan);
			if (keshiva != null)
				tv_keshi.setText(keshiva);
			if (hospitalid != null)
				Config.hospitalid=hospitalid;
			if (hospitalid != null)
				Config.cpid=cpid;
		
		}
	}
	@Override
	public void onPageScrollStateChanged(int position) {
		
		
	}
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onPageSelected(int position) {
		  // 切换选中的点,把前一个点置为normal状态  
        llPoints.getChildAt(previousSelectPosition).setBackgroundResource(R.drawable.indicator);  
        // 把当前选中的position对应的点置为enabled状态  
        llPoints.getChildAt(position % imageResId.length).setBackgroundResource(R.drawable.indicator_focused);  
        previousSelectPosition = position  % imageResId.length;
        Log.i("cccc", previousSelectPosition+"");
		
	}
	private class MyAdapter extends PagerAdapter {  
		  
        @Override  
        public int getCount() {  
            return imageResId.length;  
        }  
  
        @Override  
        public Object instantiateItem(View arg0, int arg1) {  
            ((ViewPager) arg0).addView(imageViews.get(arg1));  
            return imageViews.get(arg1);  
        }  
  
        @Override  
        public void destroyItem(View arg0, int arg1, Object arg2) {  
            ((ViewPager) arg0).removeView((View) arg2);  
        }  
  
        @Override  
        public boolean isViewFromObject(View arg0, Object arg1) {  
            return arg0 == arg1;  
        }  
  
        @Override  
        public void restoreState(Parcelable arg0, ClassLoader arg1) {  
  
        }  
  
        @Override  
        public Parcelable saveState() {  
            return null;  
        }  
  
        @Override  
        public void startUpdate(View arg0) {  
  
        }  
  
        @Override  
        public void finishUpdate(View arg0) {  
  
        }  
    } 
	
	@Override
	public void onStart() {
		scheduledExecutorService =
				 Executors.newSingleThreadScheduledExecutor();
				// 当Activity显示出来后，每两秒钟切换一次图片显示
				 scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 3,
				 TimeUnit.SECONDS);
		super.onStart();
	}
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
}
