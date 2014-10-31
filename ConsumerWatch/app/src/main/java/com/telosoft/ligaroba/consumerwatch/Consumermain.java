package com.telosoft.ligaroba.consumerwatch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.telosoft.ligaroba.consumerwatch.custom.GetterSetterClass;
import com.telosoft.ligaroba.consumerwatch.network.SqliteDbHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by root on 10/20/14.
 */
public class Consumermain extends Activity implements  View.OnClickListener {
    Spinner spinner, spinquantity;
    String path[] = {"Laptops", "DesktopPC", "Tablets", "Add-Ons", "Gaming"};

    String Laptops[] = {"Dell", "Hp", "Apple"};
    ListView lstView;
     ArrayAdapter<String> adapter;

    int[] mapto;
    String[] from;
    String itqty;
    Button add;
    ArrayList<String> productlist;
    CustomAdapter listadapter;
    String TAG="Consumer";
    Button shop;
    Supermarkets supermarket;
    FragmentManager fragment;
    ArrayList<GetterSetterClass> products=null;
    private SqliteDbHandler db;
    private List<GetterSetterClass> list =null;
    private List<GetterSetterClass> listitems =null;
    String pname;
   Drawable icon;
   ArrayList<String> prodname = new ArrayList<String>();
    ArrayList<Integer> prodqty = new ArrayList<Integer>();


