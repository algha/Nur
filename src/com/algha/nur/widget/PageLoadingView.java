package com.algha.nur.widget;

import com.algha.nur.App;
import com.algha.nur.R;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class PageLoadingView extends LinearLayout {
	public ImageView imageView;
	public UyghurTextView textView;
	private Context pageContext;
	public PageLoadingView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		pageContext = context;
	}
	
	public void Initalization(){
		SetParams();
		SetLoadingImage();
	}

	public void SetParams(){
		
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		setLayoutParams(params);
		
		this.setOrientation(VERTICAL);
		this.setGravity(Gravity.CENTER|Gravity.CENTER);
		this.setBackgroundColor(Color.parseColor("#ffffff"));
		
	}

	public void SetText(String string){
		
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.topMargin = 20;
		
		textView = new UyghurTextView(pageContext);
		textView.setText(string);
		textView.setGravity(Gravity.CENTER|Gravity.CENTER);
		textView.setLayoutParams(params);
		
		addView(textView);
	}
	
	public void SetLoadingImage() {
		
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		
		imageView = new ImageView(pageContext);
		imageView.setImageResource(R.drawable.icon_loading_big);
		imageView.setLayoutParams(params);
		addView(imageView);
		
		App.startLoadingAnimation(imageView);
		
	}

}
