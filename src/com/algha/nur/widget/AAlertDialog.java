package com.algha.nur.widget;

import com.algha.nur.R;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.PopupWindow;

public class AAlertDialog extends PopupWindow {
	private Context context;
	private View view;
	public AAlertDialog(Context mContext){
		context = mContext;
		LayoutInflater inflater = LayoutInflater.from(context);
		view = inflater.inflate(R.layout.layout_dialog, null);
		
		this.setContentView(view);
		this.setWidth(LayoutParams.MATCH_PARENT);
		this.setHeight(LayoutParams.MATCH_PARENT);
		this.setFocusable(true);
		this.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#80000000")));
		
	}
	
	
	public void SetTitle(String Title){
		UyghurTextView textView = (UyghurTextView)view.findViewById(R.id.dialog_title);
		textView.setText(Title);
		textView.setVisibility(View.VISIBLE);
	}
	
	public void SetContent(String Content) {
		UyghurTextView textView = (UyghurTextView)view.findViewById(R.id.dialog_content);
		textView.setText(Content);
	}
	
	public void SetCancelButton(String Title,OnClickListener listener){
		UyghurTextView textView = (UyghurTextView)view.findViewById(R.id.Cancel);
		textView.setOnClickListener(listener);
		textView.setVisibility(View.VISIBLE);
		textView.setText(Title);
	}
	
	public void SetOkButton(String Title,OnClickListener listener){
		UyghurTextView textView = (UyghurTextView)view.findViewById(R.id.Ok);
		textView.setOnClickListener(listener);
		textView.setVisibility(View.VISIBLE);
		textView.setText(Title);
	}

	public void showPopupWindow(View parent) {
		if (!this.isShowing()) {
			this.showAtLocation(parent, Gravity.CENTER, 0, 0);
		} else {
			this.dismiss();
		}
	}
}
