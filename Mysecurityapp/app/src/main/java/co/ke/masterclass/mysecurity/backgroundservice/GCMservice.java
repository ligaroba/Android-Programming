package co.ke.masterclass.mysecurity.backgroundservice;

/**
 * Created by root on 10/15/14.
 */

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

;import com.google.android.gms.gcm.GoogleCloudMessaging;

import co.ke.masterclass.mysecurity.R;
import co.ke.masterclass.mysecurity.Securitymain;
import co.ke.masterclass.mysecurity.brodcast.GCMBroadcastReceiver;

public class GCMservice extends IntentService {
    public static final int NOTIFICATION_ID=1;
    private NotificationManager mNotification;
    NotificationCompat.Builder builder;

    private String TAG="GCMservice";


    public GCMservice(){
        super("GCMservice");

    }


    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras =intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        String messageType= gcm.getMessageType(intent);
        if(!extras.isEmpty()){
            if(GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equalsIgnoreCase(messageType)){
                sendNotification("Send Error:" + extras.toString());
            }else if(GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equalsIgnoreCase(messageType)){
                sendNotification("Deleted message from server: "+ extras.toString());
            }else if(GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equalsIgnoreCase(messageType)){

                for (int i = 0; i < 3; i++) {
                    Log.e(TAG,
                            "Working... " + (i + 1) + "/5 @ "
                                    + SystemClock.elapsedRealtime());
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                    }

                }
                Log.e(TAG, "Completed work @ " + SystemClock.elapsedRealtime());

                sendNotification("Recieved: "+messageType);
            }
        }
        GCMBroadcastReceiver.completeWakefulIntent(intent);

    }
    private void sendNotification(String msg) {
        Log.d(TAG, "Preparing to send notification...: " + msg);
        mNotification = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, Securitymain.class), 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                this).setSmallIcon(R.drawable.ic_policeman)
                .setContentTitle("GCM Notification")
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                .setContentText(msg);

        mBuilder.setContentIntent(contentIntent);
        mNotification.notify(NOTIFICATION_ID, mBuilder.build());
        Log.d(TAG, "Notification sent successfully.");
    }


}
