package com.telosoft.mtokamanager.fragments;

import com.telosoft.mtokamanager.R;
import com.telosoft.mtokamanager.fragments.AddGroupFragment.onSubmitListener;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class AssetManagerFragment extends Fragment implements OnClickListener {
	View rootView;
	Button addAsset;
	int buttonStatus;
	onAddAssetButtonclick listener;
	public interface onAddAssetButtonclick{
		
		public void setOnAddAssetbuttonListener(int id);
	}
	
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		
			 try{
				 listener= (onAddAssetButtonclick)activity;
			 }catch(ClassCastException e){
				 throw new ClassCastException(activity.toString()
		                    + " must implement AuthenticationDialogListener"); 
				 
			 }
			 
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		rootView= inflater.inflate(R.layout.asset_dashboard,container,false);
		addAsset=(Button) rootView.findViewById(R.id.btnadd);
		addAsset.setOnClickListener(this);
		return rootView;
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnadd:
			buttonStatus=1;
			listener.setOnAddAssetbuttonListener(buttonStatus);
			break;

		default:
			break;
		}
		
	}
	

}
