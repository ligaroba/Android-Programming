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
import android.widget.Spinner;

import com.telosoft.mazingira.R;
import com.telosoft.mazingira.validation.Validation;

public class MakeReport extends Fragment implements OnClickListener{
View view;
EditText instartAmount,inChargeRush,inChargeNom,infuel,inMaintenace,inParking,inInsurance,inOthers;
Spinner inTripsrush,inTripsnom;
Button makeReport;
String message;
private Validation validate = validation();
private static String TAG="Make Report";

public interface onMakeReportClicked{
	public void onMakeReportSubmitted(String description,String audio,
			String image,String video);
	
}
onMakeReportClicked listener;
private String triprush,tripnom="";
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		
	}

	private Validation validation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.reportcrime, container,false);
		
		
		return view;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnsendcrime:
			
			
		

		default:
			break;
		}

		
	}

	

	

}
