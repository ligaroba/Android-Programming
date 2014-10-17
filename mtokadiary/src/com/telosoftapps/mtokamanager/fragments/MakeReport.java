package com.telosoftapps.mtokamanager.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.telosoftapps.mtokamanager.R;

public class MakeReport extends Fragment implements OnClickListener, OnItemSelectedListener {
View view;
EditText instartAmount,inChargeRush,inChargeNom,infuel,inMaintenace,inParking,inInsurance,inOthers;
Spinner inTripsrush,inTripsnom;
Button makeReport;
private static String TAG="Make Report";

public interface onMakeReportClicked{
	public void onMakeReportSubmitted(String stAmount,String cRush,
			String cNomal,String fuel,String maintanance, String parking,String insurance,String others,String tRush,String tNom);
	
}
onMakeReportClicked listener;
private String triprush,tripnom="";
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		 try{
			 listener= (onMakeReportClicked)activity;
		 }catch(ClassCastException e){
			 throw new ClassCastException(activity.toString()
	                    + " must implement AuthenticationDialogListener"); 
			 
		 }
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.make_report, container,false);
		instartAmount=(EditText) view.findViewById(R.id.edtstartamount);
		inChargeRush=(EditText) view.findViewById(R.id.edtchargersrush);
		inChargeNom=(EditText) view.findViewById(R.id.edtchargersnomal);
		infuel=(EditText) view.findViewById(R.id.edtfuelcost);
		inMaintenace=(EditText) view.findViewById(R.id.edtmaitencecost);
		inParking=(EditText) view.findViewById(R.id.edtparkingcost);
		inInsurance=(EditText) view.findViewById(R.id.edtinsurancecost);
		inOthers=(EditText) view.findViewById(R.id.edtothercost);
		inTripsrush= (Spinner) view.findViewById(R.id.spintripsrush);
		inTripsnom=(Spinner) view.findViewById(R.id.spintripsnomal);
		makeReport= (Button) view.findViewById(R.id.btnmakereport);
		makeReport.setOnClickListener(this);
		inTripsnom.setOnItemSelectedListener(this);
		inTripsrush.setOnItemSelectedListener(this);
		
		return view;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnmakereport:
			listener.onMakeReportSubmitted(instartAmount.getText().toString(),
					inChargeRush.getText().toString(), inChargeNom.getText().toString(),
					infuel.getText().toString(), inMaintenace.getText().toString(),
					inParking.getText().toString(), inInsurance.getText().toString(), inOthers.getText().toString(), 
					triprush, tripnom);
			
			instartAmount.setText("");
			inChargeRush.setText("");
			inChargeNom.setText("");
			infuel.setText("");
			inMaintenace.setText("");
			inParking.setText("");
			inInsurance.setText("");
			inOthers.setText("");
			
			break;

		default:
			break;
		}

		
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
		switch (parent.getId()) {
		case R.id.spintripsrush:
			String[] rush = getResources().getStringArray(R.array.trips);
			if(rush[position]!=null && rush[position]!=""&& rush[position]!="Trips"){
				//Log.d(TAG, "this item is clicked " + triprush);
						triprush=rush[position];
						//Toast.makeText(getActivity(),"selection" + triprush , Toast.LENGTH_LONG).show();	
				}
					//Toast.makeText(getActivity(),"please make a selection " , Toast.LENGTH_LONG).show();	
			break;

		case R.id.spintripsnomal:
			String[] nom = getResources().getStringArray(R.array.trips);
			if(nom[position]!=null && nom[position]!=""&& nom[position]!="Trips"){
				Log.d(TAG, "this item is clicked " + triprush);
						tripnom=nom[position];
					//	Toast.makeText(getActivity(),"selection" + tripnom , Toast.LENGTH_LONG).show();
				}
				//Toast.makeText(getActivity(),"please make a selection " , Toast.LENGTH_LONG).show();
			
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
