package com.npi.blureffect;

import java.io.File;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.jeremyfeinstein.slidingmenu.lib.*;


import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;

public class Mgmt extends Fragment{
	
	private static final String BLURRED_IMG_PATH = "image3.jpg";
	private ListView mList;
	private ImageView mBlurredImage;
	private ImageView mNormalImage;
	private float alpha;
	private View headerView;
	private static final int TOP_HEIGHT = 700;
	private static final String tg = "nitin";
	View rootView;
	   @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
		   
		
	 
	        rootView = inflater.inflate(R.layout.fragment_mgmt, container, false);

	        
	        mList = (ListView) rootView.findViewById(R.id.list);
	        mBlurredImage = (ImageView) rootView.findViewById(R.id.blurred_imagem);
			mNormalImage = (ImageView) rootView.findViewById(R.id.normal_imagem);
<<<<<<< HEAD
			rootView.setBackgroundResource(R.drawable.image3);
=======

>>>>>>> 5bee727d8bb8a4ad0e2700855828786b04839b65
			// Get the screen width
			final int screenWidth = ImageUtils.getScreenWidth(getActivity());

			// Try to find the blurred image
			final File blurredImage = new File(getActivity().getFilesDir() + BLURRED_IMG_PATH);
<<<<<<< HEAD
			//Log.d(tg, "M: "+blurredImage.exists());
			
=======
			Log.d(tg, "M: "+blurredImage.exists());

			/*
>>>>>>> 5bee727d8bb8a4ad0e2700855828786b04839b65
			if (!blurredImage.exists()) {

				// launch the progressbar in ActionBar
				getActivity().setProgressBarIndeterminateVisibility(true);
<<<<<<< HEAD
				//Log.d(tg, "M: if");
=======
				Log.d(tg, "M: if");
>>>>>>> 5bee727d8bb8a4ad0e2700855828786b04839b65
				new Thread(new Runnable() {

					@Override
					public void run() {

						// No image found => let's generate it!
						BitmapFactory.Options options = new BitmapFactory.Options();
						options.inSampleSize = 2;
						Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.image3, options);
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
<<<<<<< HEAD
				mNormalImage.setBackgroundResource(R.drawable.image3);
				// The image has been found. Let's update the view
				//Log.d(tg, "M: else");
=======

				// The image has been found. Let's update the view
				Log.d(tg, "M: else");
>>>>>>> 5bee727d8bb8a4ad0e2700855828786b04839b65
				updateView(screenWidth);

			}
			
<<<<<<< HEAD
			
=======
			*/
			getActivity().setProgressBarIndeterminateVisibility(true);
			Log.d(tg, "M: if");
			new Thread(new Runnable() {

				@Override
				public void run() {

					// No image found => let's generate it!
					BitmapFactory.Options options = new BitmapFactory.Options();
					options.inSampleSize = 2;
					Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.image3, options);
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

>>>>>>> 5bee727d8bb8a4ad0e2700855828786b04839b65
			
	        
			headerView = new View(getActivity());
		
			headerView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, TOP_HEIGHT));
			String[] strings = getResources().getStringArray(R.array.list_content);
			strings[0]= "Hours : /n Monday : 7:30 am - 11:00 pm \n Tuesday : 7:30 am - 11:00 pm \n Wednesday : 7:30 am - 11:00 pm \n Thursday 7:30 am - 11:00 pm \n Friday 7:30 am - 11:00 pm";
			String[] headers = new String[10];
<<<<<<< HEAD
			headers[0] = "MGMT Library";
=======
			headers[0] = "Powell Library";
>>>>>>> 5bee727d8bb8a4ad0e2700855828786b04839b65
			headers[2] = "Hours";
			headers[4] = "Laptop Availability";
			headers[6] = "Contact";
			headers[8] = "Room Reservations";
			strings[0] = "Open Now \nStudy Rooms Available : 9 \nLaptops Available : 10";
			strings[2]= "Monday : 7:30 am - 11:00 pm \nTuesday : 7:30 am - 11:00 pm \nWednesday : 7:30 am - 11:00 pm \nThursday 7:30 am - 11:00 pm \nFriday 7:30 am - 11:00 pm";
<<<<<<< HEAD
			strings[4] = "Laptops Not Available For Rent";
			strings[6] = "(310) 825-3138";
=======
			strings[4] = "Total Laptops Available = 10";
			strings[6] = "Under Construction";
>>>>>>> 5bee727d8bb8a4ad0e2700855828786b04839b65
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

}
