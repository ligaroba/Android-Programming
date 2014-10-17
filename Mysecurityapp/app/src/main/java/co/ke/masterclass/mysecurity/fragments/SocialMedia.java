package co.ke.masterclass.mysecurity.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;

import co.ke.masterclass.mysecurity.R;

/**
 * Created by root on 10/14/14.
 */
public class SocialMedia extends Fragment implements View.OnClickListener {
View view;
Button fb,twitter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.socialmedia, container, false);
        fb= (Button) view.findViewById(R.id.btnfacebook);
        twitter= (Button) view.findViewById(R.id.btntwitter);

        fb.setOnClickListener(this);
        twitter.setOnClickListener(this);



        return  view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
           // https://play.google.com/store/apps/details?id=com.MyRoad.MyRoad&hl=en

            case R.id.btntwitter:
                //share apps url on twitter



                break;
            case R.id.btnfacebook:

                // share apps url on facebook
            break;
            default:

        }
    }
}
