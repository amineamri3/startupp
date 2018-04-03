package com.example.asus.startup;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Zakaria on 02/04/2018.
 */

public class DbLocal extends SQLiteOpenHelper {

    final String profil_table="profil";
    final String id ="id";
    final String num="num";
    final String s1="s1";
    final String s2="s2";
    final String s3="s3";
    final String s4="s4";

    public DbLocal(Context context) {
        super(context, "profile.db",null,3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createProfil="create table "+profil_table+" ("
                +id+" varchar(50) PRIMARY KEY,"
                +num+" varchar(12),"
                +s1+ " integer,"
                +s2+ " integer,"
                +s3+ " integer,"
                +s4+ " integer)";

        db.execSQL(createProfil);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String dropEmploi="DROP TABLE IF EXISTS "+profil_table;
        db.execSQL(dropEmploi);
        onCreate(db);

    }

    public String getNum(String uid) {
        SQLiteDatabase database=this.getReadableDatabase();
String sql ="select num from "+profil_table+" where "+id+" ='"+uid+"'";
        Cursor cursor = database.rawQuery(sql,new String[]{});
        String num="";

        if (cursor.moveToFirst()){
            num=cursor.getString(0);

        }

        return num;
    }

    public ArrayList<Boolean> getswitch(String idof) {
        SQLiteDatabase database=this.getReadableDatabase();
        ArrayList<Boolean> allergi =new ArrayList<>();
        Boolean b1=false,b2=false,b3=false,b4=false;
        String sql ="select s1,s2,s3,s4 from "+profil_table+" where "+id+" ='"+idof+"'";
        Cursor cursor = database.rawQuery(sql,new String[]{});
        if (cursor.moveToFirst()){
            do {
                if (cursor.getInt(0)==1)
                    b1=true;
                    allergi.add(b1);
                if (cursor.getInt(1)==1)
                    b2=true;
                allergi.add(b2);
                if (cursor.getInt(2)==1)
                    b3=true;
                allergi.add(b3);
                if (cursor.getInt(3)==1)
                    b4=true;
                allergi.add(b4);
            }while (cursor.moveToNext());

        }
        else
            return null;




        return allergi;
    }

    public void updateProfile(ArrayList<Boolean> allergi, String text1,String text) {
        SQLiteDatabase database=this.getWritableDatabase();
        int s1=0;
        int s2=0;
        int s3=0;
        int s4=0;
        if (allergi.get(0))
            s1=1;
        if (allergi.get(1))
            s2=1;
        if (allergi.get(2))
            s3=1;
        if (allergi.get(3))
            s4=1;

                ContentValues cv = new ContentValues();
                cv.put(id,text);
                cv.put(num,text1);
                cv.put(this.s1,s1);
                cv.put(this.s2,s2);
                cv.put(this.s3,s3);
                cv.put(this.s4,s4);
                database.insertWithOnConflict(profil_table,null,cv,SQLiteDatabase.CONFLICT_REPLACE);




}

}
