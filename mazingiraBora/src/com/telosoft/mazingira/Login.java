package com.telosoft.mazingira;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.telosoft.mazingira.R;
import com.telosoft.mazingira.customs.IndividualOrgList;
import com.telosoft.mazingira.fragments.SignIN;
import com.telosoft.mazingira.fragments.SignUP;
import com.telosoft.mazingira.fragments.SignIN.onLoginData;
import com.telosoft.mazingira.fragments.SignUP.onSignupData;
import com.telosoft.mazingira.functions.Functions;
import com.telosoft.mazingira.network.BackGroundService;
import com.telosoft.mazingira.network.SqliteDBhandler;
import com.telosoft.mazingira.threads.DoInBackground;

public class Login extends FragmentActivity implements onLoginData,onSignupData{
	FragmentManager fragment=getSupportFragmentManager();
	ProgressDialog dialog;
	
	 private static final String KEY_ID="ID";
	 List<IndividualOrgList> telno =new ArrayList<IndividualOrgList>();
	
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
		
	
			
		
		
		 Intent service=new Intent(this,BackGroundService.class);
		 startService(service);
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
	public void passUserData(String orgname, String contact, String password) {
		// TODO Auto-generated method stub
		Status statusCo=new DoInBackground(this,"Creating account Please wait.... ",true)
		.execute(new Functions().
		registerOrg(orgname, contact, null, null, null,null, password)).getStatus();
		
		if(statusCo.FINISHED!=null){
			Status status= new DoInBackground(this,"Creating account Please wait....",false).execute(new Functions().getAllItems("initiative","1")).getStatus();
			
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
	@Override
	public void feedBackMsg(int id) {
		// TODO Auto-generated method stub
		
	}

	

}
