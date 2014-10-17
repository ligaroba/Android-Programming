package com.telosoft.mazingira.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JSONHandler {
	InputStream inputstream = null;
	//private static String Host_url= "http://mkulimabima.com//mtoka_api/index.php";
	//normal emulator 
	//private static String Host_url= "http://10.0.2.2//mtoka_api/index.php";
	// for Geny motion
	private static String Host_url= "http://10.0.3.2/mazingara/index.php";
	String result = "";
	String json= "";
	HttpResponse httpResponse;
	JSONObject jsonObject;
	JSONObject jArray = null;
	String TAG="JSONParser";
	
	private String msg="";
	
	
	
	

	public JSONHandler() {
	
		// TODO Auto-generated constructor stub
	}


	
	
		
	
	public JSONObject JsonConnection(List<NameValuePair> params){
		HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpost = new HttpPost(Host_url);
			
		//	Log.d(TAG,"this is the value of " + params + "and url " + Host_url);
			if(params!=null){
				try {
					Log.d(TAG, "params  " + params);
							httpost.setEntity(new UrlEncodedFormEntity(params));
				
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		
							
			}
			try {
						httpResponse=httpClient.execute(httpost);
						int statuscode=httpResponse.getStatusLine().getStatusCode();
						if(statuscode==200){
							
							inputstream=httpResponse.getEntity().getContent();
							
						}else{
							return null;
							
						}
						
									
				
				
			} catch (ClientProtocolException cpe) {
				// TODO Auto-generated catch block
				cpe.printStackTrace();
			} catch (IOException io) {
				// TODO Auto-generated catch block
				io.printStackTrace();
			}
			
			BufferedReader reader=null;
			try {
					reader = new BufferedReader(new InputStreamReader(inputstream, "UTF-8"), 8);
			} catch (UnsupportedEncodingException uee) {
				// TODO Auto-generated catch block
				uee.printStackTrace();
			}
						StringBuilder sb = new StringBuilder();
						String line=null;
						try {
							while ((line = reader.readLine())!=null) {
								
								sb.append(line);
							}
							inputstream.close();
							json=sb.toString();	
							
							//Log.d(TAG, "JsonHandler json  returned   " + json);
						
				
		
				
			} catch (IOException io) {
				// TODO Auto-generated catch block
				io.printStackTrace();
				
				Log.e(TAG, "Error converting to string");
			
	
				
			}

						// creating a JSON OBJECT From a json String 
				//		Log.d(TAG," getJsonConnection() " + json);
						if(!json.equals("null")){
									try {
											jsonObject= new JSONObject(json);
										
									   } catch (JSONException jerror) {
											// TODO Auto-generated catch block
												jerror.printStackTrace();
											
												Log.e(TAG,"Error parsing JSON data" + jerror.toString());
										}
										//Log.d(TAG, "JsonHandler jsonObject returned" + jsonObject.toString());
							}else {
										jsonObject=null;
									
							}
						
						//Log.d(TAG," final return  " + jsonObject.toString());
						return jsonObject;
			
			
	}






			
			
	}

	


