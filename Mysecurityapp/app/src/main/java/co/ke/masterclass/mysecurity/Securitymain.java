package co.ke.masterclass.mysecurity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import java.io.File;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import co.ke.masterclass.mysecurity.custom.AlertDialogManager;
import co.ke.masterclass.mysecurity.custom.ConstantsClass;
import co.ke.masterclass.mysecurity.custom.NavDrawerItem;
import co.ke.masterclass.mysecurity.custom.NavDrawerListAdapter;
import co.ke.masterclass.mysecurity.fragments.CrimerReport;
import co.ke.masterclass.mysecurity.fragments.CrimerReport.OnCrimeReportItemclick;
import co.ke.masterclass.mysecurity.fragments.DashboardSecurity;
import co.ke.masterclass.mysecurity.fragments.DashboardSecurity.OndashboardItemclick;
import co.ke.masterclass.mysecurity.fragments.EmergencyContacts;
import co.ke.masterclass.mysecurity.fragments.Gotoweb;
import co.ke.masterclass.mysecurity.fragments.MissingPersonReport;
import co.ke.masterclass.mysecurity.fragments.PersonsDetails;
import co.ke.masterclass.mysecurity.fragments.ReportCrimeDash;
import co.ke.masterclass.mysecurity.fragments.ReportCrimeDash.OnReportItemclick;
import co.ke.masterclass.mysecurity.fragments.UserProfile;
import co.ke.masterclass.mysecurity.fragments.ViewPersons;
import co.ke.masterclass.mysecurity.fragments.ViewPersons.OnItemSelected;
import co.ke.masterclass.mysecurity.functions.Function;
import co.ke.masterclass.mysecurity.location.GPSTracker;
import co.ke.masterclass.mysecurity.location.Googleplaces;
import co.ke.masterclass.mysecurity.location.Place;
import co.ke.masterclass.mysecurity.location.PlaceList;
import co.ke.masterclass.mysecurity.network.ConnectivityDetector;
import co.ke.masterclass.mysecurity.network.FileTransfer;
import co.ke.masterclass.mysecurity.network.ProcessingTask;

public class Securitymain extends ActionBarActivity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks,OndashboardItemclick,OnReportItemclick,OnCrimeReportItemclick,OnItemSelected,UserProfile.onProfileCliked,EmergencyContacts.Contactsdata,PersonsDetails.OnPersonDataSent ,MissingPersonReport.OnMissingReportItemclick {

	private DashboardSecurity dash;
	private ReportCrimeDash repo;
	private CrimerReport crimerepo;
	private LocationMap mapl;
	private ViewPersons wanted;
   private PersonsDetails personald;
    private Gotoweb web;
	private static final String TAG="Securitymain";

	// drawer items 


    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mDrawerTitles;


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
    ConstantsClass consts;
    MissingPersonReport personMissing;
    EmergencyContacts emerg;
    private FileTransfer filetransfer ;
    private ProcessingTask bgTask;
    private Function functions;


    private static final String UPLOAD_VIDEO_NAME="video";
    private static final String UPLOAD_AUDIO_NAME="audio";
    private static final String UPLOAD_PICTURE_NAME="picture";

    String imagePath="";
    String videoPath="";
    String audioPath="";
    String cropedimagePath="";
    String errorMessage;
    Uri picUri;
    int resultcode;
    int imageRcode,vidaRcode,audioRcode;
    static String imageFilename="No Image";
    static  String videoFilename="No Video";
    static String audioFilename="No Audio";
    static String location;
    private Uri fileuri=null;
    private Uri imagefileuri,croppedimageuri;
    File fileobject;

