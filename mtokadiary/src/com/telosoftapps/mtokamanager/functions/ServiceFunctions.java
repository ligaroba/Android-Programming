package com.telosoftapps.mtokamanager.functions;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.telosoftapps.mtokamanager.network.JSONHandler;
import com.telosoftapps.mtokamanager.network.SqliteDBhandler;

public class ServiceFunctions implements Parcelable {
	


	

	private static String POST_TAG="tag";
	private static String LOGIN_TAG="login_user";

	private static String GET_ALLITEMS_TAG="get_all_data";
	
	
	private static String GET_PERFOMANCE="get_perfomance";
	   private static String TAG="Functions tag";
	
	JSONHandler jsonParser=new JSONHandler();
	public ServiceFunctions(){
		
		
	}




	public List<NameValuePair> getAllItems(String tablename,String value){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(POST_TAG,GET_ALLITEMS_TAG));
		params.add(new BasicNameValuePair("tablename",tablename));
		  params.add(new BasicNameValuePair("value",value));
		Log.d(TAG,"The get all items tag name is  " + tablename);
		return params;
		
	}
	public List<NameValuePair> getPerfomance(String assetid,String value){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(POST_TAG,GET_PERFOMANCE));
		params.add(new BasicNameValuePair("assetid",assetid));
		  params.add(new BasicNameValuePair("value",value));
		Log.d(TAG,"The get all Performance tag name is  " + assetid);
		return params;
		
	}
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		
	}


	


	
	
	

}
