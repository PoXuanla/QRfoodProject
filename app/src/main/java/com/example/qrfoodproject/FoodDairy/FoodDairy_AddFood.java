package com.example.qrfoodproject.FoodDairy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.qrfoodproject.FoodDairy.calen.FoodDairy_Calen;
import com.example.qrfoodproject.FoodDairy.calen.FoodDairy_Calen_date;
import com.example.qrfoodproject.MySingleton;
import com.example.qrfoodproject.R;
import com.example.qrfoodproject.login.MainActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FoodDairy_AddFood extends AppCompatActivity {


    Spinner Addfood_time,Addfood_location,Addfood_restaurant,Addfood_food;
    EditText Addfood_serving;
    Button Addfood_input;
    private String getRestaurant = "http://120.110.112.96/using/FD_getRes.php";
    private String getRestaurantFood = "http://120.110.112.96/using/FD_getResFood.php";
    private String addRecord = "http://120.110.112.96/using/addrecord.php";
    private int userSelectRestaurant;
    private String[] time = {"早","午","晚"};
    private String[] location = {"靜園","宜園","至善"};
    private String times ; //接收使用者在哪個頁面新增餐點
    ArrayAdapter<String> time_adapter,location_adapter,restaurant_adapter,food_adapter;
    ArrayList<String> array1 = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fooddairy_addfood);
        //取得intent的值
        Intent intent = getIntent();
        times = intent.getStringExtra("times");

        Addfood_time = (Spinner) findViewById(R.id.Addfood_time);
        Addfood_location = (Spinner) findViewById(R.id.Addfood_location);
        Addfood_restaurant = (Spinner) findViewById(R.id.Addfood_restaurant);
        Addfood_food = (Spinner) findViewById(R.id.Addfood_food);
        Addfood_serving = (EditText) findViewById(R.id.Addfood_serving);
        Addfood_input = (Button)findViewById(R.id.Addfood_input);
        //設置各個spinner預設顯示的值

        time_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,time);
        Addfood_time.setAdapter(time_adapter);
        Addfood_time.setSelection(FindTimeIndex(times));
        location_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,location);
        Addfood_location.setAdapter(location_adapter);

        Addfood_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, addRecord, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("onResponse",response.toString());
                        //關閉FoodDairy_main/FoodDairy_Calen_date的頁面
                        if(FoodDairy_Calen_date.instance != null) {
                            try {
                                FoodDairy_Calen_date.instance.finish();
                                Intent intent = new Intent(FoodDairy_AddFood.this, FoodDairy_Calen_date.class);
                                startActivity(intent);
                                finish();
                            } catch (Exception e) {}
                        }else{
                            try{
                                FoodDairy_main.instance.finish();
                                Intent intent = new Intent(FoodDairy_AddFood.this, FoodDairy_main.class);
                                startActivity(intent);
                                finish();
                            }catch (Exception e){}
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("ErrorResponse",error.toString());
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        //取得session
                        SharedPreferences pref = FoodDairy_AddFood.this.getSharedPreferences("Data", MODE_PRIVATE);
                        String session = pref.getString("sessionID", "");

                        String strDate;
                        //取得當天時間
                        if(FoodDairy_Calen_date.instance != null){
                            strDate = FoodDairy_Calen.date_format;
                        }else{
                            SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
                            Date date = new Date();
                            strDate = sdFormat.format(date);
                        }


                        Map<String, String> params = new HashMap<String, String>();
                        params.put("sessionID",session);
                        params.put("time",Addfood_time.getSelectedItem().toString());
                        params.put("date",strDate);
                        params.put("fdName", Addfood_food.getSelectedItem().toString());
                        params.put("serving", Addfood_serving.getText().toString());
                        return params;
                    }
                };
                MySingleton.getInstance(FoodDairy_AddFood.this).addToRequestQueue(stringRequest);
            }
        });
        //設定選擇地點，餐廳欄位跳出相對應的餐廳
        Addfood_location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int pos = Addfood_location.getSelectedItemPosition();
//
                  getRestaurant(pos);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //設定選擇餐廳，食物欄位跳出該餐廳食物
        Addfood_restaurant.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                int pos = Addfood_location.getSelectedItemPosition();
                  int pos = Addfood_restaurant.getSelectedItemPosition();
//                food_adapter = new ArrayAdapter<String>(FoodDairy_AddFood.this,R.layout.support_simple_spinner_dropdown_item,food[pos][pos2]);
//                Addfood_food.setAdapter(food_adapter);
                  getRestaurantFood(pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    private void getRestaurant(int pos){
        final int position = pos;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, getRestaurant, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("onResponse",response.toString());
                try {
                    Log.v("success_getRes",response);
                    ArrayList<String> array = new ArrayList<String>();
                    //解析JSON檔傳入array
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonObject1 = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonObject1.length(); i++) {
                        JSONObject c = jsonObject1.getJSONObject(i);

                        array.add(c.getString("rsName"));
                        array1.add(c.getString("rsId"));
                    }
                    restaurant_adapter = new ArrayAdapter<String>(FoodDairy_AddFood.this,R.layout.support_simple_spinner_dropdown_item,array);
                    Addfood_restaurant.setAdapter(restaurant_adapter);
                }catch (Exception e){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("ErrorResponse",error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("location",location[position]);
                return params;
            }
        };
        MySingleton.getInstance(FoodDairy_AddFood.this).addToRequestQueue(stringRequest);
    }
    private void getRestaurantFood(int pos){
        final int position = pos;
        Log.v("getRestaurant",Addfood_restaurant.getSelectedItem().toString());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, getRestaurantFood, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("onResponse",response.toString());
                try {
                    Log.v("success_getResFood",response);
                    ArrayList<String> array = new ArrayList<String>();

                    //解析JSON檔傳入array
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonObject1 = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonObject1.length(); i++) {
                        JSONObject c = jsonObject1.getJSONObject(i);
                        array.add(c.getString("fdName"));
                    }
                    food_adapter = new ArrayAdapter<String>(FoodDairy_AddFood.this,R.layout.support_simple_spinner_dropdown_item,array);
                    Addfood_food.setAdapter(food_adapter);
                }catch (Exception e){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("Error_getResFood",error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("rsName",Addfood_restaurant.getSelectedItem().toString());
                return params;
            }
        };
        MySingleton.getInstance(FoodDairy_AddFood.this).addToRequestQueue(stringRequest);
    }
    private int FindTimeIndex(String times){
        int i;
        for(i=0;i<time.length;i++){
            if(times.equals(time[i])){
                break;
            }
        }
        return i;
    }
}
