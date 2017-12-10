package com.algha.nur;

import cn.jpush.android.api.JPushInterface;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

public class App extends Application {
	
	public static Context CONTEXT;
	public static Resources RESOURCES;
	
	public static Typeface TYPE_FACE_ALKATIP_TOR_TOM;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
         JPushInterface.init(this);
		
		CONTEXT = this;
		RESOURCES = CONTEXT.getResources();
		TYPE_FACE_ALKATIP_TOR_TOM = Typeface.createFromAsset(CONTEXT.getAssets(), "ALKATIPTT.TTF");
	}
	
	public static void startLoadingAnimation(View view) {
		Animation animation = new RotateAnimation(0, 359, Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF, 0.5F);
		animation.setInterpolator(new LinearInterpolator());
		animation.setRepeatMode(Animation.RESTART);
		animation.setRepeatCount(Animation.INFINITE);
		animation.setDuration(1000);
		view.startAnimation(animation);
	}
}
