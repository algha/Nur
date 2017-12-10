package com.algha.nur.receiver;

import org.json.JSONObject;
import com.algha.nur.activity.ActivityInfoDetail;
import com.algha.nur.activity.ActivityWebView;
import cn.jpush.android.api.JPushInterface;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class JPushReceiver extends BroadcastReceiver {
	static String TAG = "Tor";
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub

		if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {

		} else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
			
			// 自定义消息不会展示在通知栏，完全要开发者写代码去处理
		} else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
			
			// 在这里可以做些统计，或者做些其他工作
		} else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
		
			Bundle bundle = intent.getExtras();
			String jsonsString = bundle.getString(JPushInterface.EXTRA_EXTRA);
			try {
				
				JSONObject object = new JSONObject(jsonsString);
				
				try {
					if (object.getString("id") != null && object.getString("id").equals("") == false) {
						Intent mIntent = new Intent(context,ActivityInfoDetail.class);
						mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						mIntent.putExtra("from_receiver", true);
						mIntent.putExtra("id", object.getString("id"));
						context.startActivity(mIntent);
					 }
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				try {
					if (object.getString("url") != null && object.getString("url").equals("") == false) {

						Intent mIntent = new Intent(context,ActivityWebView.class);
						mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						mIntent.putExtra("from_receiver", true);
						mIntent.putExtra("type", "News");
						mIntent.putExtra("url", object.getString("url"));
						context.startActivity(mIntent);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		} else {

		}
	}
}
