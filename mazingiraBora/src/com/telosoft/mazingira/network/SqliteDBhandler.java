package com.telosoft.mazingira.network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.telosoft.mazingira.customs.IndividualOrgList;
import com.telosoft.mazingira.customs.ListSClass;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SqliteDBhandler extends SQLiteOpenHelper{
	private static final  String  DATABASE_NAME="Mazingira";
	private static final int DATABASE_VERSION=1;
	private static final String TABLE_LOGIN="login";
	
	
// table columns 
	
	private static final String KEY_ID="ID";
	private static final String KEY_ORGNAME="username";
	
	
	private static final String KEY_SUBMODEL="submodel";
	private static final String KEY_MEMBERS="membersCap";
	private static final String KEY_ORGSPEC="orgspeciality";
	private static final String KEY_LOCATION="location";
	

	SQLiteDatabase db;
	public SqliteDBhandler(Context context) {
		// TODO Auto-generated constructor stub
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String sql="CREATE TABLE IF NOT EXISTS " + TABLE_LOGIN + " (" +
					KEY_ID + " varchar(50) primary Key not null," +
					KEY_ORGNAME + " varchar(100) not null," +
					KEY_ORGSPEC + " varchar(100) not null," + 
					KEY_MEMBERS + " int(10) not null," +
					KEY_SUBMODEL + " varchar(100) not null," +
					KEY_LOCATION + " varchar(100) not null" +  
					");";
		db.execSQL(sql);
		//Log.d(TAG,"Create table login sql " + sql);
		
		
	}
	public List<ListSClass> getAll(){
		List<ListSClass> labels = new ArrayList<ListSClass>();
    	
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_LOGIN;
     
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        
     
      
     
        // looping through all rows and adding to list
       // int index=0;
       
        if (cursor.moveToFirst()) {
        	 //  Log.d(TAG, " get all asset row Count= " + cursor.getCount());
        	
      while (cursor.isAfterLast() == false){
    	  ListSClass list =new ListSClass();
            	int id =cursor.getColumnIndexOrThrow(KEY_ID);
            	list.setId(cursor.getString(id));
            	int orgname =cursor.getColumnIndexOrThrow(KEY_ORGNAME);
            	list.setOrgname(cursor.getString(orgname));
            	int member =cursor.getColumnIndexOrThrow(KEY_MEMBERS);
            	list.setMembers(cursor.getString(member));
            	int orgspec =cursor.getColumnIndexOrThrow(KEY_ORGSPEC);
            	list.setOrgspec(cursor.getString(orgspec));
            	int submodel =cursor.getColumnIndexOrThrow(KEY_SUBMODEL);
            	list.setSubmodel(cursor.getString(submodel));
            	            	
            	labels.add(list);
            	cursor.moveToNext();
            } 
        }
   
    // closing connection
    cursor.close();
    db.close();
	
	// returning lables
	return labels;
}
public void adduser(String contact,String  orgname,String orgspec,String member,String submodel,String location){
		
		try {
			db = this.getWritableDatabase();
			ContentValues values = new ContentValues();
				values.put(KEY_ID, contact);
				values.put(KEY_ORGNAME, orgname);
				values.put(KEY_ORGSPEC, orgspec);
				values.put(KEY_MEMBERS, member);
				values.put(KEY_SUBMODEL, submodel);
				values.put(KEY_LOCATION, location);
				db.insertOrThrow(TABLE_LOGIN, null, values);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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


	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}


}
