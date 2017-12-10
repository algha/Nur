package com.algha.nur.adapter;

import com.algha.nur.R;
import com.algha.nur.model.News;
import com.algha.nur.widget.UyghurTextView;
import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class NewsAdapter extends BaseAdapter {
	private Context context = null;
	private ArrayList<News> arrayList;
	private LayoutInflater inflater;

	public NewsAdapter(Context mContext,ArrayList<News> mlArrayList) {
		super();
		context = mContext;
		arrayList = mlArrayList;
		
	}

	public void AddObject(News news) {
		arrayList.add(news);
	}
	
	public ArrayList<News> getList() {
		return arrayList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arrayList.size();
	}

	
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return arrayList.get(position);
	}
	
	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return super.getItemViewType(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		News news = arrayList.get(position);
		if (convertView == null) {
			inflater = LayoutInflater.from(context);
			convertView = inflater.inflate(R.layout.news_list_item, null);
		}

		UyghurTextView titleTextView = (UyghurTextView) convertView
				.findViewById(R.id.Title);
		titleTextView.setText(news.GetTitle());

		return convertView;
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return super.getViewTypeCount();
	}
}
