package com.example.qrfoodproject.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.VolleyError;


public class whenSessionInvalid {

    //triggered when Session has been detected as "Invalid, please login again"
    public void informing(Context context, VolleyError error){
        context.getApplicationContext();
        Toast.makeText(context, "請重新登入", Toast.LENGTH_SHORT).show();
        context.startActivity(new Intent(context, MainActivity.class));
        ActivityCompat.finishAffinity((Activity)context.getApplicationContext());
        Log.d("Volley Session Lost",  error.toString());
    }

}
