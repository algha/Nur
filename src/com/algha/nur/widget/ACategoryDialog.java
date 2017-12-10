package com.algha.nur.widget;

import java.util.ArrayList;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.algha.nur.App;
import com.algha.nur.R;
import com.algha.nur.model.Category;

public class ACategoryDialog extends PopupWindow {
	private Context context;
	private View view;
	public ACategoryDialog(Context mContext){
		context = mContext;
		LayoutInflater inflater = LayoutInflater.from(context);
		view = inflater.inflate(R.layout.layout_category_dialog, null);
		
		this.setContentView(view);
		this.setWidth(LayoutParams.MATCH_PARENT);
		this.setHeight(LayoutParams.MATCH_PARENT);
		this.setFocusable(true);
		this.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#80000000")));
		
	}
	
	
	public void SetData(ArrayList<Category> dataList,OnClickListener listener){
		LinearLayout dataLayout = (LinearLayout)view.findViewById(R.id.data);
		
		
		LayoutParams AllParams = new LayoutParams(LayoutParams.MATCH_PARENT,65);

		
		UyghurTextView allTextView = new UyghurTextView(context);
		allTextView.setOnClickListener(listener);
		allTextView.setTag("0");
		allTextView.setLayoutParams(AllParams);
		allTextView.setText(App.RESOURCES.getString(R.string.All));
		dataLayout.addView(allTextView);
		
		for (int i = 0; i < dataList.size(); i++) {
			
			Category category = dataList.get(i);
			
			LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,65);

			
			UyghurTextView textView = new UyghurTextView(context);
			textView.setOnClickListener(listener);
			textView.setTag(category.GetId());
			textView.setLayoutParams(params);
			textView.setText(category.GetName());
			dataLayout.addView(textView);
		}
	}
	
	public void SetTitle(String Content) {
		UyghurTextView textView = (UyghurTextView)view.findViewById(R.id.title);
		textView.setVisibility(View.VISIBLE);
		textView.setText(Content);
	}

	
	public void SetCloseButton(String Title,OnClickListener listener){
		UyghurTextView textView = (UyghurTextView)view.findViewById(R.id.Close);
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
