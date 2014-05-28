package com.npi.blureffect;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
 
public class TabsPagerAdapter extends FragmentPagerAdapter {
	Article a = new Article();
	YRL y = new YRL();
	Powell p = new Powell();
	Mgmt m = new Mgmt();
	Main m1 = new Main();
 
    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }
 
    @Override
    public Fragment getItem(int index) {
 
        switch (index) {
        case 0:
            return m1;
        case 1:
            return y;
        case 2:
            return p;
        case 3:
        	return m;
        case 4:
        	return a;
        }
 
        return null;
    }
 
    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 5;
    }
 
}
