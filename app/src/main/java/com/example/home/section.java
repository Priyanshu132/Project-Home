package com.example.home;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class section extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_11, R.string.tab_text_12, R.string.tab_text_13,R.string.tab_text_14,R.string.tab_text_15, R.string.tab_text_16, R.string.tab_text_17};
    private final Context mContext;

    public section(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment=null;
        switch (position){
            case 0:
                fragment=new Monday();
                break;
            case 1:
                fragment=new Tuesday();
                break;
            case 2:
                fragment=new Wednesday();
                break;
            case 3:
                fragment=new Thursday();
                break;
            case 4:
                fragment=new Friday();
                break;
            case 5:
                fragment=new Saturday();
                break;
            case 6:
                fragment=new Sunday();

        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 7;
    }
}
