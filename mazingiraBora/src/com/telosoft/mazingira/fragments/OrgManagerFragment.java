package com.telosoft.mazingira.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.telosoft.mazingira.R;
import com.telosoft.mazingira.customs.ListSClass;
import com.telosoft.mazingira.network.SqliteDBhandler;


public class OrgManagerFragment extends Fragment implements OnClickListener, OnLongClickListener {
	View rootView;
	Button addAsset;
	EditText inputSearch;
	int buttonStatus;
	ListView assetView;
	String[] mapFrom;
	SqliteDBhandler db;
	private String TAG="AssetFragment";
	ListSClass list;
	 List<HashMap<String,String>> aList ;
	SimpleAdapter sAdapter;
	List<ListSClass> assetdata =new ArrayList<ListSClass>();
	public interface onAddOrgButtonclick{
		
		public void setOnAddOrgbuttonListener(int id);
		public void onOrgMenuItemSelected(String assetId,int menuItem);
	}
	private onAddOrgButtonclick listener;
	
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		
			 try{
				 listener= (onAddOrgButtonclick)activity;
			 }catch(ClassCastException e){
				 throw new ClassCastException(activity.toString()
		                    + " must implement AuthenticationDialogListener"); 
				 
			 }
			 
	}
	
	@SuppressLint("NewApi")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		rootView= inflater.inflate(R.layout.mazingira_dashboard,container,false);
		addAsset=(Button) rootView.findViewById(R.id.btnaddorg);
		inputSearch=(EditText) rootView.findViewById(R.id.search_asset);
		assetView=(ListView) rootView.findViewById(R.id.assetlist);
		addAsset.setOnClickListener(this);
		db=new SqliteDBhandler(getActivity());
		assetdata=db.getAll();
		registerForContextMenu(assetView);
		
		
	
		
		//Log.d(TAG, "all asset data   "+ assetdata.toString());
		
		
	     // Each row in the list stores country name, currency and flag
       aList = new ArrayList<HashMap<String,String>>();        
        
        for(int i=0;i<assetdata.size();i++){
        	HashMap<String, String> hm = new HashMap<String,String>();
           
            hm.put("name", assetdata.get(i).getOrgname());
            hm.put("contact",  assetdata.get(i).getId());
            hm.put("submodel", assetdata.get(i).getSubmodel());
            hm.put("members",assetdata.get(i).getMembers());
          
         
            //hm.put("flag", Integer.toString(flags[i]));            
            aList.add(hm);        
        }
              // Log.d(TAG, "all asset data 2  "+ aList.toString());
        String[]  from = { "name","contact","submodel","members" };
        
        
		int[] mapto=new int[]{R.id.assetnamelabel,
		                    R.id.model,
		                    R.id.groupname,
		                    R.id.capacity
		                   
		                    };
		
		
		sAdapter =new SimpleAdapter(getActivity().getBaseContext(),aList,R.layout.all_org_view, from, mapto);
		assetView.setAdapter(sAdapter);
	
		// adding search functionality to search Edittext	
	inputSearch.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
							// TODO Auto-generated method stub
				OrgManagerFragment.this.sAdapter.getFilter().filter(s);
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
		
		return rootView;
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnaddorg:
			buttonStatus=1;
			listener.setOnAddOrgbuttonListener(buttonStatus);
			break;

		default:
			break;
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		HashMap<String , String>header=new HashMap<String, String>();
		AdapterView.AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		header=(HashMap<String, String>) assetView.getItemAtPosition(info.position);
		String listItemSelected= header.get("id");
	
		int menuIndex = item.getItemId();
		String[] menuItems= getResources().getStringArray(R.array.context_menu);
		String menuitemName = menuItems[menuIndex];
	
		
		
				
			listener.onOrgMenuItemSelected(listItemSelected, menuIndex);
		
		return true;
		
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		HashMap<String , String>header=new HashMap<String, String>();
		if(v.getId()==R.id.assetlist){
			AdapterView.AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
			header=(HashMap<String, String>) assetView.getItemAtPosition(info.position);
			String head= header.get("id");
			menu.setHeaderTitle(head);
			String[] menuItems= getResources().getStringArray(R.array.context_menu);
			for(int i=0;i<menuItems.length;i++){
				menu.add(Menu.NONE,i,i,menuItems[i]);
				
			}
		}
	}

	@Override
	public boolean onLongClick(View v) {
		// TODO Auto-generated method stub
			
		return false;
	}
	

}
