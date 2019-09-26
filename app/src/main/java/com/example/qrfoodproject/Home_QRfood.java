package com.example.qrfoodproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.qrfoodproject.FoodDairy.FoodDairy_main;
import com.example.qrfoodproject.NutritionInform.NutritionInform;
import com.example.qrfoodproject.Profile.Profile_main;
import com.example.qrfoodproject.Qrcode.Qrcode_main;
import com.example.qrfoodproject.Qrcode.ScanQrcode;
import com.example.qrfoodproject.login.MainActivity;


import java.util.HashMap;
import java.util.Map;

public class Home_QRfood extends AppCompatActivity {
    Button personalData,btn_qrcode,FoodDairy;
    TextView account;
    private  String session_isExist_url = "http://120.110.112.96/using/session_isExist.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_qrfood);

        account = findViewById(R.id.account);
        btn_qrcode = findViewById(R.id.btn_qrcode);
        FoodDairy = findViewById(R.id.FoodDairy);

        SharedPreferences pref = getSharedPreferences("Data", MODE_PRIVATE);
        String session = pref.getString("sessionID", "");
        account.setText(session);

        session_isExist();

        //進入個人檔案
        personalData = findViewById(R.id.personalData);
        personalData.setOnClickListener(onclick);

        //進入Qrcode
        btn_qrcode.setOnClickListener(onclick);
        // Home_QRfood點進『營養抓寶站』
        Button nutritionInform = this.findViewById(R.id.nutritioninform);
        nutritionInform.setOnClickListener(nutritionInformListener);
        //進入飲食日誌
        FoodDairy.setOnClickListener(onclick);
    }
    Button.OnClickListener onclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.personalData:
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, session_isExist_url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            startActivity(new Intent(Home_QRfood.this, Profile_main.class));
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Home_QRfood.this,"請重新登入",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(Home_QRfood.this, MainActivity.class));
                            finish();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            SharedPreferences pref = getSharedPreferences("Data", MODE_PRIVATE);
                            String session = pref.getString("sessionID", "");
                            Map<String, String> map = new HashMap<String, String>();
                            map.put("sessionID", session);
                            return map;
                        }


                    };
                    MySingleton.getInstance(Home_QRfood.this).addToRequestQueue(stringRequest);
                    break;
                case R.id.btn_qrcode:
                    startActivity(new Intent(Home_QRfood.this, ScanQrcode.class));
                    break;
                case R.id.FoodDairy:
                    startActivity(new Intent(Home_QRfood.this, FoodDairy_main.class));

            }

        }

    };
    // Home_QRfood點進『營養抓寶站』
    private Button.OnClickListener nutritionInformListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Home_QRfood.this, NutritionInform.class);
            startActivity(intent);
        }
    };
    private void session_isExist(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, session_isExist_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //startActivity(new Intent(Home_QRfood.this,Profile_main.class));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Home_QRfood.this,"請重新登入",Toast.LENGTH_LONG).show();

                startActivity(new Intent(Home_QRfood.this,MainActivity.class));
                finish();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                SharedPreferences pref = getSharedPreferences("Data", MODE_PRIVATE);
                String session = pref.getString("sessionID", "");
                Map<String, String> map = new HashMap<String, String>();
                map.put("sessionID", session);
                return map;
            }


        };
        MySingleton.getInstance(Home_QRfood.this).addToRequestQueue(stringRequest);
    }
}
