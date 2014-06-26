package com.telosoft.mtokamanager.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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

import com.telosoft.mtokamanager.R;
import com.telosoft.mtokamanager.customs.SpinnerClass;
import com.telosoft.mtokamanager.network.SqliteDBhandler;

public class AddAsset extends Fragment implements OnClickListener, OnItemSelectedListener {
	View view;
	EditText inassetid,inmodel;
	Button cancel,addaset;
	Spinner incategory,indriver,ingroup,intout;
private ArrayList<SpinnerClass> categoriesList;
onAddAssetclick listener;
private static String TAG="ADD ASSET";
private SqliteDBhandler db;
 String categoryName="";
String driverName="";
 String assistantName="";
String groupName="";
 String assetName="";
 ArrayList<String> listlabels = new ArrayList<String>();
 String selected;

public interface onAddAssetclick{
	
	public void setOnAddAssetclick();
}
	



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view=inflater.inflate(R.layout.add_asset,container,false);
		inassetid=(EditText) view.findViewById(R.id.edtassetId);
		inmodel=(EditText) view.findViewById(R.id.edmodel);
		addaset=(Button) view.findViewById(R.id.btnaddasset);
		
		addaset.setOnClickListener(this);
		
		ingroup=(Spinner) view.findViewById(R.id.spingroupname);
		incategory= (Spinner) view.findViewById(R.id.spincategory);
		intout= (Spinner) view.findViewById(R.id.spintout);
		indriver= (Spinner) view.findViewById(R.id.spindriver);
		categoriesList = new ArrayList<SpinnerClass>();
		
		ingroup.setOnItemSelectedListener(this);
		incategory.setOnItemSelectedListener(this);
		intout.setOnItemSelectedListener(this);
		indriver.setOnItemSelectedListener(this);
		// category Spinner 
		ArrayAdapter<CharSequence> catadapter =ArrayAdapter.createFromResource(getActivity(), R.array.vehicle_category,android.R.layout.simple_spinner_item);
		catadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		incategory.setAdapter(catadapter);
		// Driver Spinner 
		/*ArrayAdapter<String> dradapter =new ArrayAdapter<String>(getActivity(), R.array.drivers,android.R.layout.simple_spinner_item);
		dradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		indriver.setAdapter(dradapter);
		// assiatant Spinner 
		ArrayAdapter<String> assadapter =new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, R.array.Assistant);
		assadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		intout.setAdapter(assadapter);*/
		// group spinner 
		db=new SqliteDBhandler(getActivity());
		listlabels = db.getAllLabels("group_name","group_table");
	
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
	
	
	public void populateSpinner(ArrayList<SpinnerClass> datalist){
		
		List<String> listLabels;listLabels =new ArrayList<String>();
	
		for(int i=0;i<datalist.size();i++){
			listLabels.add(datalist.get(i).getName());
			 Log.d(TAG, "AddAsset array    " + listLabels.toString());
		}
		//creating spinner adapter 
		 ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getActivity(),
	                android.R.layout.simple_spinner_item, listLabels);
		// creating the dropdown 
		spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		ingroup.setAdapter(spinnerAdapter);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnaddasset:
			
			break;

		default:
			break;
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
	switch (view.getId()) {
		
		case R.id.spincategory:
			String[] category = getResources().getStringArray(R.array.vehicle_category);
			categoryName =category[position];
			Log.d(TAG, "this item is clicked " + categoryName);
			//categoryName=parent.getItemAtPosition(position).toString();
			Toast.makeText(getActivity(),"you have selected " + selected , Toast.LENGTH_LONG).show();
			break;
		case R.id.spindriver:
			String[] driver = getResources().getStringArray(R.array.drivers);
			driverName =driver[position];
			Log.d(TAG, "this item is clicked " + driverName);
			//driverName=parent.getItemAtPosition(position).toString();
			Toast.makeText(getActivity(),"you have selected " + selected, Toast.LENGTH_LONG).show();	
			break;
		case R.id.spintypeasset:
			String[] assettype = getResources().getStringArray(R.array.assettype_array);
			assetName =assettype[position];
			Log.d(TAG, "this item is clicked " + assetName);
			//assetName=parent.getItemAtPosition(position).toString();
			Toast.makeText(getActivity(),"you have selected " + selected, Toast.LENGTH_LONG).show();
			break;
		case R.id.spintout:
			String[] assistant = getResources().getStringArray(R.array.Assistant);
			assistantName =assistant[position];
			Log.d(TAG, "this item is clicked " + assistantName);
			//assistantName=parent.getItemAtPosition(position).toString();
			Toast.makeText(getActivity(),"you have selected " + assistantName, Toast.LENGTH_LONG).show();
			break;
		
	    case R.id.spingroupname:
	    	String[] group = listlabels.toArray(new String[0]);
	    	groupName=group[position];
	    	Log.d(TAG, "this item is clicked " + groupName);
			Toast.makeText(getActivity(),"you have selected " + groupName, Toast.LENGTH_LONG).show();
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
