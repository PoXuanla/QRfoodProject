package com.example.qrfoodproject.FoodDairy.calen;

import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.qrfoodproject.FoodDairy.FoodDairy_Fragment_breakfast;
import com.example.qrfoodproject.FoodDairy.FoodDairy_Fragment_dinner;
import com.example.qrfoodproject.FoodDairy.FoodDairy_Fragment_lunch;
import com.example.qrfoodproject.FoodDairy.FoodDairy_main;
import com.example.qrfoodproject.FoodDairy.ViewPagerFragmentAdapter;
import com.example.qrfoodproject.R;

import java.util.ArrayList;
import java.util.List;

public class FoodDairy_Calen_date extends AppCompatActivity {
    private TextView date_calen_title;
    private TabLayout tablayout_calen;
    private ViewPager viewpager_calen;
    private String[] IconName = {"早餐","中餐","晚餐"};
    public static FoodDairy_Calen_date instance = null; //用來取得當前頁面
    public static String FoodDairy_Calen_date_date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fooddairy_calen_date);

        instance = this;//用來取得當前頁面
        tablayout_calen = (TabLayout) findViewById(R.id.tablayout_calen);
        viewpager_calen = (ViewPager) findViewById(R.id.viewpager_calen);
        date_calen_title = (TextView)findViewById(R.id.date_calen_title);
        date_calen_title.setText(FoodDairy_Calen.date);

        //設定Tablayout 與 ViewPager 連動
        for(int i =0 ;i<3;i++)
            tablayout_calen.addTab(tablayout_calen.newTab());
        setViewPager();
        tablayout_calen.setupWithViewPager(viewpager_calen);
        setTabIcon();
    }
    private void setViewPager(){
        FoodDairy_Fragment_breakfast myFragment1 = new FoodDairy_Fragment_breakfast();
        FoodDairy_Fragment_lunch myFragment2 = new FoodDairy_Fragment_lunch();
        FoodDairy_Fragment_dinner myFragment3 = new FoodDairy_Fragment_dinner();
        List<Fragment> fragmentList = new ArrayList<Fragment>();
        fragmentList.add(myFragment1);
        fragmentList.add(myFragment2);
        fragmentList.add(myFragment3);
        ViewPagerFragmentAdapter myFragmentAdapter = new ViewPagerFragmentAdapter(getSupportFragmentManager(), fragmentList);
        viewpager_calen.setAdapter(myFragmentAdapter);
    }
    private void setTabIcon(){
        for(int i = 0 ; i<IconName.length ; i++){
            tablayout_calen.getTabAt(i).setText(IconName[i]);
        }
    }
    //給FoodDairy_AddFood調用，用途:關閉此頁面
    @Override
    public void finish() {
        super.finish();
        instance = null;
    }
}
