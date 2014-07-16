package com.telosoftapps.mtokamanager;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.telosoftapps.mtokamanager.R;
import com.telosoftapps.mtokamanager.customs.Constants;
import com.telosoftapps.mtokamanager.customs.SessionManager;
import com.telosoftapps.mtokamanager.fragments.AddAsset;
import com.telosoftapps.mtokamanager.fragments.AddGroupFragment;
import com.telosoftapps.mtokamanager.fragments.AssetManagerFragment;
import com.telosoftapps.mtokamanager.fragments.MakeReport;
import com.telosoftapps.mtokamanager.fragments.NewInchargeFragment;
import com.telosoftapps.mtokamanager.fragments.ViewReport;
import com.telosoftapps.mtokamanager.fragments.AddAsset.onAddAssetclick;
import com.telosoftapps.mtokamanager.fragments.AddGroupFragment.onSubmitListener;
import com.telosoftapps.mtokamanager.fragments.AssetManagerFragment.onAddAssetButtonclick;
import com.telosoftapps.mtokamanager.fragments.MakeReport.onMakeReportClicked;
import com.telosoftapps.mtokamanager.fragments.NewInchargeFragment.onSubmitListenerIncharge;
import com.telosoftapps.mtokamanager.functions.Functions;
import com.telosoftapps.mtokamanager.network.BackGroundService;
import com.telosoftapps.mtokamanager.network.SqliteDBhandler;
import com.telosoftapps.mtokamanager.threads.DoInBackground;

@SuppressLint("NewApi")
public class MtokaDashboard extends ActionBarActivity implements onSubmitListener,onAddAssetButtonclick,onSubmitListenerIncharge,onAddAssetclick,onMakeReportClicked{
	      private DrawerLayout mDrawerLayout;
	      private ListView mDrawerList;
	      private ActionBarDrawerToggle mDrawerToggle;
	      private CharSequence mDrawerTitle;
	      private CharSequence mTitle;
	      CustomDrawerAdapter adapter;
	      DialogFragment dialogFragment;
	      private static String TAG="MtokaDashboard tag";
	      String getAssetid="";
	      private Handler serviceHandler;
 String getUserid="";
 String tel="";
 List<DrawerItem> dataList;
 Intent viewIntent;
 DataReceiver mReceiver;
 IntentFilter mFilter;
 SqliteDBhandler db =new SqliteDBhandler(this);
 SessionManager session;
 // Shared Preferences
SharedPreferences pref;
// Editor for Shared preferences
Editor editor;


@Override
protected void onResume() {
// TODO Auto-generated method stub
super.onResume();

}

@Override
protected void onCreate(Bundle savedInstanceState) {
	            super.onCreate(savedInstanceState);
	            setContentView(R.layout.asset_manager);
	            if (android.os.Build.VERSION.SDK_INT > 9) {
	                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
	                        .permitAll().build();
	                StrictMode.setThreadPolicy(policy);
	            }
	           
	           

	            // Initializing
	            dataList = new ArrayList<DrawerItem>();
	            mTitle = mDrawerTitle = getTitle();
	            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
	            mDrawerList = (ListView) findViewById(R.id.left_drawer);
	 
	            mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
	                        GravityCompat.START);
	 
	            // Add Drawer Item to dataList
	           
	            dataList.add(new DrawerItem("  Add Group", R.drawable.ic_action_group));
	            dataList.add(new DrawerItem("  Add Asset Incharge", R.drawable.ic_action_group));
	            dataList.add(new DrawerItem("  Logout", R.drawable.ic_logout));
	            dataList.add(new DrawerItem("  About", R.drawable.ic_action_about));
	            dataList.add(new DrawerItem("  Settings", R.drawable.ic_action_settings));
	            dataList.add(new DrawerItem("  Help", R.drawable.ic_action_help));
	           
	 
	            adapter = new CustomDrawerAdapter(this, R.layout.custom_drawer_item,
	                        dataList);
	 
	            mDrawerList.setAdapter(adapter);
	 
	            mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
	            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	            getSupportActionBar().setHomeButtonEnabled(true);;
	 
