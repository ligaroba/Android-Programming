package com.telosoft.mazingira.network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.telosoft.mazingira.customs.Constants;
import com.telosoft.mazingira.functions.Functions;

public class BackGroundService extends Service{
JSONArray json_initiative;	
public JSONObject jsoninitiative;
private static final String TAG = BackGroundService.class.getSimpleName();
boolean running =false;
SqliteDBhandler db;

public BackGroundService(){}

public BackGroundService(Context context){

		db =new SqliteDBhandler(context);
	
	
	
	
}
		@Override
		public void onCreate() {
			// TODO Auto-generated method stub
			super.onCreate();
		

		}





		@Override
		public int onStartCommand(Intent intent, int flags, int startId) {
			// TODO Auto-generated method stub
		
				
			
			  
			 
			
		new Thread(){
			public void run(){
			
						
				
			jsoninitiative = new JSONHandler().JsonConnection(new Functions().getAllItems("initiative", "1"));
			
		
			
			db=new SqliteDBhandler(getApplicationContext());
	
			if(jsoninitiative != null){
				
				db.resetTables("login");
				try {
					json_initiative = jsoninitiative.getJSONArray("data");
				} catch (JSONException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
					 for (int i = 0; i < json_initiative.length(); i++) {
	                        JSONObject initiative = null;
							try {
								initiative = json_initiative.getJSONObject(i);
							} catch (JSONException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
//	 update spinners
					
	                      
							try {
								db.adduser(initiative.getString("contact").toLowerCase(),
										initiative.getString("orgname").toLowerCase(), 
										initiative.getString("orgspeciality").toLowerCase(),
										initiative.getString("members").toLowerCase(),
										initiative.getString("subcripmodel").toLowerCase(),
										initiative.getString("location").toLowerCase());
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							
					
	                   									
	                       
	                    }	 
				
			}
			}
			}.start();
			return startId;
		   
		}





		@Override
		public void onDestroy() {
			// TODO Auto-generated method stub
			super.onDestroy();
		
		}





		@Override
		public IBinder onBind(Intent intent) {
			// TODO Auto-generated method stub
			return null;
		}
	
	
	
	
}
