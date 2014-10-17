package co.ke.masterclass.mysecurity.fragments;


import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import co.ke.masterclass.mysecurity.R;
import co.ke.masterclass.mysecurity.Securitymain;
import co.ke.masterclass.mysecurity.custom.ConstantsClass;
import co.ke.masterclass.mysecurity.custom.CustomSeterClass;
import co.ke.masterclass.mysecurity.custom.Validation;
import co.ke.masterclass.mysecurity.functions.CustomFunctions;
import co.ke.masterclass.mysecurity.network.SqliteDbHandler;


public class PersonsDetails extends Fragment implements OnClickListener {
    View view;
    private ListView listpersons;
    private SimpleAdapter adapter;
    private List<HashMap<String, Object>> personlist = new ArrayList<HashMap<String, Object>>();
    private int id;
    private String crimename = "";
    String jsonRecived;
    String TAG = "Person Details";
    HashMap<String, Object> resultdata;
    SqliteDbHandler db;
    List<CustomSeterClass>  personDetails;
    Securitymain sec = new Securitymain();
    int personid;
    Validation vali=new Validation();

    TextView inname,ingender,inlocation,indescription;
    ImageView inpersonimage;
    EditText inTipDetails;
    Button reportSeen;

    //interfaces
    public interface OnPersonDataSent {
        public void OnTipSent(String des, int personid);
    }


    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        try {
            listener = (OnPersonDataSent) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement AuthenticationDialogListener");

        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        view = inflater.inflate(R.layout.persondetails, container, false);
        inname = (TextView)view.findViewById(R.id.tvnames);
        indescription = (TextView)view.findViewById(R.id.tvdescription);
        ingender = (TextView)view.findViewById(R.id.tvgender);
        inlocation = (TextView)view.findViewById(R.id.tvlocation);
        inpersonimage= (ImageView) view.findViewById(R.id.imgperson);
        inTipDetails= (EditText) view.findViewById(R.id.edtdetails);
        reportSeen= (Button) view.findViewById(R.id.btnreportwantedperson);
        reportSeen.setOnClickListener(this);



        Bundle dataid= getArguments();
        personid=dataid.getInt("personid");

        db = new SqliteDbHandler(getActivity());
   //  new Securitymain().setTitle("Wanted List");
        personDetails = db.getPersonsDetails(personid);
        if ( personDetails != null) {


            inname.setText(personDetails.get(0).getName());
            indescription.setText(personDetails.get(0).getDescription());
            inlocation.setText("Location seen: " +personDetails.get(0).getLocation());

            ingender.setText("Gender: "+ personDetails.get(0).getGender());
            inpersonimage.setImageResource(R.drawable.ic_person);

            HashMap<String, Object> hm =new HashMap<String, Object>();
            String imgUrl =  personDetails.get(0).getPhoto();
            ImageTask imageLoaderTask = new ImageTask();
            String full_url = "http://myroad.co.ke/mysecurity/uploads/persons/wanted/" + imgUrl;
            HashMap<String, Object> hmDownload = new HashMap<String, Object>();
                hm.put("image_path", full_url);
                hm.put("position", personid);
                imageLoaderTask.execute(hm);

            }


        return view;
    }
    //instantiating interfaces
    private OnPersonDataSent listener;

    @Override
    public void onClick(View view) {
       if( vali.checkEmpty(inTipDetails)==true){
           Toast.makeText(getActivity(),"Please make description",Toast.LENGTH_LONG).show();
       }else{
           listener.OnTipSent(inTipDetails.getText().toString(),personid);
           inTipDetails.setHint(R.string.descriptionguide);
       }

    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
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








    class ImageTask extends AsyncTask<HashMap<String, Object>, Void,HashMap<String, Object>> {

        private static final int IO_BUFFER_SIZE = 4 * 1024;


        @Override
        protected     HashMap<String, Object>  doInBackground(HashMap<String, Object>... hm) {
            InputStream iStream = null;
            String imgUrl = (String) hm[0].get("image_path");
            int position = (Integer) hm[0].get("position");


            URL rURL = null;
            try {
                rURL = new URL(imgUrl);


                URLConnection connection = rURL.openConnection();
                connection.setConnectTimeout(10240);
               // BufferedReader inputStream = new BufferedReader(new InputStreamReader(rURL.openStream()));
                InputStream inputStream = new BufferedInputStream(rURL.openStream(), 10240);
                Log.e(TAG,inputStream.toString() + " from this url:" + rURL);
                File cacheDir = new CustomFunctions().getDataFolder(getActivity());
                File cacheFile = new File(cacheDir, "Per_" + position + ".png");



                FileOutputStream outputStream = new FileOutputStream(cacheFile);


                byte[] byt = new byte[2048];
                int length;

                while ((length=inputStream.read(byt))!=-1){

                    outputStream.write(byt,0,length);
                }
                InputStream fileInputStream = new FileInputStream(cacheFile);



                HashMap<String, Object> hmBitmap = new HashMap<String, Object>();

                // Storing the path to the temporary image file
                hmBitmap.put("image",Uri.fromFile(cacheFile));

                // Storing the position of the image in the listview
                hmBitmap.put("position", position);

                new CustomFunctions().decodeSampledBitmapFromStream((FileInputStream) fileInputStream, 100, 100);

                // Returning the HashMap object containing the image path and position

                return  hmBitmap;


            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.e(TAG,e.toString());
            } catch (IOException oi) {
                oi.printStackTrace();
                Log.e(TAG, oi.toString());
            }

            return null;


        }


        @Override
        protected void onPostExecute( HashMap<String, Object>  result) {

            if(result!=null) {
                  inpersonimage.setImageURI((Uri) result.get("image"));


            }
        }
    }
}
	



