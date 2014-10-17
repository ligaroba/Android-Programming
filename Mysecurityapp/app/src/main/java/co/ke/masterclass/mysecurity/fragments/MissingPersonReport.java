package co.ke.masterclass.mysecurity.fragments;


import android.app.Activity;
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

import co.ke.masterclass.mysecurity.R;
import co.ke.masterclass.mysecurity.custom.Alertbuildercustom;
import co.ke.masterclass.mysecurity.custom.Validation;


public class MissingPersonReport extends Fragment implements OnClickListener {
    View view;
    private Button btnaudio, btnpic, btnvideo, btnsend;
    private EditText indescription,inpersonname,inid;
    private Validation validate = new Validation();
    private Spinner genderspin;
    private Alertbuildercustom alert;
    String message;
    String gender;
    String des;

    private int id, itemclicked;


    public static interface OnMissingReportItemclick {
        public void ButtonPressed(int id,int crop);

        public void DataMissingPassed(String name,String identity,String gender, String description);
    }

    private OnMissingReportItemclick listener;

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        try {
            listener = (OnMissingReportItemclick) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement AuthenticationDialogListener");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.reportmissing, container, false);



        indescription = (EditText) view.findViewById(R.id.edcrimedescription);
        inpersonname = (EditText) view.findViewById(R.id.editpersonname);
        inid = (EditText) view.findViewById(R.id.editidentity);
        genderspin= (Spinner) view.findViewById(R.id.spinemerselectpersongender);
        indescription.requestFocus();

        btnaudio = (Button) view.findViewById(R.id.btnaudio);
        btnpic = (Button) view.findViewById(R.id.btnpic);
        btnsend = (Button) view.findViewById(R.id.btnsendcrime);
        btnvideo = (Button) view.findViewById(R.id.btnvideo);
        genderspin.setOnItemSelectedListener(new MyCustomClickListener());

        btnaudio.setOnClickListener(this);
        btnpic.setOnClickListener(this);
        btnvideo.setOnClickListener(this);
        btnsend.setOnClickListener(this);



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
                listener.ButtonPressed(id,1);
                break;
            case R.id.btnvideo:
                id = 3;
                listener.ButtonPressed(id,0);

                break;
            case R.id.btnsendcrime:

                if (validate.checkEmpty(indescription) == true) {
                    message = "Please other details";
                } else if (validate.checkEmpty(inid) == true) {
                    message = "Please Enter id";
                }else if (validate.checkEmpty(inpersonname) == true) {
                    message = "Please enter person name";
                }
                else {
                    des = indescription.getText().toString();
                    indescription.setHint(R.string.persondescrip);
                    listener.DataMissingPassed(inpersonname.getText().toString(),inid.getText().toString(),gender,des);
                }
            default:
                break;


        }


    }
    private class MyCustomClickListener implements AdapterView.OnItemSelectedListener {



        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
            switch (parent.getId()) {
                case R.id.spinemerselectpersongender:
                    String[] choice = getResources().getStringArray(R.array.gender);


                    if(choice[position]!=null && choice[position]!=""){
                        gender=choice[position];
                    }

                    break;

            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }


}
