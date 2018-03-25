package com.example.asus.startup;

/**
 * Created by asus on 24-Mar-18.
 */

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteOpenHelper;

        import android.content.Context;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mehrez on 24-Mar-18.
 */

public class DBHelper extends SQLiteOpenHelper {

    SQLiteDatabase db;
    SQLiteDatabase database;


    private static final String DATABASE_NAME = "DBName";

    private static final int DATABASE_VERSION = 4;

    private static final String DATABASE_CREATE1 = "create table Ingredient( _id integer primary key AUTOINCREMENT,name text not null,isAll integer DEFAULT 0);";
    private static final String DATABASE_CREATE2 = "create table Aliment( _id integer primary key AUTOINCREMENT,codeAB text,name text not null,_idIng integer ,codeAlt text);";
    private static final String DATABASE_CREATE3 = "create table Guy( _id integer primary key AUTOINCREMENT,age integer, longueur integer, poids integer,nom text,tel integer);";



    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);


    }

    // Method is called during creation of the database
    @Override
    public void onCreate(SQLiteDatabase database) {
        this.database = database;
        database.execSQL(DATABASE_CREATE1);
        database.execSQL(DATABASE_CREATE2);
        database.execSQL(DATABASE_CREATE3);
        String s ="insert into Aliment (codeAB,name,_idIng,codeAlt) values('6194043001019','TESTT',1,'https://www.c');";
        database.execSQL(s);
        Cursor c;
        c = database.rawQuery("Select name from aliment where codeAB = ?",new String[]{"6194043001019"});
        c.moveToFirst();
        String d = c.getString(c.getColumnIndex("name"));
        Log.i("LOGGG",d);

        insertAli("6191503210059","Golden Chips : Frommage",new String[]{"Arome fromage","sel","huile de palme","amidon de pomme de terre"},"http://www.carrefour.fr/catalogue/carrefour/hypermarche-Promo12_0318_6qK4rUr3/produit/191-pringles?v=V01&cda_source=sea_medias&cda_medium=cpc&cda_campaign=jhcpromo10&cda_content=MONTEREAU_&gclid=CjwKCAjw7tfVBRB0EiwAiSYGM-xczJig7XcgXkYj-uP2PYx_-Ip2amro0LyPd1NGv1LgrzfXifQS_hoC2bsQAvD_BwE");
        insertAli("6194008515520","Start",new String[]{"Farine de blé","graisse de palme","bicarbonate","agents de levée","arome mature"},"http://nutridiet.tn/produit/digestif-orange/");
        insertAli("6194043001019","Lait 1L Delice",new String[]{"lactose","E339","lactoglobuline","gluten"},"https://www.candia.fr/produit/grandlait-leger-digeste/");

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
    public String getAli(String codeAB ){

        Cursor c = null;
        String d = null;
        try{
            c = database.rawQuery("Select name from Aliment where codeAB = ?",new String[]{codeAB});
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
        database.insert("Ingredient",null,values);

    }
    public String getName(){
        Cursor c = null;
        c = database.rawQuery("SELECT nom FROM Guy",new String[]{});
        c.moveToFirst();
        return c.getString(0);
    }
    public void setName(String n){
        Cursor c = null;
        String req = "UPDATE Guy Set nom = '"+ n+"';";
        database.execSQL(req);

    }
    public String getNum(){
        Cursor c = null;
        c = database.rawQuery("SELECT tel FROM Guy",new String[]{});
        c.moveToFirst();
        return c.getString(0);
    }

    public void setNum(String n){
        Cursor c = null;
        String req = "UPDATE Guy Set tel = '"+ n+"';";
        database.execSQL(req);
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
            c = database.rawQuery("Select * FROM Ingredient WHERE name = ?", new String []{nameIng[i]});
            if(c.moveToFirst()){


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

    }
//ggg
    public List<String> verify(String codeAB){

        List<String> list=new ArrayList<>() ;
        Cursor c,c2 = null;
        c = database.rawQuery("SELECT * FROM  Aliment WHERE codeAB = ?",new String[]{codeAB});
            if (c.moveToFirst()){


        do{
            int x = c.getInt(c.getColumnIndex("_idIng"));
            Log.d("dqsfsd",""+x);
            c2 = database.rawQuery("SELECT * FROM Ingredient WHERE _id = ?",new String[]{String.valueOf(x)});
            int test = c2.getInt(c.getColumnIndex("isAll"));
            if(test == 1){
                list.add(c2.getString(c2.getColumnIndex("name")));
            }
        }while(c.moveToNext());}

        return list;
    }


}
