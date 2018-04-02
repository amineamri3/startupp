package com.example.asus.startup;

/**
 * Created by asus on 25-Mar-18.
 */

import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;

        import android.content.DialogInterface;
        import android.content.Intent;
        import android.content.pm.PackageManager;
        import android.hardware.Camera;
        import android.net.Uri;
        import android.os.Build;
        import android.support.v4.app.ActivityCompat;
        import android.support.v4.content.ContextCompat;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.widget.Toast;

        import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

        import static android.Manifest.permission.CAMERA;

public class Scanner extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView scannerView;

    String[][] tab;
    int ind=1;
    String msg="";

    private static int camId = Camera.CameraInfo.CAMERA_FACING_BACK;

        private ZXingScannerView mScannerView;

        @Override
        public void onCreate(Bundle state) {
            super.onCreate(state);
            getSupportActionBar().hide();
            mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
            setContentView(mScannerView);                // Set the scanner view as the content view
        }

        @Override
        public void onResume() {
            super.onResume();
            mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
            mScannerView.startCamera();          // Start camera on resume
        }

        @Override
        public void onPause() {
            super.onPause();
            mScannerView.stopCamera();           // Stop camera on pause
        }

        @Override
        public void handleResult(Result rawResult) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(rawResult.getText());
        //List<String> l  ;
       // l=dbAccess.verify(result.getText());
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mScannerView.resumeCameraPreview(Scanner.this);
            }
        });
        builder.setNeutralButton("Alternative", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        if(true)
             msg = "Ce Produit Ne Contient Ingredient Allergere: \n";
        else
            msg = "Eviter De Consommer Ce Produit Car Il Contient: `\n" + " ITEM";

        builder.setMessage(msg);
        AlertDialog alert1 = builder.create();
        alert1.show();
        }
    }

