package com.example.cityguild.fragement;

import java.util.ArrayList;
import java.util.List;

import com.examlpe.ices.util.StringUtil;
import com.examlpe.ices.util.TitleMainUtil;
import com.examlpe.ices.util.TitleMenuUtil;
import com.example.cityguild.R;
import com.example.cityguild.activity.MainActivity;
import com.example.cityguild.activity.PostFristActivity;
import com.example.cityguild.activity.PostLastActivity;
import com.example.cityguild.activity.PostRecordActivity;
import com.example.cityguild.activity.PostSearchActivity;
import com.example.cityguild.activity.PostTuijianActivity;
import com.example.cityguild.activity.PostType;
import com.example.cityguild.entity.SearchEntity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class PostFragement extends Fragment {
	private MainActivity mActivity;
	private View view;
	private Button silde;
	private Button myInfo;

	private RelativeLayout leibie;
	private RelativeLayout shouxuan;
	private RelativeLayout cixuan;
	private Button search;
	private RelativeLayout tuijian;
	private EditText ed;
	private TextView typetv;
	private TextView shouxuantv;
	private TextView cixuantv;
	private SharedPreferences mySharedPreferences;

	private TextView tv_jilu1;
	private TextView tv_jilu2;
	private TextView tv_jilu3;
	private RelativeLayout rl_jilu;
	private LinearLayout jilu;
	private RelativeLayout jilu1;
	private RelativeLayout jilu2;
	private RelativeLayout jilu3;

	private List<SearchEntity> slist = new ArrayList<SearchEntity>();
	private String stext;
	private String[] records;
	private SearchEntity serchet;
	private String record;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}

	private void initView() {
		jilu = (LinearLayout) view.findViewById(R.id.serchjilu);
		jilu1 = (RelativeLayout) view.findViewById(R.id.jilu1);
		jilu2 = (RelativeLayout) view.findViewById(R.id.jilu2);
		jilu3 = (RelativeLayout) view.findViewById(R.id.jilu3);

		jilu1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(mActivity, PostSearchActivity.class);
				
				String str[]=records[records.length - 1].split("\\+");
				i.putExtra("type", str[0]);
				i.putExtra("shouxuan", str[1]);
				i.putExtra("cixuan", str[2]);
				if(str.length==4)
				i.putExtra("text", str[3]);
				startActivity(i);
				
			}
		});
		jilu2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(mActivity, PostSearchActivity.class);
				
				String str[]=records[records.length - 2].split("\\+");
				i.putExtra("type", str[0]);
				i.putExtra("shouxuan", str[1]);
				i.putExtra("cixuan", str[2]);
				if(str.length==4)
				i.putExtra("text", str[3]);
				startActivity(i);

			}
		});
		jilu3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
	Intent i = new Intent(mActivity, PostSearchActivity.class);
				
				String str[]=records[records.length - 3].split("\\+");
				i.putExtra("type", str[0]);
				i.putExtra("shouxuan", str[1]);
				i.putExtra("cixuan", str[2]);
				if(str.length==4)
				i.putExtra("text", str[3]);
				startActivity(i);
			}
		});

		tv_jilu1 = (TextView) view.findViewById(R.id.tv_jilu1);
		tv_jilu2 = (TextView) view.findViewById(R.id.tv_jilu2);
		tv_jilu3 = (TextView) view.findViewById(R.id.tv_jilu3);

		typetv = (TextView) view.findViewById(R.id.typetv);
		shouxuantv = (TextView) view.findViewById(R.id.shouxuantv);
		cixuantv = (TextView) view.findViewById(R.id.cixuantv);

		ed = (EditText) view.findViewById(R.id.edsc);

		leibie = (RelativeLayout) view.findViewById(R.id.leibie);
		shouxuan = (RelativeLayout) view.findViewById(R.id.shouxuanqu);
		cixuan = (RelativeLayout) view.findViewById(R.id.cixuanqu);
		search = (Button) view.findViewById(R.id.postsearch);
		rl_jilu = (RelativeLayout) view.findViewById(R.id.serchjl);
		tuijian = (RelativeLayout) view.findViewById(R.id.tuijian);

		leibie.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				startActivityForResult(new Intent(mActivity, PostType.class), 0);

			}
		});
		shouxuan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivityForResult(new Intent(mActivity,
						PostFristActivity.class), 0);

			}
		});

		cixuan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivityForResult(new Intent(mActivity,
						PostLastActivity.class), 0);

			}
		});

		search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
			/*	if(ed.getText().toString().equals("")){
					Toast.makeText(mActivity, "请输入关键字!", 1000).show();
					return;
				}*/
				serchRecord();
				Intent i=new Intent(mActivity, PostSearchActivity.class);
				i.putExtra("type", typetv.getText());
				i.putExtra("shouxuan", shouxuantv.getText());
				i.putExtra("cixuan", cixuantv.getText());
				i.putExtra("text", ed.getText().toString().trim());
				startActivity(i);
				
				initRecord();
			}

		});
		rl_jilu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(mActivity, PostRecordActivity.class));

			}
		});
		tuijian.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(mActivity, PostTuijianActivity.class));

			}
		});
	}

	public void serchRecord() {
		//String[] flag={"0","0","0"};
		String record = "";
		stext = ed.getText().toString().trim();
		//if (!typetv.getText().toString().trim().equals("行业类别")) {
			record = typetv.getText().toString().trim() + "+";
			//flag[0]="1";
		//}

		//if (!shouxuantv.getText().toString().trim().equals("首选工作区域")) {
			record = record + shouxuantv.getText().toString().trim() + "+";
			//flag[1]="1";
		//} 

		//if (!cixuantv.getText().toString().trim().equals("次选工作区域")) {
			record = record + cixuantv.getText().toString().trim()+ "+" ;
			//flag[2]="1";
		//} 
		if(stext.equals("")){
			record = record ;
		}else{
			record = record +stext;
		}
		
		if (!record.equals("")) {
			Log.i("record",record);
			mySharedPreferences = mActivity.getSharedPreferences("test11",
					Activity.MODE_PRIVATE);
			SharedPreferences.Editor editor = mySharedPreferences.edit();
			String recordlast = mySharedPreferences.getString("record7", "");
			int index = 0;
			if(recordlast.contains(record)){
				records = recordlast.split("@");
				String[] ary = new String[records.length-1];
				for(int i=0;i<records.length;i++){
					if(records[i].equals(record)){
						index=i;
						break;
					}
				}
				System.arraycopy(records, 0, ary, 0, index);
				System.arraycopy(records, index+1, ary, index, ary.length-index);
				recordlast="";
				for(int i=0;i<ary.length;i++){
					if(recordlast!=""){
						recordlast=recordlast+"@"+ary[i];
					}else{
						recordlast=ary[i];
					}
					
				}
			}
				
			
			
			if (!recordlast.equals("")) {
				/*for (int i = 0; i < records.length; i++) {
					if(record.equals(i)){
						return;
					}
				}*/
				if(record.equals("行业类别+首选工作区域+次选工作区域+"))
					return;
				record = recordlast + "@" + record;
				editor.putString("record7", record);

			} else {
				if(record.equals("行业类别+首选工作区域+次选工作区域+"))
					return;
				editor.putString("record7", record);

			}

			editor.commit();
		}
	}

	public void initRecord() {
		mySharedPreferences = mActivity.getSharedPreferences("test11",
				Activity.MODE_PRIVATE);
		record = mySharedPreferences.getString("record7", "");
		if (!record.equals("")) {

			records = record.split("@");
			Log.i("le", String.valueOf(records.length));
			jilu.setVisibility(View.VISIBLE);
			for (int i = 0; i < records.length; i++) {
				//Log.i("le", String.valueOf(records[i]));
			}
			if (records.length == 1) {
				
				tv_jilu1.setText(StringUtil.replace(records[records.length-1]));
				jilu1.setVisibility(View.VISIBLE);
				Log.i("cccc",StringUtil.replace(records[records.length-1]));
				jilu2.setVisibility(View.GONE);
				jilu3.setVisibility(View.GONE);

			} else if (records.length == 2) {
				tv_jilu1.setText(StringUtil.replace(records[records.length-1]));
				tv_jilu2.setText(StringUtil.replace(records[records.length-2]));
				jilu2.setVisibility(View.VISIBLE);
				jilu3.setVisibility(View.GONE);

			} else if (records.length == 3) {
				tv_jilu1.setText(StringUtil.replace(records[records.length-1]));
				tv_jilu2.setText(StringUtil.replace(records[records.length-2]));
				tv_jilu3.setText(StringUtil.replace(records[records.length-3]));
				jilu2.setVisibility(View.VISIBLE);
				jilu3.setVisibility(View.VISIBLE);
			} else {
				tv_jilu1.setText(StringUtil.replace(records[records.length - 1]));
				tv_jilu2.setText(StringUtil.replace(records[records.length - 2]));
				tv_jilu3.setText(StringUtil.replace(records[records.length - 3]));
				jilu1.setVisibility(View.VISIBLE);
				jilu2.setVisibility(View.VISIBLE);
				jilu3.setVisibility(View.VISIBLE);

			}
		} else {
			jilu.setVisibility(View.GONE);
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
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.post_main, null);
		new TitleMainUtil(mActivity, "岗位查询", view).init();
		initView();
		initRecord();
		return view;
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (data != null) {
			Bundle b = data.getExtras();
			
			String type = b.getString("type");
			
			String shouxuan = b.getString("shouxuan");
			String cixuan = b.getString("cixuan");
			if (type != null)
				typetv.setText(type);
			
				
			if (shouxuan != null)
				shouxuantv.setText(shouxuan);
			
			if (cixuan != null)

				cixuantv.setText(cixuan);
		}
	}
}