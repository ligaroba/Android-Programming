package co.ke.masterclass.mysecurity.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import co.ke.masterclass.mysecurity.R;



public class CrimerReport extends Fragment implements OnClickListener {
View view;
private Button btnaudio,btnpic,btnvideo,btnsend;
private int id;
public interface OnCrimeReportItemclick{
	public void ButtonPressed(int id);
}
private OnCrimeReportItemclick listener;
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		 try{
			 listener= (OnCrimeReportItemclick)activity;
		 }catch(ClassCastException e){
			 throw new ClassCastException(activity.toString()
	                    + " must implement AuthenticationDialogListener"); 
			 
		 }
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
			view =inflater.inflate(R.layout.reportcrime, container,false);
			btnaudio=(Button) view.findViewById(R.id.btnaudio);
			btnpic=(Button) view.findViewById(R.id.btnpic);
			btnvideo=(Button) view.findViewById(R.id.btnvideo);
			btnaudio.setOnClickListener(this);
			btnpic.setOnClickListener(this);
			btnvideo.setOnClickListener(this);
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
		case R.id.btnaudio:
			id=1;
			listener.ButtonPressed(id);
			
			
			break;
		case R.id.btnpic:
			id=2;
			listener.ButtonPressed(id);
			
			
			break;
		case R.id.btnvideo:
			id=3;
			listener.ButtonPressed(id);
			
			
			break;

		default:
			break;
		}
	}
	

}
