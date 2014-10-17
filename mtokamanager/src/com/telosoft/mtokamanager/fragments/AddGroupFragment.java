package com.telosoft.mtokamanager.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.telosoft.mtokamanager.R;

public class AddGroupFragment extends DialogFragment implements OnClickListener {
Button addGroup,cancel;
EditText groupname;
onSubmitListener listener;
View view;
ProgressDialog progress;
private static String TAG="Add Group tag";



public interface onSubmitListener{
	public void setOnSubmitListener(DialogFragment dialog,String name);
}


public AddGroupFragment() {

}
	
	@Override
public void onAttach(Activity activity) {
	// TODO Auto-generated method stub
	super.onAttach(activity);
	
		 try{
			 listener= (onSubmitListener)activity;
		 }catch(ClassCastException e){
			 throw new ClassCastException(activity.toString()
	                    + " must implement AuthenticationDialogListener"); 
			 
		 }
		 
}
	


	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.addgroup_fragment, container);
    	
		addGroup= (Button) view.findViewById(R.id.btnaddgroup);
		cancel = (Button) view.findViewById(R.id.btncancel);
		groupname=(EditText) view.findViewById(R.id.editaddgroup);
		
		addGroup.setOnClickListener(this);
		cancel.setOnClickListener(this);
       
		getDialog().setCanceledOnTouchOutside(true);
        getDialog().setTitle(R.string.add_group_fragment);
        // Show soft keyboard automatically
        groupname.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        return view;
    }



	


	@Override
public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.btnaddgroup:
			Log.d(TAG,"The entered group name is  " + groupname.getText().toString());
					listener.setOnSubmitListener(this,groupname.getText().toString());
					
			
			break;
		case R.id.btncancel:
					listener.setOnSubmitListener(this,null);
			
			break;

		default:
			break;
		}
		
		
	}

	

}
