package com.example.cityguild.fragement;

import com.example.cityguild.R;
import com.example.cityguild.activity.MainActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NaviFragment extends Fragment implements OnClickListener {
	private MainActivity mActivity;
	private Fragment currentFragment;
	private LinearLayout shouye;
	private LinearLayout zuixin;
	private LinearLayout weiquan;
	private LinearLayout jigou;
	private View rootView;// 缓存Fragment view
	private LinearLayout gangwei;
	private LinearLayout shanghu;
	private LinearLayout jiankang;
	private LinearLayout xiangguan;
	private LinearLayout xiazai;
	private MainActivity fca;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	/**
	 * 显示左边导航栏fragment
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (rootView == null) {
			rootView = inflater.inflate(R.layout.fragment_navi, null);
		}

		init();

		return rootView;

	}
	@Override
	public void onAttach(Activity activity) {
		mActivity = (MainActivity) activity;
		super.onAttach(activity);
	}

	/**
	 * 初始化，设置点击事件
	 */
	private void init() {
		
		
		
		shouye = (LinearLayout) rootView.findViewById(R.id.ll_sshouye);
		zuixin = (LinearLayout) rootView.findViewById(R.id.ll_zuixin);
		weiquan = (LinearLayout) rootView.findViewById(R.id.ll_weiquan);
		jigou = (LinearLayout) rootView.findViewById(R.id.ll_jigou);
		shouye.setSelected(true);// 默认选中菜单
		gangwei = (LinearLayout) rootView.findViewById(R.id.ll_gangwei);
		shanghu = (LinearLayout) rootView.findViewById(R.id.ll_shanghu);
		jiankang = (LinearLayout) rootView.findViewById(R.id.ll_jiankang);
		xiangguan = (LinearLayout) rootView.findViewById(R.id.ll_xiangguan);
		xiazai = (LinearLayout) rootView.findViewById(R.id.ll_xiazai);
		shouye.setOnClickListener(this);
		zuixin.setOnClickListener(this);
		weiquan.setOnClickListener(this);
		jigou.setOnClickListener(this);
		gangwei.setOnClickListener(this);
		shanghu.setOnClickListener(this);
		jiankang.setOnClickListener(this);
		xiangguan.setOnClickListener(this);
		xiazai.setOnClickListener(this);
	}

	/**
	 * 点击导航栏切换 同时更改标题
	 */
	@Override
	public void onClick(View view) {
		
		switch (view.getId()) {
		case R.id.ll_sshouye://
			mActivity.finish();
			//OnTabSelected(1);
			break;
		case R.id.ll_zuixin://
		OnTabSelected(2);
			break;
		case R.id.ll_weiquan://
			OnTabSelected(3);
			break;
		case R.id.ll_jigou://
			OnTabSelected(4);
			break;
		case R.id.ll_gangwei://
			OnTabSelected(5);
			break;
		case R.id.ll_shanghu://
			OnTabSelected(6);
			break;
		case R.id.ll_xiangguan://
			OnTabSelected(8);
			break;
		case R.id.ll_xiazai://
			OnTabSelected(9);
			break;
		case R.id.ll_jiankang://
			OnTabSelected(10);
			break;
		}
	
	
		 mActivity.getSlidingMenu().toggle();
	}

	// 选中导航中对应的tab选项
	private void OnTabSelected(int index) {
		fca = (MainActivity) getActivity();
		fca.switchContent(index);
		
	}



}
