package com.example.cityguild.activity;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.examlpe.ices.util.ImageCacheUtil;
import com.examlpe.ices.util.RegHtml;
import com.examlpe.ices.util.StringUtil;
import com.examlpe.ices.util.TitleMenuUtil;
import com.examlpe.ices.util.URLImageParser;
import com.example.cityguild.BaseActivity;
import com.example.cityguild.Config;
import com.example.cityguild.MyApplication;
import com.example.cityguild.R;
import com.example.cityguild.entity.AboutEntity;
import com.example.cityguild.entity.PostEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class AboutDetailActivity extends BaseActivity {
	private String id;
	private String type;
	private AboutEntity pe = new AboutEntity();
	private TextView title;
	private TextView content;
	private Button top;
	private ScrollView sc;
	private ImageView im;
	private WebView wb;
	private int width;
	private int height;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.aboutdetail);
		Display display = getWindowManager()
				.getDefaultDisplay();
		width = display.getWidth();
		height = display.getHeight();
		id = getIntent().getStringExtra("id");
		type = getIntent().getStringExtra("type");
		initView();
		getData();

	}

	private void initView() {

		
		title = (TextView) findViewById(R.id.title);
		content = (TextView) findViewById(R.id.content);
		sc = (ScrollView)findViewById(R.id.scroll);
		top = (Button) findViewById(R.id.top);
		im = (ImageView)findViewById(R.id.im);
		top.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				top.post(new Runnable() {
				    
				   @Override
				   public void run() {
				    // TODO Auto-generated method stub
				    sc.fullScroll(ScrollView.FOCUS_UP);
				   }
				  });
				
			}
		});

	}

	private void getData() {
		String url = "";
		if (type.equals("1")) {
			url = Config.ABOUTFGDT;
		} else {
			url = Config.ABOUTHZDT;
		}
		RequestParams params = new RequestParams();
		params.put("id", id);
		// params.setUseJsonStreamer(true);
		AsyncHttpClient c = new AsyncHttpClient();

		c.post(url, params, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] responseBody) {
				// TODO Auto-generated method stub
				String userMsg = new String(responseBody).toString();

				Log.i("ljp", userMsg);
				Gson gson = new Gson();
				// EventEntity
				JSONObject jsonobject = null;
				int code = 0;
				try {
					jsonobject = new JSONObject(userMsg);

					code = jsonobject.getInt("code");

					if (code == -2) {

					} else if (code == 1) {
						
						pe = gson.fromJson(jsonobject.getString("result"),
								new TypeToken<AboutEntity>() {
								}.getType());
						if (type.equals("1")) {
							
							//title.setText(pe.getTitle());
							 //LinearLayout.LayoutParams lParams = (LinearLayout.LayoutParams)wb.getLayoutParams();
						     //wb.getLayoutParams();
						        //lParams.width=width;
						        //lParams.height=height;
						        //wb.setLayoutParams(lParams);
								wb = (WebView)findViewById(R.id.webview);
								wb.setVisibility(View.GONE);
								wb.setBackgroundColor(2); // 设置背景色
								wb.getBackground().setAlpha(0); // 设置填充透明度 范围：0-255
							WebSettings webSettings= wb.getSettings(); // webView: 类WebView的实例 
							webSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);  //就是这句
							String str=RegHtml.replaceUrl(pe.getContent());
							wb.loadDataWithBaseURL(null,str , "text/html", "utf-8", null);
							Log.i("ccc",RegHtml.replaceUrl2(str));
							//wb.setVisibility(View.VISIBLE);  
							//String str=RegHtml.replaceUrl(pe.getContent());					
							 //  content.setMovementMethod(LinkMovementMethod.getInstance());//加这句才能让里面的超链接生效
							 //  URLImageParser ReviewImgGetter = new URLImageParser(content,AboutDetailActivity.this);//实例化URLImageGetter类
							 //   content.setText(Html.fromHtml(str,ReviewImgGetter,null));
							wb.setVisibility(View.VISIBLE);
							new TitleMenuUtil(AboutDetailActivity.this, pe
									.getTitle()).init();
						}else{
							//String str=RegHtml.replaceUrl1(pe.getImg());	
							String str=pe.getImg();
							  // content.setMovementMethod(LinkMovementMethod.getInstance());//加这句才能让里面的超链接生效
							   //URLImageParser ReviewImgGetter = new URLImageParser(content,AboutDetailActivity.this);//实例化URLImageGetter类
							    //content.setText(Html.fromHtml(str,ReviewImgGetter,null));
							title.setText(pe.getName());
							ImageCacheUtil.IMAGE_CACHE.get(str,im);
							new TitleMenuUtil(AboutDetailActivity.this, pe
									.getName()).init();
							
						}
						LinearLayout ll_load = (LinearLayout)findViewById(R.id.load);
						ll_load.setVisibility(View.GONE);
					} else {
						Toast.makeText(AboutDetailActivity.this,
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
				// TODO Auto-generated method stub

			}
		});

	}
}
