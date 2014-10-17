package co.ke.masterclass.mysecurity.brodcast;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

import co.ke.masterclass.mysecurity.backgroundservice.GCMservice;

/**
 * Created by root on 10/15/14.
 */
public class GCMBroadcastReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ComponentName comp = new ComponentName(context.getPackageName(),GCMservice.class.getName());
        startWakefulService(context, (intent.setComponent(comp)));
        setResultCode(Activity.RESULT_OK);
    }

  /*  public static void completeWakefulIntent(Intent intent) {
    }*/
}
