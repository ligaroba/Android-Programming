package co.ke.masterclass.mysecurity.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

import co.ke.masterclass.mysecurity.R;
import co.ke.masterclass.mysecurity.Securitymain;
import co.ke.masterclass.mysecurity.custom.ConstantsClass;
import co.ke.masterclass.mysecurity.custom.Validation;

/**
 * Created by root on 9/28/14.
 */
public class UserProfile extends DialogFragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
View view;
EditText infullname, inemail, inphone,inconstituency,inestate,insocial;
Spinner incounty;
 Button setprofile;
    Validation validate=new Validation();
    String message;
    String residentialArea;
    String countyName;
    String gcm_regid;
  GoogleCloudMessaging gcm;
    public static final String REG_ID = "regId";
    private static final String APP_VERSION = "appVersion";
   String TAG="Userprofile";



    public interface onProfileCliked{
    public void onSetProfileClicked(String name,String email, String phone,String social,String locality, String gcm_id);
//,String county, String constu, String estate

}
 onProfileCliked listener;
    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        try{
       listener = (onProfileCliked) activity;
        }catch(ClassCastException e){
            throw new ClassCastException(activity.toString()
                    + " must implement AuthenticationDialogListener");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        view =inflater.inflate(R.layout.userprofile, container,false);
        infullname = (EditText) view.findViewById(R.id.editfullname);
        inemail= (EditText) view.findViewById(R.id.editemail);
        inphone= (EditText) view.findViewById(R.id.editphone);
        inconstituency = (EditText) view.findViewById(R.id.editconstituency);
        inestate= (EditText) view.findViewById(R.id.editestate);
        insocial= (EditText) view.findViewById(R.id.editsocial);
        incounty= (Spinner) view.findViewById(R.id.spincounty);
        setprofile = (Button) view.findViewById(R.id.btnsetprofile);


        setprofile.setOnClickListener(this);
         incounty.setOnItemSelectedListener(this);

        infullname.requestFocus();

        getDialog().setTitle(R.string.userProfle);
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);






        return view;
    }


    @Override
    public void onClick(View view) {
    switch (view.getId()){
        case  R.id.btnsetprofile:

         if(validate.checkEmpty(infullname)==true){
             message="Enter Full names";
         }else if(validate.checkEmpty(inemail)==true){

              message="Please Enter Email" ;
         }else if(validate.checkEmpty(inphone)==true){

             message="Please Enter phone";
         }else if(validate.checkEmpty(inconstituency)==true){

             message="Please Enter Constituency";
         }else if(validate.checkEmpty(inestate)==true){

             message="Please Enter Estate name";
         }
         else {
             if (TextUtils.isEmpty(gcm_regid)) {
                    gcm_regid=registerGCM();

             }else{

                 residentialArea = inestate.getText().toString() + "," + inconstituency.getText().toString() + "," + countyName;

                 listener.onSetProfileClicked(infullname.getText().toString(), inemail.getText().toString(),
                         inphone.getText().toString(), insocial.getText().toString(), residentialArea, gcm_regid);

             }
             }
             Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
             break;

    }

    }

    private String registerGCM() {
        gcm=GoogleCloudMessaging.getInstance(getActivity());
        gcm_regid=getRegistrationId(getActivity());
        if(TextUtils.isEmpty(gcm_regid)){
            registerInBackground();
        }else{
           // reg id already avalible
        }

        return gcm_regid;
    }



    private String getRegistrationId(FragmentActivity activity) {

        final SharedPreferences prefs=activity.getPreferences(getActivity().MODE_PRIVATE);
        String registrationId= prefs.getString(REG_ID,"");
        if(registrationId.isEmpty()){
            return "";
        }
        int registeredVersion=prefs.getInt(APP_VERSION, Integer.MIN_VALUE);;
        int currentVersion=getAppVersion(getActivity());
        if(registeredVersion!=currentVersion){
            Log.e(TAG, "App version changed.");
            return "";
        }


        return registrationId;
    }

    private int getAppVersion(FragmentActivity activity) {
        try {
            PackageInfo packageInfo = activity.getPackageManager().getPackageInfo(activity.getPackageName(),0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
    private void registerInBackground() {
        new AsyncTask<Void, Void, String>() {
            String msg="";
            @Override
            protected String doInBackground(Void... voids) {

                try {
                if(gcm==null){
                    gcm=GoogleCloudMessaging.getInstance(getActivity());
                }

                    gcm_regid=gcm.register(ConstantsClass.GOOGLE_PROJECTID);
                    Log.e(TAG, "registerInBackground - regId: "
                            + gcm_regid);
                    msg = "Device registered, registration ID=" + gcm_regid;
                    storeRegistrationId(getActivity(),gcm_regid);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    msg = "Error :" + ex.getMessage();
                    Log.e(TAG, "Error: " + msg);
                }

                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                Toast.makeText(getActivity(),
                        "Registered with GCM Server." + msg, Toast.LENGTH_LONG)
                        .show();
            }
        }.execute(null,null,null);

    }
    public void storeRegistrationId(FragmentActivity activity,String gcmid){
        final SharedPreferences preferences = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= preferences.edit();
        int appVersion= getAppVersion(activity);
        editor.putString(REG_ID,gcmid);
        editor.putInt(APP_VERSION,appVersion);
        editor.commit();

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String[] county= getResources().getStringArray(R.array.counties);
        if(county[position]!=null && county[position]!=""){
            countyName=county[position];
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }





}
