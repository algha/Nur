package com.algha.nur.activity;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import com.algha.nur.App;
import com.algha.nur.R;
import com.algha.nur.Server;
import com.algha.nur.fragment.ActivityHome;
import com.algha.nur.fragment.ActivityNews;
import com.algha.nur.fragment.ActivitySettings;
import com.algha.nur.http.AsyncHttpClient;
import com.algha.nur.http.AsyncHttpResponseHandler;
import com.algha.nur.widget.AAlertDialog;
import com.algha.nur.widget.UyghurTextView;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class ActivityMain extends Activity {
	private LinearLayout TabsView;
	private ActivityHome activityHome;
	private ActivityNews activityNews;
	private ActivitySettings activitySettings;

	private android.app.FragmentManager fragmentManager;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		fragmentManager = getFragmentManager();

		initView();
		setTabSelection(0);
		CheckViewFromServer();
	}

	private void initView() {

		TabsView = (LinearLayout) findViewById(R.id.TabsView);
		for (int i = 0; i < TabsView.getChildCount(); i++) {
			UyghurTextView textView = (UyghurTextView) TabsView.getChildAt(i);
			textView.setOnClickListener(new TabClickListener());
		}
		SetSelected(R.id.home);
	}

	public class TabClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			SetSelected(v.getId());
			setTabSelection(Integer.parseInt(v.getTag().toString()));
		}
	}

	public void SetSelected(int id) {
		for (int i = 0; i < TabsView.getChildCount(); i++) {
			UyghurTextView textView = (UyghurTextView) TabsView.getChildAt(i);
			textView.setTextColor(Color.parseColor("#757575"));
			if (textView.getId() == id) {
				textView.setTextColor(Color.parseColor("#00a0e0"));
			}
		}
	}

	private void setTabSelection(int index) {

		FragmentTransaction transaction = fragmentManager.beginTransaction();

		hideFragments(transaction);

		switch (index) {
		case 0:
			if (activityHome == null) {
				activityHome = new ActivityHome();
				transaction.add(R.id.Content, activityHome);
			} else {
				transaction.show(activityHome);
			}
			break;
		case 1:
			if (activityNews == null) {
				activityNews = new ActivityNews();
				transaction.add(R.id.Content, activityNews);
			} else {
				transaction.show(activityNews);
			}
			break;
		case 2:
			if (activitySettings == null) {
				activitySettings = new ActivitySettings();
				transaction.add(R.id.Content, activitySettings);
			} else {
				transaction.show(activitySettings);
			}
			break;
		}
		transaction.commit();
	}

	private void hideFragments(FragmentTransaction transaction) {
		if (activityHome != null) {
			transaction.hide(activityHome);
		}
		if (activityNews != null) {
			transaction.hide(activityNews);
		}
		if (activitySettings != null) {
			transaction.hide(activitySettings);
		}

	}

	public void CheckViewFromServer() {

		AsyncHttpClient client = new AsyncHttpClient();
		client.get(Server.CheckVertion(), new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] responseBody) {
				// TODO Auto-generated method stub
				String string = new String(responseBody);
				try {
					final JSONObject object = new JSONObject(string);
					if (object.getString("ver").equals(Server.APP_VERSION) == false) {
						final AAlertDialog dialog = new AAlertDialog(
								ActivityMain.this);
						dialog.SetTitle(App.RESOURCES.getString(R.string.alert));
						dialog.SetCancelButton(
								App.RESOURCES.getString(R.string.cancel),
								new OnClickListener() {

									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										dialog.dismiss();
									}
								});
						dialog.SetOkButton(
								App.RESOURCES.getString(R.string.now_update),
								new OnClickListener() {
									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										try {
											Uri uri = Uri.parse(object
													.getString("url"));
											Intent it = new Intent(
													Intent.ACTION_VIEW, uri);
											startActivity(it);
										} catch (JSONException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}

										dialog.dismiss();
									}
								});
						dialog.SetContent(object.getString("msg"));
						dialog.showPopupWindow(TabsView);
					}
				} catch (Exception e) {
					// TODO: handle exception

				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] responseBody, Throwable error) {
				// TODO Auto-generated method stub

			}
		});
	}

}