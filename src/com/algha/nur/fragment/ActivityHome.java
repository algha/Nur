package com.algha.nur.fragment;

import java.util.ArrayList;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.algha.nur.App;
import com.algha.nur.R;
import com.algha.nur.Server;
import com.algha.nur.activity.ActivityInfoDetail;
import com.algha.nur.adapter.InfoAdapter;
import com.algha.nur.http.AsyncHttpClient;
import com.algha.nur.http.AsyncHttpResponseHandler;
import com.algha.nur.model.Category;
import com.algha.nur.model.Info;
import com.algha.nur.widget.ACategoryDialog;
import com.algha.nur.widget.AToast;
import com.algha.nur.widget.PageErrorView;
import com.algha.nur.widget.PageLoadingView;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class ActivityHome extends Fragment{
	
	private int page = 1;
	private ListView listView;
	private InfoAdapter adapter;
	private ArrayList<Info> arrayList;
	private ArrayList<Category> categories;
	private Context context;
	private String catid = "0";
	private Boolean IsEnd = false, loadFinishBoolean = true;
	LinearLayout view,Footer;
	PageLoadingView loadingView;
	PageErrorView errorView;
	ACategoryDialog dialog;
	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {		
		view = (LinearLayout)inflater.inflate(R.layout.fragment_home,container,false);
		return view;	
	}	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		Initalization();
		
		LoadData();
		
	}

	
	public void Initalization(){
		
		context = getActivity();
		
		categories = new ArrayList<Category>();
		
		loadingView = new PageLoadingView(context);
		loadingView.Initalization();
		loadingView.SetText(App.RESOURCES.getString(R.string.loading));
		view.addView(loadingView);
		
		errorView = new PageErrorView(context);
		errorView.Initalization();
		errorView.SetText(App.RESOURCES.getString(R.string.errorInfo));
		view.addView(errorView);
		errorView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ShowView(loadingView);
				IsEnd = false;
				LoadData();
			}
		});
		
		LayoutInflater inflater = LayoutInflater.from(context);
		Footer = (LinearLayout)inflater.inflate(R.layout.layout_page_loading,null);

		arrayList = new ArrayList<Info>();
		listView = (ListView) view.findViewById(R.id.listView);
		listView.setOnScrollListener(new MyOnScrollListener());
		listView.setVisibility(View.GONE);

		adapter = new InfoAdapter(context, arrayList);
		listView.addFooterView(Footer);
		listView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		listView.removeFooterView(Footer);
		
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Info info = arrayList.get(position);
				
				Intent intent = new Intent(context,ActivityInfoDetail.class);
				intent.putExtra("id", info.GetId());
				startActivity(intent);
			}
		});
		
		ImageView categoryBtn = (ImageView)view.findViewById(R.id.category);
		categoryBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (categories != null && categories.size() > 0) {
					dialog = new ACategoryDialog(context);
					dialog.SetTitle(App.RESOURCES.getString(R.string.categorys));
					dialog.SetData(categories,new CategoryListener());
					dialog.showPopupWindow(view);
				}
			}
		});
	}
	
	public void ShowView(View v){
		listView.setVisibility(View.GONE);
		errorView.setVisibility(View.GONE);
		loadingView.setVisibility(View.GONE);
		v.setVisibility(View.VISIBLE);
	}
	
	public void PageLoading(boolean isLoading) {
		if (isLoading == true) {
			listView.addFooterView(Footer);
			ImageView animImageView = (ImageView) Footer.findViewById(R.id.imgLoading);
			com.algha.nur.App.startLoadingAnimation(animImageView);
		} else if (isLoading == false) {
			ImageView animImageView = (ImageView) Footer.findViewById(R.id.imgLoading);
			animImageView.clearAnimation();
			listView.removeFooterView(Footer);
		}
	}
	
	public void LoadData() {

		AsyncHttpClient client = new AsyncHttpClient();
		client.get(Server.GetInfo(catid,page), new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers,byte[] responseBody) {
				PageLoading(false);
				loadFinishBoolean = true;
				// TODO Auto-generated method stub
				String string = new String(responseBody);
				try {
					JSONObject object = new JSONObject(string);
					if (Integer.parseInt(object.getString("ResultCode")) == 1) {
						
						JSONObject infoObject =  object.getJSONObject("Result");
						
						try {
							JSONArray categoryArray = infoObject.getJSONArray("category");
							categories.clear();
							for (int i = 0; i < categoryArray.length(); i++) {
								categories.add(new Category(categoryArray.getJSONObject(i)));
							}
							
						} catch (Exception e) {
							// TODO: handle exception
						}
					
						JSONArray infoArray = infoObject.getJSONArray("info");
						int i=0;
						for (i = 0; i < infoArray.length(); i++) {
							JSONObject obj = infoArray.getJSONObject(i);
							Info info = new Info(obj.getString("Id"), 
									obj.getString("Poster"), 
									obj.getString("Title"), 
									obj.getString("Content"), 
									obj.getString("Click"), 
									obj.getString("Created_at"));
							adapter.AddObject(info);
						}
						adapter.notifyDataSetChanged();
						ShowView(listView);
						if (i<20) {
							IsEnd = true;
						}
						if (page == 1 && i == 0) {
							ShowView(errorView);
							errorView.SetText(App.RESOURCES.getString(R.string.NoData));
						 }
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					if (page == 1) {
						ShowView(errorView);
						errorView.SetText(App.RESOURCES.getString(R.string.errorInfo));
					}
					if (page > 1) {
						AToast toast = new AToast(context);
						toast.show(App.RESOURCES.getString(R.string.ErrorInfoToast), Toast.LENGTH_SHORT);
						IsEnd = true;
						page--;
						new Handler().postDelayed(new Runnable() {
							@Override
							public void run() {
								// TODO Auto-generated method stub
								IsEnd = false;
							}
						}, 3000);
					}
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] responseBody, Throwable error) {
				// TODO Auto-generated method stub
				PageLoading(false);
				loadFinishBoolean = true;
				if (page == 1) {
					ShowView(errorView);
					errorView.SetText(App.RESOURCES.getString(R.string.errorInfo));
				}
				if (page > 1) {
					AToast toast = new AToast(context);
					toast.show(App.RESOURCES.getString(R.string.ErrorInfoToast), Toast.LENGTH_SHORT);
					IsEnd = true;
					page--;
					new Handler().postDelayed(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							IsEnd = false;
						}
					}, 3000);
				}
			}
		});
	}
	
	public class CategoryListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			dialog.dismiss();
			ShowView(loadingView);
			
			page = 1;
			IsEnd = false;
			
			arrayList.clear();
			listView.smoothScrollToPosition(0);
			adapter.notifyDataSetChanged();
			
			catid = v.getTag().toString();
			LoadData();
		}
	}
	
	public class MyOnScrollListener implements OnScrollListener {
		@Override
		public void onScroll(AbsListView View, int VisibleItem,
				int VisibleItemCount, int TotalItemCount) {
			// TODO Auto-generated method stub
			final int totalLoad = TotalItemCount;
			int lastItemId = listView.getLastVisiblePosition();
			if (arrayList.size() >= 20 && (lastItemId + 1) == totalLoad && loadFinishBoolean == true && IsEnd == false) {
				loadFinishBoolean = false;
				page++;
				PageLoading(true);
				LoadData();
			}
		}
		@Override
		public void onScrollStateChanged(AbsListView arg0, int arg1) {
			// TODO Auto-generated method stub

		}
	}
}
