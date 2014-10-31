package com.telosoft.ligaroba.consumerwatch;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.telosoft.ligaroba.consumerwatch.R;
import com.telosoft.ligaroba.consumerwatch.custom.GetterSetterClass;
import com.telosoft.ligaroba.consumerwatch.network.SqliteDbHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by root on 10/20/14.
 */
public class Supermarkets extends Activity {
    View view;
    ListView itemlist;
    SqliteDbHandler db;
   ArrayList<String> productname=new ArrayList<String>();
    ArrayList<Integer> quantity = new ArrayList<Integer>();
    List<GetterSetterClass> buget =new ArrayList<GetterSetterClass>();
    List<HashMap<String,Object>>aList =new ArrayList<HashMap<String, Object>>();
   private TypedArray logos;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supermaketlist);
        itemlist= (ListView) findViewById(R.id.superlists);
        db=new SqliteDbHandler(this);
        logos=getResources().obtainTypedArray(R.array.superlogos);
        Bundle bundle=getIntent().getExtras();

        productname=bundle.getStringArrayList("prodname");
        quantity=bundle.getIntegerArrayList("prodqty");


        for(int i=0;i<productname.size();i++){

         //  new GetterSetterClass().setQauntity(quantity.get(i));
          buget= db.getbudget(productname.get(i),quantity.get(i));
            Log.e("Supermarket",""+ productname.size() +  "  pname : " + productname.get(i) + "quantity: " + quantity.get(i) );


        }



        for(int i=0;i<buget.size();i++){
            HashMap<String, Object> hm = new HashMap<String,Object>();
            hm.put("sname", buget.get(i).getSupername());
            hm.put("total","Total:  Kshs "+ buget.get(i).budget());
            hm.put("icon",logos.getDrawable(i));


            aList.add(hm);
        }

        String[]  from = {"sname","total","icon"};

        int[] mapto=new int[]{
                R.id.tvshopname,
                R.id.tvbudget,
                R.id.imgvlogo
        };

        CustomAdapter  sAdapter =new CustomAdapter(this,R.layout.shoplist);
        itemlist.setAdapter(sAdapter);




    }
    private class ViewHolder{
        ImageView superlogo;
        TextView tvshopname;
        TextView tvbudget;

        int position;

    }

    public class registerCallbacks implements View.OnClickListener{

        int mposition;


        public registerCallbacks(ViewHolder viewHolder){
            mposition=viewHolder.position;
        }
        @Override
        public void onClick(View view) {
            /*// long itemid=listadapter.getItemId(mposition);
            try{
                CustomAdapter adapter =(CustomAdapter) lstView.getAdapter();
                adapter.remove(adapter.getItem(mposition));
            }catch (IndexOutOfBoundsException io){
                io.printStackTrace();
            }
           // adapter.notifyDataSetChanged();
           */
        }
    }

    private class CustomAdapter extends ArrayAdapter<String> {

        public CustomAdapter(Context context, int resource) {
            super(context, resource);
        }

        @Override
        public int getCount() {
            return aList.size();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View cView=convertView;
            ViewHolder vholder=null;




            if(cView==null){
                cView=getLayoutInflater().inflate(R.layout.shoplist,parent,false);

            }
            vholder=new ViewHolder();


            vholder.tvshopname= (TextView) cView.findViewById(R.id.tvshopname);
            vholder.tvbudget= (TextView) cView.findViewById(R.id.tvbudget);
            vholder.superlogo= (ImageView) cView.findViewById(R.id.imgvlogo);


            if(aList!=null) {


                vholder.tvshopname.setText(" " + aList.get(position).get("sname"));
                vholder.tvbudget.setText(" "+ aList.get(position).get("total"));
                vholder.superlogo.setImageDrawable((Drawable) aList.get(position).get("icon"));



            }



            return cView;

        }
    }
}
