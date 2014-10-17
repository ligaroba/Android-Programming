package com.telosoft.mazingira.fragments;


import android.app.Activity;
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

public class SignIN extends Fragment implements OnClickListener{
	View view;
	EditText inphone,inpassd;
	String phone,passd;
	Button login,gosignup;
	boolean stateclicked=false;

	public interface onLoginData{
		public void loadsignup(boolean state);
		public void passLoginData(String phone,String password);
		public void feedBackMsg(int id);
		
	}

	onLoginData listener;

		@Override
		public void onAttach(Activity activity) {
			// TODO Auto-generated method stub
			super.onAttach(activity);
			 try{
				 listener= (onLoginData)activity;
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
			view = inflater.inflate(R.layout.signin,container, false);
			
			inphone=(EditText) view.findViewById(R.id.login_phone);
			inpassd=(EditText) view.findViewById(R.id.login_password);
			login=(Button) view.findViewById(R.id.btn_login_login);
			gosignup=(Button) view.findViewById(R.id.btn_login_signup);
			login.setOnClickListener(this);
			gosignup.setOnClickListener(this);
			return view;
		
		
		}
		

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			switch (v.getId()) {
			case R.id.btn_login_login:
					
					phone=inphone.getText().toString();
					passd=inpassd.getText().toString();
					if(phone.trim().length() > 0 && passd.trim().length() > 0){
						
						listener.passLoginData(phone, passd);
					}else{
						Toast.makeText(getActivity(),"Please Enter phone Number \n and password ", Toast.LENGTH_LONG).show();
					}
				
					
				break;
			case R.id.btn_login_signup:
				    	listener.loadsignup(true);
				break;

			default:
				break;
			}
		}

	
	
}
