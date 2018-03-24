package com.example.asus.startup;

/**
 * Created by asus on 24-Mar-18.
 */

import android.database.sqlite.SQLiteOpenHelper;

        import android.content.Context;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.util.Log;

/**
 * Created by Mehrez on 24-Mar-18.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "DBName";

    private static final int DATABASE_VERSION = 2;

    private static final String DATABASE_CREATE1 = "create table Ingredient( _id integer primary key AUTOINCREMENT,name text not null,isAll integer DEFAULT 0);";
    private static final String DATABASE_CREATE2 = "create table Aliment( _id integer primary key AUTOINCREMENT,codeAB text,name text not null,_idIng integer ,codeAlt text);";
    private static final String DATABASE_CREATE3 = "create table Guy( _id integer primary key AUTOINCREMENT,age integer, longueur integer, poids integer);";



    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Method is called during creation of the database
    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE1);
        database.execSQL(DATABASE_CREATE2);
        database.execSQL(DATABASE_CREATE3);

    }

    // Method is called during an upgrade of the database,
    @Override
    public void onUpgrade(SQLiteDatabase database,int oldVersion,int newVersion){
        Log.w(DBHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS MyEmployees");
        onCreate(database);
    }
}
