package com.examlpe.ices.util;

import com.examlpe.ices.util.ClientUpdate;

import android.app.Activity;
import android.os.Bundle;
/***
 * 
*    
* �����ƣ�CancleUpdate   
* ��������   ȡ������ҳ�� ������APPȻ����ʧ����ʾԭ��ͣ����ҳ��
* �����ˣ� ljp 
* ����ʱ�䣺2014-12-8 ����3:15:26   
* @version    
*
 */
 

public class CancleUpdate extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
		String str = getIntent().getStringExtra("quxiao");
		if(str != null && str.equals("ȡ��")){
			ClientUpdate.mUpdate.cancel(true);
		}
		
		finish();
		super.onCreate(savedInstanceState);
	}

}
