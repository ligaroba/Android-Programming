package com.telosoftapps.mtokamanager.functions;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.util.Log;

import com.telosoftapps.mtokamanager.network.JSONHandler;
import com.telosoftapps.mtokamanager.network.SqliteDBhandler;

public class Functions {
	


	
	private static String REGISTER_TAG="register_user";
	private static String POST_TAG="tag";
	private static String LOGIN_TAG="login_user";
	private static String INCHARGE_TAG="new_incharge";
	private static String DELETE_TAG="delete";
	private static String REGISTER_GROUP_TAG="register_group";
	private static String GET_ALLITEMS_TAG="get_all_data";
	private static String REGISTER_ASSET_TAG="register_asset";
	private static String MAKE_REPORT="make_report";
	private static String GET_PERFOMANCE="get_perfomance";
	private static String GET_ASSETS="get_all_asset";
	   private static String TAG="Functions tag";
	
	JSONHandler jsonParser=new JSONHandler();
	public Functions(){
		
		
	}
	// Login in user
	public List<NameValuePair> loginUser(String tel, String password){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(POST_TAG, LOGIN_TAG));
		params.add(new BasicNameValuePair("telno", tel));
		params.add(new BasicNameValuePair("password", password));
		return params;
		
	}
	
	// new user registration 
	public List<NameValuePair> registerUser(String telno,String fname,String lname ,String username,String email, String password){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(POST_TAG, REGISTER_TAG));
		params.add(new BasicNameValuePair("telno",telno));
		params.add(new BasicNameValuePair("fname",fname));
		params.add(new BasicNameValuePair("lname", lname));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("username", username));
		params.add(new BasicNameValuePair("password", password));
		
		return params;
	
	}
	public List<NameValuePair> registerAsset(String assetid,String telno,
			String model,String category,String groupname
			,String capacity, String typeofasset,String incharge){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(POST_TAG, REGISTER_ASSET_TAG));
		params.add(new BasicNameValuePair("assetid",assetid));
		params.add(new BasicNameValuePair("telno",telno));
		params.add(new BasicNameValuePair("model", model));
		params.add(new BasicNameValuePair("category",category));
		params.add(new BasicNameValuePair("group_name", groupname));
		params.add(new BasicNameValuePair("capacity", capacity));
		params.add(new BasicNameValuePair("typeasset", typeofasset));
		params.add(new BasicNameValuePair("incharge", incharge));
		
		//Log.d(TAG,"The register asset tag name is  " + REGISTER_ASSET_TAG);
		
		return params;
	
	}
	public List<NameValuePair> MakeReport(String assetid,String stAmount, String cRush,
			String cNomal, String fuel, String maintanance,
			String parking, String insurance, String others,
			String tRush, String tNom){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(POST_TAG, MAKE_REPORT));
		params.add(new BasicNameValuePair("assetid",assetid));
		params.add(new BasicNameValuePair("capital",stAmount));
		params.add(new BasicNameValuePair("cRush", cRush));
		params.add(new BasicNameValuePair("cNomal",cNomal));
		params.add(new BasicNameValuePair("fuel", fuel));
		params.add(new BasicNameValuePair("maintanance", maintanance));
		params.add(new BasicNameValuePair("parking", parking));
		params.add(new BasicNameValuePair("insurance", insurance));
		params.add(new BasicNameValuePair("others", others));
		params.add(new BasicNameValuePair("tRush", tRush));
		params.add(new BasicNameValuePair("tNom", tNom));
		
		//Log.d(TAG,"The register asset tag name is  " + MAKE_REPORT);
		
		return params;
	
	}
	public List<NameValuePair> newGroup(String groupname){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(POST_TAG,REGISTER_GROUP_TAG));
		params.add(new BasicNameValuePair("group_name",groupname));
		
	//	Log.d(TAG,"The recieved group name is  " + groupname);
		return params;
		
	}
	public List<NameValuePair> getAllItems(String tablename,String value){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(POST_TAG,GET_ALLITEMS_TAG));
		params.add(new BasicNameValuePair("tablename",tablename));
		  params.add(new BasicNameValuePair("value",value));
		//Log.d(TAG,"The get all items tag name is  " + tablename);
		return params;
		
	}
	public List<NameValuePair> getAllAsset(String tablename,String value,String tel){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(POST_TAG,GET_ASSETS));
		params.add(new BasicNameValuePair("tablename",tablename));
		  params.add(new BasicNameValuePair("value",value));
		  params.add(new BasicNameValuePair("telno",tel));
	//	Log.d(TAG,"The get all items tag name is  " + tablename);
		return params;
		
	}
	public List<NameValuePair> getPerfomance(String assetid,String value){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(POST_TAG,GET_PERFOMANCE));
		params.add(new BasicNameValuePair("assetid",assetid));
		  params.add(new BasicNameValuePair("value",value));
	//	Log.d(TAG,"The get all Performance tag name is  " + assetid);
		return params;
		
	}
	public List<NameValuePair> delete(String tablename,String colid,String recid){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(POST_TAG,DELETE_TAG));
		params.add(new BasicNameValuePair("tablename",tablename));
		params.add(new BasicNameValuePair("colid",colid));
		params.add(new BasicNameValuePair("recid",recid));
		
		return params;
		
	}
	public List<NameValuePair> newIncharge(String name, String username,String telno) {
		// TODO Auto-generated method stub
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(POST_TAG,INCHARGE_TAG));
		params.add(new BasicNameValuePair("fullnames",name));
		params.add(new BasicNameValuePair("username",username));
		params.add(new BasicNameValuePair("tel",telno));
		
	//	Log.d(TAG,"The recieved incharge name is  " + name);
	//	Log.d(TAG,"The recieved incharge username is  " + username);
		return params;
	}
	
	/**
	 * Function get Login status
	 * */
	public boolean isUserLoggedIn(Context context){
		SqliteDBhandler db = new SqliteDBhandler(context);
		int count = db.getRowCount();
		if(count > 0){
			// user logged in
			return true;
		}
		return false;
	}
	
	/**
	 * Function to logout user
	 * Reset Database
	 * */
	public boolean logoutUser(Context context,String tablename){
		SqliteDBhandler  db = new SqliteDBhandler(context);
		db.resetTables(tablename);
		return true;
	}
	
	
	

}
