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
    public void onDestroy() {
        super.onDestroy();
        if (scannerView!=null)
        scannerView.stopCamera();
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA:
                if (grantResults.length > 0) {

                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted){
                    }else {
                        Toast.makeText(getApplicationContext(), "Permission Denied, You cannot access and camera", Toast.LENGTH_LONG).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(CAMERA)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{CAMERA},
                                                            REQUEST_CAMERA);
                                                }
                                            }
                                        });
                                return;
                            }
                        }
                    }
                }
                break;
        }
    }

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        getSupportActionBar().hide();
        int currentApiVersion = Build.VERSION.SDK_INT;

        if(currentApiVersion >=  Build.VERSION_CODES.M)
        {
            if(checkPermission())
            {
            }
            else
            {
                requestPermission();
            }
        }
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);
    }

    private boolean checkPermission()
    {
        return (ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission()
    {
        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, REQUEST_CAMERA);
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
    public void handleResult(Result result) {

        tab = new String[][]{
                {"6194043001019","Lait Delice","lactose","1","https://www.candia.fr/produit/grandlait-leger-digeste/"},
                {"6191503210059","Golden Chips : Frommage","Arome de frommage","0","http://www.carrefour.fr/catalogue/carrefour/hypermarche-Promo12_0318_6qK4rUr3/produit/191-pringles?v=V01&cda_source=sea_medias&cda_medium=cpc&cda_campaign=jhcpromo10&cda_content=MONTEREAU_&gclid=CjwKCAjw7tfVBRB0EiwAiSYGM-xczJig7XcgXkYj-uP2PYx_-Ip2amro0LyPd1NGv1LgrzfXifQS_hoC2bsQAvD_BwE"},
                {"6194008515520","Start","farine de blé","1","chttp://nutridiet.tn/produit/digestif-orange/"}


        };
        final String myResult = result.getText();
        Log.d("code",myResult);

        Log.d("QRCodeScanner", result.getText());
        Log.d("QRCodeScanner", result.getBarcodeFormat().toString());

        DatabaseAccess db = DatabaseAccess.getInstance(this);
        db.openToRead();
        List<String> l = new ArrayList<>();
        l = db.getAllergin(myResult);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(db.getName(myResult));
        //List<String> l  ;
        if(db.getName(myResult).equals("Code n'existe pas")){
            msg = "";
        }else if(l.size() == 0){
            msg = "Ce Produit Ne Contient Pas des Ingredient Allergere \n";
        }else{
            msg = "Eviter De Consommer Ce Produit Car Il Contient: \n";
            for(int i =0;i<l.size();i++){
                msg += l.get(i) ;
                msg += '\n';
            }
        }

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                scannerView.resumeCameraPreview(Scanner.this);
            }
        });
        builder.setNeutralButton("Alternative", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               /*
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(tab[ind][4]));
                startActivity(browserIntent);        NEED TO FIX !!!!!!!!! */
            }
        });


        builder.setMessage(msg);
        AlertDialog alert1 = builder.create();
        alert1.show();
    }


    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new android.support.v7.app.AlertDialog.Builder(Scanner.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }


}