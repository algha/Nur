package com.algha.nur.activity;

import java.util.HashMap;

import com.algha.nur.App;
import com.algha.nur.R;
import com.algha.nur.widget.PageErrorView;
import com.algha.nur.widget.PageLoadingView;
import com.algha.nur.widget.UyghurTextView;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

public class ActivityWebView extends Activity {
	private WebView webView;
	PageLoadingView loadingView;
	PageErrorView errorView;
	LinearLayout WebViewParent;
	private boolean from_receiver = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		LinearLayout view = (LinearLayout)LayoutInflater.from(this).inflate(R.layout.layout_webview, null);

		setContentView(view);
		
		from_receiver = getIntent().getExtras().getBoolean("from_receiver");
		
		WebViewParent = (LinearLayout)findViewById(R.id.WebViewParent);
		WebViewParent.setVisibility(View.GONE);
		
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
				webView.reload();
			}
		});

		String type = getIntent().getStringExtra("type");
		String url = getIntent().getStringExtra("url");

		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("AboutApp", "ئەپ ھەققىدە");
		hashMap.put("AboutCompany", "شىركەت ھەققىدە");
		hashMap.put("News", "تەپسىلاتى");

		UyghurTextView titleTextView = (UyghurTextView) findViewById(R.id.WebviewTitle);
		titleTextView.setText(hashMap.get(type));

		findViewById(R.id.back).setOnClickListener(new ClickListener());

		webView = (WebView) findViewById(R.id.WebView);
		webView.loadUrl(url);
		webView.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
			
			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				// TODO Auto-generated method stub
				super.onReceivedError(view, errorCode, description, failingUrl);
				ShowView(errorView);
			}
			
			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				super.onPageFinished(view, url);
				ShowView(WebViewParent);
			}
			
			
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// TODO Auto-generated method stub
				super.onPageStarted(view, url, favicon);
				
				ShowView(loadingView);
			}
		
		});
		
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		if (from_receiver) {
			Intent intent = new Intent(ActivityWebView.this,ActivitySplash.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
		}
		finish();
	}
	
	public void ShowView(View v){
		WebViewParent.setVisibility(View.GONE);
		errorView.setVisibility(View.GONE);
		loadingView.setVisibility(View.GONE);
		v.setVisibility(View.VISIBLE);
	}
	

	public class ClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.back:
				onBackPressed();
				finish();
				break;
			}
		}

	}
}
