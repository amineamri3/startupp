package com.example.asus.startup;

/**
 * Created by asus on 24-Mar-18.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mehrez on 24-Mar-18.
 */

public class DB{

    private DBHelper dbHelper;

    private SQLiteDatabase database;

    public final static String EMP_TABLE="MyEmployees"; // name of table
    public final static String EMP_ID="_id"; // id value for employee
    public final static String EMP_NAME="name";  // name of employee

    /**
     *
     * @param context
     */
    public DB(Context context){
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
    }


    public String getAli(String codeAB ){
        Cursor c = null;
        c = database.rawQuery("Select name from aliment where codeAB = ?",new String[]{codeAB});
        c.moveToFirst();
        return c.getString(c.getColumnIndex("name"));

    }
    public void insertIng(String ing , int isAll){
        Cursor c = null;
        String s ="insert into Ingredient (name,isAll) values("+ing+","+String.valueOf(isAll)+")";
        database.execSQL(s);

    }


    public void updateIng(String s,int x){
        String req="UPDATE Ingredient SET isAll = "+String.valueOf(x)+" WHERE name = '"+s+"';";
        database.execSQL(req);
    }

    public void getIng(int id,int isAll,String name){
        Cursor cursor = null;
        cursor= database.rawQuery("Select name,isAll From Ingredient Where idIng = ?;",new String [] {String.valueOf(id)});
        cursor.moveToFirst();
        name= cursor.getString(cursor.getColumnIndex("name"));
        isAll= cursor.getInt(cursor.getColumnIndex("isAll"));


    }

    public void insertAli(String codeAB,String name,String[] nameIng,String codeAlt){
        Cursor c;
        for(int i = 0;i<nameIng.length;i++){
            c = database.rawQuery("Select * FROM Ingredient WHERE name = ?", new String []{nameIng[i]},null);
            if(c == null){
                insertIng(nameIng[i],0);
            }
            c = database.rawQuery("Select _id FROM Ingredient WHERE name =?", new String []{nameIng[i]});
            c.moveToFirst();
            int a=c.getInt(c.getColumnIndex("_id"));
            String s ="insert into Aliment (codeAB,name,_idIng,codeAlt) values('"+codeAB+"','"+name+"',"+a+",'"+codeAlt+"');";
            database.execSQL(s);
        }

    }

    public List<String> verify(int codeAB){
        Cursor c,c2 = null;
        c = database.rawQuery("SELECT * FROM  Aliment WHERE codeAB = ?",new String[]{String.valueOf(codeAB)});
        c.moveToFirst();
        List<String> list = new ArrayList<String>();

        do{
            int x = c.getInt(c.getColumnIndex("_idIng"));
           c2 = database.rawQuery("SELECT * FROM Ingredient WHERE _id = ?",new String[]{String.valueOf(x)});
            int test = c2.getInt(c.getColumnIndex("isAll"));
            if(test == 1){
                list.add(c2.getString(c2.getColumnIndex("name")));
            }
        }while(c.moveToNext());
         return list;
    }


        }

