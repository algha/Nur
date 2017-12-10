package com.algha.nur.fragment;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.algha.nur.App;
import com.algha.nur.R;
import com.algha.nur.Server;
import com.algha.nur.activity.ActivityWebView;
import com.algha.nur.http.AsyncHttpClient;
import com.algha.nur.http.AsyncHttpResponseHandler;
import com.algha.nur.widget.AAlertDialog;
import com.algha.nur.widget.AToast;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class ActivitySettings extends Fragment {

	private Context context;
	View view;
	private boolean Cheking;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_settings, container,false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		context = getActivity();

		//view.findViewById(R.id.CacheClear).setOnClickListener(new ClickListener());
		view.findViewById(R.id.CheckVersion).setOnClickListener(new ClickListener());
		view.findViewById(R.id.AboutApp).setOnClickListener(new ClickListener());
		view.findViewById(R.id.AboutConpany).setOnClickListener(new ClickListener());
		
		Cheking = false;
	}

	public class ClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			
//			case R.id.CacheClear:
//				final AAlertDialog dialog = new AAlertDialog(context);
//				dialog.SetTitle(App.RESOURCES.getString(R.string.alert));
//				dialog.SetCancelButton(App.RESOURCES.getString(R.string.cancel),new OnClickListener() {
//					
//					@Override
//					public void onClick(View v) {
//						// TODO Auto-generated method stub
//						dialog.dismiss();
//						
//					}
//				});
//				dialog.SetOkButton(App.RESOURCES.getString(R.string.IClear),new OnClickListener() {
//					
//					@Override
//					public void onClick(View v) {
//						// TODO Auto-generated method stub
//						dialog.dismiss();
//					}
//				});
//				dialog.SetContent(App.RESOURCES.getString(R.string.ClearInfo));
//				dialog.showPopupWindow(v);
//				break;
			
			case R.id.CheckVersion:
				CheckViewFromServer();
				break;
			case R.id.AboutApp:
				Intent intent = new Intent(context, ActivityWebView.class);
				intent.putExtra("type", "AboutApp");
				intent.putExtra("url", Server.GetAboutUrl("AboutApp"));
				startActivity(intent);
				break;

			case R.id.AboutConpany:
				Intent intent1 = new Intent(context, ActivityWebView.class);
				intent1.putExtra("type", "AboutCompany");
				intent1.putExtra("url", Server.GetAboutUrl("AboutCompany"));
				startActivity(intent1);
				break;
				
			}
		}
	}
	
	public void CheckViewFromServer() {
		if (Cheking == true) {
			return;
		}
		AToast toast = new AToast(context);
		toast.show(App.RESOURCES.getString(R.string.ChekingWait), Toast.LENGTH_SHORT);
		Cheking = true;
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(Server.CheckVertion(), new AsyncHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				Cheking = false;
				// TODO Auto-generated method stub
				String string = new String(responseBody);
				try {
					final JSONObject object = new JSONObject(string);
					if (object.getString("ver").equals(Server.APP_VERSION) == false) {
						final AAlertDialog dialog = new AAlertDialog(context);
						dialog.SetTitle(App.RESOURCES.getString(R.string.alert));
						dialog.SetCancelButton(App.RESOURCES.getString(R.string.cancel),new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								dialog.dismiss();
							}
						});
						dialog.SetOkButton(App.RESOURCES.getString(R.string.now_update),new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								try {
									Uri uri = Uri.parse(object.getString("url"));
									Intent it = new Intent(Intent.ACTION_VIEW,uri);
									startActivity(it);
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								dialog.dismiss();
							}
						});
						dialog.SetContent(object.getString("msg"));
						dialog.showPopupWindow(view);
					}else{
						AToast toast = new AToast(context);
						toast.show(App.RESOURCES.getString(R.string.NoNewVersion), Toast.LENGTH_SHORT);
					}
				} catch (Exception e) {
					// TODO: handle exception
					AToast toast = new AToast(context);
					toast.show(App.RESOURCES.getString(R.string.InternetError), Toast.LENGTH_SHORT);
				}
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] responseBody, Throwable error) {
				Cheking = false;
				// TODO Auto-generated method stub
				AToast toast = new AToast(context);
				toast.show(App.RESOURCES.getString(R.string.InternetError), Toast.LENGTH_SHORT);
			}
		});
	}
	
}
