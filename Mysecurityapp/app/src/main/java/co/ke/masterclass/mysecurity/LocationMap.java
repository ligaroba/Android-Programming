package co.ke.masterclass.mysecurity;


import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import co.ke.masterclass.mysecurity.location.PlaceList;



public class LocationMap extends FragmentActivity implements OnCameraChangeListener, LocationListener {
View view;
PlaceList nearPlaces;
GoogleMap map;
double latitude;
double longitude;
String vicinity;
LatLng pos,markerpos;
private Marker userMarker;
private String TAG="LoactionMap";
private  String placesSearchStr="";
private Marker[] placeMarkers;
private final int MAX_PLACES = 20;
private MarkerOptions[] places;

    protected LocationManager locationManager;

 /*   @Override
    public View onCreate(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.maplocations,container,false);


        return view;
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maplocations);
        //near place list
        SupportMapFragment mf= (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapview);
        map=mf.getMap();
        Intent i =getIntent();

        placeMarkers = new Marker[MAX_PLACES];
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);


        // user current location
        //latitude:-1.2987482912937038now, longitude: 36.79081592954852

        double user_latitude= i.getDoubleExtra("user_latitude",-1.2667);
        double user_longitude=i.getDoubleExtra("user_longitude",36.8000);

        if(user_latitude==0.0 && user_longitude==0.0) {
            user_latitude=-1.2667;
            user_longitude=36.8000;
        }
            nearPlaces = (PlaceList) i.getSerializableExtra("near_places");

            Log.d(TAG, "Lat " + user_latitude + "   Long " + user_longitude);

            if (map != null) {
                pos = new LatLng(user_latitude, user_longitude);
                map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                // getting user location
                //map.setMyLocationEnabled(true);

                if (userMarker != null)
                userMarker.remove();
                userMarker = map.addMarker(new MarkerOptions()
                        .position(pos)
                        .title("You are here")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_bluoverlay))
                        .snippet("Your last recorded location"));

                map.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, 15));



                try {
                    placesSearchStr = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="
                            +URLEncoder.encode(String.valueOf(user_latitude), "UTF-8")
                            +","
                            + URLEncoder.encode(String.valueOf(user_longitude), "UTF-8")
                            +"&radius="
                            +URLEncoder.encode("1000", "UTF-8")
                            +"&sensor="
                            +URLEncoder.encode("true", "UTF-8")
                            +"&types="
                            +URLEncoder.encode("hospital|police", "UTF-8")
                            +"&key="
                            +URLEncoder.encode("AIzaSyD10cSacNA9yPeIjxCsE-CgirWht8spsd0", "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }


               updatePlaces();

            }
        map.setOnCameraChangeListener(new OnCameraChangeListener() {

            @Override
            public void onCameraChange(CameraPosition position) {
                // TODO Auto-generated method stub
                map.setOnCameraChangeListener(LocationMap.this);

            }
        });

  }
    @Override
    public void onLocationChanged(Location location) {
        Log.v("MyMapActivity", "location changed");
        updatePlaces();
    }

    private void updatePlaces() {
        new GetPlaces().execute(placesSearchStr);
        Log.e(TAG,placesSearchStr);
    }

    @Override
    public void onProviderDisabled(String provider){
        Log.v("MyMapActivity", "provider disabled");
    }
    @Override
    public void onProviderEnabled(String provider) {
        Log.v("MyMapActivity", "provider enabled");
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.v("MyMapActivity", "status changed");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(map!=null){
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 30000, 100, this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(map!=null){
            locationManager.removeUpdates(this);
        }
    }


    @Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}



	protected boolean isRouteDisplayed() {
		return false;
	}



	@Override
	public void onCameraChange(CameraPosition position) {
		// TODO Auto-generated method stub
		
	}

    private class GetPlaces extends AsyncTask<String, Void, String> {
        boolean missingValue=false;
        LatLng placeLL=null;
        String placeName="";
        String vicinity="";
        int currIcon = R.drawable.ic_overlay;
        int policemarker=R.drawable.ic_policemarker;
        int hospitalmarker=R.drawable.ic_hospitalmarker;
        @Override
        protected String doInBackground(String... placesURL) {

            StringBuilder placesBuilder = new StringBuilder();

            for (String placeSearchURL : placesURL) {
                HttpClient placesClient = new DefaultHttpClient();
                try {
                    HttpGet placesGet = new HttpGet(placeSearchURL);
                    HttpResponse placesResponse = placesClient.execute(placesGet);
                    StatusLine placeSearchStatus = placesResponse.getStatusLine();
                    if (placeSearchStatus.getStatusCode() == 200) {

                        HttpEntity placesEntity = placesResponse.getEntity();
                        InputStream placesContent = placesEntity.getContent();
                        InputStreamReader placesInput = new InputStreamReader(placesContent);
                        BufferedReader placesReader = new BufferedReader(placesInput);
                        String lineIn;
                        while ((lineIn = placesReader.readLine()) != null) {
                            placesBuilder.append(lineIn);
                        }
               return placesBuilder.toString();

                       }
                }
                catch(Exception e){
                    e.printStackTrace();
                }


                            }
            return null;
        }

        protected void onPostExecute(String result) {
            //parse place data returned from Google Places

            if(placeMarkers!=null){
                for(int pm=0; pm<placeMarkers.length; pm++){
                    if(placeMarkers[pm]!=null)
                        placeMarkers[pm].remove();
                }
            }
            try {
                //parse JSON
                JSONObject resultObject = new JSONObject(result);
                JSONArray placesArray = resultObject.getJSONArray("results");
                //loop through places
                places = new MarkerOptions[placesArray.length()];
                Log.e(TAG,placesArray.toString());

                for (int p=0; p<placesArray.length(); p++) {
                    //parse each place
                    try{
                        missingValue=false;
                        JSONObject placeObject = placesArray.getJSONObject(p);
                        JSONObject loc = placeObject.getJSONObject("geometry").getJSONObject("location");
                        placeLL = new LatLng(
                                Double.valueOf(loc.getString("lat")),
                                Double.valueOf(loc.getString("lng")));
                          JSONArray types = placeObject.getJSONArray("types");

                        for(int t=0; t<types.length(); t++){
                            String thisType=types.get(t).toString();
                            if(thisType.contains("hospital")){
                                currIcon = hospitalmarker;
                                break;
                            }
                            else if(thisType.contains("police")){
                                currIcon = policemarker;
                                break;
                            }

                        }
                        vicinity = placeObject.getString("vicinity");
                        placeName = placeObject.getString("name");

                    }
                    catch(JSONException jse){
                        missingValue=true;

                        jse.printStackTrace();
                    }
                    if(missingValue)    places[p]=null;
                    else
                        places[p]=new MarkerOptions()
                                .position(placeLL)
                                .title(placeName)
                                .icon(BitmapDescriptorFactory.fromResource(currIcon))
                                .snippet(vicinity);

                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            if(places!=null && placeMarkers!=null){
                for(int p=0; p<places.length && p<placeMarkers.length; p++){
                    //will be null if a value was missing
                    if(places[p]!=null)
                        placeMarkers[p]=map.addMarker(places[p]);
                }
            }

        }
//fetch and parse place data
    }
	

}
