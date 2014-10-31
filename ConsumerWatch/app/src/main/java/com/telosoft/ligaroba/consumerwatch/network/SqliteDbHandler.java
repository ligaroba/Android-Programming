package com.telosoft.ligaroba.consumerwatch.network;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.telosoft.ligaroba.consumerwatch.custom.GetterSetterClass;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 10/28/14.
 package co.ke.masterclass.mysecurity.network;

 import android.content.ContentValues;
 import android.content.Context;
 import android.database.Cursor;
 import android.database.sqlite.SQLiteDatabase;
 import android.database.sqlite.SQLiteOpenHelper;
 import android.util.Log;

 import java.util.ArrayList;
 import java.util.List;

 import co.ke.masterclass.mysecurity.custom.CustomSeterClass;

 /**
 * Created by root on 9/30/14.
 */
public class SqliteDbHandler extends SQLiteOpenHelper {
    private static final String DB_NAME="Consumerwatch";
    private static final String PRODUCT_TABLE="products";
    private static final String SUPERMARKET_TABLE="supermarkets";
    private static final String PRICES_TABLE="prices";
    private static final int DB_VERSION=1;

    // Variables

    private static final String KEY_PID="_PID";
    private static final String KEY_SID="_SID";
    private static final String KEY_PRID="_PRID";

    private static final String KEY_PNAME="product_name";
    private static final String KEY_LOGO="logo";
    private static final String KEY_SUPERNAME="supermarket_name";
    private static final String KEY_PRICES="price";

    String TAG="SqlitedbHandler";
    SQLiteDatabase db;


    public SqliteDbHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String psql="CREATE TABLE  " + PRODUCT_TABLE + " (" +
                KEY_PID + " int(5) primary key ," +
                KEY_PNAME + " varchar(100) not null," +
                KEY_LOGO +  " varchar(10) not null)";
        db.execSQL(psql);

        String ssql="CREATE TABLE  " + SUPERMARKET_TABLE + " (" +
                KEY_SID + "  int(5) primary key ," +
                KEY_SUPERNAME + " varchar(100) not null," +
                KEY_LOGO +  "  varchar(10) not null)";
        db.execSQL(ssql);



        String prsql="CREATE TABLE  " + PRICES_TABLE + " (" +
                KEY_PRID + " AUTO INCREMENT ," +
                KEY_PID + " int(5) not null ," +
                KEY_SID + " int(5) not null  ," +
                KEY_PRICES +  " int(5) not null, primary key('"+ KEY_PID+"','"+ KEY_SID + "','"+KEY_PRID+"'" + "))";
        db.execSQL(prsql);

    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        db.execSQL("DROP TABLE IF EXISTS " + PRICES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + SUPERMARKET_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + PRODUCT_TABLE);


