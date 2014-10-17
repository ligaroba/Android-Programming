package co.ke.masterclass.mysecurity.location;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.provider.Settings;

public class GPSTracker extends Service implements LocationListener {

	private final Context mContext;
	boolean isGPSenabled =false;
	boolean isNetworkenabled=false;
	boolean canGetLocation=false;
	Location location = null;
	double latitude;
	double longitude;
	private static final String TAG="GPSTracker";
	private static final long MIN_DISTANCE_CHANGE_UPDATES=10;//10 meters
	private static final long MIN_TIME_BW_UPDATES=1000*60*1;//1 min
	
	
	protected LocationManager locationManager;
	
	public GPSTracker(Context context){
		this.mContext=context;
		getLocation();
		
	}
	
	
	
	
	
	private Location getLocation() {
		// TODO Auto-generated method stub
		try{
				locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
				//getting Gps status
				
				isGPSenabled=locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
				isNetworkenabled=locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
				
				if(!isGPSenabled && !isNetworkenabled){
					
					// network not enabled 
				}else{
					
					this.canGetLocation=true;
					if(isNetworkenabled){
						locationManager.requestLocationUpdates(locationManager.NETWORK_PROVIDER,MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_UPDATES, this);
						Log.d(TAG, "Network Enabled");
						
						if(locationManager!=null){
							location=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
							if(location!=null){
								latitude=location.getLatitude();
								longitude=location.getLongitude();
							}
						}
					}
					//GPS Enabled
					if(isGPSenabled){
						locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER,MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_UPDATES, this);
						Log.d(TAG, "GPS Enabled");
						
						if(locationManager!=null){
							location=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
							if(location!=null){
								latitude=location.getLatitude();
								longitude=location.getLongitude();
							}
						}
					}
				}
				
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return location;
	}

	public void stopUsingGps(){
		
		if(locationManager!=null){
			locationManager.removeUpdates(GPSTracker.this);
		}
	}
	public double getLatitude(){
		if(location!=null){
			latitude=location.getLatitude();
		}
		return latitude;
	}
	public double getLongitude(){
		if(location!=null){
			longitude=location.getLongitude();
						
		}return longitude;
	}
	public boolean canGetlocation(){
		return this.canGetLocation;
	}

	public void showDialogAleart(){
		AlertDialog.Builder alertdialog = new AlertDialog.Builder(mContext);
		alertdialog.setTitle("GPS Settings");
		alertdialog.setMessage("GPS is not enabled do you want to go to settings");
		alertdialog.setPositiveButton("Settings", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				mContext.startActivity(intent);
			}
		});
		alertdialog.setNegativeButton("Cancel",new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.cancel();
			}
		});
		alertdialog.show();
		
	}
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
