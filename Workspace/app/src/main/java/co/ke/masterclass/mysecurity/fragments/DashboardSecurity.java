package co.ke.masterclass.mysecurity.fragments;




import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import co.ke.masterclass.mysecurity.R;



public class DashboardSecurity extends Fragment implements OnClickListener {
View view;
private Button btnreport,btnwanted,btnemerge,btnmap;
private int id;
public interface OndashboardItemclick{
	public void dashboardButtonPressed(int id);
}
private OndashboardItemclick listener;
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		 try{
			 listener= (OndashboardItemclick)activity;
		 }catch(ClassCastException e){
			 throw new ClassCastException(activity.toString()
	                    + " must implement AuthenticationDialogListener"); 
			 
		 }
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view =inflater.inflate(R.layout.securitydashboard, container,false);
		btnreport=(Button) view.findViewById(R.id.btnreport);
		btnwanted=(Button) view.findViewById(R.id.btnwanted);
		btnmap=(Button) view.findViewById(R.id.btnmap);
		btnemerge=(Button) view.findViewById(R.id.btnemergency);
		btnwanted.setOnClickListener(this);
		btnreport.setOnClickListener(this);
		btnemerge.setOnClickListener(this);
		btnmap.setOnClickListener(this);
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
		case R.id.btnreport:
			id=1;
			listener.dashboardButtonPressed(id);  
			break;
		case R.id.btnemergency:
			id=2;
			listener.dashboardButtonPressed(id);  
			break;
		case R.id.btnmap:
			id=3;
			listener.dashboardButtonPressed(id);  
			break;
		case R.id.btnwanted:
			id=4;
			listener.dashboardButtonPressed(id);  
			break;

		default:
			break;
		}
	}
	

}
