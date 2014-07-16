package com.telosoftapps.mtokamanager.threads;




import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.telosoftapps.mtokamanager.Login;
import com.telosoftapps.mtokamanager.MtokaDashboard;
import com.telosoftapps.mtokamanager.customs.Constants;
import com.telosoftapps.mtokamanager.customs.ListSClass;
import com.telosoftapps.mtokamanager.customs.SessionManager;
import com.telosoftapps.mtokamanager.functions.Functions;
import com.telosoftapps.mtokamanager.interfaces.FetchDataListener;
import com.telosoftapps.mtokamanager.network.BackGroundService;
import com.telosoftapps.mtokamanager.network.JSONHandler;
import com.telosoftapps.mtokamanager.network.SqliteDBhandler;


public class DoInBackground extends AsyncTask<List<NameValuePair>, Void,String> {
	
	
	// table columns 
		private static final String KEY_ID="ID";
		private static final String KEY_USERNAME="username";
		private static final String KEY_EMAIL="email";
		private static final String KEY_ACTVE="active";
		private static final String KEY_CREATED_AT="created_at";
		private static final String  TABLE_PPERFOMANCE="perfomance";


		private static final String TAG = "Doinbackground";
		private static  String KEY_SUCCESS="success";
		private static  String KEY_ERROR="error";
		private String progressMessage="";
		private int VALUE; 
		String response="";
		String message="";
		String loginuser="";
		Context context;
		int msgResponse;
		ProgressDialog progress;
		JSONObject json;
		String errorResponse;
		boolean loginVerified =false;
		boolean dashboard =false;
		boolean show=false;
		private ArrayList<ListSClass> categoriesList=new ArrayList<ListSClass>();
		
