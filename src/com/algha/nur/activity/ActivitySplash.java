package com.algha.nur.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import cn.jpush.android.api.JPushInterface;

import com.algha.nur.R;
import com.algha.nur.activity.ActivityMain;

public class ActivitySplash extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_splash);
		
		View v = (View)findViewById(R.id.splash);
		v.setBackgroundResource(R.drawable.splash);
		
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ActivitySplash.this,ActivityMain.class);
				startActivity(intent);
				finish();
			}
		}, 2000);
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		JPushInterface.onResume(this);
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		JPushInterface.onPause(this);
	}
	
	public void onBackPressed() {
		return;
	}
}
