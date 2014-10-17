package co.ke.masterclass.mysecurity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import co.ke.masterclass.mysecurity.custom.AlertDialogManager;
import co.ke.masterclass.mysecurity.custom.NavDrawerItem;
import co.ke.masterclass.mysecurity.custom.NavDrawerListAdapter;
import co.ke.masterclass.mysecurity.fragments.CrimerReport;
import co.ke.masterclass.mysecurity.fragments.CrimerReport.OnCrimeReportItemclick;
import co.ke.masterclass.mysecurity.fragments.DashboardSecurity;
import co.ke.masterclass.mysecurity.fragments.DashboardSecurity.OndashboardItemclick;
import co.ke.masterclass.mysecurity.fragments.ReportCrimeDash;
import co.ke.masterclass.mysecurity.fragments.ReportCrimeDash.OnReportItemclick;
import co.ke.masterclass.mysecurity.fragments.ViewPersons;
import co.ke.masterclass.mysecurity.fragments.ViewPersons.OnItemSelected;
import co.ke.masterclass.mysecurity.location.GPSTracker;
import co.ke.masterclass.mysecurity.location.Googleplaces;
import co.ke.masterclass.mysecurity.location.Place;
import co.ke.masterclass.mysecurity.location.PlaceList;
import co.ke.masterclass.mysecurity.network.ConnectivityDetector;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

public class Securitymain extends ActionBarActivity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks,OndashboardItemclick,OnReportItemclick,OnCrimeReportItemclick,OnItemSelected{

	private DashboardSecurity dash;
	private ReportCrimeDash repo;
	private CrimerReport crimerepo;
	private LocationMap mapl;
	private ViewPersons wanted;
	private static final String TAG="Securitymain";

	// drawer items 
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	
	//Slide menu items
	private String[] navMenutitles;
	private TypedArray navMenuIcons;
	private ArrayList<NavDrawerItem> navDraweritems;
	private NavDrawerListAdapter adapter;
	

	private FragmentManager fragment=getSupportFragmentManager();
	Boolean isInternetPresent = false;
	ConnectivityDetector cd;
	AlertDialogManager alert= new AlertDialogManager();
	Googleplaces googleplaces;
	PlaceList nearplaces;
	GPSTracker gps;
	ProgressDialog pDialog;
	int resultcode;
	
	ArrayList<HashMap<String,String>> placesListItems =new ArrayList<HashMap<String,String>>();
	public static String KEY_REFERENCE ="reference";
	public static String KEY_NAME="name";
	public static String KEY_VICINITY="vicinity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_security_main);
		 mTitle=mDrawerTitle=getTitle();
		try {
	          ViewConfiguration config = ViewConfiguration.get(this);
	        Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
	        if(menuKeyField != null) {
	            menuKeyField.setAccessible(true);
	            menuKeyField.setBoolean(config, false);
	        }
	    } catch (Exception ex) {
	        // Ignore
	    }
		

		 
		navMenutitles=getResources().getStringArray(R.array.nav_drawer_items);
		navMenuIcons=getResources().obtainTypedArray(R.array.nav_drawer_icons);
		mDrawerLayout=(DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList=(ListView) findViewById(R.id.list_slidermenu);
		navDraweritems=new ArrayList<NavDrawerItem>();
		
		navDraweritems.add(new NavDrawerItem(navMenutitles[0], navMenuIcons.getResourceId(0, -1)));
		navDraweritems.add(new NavDrawerItem(navMenutitles[1], navMenuIcons.getResourceId(1, -1), true, "22"));
		navDraweritems.add(new NavDrawerItem(navMenutitles[2], navMenuIcons.getResourceId(2, -1), true, "25"));
		navDraweritems.add(new NavDrawerItem(navMenutitles[3], navMenuIcons.getResourceId(3, -1)));
		navDraweritems.add(new NavDrawerItem(navMenutitles[4], navMenuIcons.getResourceId(4, -1)));
		navDraweritems.add(new NavDrawerItem(navMenutitles[5], navMenuIcons.getResourceId(5, -1)));
		navMenuIcons.recycle();
		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());
		adapter=new NavDrawerListAdapter(getApplicationContext(),navDraweritems);
		mDrawerList.setAdapter(adapter);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer,R.string.app_name, R.string.app_name)
		{
			public void onDrawerClosed(View view) {
				getSupportActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				supportInvalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getSupportActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				supportInvalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		
		if(savedInstanceState==null){
		     dash= new DashboardSecurity();
			FragmentManager fragment=getSupportFragmentManager();
			fragment.beginTransaction().add(R.id.container,dash).commit();
			
			resultcode=GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
			if(resultcode==ConnectionResult.SUCCESS){	
			cd=new ConnectivityDetector(getApplicationContext());
			isInternetPresent= cd.isConnectionToInternet();
			if(!isInternetPresent){
				
					alert.showAlertDialog(Securitymain.this, "Connectivity Error", "Please connect to the internet", false);
					return;
			}
			gps=new GPSTracker(getApplicationContext());
			if(gps.canGetlocation()){
				
				Log.d(TAG, "latitude:" + gps.getLatitude() + ", longitude: " + gps.getLongitude());
			}else {
				alert.showAlertDialog(Securitymain.this, "GPS Status",
						"Couldn't get location information. Please enable GPS",
						false);
			
				return;
				
			}
			//new LoadPlaces().execute();
		}
		}else if(resultcode==ConnectionResult.SERVICE_MISSING){
				Toast.makeText(this, "Service missing", Toast.LENGTH_SHORT).show();
		}
		else if(resultcode==ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED){
			Toast.makeText(this, "Service version update required", Toast.LENGTH_SHORT).show();
	   }
		else if(resultcode==ConnectionResult.SERVICE_DISABLED){
			Toast.makeText(this, "Service disabled", Toast.LENGTH_SHORT).show();
			}
		else if(resultcode==ConnectionResult.SERVICE_INVALID){
			Toast.makeText(this, "Service invalid", Toast.LENGTH_SHORT).show();
	}
		
	}


private class SlideMenuClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			//displayView(position);
		}
}

