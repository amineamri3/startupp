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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);
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

        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.M) {
            if (checkPermission()) {
                if(scannerView == null) {
                    scannerView = new ZXingScannerView(this);
                    setContentView(scannerView);
                }
                scannerView.setResultHandler(this);
                scannerView.startCamera();
            } else {
                requestPermission();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new android.support.v7.app.AlertDialog.Builder(Scanner.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
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
        for(int i=0;i<3;i++)if(myResult.equals(tab[i][0]))ind=i;
        Log.d("QRCodeScanner", result.getText());
        Log.d("QRCodeScanner", result.getBarcodeFormat().toString());

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(tab[ind][1]);
        //List<String> l  ;
       // l=dbAccess.verify(result.getText());
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                scannerView.resumeCameraPreview(Scanner.this);
            }
        });
        builder.setNeutralButton("Alternative", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(myResult));
                //startActivity(browserIntent);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(tab[ind][4]));
                startActivity(browserIntent);
            }
        });
        if(tab[ind][3]=="0")
             msg = "Ce Produit Ne Contient Ingredient Allergere: \n";
        else
            msg = "Eviter De Consommer Ce Produit Car Il Contient: `\n" + tab[ind][2] ;

        builder.setMessage(msg);
        AlertDialog alert1 = builder.create();
        alert1.show();
    }
}