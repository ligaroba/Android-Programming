package co.ke.masterclass.mysecurity.functions;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.android.gms.internal.hm;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import co.ke.masterclass.mysecurity.R;

/**
 * Created by root on 9/28/14.
 */

public class CustomFunctions {
    // Receives a JSONObject and returns a list
    public List<HashMap<String, Object>> parse(JSONObject jObject) throws JSONException {

        JSONArray jPerson;
        jPerson = jObject.getJSONArray("person");

        // Invoking getCountries with the array of json object
        // where each json object represent a country


        return getPersons(jPerson);


    }

    private List<HashMap<String, Object>> getPersons(JSONArray jPerson) {
        int personCount = jPerson.length();
        List<HashMap<String, Object>> personList = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> person = new HashMap<String, Object>();

        Log.e("jPerson" ,  jPerson .toString());

        // Taking each country, parses and adds to list object
        for (int i = 0; i < personCount; i++) {
            try {
                // Call getCountry with country JSON object to parse the country
              person = getPerson((JSONObject) jPerson.get(i));
                personList.add(person);


                Log.e(" personList;" ,  personList.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return personList;
    }

    // Parsing the Country JSON object
    private HashMap<String, Object> getPerson(JSONObject jCountry){

        HashMap<String, Object> persondetails = new HashMap<String, Object>();
        String Name = "";
        String image="";
        String details = "";
        String dateReported = "";
        String gender = "";


        try {
            Name = jCountry.getString("names");
            image = jCountry.getString("photo");
            details = jCountry.getString("description");
            dateReported = jCountry.getString("dateReported");
            gender=jCountry.getString("gender");



            persondetails.put("name", persondetails);
            persondetails.put("image", R.drawable.ic_person);
            persondetails.put("image_path", image);
            persondetails.put("details", details);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return persondetails;
    }

    public File getDataFolder(Context context) {

        File dataDir = null;

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

            dataDir = new File(Environment.getExternalStorageDirectory()+"/Mysecurity/Persons");

            if(!dataDir.isDirectory()) {

                dataDir.mkdirs();

            }
        }



        if(!dataDir.isDirectory()) {

            dataDir = context.getFilesDir();

        }



        return dataDir;

    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromStream(FileInputStream fileInputStream,int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
           BitmapFactory.decodeStream(fileInputStream, null, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(fileInputStream, null, options);
    }




    public File getCacheFolder(Context context) {

        File cacheDir = null;

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

            cacheDir = new File(Environment.getExternalStorageDirectory()+"/Mysecurity");

            if(!cacheDir.isDirectory()) {

                cacheDir.mkdirs();

            }

        }



        if(!cacheDir.isDirectory()) {

            cacheDir = context.getCacheDir(); //get system cache folder

        }



        return cacheDir;

    }



}


