package com.telosoft.mazingira.functions;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.util.Log;

import com.telosoft.mazingira.network.JSONHandler;
import com.telosoft.mazingira.network.SqliteDBhandler;

public class Functions {
	


	
	private static String REGISTER_TAG="register_org";
	private static String POST_TAG="tag";
	private static String LOGIN_TAG="login_org";
	private static String INCHARGE_TAG="new_incharge";
	private static String DELETE_TAG="delete";
	private static String GET_ALL_ITEMS="get_all_data";

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
	public List<NameValuePair> registerOrg(String orgname,String contact,String location ,String orgspeciality,String members,String subscrimodel, String password){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(POST_TAG, REGISTER_TAG));
		params.add(new BasicNameValuePair("orgname",orgname));
		params.add(new BasicNameValuePair("contact",contact));
		params.add(new BasicNameValuePair("location", location));
		params.add(new BasicNameValuePair("orgspeciality", orgspeciality));
		params.add(new BasicNameValuePair("members", members));
		params.add(new BasicNameValuePair("subscrimodel", subscrimodel));
		params.add(new BasicNameValuePair("password", password));
		
		return params;
	
	}
	
	
	
	public List<NameValuePair> getAllItems(String tablename,String value){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(POST_TAG, GET_ALL_ITEMS));
		params.add(new BasicNameValuePair("tablename",tablename));
		  params.add(new BasicNameValuePair("value",value));
		//Log.d(TAG,"The get all items tag name is  " + tablename);
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
