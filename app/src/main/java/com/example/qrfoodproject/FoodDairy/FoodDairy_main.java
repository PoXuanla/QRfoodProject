package com.example.qrfoodproject.FoodDairy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.qrfoodproject.R;
import com.example.qrfoodproject.login.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class FoodDairy_main extends AppCompatActivity {
    private ImageButton calen;
    private TabLayout tablayout;
    private ViewPager viewpager;
    public static FoodDairy_main instance = null;
    private String[] IconName = {"早餐","中餐","晚餐"};
    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.fooddairy_main);
        instance = this;
        calen = (ImageButton) findViewById(R.id.calen);
        tablayout = (TabLayout) findViewById(R.id.tablayout);
        viewpager = (ViewPager) findViewById(R.id.viewpager);

        Toast.makeText(getApplication(),"ff",Toast.LENGTH_LONG).show();

        //設定Tablayout 與 ViewPager 連動
        for(int i =0 ;i<3;i++)
            tablayout.addTab(tablayout.newTab());
        setViewPager();
        tablayout.setupWithViewPager(viewpager);
        setTabIcon();
        //設定日曆的監聽
      //  calen.setOnClickListener(calenListener);
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
        viewpager.setAdapter(myFragmentAdapter);
    }
    private void setTabIcon(){
        for(int i = 0 ; i<3 ; i++){
            tablayout.getTabAt(i).setText(IconName[i]);
        }
    }
    private Button.OnClickListener calenListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(FoodDairy_main.this, MainActivity.class);
            startActivity(intent);
        }
    };

    @Override
    public void finish() {
        super.finish();
        instance = null;
    }
}
