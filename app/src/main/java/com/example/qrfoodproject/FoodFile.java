package com.example.qrfoodproject;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class FoodFile extends AppCompatActivity implements FoodFile01.OnFragmentInteractionListener, FoodFile02.OnFragmentInteractionListener, FoodFile03.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foodfile_main);
        TabLayout tabLayout = this.findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("靜園餐廳"));
        tabLayout.addTab(tabLayout.newTab().setText("宜園餐廳"));
        tabLayout.addTab(tabLayout.newTab().setText("至善美食廣場"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = this.findViewById(R.id.pager);
        final FoodFile_PagerAdapter adapter = new FoodFile_PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
