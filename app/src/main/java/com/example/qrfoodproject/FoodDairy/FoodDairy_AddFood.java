package com.example.qrfoodproject.FoodDairy;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.qrfoodproject.MySingleton;
import com.example.qrfoodproject.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FoodDairy_AddFood extends AppCompatActivity {


    Spinner Addfood_time,Addfood_location,Addfood_restaurant,Addfood_food;
    private String getRestaurant = "http://120.110.112.96/using/getFoodRestaurant.php";
    private int userSelectRestaurant;
    private String[] time = {"早","中","晚"};
    private String[] location = {"靜園","宜園","至善"};
    private String[][] restaurant = {{"白鬍子","極寶"},{"藍卡","買粥"},{"Yami快餐"}};
    private String[][][] food = {{{"綠茶","紅茶","珍珠奶茶"},{"鐵板麵","焗烤","炒飯"}},{{"水果茶"},{"海鮮粥","巧克力厚片"}},{{"豬排飯","烤牛肉飯"}}};
    ArrayAdapter<String> time_adapter,location_adapter,restaurant_adapter,food_adapter;
    ArrayList<String> array1 = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fooddairy_addfood);

        Addfood_time = (Spinner) findViewById(R.id.Addfood_time);
        Addfood_location = (Spinner) findViewById(R.id.Addfood_location);
        Addfood_restaurant = (Spinner) findViewById(R.id.Addfood_restaurant);
        Addfood_food = (Spinner) findViewById(R.id.Addfood_food);
        //設置各個spinner預設顯示的值

        time_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,time);
        Addfood_time.setAdapter(time_adapter);

        location_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,location);
        Addfood_location.setAdapter(location_adapter);

        restaurant_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,restaurant[0]);
        Addfood_restaurant.setAdapter(restaurant_adapter);

        food_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,food[0][0]);
        Addfood_food.setAdapter(food_adapter);

        //設定選擇地點，餐廳欄位跳出相對應的餐廳
        Addfood_location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int pos = Addfood_location.getSelectedItemPosition();
//                restaurant_adapter = new ArrayAdapter<String>(FoodDairy_AddFood.this,R.layout.support_simple_spinner_dropdown_item,restaurant[pos]);
//                Addfood_restaurant.setAdapter(restaurant_adapter);
                 getFoodRestaurant(pos);
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
//                int pos2 = Addfood_restaurant.getSelectedItemPosition();
//                food_adapter = new ArrayAdapter<String>(FoodDairy_AddFood.this,R.layout.support_simple_spinner_dropdown_item,food[pos][pos2]);
//                Addfood_food.setAdapter(food_adapter);
                  getRestaurantFood();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    private void getFoodRestaurant(int pos){
        final int position = pos;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, getRestaurant, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("onResponse",response.toString());
                try {
                    Log.v("Response",response);
                    ArrayList<String> array = new ArrayList<String>();
                    //解析JSON檔傳入array
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonObject1 = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonObject1.length(); i++) {
                        JSONObject c = jsonObject1.getJSONObject(i);
                        array.add(c.getString("rsName"));
                        array1.add(c.getString("rsName"));
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

}
