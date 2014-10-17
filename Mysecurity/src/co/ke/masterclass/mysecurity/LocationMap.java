package co.ke.masterclass.mysecurity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import co.ke.masterclass.mysecurity.location.Place;
import co.ke.masterclass.mysecurity.location.PlaceList;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;



public class LocationMap extends FragmentActivity implements OnCameraChangeListener{
View view;
PlaceList nearPlaces;
GoogleMap map;
double latitude;
double longitude;
String vicinity;
LatLng pos,markerpos;
private Marker userMarker;
private String TAG="LoactionMap";






	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	
		   setContentView(R.layout.maplocations);
//near place list
	//SupportMapFragment mf=  (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapview);
	//map=mf.getMap();
      Intent i =new Intent();
	
//	// user current location
//	//latitude:-1.2987482912937038now, longitude: 36.79081592954852
//
    double user_latitude= i.getDoubleExtra("user_latitude",-1.2667);
    double user_longitude=i.getDoubleExtra("user_longitude",36.8000);
   nearPlaces =(PlaceList) i.getSerializableExtra("near_places");
//	
//      Log.d(TAG,"Lat "+  user_latitude + "   Long "+ user_longitude );
//      
//		if(map!=null){
//		pos = new LatLng(user_latitude,user_longitude);
//		map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//	
//	// getting user location
//			//map.setMyLocationEnabled(true);
//	
//		if(userMarker!=null) userMarker.remove();
//		userMarker = map.addMarker(new MarkerOptions()
//			    .position(pos)
//			    .title("You are here")
//			    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_bluoverlay))
//			    .snippet("Your last recorded location"));
//		
//		map.animateCamera(CameraUpdateFactory.newLatLng(pos), 3000, null);
//          
//         
//		}  
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

	protected boolean isRouteDisplayed() {
		return false;
	}



	@Override
	public void onCameraChange(CameraPosition position) {
		// TODO Auto-generated method stub
		
	}
	

}
