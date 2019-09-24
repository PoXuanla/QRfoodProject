package com.example.qrfoodproject.Qrcode;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.example.qrfoodproject.MySingleton;
import com.example.qrfoodproject.R;
import org.json.JSONObject;

public class Qrcode_main extends AppCompatActivity {
    private String url = "http://120.110.112.96/using/getFoodInformationById.php?fdId=";
    static String QrcodeResult ;
    private TextView fdName,gram,calorie,protein,fat,saturateFat,transFat,cholesterol,sugar,dietaryFiber,sodium,calcium,potassium,ferrum;
    private String urll;
    private ImageView my_image_view;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrfood_main);

        my_image_view = findViewById(R.id.my_image_view);
        fdName = findViewById(R.id.fdName);
        gram = findViewById(R.id.gram);
        calorie = findViewById(R.id.calorie);
        protein = findViewById(R.id.protein);
        fat = findViewById(R.id.fat);
        saturateFat = findViewById(R.id.saturatedFat);
        transFat = findViewById(R.id.transFat);
        cholesterol = findViewById(R.id.cholesterol);
        sugar = findViewById(R.id.sugar);
        dietaryFiber = findViewById(R.id.dietaryFiber);
        sodium = findViewById(R.id.sodium);
        calcium = findViewById(R.id.calcium);
        potassium = findViewById(R.id.potassium);
        ferrum = findViewById(R.id.ferrum);
        //startActivity(new Intent(getApplicationContext(),ScanQrcode.class));
        print(); //呈現食物的詳細資料
    }
    private void print(){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url+QrcodeResult, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.v("Log",response);
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");


                    fdName.setText(jsonObject1.getString("fdName"));
                    gram.setText(jsonObject1.getString("gram")+"克");
                    calorie.setText(jsonObject1.getString("calorie")+"大卡");
                    protein.setText(jsonObject1.getString("protein"));
                    fat.setText(jsonObject1.getString("fat"));
                    saturateFat.setText(jsonObject1.getString("saturatedFat"));
                    transFat.setText(jsonObject1.getString("transFat"));
                    cholesterol.setText(jsonObject1.getString("cholesterol"));
                    sugar.setText(jsonObject1.getString("sugar"));
                    dietaryFiber.setText(jsonObject1.getString("dietaryFiber"));
                    sodium.setText(jsonObject1.getString("sodium")+"毫克");
                    calcium.setText(jsonObject1.getString("calcium")+"毫克");
                    potassium.setText(jsonObject1.getString("potassium")+"毫克");
                    ferrum.setText(jsonObject1.getString("ferrum")+"毫克");
                    urll =jsonObject1.getString("photo");
                    String urlll = urll.replaceAll(
                            "\\\\",""
                    );
                    Glide.with(Qrcode_main.this).
                            load(urlll).
                            centerCrop().
                            into(my_image_view);
                }catch (Exception e){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Qrcode_main.this,"查無該資料",Toast.LENGTH_LONG).show();
                finish();
            }
        });



        MySingleton.getInstance(Qrcode_main.this).addToRequestQueue(stringRequest);
    }
}
