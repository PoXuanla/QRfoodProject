package com.example.qrfoodproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText edtAccount, edtPassword;
    private Button btn_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtAccount = this.findViewById(R.id.edtAccount);
        edtPassword = this.findViewById(R.id.edtPassword);
        btn_login = this.findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String account = edtAccount.getText().toString().trim();
                final String password = edtPassword.getText().toString().trim();

                if (!account.isEmpty() || !password.isEmpty()){
                    final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("Login...");
                    progressDialog.show();
                    //Login(account, password);
                }
                else{
                    edtAccount.setError("Please input Account");
                    edtPassword.setError("Please input Password");
                }
                Intent intent = new Intent(getApplication(),Home_QRfood.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
