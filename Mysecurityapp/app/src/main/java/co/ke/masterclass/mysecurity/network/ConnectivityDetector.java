package co.ke.masterclass.mysecurity.network;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.net.ConnectivityManagerCompat;

public class ConnectivityDetector {
	
	private Context _context;


	  
	  
	public ConnectivityDetector(Context _context) {
		
		this._context = _context;
	}
	

	
	public boolean isConnectionToInternet(){
		
       ConnectivityManager connectivity = 
				(ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if(connectivity !=null){
		NetworkInfo info = connectivity.getActiveNetworkInfo();
         if (info != null && info.isConnected()){
             return true;  
        }else{
        return false;
}
	}
		 return false;
}
}
	
	

