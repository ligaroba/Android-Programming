package co.ke.masterclass.mysecurity.fragments;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import co.ke.masterclass.mysecurity.R;



public class ViewPersons extends Fragment implements OnClickListener, OnItemSelectedListener {
View view;
private ListView listpersons;
private SimpleAdapter listp;
private List<HashMap<String,String>> personlist;
private int id;
private String crimename="";

//instantiating interfaces
private OnItemSelected listener;

//interfaces 
public interface OnItemSelected{
	public void OnselectedItem(int id,String name);
}








	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		 try{
			 listener= (OnItemSelected)activity;
		 }catch(ClassCastException e){
			 throw new ClassCastException(activity.toString()
	                    + " must implement AuthenticationDialogListener"); 
			 
		 }
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view =inflater.inflate(R.layout.viewmissingperson, container,false);
	
	
		listpersons=(ListView) view.findViewById(R.id.listViewpersons);
		//btnwanted=(Button) view.findViewById(R.id.btnwanted);
		personlist=new ArrayList<HashMap<String,String>>();
		HashMap<String, String> hm = new HashMap<String,String>();
	for(int i=0;i<10;i++){
		 hm.put("n",  "Someone  " + (i+1));
         hm.put("t", "Very Dangerous");
         
         personlist.add(hm);
		 }
		
         String[]  from = { "n","t"};
        
        
		int[] mapto=new int[]{R.id.txtvpersonsname,
		                       R.id.txtvwdescriptn
		                    };
		
		
		listp=new SimpleAdapter(getActivity().getBaseContext(), personlist,R.layout.custompersonslistitem, from, mapto);
		listpersons.setAdapter(listp);
		listpersons.setOnItemSelectedListener(this);
		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}



	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btntheft:
			id=1;
			listener.OnselectedItem(id,"Theft");
			
			break;
		case R.id.btnaccident:
			id=2;
			listener.OnselectedItem(id,"Accident");
			break;
		case R.id.btnassult:
			id=3;
			listener.OnselectedItem(id,"Assault");
			break;
		case R.id.btndistubance:
			id=4;
			listener.OnselectedItem(id,"Disturbance");
			break;
		case R.id.btndrugs:
			id=5;
			listener.OnselectedItem(id,"Drug Abuse");
			break;
		case R.id.btnharrasment:
			id=6;
			listener.OnselectedItem(id,"Harrasement");
			break;
		case R.id.btnmedical:
			id=7;
			listener.OnselectedItem(id,"Mental health");
			break;
		case R.id.btnmissing:
			id=8;
			listener.OnselectedItem(id,"Missing person");
			break;
		case R.id.btncorruption:
			id=9;
			listener.OnselectedItem(id,"Suggestion");
			break;
		case R.id.btnsuspecious:
			id=10;
			listener.OnselectedItem(id,"Suspecious Activity");
			break;
		case R.id.btnvandalism:
			id=11;
			listener.OnselectedItem(id,"Vandalism");
			break;
		case R.id.btnwanted:
			id=12;
			listener.OnselectedItem(id,"Wanted person");
			break;
		case R.id.btnproperty:
			id=13;
			listener.OnselectedItem(id,"Other");
			break;
		default:
			break;
		}
	}


	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
	

}