    private TypedArray logos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consumermain);
        spinner = (Spinner) findViewById(R.id.spinitemlist);
        spinquantity = (Spinner) findViewById(R.id.spinquantity);
        add=(Button) findViewById(R.id.btnadditem);
        shop=(Button) findViewById(R.id.btnshop);
        populateDb();
        supermarkets();
        prices();
        db=new SqliteDbHandler(this);
        productlist=new ArrayList<String>();
        productlist= db.getAllLabels("product_name","products","product_name");
        list=new ArrayList<GetterSetterClass>();
        listitems=new ArrayList<GetterSetterClass>();
        lstView = (ListView) findViewById(R.id.shoppncartlist);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,  productlist);


        listadapter  = new CustomAdapter(Consumermain.this,R.layout.customproductlist);
        logos=getResources().obtainTypedArray(R.array.logos);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                try {
                    String[] group = productlist.toArray(new String[0]);
                    pname = group[position];
                  icon=  logos.getDrawable(position);


                } catch (IndexOutOfBoundsException io) {
                    io.printStackTrace();
                    Log.e(TAG, io.toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinquantity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String[] qty = getResources().getStringArray(R.array.itemquantity);
                itqty = qty[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        add.setOnClickListener(this);
        shop.setOnClickListener(this);

    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnadditem:

                // list.add("" + s);

                Toast.makeText(Consumermain.this, "tha values: " + pname + " qty : " + itqty , Toast.LENGTH_LONG).show();


                   GetterSetterClass hm =new GetterSetterClass();
                    hm.setPname(pname);
                    hm.setQauntity(Integer.parseInt(itqty));
                    hm.setImage(icon);
                    list.add(hm);
                    listadapter.notifyDataSetChanged();



                lstView.setAdapter(listadapter);

                break;
            case R.id.btnshop:
                  //  listadapter.getItem(mp);
                CustomAdapter adatpert= (CustomAdapter)lstView.getAdapter();
                if(adatpert.getCount()<1){

                    Toast.makeText(this,"choose an item",Toast.LENGTH_LONG).show();
                }


                Bundle bundle= new Bundle();

                Intent intent =new Intent(this,Supermarkets.class);
               for (int i=0;i<list.size();i++){

                  prodname.add(list.get(i).getPname());
                  prodqty.add(list.get(i).getQauntity());

                Log.e("Consumee : ", "prodname" + prodname + "  prodqty  : " + prodqty );
               }

                bundle.putStringArrayList("prodname", prodname);
                bundle.putIntegerArrayList("prodqty", prodqty);
                intent.putExtras(bundle);
                startActivity(intent);




                break;
        }
    }

    private class ViewHolder{
        ImageView logo;
        TextView tvpName;
        TextView tvQty;
        Button delete;
        int position;

    }

    public class registerCallbacks implements View.OnClickListener{

        int mposition;


        public registerCallbacks(ViewHolder viewHolder){
           mposition=viewHolder.position;
        }
        @Override
        public void onClick(View view) {
           // long itemid=listadapter.getItemId(mposition);
            try{
                CustomAdapter adapter =(CustomAdapter) lstView.getAdapter();
                adapter.remove(adapter.getItem(mposition));
            }catch (IndexOutOfBoundsException io){
                io.printStackTrace();
            }
            adapter.notifyDataSetChanged();
        }
    }

    private class CustomAdapter extends ArrayAdapter<String> {

        public CustomAdapter(Context context, int resource) {
            super(context, resource);
        }

        @Override
        public int getCount() {
            return list.size();
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
                cView=getLayoutInflater().inflate(R.layout.customproductlist,parent,false);

            }
            vholder=new ViewHolder();
            vholder.tvpName= (TextView) cView.findViewById(R.id.tvproductname);
            vholder.tvQty= (TextView) cView.findViewById(R.id.tvqty);
            vholder.logo= (ImageView) cView.findViewById(R.id.productlogo);
            vholder.delete= (Button) cView.findViewById(R.id.btndelete);
            String pname;
            int quant;
            int lengh=0;

            if(list!=null) {
                pname =list.get(position).getPname();
                quant=list.get(position).getQauntity();
                vholder.tvpName.setText(" " + pname);
                vholder.tvQty.setText("Qty: " + quant);
                //cView.setTag(list.get(position));
                GetterSetterClass item= new GetterSetterClass();

                Log.e("getView position" , "" + position + "  name: " + pname + " quant : " + quant);
                vholder.logo.setImageDrawable((Drawable) list.get(position).getImage());
             //   quantity[position]= quant;

                item.setPname(pname);
                item.setQauntity(quant);
                listitems.add(item);

            }

            vholder.position=position;
            vholder.delete.setOnClickListener(new registerCallbacks(vholder));
            lengh++;

            return cView;

        }
    }
    public void supermarkets(){
        db=new SqliteDbHandler(this);
        db.resetTables("supermarkets");
        db.supermarket(1,"Tuskys", "turskys");
        db.supermarket(2,"Uchumi", "Uchumi");
        db.supermarket(3,"Nakumatt", "Nakumatt");
        db.supermarket(4,"Naivas", "Naivas");



    }
    public void populateDb(){
         db=new SqliteDbHandler(this);
          db.resetTables("products");
        db.product( 1,"Unga Hostess 2kg", "hostess");
        db.product(2,"Unga Jogoo Fortified 2kg", "jogoo");
        db.product(3, "unga soko maize meal", "soko");
        db.product(4, "Cooking Oil Elianto 500 ml", "elianto");
        db.product( 5,"Cooking Oil Elianto 1 litre", "elianto");
        db.product( 6,"Cooking oil Elianto 2 litres", "elianto");
        db.product( 7,"Cooking oil Elianto 3 litres", "elianto");
        db.product(8, "Cooking oil Elianto 5 litres", "elianto");
        db.product( 9,"Cooking oil Golden Fry 500ml", "golden_fry");
        db.product(10, "Cooking Oil Golden Fry 1 litre", "golden_fry");
        db.product( 11,"Cooking Oil Golden Fry 2 litres", "golden_fry");
        db.product(12, "Cooking Oil Golden Fry 3 litres", "golden_fry");
        db.product( 13,"Cooking Oil Golden Fry 5 litres", "golden_fry");
        db.product(14, "Milk- Crown Gold Milk  500ml", "golden_fry");

     db.product(15,"Milk- Ilara Fresh Milk 500ml ",  "ilara");


    }

    public void prices(){
        db=new SqliteDbHandler(this);
        db.resetTables("prices");
        db.prices(1,1,138);
        db.prices(1,2,138);
        db.prices(1,3,138);
        db.prices(1,4,138);
        db.prices(2,1,111);
        db.prices(2,2,113);
        db.prices(2,3,111);
        db.prices(2,4,111);
        db.prices(3,1,104);
        db.prices(3,2,107);
        db.prices(3,3,105);
        db.prices(3,4,106);

        db.prices(4,1,156);
        db.prices(4,2,156);
        db.prices(4,3,156);
        db.prices(4,4,156);

        db.prices(5,1,263);
        db.prices(5,2,263);
        db.prices(5,3,199);
        db.prices(5,4,199);

        db.prices(6,1,515);
        db.prices(6,2,374);
        db.prices(6,3,374);
        db.prices(6,4,370);

        db.prices(7,1,760);
        db.prices(7,2,800);
        db.prices(7,3,807);
        db.prices(7,4,807);

        db.prices(8,1,1515);
        db.prices(8,2,1374);
        db.prices(8,3,1374);
        db.prices(8,4,1370);

        db.prices(9,1,111);
        db.prices(9,2,111);
        db.prices(9,3,110);
        db.prices(9,4,110);

        db.prices(10,1,199);
        db.prices(10,2,199);
        db.prices(10,3,199);
        db.prices(10,4,199);

        db.prices(11,1,374);
        db.prices(11,2,374);
        db.prices(11,3,370);
        db.prices(11,4,370);

        db.prices(12,1,536);
        db.prices(12,2,536);
        db.prices(12,3,535);
        db.prices(12,4,535);

        db.prices(14,1,43);
        db.prices(14,2,45);
        db.prices(14,3,45);
        db.prices(14,4,45);

        db.prices(15,1,46);
        db.prices(15,2,46);
        db.prices(15,3,45);
        db.prices(15,4,45);
    }
}



