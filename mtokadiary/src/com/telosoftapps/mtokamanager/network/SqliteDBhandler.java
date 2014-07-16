package com.telosoftapps.mtokamanager.network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.telosoftapps.mtokamanager.customs.IndividualVehicleList;
import com.telosoftapps.mtokamanager.customs.ListSClass;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SqliteDBhandler extends SQLiteOpenHelper{
	private static final  String  DATABASE_NAME="Mtokadb";
	private static final int DATABASE_VERSION=1;
	private static final String TABLE_LOGIN="login";
	private static final String TABLE_GROUPTABLE="group_table";
	private static final String TABLE_EMP="employee";
	private static final String TABLE_ASSET="asset";
	private static final String  TABLE_PPERFOMANCE="perfomance";
	
// table columns 
	
	private static final String KEY_ID="ID";
	private static final String KEY_USERNAME="username";
	private static final String KEY_EMAIL="email";
	private static final String KEY_ACTVE="ACTIVE";
	private static final String KEY_CREATED_AT="created_at";
	private static final String KEY_GROUP_NAME="group_name";
	
	private static final String KEY_MODEL="model";
	private static final String KEY_CAPACITY="capacity";
	private static final String KEY_INCHARGE="incharge";
	private static final String KEY_TYPE="type";
	private static final String KEY_CATEGORY="category";
	
	private static final String KEY_CHHR_RUSH="chargeRush";
	private static final String KEY_CHHR_NOM="chargeNom";
	private static final String KEY_TRIP_RUSH="tripRush";
	private static final String KEY_TRIP_NOM="tripNom";
	private static final String KEY_FUEL="fuel";
	private static final String KEY_DATEREPORT="datereport";
	private static final String KEY_MAINT="maintain";
	private static final String KEY_PARKING="parking";
	private static final String KEY_OTHERS="others";
	private static final String KEY_INSURANCE="insurance";
	private static final String KEY_STRTAMONUNT="strAmnt";
	private static final String KEY_ASSETID="assetid";
	private static final String TAG="SQLDB";
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
					KEY_EMAIL + " varchar(100) not null," +
					KEY_USERNAME + " varchar(100) not null," +
					KEY_ACTVE + " int(1) not null," + 
					KEY_CREATED_AT + " timestamp not null" + 
					");";
		db.execSQL(sql);
		//Log.d(TAG,"Create table login sql " + sql);
		
		String perfomsql="CREATE TABLE IF NOT EXISTS " + TABLE_PPERFOMANCE+ " (" +
				KEY_ID + " int(10) primary Key not null," +
				KEY_ASSETID + " varchar(20) not null," +
				KEY_CHHR_NOM + " REAL not null," +
				KEY_CHHR_RUSH + " REAL not null," + 
				KEY_TRIP_RUSH + " int(2) not null," + 
				KEY_TRIP_NOM + " int(2) not null," + 
				KEY_FUEL + " REAL not null," + 
				KEY_INSURANCE + " REAL not null," + 
				KEY_MAINT + " REAL not null," + 
				KEY_OTHERS + " REAL not null," + 
				KEY_PARKING + " REAL not null," + 
				KEY_STRTAMONUNT + " REAL not null," +
				KEY_DATEREPORT + " date not null" + 
				");";
	db.execSQL(perfomsql);
	//Log.d(TAG,"Create table perfom " + perfomsql);
	

		String assetsql="CREATE TABLE IF NOT EXISTS " + TABLE_ASSET+ " (" +
				KEY_ID + " int(10) primary Key not null," +
			
				KEY_MODEL + " varchar(50) not null," +
				KEY_GROUP_NAME + " varchar(50) not null," + 
				KEY_CATEGORY + " varchar(50) not null," + 
				KEY_CAPACITY + " varchar(50) not null," + 
				KEY_TYPE + " varchar(50) not null," + 
				KEY_INCHARGE + " varchar(50) not null," + 
				KEY_CREATED_AT + " date not null" + 
				");";
	db.execSQL(assetsql);
	//Log.d(TAG,"Create table  asset " + assetsql);
	
	

		String groupsql="CREATE TABLE IF NOT EXISTS  " + TABLE_GROUPTABLE + " (" +
				KEY_ID + " int(4) primary Key not null," +
				KEY_GROUP_NAME + " varchar(100) not null" +
				");";
		db.execSQL(groupsql);
	//	Log.d(TAG,"Create table group sql " + groupsql);
		String empsql="CREATE TABLE IF NOT EXISTS " + TABLE_EMP + " (" +
				KEY_ID + " int(4) primary Key not null," +
				KEY_USERNAME + " varchar(100) not null" +
				");";
		db.execSQL(empsql);
		//Log.d(TAG,"Create table emp sql " + empsql);
		
		String emptabl="CREATE TABLE IF NOT EXISTS " + TABLE_EMP + " (" +
				KEY_ID + " int(4) primary Key not null," +
				KEY_USERNAME + " varchar(100) not null" +
				");";
	db.execSQL(emptabl);
	
	//Log.d(TAG,"Create table emp sql " + emptabl);
		
		
		
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMP);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ASSET);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUPTABLE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PPERFOMANCE);
		
		onCreate(db);
		
	}
	/*@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		super.onDowngrade(db, oldVersion, newVersion);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMP);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ASSET);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUPTABLE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PPERFOMANCE);
		
		onCreate(db);
	}*/
	public void adduser(String tel,String username,String email,int active,String created_at){
		
		try {
			db = this.getWritableDatabase();
			ContentValues values = new ContentValues();
				values.put(KEY_ID, tel);
				values.put(KEY_USERNAME, username);
				values.put(KEY_EMAIL, email);
				values.put(KEY_ACTVE, active);
				values.put(KEY_CREATED_AT, created_at);
				db.insertOrThrow(TABLE_LOGIN, null, values);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db.close();
		
	}
	public void addAsset(String id,String model,String group,String category,String capacity,String type,String incharge,String created_at){
	
		try {
			db = this.getWritableDatabase();
			ContentValues values = new ContentValues();
				values.put(KEY_ID, id);
				values.put(KEY_MODEL, model);
				values.put(KEY_GROUP_NAME, group);
				values.put(KEY_CATEGORY, category);
				values.put(KEY_CAPACITY, capacity);
				values.put(KEY_TYPE, type);
				values.put(KEY_INCHARGE, incharge);
				values.put(KEY_CREATED_AT, created_at);
				db.insertOrThrow(TABLE_ASSET, null, values);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db.close();
		
	}
	public void addPerfomance(int id,String assetid,double chargeN,double chargeR,int trpN,int trpR,
			double fuel,double maint, double park,double others, double insurance,double strAmnt,String dateR){
	
		try {
			db = this.getWritableDatabase();
			ContentValues values = new ContentValues();
				values.put(KEY_ID, id);
				values.put(KEY_ASSETID, assetid);
				values.put(KEY_CHHR_NOM,  chargeN);
				values.put(KEY_CHHR_RUSH, chargeR);
				values.put(KEY_TRIP_RUSH, trpR);
				values.put(KEY_TRIP_NOM, trpN);
				values.put(KEY_FUEL, fuel);
				values.put(KEY_INSURANCE, insurance);
				values.put(KEY_MAINT,maint);
				values.put(KEY_OTHERS, others);
				values.put(KEY_PARKING, park);
				values.put(KEY_STRTAMONUNT,strAmnt);
				values.put(KEY_DATEREPORT,  dateR);
				db.insertOrThrow(TABLE_PPERFOMANCE, null, values);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		db.close();
		
	}
	public void addgroup(int id,String groupname){
		
		try {
			db = this.getWritableDatabase();
			ContentValues values = new ContentValues();
				values.put(KEY_ID, id);
				values.put(KEY_GROUP_NAME, groupname);
				db.insertOrThrow(TABLE_GROUPTABLE, null, values);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db.close();
		
	}
	public void addIncharge(int id,String username){
		
		try {
			db = this.getWritableDatabase();
			ContentValues values = new ContentValues();
				values.put(KEY_ID, id);
				values.put(KEY_USERNAME, username);
				db.insertOrThrow(TABLE_EMP, null, values);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
        public List<ListSClass> getAll(){
    		List<ListSClass> labels = new ArrayList<ListSClass>();
        	
            // Select All Query
            String selectQuery = "SELECT * FROM " + TABLE_ASSET;
         
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            
         
          
         
            // looping through all rows and adding to list
           // int index=0;
           
            if (cursor.moveToFirst()) {
            	 //  Log.d(TAG, " get all asset row Count= " + cursor.getCount());
            	
          while (cursor.isAfterLast() == false){
        	  ListSClass list =new ListSClass();
                	int id =cursor.getColumnIndexOrThrow(KEY_ID);
                	list.setAssetid(cursor.getString(id));
                	int model =cursor.getColumnIndexOrThrow(KEY_MODEL);
                	list.setModel(cursor.getString(model));
                	int group =cursor.getColumnIndexOrThrow(KEY_GROUP_NAME);
                	list.setGroupName(cursor.getString(group));
                	int category =cursor.getColumnIndexOrThrow(KEY_CATEGORY);
                	list.setCategory(cursor.getString(category));
                	int capacity =cursor.getColumnIndexOrThrow(KEY_CAPACITY);
                	list.setCapacity(cursor.getString(capacity));
                	int type =cursor.getColumnIndexOrThrow(KEY_TYPE);
                	list.setType(cursor.getString(type));
                	int incharge =cursor.getColumnIndexOrThrow(KEY_INCHARGE);
                	list.setIncharge(cursor.getString(incharge));
                	int createdAt =cursor.getColumnIndexOrThrow(KEY_CREATED_AT);
                	list.setCreated_at(cursor.getString(createdAt));
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
       public List<IndividualVehicleList> getAssetData(String id){
      	  List<IndividualVehicleList> label = new ArrayList<IndividualVehicleList>();
      	  String selectAsset = "SELECT group_name,created_at,capacity FROM " + TABLE_ASSET + " Where  "+ KEY_ID+" = "+ "'"+ id + "'";
            
            SQLiteDatabase db = this.getReadableDatabase();
          
            Cursor cursor2 = db.rawQuery(selectAsset, null);
            
            if (cursor2.moveToFirst()) {
            	//   Log.d(TAG, "asset data row Count= " + cursor2.getCount());
            	
          while (cursor2.isAfterLast() == false){
           IndividualVehicleList list =new IndividualVehicleList();
           
      		int group =cursor2.getColumnIndexOrThrow(KEY_GROUP_NAME);
          	list.setGroup(cursor2.getString(group));
          	int createdAt =cursor2.getColumnIndexOrThrow(KEY_CREATED_AT);
          	list.setCreatedat(cursor2.getString(createdAt));
          	label.add(list);
          	cursor2.moveToNext();
          } 
      }

      // closing connection
      cursor2.close();
      db.close();

      // returning lables
      return label;
      	  
       }
        
       
        
  public List<IndividualVehicleList> getID(){
	  List<IndividualVehicleList> label = new ArrayList<IndividualVehicleList>();
	  String selectAsset = "SELECT * from " + TABLE_LOGIN;
      
      SQLiteDatabase db = this.getReadableDatabase();
    
      Cursor cursor2 = db.rawQuery(selectAsset, null);
      
      if (cursor2.moveToFirst()) {
      	   //Log.d(TAG, "row Count from login table= " + cursor2.getCount());
      	
    while (cursor2.isAfterLast() == false){
     IndividualVehicleList list =new IndividualVehicleList();
	     
	     int tel=cursor2.getColumnIndexOrThrow(KEY_ID);
	  	list.setTelno(cursor2.getString(tel));
	 	label.add(list);
    	label.add(list);
    	cursor2.moveToNext();
    } 
}

// closing connection
cursor2.close();
db.close();

// returning lables
return label;
	  
 }

  public List<IndividualVehicleList> getPeromance(String id){
    		List<IndividualVehicleList> label = new ArrayList<IndividualVehicleList>();
        	
            // Select All Query
            //String selectQuery = "SELECT * FROM " + TABLE_PPERFOMANCE + " Where assetid="+ "'"+ id + "'";
           String selectQuery ="select * from " + TABLE_PPERFOMANCE + "  as p inner join  " +  TABLE_ASSET + " as tA ON p.assetid= tA.ID "
           		+ "where tA.ID="+ "'"+ id + "'";
            
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
         
            
         
          
         
            // looping through all rows and adding to list
           // int index=0;
           
            if (cursor.moveToFirst()) {
            	  // Log.d(TAG, "perfomance row Count = " + cursor.getCount());
            	
          while (cursor.isAfterLast() == false){
        	  IndividualVehicleList list =new IndividualVehicleList();
                    int assetid =cursor.getColumnIndexOrThrow(KEY_ASSETID);
                	list.setAssetid(cursor.getString(assetid));
                	int chargNOM =cursor.getColumnIndexOrThrow(KEY_CHHR_NOM);
                	list.setChargesnormal(cursor.getDouble(chargNOM));
                	int chargeRush =cursor.getColumnIndexOrThrow(KEY_CHHR_RUSH);
                	list.setChargesrush(cursor.getDouble(chargeRush));
                	int tripNOM =cursor.getColumnIndexOrThrow(KEY_TRIP_NOM);
                	list.setTripnormalhr(cursor.getInt(tripNOM));
                	int tripRush =cursor.getColumnIndexOrThrow(KEY_TRIP_RUSH);
                	list.setTripsrushhr(cursor.getInt(tripRush));
                	int fuel =cursor.getColumnIndexOrThrow(KEY_FUEL);
                	list.setFuelcost(cursor.getDouble(fuel));
                	int insurance =cursor.getColumnIndexOrThrow(KEY_INSURANCE);
                	list.setInsurance(cursor.getDouble(insurance));
                	int maint =cursor.getColumnIndexOrThrow(KEY_MAINT);
                	list.setMaintenacecost(cursor.getDouble(maint));
                	int park =cursor.getColumnIndexOrThrow(KEY_PARKING);
                	list.setPackingfee(cursor.getDouble(park));
                	int other =cursor.getColumnIndexOrThrow(KEY_OTHERS);
                	list.setOthers(cursor.getDouble(other));
                	int reportDate=cursor.getColumnIndexOrThrow(KEY_DATEREPORT);
                	list.setDateOfReport(cursor.getString(reportDate));
            		int group =cursor.getColumnIndexOrThrow(KEY_GROUP_NAME);
                  	list.setGroup(cursor.getString(group));
                  	int createdAt =cursor.getColumnIndexOrThrow(KEY_CREATED_AT);
                  	list.setCreatedat(cursor.getString(createdAt));
                	int typeuse =cursor.getColumnIndexOrThrow(KEY_TYPE);
                  	list.setTypeUse(cursor.getString(typeuse));
                  	int capacity =cursor.getColumnIndexOrThrow(KEY_CAPACITY);
                  	list.setCapacity(cursor.getInt(capacity));
                
                
                	label.add(list);
                	cursor.moveToNext();
                } 
            }
       
        // closing connection
        cursor.close();
        db.close();
    	
    	// returning lables
    	return label;
    }

}
