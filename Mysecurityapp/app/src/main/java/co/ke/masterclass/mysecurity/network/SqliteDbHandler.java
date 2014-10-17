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
    private static final String DB_NAME="mysecurity";
    private static final String PERSONS_TABLE="Person";
    private static final String USER_TABLE="user";
    private static final String CONTACTS_TABLE="EmergencyLines";
    private static final int DB_VERSION=1;

    // Variables

    private static final String KEY_ID="_ID";
    private static final String KEY_NAME="person_name";
    private static final String KEY_GENDER="gender";
    private static final String KEY_CONTACTNAME="organization";
    private static final String KEY_CONTACT="contact";
    private static final String KEY_DESCRIPTION="description";
    private static final String KEY_DATE_REPORTED="dateReported";
    private static final String KEY_LOCATION="loaction";
    private static final String KEY_STATUS="status";
    private static final String KEY_PHOTO="photo";
    private static final String KEY_IDENTITY="identity_num";

    private static final String KEY_EMAIL="email";
    private static final String KEY_PHONE="phone";
    private static final String KEY_LOCALITY="locality";
    private static final String KEY_SOCIAL="social";
    private static final String KEY_DATEADDED="dateAdded";
    String TAG="SqlitedbHandler";


    public SqliteDbHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String personsql="CREATE TABLE  " + PERSONS_TABLE + " (" +
                KEY_ID + " int(5) not null primary key ," +
                KEY_NAME + " varchar(100) not null," +
               KEY_GENDER +  " char(2) not null," +
               KEY_DESCRIPTION +  " TEXT not null," +
               KEY_IDENTITY +  " varchar(20) null," +
               KEY_LOCATION + " varchar(20) null," +
               KEY_PHOTO + " varchar(30) null," +
               KEY_DATE_REPORTED +  " varchar(20) not null," +
               KEY_STATUS +  " int(1) not null)";
        db.execSQL(personsql);

        String usernsql="CREATE TABLE  " + USER_TABLE + " (" +
                KEY_ID + " int(5) not null primary key ," +
                KEY_NAME + " varchar(100) not null," +
                KEY_EMAIL +  " varchar(100) not null," +
                KEY_PHONE +  " varchar(100) not null," +
                KEY_SOCIAL +  " varchar(100) null," +
                KEY_LOCALITY + " varchar(100) null," +
               KEY_DATEADDED +  " varchar(100) not null)";
        db.execSQL(usernsql);



        String contactssql="CREATE TABLE  " + CONTACTS_TABLE + " (" +
                KEY_ID + " int(5) not null primary key ," +
                KEY_CONTACTNAME + " varchar(100) not null," +
                KEY_CONTACT +  " char(2) not null)";
        db.execSQL(contactssql);

    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }


    public void addPerson(int id, String name, String gender, String description, String identity,
                          String location, String photo,String dateReport, int status){

       SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID,id);
        values.put(KEY_NAME,name);
        values.put(KEY_GENDER,gender);
        values.put(KEY_DESCRIPTION,description);
        values.put(KEY_IDENTITY,identity);
        values.put(KEY_LOCATION,location);
        values.put(KEY_PHOTO,photo);
        values.put(KEY_DATE_REPORTED,dateReport);
        values.put(KEY_STATUS,status);
        db.insertOrThrow(PERSONS_TABLE,null,values);
        db.close();
    }

    public void addUser(int id,String name, String email, String phone, String social, String locality,
                          String dateadded){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID,id);
        values.put(KEY_NAME,name);
        values.put(KEY_EMAIL,email);
        values.put(KEY_PHONE,phone);
        values.put(KEY_SOCIAL,social);
        values.put(KEY_LOCALITY,locality);
        values.put(KEY_DATEADDED,dateadded);
       db.insertOrThrow(USER_TABLE,null,values);
        db.close();
    }

    public void addContact(int id, String orgname, String contact){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID,id);
        values.put(KEY_CONTACTNAME,orgname);
        values.put(KEY_CONTACT,contact);

        db.insertOrThrow(CONTACTS_TABLE,null,values);
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
    public List<CustomSeterClass> getPersons(int status){
        SQLiteDatabase db=this.getReadableDatabase();
        List<CustomSeterClass> list =new ArrayList<CustomSeterClass>();
        String Sql="Select * from " + PERSONS_TABLE + "  Where " + KEY_STATUS + "= "+ status;


        Cursor cursor = db.rawQuery(Sql,null);

        if(cursor.moveToFirst()){
            while (cursor.isAfterLast()==false){
              CustomSeterClass data= new CustomSeterClass();
             int id=cursor.getColumnIndex(KEY_ID);
              data.setId(cursor.getInt(id));
              int name=  cursor.getColumnIndex(KEY_NAME);
              data.setName(cursor.getString(name));
              int des= cursor.getColumnIndex(KEY_DESCRIPTION);
              data.setDescription(cursor.getString(des));
              int photo = cursor.getColumnIndex(KEY_PHOTO);
               data.setPhoto(cursor.getString(photo));
              int location= cursor.getColumnIndex(KEY_LOCATION);
              data.setLocation(cursor.getString(location));
              list.add(data);
              cursor.moveToNext();

            }


        }
        cursor.close();
        db.close();

        return list;
    }


    public List<CustomSeterClass> getPersonsDetails(int id){
        SQLiteDatabase db=this.getReadableDatabase();
        List<CustomSeterClass> list =new ArrayList<CustomSeterClass>();
        String Sql="Select * from " + PERSONS_TABLE + "  Where " + KEY_ID + "= "+ id;


        Cursor cursor = db.rawQuery(Sql,null);

        if(cursor.moveToFirst()){
            while (cursor.isAfterLast()==false){
                CustomSeterClass data= new CustomSeterClass();
                int pdid=cursor.getColumnIndex(KEY_ID);
                data.setId(cursor.getInt(pdid));
                int name=  cursor.getColumnIndex(KEY_NAME);
                data.setName(cursor.getString(name));

                int des= cursor.getColumnIndex(KEY_DESCRIPTION);
                data.setDescription(cursor.getString(des));
                int photo = cursor.getColumnIndex(KEY_PHOTO);
                data.setPhoto(cursor.getString(photo));

                int location= cursor.getColumnIndex(KEY_LOCATION);
                data.setLocation(cursor.getString(location));
                int gender = cursor.getColumnIndex(KEY_GENDER);
                data.setGender(cursor.getString(gender));
                list.add(data);
                cursor.moveToNext();


            }


        }
        cursor.close();
        db.close();
        Log.e(TAG,list.toString());
        return list;
    }

    public List<CustomSeterClass> getEmergContacts(){
        SQLiteDatabase db=this.getReadableDatabase();
        List<CustomSeterClass> list =new ArrayList<CustomSeterClass>();
        String Sql="Select * from " + CONTACTS_TABLE ;


        Cursor cursor = db.rawQuery(Sql,null);

        if(cursor.moveToFirst()){
            while (cursor.isAfterLast()==false){
                CustomSeterClass data= new CustomSeterClass();
                int orgname=cursor.getColumnIndex(KEY_CONTACTNAME);
                data.setOrganme(cursor.getString(orgname));
                int contact=  cursor.getColumnIndex(KEY_CONTACT);
                data.setContact(cursor.getString(contact));
                list.add(data);
                cursor.moveToNext();
            }


        }
        cursor.close();
        db.close();
       return list;
    }

}