        onCreate(db);
    }


    public void product(int pid,String pname,String logo){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PID,pid);
        values.put(KEY_PNAME,pname);
        values.put(KEY_LOGO,logo);


        db.insertOrThrow(PRODUCT_TABLE,null,values);
        db.close();
    }

    public void supermarket(int sid ,String supername, String logo){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_SID,sid);
        values.put(KEY_SUPERNAME,supername);
        values.put(KEY_LOGO,logo);

        db.insertOrThrow(SUPERMARKET_TABLE,null,values);
        db.close();
    }

    public void prices(int pid,int sid,int  price){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put(KEY_PRID,prid);
        values.put(KEY_PID,pid);
        values.put(KEY_SID,sid);
        values.put(KEY_PRICES,price);


        db.insertOrThrow(PRICES_TABLE,null,values);
        db.close();
    }





    public void resetTables(String tablename) {
        // TODO Auto-generated method stub
        SQLiteDatabase db =this.getWritableDatabase();
        try {
            db.delete(tablename, null,null);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        db.close();

    }
    public List<GetterSetterClass> getProducts(){
        SQLiteDatabase db=this.getReadableDatabase();
        List<GetterSetterClass> list =new ArrayList<GetterSetterClass>();
        String Sql="Select * from " + PRODUCT_TABLE ;


        Cursor cursor = db.rawQuery(Sql,null);

        if(cursor.moveToFirst()){
            while (cursor.isAfterLast()==false){
                GetterSetterClass data= new GetterSetterClass();
                int id=cursor.getColumnIndex(KEY_PID);
                data.setId(cursor.getInt(id));
                int pname=  cursor.getColumnIndex(KEY_PNAME);
                data.setPname(cursor.getString(pname));
                int logo= cursor.getColumnIndex(KEY_LOGO);
                data.setLogo(Integer.parseInt(cursor.getString(logo)));
                list.add(data);
                cursor.moveToNext();

            }


        }
        cursor.close();
        db.close();

        return list;
    }


    public ArrayList<String> getAllLabels(String param,String tablename,String index){
        ArrayList<String> labels = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT "+ param +" FROM " + tablename;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                int indexid =cursor.getColumnIndexOrThrow(index);
                labels.add(cursor.getString(indexid));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return labels;
    }

    public List<GetterSetterClass> getbudget(String productname,int quantity){
        List<GetterSetterClass>labels = new ArrayList<GetterSetterClass>();

        // Select All Query
        String select = "SELECT * FROM " + PRICES_TABLE +"  as p inner join  " +  SUPERMARKET_TABLE + " as tA ON p."+ KEY_SID +"= tA." + KEY_SID +
               "  inner join  " +  PRODUCT_TABLE + " as pdt ON p."+ KEY_PID +"= pdt." + KEY_PID +
                "  where " + KEY_PNAME + "  = '"+  productname + "' group by " + KEY_SID ;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(select, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false){
                GetterSetterClass list =new GetterSetterClass();

                int price=cursor.getColumnIndexOrThrow(KEY_PRICES);
                list.setPrices(cursor.getInt(price));
                int supname=cursor.getColumnIndexOrThrow(KEY_SUPERNAME);
                list.setSupername(cursor.getString(supname));
                list.setQauntity(quantity);
Log.e("Sqlitedata: ","data :" + list.toString());
                labels.add(list);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return labels;
    }

    public String getitem(String param,String tablename,String index){
        String id = "";

        // Select All Query
        String selectQuery = "SELECT "+ param +" FROM " + tablename + "  where " + KEY_PNAME + " = '" + index + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()) {
            int indexid = cursor.getColumnIndexOrThrow(param);
            id = cursor.getString(indexid);
        }
        cursor.close();
        db.close();
        return id;
    }


    public List<GetterSetterClass> supermarket(int supid){
        SQLiteDatabase db=this.getReadableDatabase();
        List<GetterSetterClass> list =new ArrayList<GetterSetterClass>();
        String Sql="Select * from " + SUPERMARKET_TABLE + "  where " + KEY_SID + "  =  " + supid ;


        Cursor cursor = db.rawQuery(Sql,null);

        if(cursor.moveToFirst()){
            while (cursor.isAfterLast()==false){
                GetterSetterClass data= new GetterSetterClass();
                int sid=cursor.getColumnIndex(KEY_SID);
                data.setId(cursor.getInt(sid));
                int sname=  cursor.getColumnIndex(KEY_SUPERNAME);
                data.setSupername(cursor.getString(sname));

                int logo = cursor.getColumnIndex(KEY_LOGO);
                data.setLogo(Integer.parseInt(cursor.getString(logo)));
                list.add(data);
                cursor.moveToNext();


            }


        }
        cursor.close();
        db.close();
        Log.e(TAG, list.toString());
        return list;
    }

    public List<GetterSetterClass> itemprice(int pid){
        SQLiteDatabase db=this.getReadableDatabase();
        List<GetterSetterClass> list =new ArrayList<GetterSetterClass>();
        String Sql="Select * from " + PRICES_TABLE + "   where " + KEY_PID + " = " + pid;


        Cursor cursor = db.rawQuery(Sql,null);

        if(cursor.moveToFirst()){
            while (cursor.isAfterLast()==false){
               GetterSetterClass data= new GetterSetterClass();
                int supid=cursor.getColumnIndex(KEY_SID);
                data.setSuperid(cursor.getInt(supid));
                int price=  cursor.getColumnIndex(KEY_PRICES);
                data.setPrices(cursor.getInt(price));
                list.add(data);
                cursor.moveToNext();
            }


        }
        cursor.close();
        db.close();
        return list;
    }



}
