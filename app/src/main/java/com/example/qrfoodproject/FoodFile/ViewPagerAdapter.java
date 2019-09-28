package com.example.qrfoodproject.FoodFile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    String arr[] = {"靜園","宜園","至善"};
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
       Fragment frag = new FoodFile_res_Fragment();
       Bundle bundle = new Bundle();
       bundle.putString("location",arr[i]);
       frag.setArguments(bundle);
       return frag;

    }

    @Override
    public int getCount() {
        return 3;
    }

}
