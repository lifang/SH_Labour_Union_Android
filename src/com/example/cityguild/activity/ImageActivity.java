package com.example.cityguild.activity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.examlpe.ices.util.ImageCacheUtil;
import com.example.cityguild.BaseActivity;
import com.example.cityguild.R;

public class ImageActivity extends BaseActivity {
	private ImageView imageView;
	private ProgressBar pb;
	private String path;
	private int width;
	private int height;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.imageactivity);
		path = getIntent().getStringExtra("path");
		Display display =getWindowManager()
				.getDefaultDisplay();
		width = display.getWidth();
		height = display.hashCode();
		initView();
		new ImageAsynTask().execute();
	}

	private void initView() {
		pb = (ProgressBar)findViewById(R.id.progressBar);
		imageView = (ImageView)findViewById(R.id.im);
		imageView.setOnClickListener(this);
		pb.setVisibility(View.VISIBLE);
		//ImageCacheUtil.IMAGE_CACHE.get(path,imageView);
	
	}
	 private class ImageAsynTask extends AsyncTask<Void, Void, Drawable> {

	        @Override
	        protected Drawable doInBackground (Void... params) {
	            ;
	            return loadImages(path);
	        }
	        
	        @Override
	        protected void onPostExecute (Drawable result) {
	            super.onPostExecute(result);
	            if(result==null)
	            	return;
	            int imgw=result.getIntrinsicWidth();
	            int imgh=result.getIntrinsicHeight();
	       	 Log.i(result.getIntrinsicWidth()+"",result.getIntrinsicHeight()+"");
	            pb.setVisibility(View.GONE);
	            if(imgw>imgh){
	            	 LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0,0);
	            	 layoutParams.width=width;
	            	 layoutParams.height=imgh*(width/imgw);
	            	 imageView.setLayoutParams(layoutParams);
	            	 Log.i( layoutParams.width+"",layoutParams.height+"");
	            }else{
	            	 LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0,0);
	            	layoutParams.width=imgw*(height/imgh);
	            	 layoutParams.height=height;
	            	 imageView.setLayoutParams(layoutParams);
	            }
	            imageView.setImageDrawable(result);
	        }
	        
	        @Override
	        protected void onPreExecute () {
	            super.onPreExecute();
	          
	        }
	    }
	    
	    @Override
	    protected void onDestroy () {
	        super.onDestroy();
	      
	    }
	    
	    public Drawable loadImages(String url) { 
	        try {
	            return Drawable.createFromStream((InputStream)(new URL(url)).openStream(), "test");
	        }
	        catch (IOException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		if(v.getId()==R.id.im){
			this.finish();
		}
	}

}
