package com.example.qrfoodproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Home_QRfood extends AppCompatActivity {
    Button personalData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_qrfood);

        personalData = findViewById(R.id.personalData);

        personalData.setOnClickListener(onclick);

    }
    Button.OnClickListener onclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Home_QRfood.this,Profile_main.class);
            startActivity(intent);


        }
    };
}
