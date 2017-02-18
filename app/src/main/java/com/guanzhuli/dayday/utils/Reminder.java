package com.guanzhuli.dayday.utils;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;
import com.guanzhuli.dayday.MainActivity;
import com.guanzhuli.dayday.R;
import com.guanzhuli.dayday.model.DaysList;
import com.guanzhuli.dayday.model.Item;

/**
 * Created by Guanzhu Li on 2/10/2017.
 */
public class Reminder {
    private static Context mContext;
    public Reminder(Context context) {
        mContext = context;
    }

    public void scheduleReminder(int delay, String data) {
        Item item = DaysList.getInstance().get(Integer.parseInt(data));
        Log.d("notification", item.toString());
        Notification.Builder builder = new Notification.Builder(mContext)
                .setSmallIcon(R.drawable.calendar_small)
                .setContentTitle("DayDay")
                .setContentText(item.getTitle());
        Log.d("notification", builder.getExtras().toString());
        Intent intent = new Intent(mContext, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(mContext);
// Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(intent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        builder.setContentIntent(resultPendingIntent);
        Intent notificationIntent = new Intent(mContext, Repeat.class);
        notificationIntent.setAction("android.media.action.DISPLAY_NOTIFICATION");
        //notificationIntent.setAction("android.intent.action.BOOT_COMPLETED");
        notificationIntent.putExtra(Repeat.NOTIFICATION_ID, item.getID());
        notificationIntent.putExtra(Repeat.NOTIFICATION, builder.build());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, 0, notificationIntent,0);
        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, futureInMillis, pendingIntent);
    }
}


