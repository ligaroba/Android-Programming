package com.telosoftapps.mtokamanager;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.AsyncTask.Status;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.telosoftapps.mtokamanager.R;
import com.telosoftapps.mtokamanager.customs.Constants;
import com.telosoftapps.mtokamanager.customs.IndividualVehicleList;
import com.telosoftapps.mtokamanager.fragments.SignIN;
import com.telosoftapps.mtokamanager.fragments.SignUP;
import com.telosoftapps.mtokamanager.fragments.SignIN.onLoginData;
import com.telosoftapps.mtokamanager.fragments.SignUP.onSignupData;
import com.telosoftapps.mtokamanager.functions.Functions;
import com.telosoftapps.mtokamanager.network.BackGroundService;
import com.telosoftapps.mtokamanager.network.SqliteDBhandler;
import com.telosoftapps.mtokamanager.threads.DoInBackground;

public class Login extends FragmentActivity implements onLoginData,onSignupData{
	FragmentManager fragment=getSupportFragmentManager();
	ProgressDialog dialog;
	 public static final String PREFS_NAME = "loginPrefs";
	 public static final String IS_LOGGED_IN = "isLoggedIn";
	 private static final String KEY_ID="ID";
	 List<IndividualVehicleList> telno =new ArrayList<IndividualVehicleList>();
	 private String tel;
	 SqliteDBhandler db =new SqliteDBhandler(this);
		// Shared Preferences
		SharedPreferences pref;
		// Editor for Shared preferences
		Editor editor;
private static AsyncTask<List<NameValuePair>, Void, String> mTask = null;	
@Override
protected void onStart() {
	// TODO Auto-generated method stub
	super.onStart();
	
}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
	  	pref = getSharedPreferences(Constants.PREFS_NAME,Constants.PRIVATE_MODE);
		String isloggedin=pref.getString(Constants.IS_LOGGED_IN, Constants.LOGGED_OUT);
		if(isloggedin.equals(Constants.LOGGED_OUT)){
			SignIN login =new SignIN();
			fragment.beginTransaction().add(R.id.login_frame, login).commit();
			
			dialog=null;
		}else{
			telno=db.getID();
			tel=telno.get(0).getTelno();
			 SharedPreferences settings =getSharedPreferences(Constants.PREFS_USERID, 0);
			   SharedPreferences.Editor editor = settings.edit();
			    editor.putString(Constants.KEY_TEL, tel);
			    editor.commit();
		 Intent d=new Intent(this,MtokaDashboard.class);
			// Closing all the Activities
			d.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			// Add new Flag to start new Activity
			d.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		 startActivity(d);
		 this.finish();
		 Intent service=new Intent(this,BackGroundService.class);
		 startService(service);
		 }
		
		
		
		
	}

	@Override
	public void loadLogin(boolean state) {
		// TODO Auto-generated method stub
	if(state==true){
		SignIN showlogin =new SignIN();
			fragment.beginTransaction().replace(R.id.login_frame, showlogin).addToBackStack(null).commit();
	
		}
	}

	@Override
	public void passUserData(String fname, String lname, String email,
			String phone,String username, String password) {
		// TODO Auto-generated method stub
		Status statusCo=new DoInBackground(this,"Creating account Please wait.... ",true)
		.execute(new Functions().
		registerUser(phone, fname, lname, username, email, password)).getStatus();
		
		if(statusCo.FINISHED!=null){
			Status status= new DoInBackground(this,"Creating account Please wait....",false).execute(new Functions().getAllItems("users","1")).getStatus();
			
		}
	}

	@Override
	public void loadsignup(boolean state) {
		// TODO Auto-generated method stub
		if(state==true){	
		SignUP showsignUp =new SignUP();		
		fragment.beginTransaction().replace(R.id.login_frame, showsignUp).addToBackStack(null).commit();
		}
	}

	@Override
	public void passLoginData(String phone, String password) {
		// TODO Auto-generated method stub
		mTask=new DoInBackground(this,"Login in....",false).execute(new Functions().loginUser(phone, password));
		
		
	}

	@Override
	public void feedBackMsg(String msg) {
		// TODO Auto-generated method stub
		
	}
	@Override
    protected void onStop() {

        super.onStop();

        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }

    }

	

}
