package com.npi.blureffect;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
 
public class TabsPagerAdapter extends FragmentPagerAdapter {
<<<<<<< HEAD
	Article a = new Article();
	YRL y = new YRL();
	Powell p = new Powell();
	Mgmt m = new Mgmt();
	Main m1 = new Main();
=======
	
	YRL y = new YRL();
	Powell p = new Powell();
	Mgmt m = new Mgmt();
>>>>>>> 5bee727d8bb8a4ad0e2700855828786b04839b65
 
    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }
 
    @Override
    public Fragment getItem(int index) {
 
        switch (index) {
        case 0:
<<<<<<< HEAD
            return m1;
        case 1:
            return y;
        case 2:
            return p;
        case 3:
        	return m;
        case 4:
        	return a;
=======
            return y;
        case 1:
            return p;
        case 2:
            return m;
>>>>>>> 5bee727d8bb8a4ad0e2700855828786b04839b65
        }
 
        return null;
    }
 
    @Override
    public int getCount() {
        // get item count - equal to number of tabs
<<<<<<< HEAD
        return 5;
=======
        return 3;
>>>>>>> 5bee727d8bb8a4ad0e2700855828786b04839b65
    }
 
}
