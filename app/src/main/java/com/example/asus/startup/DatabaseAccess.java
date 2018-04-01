package com.example.asus.startup;

/**
 * Created by asus on 31-Mar-18.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.Editable;


import java.util.ArrayList;
import java.util.List;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    /**
     * Private constructor to avoid object creation from outside classes.
     *
     * @param context
     */
    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseHelper(context);
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    /**
     * Open the database connection for writing.
     */
    public void openToWrite() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Open the database connection for reading.
     */
    public void openToRead() {
        this.database = openHelper.getReadableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    /**
     * Read all allergic products from the database.
     *
     * @return a List of Aliments
     */
    public List<String> getAllAllergin() {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM alternatif" +
                " INNER JOIN aliment ON alternatif.code=aliment.code" +
                " INNER JOIN ingredient ON ingredient.id=alternatif.idingredient" +
                " WHERE ingredient.isallergic=1", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(5));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    /**
     * Read an allergic product with specific code from the database.
     *
     * @return a List of Aliments
     */
    public String getAllergin(String code) {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM alternatif"+
                " INNER JOIN aliment ON alternatif.code=aliment.code" +
                " INNER JOIN ingredient ON ingredient.id=alternatif.idingredient" +
                " WHERE ingredient.isallergic=1"+
                " AND Aliment.code = ?",new String[]{code});
        cursor.moveToFirst();

        return ("you are allergic to \"" + cursor.getString(1)+"\"\nfor an alternatif product please visit\n "+cursor.getString(4));

    }

    public String getNum(String uid) {
        return "99005484";
    }

    public ArrayList<Boolean> getswitch(String offline) {

        return null;
    }

    public void updateProfile(ArrayList<Boolean> allergi, String text1,String text) {
    }
}
