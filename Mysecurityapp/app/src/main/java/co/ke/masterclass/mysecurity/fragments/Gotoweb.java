package co.ke.masterclass.mysecurity.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.net.URL;

import co.ke.masterclass.mysecurity.R;

/**
 * Created by root on 10/14/14.
 */
public class Gotoweb extends Fragment{
View view;
WebView webview;
    String url;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.webbviewlayout, container, false);
        Bundle burl=getArguments();
        url=burl.getString("url");
        webview= (WebView) view.findViewById(R.id.webView);
        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);

        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        webview.loadUrl(url);


        return  view;
    }
}
