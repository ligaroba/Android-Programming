package co.ke.masterclass.mysecurity.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import co.ke.masterclass.mysecurity.location.PlaceList;

import com.google.gson.JsonObject;

public class MapJsonHandler {
	
	public JsonObject NearPlaceSearch(double latitude, double longitude, double radius, String types){
		
		String placesSearchStr = "https://maps.googleapis.com/maps/api/place/nearbysearch/" +
			    "json?location="+latitude +","+longitude+
			    "&radius=1000&sensor=true" +
			    "&types="+ types +
			    "&key=AIzaSyAkFbEMEPxmY7u7SmRYLEnDG8CUpUBB1mY";
		

		try {
			StringBuilder stringbuilder= new StringBuilder();
			HttpClient placesClient = new DefaultHttpClient();
			HttpGet placesGet = new HttpGet(placesSearchStr);
			HttpResponse placesResponse = placesClient.execute(placesGet);
			StatusLine placeSearchStatus = placesResponse.getStatusLine();
			if(placeSearchStatus.getStatusCode() == 200){
				
				HttpEntity placesEntity = placesResponse.getEntity();
				InputStream placesContent = placesEntity.getContent();
				InputStreamReader placesInput = new InputStreamReader(placesContent);
				BufferedReader placesReader = new BufferedReader(placesInput);
			
		String lineIn;
				while ((lineIn = placesReader.readLine()) != null) {
					stringbuilder.append(lineIn);
				}
		//	list=stringbuilder.parseAs(PlaceList.class);
			}
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	
		
		
		return null;
		
		
	}

}
