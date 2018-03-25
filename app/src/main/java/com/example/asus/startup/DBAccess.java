package com.example.asus.startup;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 25-Mar-18.
 */


public class DBAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static DBAccess instance;


    private DBAccess(Context context){
        this.openHelper = new DatabaseHelper(context);
    }

    public static DBAccess getInstance(Context context){
        if (instance == null) {
            instance = new DBAccess(context);
        }
        return instance;
    }


    // Open DB connection
    public void open(){
        this.db = openHelper.getWritableDatabase();
    }

    //close DB connection
    public void close(){
        if ( db != null){ // if the DB does exist then close it
            this.db.close();
        }}

    public String getAli(String codeAB ){

        Cursor c = null;
        String d = null;
        try{
            c = db.rawQuery("Select name from Aliment where codeAB = ?",new String[]{codeAB});
            c.moveToFirst();
            d = c.getString(c.getColumnIndex("name"));

        }catch (SQLException e){
            e.printStackTrace();
        }



        return d ;

    }
    public void insertIng(String ing , int isAll){
        ContentValues values = new ContentValues();
        values.put("name", ing);
        values.put("isAll", isAll);
        db.insert("Ingredient",null,values);

    }
    public String getName(){
        Cursor c = null;
        c = db.rawQuery("SELECT nom FROM Guy",new String[]{});
        c.moveToFirst();
        return c.getString(0);
    }
    public void setName(String n){
        Cursor c = null;
        String req = "UPDATE Guy Set nom = '"+ n+"';";
        db.execSQL(req);

    }
    public String getNum(){
        Cursor c = null;
        c = db.rawQuery("SELECT tel FROM Guy",new String[]{});
        c.moveToFirst();
        return c.getString(0);
    }

    public void setNum(String n){
        Cursor c = null;
        String req = "UPDATE Guy Set tel = '"+ n+"';";
        db.execSQL(req);
    }




    public void updateIng(String s,int x){
        String req="UPDATE Ingredient SET isAll = "+String.valueOf(x)+" WHERE name = '"+s+"';";
        db.execSQL(req);
    }

    public void getIng(int id,int isAll,String name){

        Cursor cursor = null;
        cursor= db.rawQuery("Select name,isAll From Ingredient Where idIng = ?;",new String [] {String.valueOf(id)});
        cursor.moveToFirst();
        name= cursor.getString(cursor.getColumnIndex("name"));
        isAll= cursor.getInt(cursor.getColumnIndex("isAll"));


    }

    public void insertAli(String codeAB,String name,String[] nameIng,String codeAlt){
        Cursor c;

        for(int i = 0;i<nameIng.length;i++){
            c = db.rawQuery("Select * FROM Ingredient WHERE name = ?", new String []{nameIng[i]});
            if(c.moveToFirst()){


                if(c == null){
                    insertIng(nameIng[i],0);
                }
                c = db.rawQuery("Select _id FROM Ingredient WHERE name =?", new String []{nameIng[i]});
                c.moveToFirst();
                int a=c.getInt(c.getColumnIndex("_id"));
                String s ="insert into Aliment (codeAB,name,_idIng,codeAlt) values('"+codeAB+"','"+name+"',"+a+",'"+codeAlt+"');";
                db.execSQL(s);
            }
        }

    }
    //ggg
    public List<String> verify(String codeAB){

        List<String> list=new ArrayList<>() ;
        Cursor c2 = null;
       // c = db.rawQuery("SELECT * FROM  Aliment WHERE codeAB = ?",new String[]{codeAB});
       // c.moveToFirst();


            for(int i=1;i<=4;i++){
                //int x = c.getInt(c.getColumnIndex("_idIng"));
                 String req = "SELECT * FROM Ingredient WHERE _id = '"+String.valueOf(i)+"';";
                c2 = db.rawQuery(req,new String[]{});
                int test = c2.getInt(2);
                c2.moveToFirst();
                if(test == 1){
                    list.add(c2.getString(1));
                }
                //c.moveToNext();
            }

        return list;


    }}


