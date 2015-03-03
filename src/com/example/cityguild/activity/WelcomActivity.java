package com.example.cityguild.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.TextView;

import com.example.cityguild.BaseActivity;
import com.example.cityguild.R;

public class WelcomActivity extends BaseActivity {
	private TextView tv1;
	private TextView tv2;
	private TextView tv3;
	private AlphaAnimation aa1;
	private AlphaAnimation aa2;
	private AlphaAnimation aa3;
	private boolean isfrist=true;
	private AlphaAnimation aa4;
	private AlphaAnimation aa5;
	private AlphaAnimation aa6;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcom);
		initView();
	}
	
	private void initView() {
	tv1 = (TextView)findViewById(R.id.tv1);
	tv2 = (TextView)findViewById(R.id.tv2);
	tv3 = (TextView)findViewById(R.id.tv3);
	//1
	   AlphaAnimation aa1 = new AlphaAnimation(1.0f, 0.0f);//����һ��AlphaAnimation ���󣬽����1->0
	   aa1.setDuration(500);//���ó���ʱ��
	   aa1 = new AlphaAnimation(1.0f, 0.0f);
	   aa1.setDuration(500);//���ó���ʱ��
	   //aa1.setRepeatCount(0);
	  
	   aa2 = new AlphaAnimation(1.0f, 0.0f);
	   aa2.setDuration(500);//���ó���ʱ��
	  // aa2.setRepeatCount(0);

	   aa3 = new AlphaAnimation(1.0f, 0.0f);
	   aa3.setDuration(500);
	   
	  aa4 = new AlphaAnimation(0.0f, 1.0f);
	    aa4.setDuration(500);
	   
	   aa5 = new AlphaAnimation(0.0f, 1.0f);
	   aa5.setDuration(500);
	   
	   aa6 = new AlphaAnimation(0.0f, 1.0f);
	    aa6.setDuration(500);
	   
	   aa1.setFillAfter(true);//�������View����״̬�������Ǵ�1->0,������������ʧ״̬������ǿ����������View�ģ�
	    tv1.startAnimation(aa1);//��������
		   aa1.setAnimationListener(new AnimationListener() {
				
				@Override
				public void onAnimationStart(Animation aa) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationRepeat(Animation aa) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationEnd(Animation aa) {
					Log.i("��һ�н���","��һ�н���");
					aa2.setFillAfter(true);
					tv2.startAnimation(aa2);
					
					
				}
			});
		   aa2.setAnimationListener(new AnimationListener() {
				
				@Override
				public void onAnimationStart(Animation aa) {
					Log.i("�ڶ��п�ʼ","�ڶ��п�ʼ");
					
				}
				
				@Override
				public void onAnimationRepeat(Animation aa) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationEnd(Animation aa) {
					Log.i("�ڶ��н���","�ڶ��н���");
					aa3.setFillAfter(true);
					tv3.startAnimation(aa3);
					
					
				}
			});
		   aa3.setAnimationListener(new AnimationListener() {
				
			@Override
			public void onAnimationStart(Animation aa) {
				Log.i("�����п�ʼ","�����п�ʼ");
				
			}
			
			@Override
			public void onAnimationRepeat(Animation aa) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation aa) {
				Log.i("�����н���","�����н���");
				aa4.setFillAfter(true);
				tv1.startAnimation(aa4);
			}
		});
		   aa4.setAnimationListener(new AnimationListener() {
				
				@Override
				public void onAnimationStart(Animation aa) {
					
					
				}
				
				@Override
				public void onAnimationRepeat(Animation aa) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationEnd(Animation aa) {
					
					aa5.setFillAfter(true);
					tv2.startAnimation(aa5);
				}
			});
		   aa5.setAnimationListener(new AnimationListener() {
				
				@Override
				public void onAnimationStart(Animation aa) {
					
					
				}
				
				@Override
				public void onAnimationRepeat(Animation aa) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationEnd(Animation aa) {
				
					aa6.setFillAfter(true);
					tv3.startAnimation(aa6);
				}
			});
		   aa6.setAnimationListener(new AnimationListener() {
				
				@Override
				public void onAnimationStart(Animation aa) {
					
					
				}
				
				@Override
				public void onAnimationRepeat(Animation aa) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationEnd(Animation aa) {
					WelcomActivity.this.finish();
					startActivity(new Intent(WelcomActivity.this,MainconActivity.class));
				}
			});
	}

	@Override
	public void onClick(View v) {
		
		super.onClick(v);
	}

}
