package co.ke.masterclass.mysecurity.network;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpConnection;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import co.ke.masterclass.mysecurity.custom.ConstantsClass;


/**
 * Created by root on 9/23/14.
 */
public class FileTransfer extends AsyncTask<String,Void,Integer>{
    HttpURLConnection connection = null;
    DataOutputStream outputStream = null;
    DataInputStream inputStream = null;
    String lineEnd="\r\n";
    String twoHyphens="--";
    String boundary="*****";
    int bytesRead,bytesAvailable,bufferSize;
    byte[] buffer;
    int maxBufferSize=1*1024*1024;
    String responseFromServer="";
    String urlString="http://www.myroad.co.ke/mysecurity/report_crimes.php";
    int serverResponseCode;
    String serverResponseMessage="";
    String TAG="FileTransfer";
    String name;
    String pmessage;
    String message;
    ProgressDialog pDialog;
    Context context;
    String json;
    int response;
    JSONObject jsonObject;

    public FileTransfer(String name,String pmessage,Context context){

       this.name=name;
       this.pmessage=pmessage;
       this.context=context;
    }

    protected void onPreExecute(){
        super.onPreExecute();
        pDialog=new ProgressDialog(context);
        pDialog.setMessage(Html.fromHtml("<b> <i> " + pmessage + "</i> </b>"));
        pDialog.setCancelable(false);
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setIndeterminate(true);
        pDialog.show();


    }

    protected Integer doInBackground(String ... selectedPath) {

        File sourcefile = new File(selectedPath[0]);
        if (!sourcefile.isFile()) {
            Log.e(TAG, "source file does not exist: " + selectedPath[0]);
            return 0;
        } else {

            try {
                FileInputStream fileInputStream = new FileInputStream(new File(selectedPath[0]));

                URL url = new URL(urlString);
                connection = (HttpURLConnection) url.openConnection();

                // Allow Inputs &amp; Outputs.
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setUseCaches(false);

                // Set HTTP method to POST.
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Connection", "Keep-Alive");
                connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                connection.setRequestProperty(name, selectedPath[0]);


                outputStream = new DataOutputStream(connection.getOutputStream());
                outputStream.writeBytes(twoHyphens + boundary + lineEnd);
                outputStream.writeBytes("Content-Disposition: form-data;name="+name+";filename=" + sourcefile + "" + lineEnd);
                outputStream.writeBytes(lineEnd);

                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                // Read file
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0) {
                    outputStream.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                }

                outputStream.writeBytes(lineEnd);
                outputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                // Responses from the server (code and message)
                serverResponseCode = connection.getResponseCode();
                serverResponseMessage = connection.getResponseMessage();

                Log.e("uploadFile", "HTTP Response is : "
                        + serverResponseMessage + ": " + serverResponseCode);


                fileInputStream.close();
                outputStream.flush();
                outputStream.close();
            } catch (MalformedURLException ex) {
                Log.e("Debug", "error: " + ex.getMessage(), ex);
            } catch (IOException ioe) {
                Log.e("Debug", "error: " + ioe.getMessage(), ioe);
            }
            //------------------ read the SERVER RESPONSE
            try {
                inputStream = new DataInputStream(connection.getInputStream());
                String str;
                StringBuilder stringBuilder = new StringBuilder();

                while ((str = inputStream.readLine()) != null) {
                    Log.e("Debug", "Server Response " + str);
                        stringBuilder.append(str);

                }
              json=stringBuilder.toString();
              jsonObject = new JSONObject(json);
             response=Integer.parseInt(jsonObject.getString("success"));
             Log.e(TAG,jsonObject.toString());
              inputStream.close();

            } catch (IOException ioex) {
                Log.e("Debug", "error: " + ioex.getMessage(), ioex);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.e(TAG, "Code: " + serverResponseCode + " msg" + serverResponseMessage);
            return serverResponseCode;

        }
    }
    protected void onPostExecute(Integer result){
        pDialog.dismiss();


        if(result==200 && response==1) {
            ConstantsClass.SUCCESS_RESPONSE=response;
           message="File Uploaded successfully";
        }else{
           message="Upload Failed Try again";
        }

        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }


}
