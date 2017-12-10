package com.algha.nur.adapter;

import java.util.ArrayList;

import com.algha.nur.R;
import com.algha.nur.model.Info;
import com.algha.nur.widget.UyghurTextView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class InfoAdapter extends BaseAdapter{
	private Context context = null;
	private ArrayList<Info> arrayList;
	private LayoutInflater inflater;

	public InfoAdapter(Context mContext,ArrayList<Info> mlArrayList) {
		super();
		context = mContext;
		arrayList = mlArrayList;
	}

	public void AddObject(Info info) {
		arrayList.add(info);
	}
	
	public ArrayList<Info> getList() {
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
		Info info = arrayList.get(position);
		if (convertView == null) {
			inflater = LayoutInflater.from(context);
			convertView = inflater.inflate(R.layout.news_list_item, null);
		}

		UyghurTextView titleTextView = (UyghurTextView) convertView.findViewById(R.id.Title);
		titleTextView.setText(info.GetTitle());

		return convertView;
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return super.getViewTypeCount();
	}
}
