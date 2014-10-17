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
import android.widget.AdapterView;
import android.widget.Spinner;

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
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import co.ke.masterclass.mysecurity.location.PlaceList;


public class HotspotsMap extends FragmentActivity implements OnCameraChangeListener, LocationListener, AdapterView.OnItemSelectedListener {
    private static final String TAG_VALUE = "tag";
    private static final String GET_HOTSPOTS = "getallhotspots";

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

    List<NameValuePair> params =new ArrayList<NameValuePair>();

    protected LocationManager locationManager;

 /*   @Override
    public View onCreate(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.maplocations,container,false);


        return view;
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hotspots);



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

        params.add(new BasicNameValuePair(TAG_VALUE,GET_HOTSPOTS));




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

                map.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, 12));




                    placesSearchStr = "http://www.myroad.co.ke/mysecurity/index.php";



               updatePlaces();

            }
        map.setOnCameraChangeListener(new OnCameraChangeListener() {

            @Override
            public void onCameraChange(CameraPosition position) {
                // TODO Auto-generated method stub
                map.setOnCameraChangeListener(HotspotsMap.this);

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



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        String [] choice= getResources().getStringArray(R.array.choicelocatn);
        if(choice[position]!=null && choice[position]!=""){

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private class GetPlaces extends AsyncTask<String, Void, String> {
        boolean missingValue=false;
        LatLng placeLL=null;
        String placeName="";
        String vicinity="";
        int currIcon = R.drawable.ic_overlay;

        int hotspot=R.drawable.ic_hostpots;
        @Override
        protected String doInBackground(String... placesURL) {

            StringBuilder placesBuilder = new StringBuilder();

            for (String placeSearchURL : placesURL) {
                HttpClient placesClient = new DefaultHttpClient();
                try {
                    HttpPost placesGet = new HttpPost(placeSearchURL);
                    placesGet.setEntity(new UrlEncodedFormEntity(params));
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
                        Log.e(TAG,placesBuilder.toString());
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
                JSONArray placesArray = resultObject.getJSONArray("data");
                //loop through places
                places = new MarkerOptions[placesArray.length()];


                for (int p=0; p<placesArray.length(); p++) {
                    //parse each place
                    try{
                        missingValue=false;
                        JSONObject placeObject = placesArray.getJSONObject(p);

                        placeLL = new LatLng(
                                Double.valueOf(placeObject.getString("latitude")),
                                Double.valueOf(placeObject.getString("longitude")));
                        currIcon = hotspot;
                        vicinity ="Hot spot area";
                        placeName = placeObject.getString("hname");

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
