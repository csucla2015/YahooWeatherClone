package com.npi.blureffect;

import java.io.File;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;

public class MainActivity extends Activity {

	private static final String BLURRED_IMG_PATH = "blurred_image.png";
	private static final int TOP_HEIGHT = 800;
	private ListView mList;
	
	
	private ImageView mBlurredImage;
	private View headerView;
	private ImageView mNormalImage;
	private ScrollableImageView mBlurredImageHeader;
	private Switch mSwitch;
	private float alpha;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_main);

		// Get the screen width
		final int screenWidth = ImageUtils.getScreenWidth(this);

		// Find the view
		mBlurredImage = (ImageView) findViewById(R.id.blurred_image);
		mNormalImage = (ImageView) findViewById(R.id.normal_image);
		mBlurredImageHeader = (ScrollableImageView) findViewById(R.id.blurred_image_header);
		mSwitch = (Switch) findViewById(R.id.background_switch);
		mList = (ListView) findViewById(R.id.list);

		// prepare the header ScrollableImageView
		mBlurredImageHeader.setScreenWidth(screenWidth);

		// Action for the switch
		mSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					mBlurredImage.setAlpha(alpha);
				} else {
					mBlurredImage.setAlpha(0f);

				}

			}
		});

		// Try to find the blurred image
		final File blurredImage = new File(getFilesDir() + BLURRED_IMG_PATH);
		if (!blurredImage.exists()) {

			// launch the progressbar in ActionBar
			setProgressBarIndeterminateVisibility(true);

			new Thread(new Runnable() {

				@Override
				public void run() {

					// No image found => let's generate it!
					BitmapFactory.Options options = new BitmapFactory.Options();
					options.inSampleSize = 2;
					Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.image, options);
					Bitmap newImg = Blur.fastblur(MainActivity.this, image, 20);
					ImageUtils.storeImage(newImg, blurredImage);
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							updateView(screenWidth);

							// And finally stop the progressbar
							setProgressBarIndeterminateVisibility(false);
						}
					});

				}
			}).start();

		} else {

			// The image has been found. Let's update the view
			updateView(screenWidth);

		}

		String[] strings = getResources().getStringArray(R.array.list_content);
		String[] headers = new String[10];
		headers[0] = "Powell Library";
		headers[2] = "Hours";
		headers[4] = "Laptop Availability";
		headers[6] = "Contact";
		headers[8] = "Room Reservations";
		strings[0] = "Open Now \nStudy Rooms Available : 9 \nLaptops Available : 10";
		strings[2]= "Monday : 7:30 am - 11:00 pm \nTuesday : 7:30 am - 11:00 pm \nWednesday : 7:30 am - 11:00 pm \nThursday 7:30 am - 11:00 pm \nFriday 7:30 am - 11:00 pm";
		strings[4] = "Total Laptops Available = 10";
		strings[6] = "Under Construction";
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
		headerView = new View(this);
		headerView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, TOP_HEIGHT));

		// Prepare the header view for our list
		MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(this, strings, headers);
		mList.addHeaderView(headerView);

	    mList.setAdapter(adapter);
		/*headerView = new View(this);
		headerView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, TOP_HEIGHT));
		mList.addHeaderView(headerView);		*/
	    //mList.setAdapter(new ArrayAdapter<String>(this, R.layout.list_item, strings));
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
				if (mSwitch.isChecked()) {
					mBlurredImage.setAlpha(alpha);
				}

				// Parallax effect : we apply half the scroll amount to our
				// three views
				mBlurredImage.setTop(headerView.getTop() / 2);
				mNormalImage.setTop(headerView.getTop() / 2);
				mBlurredImageHeader.handleScroll(headerView.getTop() / 2);

			}
		});
	}

	private void updateView(final int screenWidth) {
		Bitmap bmpBlurred = BitmapFactory.decodeFile(getFilesDir() + BLURRED_IMG_PATH);
		bmpBlurred = Bitmap.createScaledBitmap(bmpBlurred, screenWidth, (int) (bmpBlurred.getHeight()
				* ((float) screenWidth) / (float) bmpBlurred.getWidth()), false);

		mBlurredImage.setImageBitmap(bmpBlurred);

		mBlurredImageHeader.setoriginalImage(bmpBlurred);
	}
	
}
