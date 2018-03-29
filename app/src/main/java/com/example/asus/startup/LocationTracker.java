package com.example.asus.startup;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Zakaria on 28/03/2018.
 */

public class LocationTracker extends Activity implements LocationListener {
    LocationManager locationManager;
    String provider;
    double longitude;
    String knownName;
    String city;

    double atitude;

    public LocationTracker() {
    }

    public void getLocation (){


             if (ActivityCompat.checkSelfPermission(this,
    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        // Check Permissions Now
        ActivityCompat.requestPermissions(this,
                new String[] { Manifest.permission.ACCESS_FINE_LOCATION },
                0);
    }
    // Getting LocationManager object
    //statusCheck();


    locationManager = (LocationManager) getSystemService(
            Context.LOCATION_SERVICE);

    // Creating an empty criteria object
    Criteria criteria = new Criteria();

    // Getting the name of the provider that meets the criteria
    provider = locationManager.getBestProvider(criteria, false);

        if (provider != null && !provider.equals("")) {
        if (!provider.contains("gps")) { // if gps is disabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings",
                    "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            sendBroadcast(poke);
        }
        // Get the location from the given provider
        Location location = locationManager
                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, 500, 0, this);

        if (location != null)
            onLocationChanged(location);
        else
            location = locationManager.getLastKnownLocation(provider);
        if (location != null){
            onLocationChanged(location);

            Toast.makeText(this, ""+atitude+" "+longitude, Toast.LENGTH_SHORT).show();

       }
        else

            Toast.makeText(getBaseContext(), "Location can't be retrieved",
                    Toast.LENGTH_SHORT).show();

    } else {
        Toast.makeText(getBaseContext(), "No Provider Found",
                Toast.LENGTH_SHORT).show();
    }

    }
//    public void statusCheck() {
//        final LocationManager manager = (LocationManager) getSystemService(
//                Context.LOCATION_SERVICE);
//
//        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//            buildAlertMessageNoGps();
//
//        }
//    }
//
//    private void buildAlertMessageNoGps() {
//        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setMessage(
//                "Your GPS seems to be disabled, do you want to enable it?")
//                .setCancelable(false).setPositiveButton("Yes",
//                new DialogInterface.OnClickListener(){
//                    public void onClick(final DialogInterface dialog,
//                                        final int id) {
//                        startActivity(new Intent(
//                                android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
//                    }
//                })
//                .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                    public void onClick(final DialogInterface dialog,
//                                        final int id) {
//                        dialog.cancel();
//                    }
//                });
//        final AlertDialog alert = builder.create();
//        alert.show();
//    }



    @Override
    public void onLocationChanged(Location location) {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

            // //address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            city = addresses.get(0).getLocality();
//    String state = addresses.get(0).getAdminArea();
//    String country = addresses.get(0).getCountryName();
//    String postalCode = addresses.get(0).getPostalCode();
            knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL

            longitude=location.getLongitude();
            atitude=location.getLatitude();
            // Setting Current Longitude

        }catch (IOException e){

        }
        // Here 1 represent max location result to returned, by documents it recommended 1 to 5


    }

    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Check Permissions Now
            ActivityCompat.requestPermissions(this,
                    new String[] { Manifest.permission.ACCESS_FINE_LOCATION },
                    0);
        }
    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub
    }


}
