package com.telosoftapps.mtokamanager.fragments;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.telosoftapps.mtokamanager.R;
import com.telosoftapps.mtokamanager.customs.Constants;
import com.telosoftapps.mtokamanager.customs.IndividualVehicleList;
import com.telosoftapps.mtokamanager.interfaces.FetchDataListener;
import com.telosoftapps.mtokamanager.network.SqliteDBhandler;

public class ViewReport extends Fragment implements FetchDataListener{
View view;
TextView listheader,groupname,reportdate,createdat,typeuse;
ListView listviewasset;
IndividualVehicleList assetdata;
SqliteDBhandler db;
String asseid;
private String TAG="ViewReport";
List<IndividualVehicleList> listdata= new ArrayList<IndividualVehicleList>();
List<IndividualVehicleList>  assetInfo= new ArrayList<IndividualVehicleList>();
List<HashMap<String,String>> aList ;
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}

	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view =inflater.inflate(R.layout.individual_asset_view, container,false);
		listheader=(TextView) view.findViewById(R.id.assetname);
		groupname=(TextView) view.findViewById(R.id.showgroupname);
		typeuse=(TextView) view.findViewById(R.id.showtypeuse);
		reportdate=(TextView) view.findViewById(R.id.showreporteddate);
		createdat=(TextView) view.findViewById(R.id.showdatecreated);
		listviewasset=(ListView) view.findViewById(R.id.individualasset);
		db=new SqliteDBhandler(getActivity());
		SharedPreferences settings =getActivity().getSharedPreferences(Constants.PREFS_ASSET, 0);
		  if(settings.getString(Constants.KEY_ASSETID, null)!=null){
			  asseid= settings.getString(Constants.KEY_ASSETID, null);
		  }
		
		listdata=db.getPeromance(asseid);
		assetInfo=db.getAssetData(asseid);
		
		listheader.setText(asseid);
		
		
	
		String date="";
		String createDate=assetInfo.get(0).getCreatedat();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm");
		try {
			date = String.valueOf((Date)formatter.parse(createDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		createdat.setText(date);
		groupname.setText(assetInfo.get(0).getGroup());

aList = new ArrayList<HashMap<String,String>>();        
        
        for(int i=0;i<listdata.size();i++){
        	HashMap<String, String> hm = new HashMap<String,String>();
            hm.put("expense",Double.toString(listdata.get(i).expenses()));
            hm.put("returns",Double.toString(listdata.get(i).revenue()));
            hm.put("profitLoss", Double.toString(listdata.get(i).profitLoss())); 
            try {
				hm.put("report",String.valueOf((Date)formatter.parse(listdata.get(i).getDateOfReport())));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            hm.put("typeuse",listdata.get(i).getTypeUse()); 
            aList.add(hm);        
        }
               //Log.d(TAG, "asset array  "+ aList.toString()+ "And all are " + listdata.size());
        String[]  from = {"expense","returns","profitLoss","report","typeuse"};
              
		int[] mapto=new int[]{
							R.id.showtexpenses,
		                    R.id.showreturns,
		                     R.id.showprofitloss,
		                   R.id.showreporteddate,
		                   R.id.showtypeuse
		                    };
		
		
		SimpleAdapter sAdapter =new SimpleAdapter(getActivity().getBaseContext(),aList,R.layout.custom_report_textview,from, mapto);
		listviewasset.setAdapter(sAdapter);
return view;
		
	}

	@Override
	public void onFetchDataComplete(List<IndividualVehicleList> data) {
		// TODO Auto-generated method stub
	
		
	
	}

	@Override
	public void onFetchDataFailure(String msg) {
		// TODO Auto-generated method stub
		
     
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();

	}




	

}