    boolean audioDone=false;
    boolean videoDone=false;
    boolean imageDone=false;
    private static final int PIC_CROP = 2;
    private static final int MEDIA_TYPE_IMAGE = 4;
    private static final int MEDIA_TYPE_VIDEO=3;
    private static final int CAPTURE_VIDEO_ACTIVITY=200;
    private static final int CAPTURE_IMAGE_ACTIVITY=300;
    private static final int CAPTURE_IMAGE_CROP_ACTIVITY=350;
    private static final int CAPTURE_AUDIO_ACTIVITY=400;
    public static Securitymain  ActivityContext;
    private MenuItem refreshMenuItem;




	
	ArrayList<HashMap<String,String>> placesListItems =new ArrayList<HashMap<String,String>>();
	public static String KEY_REFERENCE ="reference";
	public static String KEY_NAME="name";
	public static String KEY_VICINITY="vicinity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_security_main);
		 mTitle=mDrawerTitle=getTitle();
        mTitle = mDrawerTitle = getTitle();
//        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
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
        navDraweritems.add(new NavDrawerItem(navMenutitles[6], navMenuIcons.getResourceId(6, -1)));
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
            selectItem(0);
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
                location=new GPSTracker(this).getLatitude() + "," + new GPSTracker(this).getLongitude();
				Log.e(TAG, "latitude:" + gps.getLatitude() + ", longitude: " + gps.getLongitude());
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
@Override
protected void onResume(){
    super.onResume();


}
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
      //  menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }


    public void selectItem(int possition) {

        switch (possition){

            case 0:


                break;
            case 1:


                break;
            case 2:
               // hotspot= new HotspotsMap();

                Intent mapl = new Intent(this, HotspotsMap.class);
                mapl.putExtra("user_latitude", gps.getLatitude());
                mapl.putExtra("user_longitude", gps.getLongitude());
                mapl.putExtra("near_places", nearplaces);
                startActivity(mapl);

                break;
            case 3:


                break;
            case 4:
                UserProfile profile = new UserProfile();
                profile.show(fragment,"UserProfile");
                break;
            case 5:

                break;
            case 6:
               personMissing=new MissingPersonReport();
               fragment.beginTransaction().remove(dash).add(R.id.container,personMissing).addToBackStack(null).commit();

                break;

        }


        mDrawerList.setItemChecked(possition, true);
        setTitle(navDraweritems.get(possition).getTitle());
        mDrawerLayout.closeDrawer(mDrawerList);

    }

    @Override
    public void onSetProfileClicked(String name, String email, String phone,String social,String locality,String gcm_regid) {
           if(isInternetPresent!=false) {
               new ProcessingTask(this, "Setting Profile...",1).execute(new Function().setProfile(name, email, phone,locality,social,gcm_regid));
           }else{

               Toast.makeText(this,ConstantsClass.INTERNET_MSG,Toast.LENGTH_LONG).show();
           }
    }

    @Override
    public void onContactListItemClick(String orgname, String Contact) {
        Intent callintent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:" +Contact));
        startActivity(callintent);
    }

    @Override
    public void OnTipSent(String des, int personid) {

        if(isInternetPresent!=false) {
            new ProcessingTask(this, "Sending Tip...",1).execute(new Function().reportSeen("1",des,""+personid,location));
        }else{

            Toast.makeText(this,ConstantsClass.INTERNET_MSG,Toast.LENGTH_LONG).show();
        }

    }


