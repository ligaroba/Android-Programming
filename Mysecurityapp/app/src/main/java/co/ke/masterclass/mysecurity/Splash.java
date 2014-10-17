package co.ke.masterclass.mysecurity;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

	public class Splash extends Activity {
		long Delay=8000;

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.splash);
			Timer timer=new Timer();
			TimerTask showsplash=new TimerTask(){
				public void run(){
					finish();
					Intent dashboard=new Intent(Splash.this,Securitymain.class);
					startActivity(dashboard);
				}
			};
			timer.schedule(showsplash, Delay);
			
		}

	}



