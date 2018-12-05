package com.kmu.blockscheduling;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter{
    int mNumofTabs;

    public PagerAdapter(FragmentManager fm, int NumofTabs) {
        super(fm);
        this.mNumofTabs = NumofTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                CurrentFragment current = new CurrentFragment();
                return current;
            case 1:
                PastFragment past = new PastFragment();
                return past;
             default:
                 return null;
        }
    }

    @Override
    public int getCount() {
        return mNumofTabs;
    }
}
