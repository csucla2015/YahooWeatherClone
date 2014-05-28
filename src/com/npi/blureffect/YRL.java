package com.npi.blureffect;

import java.io.BufferedReader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import java.net.URI;
import java.net.URL;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.jeremyfeinstein.slidingmenu.lib.*;


import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;

public class YRL extends Fragment{
	
	private static final String BLURRED_IMG_PATH = "image.jpg";
	private ListView mList;
	private ImageView mBlurredImage;
	private ImageView mNormalImage;
	private float alpha;
	private View headerView;
	private static final int TOP_HEIGHT = 700;
	private static final String tg = "nitin";
	boolean flag = true;
	String newlink ="";
	String newlink2 ="";
	View rootView;
	   @SuppressLint("NewApi")
	@Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
		   
		 
		   newlink = "http://webservices.library.ucla.edu/libservices/hours/unit/7";
		   newlink2 = "http://webservices.library.ucla.edu/laptops/available/";
	        rootView = inflater.inflate(R.layout.fragment_yrl, container, false);
	        
	        
	        mList = (ListView) rootView.findViewById(R.id.list);
	        mBlurredImage = (ImageView) rootView.findViewById(R.id.blurred_imagey);
			mNormalImage = (ImageView) rootView.findViewById(R.id.normal_imagey);
			

			// Get the screen width
			final int screenWidth = ImageUtils.getScreenWidth(getActivity());
		
	
			// Try to find the blurred image
			final File blurredImage = new File(getActivity().getFilesDir() + BLURRED_IMG_PATH);
			//Log.d(tg, "Y "+blurredImage.exists());
			
