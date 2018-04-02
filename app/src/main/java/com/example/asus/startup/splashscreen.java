package com.example.asus.startup;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;


/**
 * Created by Zakaria on 02/04/2018.
 */

//extends AwesomeSplash!
public class splashscreen extends AppCompatActivity{



        private final int SPLASH_DISPLAY_LENGTH = 3000;

        @Override
        public void onCreate(Bundle icicle) {
            super.onCreate(icicle);
            setContentView(R.layout.splashscreen);
            getSupportActionBar().hide();
            new Handler().postDelayed(new Runnable(){
                @Override
                public void run() {
                    Intent mainIntent = new Intent(splashscreen.this,LoginActivity.class);
                   splashscreen.this.startActivity(mainIntent);
                    splashscreen.this.finish();
                }
            }, SPLASH_DISPLAY_LENGTH);
        }
    }
