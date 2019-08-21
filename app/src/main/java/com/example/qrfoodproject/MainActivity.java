package com.example.qrfoodproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
        private EditText edtAccount, edtPassword;
        private Button Btn_login;
        private String login_url = "http://10.0.11.75/login.php";
        private String account="",password="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtAccount = findViewById(R.id.edtAccount);
        edtPassword = findViewById(R.id.edtPassword);
        Btn_login = findViewById(R.id.btn_login);
        Btn_login.setOnClickListener(btn_listener);

    }

    private Button.OnClickListener btn_listener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
             account = edtAccount.getText().toString().trim();
             password = edtPassword.getText().toString().trim();

            if (!account.isEmpty() && !password.isEmpty()) {
                checkInformation();
            } else {
                edtAccount.setError("Please input Account");
                edtPassword.setError("Please input Password");
                Toast.makeText(MainActivity.this,"缺少參數",Toast.LENGTH_LONG).show();
            }

        }
    };

    private void checkInformation() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, login_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                Intent intent = new Intent(getApplication(), Home_QRfood.class);
                startActivity(intent);
                Toast.makeText(MainActivity.this,"歡迎登入:" + account ,Toast.LENGTH_LONG).show();
                finish();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                try {
                    String responseBody = new String(error.networkResponse.data,Charset.forName("utf-8"));
                    JSONObject data = new JSONObject(responseBody);
                    String message = data.getString("message");

                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.getMessage();
                }
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("account", account);
                params.put("password",password);
                return params;
            }
        };
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}

