package com.telosoft.mazingira.fragments;



import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.telosoft.mazingira.R;
import com.telosoft.mazingira.validation.Validation;



public class SignUP extends Fragment implements OnClickListener {
View view;
EditText incontact,inorgname,inpasswd,incpasswd;
String orgname,contact,passd,cpassd;
Button signup,gologin;
boolean stateclicked=false;
ProgressDialog dialog;
String message;
private Validation validate=new Validation();

public interface onSignupData{
	public void loadLogin(boolean state);
	public void passUserData(String orgname,String contact,String password);
	void feedBackMsg(String msg);
	
	
}

onSignupData listener;

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		 try{
			 listener= (onSignupData)activity;
		 }catch(ClassCastException e){
			 throw new ClassCastException(activity.toString()
	                    + " must implement AuthenticationDialogListener"); 
			 
		 }
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateView(inflater, container, savedInstanceState);
		view = inflater.inflate(R.layout.signup,container, false);
		inorgname=(EditText) view.findViewById(R.id.signup_orgname);
		incontact=(EditText) view.findViewById(R.id.signup_contacts);
	     inpasswd=(EditText) view.findViewById(R.id.signup_password);
		incpasswd=(EditText) view.findViewById(R.id.signup_cpassword);
		signup=(Button) view.findViewById(R.id.btn_signup);
		gologin=(Button) view.findViewById(R.id.btn_signup_login);
		signup.setOnClickListener(this);
		gologin.setOnClickListener(this);
		return view;
	
	
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.btn_signup:
				orgname=inorgname.getText().toString();
				contact=incontact.getText().toString();
				
				passd=inpasswd.getText().toString();
				
				cpassd=incpasswd.getText().toString();
				
				if(validate.checkNullity(orgname)==true){
					message="Organme required";
				}else if(validate.checkNullity(contact)==true){
					message="Contacts name required";
				
				}else if(validate.checkselection(passd)==true){
					message="Password required";
				}
				else if(validate.checkselection(cpassd)==true){
					message="Please confirm password";
				}else{
				if(cpassd.equals(passd)){
			
					
					listener.passUserData(orgname,contact,passd);
				}else{
					message="Password Not Matching";
					
				}
				}
					Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
			
				
			break;
		case R.id.btn_signup_login:
			    	listener.loadLogin(true);
			break;

		default:
			break;
		}
	}

	
	
}
