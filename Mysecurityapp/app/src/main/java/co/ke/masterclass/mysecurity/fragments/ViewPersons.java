package co.ke.masterclass.mysecurity.fragments;


import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.ArrayAdapter;
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
import co.ke.masterclass.mysecurity.functions.CustomFunctions;
import co.ke.masterclass.mysecurity.network.SqliteDbHandler;


public class ViewPersons extends Fragment implements OnClickListener{
    View view;
    private ListView listpersons;
    private SimpleAdapter adapter;
    private int id;
    String TAG = "ViewPersons";

    SqliteDbHandler db;
    List<CustomSeterClass> personData=new ArrayList<CustomSeterClass>();
    Securitymain sec = new Securitymain();
    int position;

    //instantiating interfaces
    private OnItemSelected listener;



    //interfaces
    public interface OnItemSelected {
        public void OnselectedItem(int id, String name);
        public void ListviewItemClicked(int id);
    }


    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        try {
            listener = (OnItemSelected) activity;
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
        view = inflater.inflate(R.layout.viewmissingperson, container, false);
        listpersons = (ListView) view.findViewById(R.id.listViewpersons);
        db = new SqliteDbHandler(getActivity());

        personData = db.getPersons(1);
        Log.e(TAG, "" + personData.size() + "data" + personData.toArray());

         ArrayAdapter<CustomSeterClass>  adapter = new PersonCustomAdapter(getActivity(),R.layout.custompersonslistitem,personData);

            listpersons.setAdapter(adapter);

            listpersons.setDivider(new ColorDrawable(0xffffffff));
            listpersons.setDividerHeight(1);
            adapter.notifyDataSetChanged();
        return view;
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


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.btntheft:
                id = 1;
                listener.OnselectedItem(id, "Theft");

                break;
            case R.id.btnaccident:
                id = 2;
                listener.OnselectedItem(id, "Accident");
                break;
            case R.id.btnassult:
                id = 3;
                listener.OnselectedItem(id, "Assault");
                break;
            case R.id.btndistubance:
                id = 4;
                listener.OnselectedItem(id, "Disturbance");
                break;
            case R.id.btndrugs:
                id = 5;
                listener.OnselectedItem(id, "Drug Abuse");
                break;
            case R.id.btnharrasment:
                id = 6;
                listener.OnselectedItem(id, "Harrasement");
                break;
            case R.id.btnmedical:
                id = 7;
                listener.OnselectedItem(id, "Mental health");
                break;
            case R.id.btnmissing:
                id = 8;
                listener.OnselectedItem(id, "Missing person");
                break;
            case R.id.btncorruption:
                id = 9;
                listener.OnselectedItem(id, "Suggestion");
                break;
            case R.id.btnsuspecious:
                id = 10;
                listener.OnselectedItem(id, "Suspecious Activity");
                break;
            case R.id.btnvandalism:
                id = 11;
                listener.OnselectedItem(id, "Vandalism");
                break;
            case R.id.btnwanted:
                id = 12;
                listener.OnselectedItem(id, "Wanted person");
                break;
            case R.id.btnproperty:
                id = 13;
                listener.OnselectedItem(id, "Other");
                break;
            default:
                break;
        }
    }







    class ImageTask extends AsyncTask<ViewHolder, Void, ViewHolder> {

        private static final int IO_BUFFER_SIZE = 4 * 1024;


        @Override
        protected ViewHolder doInBackground(ViewHolder... viholder) {
            InputStream iStream = null;
           ViewHolder viewHolder=viholder[0];
           String imgUrl = viewHolder.url;
                    //(String) hm[0].get("image_path");
            position =viewHolder.position;
                    // (Integer) hm[0].get("position");


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
                outputStream.flush();
                outputStream.close();
                InputStream fileInputStream = new FileInputStream(cacheFile);

            new CustomFunctions().decodeSampledBitmapFromStream((FileInputStream) fileInputStream, 100, 100);



                viewHolder.path=Uri.fromFile(cacheFile);


                fileInputStream.close();



                // Returning the HashMap object containing the image path and position
                Log.e(TAG, cacheFile.getPath().toString());
                return viewHolder;


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
        protected void onPostExecute(ViewHolder result) {

            if(result!=null) {



                // Getting adapter of the listview
                ArrayAdapter<CustomSeterClass> adapters = (ArrayAdapter<CustomSeterClass>) listpersons.getAdapter();

                // Getting the hashmap object at the specified position of the listview
                CustomSeterClass Cdata =  adapters.getItem(position);
                Cdata.setPath(result.path);
                result.imageView.setImageURI(Cdata.getPath());
                 adapters.notifyDataSetChanged();

            }else{
              //  Toast.makeText(getActivity(), ConstantsClass.INTERNET_MSG,Toast.LENGTH_LONG).show();
            }
        }
    }
    private static class ViewHolder {
        int position;
        ImageView imageView;
        String url;
       Uri path;
        int id;
        TextView tvdes;
        TextView tvname;

    }
    private class  registerClickcallback implements View.OnClickListener{
        int mposition;
        int id;
        Uri imag;
        public  registerClickcallback(ViewHolder viewHolder){

            this.mposition=viewHolder.position;
            id=viewHolder.id;
            imag=viewHolder.path;
        }

        @Override
        public void onClick(View view) {
            CustomSeterClass itemclicked = personData.get(mposition) ;
            listener.ListviewItemClicked(id);

        }


    }
    private class PersonCustomAdapter extends ArrayAdapter<CustomSeterClass> {
        public PersonCustomAdapter(Context context, int resource, List<CustomSeterClass> objects) {
            super(context, resource, objects);
        }
        @Override
        public int getCount() {
            if(personData.size()<=0) {
                return 1;
            }else {
                return personData.size();
            }
        }

        @Override
        public CustomSeterClass getItem(int position) {
            return super.getItem(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
           View pView= convertView;
            ViewHolder viewHolder = null;

            if(pView==null){
                pView = getLayoutInflater(null).inflate(R.layout.custompersonslistitem,parent,false);
            }
            try {
            CustomSeterClass person = personData.get(position);
              viewHolder = new ViewHolder();
            viewHolder.tvname= (TextView) pView.findViewById(R.id.txtvpersonsname);
              viewHolder.tvname.setText(" " + person.getName());
              viewHolder.tvdes = (TextView) pView.findViewById(R.id.txtvwdescriptn);

                viewHolder.id=person.getId();
               viewHolder.tvdes.setText(" " + person.getDescription());

                viewHolder.imageView = (ImageView) pView.findViewById(R.id.imgvpersonpic);
                viewHolder.position=position;
                viewHolder.url="http://myroad.co.ke/mysecurity/uploads/persons/wanted/" + person.getPhoto();

               ImageTask imageLoaderTask = new ImageTask();
              //  String full_url = "http://myroad.co.ke/mysecurity/uploads/persons/wanted/" + imgUrl;

                imageLoaderTask.execute(viewHolder);


                pView.setOnClickListener(new registerClickcallback(viewHolder));
        }catch (IndexOutOfBoundsException io){
            io.printStackTrace();
        }

            return pView;
        }
    }
}
	



