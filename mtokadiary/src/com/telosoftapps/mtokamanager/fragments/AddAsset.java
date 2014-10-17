package com.telosoftapps.mtokamanager.fragments;

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

import com.telosoftapps.mtokamanager.R;
import com.telosoftapps.mtokamanager.customs.ListSClass;
import com.telosoftapps.mtokamanager.network.SqliteDBhandler;
import com.telosoftapps.mtokamanager.validation.Validation;

public class AddAsset extends Fragment implements OnClickListener {
	View view;
	EditText inassetid,inmodel,incapacity;
	Button cancel,addaset;
	Spinner incategory,inemp,ingroup,intypuse;
private ArrayList<ListSClass> categoriesList;
private static String TAG="ADD ASSET";
private static final String KEY_GROUP_NAME="group_name";
private static final String KEY_USERNAME="username";
private static final String TABLE_GROUPTABLE="group_table";
private static final String TABLE_EMP="employee";
private SqliteDBhandler db;
 String categoryName=null;
 String empName=null;
 String assistantName=null;
 String groupName=null;
 String assetuse=null;
public  String message="";
 ArrayList<String> listlabels = new ArrayList<String>();
 ArrayList<String> emplabels = new ArrayList<String>();
 String selected;
 Validation validate=new Validation();

public interface onAddAssetclick{
	
	public void setOnAddAssetclick(String assetid,String capacity, String model,String typeuse,String category,String groupname,String incharge);
}
onAddAssetclick listener;	
public void onAttach(Activity activity) {
	// TODO Auto-generated method stub
	super.onAttach(activity);
	
		 try{
			 listener= (onAddAssetclick)activity;
		 }catch(ClassCastException e){
			 throw new ClassCastException(activity.toString()
	                    + " must implement AuthenticationDialogListener"); 
			 
		 }
		 
}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view=inflater.inflate(R.layout.add_asset,container,false);
		inassetid=(EditText) view.findViewById(R.id.edtassetId);
		inmodel=(EditText) view.findViewById(R.id.edmodel);
		incapacity=(EditText) view.findViewById(R.id.edcapacity);
		addaset=(Button) view.findViewById(R.id.btnaddasset);
		
		addaset.setOnClickListener(this);
		intypuse=(Spinner) view.findViewById(R.id.spintypeasset);
		ingroup=(Spinner) view.findViewById(R.id.spingroupname);
		incategory= (Spinner) view.findViewById(R.id.spincategory);
		inemp= (Spinner) view.findViewById(R.id.spinemp);
		categoriesList = new ArrayList<ListSClass>();
		
		// settin up listeners 
		intypuse.setOnItemSelectedListener(new MyCustomClickListener());
		ingroup.setOnItemSelectedListener(new MyCustomClickListener());
		incategory.setOnItemSelectedListener(new MyCustomClickListener());
		inemp.setOnItemSelectedListener(new MyCustomClickListener());
		// category Spinner 
		ArrayAdapter<CharSequence> catadapter =ArrayAdapter.createFromResource(getActivity(), R.array.vehicle_category,android.R.layout.simple_spinner_item);
		catadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		incategory.setAdapter(catadapter);
		
		// employees spinner 	
		db=new SqliteDBhandler(getActivity());
		emplabels = db.getAllLabels(KEY_USERNAME,TABLE_EMP,KEY_USERNAME);
		ArrayAdapter<String> empadapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,emplabels);
		empadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		inemp.setAdapter(empadapter);
		
		// vehicle group spinner 
		db=new SqliteDBhandler(getActivity());
		listlabels = db.getAllLabels("group_name","group_table",KEY_GROUP_NAME);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,listlabels);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		ingroup.setAdapter(adapter);
		
		
		
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
			
			String id=inassetid.getText().toString();
			String cap=incapacity.getText().toString();
			String model=inmodel.getText().toString();
			if(validate.checkNullity(id)==true){
				message="Asset id is empty";
			}else if(validate.checkNullity(cap)==true){
				message="Capacity is empty";
			}else if(validate.checkNullity(model)==true){
				message="Model is empty";
			}else if(validate.checkselection(assetuse)==true){
				message="Please select asset Use";
			}else if(validate.checkselection(categoryName)==true){
				message="Please select asset category";
			}
			else{
			
			listener.setOnAddAssetclick(id,cap,model
					,assetuse, categoryName, groupName, empName);
			
				inassetid.setHint("KBB 455G");
				incapacity.setHint("Capacity");
				inmodel.setHint("model");
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
				String[] category = getResources().getStringArray(R.array.vehicle_category);
				
				if(category[position]!=null && category[position]!=""){
					categoryName=category[position];
				}
					
				break;
			case R.id.spinemp:
				String[] emp = emplabels.toArray(new String[0]);
				
			 	if(emp[position]!=null && emp[position]!=""){
		    	   		empName =emp[position];
		    	}
			 		
				break;
			case R.id.spintypeasset:
				String[] assettype = getResources().getStringArray(R.array.assettype_array);
				
				if(assettype[position]!=null && assettype[position]!=""){
						assetuse=assettype[position];
						
				}
				
				break;
			
			
		    case R.id.spingroupname:
		    	String[] group = listlabels.toArray(new String[0]);
		    	groupName=group[position];
		    	if(group[position]!=null && group[position]!=""){
		    	
		    		groupName=group[position];
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
