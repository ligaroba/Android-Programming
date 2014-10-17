package co.ke.masterclass.mysecurity.fragments;


import co.ke.masterclass.mysecurity.R;
import co.ke.masterclass.mysecurity.fragments.CrimerReport.OnCrimeReportItemclick;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;



public class ReportCrimeDash extends Fragment implements OnClickListener {
View view;
private Button btntheft,btnvandalism,btnmissing,btnmedical,
btnaccident,btnassult,btndistubance,btndrugs,btnharrasment,btnproperty,btncorruption,btnsuspecious,btnwanted;
private int id;
private String crimename="";

//instantiating interfaces
private OnReportItemclick listener;

//interfaces 
public interface OnReportItemclick{
	public void ReportButtonPressed(int id,String name);
}








	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		 try{
			 listener= (OnReportItemclick)activity;
		 }catch(ClassCastException e){
			 throw new ClassCastException(activity.toString()
	                    + " must implement AuthenticationDialogListener"); 
			 
		 }
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view =inflater.inflate(R.layout.crimeoptions, container,false);
		btntheft=(Button) view.findViewById(R.id.btntheft);
		btnaccident=(Button) view.findViewById(R.id.btnaccident);
		btnassult=(Button) view.findViewById(R.id.btnassult);
		btndistubance=(Button) view.findViewById(R.id.btndistubance);
		btndrugs=(Button) view.findViewById(R.id.btndrugs);
		btnharrasment=(Button) view.findViewById(R.id.btnharrasment);
		btnmedical=(Button) view.findViewById(R.id.btnmedical);
		btnmissing=(Button) view.findViewById(R.id.btnmissing);
		btnproperty=(Button) view.findViewById(R.id.btnproperty);
		btncorruption=(Button) view.findViewById(R.id.btncorruption);
		btnsuspecious=(Button) view.findViewById(R.id.btnsuspecious);
		btnvandalism=(Button) view.findViewById(R.id.btnvandalism);
		//btnwanted=(Button) view.findViewById(R.id.btnwanted);
		
		btntheft.setOnClickListener(this);
		btnaccident.setOnClickListener(this);
		btnassult.setOnClickListener(this);
		btndistubance.setOnClickListener(this);
		btndrugs.setOnClickListener(this);
		btnharrasment.setOnClickListener(this);
		btnmedical.setOnClickListener(this);
		btnmissing.setOnClickListener(this);
		btnproperty.setOnClickListener(this);
		btncorruption.setOnClickListener(this);
		btnsuspecious.setOnClickListener(this);
		btnvandalism.setOnClickListener(this);
		//btnwanted.setOnClickListener(this);
		
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
			listener.ReportButtonPressed(id,"Theft");
			
			break;
		case R.id.btnaccident:
			id=2;
			listener.ReportButtonPressed(id,"Accident");
			break;
		case R.id.btnassult:
			id=3;
			listener.ReportButtonPressed(id,"Assault");
			break;
		case R.id.btndistubance:
			id=4;
			listener.ReportButtonPressed(id,"Disturbance");
			break;
		case R.id.btndrugs:
			id=5;
			listener.ReportButtonPressed(id,"Drug Abuse");
			break;
		case R.id.btnharrasment:
			id=6;
			listener.ReportButtonPressed(id,"Harrasement");
			break;
		case R.id.btnmedical:
			id=7;
			listener.ReportButtonPressed(id,"Mental health");
			break;
		case R.id.btnmissing:
			id=8;
			listener.ReportButtonPressed(id,"Missing person");
			break;
		case R.id.btncorruption:
			id=9;
			listener.ReportButtonPressed(id,"corruption");
			break;
		case R.id.btnsuspecious:
			id=10;
			listener.ReportButtonPressed(id,"Suspecious Activity");
			break;
		case R.id.btnvandalism:
			id=11;
			listener.ReportButtonPressed(id,"Vandalism");
			break;
		case R.id.btnwanted:
			id=12;
			listener.ReportButtonPressed(id,"Wanted person");
			break;
		case R.id.btnproperty:
			id=13;
			listener.ReportButtonPressed(id,"Property");
			break;
		default:
			break;
		}
	}
	

}
