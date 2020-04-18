package com.developer.rjtech.pdcbmsce.Utils;



import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that stores fragments for tabs
 */
public class AboutSectionPageAdapter extends FragmentPagerAdapter {

    private static final String TAG = "AboutSectionPageAdapter";

    private final List<Fragment> mFragmentList = new ArrayList<>();


    public AboutSectionPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }


    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment){
        mFragmentList.add(fragment);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {

            case 0:
                return "College";
            case 1:
                return "Faculty";
            case 2:
                return "Student";
            default:
                return "";

        }


    }
}