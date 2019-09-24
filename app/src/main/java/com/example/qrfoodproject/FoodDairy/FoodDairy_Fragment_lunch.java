package com.example.qrfoodproject.FoodDairy;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

import static android.content.Context.MODE_PRIVATE;

public class FoodDairy_Fragment_lunch extends Fragment {
    private RecyclerView mRecyclerView;
    String url = "http://120.110.112.96/using/getFoodDairyRecord.php";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fooddairy_fragment_lunch, container, false);

        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.LunchView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        Log.v("Lunch","refresh");
        getLunchData(); //取得食物的資料
    }

    public void setRecycleView(ArrayList<HashMap<String,String>> array){
        FoodDairy_Adapter mAdapter = new FoodDairy_Adapter(getActivity(),array);
        mRecyclerView.setAdapter(mAdapter);
    }
    public void getLunchData(){

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("onResponse",response.toString());
                try {
                    ArrayList<HashMap<String,String>> array = new ArrayList<HashMap<String, String>>();
                    //解析JSON檔傳入array
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonObject1 = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonObject1.length(); i++) {
                        JSONObject c = jsonObject1.getJSONObject(i);
                        HashMap<String,String> total = new HashMap<String, String>();
                        total.put("location",c.getString("location"));
                        total.put("fdName",c.getString("fdName"));
                        total.put("sn",c.getString("sn"));
                        array.add(total);

                        setRecycleView (array); //設定RecycleView
                    }
                }catch (Exception e){

                }
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("ErrorResponse",error.toString());
                progressDialog.dismiss();
            }

        }) {
            @Override
            protected Map<String, String> getParams() {
                //取得sessionID
                SharedPreferences pref = getActivity().getSharedPreferences("Data", MODE_PRIVATE);
                String session = pref.getString("sessionID", "");
                //取得當天時間
                SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                String strDate = sdFormat.format(date);

                Map<String, String> params = new HashMap<String, String>();
                params.put("sessionID",session);
                params.put("date",strDate);
                params.put("time","午");
                return params;
            }
        };
        MySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }
}
