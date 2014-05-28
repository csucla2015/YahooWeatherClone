package com.npi.blureffect;


import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AbsListView.LayoutParams;
import java.io.File;
import com.jeremyfeinstein.slidingmenu.lib.*;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

//import com.google.android.apps.analytics.GoogleAnalyticsTracker;




public class Main extends Fragment {
	/** Called when the activity is first created. */
	private String searchKeywords;


	private String[] autoSuggestions;
	private ArrayAdapter<String> adapter;
	View rootView;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		 
        rootView = inflater.inflate(R.layout.fragment_main, container, false);
		searchKeywords = "";

		/*tracker = GoogleAnalyticsTracker.getInstance();
		tracker.startNewSession("UA-26334988-1", this);
		tracker.trackPageView("/MainScreen");
		tracker.dispatch();
*/
		
		

		
		
		autoSuggestions = new String [0];
		new PopulateSuggestionsTask().execute("");	
		

	/*	final Button top101Button = (Button) rootView.findViewById(R.id.top101Btn);
		top101Button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//Intent i = new Intent(Main.this, Top101.class);
				//startActivity(i);
			}
		});

		final Button newFeaturesButton = (Button) rootView.findViewById(R.id.newfeatures);
		newFeaturesButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//Intent i = new Intent(Main.this, NewFeatures.class);
				//startActivity(i);
			}
		});*/
        return rootView;

	}
	
	private class PopulateSuggestionsTask extends AsyncTask<String, Void, String []> {
		protected String [] doInBackground(String... arg0) {
			return getSuggestions();
		}			
		protected void onPostExecute(String [] result) {
			adapter = new ArrayAdapter<String>(getActivity(),
					android.R.layout.simple_dropdown_item_1line, result);
			
			final AutoCompleteTextView edittext = (AutoCompleteTextView) rootView.findViewById(R.id.search);

			edittext.setAdapter(adapter);
			
			edittext.setOnKeyListener(new OnKeyListener() {
				public boolean onKey(View v, int keyCode, KeyEvent event) {
					// If the event is a key-down event on the "enter" button
					if ((event.getAction() == KeyEvent.ACTION_DOWN)
							&& (keyCode == KeyEvent.KEYCODE_ENTER)) {

						Toast.makeText(getActivity(), edittext.getText(),
								Toast.LENGTH_SHORT).show();
						searchKeywords = edittext.getText().toString();
						Log.v("My search keywork is",searchKeywords);
						Intent i = new Intent(getActivity(), Test.class);
						Bundle b = new Bundle();
						b.putString("searchKeywords", searchKeywords);
						b.putString("queryType", "GKEY");
						i.putExtras(b);

						startActivity(i);

						// closes soft keyboard
						
						return true;
					}
					return false;
				}
			});
			
			final ImageButton searchButton = (ImageButton) rootView.findViewById(R.id.searchBtn);
			searchButton.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					/////////////IMP//////////////////////////////
					/*Toast.makeText(Main.this, edittext.getText(),
							Toast.LENGTH_SHORT).show();
					searchKeywords = edittext.getText().toString();

					Intent i = new Intent(this, MainActivity.class);
					Bundle b = new Bundle();
					b.putString("searchKeywords", searchKeywords);
					b.putString("queryType", "GKEY");
					i.putExtras(b);
					startActivity(i);
					*/
					 Intent i = new Intent(getActivity(), Test.class);
					 Bundle b = new Bundle();
						b.putString("searchKeywords", searchKeywords);
						b.putString("queryType", "GKEY");
						i.putExtras(b);
		                startActivity(i);
					
				}
			});
		}
	}

	public boolean isNetworkAvailable() {
		Context context = getApplicationContext();
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			// boitealerte(this.getString(R.string.alert),"getSystemService rend null");
		} else {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}

	private Context getApplicationContext() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean checkNetwork() {
		if (!isNetworkAvailable()) {
			AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
			alert.setMessage(
					"A network error has occurred. Retry or cancel and return to the previous screen.")
					.setCancelable(false)
					.setPositiveButton("Retry",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									// MyActivity.this.finish();

								/*	Intent intent = getIntent();
									Main.this.finish();
									startActivity(intent);
*/
								}
							})
					.setNegativeButton("Cancel",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.cancel();
								}
							});
			AlertDialog alertD = alert.create();

			alertD.show();
			return false;
		}
		return true;

	}

	public void onResume() {
		//tracker.trackPageView("/MainScreen");
		super.onResume();
	}

	public String[] getSuggestions() {
		
		String[] a={"Meet","Meet"};
		return a;
	}

	
	public void instantiate() {
		// TODO Auto-generated method stub

	}
}