package com.telosoftapps.mtokamanager.fragments;

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

import com.telosoftapps.mtokamanager.R;

public class NewInchargeFragment extends DialogFragment implements OnClickListener {
Button addincharge,cancel;
EditText infullnames,inusername;
onSubmitListenerIncharge listener;
View view;
ProgressDialog progress;
private static String TAG=" NewInchargeFragment";



public interface onSubmitListenerIncharge{
	public void setOnSubmitListener(DialogFragment dialog,String name,String username);
}


public NewInchargeFragment() {

}
	
	@Override
public void onAttach(Activity activity) {
	// TODO Auto-generated method stub
	super.onAttach(activity);
	
		 try{
			 listener= (onSubmitListenerIncharge)activity;
		 }catch(ClassCastException e){
			 throw new ClassCastException(activity.toString()
	                    + " must implement AuthenticationDialogListener"); 
			 
		 }
		 
}
	


	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.newincharge_fragment, container);
    	
		addincharge= (Button) view.findViewById(R.id.btnincharge);
		cancel = (Button) view.findViewById(R.id.btnincancel);
		infullnames=(EditText) view.findViewById(R.id.editinchargenames);
		inusername=(EditText) view.findViewById(R.id.editinchargeusername);
		
		addincharge.setOnClickListener(this);
		cancel.setOnClickListener(this);
       
		getDialog().setCanceledOnTouchOutside(true);
        getDialog().setTitle(R.string.add_incharge_fragment);
        // Show soft keyboard automatically
        infullnames.requestFocus();
        inusername.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        return view;
    }



	


	@Override
public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.btnincharge:
			//Log.d(TAG,"The entered group name is  " + infullnames.getText().toString());
			//Log.d(TAG,"The entered group name is  " + inusername.getText().toString());
					listener.setOnSubmitListener(this,infullnames.getText().toString(),inusername.getText().toString());
					
			infullnames.setText(" ");
			inusername.setText(" ");
			break;
		case R.id.btnincancel:
					listener.setOnSubmitListener(this,null,null);
			
			break;

		default:
			break;
		}
		
		
	}

	

}
