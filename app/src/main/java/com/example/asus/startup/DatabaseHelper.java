package com.example.asus.startup;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DatabaseHelper extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "allergysweeper.db";
    private static final int DATABASE_VERSION = 1;
    Context ctx;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.ctx=context;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            ctx.deleteDatabase(DATABASE_NAME);
            new DatabaseHelper(ctx);
        }else
            super.onUpgrade(db, oldVersion, newVersion);
    }
}