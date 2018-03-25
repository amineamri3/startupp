package com.example.asus.startup;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by asus on 25-Mar-18.
 */
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DatabaseHelper extends SQLiteAssetHelper {
    //TODO: Load DB From Assets

    //DB Info
    private static String DB_PATH = "";
    private static String DB_NAME = "DB.db";
    private static int DB_VER = 1;


    private Context mContext;
    private SQLiteDatabase db;
    private SQLiteOpenHelper openHelper;



    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
        mContext = context;
        setForcedUpgrade();
    }




}