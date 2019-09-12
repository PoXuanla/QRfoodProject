package com.example.qrfoodproject.Qrcode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanQrcode extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    ZXingScannerView ScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScannerView = new ZXingScannerView(this);
        setContentView(ScannerView);

    }

    @Override
    public void handleResult(Result result) {
        Qrcode_main.QrcodeResult = result.getText();
        startActivity(new Intent(ScanQrcode.this,Qrcode_main.class));
        finish();
        //Toast.makeText(ScanQrcode.this,result.getText(),Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPause() {

        super.onPause();
        ScannerView.stopCamera();
    }
    @Override
    protected void onResume(){
        super.onResume();
        ScannerView.setResultHandler(this);
        ScannerView.startCamera();
    }
}
