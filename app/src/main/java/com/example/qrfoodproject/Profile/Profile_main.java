package com.example.qrfoodproject.Profile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.qrfoodproject.login.MainActivity;
import com.example.qrfoodproject.MySingleton;
import com.example.qrfoodproject.R;
import com.example.qrfoodproject.login.whenSessionInvalid;

import org.json.JSONException;
import org.json.JSONObject;


import java.util.HashMap;
import java.util.Map;

public class Profile_main extends AppCompatActivity {
    Button modifyData, modifyPassword, logout;
    TextView account, name, gender, height, weight, exercise, email;

    private String logout_url = "http://120.110.112.96/using/destroy_Session.php";
    private String print_profile_url = "http://120.110.112.96/using/Profile/getUserinformation.php";
    private String session_isExist_url = "http://120.110.112.96/using/session_isExist.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_main);

        setView();//設定元件ID

        setButtonListener(); //設定按鈕監聽

        print_profile(); //顯示個人資料
    }

    private void setView() {
        modifyData = findViewById(R.id.modifyData);
        modifyPassword = findViewById(R.id.modifyPassword);
        logout = findViewById(R.id.logout);
        account = findViewById(R.id.account);
        name = findViewById(R.id.name);
        gender = findViewById(R.id.gender);
        height = findViewById(R.id.height);
        weight = findViewById(R.id.weight);
        email = findViewById(R.id.email);
        exercise = findViewById(R.id.exercise);
    }

    private void setButtonListener() {
        //設定「修改資料」、「修改密碼」、「登出」按鈕
        modifyData.setOnClickListener(onclick);
        modifyPassword.setOnClickListener(onclick);
        logout.setOnClickListener(onclick);
    }

    Button.OnClickListener onclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                //前往「修改資料」
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
                            new whenSessionInvalid().informing(Profile_main.this, error);
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            //取得sessionID
                            SharedPreferences pref = getSharedPreferences("Data", MODE_PRIVATE);
                            String session = pref.getString("sessionID", "");

                            Map<String, String> map = new HashMap<>();
                            map.put("sessionID", session);
                            return map;
                        }
                    };
                    MySingleton.getInstance(Profile_main.this).addToRequestQueue(stringRequest1);
                    break;

                //前往「修改密碼」
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
                            new whenSessionInvalid().informing(Profile_main.this, error);
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
                    MySingleton.getInstance(Profile_main.this).addToRequestQueue(stringRequest2);
                    break;

                //前往「登出」
                case R.id.logout:
                    SharedPreferences pref = getSharedPreferences("Data", MODE_PRIVATE);
                    final String session = pref.getString("sessionID", "");
                    pref.edit().clear().apply();
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, logout_url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //Anything to do?
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            new whenSessionInvalid().informing(Profile_main.this, error);
                            ActivityCompat.finishAffinity(Profile_main.this);
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();

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
                    name.setText(data.getString("name"));
                    email.setText(data.getString("email"));

                    if (data.getString("gender").equals(true)) {
                        gender.setText("Female");
                    } else {
                        gender.setText("Male");
                    }

                    height.setText(data.getString("height"));
                    weight.setText(data.getString("weight"));
                    exercise.setText(data.getString("exercise"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new whenSessionInvalid().informing(Profile_main.this, error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                //取得sessionID
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