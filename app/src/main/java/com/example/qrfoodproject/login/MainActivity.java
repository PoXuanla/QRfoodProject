package com.example.qrfoodproject.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.charset.Charset;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.qrfoodproject.Home_QRfood;
import com.example.qrfoodproject.MySingleton;
import com.example.qrfoodproject.R;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;


import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText edtAccount, edtPassword;
    private Button Btn_login;
    private String login_url = "http://120.110.112.96/using/Login/login.php";
    private String account = "", password = "";
    private TextView link_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setView();//設定元件ID

        setButtonListener(); //設定按鈕監聽


    }

    private void setButtonListener() {
        Btn_login.setOnClickListener(btn_listener);
        link_register.setOnClickListener(link_registerListener);
    }

    private void setView() {
        edtAccount = findViewById(R.id.edtAccount);
        edtPassword = findViewById(R.id.edtPassword);
        Btn_login = findViewById(R.id.btn_login);
        link_register = findViewById(R.id.link_register);
    }

    // 進入註冊畫面
    Button.OnClickListener btn_listener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            account = edtAccount.getText().toString().trim();
            password = edtPassword.getText().toString().trim();

            if (!account.isEmpty() && !password.isEmpty()) {
                checkInformation();
            } else {
                if (account.isEmpty()) edtAccount.setError("Please input Account");
                else if (password.isEmpty()) edtPassword.setError("Please input Password");
                else Log.e("Weird Error", "Unexpected error occurred at Login State");
            }

        }
    };

    private void checkInformation() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, login_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.v("loginSuccess",response);
                    JSONObject responseData = new JSONObject(response);
                    JSONObject data = responseData.getJSONObject("data");
                    String sessionID = data.getString("sessionID");
                    SharedPreferences sharedPreferences = getSharedPreferences("Data", Context.MODE_PRIVATE);
                    sharedPreferences.edit().putString("sessionID", sessionID).apply();
                    //using apply() instead of commit(), later one uses persistent storage but apply() handles at background

                    Log.v("sessionID", sessionID);
                    Log.v("checkInform", response);
                    Intent intent = new Intent(getApplication(), Home_QRfood.class);
                    startActivity(intent);
                    Toast.makeText(MainActivity.this, "歡迎登入:" + account, Toast.LENGTH_LONG).show();
                    finish();

                } catch (Exception e) {
                    Log.v("Exception", e.getMessage());
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                try {
                    String responseBody = new String(error.networkResponse.data, Charset.forName("utf-8"));
                    JSONObject data = new JSONObject(responseBody);
                    String message = data.getString("message");
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {

                    Log.v("Error_Exception", e.getMessage());
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("account", account);
                params.put("password", password);
                return params;
            }
        };
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private TextView.OnClickListener link_registerListener = new TextView.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Start the Sign up activity
            Intent intent = new Intent(MainActivity.this, Register.class);
            startActivity(intent);
        }
    };


}