			if (!blurredImage.exists()) {

				// launch the progressbar in ActionBar
				getActivity().setProgressBarIndeterminateVisibility(true);
				//Log.d(tg, "Y: if");
				new Thread(new Runnable() {

					@Override
					public void run() {

						// No image found => let's generate it!
						BitmapFactory.Options options = new BitmapFactory.Options();
						options.inSampleSize = 2;
						Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.image, options);
						Bitmap newImg = Blur.fastblur(getActivity(), image, 7);
						ImageUtils.storeImage(newImg, blurredImage);
						getActivity().runOnUiThread(new Runnable() {

							@Override
							public void run() {
								updateView(screenWidth);

								// And finally stop the progressbar
								getActivity().setProgressBarIndeterminateVisibility(true);
							}
						});

					}
				}).start();

			} else {
				mNormalImage.setBackgroundResource(R.drawable.image);
				
				// The image has been found. Let's update the view
				//Log.d(tg, "Y: else");
				updateView(screenWidth);

			}
			
			
			
	        
			headerView = new View(getActivity());
		
			headerView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, TOP_HEIGHT));
			String[] strings = getResources().getStringArray(R.array.list_content);
			

			
			FakeNetLoader fl = new FakeNetLoader();
			fl.execute("http://webservices.library.ucla.edu/libservices/hours/unit/7");
			String j = "JSON not receieved!";
			try {
				j = fl.get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//Log.d(tg, j);
			
			
			FakeNetLoader1 fl1 = new FakeNetLoader1();
			fl1.execute("http://webservices.library.ucla.edu/laptops/available/");
			String j1 = "JSON not receieved!";
			try {
				j1 = fl1.get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//Log.d(tg, j1);
			
			
			//strings[0]= "Hours : /n Monday : 7:30 am - 11:00 pm \n Tuesday : 7:30 am - 11:00 pm \n Wednesday : 7:30 am - 11:00 pm \n Thursday 7:30 am - 11:00 pm \n Friday 7:30 am - 11:00 pm";
			String[] headers = new String[10];
			headers[0] = "YRL Library";
			headers[2] = "Hours";
			headers[4] = "Laptop Availability";
			headers[6] = "Contact";
			headers[8] = "Room Reservations";
			
			if (j.charAt(0) == 'Y')
				strings[0] = "Open Now \nStudy Rooms Available : 9 \nLaptops Available : "+j1;
			else
				strings[0] = "Closed Now \nStudy Rooms Available : 9 \nLaptops Available : "+j1;
			j = j.substring(1);
			strings[2]= j;//"Monday : 7:30 am - 11:00 pm \nTuesday : 7:30 am - 11:00 pm \nWednesday : 7:30 am - 11:00 pm \nThursday 7:30 am - 11:00 pm \nFriday 7:30 am - 11:00 pm";
			strings[4] = "Total Laptops Available = "+j1;
			strings[6] = "310.825.4732";
			strings[8] = "Under Construction";


			headers[1] = "";
			headers[3] = "";
			headers[5] = "";
			headers[7] = "";
			headers[9] = "";
			strings[1] = "";
			strings[3]= "";
			strings[5] = "";
			strings[7] = "";
			strings[9] = "";
			MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(getActivity(), strings, headers);
			mList.addHeaderView(headerView);
			mList.setAdapter(adapter); 
			mList.setOnScrollListener(new OnScrollListener() {

				@Override
				public void onScrollStateChanged(AbsListView view, int scrollState) {

				}

				/**
				 * Listen to the list scroll. This is where magic happens ;)
				 */
				@Override
				public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

					// Calculate the ratio between the scroll amount and the list
					// header weight to determinate the top picture alpha
					alpha = (float) -headerView.getTop() / (float) TOP_HEIGHT;
					// Apply a ceil
					if (alpha > 1) {
						alpha = 1;
					}

					// Apply on the ImageView if needed
					//if (mSwitch.isChecked()) {
						mBlurredImage.setAlpha(alpha);
					//}

					// Parallax effect : we apply half the scroll amount to our
					// three views
					mBlurredImage.setTop(headerView.getTop() / 2);
					mNormalImage.setTop(headerView.getTop() / 2);
					//mBlurredImageHeader.handleScroll(headerView.getTop() / 2);

				}
			});
			
			
	        //getActivity().getActionBar().hide();
	        return rootView;
	    }
	   
	   private void updateView(final int screenWidth) {
			Bitmap bmpBlurred = BitmapFactory.decodeFile(getActivity().getFilesDir() + BLURRED_IMG_PATH);
			bmpBlurred = Bitmap.createScaledBitmap(bmpBlurred, screenWidth, (int) (bmpBlurred.getHeight()
					* ((float) screenWidth) / (float) bmpBlurred.getWidth()), false);
			

			mBlurredImage.setImageBitmap(bmpBlurred);

			//mBlurredImageHeader.setoriginalImage(bmpBlurred);
		}
	   
	   
	   
	private class FakeNetLoader extends AsyncTask<String, Void, String> {
		// private final ProgressDialog dialog = new ProgressDialog(YRL.this);
		
		
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Log.d(tg,"prehere");

			// dialog.setMessage("Fetching Results...");
			// dialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			if (flag == false) {
				StringBuilder builder = new StringBuilder();
				for (String s : params) {
					builder.append(s);
				}
				newlink = builder.toString();
				//Log.d(tg, newlink);
			}

			String result = new String();
			InputStream is1 = null;
			// Log.d(tg,"We made the more request. Yaay!!");

			// /////////////////////JUST MAKING THE FIRST
			// REQUEST////////////////////////////////
			// result.add("meet");
			InputStream is = null;
			JSONObject jObj = null;
			String json = "";
			try {
				// defaultHttpClient
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpGet get1;
				// Log.d(tg,newlink);
				get1 = new HttpGet(newlink);

				HttpResponse httpResponse = httpClient.execute(get1);
				HttpEntity httpEntity = httpResponse.getEntity();
				// Log.d(tg, newlink);
				is = httpEntity.getContent();

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is, "iso-8859-1"), 8);
				StringBuilder sb = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
				is.close();
				json = sb.toString();
			} catch (Exception e) {
				Log.e("Buffer Error", "Error converting result " + e.toString());
			}
			String bibid = "";
			// try parse the string to a JSON object
			try {
				
				jObj = new JSONObject(json);
				jObj = jObj.getJSONObject("unitSchedule");
				String mtOpen = getHour(jObj.getString("monThursOpens"));
				String mtClose = getHour(jObj.getString("monThursCloses"));
				
				String friOpen = getHour(jObj.getString("friOpens"));
				String friClose = getHour(jObj.getString("friCloses"));
				
				
				Calendar c = Calendar.getInstance(); 
				int hour = c.get(Calendar.HOUR);
				int minutes = c.get(Calendar.MINUTE);
				int day = c.get(Calendar.DAY_OF_WEEK);
				//Log.d(tg, minutes+"");
				String hours = "Monday : "+mtOpen+ " - "+mtClose+" \nTuesday : "+mtOpen+" - "+mtClose+" \nWednesday : "+mtOpen+" - "+mtClose+" \nThursday : "+mtOpen+" - "+mtClose+" \nFriday : "+friOpen+" - "+friClose+"";
				String open = "";
				if (mtOpen.length() == 7){
					mtOpen = "0"+mtOpen;
				}
				if (mtClose.length() == 7){
					mtClose = "0"+mtClose;
				}
				if (friOpen.length() == 7){
					friOpen = "0"+friOpen;
				}
				if (friClose.length() == 7){
					friClose = "0"+friClose;
				}
				
				if (day != 6){
					//compare using monday-thursday time
					if ((hour >= Integer.parseInt(mtOpen.substring(0,2))) && (hour <= Integer.parseInt(mtClose.substring(0,2))) && (minutes >= Integer.parseInt(mtOpen.substring(3,5))) && (minutes >= Integer.parseInt(mtClose.substring(3,5)))){
						open = "Y";
					}else{
						open = "N";
					}
					
				}else{
					//compare using friday time
					if ((hour >= Integer.parseInt(friOpen.substring(0,2))) && (hour <= Integer.parseInt(friClose.substring(0,2))) && (minutes >= Integer.parseInt(friOpen.substring(3,5))) && (minutes >= Integer.parseInt(friClose.substring(3,5)))){
						open = "Y";
					}else{
						open = "N";
					}
				}
				
				
				result = open+hours;
				Thread.sleep(1000);
			} catch (JSONException e) {
				Log.d(tg, "Error parsing data " + e.toString());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;
		}
		
		public String getHour(String mon){
			mon = mon.substring(mon.indexOf("T"), mon.length());
			mon = mon.substring(1);
			if (mon.charAt(0) == '0'){
				mon = mon.substring(1, 5) + " AM";
			}else{
				int hrs = Integer.parseInt(mon.substring(0, 2));
				hrs = hrs - 12;
				mon = hrs + mon.substring(2, 5) + " PM";
			}
			return mon;
		}
	}
	
	private class FakeNetLoader1 extends AsyncTask<String, Void, String> {
		// private final ProgressDialog dialog = new ProgressDialog(YRL.this);
		
		
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Log.d(tg,"prehere");

			// dialog.setMessage("Fetching Results...");
			// dialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			if (flag == false) {
				StringBuilder builder = new StringBuilder();
				for (String s : params) {
					builder.append(s);
				}
				newlink2 = builder.toString();
				Log.d(tg, newlink2);
			}

			String result = new String();
			InputStream is1 = null;
			// Log.d(tg,"We made the more request. Yaay!!");

			// /////////////////////JUST MAKING THE FIRST
			// REQUEST////////////////////////////////
			// result.add("meet");
			InputStream is = null;
			JSONObject jObj = null;
			String laptops = "";
			String json = "";
			try {
				// defaultHttpClient
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpGet get1;
				// Log.d(tg,newlink2);
				get1 = new HttpGet(newlink2);

				HttpResponse httpResponse = httpClient.execute(get1);
				HttpEntity httpEntity = httpResponse.getEntity();
				// Log.d(tg, newlink2);
				is = httpEntity.getContent();

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is, "iso-8859-1"), 8);
				StringBuilder sb = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
				is.close();
				json = sb.toString();
			} catch (Exception e) {
				Log.e("Buffer Error", "Error converting result " + e.toString());
			}
			String bibid = "";
			// try parse the string to a JSON object
			try {
				
				jObj = new JSONObject(json);
				//Log.d(tg, json);
				JSONArray ja = jObj.getJSONArray("laptops");
				jObj = ja.getJSONObject(2);
				
				
				laptops = jObj.getString("availableCount");
				Thread.sleep(1000);
			} catch (JSONException e) {
				Log.d(tg, "Error parsing data " + e.toString());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return laptops;
		}
		
		

	}

	

}
