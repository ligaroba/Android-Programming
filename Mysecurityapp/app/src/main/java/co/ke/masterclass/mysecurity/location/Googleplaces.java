package co.ke.masterclass.mysecurity.location;



import android.util.Log;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson.JacksonFactory;

public class Googleplaces {
	private static final HttpTransport HTTP_TRANSPRT=new NetHttpTransport();
    private static final String API_KEY_SERVER="AIzaSyCuOq3bJutX3hpn4RMsCJ57P5F0LfcLqWI";
    private static final String API_KEY_BROWSER="AIzaSyD10cSacNA9yPeIjxCsE-CgirWht8spsd0";//browser api key
    
    //search urls 
	private static final String  PLACES_SEARCH_URL="https://maps.googleapis.com/maps/api/place/nearbysearch/json?";
	private static final String PLACES_TEXT_SEARCH_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?";
	private static final String PLACES_DETAILS_URL = "https://maps.googleapis.com/maps/api/place/details/json?";
	
	
	private double _latitude;
	private double _longitude;
	private double _radius;
	
	private static final String TAG="GooglePlaces";
	private HttpRequestFactory httpRequestFactory;
	private HttpRequest request;
	
	
	
	
	// searching for places given the longitude and latitude
public PlaceList search(double latitude, double longitude, double radius, String types)
	throws Exception{
		this._latitude=latitude;
		this._longitude=longitude;
		this._radius=radius;
		
		try{
			httpRequestFactory = createRequestFactory(HTTP_TRANSPRT);
		    request = httpRequestFactory.buildGetRequest(new GenericUrl(PLACES_SEARCH_URL));
			request.getUrl().put("Key", API_KEY_BROWSER);
			request.getUrl().put("location",_latitude + "," + _longitude);
			request.getUrl().put("radius", _radius);// in meters
			request.getUrl().put("sensor", "false");
			//error
			Log.d(TAG,latitude + "  "  + longitude + "    " + types );
			if(types !=null){
				request.getUrl().put("type", types);
				PlaceList list=request.execute().parseAs(PlaceList.class);
		
		    
				Log.d(TAG,"Places Status"+ " " + list.status);
				Log.d(TAG,list.results.toString());
				return list;
				
			}
			
		}catch(HttpResponseException httpe){
			
			Log.e(TAG, "Error " + httpe.getMessage());
			return null;
		}
		return null;
		
	}
	
// searching for places detailed information 
	
		public PlaceDetails getPlaceDetails(String reference) throws Exception {
			try {
				httpRequestFactory =createRequestFactory(HTTP_TRANSPRT);
				request=httpRequestFactory.buildGetRequest(new GenericUrl(PLACES_DETAILS_URL));
				request.getUrl().put("reference", reference);
				request.getUrl().put("sensor", "false");
				PlaceDetails place = request.execute().parseAs(PlaceDetails.class);
				
				return place;
				
			}catch(HttpResponseException httpe){
				Log.e(TAG,"error " + httpe.getMessage());
				throw httpe;
				
			}
		}
		
		// creating http request factory
		
		public static HttpRequestFactory createRequestFactory(final HttpTransport transport){
			
			return transport.createRequestFactory(new HttpRequestInitializer()
			{
				public void initialize(HttpRequest request) {
					HttpHeaders headers = new HttpHeaders();
					headers.setUserAgent("SecurityApp");
					request.setHeaders(headers);
					JsonObjectParser parser = new JsonObjectParser(new JacksonFactory());
					request.setParser(parser);
				}
			});
		
		
			
}
		}