    private class SlideMenuClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			selectItem(position);


		}
}




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

        case R.id.action_share:

                Menuitemselected(0);
                return true;
            case R.id.action_refresh:

                Menuitemselected(0);
                return true;
         case R.id.action_about:

                Menuitemselected(1);
                return true;




		default:
			return super.onOptionsItemSelected(item);
		}

	}


    public void Menuitemselected(int itempos) {

        switch (itempos) {
            case 0:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Hey check out my app at: https://play.google.com/store/apps/details?id=com.MyRoad.MyRoad&hl=en");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);

                break;
            case 1:
                web= new Gotoweb();
                Bundle sendtoweb= new Bundle();
                sendtoweb.putString("url","http://www.masterclass.co.ke/faqs");
                web.setArguments(sendtoweb);
                fragment.beginTransaction().remove(dash).add(R.id.container,web).addToBackStack(null);

                break;
            case 2:

                break;
            case 3:

                break;
            case 4:
               /* Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Hey check out my app at: https://play.google.com/store/apps/details?id=com.MyRoad.MyRoad&hl=en");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);*/

                break;
            case 5:

                break;
            case 6:

                break;

            default:

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
            new ProcessingTask(this, "Refreshing please wait ...",0).execute(new Function().getAll("tbl_emergency_Lines"));
			emerg= new EmergencyContacts();
			fragment.beginTransaction().remove(dash).add(R.id.container,emerg).addToBackStack(null).commit();
			
			break;
		case 3:
			
			mapl= new LocationMap();
						
			Intent mapl = new Intent(this, LocationMap.class);
			mapl.putExtra("user_latitude", gps.getLatitude());
			mapl.putExtra("user_longitude", gps.getLongitude());
			mapl.putExtra("near_places", nearplaces);
			startActivity(mapl);
           // fragment.beginTransaction().remove(dash).add(R.id.container,mapl).addToBackStack(null).commit();
			
			
			break;
		case 4:
         //  if(isInternetPresent!=false) {
               new ProcessingTask(this, "Refreshing please wait ...",0).execute(new Function().PersonList("1"));
           // }
              wanted = new ViewPersons();
              fragment.beginTransaction().remove(dash).add(R.id.container, wanted).addToBackStack(null).commit();

			break;

		default:
			break;
		}
	}
	public void showCrimeInterface(String crimename){
		crimerepo= new CrimerReport();
        Bundle bundle= new Bundle();
        bundle.putString("Crime",crimename);
        crimerepo.setArguments(bundle);
        fragment.beginTransaction().remove(repo).add(R.id.container,crimerepo).addToBackStack(null).commit();

		
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
public void picCrop(Uri pic){

    if(pic!=null) {
        //call the standard crop action intent (the user device may not support it)
        Intent cropIntent = new Intent("com.android.camera.action.CROP");
        //indicate image type and Uri
        cropIntent.setDataAndType(pic, "image/*");
        //set crop properties
        cropIntent.putExtra("crop", "true");
        //indicate aspect of desired crop
        cropIntent.putExtra("aspectX", 1);
        cropIntent.putExtra("aspectY", 1);
        //indicate output X and Y
        cropIntent.putExtra("outputX", 256);
        cropIntent.putExtra("outputY", 256);
        //retrieve data on return
        try {
            croppedimageuri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
            cropIntent.putExtra("return-data", true);

            //start the activity - we handle returning in onActivityResult
            cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, croppedimageuri);
            startActivityForResult(cropIntent, PIC_CROP);
        } catch (ActivityNotFoundException anfe) {
            //display an error message
            errorMessage = "Sorry your device doesnt support Croping";
            Toast toast = Toast.makeText(Securitymain.this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }else{
        Toast.makeText(this,"No image to crop",Toast.LENGTH_SHORT).show();
    }

}

	@Override
	public void ButtonPressed(int id,int crop) {
		// TODO Auto-generated method stub
        final int cropping=crop;
		switch (id) {
		case 1:
            final CharSequence[] list={"Choose an Audio file","Record Audio", "Cancel"};
          //  alertintent.showAlertDialog("Audio",list);

          try{
            Intent intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
            intent.setType("audio/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent,"Select Audio "),CAPTURE_AUDIO_ACTIVITY);
        } catch(ActivityNotFoundException anfe){
            //display an error message
            errorMessage = "Sorry your device doesnt support audio capturing";
            Toast toast = Toast.makeText(Securitymain.this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }

        break;
		case 2:
            final CharSequence[] options={"Choose from Gallery","Take a Photo", "Cancel"};

            AlertDialog.Builder imgbuilder = new AlertDialog.Builder(Securitymain.this);

            imgbuilder.setTitle("Picture");

            imgbuilder.setItems(options, new DialogInterface.OnClickListener() {


                public void onClick(DialogInterface dialog, int item) {



                    if (options[item].equals("Take a Photo"))

                    { if(cropping==0) {

                        try {
                            Intent takephoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            imagefileuri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                            takephoto.putExtra(MediaStore.EXTRA_OUTPUT, imagefileuri);

                            startActivityForResult(takephoto, CAPTURE_IMAGE_ACTIVITY);

                        } catch (ActivityNotFoundException anfe) {
                            //display an error message
                            errorMessage = "Sorry your device doesnt support image capturing";
                            Toast toast = Toast.makeText(Securitymain.this, errorMessage, Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }else{
                        try {
                            Intent takephoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                           imagefileuri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                           takephoto.putExtra(MediaStore.EXTRA_OUTPUT, imagefileuri);
                            startActivityForResult(takephoto, CAPTURE_IMAGE_CROP_ACTIVITY);

                        } catch (ActivityNotFoundException anfe) {
                            //display an error message
                            errorMessage = "Sorry your device doesnt support image capturing";
                            Toast toast = Toast.makeText(Securitymain.this, errorMessage, Toast.LENGTH_SHORT);
                            toast.show();
                        }

                    }



                } else if (options[item].equals("Choose from Gallery")){

                     if(cropping==0) {
                         try {
                             Intent photopick = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                             startActivityForResult(photopick, CAPTURE_IMAGE_ACTIVITY);
                         } catch (ActivityNotFoundException anfe) {
                             //display an error message
                             errorMessage = "Sorry your device doesnt support Image capturing";
                             Toast toast = Toast.makeText(Securitymain.this, errorMessage, Toast.LENGTH_SHORT);
                             toast.show();
                         }
                     }else{
                         try {
                             Intent photopick = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                             startActivityForResult(photopick, CAPTURE_IMAGE_CROP_ACTIVITY);
                         } catch (ActivityNotFoundException anfe) {
                             //display an error message
                             errorMessage = "Sorry your device doesnt support Image capturing";
                             Toast toast = Toast.makeText(Securitymain.this, errorMessage, Toast.LENGTH_SHORT);
                             toast.show();
                         }

                     }


                }
                    else if (options[item].equals("Cancel")) {


                        dialog.dismiss();

                    }



                }

            });

            imgbuilder.show();


			break;
		case 3:
            final CharSequence[] option={"Choose a Video","Record Video", "Cancel"};

           AlertDialog.Builder builder = new AlertDialog.Builder(Securitymain.this);

            builder.setTitle("Video");

            builder.setItems(option, new DialogInterface.OnClickListener() {


                public void onClick(DialogInterface dialog, int item) {



                    if (option[item].equals("Record Video"))

                    {

                        try {
                            Intent intentvideo = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

                            fileuri = getOutputMediaFileUri(MEDIA_TYPE_VIDEO);
                            intentvideo.putExtra(MediaStore.EXTRA_OUTPUT, fileuri);
                            intentvideo.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                            startActivityForResult(intentvideo, CAPTURE_VIDEO_ACTIVITY);

                        } catch(ActivityNotFoundException anfe){
                                //display an error message
                            errorMessage = "Sorry your device doesnt support video capturing";
                                Toast toast = Toast.makeText(Securitymain.this, errorMessage, Toast.LENGTH_SHORT);
                                toast.show();
                            }

                    } else if (option[item].equals("Choose a Video")){

                      try{  Intent videopick =new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(videopick, CAPTURE_VIDEO_ACTIVITY);
                    } catch(ActivityNotFoundException anfe){
                        //display an error message
                        errorMessage = "Sorry your device doesnt support video capturing";
                        Toast toast = Toast.makeText(Securitymain.this, errorMessage, Toast.LENGTH_SHORT);
                        toast.show();
                    }


                }else if (option[item].equals("Cancel")) {


                         dialog.dismiss();

                    }



                }

            });

            builder.show();








            break;

		default:
			break;
		}
	}



    /** Create a file Uri for saving an image or video */
    private static Uri getOutputMediaFileUri(int type){

        return Uri.fromFile(getOutputMediaFile(type));
    }
    /** Create a File for saving an image or video */
    private  static File getOutputMediaFile(int type){

 //checking mounting of sd card
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()+"/Mysecurity");


        if(!mediaStorageDir.exists()){
            if(!mediaStorageDir.mkdirs()) {
               Toast.makeText(ActivityContext, "Failed to create Directory Please Mount SD Card", Toast.LENGTH_SHORT).show();
            }

            return null;
        }
        // creating a media file

        Date date = new Date();
        String timestamp = new SimpleDateFormat("yyyymmdd_HHmmss").format(date.getTime());

        File mediafile;
        if(type==MEDIA_TYPE_IMAGE){

            mediafile =new  File(mediaStorageDir.getPath() + File.separator + "MYSECURITY_PIC_EVIDENCE_"+ timestamp + ".jpg" );
            imageFilename = "MYSECURITY_PIC_EVIDENCE_"+ timestamp + ".jpg";

        }
       else if(type==MEDIA_TYPE_VIDEO){

            mediafile =new  File(mediaStorageDir.getPath() + File.separator + "MYSECURITY_VID_EVIDENCE_"+ timestamp + ".mp4" );
            videoFilename="MYSECURITY_VID_EVIDENCE_"+ timestamp + ".mp4";
        } else{

            return null;
        }
        return mediafile;

    }
  public String getRealPathFromURI(Uri contentUri) {
        String [] proj={MediaStore.Images.Media.DATA};
        android.database.Cursor cursor = getContentResolver().query( contentUri,
                proj,     // Which columns to return
                null,     // WHERE clause; which rows to return (all rows)
                null,     // WHERE clause selection arguments (none)
                null);     // Order-by clause (ascending by name)
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }







public void onActivityResult(int requestCode,int resultCode, Intent data){
    super.onActivityResult(requestCode, resultCode, data);


       if(requestCode ==CAPTURE_IMAGE_ACTIVITY){
              if(data==null) {
                    if(imagefileuri==null){
                        Toast.makeText(this,"Application closed",Toast.LENGTH_SHORT).show();
                        imagePath ="";
                    }else {

                        imagePath = imagefileuri.getPath();


                    }
              }else{


               //  String imagePath2=data.getData().getPath();
                 imagePath= getRealPathFromURI(data.getData());
                 imageFilename=new File(imagePath).getName();




              }
       }else
    if(requestCode ==CAPTURE_IMAGE_CROP_ACTIVITY){
        if(data==null) {
            if(imagefileuri==null) {
                Toast.makeText(this, "Application closed", Toast.LENGTH_SHORT).show();
                imagePath = "";
            }else{
                cropedimagePath=imagefileuri.getPath();
                picCrop(imagefileuri);
            }
        }else{

                picCrop(data.getData());
            //  String imagePath2=data.getData().getPath();
           // imagePath= getRealPathFromURI(data.getData());
           // imageFilename=new File(imagePath).getName();




        }
    }else  if(requestCode ==PIC_CROP){
        if(resultCode==RESULT_OK){
        if(data==null) {

            if( croppedimageuri==null){
                Toast.makeText(this,"Application closed",Toast.LENGTH_SHORT).show();
                cropedimagePath ="";
            }else{

            }
        }else{

          //  cropedimagePath= data.getData().getPath();
          //  imageFilename=new File(cropedimagePath).getName();

            Toast.makeText(this,"Cropped image name is :  "+ imageFilename + "   path  " + cropedimagePath.toString(),Toast.LENGTH_LONG).show();

        }

        }else {
        Toast.makeText(this, "Application Closed", Toast.LENGTH_LONG).show();
    }


    }

       else if(requestCode ==CAPTURE_AUDIO_ACTIVITY){
           if(resultCode==RESULT_OK){

                 audioPath= getRealPathFromURI(data.getData());
                   audioFilename=new File(audioPath).getName();

           }else if (resultCode==RESULT_CANCELED){

               Toast.makeText(this, "User cancelled the audio capture.",
                       Toast.LENGTH_LONG).show();
               audioPath="";
           }




                         }
        else  if(requestCode ==CAPTURE_VIDEO_ACTIVITY){

        if(resultCode == RESULT_OK) {

                   // Video captured and saved to fileUri specified in the Intent
             if(data!=null) {

                  videoPath=getRealPathFromURI(data.getData());
                 videoFilename=new File(videoPath).getName();

             } else {
                 if(fileuri==null) {
                     Toast.makeText(this, "Application closed", Toast.LENGTH_LONG).show();
                     videoPath="";
                 }else{
                     videoPath=fileuri.getPath();
                   //  videoFilename=new File(videoPath).getName();
                 }
             }

       } else if (resultCode == RESULT_CANCELED) {

                         // User cancelled the video capture
                   Toast.makeText(this, "User cancelled the video capture.",
                           Toast.LENGTH_LONG).show();

               } else {

                   // Video capture failed, advise user
                   Toast.makeText(this, "Video capture failed.",
                           Toast.LENGTH_LONG).show();
               }


        }

}
    @Override
    public void DataPassed(String crime,String description, boolean anonimity,String emercode){


       if(isInternetPresent) {
           if (imagePath != "") {
               if (new FileTransfer(UPLOAD_PICTURE_NAME, "Uploading files...", this).execute(imagePath).getStatus() == AsyncTask.Status.FINISHED && ConstantsClass.SUCCESS_RESPONSE == 1) {
                   imageDone = true;
               } else {
                   imageDone = false;
               }
           } else {

           }
           if (videoPath != "") {
               if (new FileTransfer(UPLOAD_VIDEO_NAME, "Uploading files...", this).execute(videoPath).getStatus() == AsyncTask.Status.FINISHED && ConstantsClass.SUCCESS_RESPONSE == 1) {
                   videoDone = true;
               } else {
                   videoDone = false;
               }
           } else {

           }
           if (audioPath != "") {
               if (new FileTransfer(UPLOAD_AUDIO_NAME, "Uploading files...", this).execute(audioPath).getStatus() == AsyncTask.Status.FINISHED && ConstantsClass.SUCCESS_RESPONSE == 1) {
                   audioDone = true;
               } else {
                   audioDone = false;
               }

           } else {

           }

/*if(( imageDone==true &&  videoDone==true && audioDone==true)
    || (imageDone==true ||  videoDone==true||  audioDone==true)
        || (imageDone==true &&  videoDone==true) || ( audioDone==true && videoDone==true)
        || ( imageDone==true && audioDone==true) ){*/

           if (anonimity == true) {

               new ProcessingTask(Securitymain.this, "Sending Tip...",1).execute(new Function().Makereport(crime, "1", description, imageFilename, videoFilename, audioFilename, location, emercode));
           } else {
               new ProcessingTask(Securitymain.this, "Sending Tip...",1).execute(new Function().Makereport(crime, "0", description, imageFilename, videoFilename, audioFilename, location, emercode));
           }
       }else{
           Toast.makeText(this,ConstantsClass.INTERNET_MSG,Toast.LENGTH_SHORT).show();
       }
/*}else {
 */
  }
    @Override
    public void DataMissingPassed(String name,String identity,String gender, String description) {


       // if(isInternetPresent) {
            if (cropedimagePath != "") {
                if (new FileTransfer(UPLOAD_PICTURE_NAME, "Uploading files...", this).execute(cropedimagePath).getStatus() == AsyncTask.Status.FINISHED && ConstantsClass.SUCCESS_RESPONSE == 1) {
                    imageDone = true;
                } else {
                    imageDone = false;
                }
            } else {

            }
            if (videoPath != "") {
                if (new FileTransfer(UPLOAD_VIDEO_NAME, "Uploading files...", this).execute(videoPath).getStatus() == AsyncTask.Status.FINISHED && ConstantsClass.SUCCESS_RESPONSE == 1) {
                    videoDone = true;
                } else {
                    videoDone = false;
                }
            } else {

            }
            if (audioPath != "") {
                if (new FileTransfer(UPLOAD_AUDIO_NAME, "Uploading files...", this).execute(audioPath).getStatus() == AsyncTask.Status.FINISHED && ConstantsClass.SUCCESS_RESPONSE == 1) {
                    audioDone = true;
                } else {
                    audioDone = false;
                }

            } else {

            }

                new ProcessingTask(Securitymain.this, "Sending Please wait...",1).execute(new Function().ReportMissing("1",name,identity,gender,location,imageFilename, videoFilename, audioFilename,description,"0"));

      //  }else{
      //      Toast.makeText(this,ConstantsClass.INTERNET_MSG,Toast.LENGTH_SHORT).show();
      //  }

    }

	@Override
	public void OnselectedItem(int id, String name) {
		// TODO Auto-generated method stub
		
	}

    @Override
    public void ListviewItemClicked(int id) {
        personald = new PersonsDetails();
        Bundle bpd = new Bundle();
        bpd.putInt("personid",id);
       personald.setArguments(bpd);
        fragment.beginTransaction().remove(wanted).add(R.id.container,personald).addToBackStack(null).commit();
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

 /*private class FragmentItemsAdapter extends BaseAdapter {
        TextView tv;View v;
        LinearLayout ll;
        Context context;
        public Integer[] icon={
                R.drawable.ic_home,
                R.drawable.ic_home,
                R.drawable.ic_home,
                R.drawable.ic_home,
                R.drawable.ic_home,
                R.drawable.ic_home,
                R.drawable.ic_home,
                R.drawable.ic_home,
                R.drawable.ic_home,
                R.drawable.ic_home,
                R.drawable.ic_home,
        };

        public String icon_label[]={

                "Traffic Updates",
                "Default Road",
                "Other Roads",
                "My Route/Stage",
                "Other Routes/Stages",
                "Add Road",
                "Add Section",
                "Add Direction",
                "About",
                "Terms of Use",
                "Privacy Policy"
        };
        public String background[]={
                "#FFFFFF",
                "#8025383C",
                "#8025383C",
                "#8025383C",
                "#8025383C",
                "#8025383C",
                "#8025383C",
                "#8025383C",
                "#8025383C",
                "#FFFFFF",
                "#FFFFFF",
        };

        public String foreground[]={

                "#25383C",
                "#FFFFFF",
                "#FFFFFF",
                "#FFFFFF",
                "#FFFFFF",
                "#FFFFFF",
                "#FFFFFF",
                "#FFFFFF",
                "#FFFFFF",
                "#25383C",
                "#25383C",
        };

        public int visible[]={
                0,
                1,
                1,
                1,
                1,
                1,
                1,
                1,
                0,
                1,
                0,
        };

        public FragmentItemsAdapter(Context c) {
            context=c;
        }
        public int getCount() {
            return icon.length;
        }

        public Object getItem(int position) {
            return icon[position];
        }

        public long getItemId(int position) {
            return icon[position];
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View MyView = convertView;

		/*we define the view that will display on the grid*/

            //Inflate the layout
     /*       LayoutInflater li = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            MyView = li.inflate(R.layout.list_text, null);

            // Add The Text!!!
            tv = (TextView)MyView.findViewById(R.id.text);
            v = (View)MyView.findViewById(R.id.v);
            ll = (LinearLayout)MyView.findViewById(R.id.GridItem);
            ll.setBackgroundColor(Color.parseColor(background[position]));
            tv.setTextColor(Color.parseColor(foreground[position]));
            tv.setText(icon_label[position]);
            if(visible[position]==1)
            {
                v.setVisibility(View.VISIBLE);
            }
            // Add The Image!!!
            ImageView iv = (ImageView)MyView.findViewById(R.id.img);
            iv.setImageResource(icon[position]);


            return MyView;
        }




    }*/

}
