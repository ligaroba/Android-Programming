package co.ke.masterclass.mysecurity.network;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import co.ke.masterclass.mysecurity.R;
import co.ke.masterclass.mysecurity.custom.ConstantsClass;
import co.ke.masterclass.mysecurity.fragments.CrimerReport;
import co.ke.masterclass.mysecurity.fragments.ViewPersons;
import co.ke.masterclass.mysecurity.functions.CustomFunctions;

public class ProcessingTask extends AsyncTask<List<NameValuePair>,String,String>{
  JSONObject json;
    private JsonHandler handler;
    Context context;
    String message;
    ProgressDialog pDialog;
    JSONArray jsonArray;
    JSONObject jsonPerson,jsonemergency,jsonuser;
    String response;
    ListView mListview;
    String TAG="ProcessingTAsk";
    SqliteDbHandler db;
    private ConstantsClass consta;
    private static final String SUCCESS="success";
    private static final String ERROR="error";
    private int displayprogres;
    private MenuItem refreshMenuItem;
    public ProcessingTask(Context context,String message
          ,int show//,MenuItem item
                          //
                         ) {
        handler = new JsonHandler();
        this.context=context;
        this.message=message;
       displayprogres=show;
      //  refreshMenuItem=item;

    }

    public void showProgress(){
        pDialog=new ProgressDialog(context);
        pDialog.setMessage(Html.fromHtml("<b> <i> " + message+ "</i> </b>"));
        pDialog.setCancelable(false);
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setIndeterminate(true);
        pDialog.show();
    }

    @SuppressLint("NewApi")
    protected void onPreExecute(){
    super.onPreExecute();
        if(displayprogres==1){
            showProgress();
        }else{
          //  refreshMenuItem.setActionView(R.layout.custom_progressbar);

          //  refreshMenuItem.expandActionView();
        }



  }
  protected  String doInBackground(List<NameValuePair>... params){


      try {
             json=handler.JsonConnection(params[0]);


          if(Integer.parseInt(json.getString(SUCCESS))>0){
              // jsonArray= json.getJSONArray("tip");
              response = json.getString(SUCCESS);
              if(Integer.parseInt(response)==1){
                 consta.SUCCESS_RESPONSE=1;
              }else if (Integer.parseInt(response) == 2){

                      consta.SUCCESS_RESPONSE = 2;
                      db= new SqliteDbHandler(context);
                      db.resetTables("Person");
                   //   CustomFunctions personJsonParser = new CustomFunctions();
                   // ConstarntsClass.PERSONS = personJsonParser.parse(json);
                      jsonArray = json.getJSONArray("person");
                      Log.e(TAG,jsonArray.toString());

                    for(int i=0; i <jsonArray.length(); i++) {
                        jsonPerson = jsonArray.getJSONObject(i);
                        db.addPerson(jsonPerson.getInt("id"),jsonPerson.getString("names"),jsonPerson.getString("gender"),
                                jsonPerson.getString("description"),jsonPerson.getString("identity"),jsonPerson.getString("location"),jsonPerson.getString("photo"),
                                jsonPerson.getString("dateReported"),jsonPerson.getInt("missinCrimestatus"));
                        Log.e(TAG,jsonPerson.toString());
                    }


              }else if (Integer.parseInt(response) == 3){

                  consta.SUCCESS_RESPONSE = 3;
                  db= new SqliteDbHandler(context);
                  db.resetTables("user");
                  //   CustomFunctions personJsonParser = new CustomFunctions();
                  // ConstarntsClass.PERSONS = personJsonParser.parse(json);
                  jsonArray = json.getJSONArray("user");

                  Log.e(TAG,jsonArray.toString());

                  for(int i=0; i <jsonArray.length(); i++) {
                      jsonuser = jsonArray.getJSONObject(i);
                      db.addUser(jsonuser.getInt("id"),jsonuser.getString("names"),jsonuser.getString("email"),jsonuser.getString("phone"),
                              jsonuser.getString("social"),jsonuser.getString("locality"),jsonuser.getString("dateAdded"));
                      Log.e(TAG,jsonuser.toString());
                  }


              }

              else if (Integer.parseInt(response) == 4){

                  consta.SUCCESS_RESPONSE = 4;
                  db= new SqliteDbHandler(context);
                  db.resetTables("EmergencyLines");
                  //   CustomFunctions personJsonParser = new CustomFunctions();
                  // ConstantsClass.PERSONS = personJsonParser.parse(json);
                  jsonArray = json.getJSONArray("emergency");
                  Log.e(TAG,jsonArray.toString());

                  for(int i=0; i <jsonArray.length(); i++) {
                      jsonemergency = jsonArray.getJSONObject(i);
                      db.addContact(jsonemergency.getInt("id"), jsonemergency.getString("organization"),jsonemergency.getString("contact")
                              );
                      Log.e(TAG, jsonemergency.toString());
                  }


              }
              else if (Integer.parseInt(response) == 5){

                  consta.SUCCESS_RESPONSE = 5;


                  }

          }else if(Integer.parseInt(json.getString(ERROR))>0){
              //jsonArray= json.getJSONArray("tip");
              response = json.getString(ERROR);
              if(Integer.parseInt(response)==1){
                  consta.ERROR_RESPONSE=1;
              }else
              if(Integer.parseInt(response)==2){
                  consta.ERROR_RESPONSE=2;
              }else
              if(Integer.parseInt(response)==3){
                  consta.ERROR_RESPONSE=3;
                  consta.ERROR_MESSAGE=json.getString("error_msg");
              }
              else
              if(Integer.parseInt(response)==4){
                  consta.ERROR_RESPONSE=4;
              }else
              if(Integer.parseInt(response)==5){
                  consta.ERROR_RESPONSE=5;
              }


          }





      } catch (JSONException e) {
          e.printStackTrace();
      }
      return null;
  }


   protected void onPostExecute(String result){
if(displayprogres==1) {
    pDialog.dismiss();
}
 //   refreshMenuItem.collapseActionView();
  //     refreshMenuItem.setActionView(null);

        if(consta.SUCCESS_RESPONSE==1){
                message="Tip Succesfully Sent";
       }else if(consta.ERROR_RESPONSE==1){
            message="Sorry Error Occurred try again";

        }
        else if(consta.ERROR_RESPONSE==3){

            message=consta.ERROR_MESSAGE;

        }else if(consta.ERROR_RESPONSE==5){
            message="Failed to report try again";

        }else if(consta.SUCCESS_RESPONSE==5){
            message="Tip Sent successfully";

        }


       Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
  }







}