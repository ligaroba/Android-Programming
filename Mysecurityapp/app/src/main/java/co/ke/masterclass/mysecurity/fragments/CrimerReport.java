package co.ke.masterclass.mysecurity.fragments;


import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import co.ke.masterclass.mysecurity.R;
import co.ke.masterclass.mysecurity.custom.Alertbuildercustom;
import co.ke.masterclass.mysecurity.custom.Validation;


public class CrimerReport extends Fragment implements OnClickListener {
    View view;
    private Button btnaudio, btnpic, btnvideo, btnsend;
    private Spinner emrgspin;
    private CheckBox inanonimity;
    private EditText indescription;
    private Validation validate = new Validation();
    private Alertbuildercustom alert;
    String message;
    String crime;
    String des;
    String statusChoice;
    String emergency;

    private int id, itemclicked;


    public static interface OnCrimeReportItemclick {
        public void ButtonPressed(int id,int crop);

        public void DataPassed(String crime, String description, boolean anonimity, String emercode);
    }

    private OnCrimeReportItemclick listener;

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        try {
            listener = (OnCrimeReportItemclick) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement AuthenticationDialogListener");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.reportcrime, container, false);
        Bundle bundle = getArguments();
        crime = bundle.getString("Crime");


        indescription = (EditText) view.findViewById(R.id.edcrimedescription);
        indescription.requestFocus();
        inanonimity = (CheckBox) view.findViewById(R.id.chbanonimous);
        btnaudio = (Button) view.findViewById(R.id.btnaudio);
        btnpic = (Button) view.findViewById(R.id.btnpic);
        btnsend = (Button) view.findViewById(R.id.btnsendcrime);
        btnvideo = (Button) view.findViewById(R.id.btnvideo);
        emrgspin = (Spinner) view.findViewById(R.id.spinemerselect);
      //  emrgspin.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        btnaudio.setOnClickListener(this);
        btnpic.setOnClickListener(this);
        btnvideo.setOnClickListener(this);
        btnsend.setOnClickListener(this);
        emrgspin.setOnItemSelectedListener(new MyCustomClickListener());


        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }



    @Override
    public void onClick(View v) {

        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.btnaudio:
                id = 1;
                listener.ButtonPressed(id,0);
                break;
            case R.id.btnpic:
                id = 2;
                listener.ButtonPressed(id,0);
                break;
            case R.id.btnvideo:
                id = 3;
                listener.ButtonPressed(id,0);

                break;
            case R.id.btnsendcrime:

                if (validate.checkEmpty(indescription) == true) {
                    message = "Please Enter Crime Description";
                } else {
                    des = indescription.getText().toString();

                if(statusChoice.equalsIgnoreCase("Normal")){
                        emergency="0";
                    }else{
                        emergency="1";
                    }


                    if (inanonimity.isChecked()) {

                        listener.DataPassed(crime, des, true, emergency);
                        indescription.setHint(R.string.crimedescrip);

                    } else {

                        listener.DataPassed(crime, des, false, emergency);
                        indescription.setHint(R.string.crimedescrip);

                    }
                }
                break;

            default:
                break;


        }


    }

    private class MyCustomClickListener implements AdapterView.OnItemSelectedListener {



        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
            switch (parent.getId()) {
                case R.id.spinemerselect:
                    String[] choice = getResources().getStringArray(R.array.emegencylist);


                    if(choice[position]!=null && choice[position]!=""){
                        statusChoice=choice[position];
                    }

                    break;

            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }
}
