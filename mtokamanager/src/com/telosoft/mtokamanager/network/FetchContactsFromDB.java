package com.telosoft.mtokamanager.network;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.telosoft.mtokamanager.R;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FetchContactsFromDB extends Activity {
	public TextView outputText;
	private String[] arrayA;
	String URL = "http://10.0.2.2/mtoka_api/index.php?tag=register_group&group_name=name";
	String fullname, phonenumber, bloodgroups;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addgroup_fragment);
		outputText = new TextView(getApplicationContext());
		Button go = (Button) findViewById(R.id.btnaddgroup);
		go.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new GetDetails().execute();	
			}
		});
		//fetchContacts();
	
		//get details online
		
		
	}
	
	ArrayList<String> list2 = new ArrayList<String>();
	private class GetDetails extends AsyncTask<Void, Void, Void> {
		//String URL = getResources().getString(R.string.get_specialty_url);
		
		private String[] lv_arr;
		@Override
		protected void onPreExecute() {
			
			super.onPreExecute();
		}
	
		@Override
		
		
		protected Void doInBackground(Void... arg0) {
			
			 		
			try
			{
			JSONObject json= JSONFunctions.getJSONfromURL(URL);
			
			Log.i("JSON OBJECT1", json.toString());
				
			JSONArray JA=new JSONArray();
				JA = json.getJSONArray("PAYLOAD");
			
			for(int i=0;i<JA.length();i++)
			{
		    JSONObject e =  JA.getJSONObject(i);   
		    Log.i("JSON OBJECT", e.toString());
	 
		    }
		
			
			}
			catch(Exception e)
			{
				Log.e("Fail", e.toString());
			}       
		
			return null;
			
			}
		@Override
		 protected void onPostExecute(Void result) {
			//Display the Contacts that are found in both db and PhoneContacts
			
	
			
	     }
	}
	ArrayList<String> list ;
	String phoneNumber = null;
	StringBuffer b;
	public void fetchContacts() {

		
		String email = null;

		Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
		String _ID = ContactsContract.Contacts._ID;
		String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
		String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;

		Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
		String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
		String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;

		Uri EmailCONTENT_URI =  ContactsContract.CommonDataKinds.Email.CONTENT_URI;
		String EmailCONTACT_ID = ContactsContract.CommonDataKinds.Email.CONTACT_ID;
		String DATA = ContactsContract.CommonDataKinds.Email.DATA;

		StringBuffer output = new StringBuffer();

		ContentResolver contentResolver = getContentResolver();

		Cursor cursor = contentResolver.query(CONTENT_URI, null,null, null, null);	

		// Loop for every contact in the phone
		if (cursor.getCount() > 0) {

			while (cursor.moveToNext()) {

				String contact_id = cursor.getString(cursor.getColumnIndex( _ID ));
				String name = cursor.getString(cursor.getColumnIndex( DISPLAY_NAME ));

				int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex( HAS_PHONE_NUMBER )));

				if (hasPhoneNumber > 0) {

					//output.append("\n First Name:" + name);

					// Query and loop for every phone number of the contact
					Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?", new String[] { contact_id }, null);
					 list = new ArrayList<String>();
					while (phoneCursor.moveToNext()) {
						phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
						//output.append("\n Phone number:" + phoneNumber);
						list.add(phoneNumber);//add contacts to array list 1
						
						
					}
					
					//get both arrayslist as string arrays
					//arrayA = (String[])list.toArray(new String[0]);
					
					
					
//					String [] arrayB = (String[])list2.toArray(new String[0]);
//			
//					//compare them
//					//and comparing each element to each element in arrayB
//					//switch variable used to indicate whether a match was found
//					boolean foundSwitch = false;
//
//					//outer loop for all the elements in arrayA[i]
//					for(int i = 0; i < arrayA.length; i++)
//					{
//					//inner loop for all the elements in arrayB[j]
//					for (int j = 0; j < arrayB.length;j++)
//					{
//					//compare arrayA to arrayB and output results
//					if( arrayA[i].equals(arrayB[j]))
//					{
//					foundSwitch = true;
//					Toast.makeText(FetchContactsFromDB.this, "Array element"+arrayA[i]+" was found in Array B", Toast.LENGTH_SHORT).show();
//					//list3.add(arrayA[i]);
//					output.append("Number: "+arrayA[i]+"\n\n");//add match to strign buffer
//					}
//					
//					
//					}
//					if (foundSwitch == false)
//					{
//						Toast.makeText(FetchContactsFromDB.this, "Array element"+arrayA[i]+" was Not found in Array B", Toast.LENGTH_SHORT).show();
//						
//						//System.out.println( �arrayA element \�" + arrayA[i] + �\� was Not found in arrayB� );
//					}
//					//set foundSwitch bool back to false
//					foundSwitch = false;
//					}
					
		
					
					phoneCursor.close();

					// Query and loop for every email of the contact
					Cursor emailCursor = contentResolver.query(EmailCONTENT_URI,	null, EmailCONTACT_ID+ " = ?", new String[] { contact_id }, null);

					while (emailCursor.moveToNext()) {

						email = emailCursor.getString(emailCursor.getColumnIndex(DATA));

						//output.append("\nEmail:" + email);

					}

					emailCursor.close();
				}

				//output.append("\n");
			}
			
		
			
			//add buffer to the textview
		}

	}

}