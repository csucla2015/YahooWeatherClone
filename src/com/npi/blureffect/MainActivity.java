package com.npi.blureffect;

import java.io.File;
import com.jeremyfeinstein.slidingmenu.lib.*;


import android.app.ActionBar;
import android.app.ActionBar.Tab;
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

public class MainActivity extends FragmentActivity implements ActionBar.TabListener {

//	private static final String BLURRED_IMG_PATH = "image.jpg";
//	private static final int TOP_HEIGHT = 700;
	
//	private ImageView mBlurredImage;
//	private View headerView;
//	private ImageView mNormalImage;
	//private ScrollableImageView mBlurredImageHeader;
	//private Switch mSwitch;
	//private float alpha;
	
	

	private SlidingMenu menu;
	private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
	private ActionBar actionBar;
	
	private String[] tabs = { "YRL", "Powell", "Mgmt" };
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_main);
//		View decorView = getWindow().getDecorView();
//		// Hide the status bar.
//		int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
//		decorView.setSystemUiVisibility(uiOptions);

		// Get the screen width
		final int screenWidth = ImageUtils.getScreenWidth(this);
		
		//Set up view pager
		viewPager = (ViewPager) findViewById(R.id.pager);
		actionBar = getActionBar();
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
 
        viewPager.setAdapter(mAdapter);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);  

 
        // Adding Tabs
        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener(this));
        }
		
        /**
         * on swiping the viewpager make respective tab selected
         * */
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
 
            @Override
            public void onPageSelected(int position) {
                // on changing the page
                // make respected tab selected
                actionBar.setSelectedNavigationItem(position);
            }
 
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }
 
            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
        
        actionBar.hide();
     
		

		// Find the view
//		mBlurredImage = (ImageView) findViewById(R.id.blurred_image);
//		mNormalImage = (ImageView) findViewById(R.id.normal_image);
//		
//
//		// Try to find the blurred image
//		final File blurredImage = new File(getFilesDir() + BLURRED_IMG_PATH);
//		if (!blurredImage.exists()) {
//
//			// launch the progressbar in ActionBar
//			setProgressBarIndeterminateVisibility(true);
//
//			new Thread(new Runnable() {
//
//				@Override
//				public void run() {
//
//					// No image found => let's generate it!
//					BitmapFactory.Options options = new BitmapFactory.Options();
//					options.inSampleSize = 2;
//					Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.image, options);
//					Bitmap newImg = Blur.fastblur(MainActivity.this, image, 7);
//					ImageUtils.storeImage(newImg, blurredImage);
//					runOnUiThread(new Runnable() {
//
//						@Override
//						public void run() {
//							updateView(screenWidth);
//
//							// And finally stop the progressbar
//							setProgressBarIndeterminateVisibility(false);
//						}
//					});
//
//				}
//			}).start();
//
//		} else {
//
//			// The image has been found. Let's update the view
//			//updateView(screenWidth);
//
//		}

//		String[] strings = getResources().getStringArray(R.array.list_content);
//		strings[0]= "Hours : /n Monday : 7:30 am - 11:00 pm \n Tuesday : 7:30 am - 11:00 pm \n Wednesday : 7:30 am - 11:00 pm \n Thursday 7:30 am - 11:00 pm \n Friday 7:30 am - 11:00 pm";
		// Prepare the header view for our list
//		headerView = new View(this);
//		headerView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, TOP_HEIGHT));
		
		
		
//		menu = new SlidingMenu(this);
//		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
//		menu.setShadowWidthRes(R.dimen.shadow_width);
//		menu.setShadowDrawable(R.drawable.shadow);
//		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
//		menu.setFadeDegree(0.35f);
//		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
//		menu.setMenu(R.layout.menu_frame);
//		getSupportFragmentManager()
//		.beginTransaction()
//		.replace(R.id.menu_frame, new SampleListFragment())
//		.commit();
		
		actionBar.hide();

    }
 



//	private void updateView(final int screenWidth) {
//		Bitmap bmpBlurred = BitmapFactory.decodeFile(getFilesDir() + BLURRED_IMG_PATH);
//		bmpBlurred = Bitmap.createScaledBitmap(bmpBlurred, screenWidth, (int) (bmpBlurred.getHeight()
//				* ((float) screenWidth) / (float) bmpBlurred.getWidth()), false);
//
//		mBlurredImage.setImageBitmap(bmpBlurred);
//
//		//mBlurredImageHeader.setoriginalImage(bmpBlurred);
//	}
//	
    @Override
    public void onTabReselected(Tab tab, FragmentTransaction ft) {
    }
 
    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft) {
        // on tab selected
        // show respected fragment view
        viewPager.setCurrentItem(tab.getPosition());
    }
 
    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
    }
}