	public DoInBackground(Context context,String message,boolean show) {
			this.context=context;
			progressMessage=message;
			this.show=show;
		}
	
public void progressDialog(String message){
	progress= new ProgressDialog(context);
	progress.setMessage(message);
	progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	progress.setCancelable(true);
	progress.setIndeterminate(true);
	
	
}
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		
		if(show=true){
			progressDialog(progressMessage);
			progress.show();
		}else{
			progress.hide();
		}
	}
	@Override
	protected String doInBackground(List<NameValuePair>... params){
		// TODO Auto-generated method stub
	
		
			try {
				json = new JSONHandler().JsonConnection(params[0]);
					//Log.d(TAG, "Doinbackgroud json thread retruned    " + json);
					
		if(json.getString(KEY_SUCCESS)!=null){
				response = json.getString(KEY_SUCCESS);
				//Log.d(TAG, "Doinginbackgroud succes response" +  response ); 
				
						if(Integer.parseInt(response)==1){
								// user successfully registerd
								SqliteDBhandler db = new SqliteDBhandler(context);
								JSONArray json_user = json.getJSONArray("data");
								for (int i=0;i<json_user.length();i++){
								JSONObject json_userid = json_user.getJSONObject(i);
								new Functions().logoutUser(context,"login");
								db.adduser(json_userid.getString("telno"), json_userid.getString(KEY_USERNAME).toLowerCase(),
										json_userid.getString(KEY_EMAIL).toLowerCase(),json_userid.getInt(KEY_ACTVE),
										json_userid.getString(KEY_CREATED_AT));
								}
								loginVerified=false;
								return message= "Account Success";
								
								
							}if(Integer.parseInt(response)==2){
								SqliteDBhandler db = new SqliteDBhandler(context);
								new Functions().logoutUser(context,"login");
								JSONObject json_userid = json.getJSONObject("user");
								loginuser=json_userid.getString("userid");
								for (int i=0;i<1;i++){
										db.adduser(json_userid.getString("userid"), json_userid.getString(KEY_USERNAME).toLowerCase(),
										json_userid.getString(KEY_EMAIL).toLowerCase(),json_userid.getInt(KEY_ACTVE),
										json_userid.getString(KEY_CREATED_AT));
								}
								
								//Log.d(TAG, "value of login id " + loginuser);
								loginVerified=true;
								
														
							}
							if(Integer.parseInt(response)==3){
								
								Constants.RESPONSE=3;
								//message="Asset inserted successfully";
								
								
							}
							if(Integer.parseInt(response)==4){
								Constants.RESPONSE=4;
								//message="Group added successfully";
								
								
							}
							if(Integer.parseInt(response)==8){
								Constants.RESPONSE=8;
								//message="Incharge added successfully";
								
								
							}
							if(Integer.parseInt(response)==9){
								Constants.RESPONSE=9;
								//message="Report Made successfully";
								
								
							}
				
				
			}else if(json.getString(KEY_ERROR)!=null){
				 errorResponse = json.getString(KEY_ERROR);
					//Log.d(TAG, "Doingbackground error response" +   errorResponse ); 
							errorResponse = json.getString(KEY_ERROR);
							if(Integer.parseInt(response)==1){
								Constants.RESPONSE=-1;
														
								
							}
								
								if(Integer.parseInt(errorResponse)==2){
									//do somthing
									Constants.RESPONSE=-2;
								
								}
								if(Integer.parseInt(errorResponse)==3){
									//do somthing
									Constants.RESPONSE=-3;
									
								}
								if(Integer.parseInt(errorResponse)==4){
									//do somthing
									Constants.RESPONSE=-4;
									
								}
								if(Integer.parseInt(errorResponse)==5){
									//do somthing
									Constants.RESPONSE=-5;
									
								}
								if(Integer.parseInt(errorResponse)==6){
									//do somthing
									Constants.RESPONSE=-6;
									
								}
								if(Integer.parseInt(errorResponse)==7){
									//do somthing
									Constants.RESPONSE=-7;
									
									
								}if(Integer.parseInt(errorResponse)==8){
									//do somthing
									Constants.RESPONSE=-8;
									
									
								}if(Integer.parseInt(errorResponse)==9){
									//do somthing
									Constants.RESPONSE=-9;
									
								}
					   
								if(Integer.parseInt(errorResponse)==10){
									//do somthing
									Constants.RESPONSE=-10;
									
								}
					   
					
		}
					
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
			
		return null;
	}
	protected void onPostExecute(String result) {
		  progress.dismiss();

	 if(loginVerified==true){
			  
			
			
			   message="Login Successful"; 
			   SharedPreferences settings = context.getSharedPreferences(Constants.PREFS_NAME, 0);
			   SharedPreferences.Editor editor = settings.edit();
			    editor.putString(Constants.IS_LOGGED_IN, Constants.LOGIN_IN);
			    editor.putString(KEY_ID, loginuser);
			    editor.commit();
			  //  Log.d(TAG, "user login info" + loginuser);
			    
				Bundle login =new Bundle();
				   login.putString(Constants.KEY_TEL, loginuser);
				//  Log.d(TAG, "value of bundled login id " + loginuser);
				   Intent dashboard=new Intent(context,MtokaDashboard.class);
				  dashboard.putExtras(login);
				  context.startActivity(dashboard);
			  
			  
			  
		  }if(loginVerified==false)
			  //if(result.equals("AccountSuccess"))
			  {
					  message="Account Created successfully \n Please Login";
					  Intent login=new Intent(context,Login.class);
					  context.startActivity(login);
			 
			  
		  }
		  if(Constants.RESPONSE==3)
			  //if(result.equals("AccountSuccess"))
			  {
					  message="Asset Created succefully";
					  Intent dashboard=new Intent(context,MtokaDashboard.class);
					  context.startActivity(dashboard);
					  
			 			  
		  }
		  if(Constants.RESPONSE==4)
			  //if(result.equals("AccountSuccess"))
			  {
			  			message="Group added successfully";
					  Intent dashboard=new Intent(context,MtokaDashboard.class);
					  context.startActivity(dashboard);
					  
			 			  
		  }
		  if(Constants.RESPONSE==8)
			  //if(result.equals("AccountSuccess"))
			  {
					  message="Incharge added successfully";
					  Intent dashboard=new Intent(context,MtokaDashboard.class);
					  context.startActivity(dashboard);
					  
			 			  
		  }
		  if(Constants.RESPONSE==9)
			  //if(result.equals("AccountSuccess"))
			  {
			           message="Report Made successfully";
					  Intent dashboard=new Intent(context,MtokaDashboard.class);
					  context.startActivity(dashboard);
					  
			 			  
		  }
/*
 * Error Messages Here 
 *  */ 
	
		  if(Constants.RESPONSE==-1)
			  //if(result.equals("AccountSuccess"))
			  {
			           message="Email Exists";
							  
			 			  
		  }
		  if(Constants.RESPONSE==-2)
			  //if(result.equals("AccountSuccess"))
			  {
			  message=  "number Exists";
							  
			 			  
		  }
		  if(Constants.RESPONSE==-3)
			  //if(result.equals("AccountSuccess"))
			  {
			 message=  "username Exists";
							  
			 			  
		  }
		  if(Constants.RESPONSE==-4)
			  //if(result.equals("AccountSuccess"))
			  {
		message=  "wrong Credentials";
							  
			 			  
		  }
		  if(Constants.RESPONSE==-1)
			  //if(result.equals("AccountSuccess"))
			  {
			           message="Email Exists";
							  
			 			  
		  }
		  if(Constants.RESPONSE==-5)
			  //if(result.equals("AccountSuccess"))
			  {
			  message= "group Exists";
							  
			 			  
		  }
		  if(Constants.RESPONSE==-6)
			  //if(result.equals("AccountSuccess"))
			  {
			 message=  "incharge Exists";
							  
			 			  
		  }
		  if(Constants.RESPONSE==-7)
			  //if(result.equals("AccountSuccess"))
			  {
			message= "Asset Exists";			 			  
		  }
		  if(Constants.RESPONSE==-8)
			  //if(result.equals("AccountSuccess"))
			  {
			  message= "inchargeAssigned";
							  
			 			  
		  }
		  if(Constants.RESPONSE==-9)
			  //if(result.equals("AccountSuccess"))
			  {
			 message=  "incharge Assigned";						  
			 			  
		  }
		  if(Constants.RESPONSE==-10)
			  //if(result.equals("AccountSuccess"))
			  {
		     message= "deletion Failed" ;
							  
			 			  
		  }
			 
 
		
	

		
			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
			
			
	}


}
