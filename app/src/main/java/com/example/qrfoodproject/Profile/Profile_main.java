package com.example.qrfoodproject.Profile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.qrfoodproject.login.MainActivity;
import com.example.qrfoodproject.MySingleton;
import com.example.qrfoodproject.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Profile_main extends AppCompatActivity {
    Button modifyData, modifyPassword, logout;
    TextView account, password, name, gender, height, weight, exercise, email;
    private String logout_url = "http://120.110.112.96/using/destroy.php";
    private String print_profile_url = "http://120.110.112.96/using/getUserinformation.php";
    private String session_isExist_url = "http://120.110.112.96/using/session_isExist.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_main);

        modifyData = findViewById(R.id.modifyData);
        modifyPassword = findViewById(R.id.modifyPassword);
        logout = findViewById(R.id.logout);

        account = findViewById(R.id.account);
        password = findViewById(R.id.password);
        name = findViewById(R.id.name);
        gender = findViewById(R.id.gender);
        height = findViewById(R.id.height);
        weight = findViewById(R.id.weight);
        email = findViewById(R.id.email);
        exercise = findViewById(R.id.exercise);

        modifyData.setOnClickListener(onclick);
        modifyPassword.setOnClickListener(onclick);
        logout.setOnClickListener(onclick);

        print_profile(); //顯示個人資料
    }

    Button.OnClickListener onclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.modifyData:
                    StringRequest stringRequest1 = new StringRequest(Request.Method.POST, session_isExist_url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            startActivity(new Intent(Profile_main.this, Profile_ModifyData.class));
                            finish();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Profile_main.this, "請重新登入", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(Profile_main.this, MainActivity.class));
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
                    MySingleton.getInstance(Profile_main.this).addToRequestQueue(stringRequest1);
                    break;
                case R.id.modifyPassword:
                    StringRequest stringRequest2 = new StringRequest(Request.Method.POST, session_isExist_url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            startActivity(new Intent(Profile_main.this, Profile_ModifyPassword.class));
                            finish();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Profile_main.this, "請重新登入", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(Profile_main.this, MainActivity.class));
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
                    MySingleton.getInstance(Profile_main.this).addToRequestQueue(stringRequest2);
                    break;
                case R.id.logout:
                    SharedPreferences pref = getSharedPreferences("Data", MODE_PRIVATE);
                    final String session = pref.getString("sessionID", "");
                    pref.edit().clear().apply();
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, logout_url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();

                            params.put("sessionID", session);
                            return params;
                        }
                    };
                    MySingleton.getInstance(Profile_main.this).addToRequestQueue(stringRequest);
                    Intent intent1 = new Intent(Profile_main.this, MainActivity.class);

                    startActivity(intent1);
                    ActivityCompat.finishAffinity(Profile_main.this);
                    break;
            }
        }
    };

    private void print_profile() {
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, print_profile_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("response", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject data = jsonObject.getJSONObject("data");
                    account.setText(data.getString("account"));
                    password.setText(data.getString("password"));
                    name.setText(data.getString("name"));
                    email.setText(data.getString("email"));
                    gender.setText(data.getString("gender"));
                    height.setText(data.getString("height"));
                    weight.setText(data.getString("weight"));
                    exercise.setText(data.getString("exercise"));

                } catch (Exception e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Profile_main.this, "請重新登入", Toast.LENGTH_LONG).show();
                Log.v("ERROR", error.toString());
                startActivity(new Intent(Profile_main.this, MainActivity.class));
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
        MySingleton.getInstance(Profile_main.this).addToRequestQueue(jsonObjectRequest);
    }
}