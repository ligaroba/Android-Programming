package co.ke.masterclass.mysecurity.custom;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

/**
 * Created by root on 9/23/14.
 */

public class Alertbuildercustom {
    Context context;
    ConstantsClass cont;
    public Alertbuildercustom(Context context){
        this.context=context;

    }



    public void showAlertDialog(String title,final CharSequence[] option) {



        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle( title);

        builder.setItems(option, new DialogInterface.OnClickListener() {


            public void onClick(DialogInterface dialog, int item) {


                if (option[item].equals("Take a Photo"))

                {
                   cont.ITEMCLICKED=1;
                   // setItemClicked(1);
                    Toast.makeText(context,"Take photo" + cont.ITEMCLICKED, Toast.LENGTH_SHORT).show();

                } else if (option[item].equals("Choose from Gallery"))

                {
                    //setItemClicked(2);
                    cont.ITEMCLICKED=2;
                    Toast.makeText(context,"Take Gallary clicked" , Toast.LENGTH_SHORT).show();

                }
                if (option[item].equals("Record Video"))

                {

                    //setItemClicked(4);
                    cont.ITEMCLICKED=4;

                } else if (option[item].equals("Choose a Video"))

                {


                    //setItemClicked(5);
                    cont.ITEMCLICKED=5;


                }
                if (option[item].equals("Record Audio"))

                {

                    //setItemClicked(6);
                    cont.ITEMCLICKED=6;

                } else if (option[item].equals("Choose an Audio file"))

                {

                    //setItemClicked(7);
                    cont.ITEMCLICKED=7;

                } else if (option[item].equals("Cancel")) {
                   // setItemClicked(3);
                    cont.ITEMCLICKED=3;

                    //dialog.dismiss();

                }



            }

        });

        builder.show();



    }

}

