package com.sugood.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.sugood.app.R;


public class WelcomeActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		
		MyThread myThread = new MyThread();
        myThread.start();
        
	}
	
	class MyThread extends Thread{
		
		@Override
		public void run() {
			super.run();
			try {
				Thread.sleep(3000);
				Intent it = new Intent();
				it.setClass(WelcomeActivity.this, MainActivity.class);
				startActivity(it);
				finish();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}

}
