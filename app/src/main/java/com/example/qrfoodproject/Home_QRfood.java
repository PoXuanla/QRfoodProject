package com.example.qrfoodproject;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.qrfoodproject.FoodDairy.FoodDairy_main;
import com.example.qrfoodproject.FoodFile.FoodFile_restaurant;
import com.example.qrfoodproject.NutritionInform.NutritionInform;
import com.example.qrfoodproject.Profile.Profile_main;
import com.example.qrfoodproject.Qrcode.ScanQrcode;
import com.example.qrfoodproject.login.MainActivity;
import com.example.qrfoodproject.login.whenSessionInvalid;


import java.util.HashMap;
import java.util.Map;

public class Home_QRfood extends AppCompatActivity {
    Button personalData, btn_qrcode, FoodDairy, nutritionInform, foodDocument;
    TextView account;
    private  String session_isExist_url = "http://120.110.112.96/using/session_isExist.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_qrfood);

        setView();

        setClick();

        setTitleSession(); //設置主頁面session(測試用)，之後會改成使用者名稱

        session_atHome();
    }



    private void setView(){
        account = findViewById(R.id.account);
        btn_qrcode = findViewById(R.id.btn_qrcode);
        FoodDairy = findViewById(R.id.FoodDairy);
        nutritionInform = findViewById(R.id.nutritioninform);
        personalData = findViewById(R.id.personalData);
        foodDocument = findViewById(R.id.foodDocument);
    }

    private void setClick() {

        //進入食物檔案
        foodDocument.setOnClickListener(onclick);

        //進入個人資料
        personalData.setOnClickListener(onclick);

        //進入Qrcode相機
        btn_qrcode.setOnClickListener(onclick);

        //進『營養抓寶站』
        nutritionInform.setOnClickListener(onclick);

        //進入飲食日誌
        FoodDairy.setOnClickListener(onclick);

    }

    Button.OnClickListener onclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){

                case R.id.foodDocument:
                    session_isExist(Home_QRfood.this,R.id.foodDocument);
                    break;

                case R.id.FoodDairy:
                    session_isExist(Home_QRfood.this, R.id.FoodDairy);
                    break;
                case R.id.nutritioninform:
                    startActivity(new Intent(Home_QRfood.this, NutritionInform.class));
                    break;
                case R.id.personalData:
                    session_isExist(Home_QRfood.this, R.id.personalData);
                    break;
                case R.id.btn_qrcode:
                    startActivity(new Intent(Home_QRfood.this, ScanQrcode.class));
                    break;

            }

        }

    };

    private void setTitleSession() {
        SharedPreferences pref = getSharedPreferences("Data", MODE_PRIVATE);
        String session = pref.getString("sessionID", "");
        account.setText(session);
    }

    private void session_isExist(final Context context , final int buttonID){

        //此session判斷是用在使用者按下HOME裡面的任意按鈕時執行的
        context.getApplicationContext();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, session_isExist_url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                switch (buttonID){
                    case R.id.FoodDairy:
                        Log.d("Session: ", "still valid");
                        startActivity(new Intent(context, FoodDairy_main.class));
                        break;
                    case R.id.personalData:
                        Log.d("Session: ", "still valid");
                        startActivity(new Intent(context, Profile_main.class));
                        break;
                    case R.id.foodDocument:
                        startActivity(new Intent(context, FoodFile_restaurant.class));
                        break;
                    default:
                        break;
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Home_QRfood.this,"請重新登入",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(context , MainActivity.class));
                finish();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                SharedPreferences pref = getSharedPreferences("Data", MODE_PRIVATE);
                String session = pref.getString("sessionID", "");
                Map<String, String> map = new HashMap<>();
                map.put("sessionID", session);
                return map;
            }


        };
        MySingleton.getInstance(context).addToRequestQueue(stringRequest);


    }

    private void session_atHome(){

        //此session判斷是用在使用者重新打開app時，在HOME的生命週期處於onCreate()階段時處理的判斷
        StringRequest stringRequest = new StringRequest(Request.Method.POST, session_isExist_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(Home_QRfood.this, "Session still valid", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new whenSessionInvalid().informing(Home_QRfood.this, error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                SharedPreferences pref = getSharedPreferences("Data", MODE_PRIVATE);
                String session = pref.getString("sessionID", "");
                Map<String, String> map = new HashMap<>();
                map.put("sessionID", session);
                return map;
            }
        };
        MySingleton.getInstance(Home_QRfood.this).addToRequestQueue(stringRequest);
    }
}
