package com.telosoftapps.mtokamanager.fragments;



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

import com.telosoftapps.mtokamanager.R;


public class SignUP extends Fragment implements OnClickListener {
View view;
EditText infname,inlname,inemail,inphone,inpassd,incpassd,inusername;
String fname,lname,email,phone,passd,cpassd,username;
Button signup,gologin;
boolean stateclicked=false;
ProgressDialog dialog;

public interface onSignupData{
	public void loadLogin(boolean state);
	public void passUserData(String fname,String lname,String email,String phone,String username,String password);
	public void feedBackMsg(String msg);
	
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
		infname=(EditText) view.findViewById(R.id.signup_fname);
		inlname=(EditText) view.findViewById(R.id.signup_lname);
		inemail=(EditText) view.findViewById(R.id.signup_email);
		inphone=(EditText) view.findViewById(R.id.signup_pone);
		inusername=(EditText) view.findViewById(R.id.signup_username);
		inpassd=(EditText) view.findViewById(R.id.signup_password);
		incpassd=(EditText) view.findViewById(R.id.signup_cpassword);
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
				fname=infname.getText().toString();
				lname=inlname.getText().toString();
				email=inemail.getText().toString();
				phone=inphone.getText().toString();
				passd=inpassd.getText().toString();
				username=inusername.getText().toString();
				cpassd=incpassd.getText().toString();
				
				if(cpassd.equals(passd)){
					listener.passUserData(fname, lname, email, phone,username, passd);
				}else{
					Toast.makeText(getActivity(), "Password Not Matching", Toast.LENGTH_LONG).show();
				}
			
				
			break;
		case R.id.btn_signup_login:
			    	listener.loadLogin(true);
			break;

		default:
			break;
		}
	}

	
	
}
