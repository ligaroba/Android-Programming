package com.telosoftapps.mtokamanager.network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.telosoftapps.mtokamanager.customs.Constants;
import com.telosoftapps.mtokamanager.functions.Functions;

public class BackGroundService extends Service{
JSONArray json_Incharge,json_group,json_Asset,json_perfom;	
public JSONObject jsonAsset,jsongroup,jsonperfom,jsonIncharge;
private static final String TAG = "BackGroundService";
boolean running =false;
SqliteDBhandler db;
private String message,tel,asseid;
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
			//Log.d(TAG, "onStartCommand" + json.toString());	
			SharedPreferences telno = getSharedPreferences(Constants.PREFS_USERID, 0);
			SharedPreferences settings = getSharedPreferences(Constants.PREFS_ASSET, 0);
			  if(settings.getString(Constants.KEY_ASSETID, null)!=null){
				  asseid= settings.getString(Constants.KEY_ASSETID, null);
			  }
			  if(telno.getString(Constants.KEY_TEL, null)!=null){
				 tel= telno.getString(Constants.KEY_TEL, null);
			  }
			  if(settings.getString(Constants.KEY_TEL, null)!=null){
					 tel= settings.getString(Constants.KEY_TEL, null);
			}
			  
			  Log.d(TAG, "asset Id " + asseid + " and tel number " + tel);
			
		new Thread(){
			public void run(){
				
						
		try {
			
			Log.d(TAG, "the value of userid " + tel);
			Log.d(TAG, "the value of assetid" + asseid);
			jsongroup = new JSONHandler().JsonConnection(new Functions().getAllItems("group_table", "7"));
			jsonAsset = new JSONHandler().JsonConnection(new Functions().getAllAsset("asset", "3",tel));
			jsonperfom = new JSONHandler().JsonConnection(new Functions().getPerfomance(asseid,"9"));
			jsonIncharge = new JSONHandler().JsonConnection(new Functions().getAllItems("asset_incharge", "8"));
			
			db=new SqliteDBhandler(getApplicationContext());
	
			if(jsongroup != null){
				
				db.resetTables("employee");
				json_Incharge = jsonIncharge.getJSONArray("data");
					db.addIncharge(-1,"None");
					 for (int i = 0; i < json_Incharge.length(); i++) {
	                        JSONObject incharge = json_Incharge.getJSONObject(i);
//	 update spinners
							 Log.d(TAG, "Backgroundservice 8   " + incharge.toString());
						 try {
							db.addIncharge(incharge.getInt("incharge_id"),incharge.getString("incharge_jobname").toLowerCase());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							Log.d(TAG, "sql exception " + e.toString());
						}
	                   									
	                       
	                    }	 
				
			}if(jsonIncharge!= null){	
				db.resetTables("group_table");
				json_group = jsongroup.getJSONArray("data");
				db.addgroup(-1,"None");
				 for (int i = 0; i < json_group.length(); i++) {
                        JSONObject group = json_group.getJSONObject(i);
//                    	 update spinners
						 Log.d(TAG, "BacgroundService    " + group.toString());
						 try {
							db.addgroup(group.getInt("group_id"),group.getString("group_name").toLowerCase());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                 
					
                       
                    }
									
		
			}if(jsonAsset!= null){			
						 
				db.resetTables("asset");
				if(Integer.parseInt(jsonAsset.getString(Constants.KEY_SUCCESS))==3){
				
						json_Asset = jsonAsset.getJSONArray("data");
						 for (int i = 0; i < json_Asset.length(); i++) {
							  JSONObject Asset= json_Asset.getJSONObject(i); 
//	                    	 update spinners
								 Log.d(TAG, "Doinginbackgroud response   " + Asset.toString());
								 try {
									db.addAsset(
											 Asset.getString("assetid")
								,Asset.getString("model").toLowerCase()
								,Asset.getString("group_name").toLowerCase()
								,Asset.getString("category").toLowerCase()
								,Asset.getString("capacity").toLowerCase()
								,Asset.getString("typeasset").toLowerCase()
								,Asset.getString("asset_incharge").toLowerCase()
								,Asset.getString("created_at").toLowerCase());
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
											
						 }  
			
	
				}
			}if(jsonperfom != null){
					json_perfom = jsonperfom.getJSONArray("data");
					db.resetTables(Constants.TABLE_PPERFOMANCE);
			for (int i = 0; i < json_perfom.length(); i++) {
	                        JSONObject perfom = json_perfom.getJSONObject(i);
	                      
	                 //update spinners
							 Log.d(TAG, "Backgroundservice 9   " + perfom.toString());
		try {
			db.addPerfomance(perfom.getInt("expenseid"), perfom.getString("assetid")
					,perfom.getDouble("chargesnormal"),perfom.getDouble("chargesrush"),perfom.getInt("tripnormalhr"),
					perfom.getInt("tripsrushhr"),perfom.getDouble("fuel_cost"),
					perfom.getDouble("maintenace_cost"),perfom.getDouble("packing_fee"),
					perfom.getDouble("others"),
					perfom.getDouble("insurance"),perfom.getDouble("startamount"),perfom.getString("dateofreport"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 
							
	                    }
										
				
				
					
				
		  /*   }//if(json.getString(KEY_SUCCESS)!=null)
		     else{
		    	 message="Welcome To mtoka";
		     }*/
				
			
			}else {
				Log.d(TAG, "No data fetched  "   );
			} 
		
		} catch (JSONException jsonError) {
				// TODO Auto-generated catch block
				jsonError.printStackTrace();
			}
					
					
					try {
						
						Thread.sleep(Constants.PERIODIC_EVENT_TIMEOUT);
						
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					 
					
				
			}
	
		}.start();
	
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
		return super.onStartCommand(intent, flags, startId);
		
		   
		}





		@Override
		public void onDestroy() {
			// TODO Auto-generated method stub
			super.onDestroy();
			running = false;
		}





		@Override
		public IBinder onBind(Intent intent) {
			// TODO Auto-generated method stub
			return null;
		}
	
	
	
	
}