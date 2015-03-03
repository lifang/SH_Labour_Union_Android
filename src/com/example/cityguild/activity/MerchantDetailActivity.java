package com.example.cityguild.activity;


import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cityguild.AlertDialog;
import com.example.cityguild.BaseActivity;
import com.example.cityguild.Config;
import com.example.cityguild.R;
import com.example.cityguild.activity.MerchantsMap.MyLocationListenner;
import com.example.cityguild.adapter.MerchantsListAdapter;

import com.example.cityguild.entity.MerchantsEntity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class MerchantDetailActivity extends BaseActivity implements OnPageChangeListener{
	
	private ListView shList;
	private MerchantsListAdapter mladapter;
	private RelativeLayout rl_other;
	private ImageView bt_left;
	private TextView bt_right;
	
	private List<String> imageViewList;  
    private LinearLayout llPoints;     
    private int previousSelectPosition = 0;  
    private ViewPager mainvp;  
    private boolean isLoop = true;
	private String id;
	private TextView name;
	private TextView tel;
	private TextView adress;
	private TextView jianjie;
	private RelativeLayout ll_call;
	private AlertDialog ad;  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.merchantdetail);
		id = getIntent().getStringExtra("id");
		initView();
		
		getData();
	}
	private void getData() {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		AsyncHttpClient client = new AsyncHttpClient(); // 创建异步请求的客户端对象
		RequestParams params = new RequestParams();
		params.put("id", id); 
		//params.setUseJsonStreamer(true);
		
		AsyncHttpClient c = new AsyncHttpClient();
		
		c.post(Config.MERCHANTSDATIL,params,new AsyncHttpResponseHandler() {

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
						MerchantsEntity nde = gson.fromJson(jsonobject.getString("result"),
	 		 					new TypeToken<MerchantsEntity>() {
	 							}.getType());
						name.setText(nde.getName());
						tel.setText(nde.getTel());
						adress.setText(nde.getAddr());
						if(nde.getAbout()==null||nde.getAbout().equals("")){
							jianjie.setText("暂无简介");
						}else{
							jianjie.setText("    "+nde.getAbout());
						}
						
					
					}else{
						Toast.makeText(MerchantDetailActivity.this, jsonobject.getString("message"),
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
				Log.i("sb","sb");
				
			}
		});

	
	}
	
	private void initView() {
		
	rl_other = (RelativeLayout)findViewById(R.id.othermer);
	rl_other.setOnClickListener(this);
	bt_left = (ImageView)findViewById(R.id.titlebar_img_left);
	bt_left.setOnClickListener(this);
	bt_right = (TextView)findViewById(R.id.titlebar_img_right);
	bt_right.setOnClickListener(this);
	
	name = (TextView)findViewById(R.id.name);
	tel = (TextView)findViewById(R.id.tel);
	adress = (TextView)findViewById(R.id.adress);
	jianjie = (TextView)findViewById(R.id.jianjie);
	ll_call = (RelativeLayout)findViewById(R.id.rl_call);
	ll_call.setOnClickListener(this);
	
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		if(v.getId()==R.id.othermer){
			Intent i=new Intent(this,MerchantListActivity.class);
			i.putExtra("id", id);
			startActivity(i);
		}
		if(v.getId()==R.id.titlebar_img_left){
			MerchantDetailActivity.this.finish();
		}
		if(v.getId()==R.id.titlebar_img_right){
			Intent i=new Intent(this,MerchantListActivity.class);
			i.putExtra("id", id);
			startActivity(i);
		}
		if(v.getId()==R.id.rl_call){
			
			ad = new AlertDialog(this);
			ad.setTitle("提示");
			ad.setMessage(tel.getText().toString());
			ad.setPositiveButton("取消", new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					ad.dismiss();
					
				}
			});
			ad.setNegativeButton("确定", new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					ad.dismiss();
				    String strMobile = tel.getText().toString();
				    Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+strMobile));				     
				    startActivity(intent);
				}
			});
		
		}
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
        llPoints.getChildAt(position % imageViewList.size()).setBackgroundResource(R.drawable.indicator_focused);  
        previousSelectPosition = position  % imageViewList.size();  
		
	}

}
