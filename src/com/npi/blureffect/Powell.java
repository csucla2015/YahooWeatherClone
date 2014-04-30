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

public class Powell extends Fragment{
	
	private static final String BLURRED_IMG_PATH = "image2.jpg";
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
		   

	 
	        rootView = inflater.inflate(R.layout.fragment_powell, container, false);
	      	        
	        mList = (ListView) rootView.findViewById(R.id.list);
	        mBlurredImage = (ImageView) rootView.findViewById(R.id.blurred_imagep);
			mNormalImage = (ImageView) rootView.findViewById(R.id.normal_imagep);
		
			// Get the screen width
			final int screenWidth = ImageUtils.getScreenWidth(getActivity());

	
			// Try to find the blurred image
			final File blurredImage = new File(getActivity().getFilesDir() + BLURRED_IMG_PATH);
			Log.d(tg, "P "+blurredImage.exists());
			
			if (!blurredImage.exists()) {

				// launch the progressbar in ActionBar
				getActivity().setProgressBarIndeterminateVisibility(true);
				Log.d(tg, "P :if");
				new Thread(new Runnable() {

					@Override
					public void run() {

						// No image found => let's generate it!
						BitmapFactory.Options options = new BitmapFactory.Options();
						options.inSampleSize = 2;
						Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.image2, options);
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

				// The image has been found. Let's update the view
				Log.d(tg, "P:else");
				updateView(screenWidth);

			}

			headerView = new View(getActivity());
			headerView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, TOP_HEIGHT));
			String[] strings = getResources().getStringArray(R.array.list_content);
			strings[0]= "Hours : /n Monday : 7:30 am - 11:00 pm \n Tuesday : 7:30 am - 11:00 pm \n Wednesday : 7:30 am - 11:00 pm \n Thursday 7:30 am - 11:00 pm \n Friday 7:30 am - 11:00 pm";
	        MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(getActivity(), strings);
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
