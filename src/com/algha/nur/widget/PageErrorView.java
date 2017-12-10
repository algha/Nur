package com.algha.nur.widget;

import com.algha.nur.R;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class PageErrorView extends LinearLayout{
	
	public ImageView imageView;
	public UyghurTextView textView;
	private Context pageContext;
	
	public PageErrorView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		
		pageContext = context;
		
	}
	
	public void Initalization(){
		SetParams();
		SetImage();
	}

	public void SetParams(){
		
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		setLayoutParams(params);
		
		this.setOrientation(VERTICAL);
		this.setVisibility(View.GONE);
		this.setGravity(Gravity.CENTER|Gravity.CENTER);
		this.setBackgroundColor(Color.parseColor("#ffffff"));
		
	}

	public void SetText(String string){
		
		if (textView == null) {
			LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			params.topMargin = 20;
			params.rightMargin = 50;
			params.leftMargin = 50;
			
			textView = new UyghurTextView(pageContext);
			textView.setText(string);
			textView.setGravity(Gravity.CENTER|Gravity.CENTER);
			textView.setLayoutParams(params);
			
			addView(textView);
		}else {
			textView.setText(string);
		}
	
	}
	
	public void SetImage() {
		
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		
		imageView = new ImageView(pageContext);
		imageView.setImageResource(R.drawable.info);
		imageView.setLayoutParams(params);
		addView(imageView);
		
		
	}


}
