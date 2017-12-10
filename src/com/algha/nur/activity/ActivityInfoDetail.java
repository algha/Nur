package com.algha.nur.activity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.apache.http.Header;
import org.json.JSONObject;

import com.algha.nur.App;
import com.algha.nur.R;
import com.algha.nur.Server;
import com.algha.nur.http.AsyncHttpClient;
import com.algha.nur.http.AsyncHttpResponseHandler;
import com.algha.nur.widget.PageErrorView;
import com.algha.nur.widget.PageLoadingView;
import com.algha.nur.widget.UyghurTextView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class ActivityInfoDetail extends Activity {
	private boolean from_receiver = false;
	private UyghurTextView Title,timeTextView,clickTextView,contentTextView;
	private PageLoadingView loadingView;
	private LinearLayout view;
	private PageErrorView errorView;
	private String newsid;
	private ScrollView scrollView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		super.onCreate(savedInstanceState);
		
		view = (LinearLayout)LayoutInflater.from(this).inflate(R.layout.activity_info_detail, null);

		setContentView(view);

		from_receiver = getIntent().getExtras().getBoolean("from_receiver");
		
		newsid = getIntent().getExtras().getString("id");
		
		Initalization();
	
		if(newsid != null){
			LoadFromServer(newsid);
		}
		
		InitalizationEvent();
	}
	
	private void Initalization(){
		
		scrollView = (ScrollView)view.findViewById(R.id.scrollView);
		
		Title = (UyghurTextView) findViewById(R.id.Title);
		timeTextView = (UyghurTextView) findViewById(R.id.Time);
		clickTextView = (UyghurTextView) findViewById(R.id.Click);
		contentTextView = (UyghurTextView)findViewById(R.id.Content);
		
		
		loadingView = new PageLoadingView(this);
		loadingView.Initalization();
		loadingView.SetText(App.RESOURCES.getString(R.string.loading));
		view.addView(loadingView);
		
		errorView = new PageErrorView(this);
		errorView.Initalization();
		errorView.SetText(App.RESOURCES.getString(R.string.errorInfo));
		view.addView(errorView);
		

		errorView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (newsid != null) {
					LoadFromServer(newsid);
				}
			}
		});
	}
	
	public void ShowView(View v){
		scrollView.setVisibility(View.GONE);
		errorView.setVisibility(View.GONE);
		loadingView.setVisibility(View.GONE);
		v.setVisibility(View.VISIBLE);
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		if (from_receiver) {
			Intent intent = new Intent(ActivityInfoDetail.this,ActivitySplash.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
		}
		finish();
	}
	
	public void  LoadFromServer(String Id) {
		ShowView(loadingView);
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(Server.GetOneInfo(Id), new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				// TODO Auto-generated method stub
				String jString = new String(responseBody);
				try {
					ShowView(scrollView);
					JSONObject object = new JSONObject(jString);
					Title.setText(object.getString("Title"));
					timeTextView.setText("ۋاقتى : "+getTimeText(object.getString("Created_at")));
					clickTextView.setText(object.getString("Click")+" قېتىم كۆرۈلدى ");
					contentTextView.setText(object.getString("Content"));
					
				} catch (Exception e) {
					// TODO: handle exception
					ShowView(errorView);
				}
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] responseBody, Throwable error) {
				// TODO Auto-generated method stub
				ShowView(errorView);
			}
		});
	}

	public void InitalizationEvent() {
		findViewById(R.id.back).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
				finish();
			}
		});
	}
	
	@SuppressWarnings("deprecation")
	private String getTimeText(String time) {
		long svr = Long.parseLong(time + "000");
		String ret = new SimpleDateFormat("M-ئاينىڭ d-كۈنى ", Locale.getDefault()).format(new Date(svr));

		Date dts = new Date(svr);
		Date now = new Date(System.currentTimeMillis());
		if (now.getYear() == dts.getYear() && now.getMonth() == dts.getMonth() && now.getDay() == dts.getDay()) {
			if (now.getHours() - dts.getHours() > 0 && now.getHours() - dts.getHours() <= 12) {
				ret = String.valueOf(now.getHours() - dts.getHours()) + " سائەت بۇرۇن";
			}
			if (now.getHours() == dts.getHours()) {

				if (now.getMinutes() - dts.getMinutes() > 0 && now.getMinutes() - dts.getMinutes() <= 59) {
					ret = String.valueOf(now.getMinutes() - dts.getMinutes()) + " مىنۇت بۇرۇن";
				}

				if (now.getMinutes() == dts.getMinutes()) {
					ret = "ھازىر";
				}
			}
		}
		return ret;
	}

}
