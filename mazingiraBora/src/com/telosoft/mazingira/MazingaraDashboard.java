package com.telosoft.mazingira;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
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

import com.telosoft.mazingira.customs.Constants;
import com.telosoft.mazingira.customs.IndividualOrgList;
import com.telosoft.mazingira.fragments.AddOrg;
import com.telosoft.mazingira.fragments.AddOrg.onAddOrgclick;
import com.telosoft.mazingira.fragments.MakeReport;
import com.telosoft.mazingira.fragments.MakeReport.onMakeReportClicked;
import com.telosoft.mazingira.fragments.OrgManagerFragment;
import com.telosoft.mazingira.fragments.OrgManagerFragment.onAddOrgButtonclick;
import com.telosoft.mazingira.fragments.SignIN;
import com.telosoft.mazingira.fragments.SignIN.onLoginData;
import com.telosoft.mazingira.fragments.SignUP;
import com.telosoft.mazingira.network.BackGroundService;
import com.telosoft.mazingira.network.SqliteDBhandler;

@SuppressLint("NewApi")
public class MazingaraDashboard extends ActionBarActivity implements onAddOrgButtonclick,onMakeReportClicked ,onAddOrgclick,onLoginData{
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
 FragmentManager fragment=null;

 // Shared Preferences
SharedPreferences pref;
// Editor for Shared preferences
Editor editor;

List<IndividualOrgList> listdata= new ArrayList<IndividualOrgList>();
List<IndividualOrgList>  assetInfo= new ArrayList<IndividualOrgList>();
List<IndividualOrgList> monthlydata= new ArrayList<IndividualOrgList>();
List<HashMap<String,String>> aList ;
List<HashMap<String,String>> datList;


@Override
protected void onResume() {
// TODO Auto-generated method stub
super.onResume();
Bundle login =new Bundle();
tel=login.getString(Constants.KEY_TEL);
Log.d(TAG,"bundled tel on resume "+ tel);

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
	           
	            dataList.add(new DrawerItem("  Send tip", R.drawable.ic_action_labels));
	            dataList.add(new DrawerItem("  Login", R.drawable.ic_action_labels));	            
	            dataList.add(new DrawerItem("  About", R.drawable.ic_action_about));
	            dataList.add(new DrawerItem("  Settings", R.drawable.ic_action_settings));
	            dataList.add(new DrawerItem("  Help", R.drawable.ic_action_help));
	            dataList.add(new DrawerItem("  Logout", R.drawable.ic_logout));
	           
	 
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
	            	
	            	OrgManagerFragment org =new OrgManagerFragment();
	            	getSupportFragmentManager().beginTransaction().add(R.id.content_frame, org).commit();
	            	
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
    	
	
        
    	Intent service =new Intent(MazingaraDashboard.this, BackGroundService.class);
		startService(service);
		serviceHandler.postDelayed(doRunService, Constants.PERIODIC_EVENT_TIMEOUT);
		
    }
};

	      
public String getAssetID(){
	return getAssetid;
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

					MakeReport report = new MakeReport();
					fragment=getSupportFragmentManager();
					fragment.beginTransaction().replace(R.id.content_frame, report).addToBackStack(null).commit();
				break;
	    	  case 1:
	    		    SignIN login = new SignIN();
					fragment=getSupportFragmentManager();
					fragment.beginTransaction().replace(R.id.content_frame, login).addToBackStack(null).commit();
	    		
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
			public void  onOrgMenuItemSelected(String assetId, int menuItem) {
				// TODO Auto-generated method stub
				getAssetid=assetId;
				//Log.d(TAG,"Asset id otained from Asset manager is  " + getAssetid);
				
				switch (menuItem) {
				case 0:
					
					break;
				case 1:
					
					    
				  
					
				break;
				case 2:
				
					break;
				case 3:
					//share 
					
					
					break;
                 case 4:
					//edit
                	
                	
					break;
				case 5:
						
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
			 
						// set title
						alertDialogBuilder.setTitle("Are u sure u want to delete");
			 
						// set dialog message
						alertDialogBuilder.setMessage(R.string.deletemsg);
						alertDialogBuilder.setCancelable(false);
						alertDialogBuilder.setIcon(R.drawable.ic_delete);
						alertDialogBuilder.setPositiveButton("Sure",new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,int id) {
									
									// what to do
									
								}
							  })
							.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,int id) {
									// if this button is clicked, just close
									// the dialog box and do nothing
									dialog.cancel();
								}
							});
			 
							// create alert dialog
							AlertDialog alertDialog = alertDialogBuilder.create();
			 
							// show it
							alertDialog.show();
					
				
						
					break;

				         default:
				        	 
					break;
				}
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

		@Override
		public void setOnAddOrgbuttonListener(int id) {
			// TODO Auto-generated method stub

			AddOrg report = new AddOrg();
			fragment=getSupportFragmentManager();
			fragment.beginTransaction().replace(R.id.content_frame, report).addToBackStack(null).commit();
		}

		public void onMakeReportSubmitted(String description, String audio,
				String image, String video) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void setOnAddOrgclick(String id, String name, String mem,
				String subsription, String speciality) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void loadsignup(boolean state) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void passLoginData(String phone, String password) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void feedBackMsg(int id) {
			// TODO Auto-generated method stub
			switch (id) {
			case 1:
				SignUP sin = new SignUP();
				fragment=getSupportFragmentManager();
				fragment.beginTransaction().replace(R.id.content_frame, sin).addToBackStack(null).commit();
				break;
			case 2:
				SignIN log = new SignIN();
				fragment=getSupportFragmentManager();
				fragment.beginTransaction().replace(R.id.content_frame, log).addToBackStack(null).commit();

			default:
				break;
			}
		
		}

	

		
		
		
	 
	}
	
	
	