//	@Override
//	public void onNavigationDrawerItemSelected(int position) {
//		// update the main content by replacing fragments
//		FragmentManager fragmentManager = getSupportFragmentManager();
//		fragmentManager
//				.beginTransaction()
//				.replace(R.id.container,
//						PlaceholderFragment.newInstance(position + 1)).commit();
//	}



	public void restoreActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}
	
	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getSupportActionBar().setTitle(mTitle);
	}

	
//	@Override
//  public boolean onPrepareOptionsMenu(Menu menu) {
//		// if nav drawer is opened, hide the action items
//	boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
//	menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
//	return super.onPrepareOptionsMenu(menu);
//	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
			getMenuInflater().inflate(R.menu.security_dashboard, menu);
			restoreActionBar();
			return true;
		
	}
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action bar actions click
		switch (item.getItemId()) {
		case R.id.action_settings:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}


	@Override
	public void dashboardButtonPressed(int id) {
		// TODO Auto-generated method stub
		
		switch (id) {
		case 1:
			repo= new ReportCrimeDash();
			
			fragment.beginTransaction().remove(dash).add(R.id.container,repo).addToBackStack(null).commit();
			
			break;
		case 2:
			repo= new ReportCrimeDash();
			fragment.beginTransaction().remove(dash).add(R.id.container,repo).addToBackStack(null).commit();
			
			break;
		case 3:
			
			mapl= new LocationMap();
						
			Intent mapl = new Intent(this, LocationMap.class);
			//mapl.putExtra("user_latitude", gps.getLatitude());
			//mapl.putExtra("user_longitude", gps.getLongitude());
			//mapl.putExtra("near_places", nearplaces);
			startActivity(mapl);
			
			
			break;
		case 4:
			wanted= new ViewPersons();
			fragment.beginTransaction().remove(dash).add(R.id.container,wanted).addToBackStack(null).commit();
			
			break;

		default:
			break;
		}
	}
	public void showCrimeInterface(String crimename){
		crimerepo= new CrimerReport();
		fragment.beginTransaction().remove(repo).add(R.id.container,crimerepo).addToBackStack(null).commit();
		Toast.makeText(this, crimename, Toast.LENGTH_SHORT).show();
		
	}

	@Override
	public void ReportButtonPressed(int id,String crimename) {
		// TODO Auto-generated method stub
		switch (id) {
		case 1:
			  showCrimeInterface(crimename);
			
			break;
		case 2:
			showCrimeInterface(crimename);
			
			break;
		case 3:
			showCrimeInterface(crimename);
			
			break;
		case 4:
			showCrimeInterface(crimename);
			
			break;
		case 5:
			showCrimeInterface(crimename);
			
			break;
		case 6:
			showCrimeInterface(crimename);
			
			break;
		case 7:
			showCrimeInterface(crimename);
			
			break;
		case 8:
			showCrimeInterface(crimename);
			
			break;
		case 9:
			showCrimeInterface(crimename);
			
			break;
		case 10:
			showCrimeInterface(crimename);
			
			break;
		case 11:
			showCrimeInterface(crimename);
			
			break;
		case 12:
			showCrimeInterface(crimename);
			
			break;
		case 13:
			showCrimeInterface(crimename);
			
			break;
		

		default:
			break;
		}
	}

	@Override
	public void ButtonPressed(int id) {
		// TODO Auto-generated method stub
		switch (id) {
		case 1:
			Toast.makeText(this,"audio clicked", Toast.LENGTH_SHORT).show();
			break;
		case 2:
			Toast.makeText(this,"pic clicked", Toast.LENGTH_SHORT).show();
			break;
		case 3:
			Toast.makeText(this,"video clicked", Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}
	}

	@Override
	public void OnselectedItem(int id, String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// TODO Auto-generated method stub
		
	}
	class LoadPlaces extends AsyncTask<String, String, String> {

		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Securitymain.this);
			pDialog.setMessage(Html.fromHtml("<b>Search</b><br/>Loading Places..."));
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

	
		protected String doInBackground(String... args) {
			// creating Places class object
			googleplaces = new Googleplaces();
			
			try {
				
				String types = "police|hospital"; // Listing places only police stations, hosipital
				
				// Radius in meters - increase this value if you don't find any places
				double radius = 1000; // 1000 meters 
				
				// get nearest places
				nearplaces = googleplaces.search(gps.getLatitude(),
						gps.getLongitude(), radius, types);
			
				Log.d(TAG,gps.getLatitude() + "  "  + gps.getLongitude() + "    " + types );
				

			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}


		protected void onPostExecute(String file_url) {
			
			pDialog.dismiss();
			
			runOnUiThread(new Runnable() {
				public void run() {
				
					// Get json response status
					String status = nearplaces.status;
					
					// Check for all possible status
					if(status.equals("OK")){
						// Successfully got places details
						if (nearplaces.results != null) {
							// loop through each place
							for (Place p : nearplaces.results) {
								HashMap<String, String> map = new HashMap<String, String>();
								
								// Place reference won't display in listview - it will be hidden
								// Place reference is used to get "place full details"
								map.put(KEY_REFERENCE, p.reference);
								
						
								
								// Place name
								map.put(KEY_NAME, p.name);
								
								Log.d(TAG,"place name" + p.name + " place ref" + p.reference );
								// adding HashMap to ArrayList
								placesListItems.add(map);
							}
//							// list adapter
//							ListAdapter adafromHtmlpter = new SimpleAdapter(MainActivity.this, placesListItems,
//					                R.layout.list_item,
//					                new String[] { KEY_REFERENCE, KEY_NAME}, new int[] {
//					                        R.id.reference, R.id.name });
//							
//							// Adding data into listview
//							lv.setAdapter(adapter);
						}
					}
					else if(status.equals("ZERO_RESULTS")){
						// Zero results found
						alert.showAlertDialog(Securitymain.this, "Near Places",
								"Sorry no places found. Try to change the types of places",
								false);
					}
					else if(status.equals("UNKNOWN_ERROR"))
					{
						alert.showAlertDialog(Securitymain.this, "Places Error",
								"Sorry unknown error occured.",
								false);
					}
					else if(status.equals("OVER_QUERY_LIMIT"))
					{
						alert.showAlertDialog(Securitymain.this, "Places Error",
								"Sorry query limit to google places is reached",
								false);
					}
					else if(status.equals("REQUEST_DENIED"))
					{
						alert.showAlertDialog(Securitymain.this, "Places Error",
								"Sorry error occured. Request is denied",
								false);
					}
					else if(status.equals("INVALID_REQUEST"))
					{
						alert.showAlertDialog(Securitymain.this, "Places Error",
								"Sorry error occured. Invalid Request",
								false);
					}
					else
					{
						alert.showAlertDialog(Securitymain.this, "Places Error",
								"Sorry error occured.",
								false);
					}
				}
			});

		}

	}

}
