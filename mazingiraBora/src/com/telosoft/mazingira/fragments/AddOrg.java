package com.telosoft.mazingira.fragments;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.telosoft.mazingira.R;
import com.telosoft.mazingira.customs.ListSClass;
import com.telosoft.mazingira.network.SqliteDBhandler;
import com.telosoft.mazingira.validation.Validation;



public class AddOrg extends Fragment implements OnClickListener {
	View view;
	EditText inogname,incontact,inmember;
	Button cancel,addorg;
	Spinner inorgspec,insubmodel;
private ArrayList<ListSClass> categoriesList;

private SqliteDBhandler db;
 String speciality=null;

 String subsription=null;
public  String message="";
 ArrayList<String> listlabels = new ArrayList<String>();
 ArrayList<String> emplabels = new ArrayList<String>();
 String selected;
 Validation validate=new Validation();

public interface onAddOrgclick{
	
	public void setOnAddOrgclick(String id, String name, String mem,
			String subsription, String speciality);

	void feedBackMsg(int id);

	
}
onAddOrgclick listener;	
public void onAttach(Activity activity) {
	// TODO Auto-generated method stub
	super.onAttach(activity);
	
		 try{
			 listener= (onAddOrgclick)activity;
		 }catch(ClassCastException e){
			 throw new ClassCastException(activity.toString()
	                    + " must implement AuthenticationDialogListener"); 
			 
		 }
		 
}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view=inflater.inflate(R.layout.add_org,container,false);
		inogname=(EditText) view.findViewById(R.id.edtassetId);
		incontact=(EditText) view.findViewById(R.id.edmodel);
		inmember=(EditText) view.findViewById(R.id.edcapacity);
		addorg=(Button) view.findViewById(R.id.btnaddasset);
		
		addorg.setOnClickListener(this);
		
		inorgspec= (Spinner) view.findViewById(R.id.spincategory);
		insubmodel= (Spinner) view.findViewById(R.id.spintypeasset);
		
		categoriesList = new ArrayList<ListSClass>();
		
		// settin up listeners 
		inorgspec.setOnItemSelectedListener(new MyCustomClickListener());
		insubmodel.setOnItemSelectedListener(new MyCustomClickListener());
		
		// category Spinner 
		ArrayAdapter<CharSequence> catadapter =ArrayAdapter.createFromResource(getActivity(), R.array.subscription,android.R.layout.simple_spinner_item);
		catadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		insubmodel.setAdapter(catadapter);
		
		ArrayAdapter<CharSequence> caadapter =ArrayAdapter.createFromResource(getActivity(), R.array.speciality,android.R.layout.simple_spinner_item);
		caadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		inorgspec.setAdapter(catadapter);
		
	
		
		
		
		return view;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		if(savedInstanceState!=null){
			
		}
	
	}
	
	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnaddasset:
			
			String id=incontact.getText().toString();
			String name=inogname.getText().toString();
			String mem=inmember.getText().toString();
			if(validate.checkNullity(id)==true){
				message="Contact is empty";
			}else if(validate.checkNullity(name)==true){
				message="Name is empty";
			}else if(validate.checkNullity(mem)==true){
				message="members is empty";
			}else if(validate.checkselection(subsription)==true){
				message="Please select Subscription";
			}else if(validate.checkselection(speciality)==true){
				message="Please select speciality";
			}
			else{
		
				
		
			
			listener.setOnAddOrgclick(id,name,mem,
					 subsription, speciality);
		
			
					
			}
			Toast.makeText(getActivity(),message, Toast.LENGTH_LONG).show();
				
			break;

		default:
			break;
		}
	}

	public class MyCustomClickListener implements OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
	// which spinner triggered the click		
	switch (parent.getId()) {
			
			case R.id.spincategory:
				String[] category = getResources().getStringArray(R.array.speciality);
				
				if(category[position]!=null && category[position]!=""){
					speciality=category[position];
				}
					
				break;
		
			 		
			
			case R.id.spintypeasset:
				String[] assettype = getResources().getStringArray(R.array.subscription);
				
				if(assettype[position]!=null && assettype[position]!=""){
						subsription=assettype[position];
						
				}
				
				break;
			
			
		 
				
			
			default:
				break;
			}

		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
				
		}



	}

}