	            mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
	                        R.drawable.ic_drawer, R.string.drawer_open,
	                        R.string.drawer_close) {
	                  public void onDrawerClosed(View view) {
	                       getSupportActionBar().setTitle(mTitle);
	                       supportInvalidateOptionsMenu(); // creates call to
	                                                                  // onPrepareOptionsMenu()
	                  }
	 
	                  public void onDrawerOpened(View drawerView) {
	                	  getSupportActionBar().setTitle(mDrawerTitle);
	                        supportInvalidateOptionsMenu(); // creates call to
	                                                                  // onPrepareOptionsMenu()
	                  }
	            };
	 
	            mDrawerLayout.setDrawerListener(mDrawerToggle);
	 
	            if (savedInstanceState == null) {
	                  //SelectItem(0);
	            	AssetManagerFragment asset =new AssetManagerFragment();
	            	getSupportFragmentManager().beginTransaction().add(R.id.content_frame, asset).commit();
	            	
	            }
	            
	            // doing periodic task 
	            
	            serviceHandler=new Handler();
	            serviceHandler.postDelayed(doRunService, Constants.PERIODIC_EVENT_TIMEOUT);
	 
	      }
// background Runnable 
private Runnable doRunService = new Runnable()
{
    public void run() 
    {
    	
	
        
    	Intent service =new Intent(MtokaDashboard.this, BackGroundService.class);
		startService(service);
		serviceHandler.postDelayed(doRunService, Constants.PERIODIC_EVENT_TIMEOUT);
		
    }
};

	      
public String getAssetID(){
	return getAssetid;
}




		private void showAddGroupDialog() {
	          FragmentManager fm = getSupportFragmentManager();
	          AddGroupFragment dialogFragment =new  AddGroupFragment();
	          dialogFragment.show(fm, "fragment_add_group");
	      }
		private void showInchargeDialog() {
	          FragmentManager fm = getSupportFragmentManager();
	          NewInchargeFragment dialogFragment =new  NewInchargeFragment();
	          dialogFragment.show(fm, "fragment_new_incharge");
	      }
	 
	      @Override
	      public boolean onCreateOptionsMenu(Menu menu) {
	            // Inflate the menu; this adds items to the action bar if it is present.
	            getMenuInflater().inflate(R.menu.global, menu);
	            return true;
	      }
	 
	      public void SelectItem(int possition) {
	    	 
	    	  switch (possition) {
	    	  case 0:
	    		     showAddGroupDialog();
				break;
	    	  case 1:
	    		     showInchargeDialog();
		break;
	    	  case 2:
	    		  //	session.logoutUser();
	    		  SharedPreferences settings = getSharedPreferences(Constants.PREFS_NAME, 0);
				   SharedPreferences.Editor editor = settings.edit();
				    editor.putString(Constants.IS_LOGGED_IN, Constants.LOGGED_OUT);
				    editor.commit();
	    			
	    			// After logout redirect user to Loing Activity
	    		Intent i = new Intent(this, Login.class);
	    			// Closing all the Activities
	    		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	    		// Add new Flag to start new Activity
	    		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    		// Staring Login Activity
	    			startActivity(i);
	    		   finish();
	 				  
	    		  
					 	 break;
				
			default:
				break;
			}
	         
	 
	            mDrawerList.setItemChecked(possition, true);
	            setTitle(dataList.get(possition).getItemName());
	            mDrawerLayout.closeDrawer(mDrawerList);
	 
	      }
	 
	      @Override
	      public void setTitle(CharSequence title) {
	            mTitle = title;
	            getSupportActionBar().setTitle(mTitle);
	      }
	 
	      @Override
	      protected void onPostCreate(Bundle savedInstanceState) {
	            super.onPostCreate(savedInstanceState);
	            // Sync the toggle state after onRestoreInstanceState has occurred.
	            mDrawerToggle.syncState();
	      }
	 
	      @Override
	      public void onConfigurationChanged(Configuration newConfig) {
	            super.onConfigurationChanged(newConfig);
	            // Pass any configuration change to the drawer toggles
	            mDrawerToggle.onConfigurationChanged(newConfig);
	      }
	 
	      @Override
	      public boolean onOptionsItemSelected(MenuItem item) {
	            // The action bar home/up action should open or close the drawer.
	            // ActionBarDrawerToggle will take care of this.
	            if (mDrawerToggle.onOptionsItemSelected(item)) {
	                  return true;
	            }
	 
	            return false;
	      }
	 
	        private class DrawerItemClickListener implements
	                  ListView.OnItemClickListener {
	            @Override
	            public void onItemClick(AdapterView<?> parent, View view, int position,
	                        long id) {
	                  SelectItem(position);
	 
	            }
	      }

			@Override
			public void setOnSubmitListener(DialogFragment dialog, String name) {
				// TODO Auto-generated method stub
				if(name== null){
					dialog.dismiss();
				}else{
					//Log.d(TAG,"The recieved group name is  " + name);
					Status statusCode =new DoInBackground(this,"Adding group.... ",true).execute(new Functions().newGroup(name)).getStatus();
					if(statusCode==Status.FINISHED){
						
						dialog.dismiss();
					}
					
				}
				
				
			}


			@Override
			public void setOnAddAssetbuttonListener(int id) {
				// TODO Auto-generated method stub
				if(id==1){
					AddAsset asset =new AddAsset();
					FragmentManager fm = getSupportFragmentManager();
					fm.beginTransaction().replace(R.id.content_frame, asset).addToBackStack(null).commit();
			  
			   	}
				
			}


			@Override
			public void setOnSubmitListener(DialogFragment dialog, String name,
					String username) {
				// TODO Auto-generated method stub
				if(name== null){
					dialog.dismiss();
				}else{
					
					
					//.d(TAG,"The recieved incharge name is  " + name);
					//Log.d(TAG,"The recieved incharge username is  " + username);
					Status statusCode =new DoInBackground(this,"Adding Asset Incharge.... ",true).execute(new Functions().newIncharge(name,username,tel)).getStatus();
					if(statusCode==Status.FINISHED){
						
						dialog.dismiss();
					}
					
				}
				
			}


			@Override
			public void setOnAddAssetclick(String assetid, String capacity,
					String model, String typeuse, String category,
					String groupname, String incharge) {
				// TODO Auto-generated method stub
				SharedPreferences telno = getSharedPreferences(Constants.PREFS_USERID, 0);
				SharedPreferences settings = getSharedPreferences(Constants.PREFS_ASSET, 0);
				
				  if(telno.getString(Constants.KEY_TEL, null)!=null){
					 tel= telno.getString(Constants.KEY_TEL, null);
				  }
				  if(settings.getString(Constants.KEY_TEL, null)!=null){
						 tel= settings.getString(Constants.KEY_TEL, null);
				}
				//Log.d(TAG,"Intent value of userid at mtokaDash" + tel);
				
				new DoInBackground(this,"Adding Asset.... ",true)
				.execute(new Functions().
				registerAsset(assetid,tel, model, category, groupname, capacity, typeuse, incharge));
				
			}


			@Override
			public void onAssetMenuItemSelected(String assetId, int menuItem) {
				// TODO Auto-generated method stub
				getAssetid=assetId;
				//Log.d(TAG,"Asset id otained from Asset manager is  " + getAssetid);
				FragmentManager fragment=null;
				switch (menuItem) {
				case 0:
					
						MakeReport report = new MakeReport();
						fragment=getSupportFragmentManager();
						fragment.beginTransaction().replace(R.id.content_frame, report).addToBackStack(null).commit();
					break;
				case 1:
						//viewIntent.putParcelableArrayListExtra(Constants.EXTRA_PARAMS,new Functions().getPerfomance(getAssetID(),"9"));
						//startService(viewIntent);
					 SharedPreferences settings = getSharedPreferences(Constants.PREFS_ASSET, 0);
					   SharedPreferences.Editor editor = settings.edit();
					    editor.putString(Constants.KEY_ASSETID, assetId);
					    editor.commit();
					    
				   // Log.d(TAG,"id at onshare is  " + assetId);
				   	ViewReport viewasset = new ViewReport();
					fragment=getSupportFragmentManager();
					fragment.beginTransaction().replace(R.id.content_frame, viewasset).addToBackStack(null).commit();
					
				break;
				case 2:
					
					break;
				case 3:
						
						
						
					break;

				default:
					break;
				}
				
			}


			@Override
			public void onMakeReportSubmitted(String stAmount, String cRush,
					String cNomal, String fuel, String maintanance,
					String parking, String insurance, String others,
					String tRush, String tNom) {
				// TODO Auto-generated method stub
				
				new DoInBackground(this,"Saving Report.... ",true)
				.execute(new Functions().
				MakeReport(getAssetid, stAmount,cRush, cNomal, fuel, 
						maintanance, parking, insurance, others, tRush, tNom));
				
			}
			// Defining a BroadcastReceiver
			private class DataReceiver extends BroadcastReceiver{
			

				@Override
				public void onReceive(Context context, Intent intent) {
					// TODO Auto-generated method stub
					
				}		
			}

		@Override
		protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Constants.running = false;
		serviceHandler.removeCallbacks(doRunService);
		}
		
	 
	}
	
	
	


