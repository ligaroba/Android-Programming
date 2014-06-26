package com.telosoft.mtokamanager.network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SqliteDBhandler extends SQLiteOpenHelper{
	private static final  String  DATABASE_NAME="Mtokadb";
	private static final int DATABASE_VERSION=1;
	private static final String TABLE_LOGIN="login";
	private static final String TABLE_GROUPTABLE="group_table";
	private static final String TABLE_EMP="employee";
	
// table columns 
	
	private static final String KEY_ID="ID";
	private static final String KEY_USERNAME="username";
	private static final String KEY_EMAIL="email";
	private static final String KEY_ACTVE="ACTIVE";
	private static final String KEY_CREATED_AT="created_at";
	private static final String KEY_GROUP_NAME="group_name";
	private static final String TAG="SQLDB";
	
	public SqliteDBhandler(Context context) {
		// TODO Auto-generated constructor stub
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String sql="CREATE TABLE " + TABLE_LOGIN + " (" +
					KEY_ID + " varchar(50) primary Key not null," +
					KEY_EMAIL + " varchar(100) not null," +
					KEY_USERNAME + " varchar(100) not null," +
					KEY_ACTVE + " int(1) not null," + 
					KEY_CREATED_AT + " timestamp not null" + 
					");";
		db.execSQL(sql);
		Log.d(TAG,"Create table login sql " + sql);
		String groupsql="CREATE TABLE " + TABLE_GROUPTABLE + " (" +
				KEY_ID + " int(4) primary Key not null," +
				KEY_GROUP_NAME + " varchar(100) not null" +
				");";
		db.execSQL(groupsql);
		Log.d(TAG,"Create table group sql " + groupsql);
		String emptabl="CREATE TABLE " + TABLE_EMP + " (" +
				KEY_ID + " int(4) primary Key not null," +
				KEY_USERNAME + " varchar(100) not null" +
				");";
	db.execSQL(emptabl);
	Log.d(TAG,"Create table emp sql " + emptabl);
		
		
		
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);
		onCreate(db);
		
	}
	public void adduser(String tel,String username,String email,int active,String created_at){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
			values.put(KEY_ID, tel);
			values.put(KEY_USERNAME, username);
			values.put(KEY_EMAIL, email);
			values.put(KEY_ACTVE, active);
			values.put(KEY_CREATED_AT, created_at);
			db.insert(TABLE_LOGIN, null, values);
		db.close();
		
	}
	public void addgroup(int id,String groupname){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
			values.put(KEY_ID, id);
			values.put(KEY_GROUP_NAME, groupname);
			db.insert(TABLE_GROUPTABLE, null, values);
		db.close();
		
	}
	public void addemp(int id,String username){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
			values.put(KEY_ID, id);
			values.put(KEY_USERNAME, username);
			db.insert(TABLE_EMP, null, values);
		db.close();
		
	}
	
	public HashMap<String,String> getLoginDetails(){
			String sqlQuery = "Select * from " + TABLE_LOGIN;
			SQLiteDatabase db = this.getReadableDatabase();
			HashMap<String, String> user = new HashMap<String, String>();
			Cursor cursor = db.rawQuery(sqlQuery, null);
			
			cursor.moveToFirst();
			if(cursor.getCount()>0){
				user.put("telno",cursor.getString(1));
				user.put("username", cursor.getString(2));
				user.put("email", cursor.getString(3));
				user.put("created_at", cursor.getString(5));
				
				
				
			}
			db.close();
			cursor.close();
			
		return user;
		
		
	}
	public void resetTables(String tablename) {
		// TODO Auto-generated method stub
		SQLiteDatabase db =this.getWritableDatabase();
		db.delete(tablename, null,null);
		db.close();
		
	}

	public int getRowCount() {
		// TODO Auto-generated method stub
		String selectQuery = "Select * from " + TABLE_LOGIN;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		int rowCount = cursor.getCount();
		db.close();
		cursor.close();
		
		return rowCount;
	}
	
	public ArrayList<String> getAllLabels(String param,String tablename){
		ArrayList<String> labels = new ArrayList<String>();
    	
        // Select All Query
        String selectQuery = "SELECT "+ param +" FROM " + tablename;
     
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
     
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	int groupName =cursor.getColumnIndexOrThrow(KEY_GROUP_NAME);
            	labels.add(cursor.getString(groupName));
            } while (cursor.moveToNext());
        }
        
        // closing connection
        cursor.close();
        db.close();
    	
    	// returning lables
    	return labels;
    }

}
