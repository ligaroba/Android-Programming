package com.example.splash;

import java.util.Timer;
import java.util.TimerTask;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

public class Splash extends ActionBarActivity {
	long Delay=8000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash);
		Timer timer=new Timer();
		TimerTask showsplash=new TimerTask(){
			public void run(){
				finish();
				Intent dashboard=new Intent(Splash.this,MainDashboard.class);
				startActivity(dashboard);
			}
		};
		timer.schedule(showsplash, Delay);
		
	}

}
