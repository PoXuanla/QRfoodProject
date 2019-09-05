package com.example.qrfoodproject;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class FoodFile_PagerAdapter extends FragmentStatePagerAdapter{

    int mNoOfTabs;

    public FoodFile_PagerAdapter(FragmentManager fm, int NumberOfTabs) {
        super(fm);
        this.mNoOfTabs = NumberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){

            case 0:
                FoodFile01 foodFile01 = new FoodFile01();
                return foodFile01;
            case 1:
                FoodFile02 foodFile02 = new FoodFile02();
                return foodFile02;
            case 2:
                FoodFile03 foodFile03 = new FoodFile03();
                return foodFile03;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNoOfTabs;
    }
}
