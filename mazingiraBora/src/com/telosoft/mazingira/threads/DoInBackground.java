package com.telosoft.mazingira.threads;




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

import com.telosoft.mazingira.Login;
import com.telosoft.mazingira.MazingaraDashboard;
import com.telosoft.mazingira.customs.Constants;
import com.telosoft.mazingira.customs.ListSClass;
import com.telosoft.mazingira.functions.Functions;
import com.telosoft.mazingira.network.BackGroundService;
import com.telosoft.mazingira.network.JSONHandler;
import com.telosoft.mazingira.network.SqliteDBhandler;


public class DoInBackground extends AsyncTask<List<NameValuePair>, Void,String> {
	
	
	// table columns 



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
					
		if(Integer.parseInt(json.getString(KEY_SUCCESS))>0){
				response = json.getString(KEY_SUCCESS);
				//Log.d(TAG, "Doinginbackgroud succes response" +  response ); 
			
						if(Integer.parseInt(response)==1){
								// user successfully registerd
								SqliteDBhandler db = new SqliteDBhandler(context);
								JSONArray json_user = json.getJSONArray("data");
								for (int i=0;i<json_user.length();i++){
								JSONObject json_userid = json_user.getJSONObject(i);
								new Functions().logoutUser(context,"login");
								db.adduser(json_userid.getString("contact").toLowerCase(),
										json_userid.getString("orgname").toLowerCase(), 
										json_userid.getString("orgspeciality").toLowerCase(),
										json_userid.getString("members").toLowerCase(),
										json_userid.getString("subcripmodel").toLowerCase(),
										json_userid.getString("location").toLowerCase());
								
								
								}
								Constants.RESPONSE=1;
							
								
								
							}else if(Integer.parseInt(response)==2){
								
								//Log.d(TAG, "value of login id " + loginuser);
								Constants.RESPONSE=2;
														
							}else
							if(Integer.parseInt(response)==3){
								
								Constants.RESPONSE=3;
								//message="Asset inserted successfully";
								
								
							}else
							if(Integer.parseInt(response)==4){
								Constants.RESPONSE=4;
								//message="Group added successfully";
								
								
							}else
							if(Integer.parseInt(response)==8){
								Constants.RESPONSE=8;
								//message="Incharge added successfully";
								
								
							}else
							if(Integer.parseInt(response)==9){
								Constants.RESPONSE=9;
								//message="Report Made successfully";
								
								
							}else
							if(Integer.parseInt(response)==10){
								Constants.RESPONSE=10;
								//message="Report Made successfully";
								
								
							}else
							if(Integer.parseInt(response)==101){
								Constants.RESPONSE=101;
								//message="Report Made successfully";
								
								
							}
				
				
			}else if(Integer.parseInt(json.getString(KEY_ERROR))>0){
				 errorResponse = json.getString(KEY_ERROR);
					//Log.d(TAG, "Doingbackground error response" +   errorResponse ); 
							errorResponse = json.getString(KEY_ERROR);
							if(Integer.parseInt(response)==1){
								Constants.RESPONSE=-1;
														
								
							}else
								
								if(Integer.parseInt(errorResponse)==2){
									//do somthing
									Constants.RESPONSE=-2;
								
								}else
								if(Integer.parseInt(errorResponse)==3){
									//do somthing
									Constants.RESPONSE=-3;
									
								}else
								if(Integer.parseInt(errorResponse)==4){
									//do somthing
									Constants.RESPONSE=-4;
									
								}else
								if(Integer.parseInt(errorResponse)==5){
									//do somthing
									Constants.RESPONSE=-5;
									
								}else
								if(Integer.parseInt(errorResponse)==6){
									//do somthing
									Constants.RESPONSE=-6;
									
								}else
								if(Integer.parseInt(errorResponse)==7){
									//do somthing
									Constants.RESPONSE=-7;
									
									
								}else if(Integer.parseInt(errorResponse)==8){
									//do somthing
									Constants.RESPONSE=-8;
									
									
								}else if(Integer.parseInt(errorResponse)==9){
									//do somthing
									Constants.RESPONSE=-9;
									
								}else
					   
								if(Integer.parseInt(errorResponse)==10){
									//do somthing
									Constants.RESPONSE=-10;
									
								}else
								if(Integer.parseInt(errorResponse)==101){
									//do somthing
									Constants.RESPONSE=-101;
									
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

	 if(Constants.RESPONSE==2){
			  
			
			
			   message="Login Successful"; 
			  
			   
			   SharedPreferences settings = context.getSharedPreferences(Constants.PREFS_NAME, 0);
			   SharedPreferences.Editor editor = settings.edit();
			    editor.putString(Constants.IS_LOGGED_IN, Constants.LOGIN_IN);
			    editor.putString("id", loginuser);
			    editor.commit();
			  //  Log.d(TAG, "user login info" + loginuser);
			    
				Bundle login =new Bundle();
				   login.putString(Constants.KEY_TEL, loginuser);
				  
				//  Log.d(TAG, "value of bundled login id " + loginuser);
				   Intent dashboard=new Intent(context,MazingaraDashboard.class);
				   login.putString(Constants.KEY_TEL,loginuser);
				   login.putString(Constants.IS_LOGGED_IN,Constants.LOGIN_IN);
				   dashboard .putExtras(login);
				  dashboard.putExtras(login);
				  context.startActivity(dashboard);
			  
			  
			  
		  }else if(Constants.RESPONSE==1)
			  //if(result.equals("AccountSuccess"))
			  {
					  message="Account Created successfully \n Please Login";
					  Intent login=new Intent(context,Login.class);
					  context.startActivity(login);
			 
			  
		  }else
		  if(Constants.RESPONSE==3)
			  //if(result.equals("AccountSuccess"))
			  {
					  message="Initiative Created succefully";
					  Intent dashboard=new Intent(context,MazingaraDashboard.class);
					  context.startActivity(dashboard);
					  
			 			  
		  }else
		  
		  if(Constants.RESPONSE==8)
			  //if(result.equals("AccountSuccess"))
			  {
					  message="Incharge added successfully";
					  Intent dashboard=new Intent(context,MazingaraDashboard.class);
					  context.startActivity(dashboard);
					  
			 			  
		  }else
		  if(Constants.RESPONSE==9)
			  //if(result.equals("AccountSuccess"))
			  {
			           message="Report Made successfully";
					  Intent dashboard=new Intent(context,MazingaraDashboard.class);
					  context.startActivity(dashboard);
					  
			 			  
	}else
		  if(Constants.RESPONSE==10)
			  //if(result.equals("AccountSuccess"))
			  {
			           message="Update successfull";
					  Intent dashboard=new Intent(context,MazingaraDashboard.class);
					  context.startActivity(dashboard);
					  
			 			  
	}else
	 if(Constants.RESPONSE==101)
		  //if(result.equals("AccountSuccess"))
		  {
	    
		 message= "Deleted successfully" ;
		 Intent dashboard=new Intent(context,MazingaraDashboard.class);
		  context.startActivity(dashboard);
		 			  
	  }
/*
 * Error Messages Here 
 *  */ 
	
		  if(Constants.RESPONSE==-1)
			  //if(result.equals("AccountSuccess"))
			  {
			           message="contact Exists";
							  
			 			  
		  }else
		  if(Constants.RESPONSE==-2)
			  //if(result.equals("AccountSuccess"))
			  {
			  message=  "Initiative Exists";
							  
			 			  
		  }else
		  if(Constants.RESPONSE==-3)
			  //if(result.equals("AccountSuccess"))
			  {
			 message=  "username Exists";
							  
			 			  
		  }else
		  if(Constants.RESPONSE==-4)
			  //if(result.equals("AccountSuccess"))
			  {
		message=  "wrong Credentials";
							  
			 			  
		  }else
		  if(Constants.RESPONSE==-1)
			  //if(result.equals("AccountSuccess"))
			  {
			           message="Email Exists";
							  
			 			  
		  }else
		  if(Constants.RESPONSE==-5)
			  //if(result.equals("AccountSuccess"))
			  {
			  message= "group Exists";
							  
			 			  
		  }else
		 
		  if(Constants.RESPONSE==-7)
			  //if(result.equals("AccountSuccess"))
			  {
			message= "org Exists";			 			  
		  }else
		  
		  if(Constants.RESPONSE==-101)
			  //if(result.equals("AccountSuccess"))
			  {
		     message= "deletion Failed" ;
							  
			 			  
		  }
			 
 
		
	

		
			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
			
			
	}


}
