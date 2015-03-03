package com.examlpe.ices.util;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;

/**
 * ע����֤���ʱ��
 * 
 * @author ly
 * 
 */
public class RegisterCodeTimer extends CountDownTimer {
	private  Handler mHandler;
	public static final int IN_RUNNING = 1001;
	public static int END_RUNNING = 1002;
	public static final int IN_RUNNING1 = 1003;
	public static int END_RUNNING1 = 1004;
	int type;
	/**
	 * @param millisInFuture
	 *            // ����ʱ��ʱ��
	 * @param countDownInterval
	 *            // ���ʱ��
	 * @param handler
	 *            // ֪ͨ���ȵ�Handler
	 */
	public RegisterCodeTimer(long millisInFuture, long countDownInterval,
			Handler handler,int type) {
		super(millisInFuture, countDownInterval);
		this.type=type;
		mHandler = handler;
	}

	// ����
	@Override
	public void onFinish() {
		// TODO Auto-generated method stub
		if (mHandler != null){
			if(type==1){
				mHandler.obtainMessage(END_RUNNING1, "��ȡ��֤��").sendToTarget();
			}else{
				mHandler.obtainMessage(END_RUNNING, "��ȡ��֤��").sendToTarget();
			}
		}
			
	}

	@Override
	public void onTick(long millisUntilFinished) {
		if (mHandler != null){
			if(type==1){
				mHandler.obtainMessage(IN_RUNNING1,
						(millisUntilFinished / 1000) + "s ���ط�").sendToTarget();
				
			}else{
			
				mHandler.obtainMessage(IN_RUNNING,
						(millisUntilFinished / 1000) + "s ���ط�").sendToTarget();	
			}
		}
		
	}

}

