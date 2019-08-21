package com.example.qrfoodproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Profile_main extends AppCompatActivity {
    Button modifyData,modifyPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_main);

        modifyData = findViewById(R.id.modifyData);
        modifyPassword = findViewById(R.id.modifyPassword);


        modifyData.setOnClickListener(onclick);
        modifyPassword.setOnClickListener(onclick);
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
            }
        }
    };
}
