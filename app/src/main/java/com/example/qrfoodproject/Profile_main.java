package com.example.qrfoodproject;

import android.app.DownloadManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.textclassifier.TextLinks;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Profile_main extends AppCompatActivity {
    Button modifyData,modifyPassword,logout;
    private String url = "http://192.168.0.108/destroy.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_main);

        modifyData = findViewById(R.id.modifyData);
        modifyPassword = findViewById(R.id.modifyPassword);
        logout = findViewById(R.id.logout);

        modifyData.setOnClickListener(onclick);
        modifyPassword.setOnClickListener(onclick);
        logout.setOnClickListener(onclick);
    }
    Button.OnClickListener onclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId() ){
                case R.id.modifyData:
                    Intent intent = new Intent(Profile_main.this,Profile_ModifyData.class);
                    startActivity(intent);
                    break;
                case R.id.modifyPassword:
                    startActivity(new Intent(Profile_main.this,Profile_ModifyPassword.class));
                    break;
                case R.id.logout:
                    SharedPreferences pref = getSharedPreferences("Data", MODE_PRIVATE);
                    final String session = pref.getString("sessionID", "");
                    pref.edit().clear().apply();
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();

                            params.put("sessionID",session);
                            return params;
                        }
                    };
                    MySingleton.getInstance(Profile_main.this).addToRequestQueue(stringRequest);
                    Intent intent1 = new Intent(Profile_main.this,MainActivity.class);

                    startActivity(intent1);
                    ActivityCompat.finishAffinity(Profile_main.this);
                    break;
            }
        }
    };
}
