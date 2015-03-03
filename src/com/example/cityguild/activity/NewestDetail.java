package com.example.cityguild.activity;

import java.net.URL;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.examlpe.ices.util.RegHtml;

import com.examlpe.ices.util.URLImageParser;

import com.example.cityguild.BaseActivity;
import com.example.cityguild.Config;
import com.example.cityguild.R;
import com.example.cityguild.entity.NewestDetailEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class NewestDetail extends BaseActivity {
	private LinearLayout ll;
	private Button bt_top;
	ScrollView scl;
	private TextView title;
	private TextView time;
	private TextView content;
	private String id;
	private LinearLayout load;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zhuixin_detail);
		id = getIntent().getStringExtra("id");
		//Log.i("id",id);
		initView();
		getData();
	}
	private void initView() {
		ll = (LinearLayout)findViewById(R.id.fanhui);
		ll.setOnClickListener(this);
		bt_top = (Button)findViewById(R.id.bt_top);
		bt_top.setOnClickListener(this);
		scl=(ScrollView)findViewById(R.id.scl);
		
		title = (TextView)findViewById(R.id.title);
		time = (TextView)findViewById(R.id.time);
		content = (TextView)findViewById(R.id.content);
		load = (LinearLayout)findViewById(R.id.load);
		load.setVisibility(View.VISIBLE);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		if(v.getId()==R.id.fanhui){
			this.finish();
		}
		if(v.getId()==R.id.bt_top){
			  scl.post(new Runnable() {
				    
				   @Override
				   public void run() {
				    // TODO Auto-generated method stub
				    scl.fullScroll(ScrollView.FOCUS_UP);
				   }
				  });
		}
	}
	

	private void getData() {
	
		RequestParams params = new RequestParams();
		params.put("id", id); 
		//params.setUseJsonStreamer(true);
		
		AsyncHttpClient c = new AsyncHttpClient();
		
		c.post(Config.NEWESTDETAIL,params,new AsyncHttpResponseHandler() {

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
						NewestDetailEntity nde = gson.fromJson(jsonobject.getString("result"),
	 		 					new TypeToken<NewestDetailEntity>() {
	 							}.getType());
						title.setText(nde.getTitle());
						time.setText(nde.getTime());
						String str=RegHtml.replaceUrl(nde.getContent());					
						   content.setMovementMethod(LinkMovementMethod.getInstance());//加这句才能让里面的超链接生效
						   URLImageParser ReviewImgGetter = new URLImageParser(content,NewestDetail.this);//实例化URLImageGetter类
						    content.setText(Html.fromHtml(str,ReviewImgGetter,null));
						    load.setVisibility(View.GONE);
						//Log.i("----",str);
						/*ImageGetter im=new ImageGetter() {  
							  
				            @Override  
				            public Drawable getDrawable(String source) {  
				            	 Drawable drawable=null;
				            	    URL url;
				            	    try {
				            	        url = new URL(source);
				            	        drawable = Drawable.createFromStream(url.openStream(), "");
				            	    } catch (Exception e) {
				            	        e.printStackTrace();
				            	        return null;
				            	    }
				            	    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());            
				            	    return drawable; 
				            }  
				        };*/
						//CharSequence charSequence=Html.fromHtml(str,im, null);
						//content.setText(charSequence);
						//该语句在设置后必加，不然没有任何效果
						//content.setMovementMethod(LinkMovementMethod.getInstance());
						//content.setText(nde.getContent());
					}else{
						Toast.makeText(NewestDetail.this, jsonobject.getString("message"),
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
}
