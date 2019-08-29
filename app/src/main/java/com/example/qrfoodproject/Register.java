package com.example.qrfoodproject;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    private EditText account, password, confirm_password, name, email, height, weight, exercise;
    private RadioGroup genderRadioGroup;
    private Button btn_register;
    private static String URL_REGISTER = "http://10.0.15.155:8080/qrfood_api/register.php";
    //private static String URL_REGISTER = "http://10.0.11.75/register.php";
    int gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_main);

        account = this.findViewById(R.id.Account);
        password = this.findViewById(R.id.Password);
        confirm_password = this.findViewById(R.id.Confirm_Password);
        name = this.findViewById(R.id.Name);
        email = this.findViewById(R.id.Email);
        genderRadioGroup = this.findViewById(R.id.gender_radio_group);
        height = this.findViewById(R.id.Height);
        weight = this.findViewById(R.id.Weight);
        exercise = this.findViewById(R.id.Exercise);
        btn_register = this.findViewById(R.id.btn_register);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (valiDate())
                    register();
            }
        });
        // -------------------------------------------------------
        TextView link_login = this.findViewById(R.id.link_login);
        link_login.setOnClickListener(link_loginListener);
    }
    private void register(){

        final String account = this.account.getText().toString().trim();
        final String password = this.password.getText().toString().trim();
        final String name = this.name.getText().toString().trim();
        final String email = this.email.getText().toString().trim();
        final String height = this.height.getText().toString().trim();
        final String weight = this.weight.getText().toString().trim();
        final String exercise = this.exercise.getText().toString().trim();

        int selectedId = genderRadioGroup.getCheckedRadioButtonId();
        if(selectedId == R.id.female_radio_btn)
            gender = 0;
        else
            gender = 1;


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGISTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                final ProgressDialog progressDialog = new ProgressDialog(Register.this);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Creating Account...");
                progressDialog.show();

                Toast.makeText(Register.this, "Register success!", Toast.LENGTH_SHORT).show();
                finish();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Toast.makeText(Register.this, "ErrorResponse! " + error.toString(), Toast.LENGTH_SHORT).show();
                        btn_register.setVisibility(View.VISIBLE);

                        error.printStackTrace();
                        try {
                            String responseBody = new String(error.networkResponse.data, Charset.forName("utf-8"));
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
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("account", account);
                params.put("password", password);
                params.put("name", name);
                params.put("email", email);
                params.put("gender", Integer.toString(gender));
                params.put("height", height);
                params.put("weight", weight);
                params.put("exercise", exercise);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    //-------------------------------
    //----建立邏輯認證是否生效----
    public boolean valiDate(){
        boolean valid = true;
        String Account = account.getText().toString();
        String Password = password.getText().toString();
        String Confirm_password = confirm_password.getText().toString();
        String Name = name.getText().toString();
        String Email = email.getText().toString();
        String Height = height.getText().toString();
        String Weight = weight.getText().toString();
        String Exercise = exercise.getText().toString();

        if(Account.isEmpty() || Account.length() <= 2){
            account.setError("至少要2個字元");
            valid = false;
        }
        else if(Account.length() > 20){
            account.setError("最多20個字元");
            valid = false;
        }
        else{
            account.setError(null);
        }

        if(Password.isEmpty() || Password.length() < 4){
            password.setError("至少要含有4個字母或數字");
            valid = false;
        }
        else if(Password.length() > 20){
            password.setError("最多20個字母或數字");
            valid = false;
        }
        else{
            password.setError(null);
        }

        if(Confirm_password.isEmpty() || !(Confirm_password.equals(Password))){
            confirm_password.setError("與輸入的密碼不相同");
            valid = false;
        }
        else{
            confirm_password.setError(null);
        }
        if(Name.isEmpty()){
            name.setError("請輸入姓名!");
            valid = false;
        }
        if(Email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            email.setError("請輸入有效的電子郵件");
            valid = false;
        }
        else{
            email.setError(null);
        }
        if(Height.isEmpty()){
            height.setError("請輸入身高");
            valid = false;
        }
        if(Weight.isEmpty()){
            weight.setError("請輸入體重");
            valid = false;
        }
        if(Exercise.isEmpty()){
            exercise.setError("請輸入運動量");
            valid = false;
        }
        return valid;
    }
    private TextView.OnClickListener link_loginListener = new TextView.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };
}
