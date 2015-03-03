package com.example.cityguild.fragement;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.examlpe.ices.util.TitleMainUtil;
import com.examlpe.ices.util.Tools;
import com.examlpe.ices.util.XListView;
import com.examlpe.ices.util.XListView.IXListViewListener;
import com.example.cityguild.Config;
import com.example.cityguild.MyApplication;
import com.example.cityguild.R;
import com.example.cityguild.activity.MainActivity;
import com.example.cityguild.activity.NewestDetail;
import com.example.cityguild.adapter.ViewPagerAadpter;
import com.example.cityguild.adapter.listadapter;
import com.example.cityguild.entity.NewestEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class NewestFragment extends Fragment implements OnPageChangeListener,IXListViewListener{
	
	private List<String> imageViewList = new ArrayList<String>();   
	private View rootView;// 缓存Fragment view
	private int page = 1;
	private boolean onRefresh_number = true;
	private LinearLayout llPoints;
	private ViewPager mainvp;
	private int previousSelectPosition = 0;
	private int autoPosition;
	private MainActivity mActivity;
	private XListView lv;
	private ArrayList<String> ma = new ArrayList<String>();
	List<NewestEntity> myList = new ArrayList<NewestEntity>();
	List<NewestEntity> moreList = new ArrayList<NewestEntity>();
	boolean flag=true;
	List<NewestEntity> myList1 = new ArrayList<NewestEntity>();
	List<NewestEntity> moreList1 = new ArrayList<NewestEntity>();
	private ViewPagerAadpter adapter;
	private listadapter listadapter;
	private ScheduledExecutorService scheduledExecutorService;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				onLoad();
				ll_load = (LinearLayout)rootView.findViewById(R.id.load);
				ll_load.setVisibility(View.GONE);
				listadapter.notifyDataSetChanged();
				//if(adapter!=null);
				//adapter.notifyDataSetChanged();
				onRefresh_number = true;
				break;
			case 1:
				Toast.makeText(mActivity, (String) msg.obj,
						Toast.LENGTH_SHORT).show();
				break;
			case 2: // 网络有问题
				Toast.makeText(mActivity, "没有网络连接!",
						Toast.LENGTH_SHORT).show();
				break;
			case 3:
				
				break;
			case 4:
				Toast.makeText(mActivity,  " 列表为空!",
						Toast.LENGTH_SHORT).show();
				break;
			case 5:
				Toast.makeText(mActivity,  " 没有更多数据!",
						Toast.LENGTH_SHORT).show();
				break;
			case 6:
				mainvp.setCurrentItem(autoPosition);
				break;
			}
		}
	};
	private LinearLayout ll_load;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
	}
	private class ScrollTask implements Runnable {
		
		public void run() {
			synchronized (mainvp) {
				
				autoPosition=previousSelectPosition;
				if(flag){
					autoPosition = (autoPosition + 1);
					Log.i("+++++","+++++");
				}else{
					autoPosition=autoPosition-1;
			Log.i("----","-------");
					if(autoPosition==0)
						flag=true;
				}
				if(autoPosition>=(myList.size()-1)){
					flag=false;
					Log.i("cccc",myList.size()+"");
				}
				 System.out.println("currentItem: " + previousSelectPosition);
				 
				// 切换选中的点,把前一个点置为normal状态
				//llPoints.getChildAt(previousSelectPosition).setBackgroundResource(
					//	R.drawable.indicator);
				handler.sendEmptyMessage(6); // 通过Handler切换图片
			}
		}

	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (rootView == null) {
			rootView = inflater.inflate(R.layout.newest_fragment, null);
		}

		
		new TitleMainUtil(mActivity, "最新动态",rootView).init();
		initView();
		getData1();
		getData();
		
		return rootView;

	}
	
	@Override
	public void onAttach(Activity activity) {
		mActivity = (MainActivity) activity;
		
		super.onAttach(activity);
	}
	private void onLoad() {
		lv.stopRefresh();
		lv.stopLoadMore();
		lv.setRefreshTime(Tools.getHourAndMin());
	}
	private void initViewPager() {
		mainvp=(ViewPager)rootView.findViewById(R.id.mainvp);
		
		 llPoints = (LinearLayout)rootView.findViewById(R.id.ll_points);        
	        prepareData();  	          
	       adapter = new ViewPagerAadpter(imageViewList,mActivity,myList);  
	        mainvp.setAdapter(adapter);  
	        mainvp.setOnPageChangeListener(this);  
	        llPoints.getChildAt(previousSelectPosition).setEnabled(true); 
	        llPoints.getChildAt(0)
			.setBackgroundResource(R.drawable.indicator_focused);
	        mainvp.setCurrentItem(0);  
			MainActivity.mSlidingMenu.addIgnoredView(mainvp);
	}
	private View findViewById(int llPoints2) {
	// TODO Auto-generated method stub
	return null;
}

	private void initView() {
		
		lv = (XListView) rootView.findViewById(R.id.newest_zuixin);
		lv.setPullLoadEnable(true);
		lv.setXListViewListener(this);
		lv.setDivider(null);
		listadapter = new listadapter(mActivity,myList1);
		lv.setAdapter(listadapter);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				startActivity(new Intent(mActivity, NewestDetail.class));

			}
		});
		
		
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
        llPoints.getChildAt(previousSelectPosition).setBackgroundResource(R.drawable.indicator);  
        // 把当前选中的position对应的点置为enabled状态  
        llPoints.getChildAt(position % ma.size()).setBackgroundResource(R.drawable.indicator_focused);  
        previousSelectPosition = position  % ma.size();  

	}
	 
    private void prepareData() {  
            
 
           ImageView iv;  
           View view;  
           for (int i = 0; i < getImageUrl().size(); i++) {  
              // iv = new ImageView(this);  
               //iv.setBackgroundResource(imageResIDs[i]);  
               imageViewList.add(getImageUrl().get(i));  
                 
               // 添加点view对象  
               view = new View(mActivity);  
               view.setBackgroundDrawable(getResources().getDrawable(R.drawable.indicator));  
               LinearLayout.LayoutParams lp = new 	LinearLayout.LayoutParams(15,15);
               lp.leftMargin = 6; 
               view.setLayoutParams(lp);  
               view.setEnabled(false);  
               llPoints.addView(view);  
           }  
       }  
         
       private ArrayList<String> getImageUrl() {  
    	   return ma;
       }
       private void getData1() {
   	
   		RequestParams params = new RequestParams();
   		params.put("offset", page); 

   		AsyncHttpClient c = new AsyncHttpClient();
   		
   		c.post(Config.NEWESTTEXT, params, new AsyncHttpResponseHandler() {

   			@Override
   			public void onSuccess(int statusCode, Header[] headers,
   					byte[] responseBody) {
   				// TODO Auto-generated method stub
   				String userMsg = new String(responseBody).toString();
   	 
   				Log.i("response", userMsg);
   				Gson gson = new Gson();
   				//EventEntity
   				JSONObject jsonobject = null;
   				int code = 0;
   				try {
   					jsonobject = new JSONObject(userMsg);
   					 
   					 
   					code = jsonobject.getInt("code");
   					
   					if(code==-2){
   						
   					}else if(code==1){ 
   						moreList1.clear();
   						moreList1 = gson.fromJson(jsonobject.getString("result"),
   	 		 					new TypeToken<List<NewestEntity>>() {
   	 							}.getType());
   						Log.i(String.valueOf(moreList.size()),"111");
   					
   						myList1.addAll(moreList1);
   						if(myList1.size()==0){
   							handler.sendEmptyMessage(4);
   						}else{
   							if(moreList1.size()==0)
   								handler.sendEmptyMessage(5);
   						}
   						handler.sendEmptyMessage(0);
   					}else{
   						Toast.makeText(mActivity, jsonobject.getString("message"),
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
   				Log.i("onFailure", "onFailure");
   				
   			}
   		});

   	
   	}
	private void getData() {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		AsyncHttpClient client = new AsyncHttpClient(); // 创建异步请求的客户端对象
		RequestParams params = new RequestParams();
		AsyncHttpClient c = new AsyncHttpClient();
		
		c.post(Config.NEWEST, params, new AsyncHttpResponseHandler() {

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
						
					}else if(code==1){ 
						myList.clear();
						moreList = gson.fromJson(jsonobject.getString("result"),
	 		 					new TypeToken<List<NewestEntity>>() {
	 							}.getType());
						Log.i(String.valueOf(moreList.size()),"111");
						for(int i=0;i<moreList.size();i++){
							if(moreList.get(i).getImgPath()!=null){
								ma.add(moreList.get(i).getImgPath());
							}
						}
						myList.addAll(moreList);
						initViewPager();
						 scheduledExecutorService =
								 Executors.newSingleThreadScheduledExecutor();
								// 当Activity显示出来后，每两秒钟切换一次图片显示
								 scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 3,
								 TimeUnit.SECONDS);
						handler.sendEmptyMessage(0);
					}else{
						Toast.makeText(mActivity, jsonobject.getString("message"),
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
				// TODO Auto-generated method stub
				
			}
		});

	
	}

	@Override
	public void onRefresh() {
		page = 1;
		mainvp.setCurrentItem(0);
		getData1();
		
		
	}
@Override
public void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
	
}
	@Override
	public void onLoadMore() {
	if (onRefresh_number) {
			
			if (Tools.isConnect(mActivity)) {
				
				onRefresh_number = false;
					page = page + 1;
					getData1();
				
				
			} else {
				onRefresh_number = true;
				handler.sendEmptyMessage(2);
			}
		} else {
			handler.sendEmptyMessage(3);
		}
		
	}
@Override
public void onStart() {

			super.onStart();
}
@Override
public void onStop() {
	scheduledExecutorService.shutdown();
	super.onStop();
}
}
