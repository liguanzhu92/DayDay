package com.guanzhuli.dayday.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Guanzhu Li on 2/10/2017.
 */
public class Repeat extends BroadcastReceiver {

    public static String NOTIFICATION_ID = "notification-id";
    public static String NOTIFICATION = "notification";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("notification", "call on receive");
        if (intent.getAction().equals("android.media.action.DISPLAY_NOTIFICATION")) {
            Log.d("notification", "display confirmed");
            // Set the alarm here.
            NotificationManager mNotificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
            int id = intent.getIntExtra(NOTIFICATION_ID, 0);
            Log.d("notification", ""+id);
            Notification notification = intent.getParcelableExtra(NOTIFICATION);
            mNotificationManager.notify(id, notification);
        }
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            Log.d("notification", "boot confirmed");
        }
        if (intent.getAction().equals("android.intent.action.AIRPLANE_MODE")) {
            Log.d("notification", "airplane confirmed");
            Toast.makeText(context, "airplane", Toast.LENGTH_LONG).show();
        }
    }
}
