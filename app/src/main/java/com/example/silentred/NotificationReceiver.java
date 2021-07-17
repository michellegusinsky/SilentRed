package com.example.silentred;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class NotificationReceiver extends BroadcastReceiver {
    public static final String intentAction = "com.example.silentred.NOTIFICATION_LISTENER";


    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(RedColorNotificationListenerService.TAG,"MainActivity.NotificationReceiver: onReceive()-start");
        String temp = intent.getStringExtra("notification_event") + "\n";
        System.out.println(temp);
        Log.i(RedColorNotificationListenerService.TAG,"MainActivity.NotificationReceiver: onReceive()-end");
    }
}
