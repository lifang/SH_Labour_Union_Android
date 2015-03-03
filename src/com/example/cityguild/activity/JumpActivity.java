package com.example.cityguild.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.cityguild.BaseActivity;
import com.example.cityguild.R;

public class JumpActivity extends BaseActivity {
	private LinearLayout ll_jump;
	private LinearLayout ll_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jktiaozhuan);
		ll_jump = (LinearLayout) findViewById(R.id.xizai);
		ll_jump.setOnClickListener(this);
		ll_back = (LinearLayout) findViewById(R.id.back);
		ll_back.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		if (v.getId() == R.id.back) {
			this.finish();
		}
		if (v.getId() == R.id.xizai) {
			final Uri uri = Uri
					.parse("http://android.myapp.com/myapp/detail.htm?apkName=cn.com.besttone.health");
			Intent intent = new Intent(Intent.ACTION_VIEW, uri);
			startActivity(intent);

		}
	}

}
