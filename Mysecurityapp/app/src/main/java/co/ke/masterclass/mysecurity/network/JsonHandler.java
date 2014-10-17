package co.ke.masterclass.mysecurity.network;

import android.util.Log;

import com.google.api.client.http.UrlEncodedContent;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by root on 9/22/14.
 */
public class JsonHandler {
    private String url="http://www.myroad.co.ke/mysecurity/index.php";
    StringBuilder builder =new StringBuilder();
    InputStream placeContent;
    JSONObject jsonobject;
    String json;
    String TAG="JSON HANDLER";

    public JSONObject JsonConnection(List<NameValuePair> params){

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(url);
    if(params!=null) {
      try{
        httppost.setEntity(new UrlEncodedFormEntity(params));

          Log.e(TAG,params.toString());

    } catch (UnsupportedEncodingException ex) {
        // TODO Auto-generated catch block
        ex.printStackTrace();
    }

        try{
        HttpResponse response = httpclient.execute(httppost);
        if(response.getStatusLine().getStatusCode()== 200){

            placeContent = null;

            placeContent = response.getEntity().getContent();

           BufferedReader breader = new BufferedReader(new InputStreamReader(placeContent,"UTF-8"),8) ;

            String linein="";

            while((linein=breader.readLine())!=null) builder.append(linein);


        }

            placeContent.close();



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


           json=  builder.toString();
        Log.e(TAG,json.toString());

    }

    if(json!=null) {
            try {
                jsonobject = new JSONObject(json);
                Log.e(TAG,jsonobject.toString());

              }catch(JSONException jsonX){

                jsonX.printStackTrace();
            }
    }else {

             jsonobject=null;
    }

    return jsonobject;
    }

}
