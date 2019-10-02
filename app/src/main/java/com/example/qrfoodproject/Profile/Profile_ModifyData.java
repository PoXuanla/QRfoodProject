package com.example.qrfoodproject.Profile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.qrfoodproject.MySingleton;
import com.example.qrfoodproject.R;
import com.example.qrfoodproject.login.whenSessionInvalid;

import java.util.HashMap;
import java.util.Map;

public class Profile_ModifyData extends AppCompatActivity {
    EditText email, name, height, weight, exercise;
    RadioButton female, male;
    Button commit;
    int is_Gender = 1;
    private String url = "http://120.110.112.96/using/updateUserInform.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_modifydata);

        setView(); //設定元件ID

        setButtonListener(); //設定按鈕監聽

    }

    private void setView() {
        email = findViewById(R.id.edtEmail);
        name = findViewById(R.id.edtName);
        height = findViewById(R.id.edtHeight);
        weight = findViewById(R.id.edtWeight);
        exercise = findViewById(R.id.edtExercise);
        female = findViewById(R.id.female_radio_btn);
        male = findViewById(R.id.male_radio_btn);
        commit = findViewById(R.id.commit);
    }

    private void setButtonListener() {
        female.setOnCheckedChangeListener(onClick);
        male.setOnCheckedChangeListener(onClick);
        commit.setOnClickListener(onclick);
    }

    //radio按鈕監聽
    CompoundButton.OnCheckedChangeListener onClick = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            switch (buttonView.getId()) {
                case R.id.male_radio_btn:
                    is_Gender = 1;
                    break;
                case R.id.female_radio_btn:
                    is_Gender = 2;
                    break;
            }
        }
    };
    //按鈕監聽
    Button.OnClickListener onclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            editUserInform();
            startActivity(new Intent(Profile_ModifyData.this, Profile_main.class));
            finish();
        }
    };

    private void editUserInform() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("response1", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new whenSessionInvalid().informing(Profile_ModifyData.this, error);
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                SharedPreferences pref = getSharedPreferences("Data", MODE_PRIVATE);
                String session = pref.getString("sessionID", "");
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name.getText().toString());
                params.put("email", email.getText().toString());
                params.put("height", height.getText().toString());
                params.put("weight", weight.getText().toString());
                params.put("gender", Integer.toString(is_Gender));
                params.put("exercise", exercise.getText().toString());
                params.put("sessionID", session);
                return params;
            }
        };
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }


}
