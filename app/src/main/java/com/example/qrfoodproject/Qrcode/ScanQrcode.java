package com.example.qrfoodproject.Qrcode;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanQrcode extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView ScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScannerView = new ZXingScannerView(getApplicationContext());
        checkCameraPermitted();

        //TODO camera doesnt work when first commit the permission

    }

    private void checkCameraPermitted(){
        //Changed the minimum API into 23 since it required 23 or later to work
        //reason that the permission wont been asked while installing is that USER could revoke permission after that
        final int PERMISSION_CAMERA = 100;

        if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            setContentView(ScannerView);

        }else{
            if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)){
                Toast.makeText(this, "QR camera requires your permission to work!", Toast.LENGTH_SHORT).show();
            }

            requestPermissions(new String[]{Manifest.permission.CAMERA} , PERMISSION_CAMERA);
        }
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
