package com.example.cityguild.activity;

import java.util.ArrayList;
import java.util.List;

import com.example.cityguild.AlertDialog;
import com.example.cityguild.BaseActivity;
import com.example.cityguild.R;
import com.example.cityguild.adapter.ViewPagerAadpter;
import com.example.cityguild.fragement.AboutFragment;
import com.example.cityguild.fragement.DownloadFragment;
import com.example.cityguild.fragement.JiankangFragment;
import com.example.cityguild.fragement.MainFragment;
import com.example.cityguild.fragement.MerchantFragment;
import com.example.cityguild.fragement.NaviFragment;
import com.example.cityguild.fragement.NewestFragment;
import com.example.cityguild.fragement.PostFragement;
import com.example.cityguild.fragement.WeiquanFragment;
import com.example.cityguild.fragement.jigouFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnClosedListener;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnOpenListener;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;


import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;

import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends SlidingFragmentActivity implements OnClickListener{
	public static SlidingMenu mSlidingMenu;
	
	private NewestFragment newestFragment;
	private WeiquanFragment serchFragment;
	private jigouFragment jigouFragment;
	private PostFragement post;
	private MainFragment mainFragment;
	private MerchantFragment merFragment;
	private NaviFragment naviFragment;
	private DownloadFragment downloadFragment;
	private AboutFragment aboutFragment;
	private JiankangFragment jiankangFragment;

	private FrameLayout ll_main; 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cccc);
		//ll_main = (FrameLayout)findViewById(R.id.main);
		//ll_main.setVisibility(View.INVISIBLE);
		initFragment();
		Log.i("index",getIntent().getStringExtra("index"));
		 switchContent(Integer.parseInt(getIntent().getStringExtra("index")));
	}
	 /**
     * 切换视图
     * @param fragment
     */
    public void switchContent(int index) {
        //mContent = fragment;
       // getSupportFragmentManager().beginTransaction()
        //        .replace(R.id.main, fragment).commit();
        //getSlidingMenu().showContent();
    	FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		hideFragments(transaction);
		switch (index) {
		case 5://
			if (null == post) {

				post = new PostFragement();
				 transaction.add(R.id.main, post);
				//fca = (MainActivity) getActivity();
				//fca.switchContent(post);

			} else {
				transaction.show(post);
			}
			break;
		case 1://
			//MainActivity.this.finish();
			//startActivity(new Intent(MainActivity.this,MainconActivity.class));
				//startActivity(new Intent(mActivity, MainActivity.class));
				this.finish();

			break;
		case 2: //
			Log.i("xin","xin");
			if (null == newestFragment) {
				newestFragment = new NewestFragment();
				transaction.add(R.id.main, newestFragment);
				//fca = (MainActivity) getActivity();
				//fca.switchContent(newestFragment);

			} else {
				transaction.show(newestFragment);
				// String phone = SharedPrefsUtil.getString(getActivity(),
				// AppConst.USERPHONE);
				// if (phone != null && !"".equals(phone)) {
				// contactsFragment.et_phone.setText(phone);
				// }
			}
			break;
		case 3://
		
			if (null == serchFragment) {
				
				serchFragment = new WeiquanFragment();
				 transaction.add(R.id.main, serchFragment);
				//fca = (MainActivity) getActivity();
				//fca.switchContent(serchFragment);
			} else {
				transaction.show(serchFragment);
			}
			break;
		case 4://
			if (null == jigouFragment) {
				jigouFragment = new jigouFragment();
				 transaction.add(R.id.main, jigouFragment);
				//fca = (MainActivity) getActivity();
				//fca.switchContent(jigouFragment);
			} else {
				transaction.show(jigouFragment);
			}
			break;
		case 6://
			if (null == merFragment) {
				merFragment = new MerchantFragment();
				 transaction.add(R.id.main, merFragment);
				//fca = (MainActivity) getActivity();
				//fca.switchContent(jigouFragment);
			} else {
				transaction.show(merFragment);
			}
			break;
		case 8://
			if (null == aboutFragment) {
				aboutFragment = new AboutFragment();
				 transaction.add(R.id.main, aboutFragment);
				//fca = (MainActivity) getActivity();
				//fca.switchContent(jigouFragment);
			} else {
				transaction.show(aboutFragment);
			}
			break;
		case 9://
			if (null == downloadFragment) {
				downloadFragment = new DownloadFragment();
				 transaction.add(R.id.main, downloadFragment);
				//fca = (MainActivity) getActivity();
				//fca.switchContent(jigouFragment);
			} else {
				transaction.show(downloadFragment);
			}
			break;
		case 10://
			if (null == jiankangFragment) {
				jiankangFragment = new JiankangFragment();
				 transaction.add(R.id.main, jiankangFragment);
				//fca = (MainActivity) getActivity();
				//fca.switchContent(jigouFragment);
			} else {
				transaction.show(jiankangFragment);
			}
			break;
		}
		transaction.commit();
        
        
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
		if (merFragment != null) {
			transaction.hide(merFragment);
		}
		if (aboutFragment != null) {
			transaction.hide(aboutFragment);
		}
		if (downloadFragment != null) {
			transaction.hide(downloadFragment);
		}
		if (jiankangFragment != null) {
			transaction.hide(jiankangFragment);
		}
	}
	private void initFragment() {

		mSlidingMenu = getSlidingMenu();
		
//		wechatFragment = new WechatFragment();
//		getSupportFragmentManager().beginTransaction()
//				.replace(R.id.center_frame, wechatFragment).commit();
		setBehindContentView(R.layout.frame_navi); // 给滑出的slidingmenu的fragment制定layout
		naviFragment = new NaviFragment();
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.frame_navi, naviFragment).commit();
		// 设置slidingmenu的属性
		mSlidingMenu.setMode(SlidingMenu.LEFT);// 设置slidingmeni从哪侧出现
		mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);// 只有在边上才可以打开
		mSlidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);// 偏移量
		mSlidingMenu.setFadeEnabled(true);
		mSlidingMenu.setFadeDegree(0.5f);
		mSlidingMenu.setMenu(R.layout.frame_navi);
		
		

		Bundle mBundle = null;
		// 导航打开监听事件
		mSlidingMenu.setOnOpenListener(new OnOpenListener() {
			@Override
			public void onOpen() {
			}
		});
		// 导航关闭监听事件
		mSlidingMenu.setOnClosedListener(new OnClosedListener() {

			@Override
			public void onClosed() {
			}
		});
	}
	
  
     
  
	

	@Override
	public void onClick(View v) {
		
	
	}
	
	  @Override  
	    protected void onDestroy() {  
	        super.onDestroy();  
	       
	    }  
  

}
