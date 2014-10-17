package co.ke.masterclass.mysecurity.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.ke.masterclass.mysecurity.R;
import co.ke.masterclass.mysecurity.Securitymain;
import co.ke.masterclass.mysecurity.custom.CustomSeterClass;
import co.ke.masterclass.mysecurity.network.SqliteDbHandler;

/**
 * Created by root on 10/3/14.
 */
public class EmergencyContacts extends Fragment {
View view;
ListView listContacts;
SqliteDbHandler db;
Securitymain sec =new Securitymain();

List<CustomSeterClass> contactlist=new ArrayList<CustomSeterClass>();

public interface  Contactsdata {
   public void onContactListItemClick(String orgname, String Contact);


}
    Contactsdata listener;
    @Override

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (Contactsdata) activity;
        }catch (ClassCastException ex){
            throw new ClassCastException(activity.toString()
                    + " must implement AuthenticationDialogListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       view =inflater.inflate(R.layout.emergency_contacts,container,false);
       listContacts= (ListView) view.findViewById(R.id.listcontacts);
        db =new SqliteDbHandler(getActivity());
       contactlist=db.getEmergContacts();
       ArrayAdapter<CustomSeterClass> adapter = new CustomAdapter(getActivity(), R.layout.customemerglistview,contactlist) ;
        listContacts.setAdapter(adapter);
        adapter.notifyDataSetChanged();



        return view;
    }

    private class  registerClickcallback implements View.OnClickListener{
        int mposition;
        public  registerClickcallback(int position){
            this.mposition=position;
        }

        @Override
        public void onClick(View view) {
            CustomSeterClass itemclicked = contactlist.get(mposition) ;
             listener.onContactListItemClick(itemclicked.getOrganme(),itemclicked.getContact());
        }


    }
    private class CustomAdapter extends ArrayAdapter<CustomSeterClass> {


        public CustomAdapter(Context context, int resource, List<CustomSeterClass> objects) {
            super(context, resource, objects);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
           View itemview = convertView;
            if(itemview ==null){
              itemview = getLayoutInflater(null).inflate(R.layout.customemerglistview, parent, false);

            }
            CustomSeterClass contacts = contactlist.get(position);

            TextView orgname = (TextView) itemview.findViewById(R.id.tvorgname);

            orgname.setText("" + contacts.getOrganme());
            TextView cont = (TextView) itemview.findViewById(R.id.tvcontact);
            cont.setText(""+ contacts.getContact());

            itemview.setOnClickListener(new registerClickcallback(position));
            return itemview;
        }

    }
}
