package com.examlpe.ices.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.Html.ImageGetter;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.widget.TextView;

public class URLImageParser implements ImageGetter {

	Context c;
	TextView tv_image;
	int width;
	int height;
	public URLImageParser(TextView t, Context c) {
		this.tv_image = t;
		this.c = c;
		Display display = ((Activity) c).getWindowManager() 
				.getDefaultDisplay(); 
				width = display.getWidth(); 
				height = (int) (width * 3 / 4); 
	}

	@Override
	public Drawable getDrawable(String source) {
		// TODO Auto-generated method stub
		URLDrawable urlDrawable = new URLDrawable(c);
		ImageGetterAsyncTask asyncTask = new ImageGetterAsyncTask(urlDrawable);
		asyncTask.execute(source);
		return urlDrawable;
	}

	public class ImageGetterAsyncTask extends AsyncTask<String, Void, Drawable> {
		URLDrawable urlDrawable;

		public ImageGetterAsyncTask(URLDrawable d) {
			this.urlDrawable = d;
		}

		@Override
		protected void onPostExecute(Drawable result) {
			if (result != null) {
//				urlDrawable.setBounds(0, 0, 0 + result.getIntrinsicWidth(),
//						0 + result.getIntrinsicHeight());
 				//urlDrawable.setBounds(0, 0, width, height);
				urlDrawable.drawable = result;
				URLImageParser.this.tv_image.invalidate();
			}
		}

		@Override
		protected Drawable doInBackground(String... params) {
			// TODO Auto-generated method stub
			String source = params[0];// ͼƬURL
			return fetchDrawable(source);
		}

		// ��ȡURL��Drawable����
		public Drawable fetchDrawable(String urlString) {
			try {
				InputStream is = fetch(urlString);
				Drawable drawable = Drawable.createFromStream(is, "src");
				//drawable.setBounds(50, 150, 428,
					//	285);

				drawable.setBounds(0, -width/5, width, height/5*3-50); 
				Log.i(width+"",height+"");
				return drawable;
			} catch (Exception e) {
				return null;
			}
		}

		private InputStream fetch(String urlString)
				throws MalformedURLException, IOException {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet request = new HttpGet(urlString);
			HttpResponse response = httpClient.execute(request);
			return response.getEntity().getContent();
		}

	}

}
