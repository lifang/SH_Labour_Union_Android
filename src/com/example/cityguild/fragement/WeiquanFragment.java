package com.example.cityguild.fragement;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.examlpe.ices.util.TitleMainUtil;
import com.example.cityguild.AlertDialog;
import com.example.cityguild.Config;
import com.example.cityguild.R;
import com.example.cityguild.activity.LandActivity;
import com.example.cityguild.activity.LandPersonalCenterActivity;
import com.example.cityguild.activity.MainActivity;
import com.example.cityguild.activity.PostLastActivity;
import com.example.cityguild.activity.QuesttypeActivity;
import com.example.cityguild.entity.UserEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class WeiquanFragment extends Fragment implements OnClickListener {
	private View view;
	private MainActivity mActivity;
	private String flag = "1";
	private LinearLayout ll_nav;
	private RelativeLayout rl_type;
	private EditText ed_name;
	private EditText ed_phone;
	private EditText ed_questtype;
	private EditText ed_email;
	private EditText ed_zixunbt;
	private EditText ed_zxnr;
	private Button bt_qr;
	private Button bt_call;
	private String namev;
	private String phonev;
	private String email;
	private String bt;
	private String nr;
	private TextView tv_youke;
	private TextView tv_huiyuan;
	private AlertDialog ad;
	private String code="1";
	private String name="";
	private EditText adress;
	private String adressv;
	private int type=0;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if(Config.ue!=null){
			flag="1";
		}else{
			flag="2";
		}
	}

	@Override
	public void onAttach(Activity activity) {
		mActivity = (MainActivity) activity;
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.weiquan_fragment, null);
			new TitleMainUtil(mActivity, "维权登记", view).init();
			initView();

		}
		return view;
	}

	private void initView() {
		if (Config.ue == null) {
			flag = "1";
		} else {
			flag = "2";
		}
		ll_nav = (LinearLayout) view.findViewById(R.id.ll_nav);
		ll_nav.setOnClickListener(this);
		rl_type = (RelativeLayout) view.findViewById(R.id.questype);
		rl_type.setOnClickListener(this);

		ed_name = (EditText) view.findViewById(R.id.ed_name);
		ed_phone = (EditText) view.findViewById(R.id.ed_phone);
		ed_email = (EditText) view.findViewById(R.id.ed_email);
		ed_questtype = (EditText) view.findViewById(R.id.ed_questtype);
		ed_questtype.setInputType(InputType.TYPE_NULL);
		ed_questtype.setOnClickListener(this);
		ed_zixunbt = (EditText) view.findViewById(R.id.ed_zixunbt);
		ed_zxnr = (EditText) view.findViewById(R.id.ed_zxnr);
		adress = (EditText) view.findViewById(R.id.ed_adress);
		
		bt_qr = (Button) view.findViewById(R.id.bt_qr);
		bt_qr.setOnClickListener(this);
		bt_call = (Button) view.findViewById(R.id.bt_call);
		bt_call.setOnClickListener(this);
		if (flag.equals("1")) {
			ll_nav.setBackgroundResource(R.drawable.weiquannav1);
		} else if (flag.equals("2")) {
			ll_nav.setBackgroundResource(R.drawable.weiquannav2);
			if (Config.ue.getNickName() != null)
				ed_name.setText(Config.ue.getNickName());
			if (Config.ue.getPhone() != null)
				ed_phone.setText(Config.ue.getPhone());
			

		}
		tv_youke = (TextView) view.findViewById(R.id.tv_youke);
		tv_youke.setOnClickListener(this);
		tv_huiyuan = (TextView) view.findViewById(R.id.tv_huiyuan);
		tv_huiyuan.setOnClickListener(this);
	}

	private void getData() {

		RequestParams params = new RequestParams();
		params.put("username", namev);
		params.put("mobile", phonev);
		params.put("title", bt);
		params.put("code", code);
		params.put("address", adressv);
		params.put("email", email);
		params.put("content", nr);

		AsyncHttpClient c = new AsyncHttpClient();

		c.post(Config.WEIQUAN, params, new AsyncHttpResponseHandler() {

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
						Toast.makeText(mActivity,
								"登记成功!",
								Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(mActivity,
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

			}
		});

	}

	@Override
	public void onClick(View v) {
		/*if (v.getId() == R.id.tv_youke) {
			ll_nav.setBackgroundResource(R.drawable.weiquannav1);
			flag="1";
		}
		*/
		if (v.getId() == R.id.tv_huiyuan) {
			type=1;
			if(Config.ue==null){
				
				Intent i=new Intent(mActivity,LandActivity.class);
				startActivity(i);
			}else{
				ll_nav.setBackgroundResource(R.drawable.weiquannav2);
				if (Config.ue.getNickName() != null)
					ed_name.setText(Config.ue.getNickName());
				if (Config.ue.getPhone() != null)
					ed_phone.setText(Config.ue.getPhone());
				if (Config.ue.getEmail() != null)
					ed_email.setText(Config.ue.getEmail());
			}	
		}
		if (v.getId() == R.id.tv_youke) {
			type=0;
			if(Config.ue==null){
				ll_nav.setBackgroundResource(R.drawable.weiquannav1);
				ed_name.setText("");
				ed_phone.setText("");
				ed_email.setText("");
			}else{
				ll_nav.setBackgroundResource(R.drawable.weiquannav1);
				ed_name.setText("");
				ed_phone.setText("");
				ed_email.setText("");
			}	
		}
		if (v.getId() == R.id.bt_qr) {
			namev = ed_name.getText().toString().trim();
			phonev = ed_phone.getText().toString().trim();
			email = ed_email.getText().toString().trim();
			bt = ed_zixunbt.getText().toString().trim();
			nr = ed_zxnr.getText().toString().trim();
			adressv = adress.getText().toString().trim();
			if (namev.equals("")) {
				Toast.makeText(mActivity, "姓名不能为空!", 1000).show();
				return;
			}
			if (phonev.equals("")) {
				Toast.makeText(mActivity, "联系电话不能为空!", 1000).show();
				return;
			}
			if (bt.equals("")) {
				Toast.makeText(mActivity, "标题不能为空!", 1000).show();
				return;
			}
			if (nr.equals("")) {
				Toast.makeText(mActivity, "内容不能为空!", 1000).show();
				return;
			}
			getData();
		}
		if(v.getId()==R.id.bt_call){
			ad = new AlertDialog(mActivity);
			ad.setTitle("提示");
			ad.setMessage("021-12351");
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
				    Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+"021-12351"));				     
				    startActivity(intent);
				}
			});
		}
		if(v.getId()==R.id.ed_questtype){
			startActivityForResult(new Intent(mActivity,
					QuesttypeActivity.class), 0);
		}

	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (Config.ue == null) {
			flag = "1";
		} else {
			flag = "2";
		}
		if(type==0){
			ll_nav.setBackgroundResource(R.drawable.weiquannav1);
			Log.i("111","111");
		}else{
			ll_nav.setBackgroundResource(R.drawable.weiquannav2);
			Log.i("222","222");
		}
		if (flag.equals("1")) {
			//ll_nav.setBackgroundResource(R.drawable.weiquannav1);
			//ed_name.setText("");
			//ed_phone.setText("");
			//ed_email.setText("");
			
		} else if (flag.equals("2")) {
			
			ll_nav.setBackgroundResource(R.drawable.weiquannav2);
			if (Config.ue.getNickName() != null)
				ed_name.setText(Config.ue.getNickName());
			if (Config.ue.getPhone() != null)
				ed_phone.setText(Config.ue.getPhone());
			if (Config.ue.getEmail() != null)
				ed_email.setText(Config.ue.getEmail());
		}
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (data != null) {
			Bundle b = data.getExtras();		
			name = b.getString("name");		
			code = b.getString("code");
			ed_questtype.setText(name);
		
		}
	}
}
