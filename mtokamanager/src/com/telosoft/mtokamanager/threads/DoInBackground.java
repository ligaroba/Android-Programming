package com.telosoft.mtokamanager.threads;




import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.telosoft.mtokamanager.customs.SpinnerClass;
import com.telosoft.mtokamanager.fragments.AddAsset;
import com.telosoft.mtokamanager.functions.Functions;
import com.telosoft.mtokamanager.network.JSONHandler;
import com.telosoft.mtokamanager.network.SqliteDBhandler;


public class DoInBackground extends AsyncTask<List<NameValuePair>, Void, String> {
	
	
	// table columns 
		private static final String KEY_ID="ID";
		private static final String KEY_USERNAME="username";
		private static final String KEY_EMAIL="email";
		private static final String KEY_ACTVE="actiselect * from group_table;ve";
		private static final String KEY_CREATED_AT="created_at";

		private static final String TAG = "Mtoka Doinbackground";
		private static  String KEY_SUCCESS="success";
		private static  String KEY_ERROR="error";
		private String progressMessage="";
		private String ERROR_MSG="error_msg"; 
		String response="";
		private Context context;
		ProgressDialog progress;
		JSONObject json;
		boolean show =false;
		private ArrayList<SpinnerClass> categoriesList=new ArrayList<SpinnerClass>();

		
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
	progress.show();
	
}
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		if(show=true){
			progressDialog(progressMessage);
		}
	}
	@Override
	protected String doInBackground(List<NameValuePair>... params){
		// TODO Auto-generated method stub
	
		
			try {
				json = new JSONHandler().JsonConnection(params[0]);
					Log.d(TAG, "Doinbackgroud json thread retruned    " + json);
					
		if(json.getString(KEY_SUCCESS)!="0"){
				response = json.getString(KEY_SUCCESS);
				Log.d(TAG, "Doinginbackgroud succes response" +  response ); 
				
						if(Integer.parseInt(response)==1){
								// user successfully registerd
								SqliteDBhandler db = new SqliteDBhandler(context);
								JSONObject json_user = json.getJSONObject("user");
								JSONObject json_userid = json.getJSONObject("userid");
								new Functions().logoutUser(context,"login");
								db.adduser(json_userid.getString(KEY_ID), json_user.getString(KEY_USERNAME),
										json_user.getString(KEY_EMAIL),json_user.getInt(KEY_ACTVE),
										json_user.getString(KEY_CREATED_AT));
								
								
							}else if(Integer.parseInt(response)==2){
								// user successfully loggsed in
								
							}else if(Integer.parseInt(response)==3){
								// asset successfully registerd
								
							}else if(Integer.parseInt(response)==4){
								// group successfully registerd
								return "group successfully  created";
								
								
							}else if(Integer.parseInt(response)==5){
								// update group successfully 
								
							}else if(Integer.parseInt(response)==6){
								// 	 update asset successfully 
								
							
							}else if(Integer.parseInt(response)==7){
								// 	 update spinners
								SqliteDBhandler db =new SqliteDBhandler(context);
								 Log.d(TAG, "Doinginbackgroud array    " + response);
								 new Functions().logoutUser(context,"group_table");
								JSONArray json_id = json.getJSONArray("data");
								 for (int i = 0; i < json_id.length(); i++) {
				                        JSONObject catObj = json_id.getJSONObject(i);
//				                    	 update spinners
										 Log.d(TAG, "Doinginbackgroud array    " + catObj.toString());
										 db.addgroup(catObj.getInt("group_id"),catObj.getString("group_name"));
				                      /*  SpinnerClass cat = new SpinnerClass(catObj.getInt("group_id"),
				                                catObj.getString("group_name"));
				                        categoriesList.add(cat);*/
									
				                       
				                    }
								 return "succesfully db entry";
								//new AddAsset().populateSpinner(categoriesList);
								
							}
							else if(Integer.parseInt(response)==10){
								// deletion successfully 
								
							}
				}if(json.getString(KEY_ERROR)!="0"){
					
					Log.d(TAG, "Doingbackground error response" +  response ); 
								response = json.getString(KEY_ERROR);
								if(Integer.parseInt(response)==1){
									//do somthing
								}
								if(Integer.parseInt(response)==2){
									//do somthing
								}
								if(Integer.parseInt(response)==3){
									//do somthing
								}
								if(Integer.parseInt(response)==4){
									//do somthing
								}
								if(Integer.parseInt(response)==5){
									//do somthing
									return "Group name Already Exists";
								}
								if(Integer.parseInt(response)==6){
									//do somthing
								}
					   
					
		}else {
					return "Please Enter a valid input";
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
		if (progress.isShowing())
			progress.dismiss();
		
			Toast.makeText(context, result, Toast.LENGTH_LONG).show();
			
			
	}


}
