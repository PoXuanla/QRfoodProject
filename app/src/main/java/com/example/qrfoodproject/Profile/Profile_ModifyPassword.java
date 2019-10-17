package com.example.qrfoodproject.Profile;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.qrfoodproject.MySingleton;
import com.example.qrfoodproject.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class Profile_ModifyPassword extends AppCompatActivity {
    EditText edtPassword, edtPassword1, edtoldPassword;
    Button commit;
    private String url = "http://120.110.112.96/using/Profile/edtPassword.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_modifypassword);

        setView(); //設定元件ID

        setButtonListener(); //設定按鈕監聽
    }

    private void setView() {
        edtPassword = findViewById(R.id.edtPassword);
        edtPassword1 = findViewById(R.id.edtPassword1);
        edtoldPassword = findViewById(R.id.edtoldPassword);
        commit = findViewById(R.id.commit);
    }

    private void setButtonListener() {
        commit.setOnClickListener(onclick);
    }

    Button.OnClickListener onclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(Profile_ModifyPassword.this, "更改成功", Toast.LENGTH_LONG).show();
                    finish();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.v("error1", error.toString());
                    error.printStackTrace();
                    try {
                        String responseBody = new String(error.networkResponse.data, Charset.forName("utf-8"));
                        JSONObject data = new JSONObject(responseBody);
                        String message = data.getString("message");
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {

                        Log.v("Error_Exception", e.getMessage());
                    }
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    SharedPreferences pref = getSharedPreferences("Data", MODE_PRIVATE);
                    String session = pref.getString("sessionID", "");
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("oldPassword", edtoldPassword.getText().toString());
                    params.put("newPassword", edtPassword.getText().toString());
                    params.put("sessionID", session);
                    return params;
                }
            };
            MySingleton.getInstance(Profile_ModifyPassword.this).addToRequestQueue(stringRequest);
        }
    };

}

