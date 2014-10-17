package com.telosoft.mtokamanager;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.AsyncTask.Status;
import android.os.Bundle;
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

import com.telosoft.mtokamanager.fragments.AddAsset;
import com.telosoft.mtokamanager.fragments.AddGroupFragment;
import com.telosoft.mtokamanager.fragments.AddGroupFragment.onSubmitListener;
import com.telosoft.mtokamanager.fragments.AssetManagerFragment;
import com.telosoft.mtokamanager.fragments.AssetManagerFragment.onAddAssetButtonclick;
import com.telosoft.mtokamanager.functions.Functions;
import com.telosoft.mtokamanager.threads.DoInBackground;

@SuppressLint("NewApi")
public class MtokaDashboard extends ActionBarActivity implements onSubmitListener,onAddAssetButtonclick{
	   	private DrawerLayout mDrawerLayout;
	      private ListView mDrawerList;
	      private ActionBarDrawerToggle mDrawerToggle;
	      private CharSequence mDrawerTitle;
	      private CharSequence mTitle;
	      CustomDrawerAdapter adapter;
	      DialogFragment dialogFragment;
	      private static String TAG="MtokaDashboard tag";
	 
	      List<DrawerItem> dataList;

	 

	      @Override
protected void onCreate(Bundle savedInstanceState) {
	            super.onCreate(savedInstanceState);
	            setContentView(R.layout.asset_manager);
	            if (android.os.Build.VERSION.SDK_INT > 9) {
	                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
	                        .permitAll().build();
	                StrictMode.setThreadPolicy(policy);
	            }
	      		new DoInBackground(this,"Preparing Dashboard...",false).execute(new Functions().getAllItems("group_table"));
	            // Initializing
	            dataList = new ArrayList<DrawerItem>();
	            mTitle = mDrawerTitle = getTitle();
	            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
	            mDrawerList = (ListView) findViewById(R.id.left_drawer);
	 
	            mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
	                        GravityCompat.START);
	 
	            // Add Drawer Item to dataList
	           
	            dataList.add(new DrawerItem("Add Group", R.drawable.ic_action_group));
	            dataList.add(new DrawerItem("Search", R.drawable.ic_action_search));
	            dataList.add(new DrawerItem("About", R.drawable.ic_action_about));
	            dataList.add(new DrawerItem("Settings", R.drawable.ic_action_settings));
	            dataList.add(new DrawerItem("Help", R.drawable.ic_action_help));
	            dataList.add(new DrawerItem("Logout", R.drawable.ic_action_email));
	 
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
	 
	      }
	      
	  
		private void showAddGroupDialog() {
	          FragmentManager fm = getSupportFragmentManager();
	          AddGroupFragment dialogFragment =new  AddGroupFragment();
	          dialogFragment.show(fm, "fragment_add_group");
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
					Log.d(TAG,"The recieved group name is  " + name);
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
	 
	}
	
	
	


