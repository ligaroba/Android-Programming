package co.ke.masterclass.mysecurity.custom;

import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by root on 9/23/14.
 */
public class Validation {

    public Validation(){


    }

    public boolean checkEmpty( EditText edt){

       if(edt.length()<=0) {
           return true;

       }
        return false;
    }
}
