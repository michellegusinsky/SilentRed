package com.example.silentred.service;

import android.content.Intent;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

public class RedColorNotificationListenerService extends NotificationListenerService {
    public static final String TAG = "silentred";
    private final String redColorAppSubTextKey = "android.subText";
    private final String redColorAppTextKey = "android.text";
    private final String intentExtraNotificationEventKey = "notification_event";
    public static boolean isNotificationAccessEnabled = false;

    @Override
    public void onListenerConnected() {
        isNotificationAccessEnabled = true;
        super.onListenerConnected();
    }
    @Override
    public void onListenerDisconnected() {
        isNotificationAccessEnabled = false;
        super.onListenerDisconnected();
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        Log.i(TAG,"NLService: onNotificationPosted()-start");
        // checks if the notification is from red color app
        String redColorAppName = "redcolor";
        if (sbn.getPackageName().contains(redColorAppName)) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                String notificationSubText = sbn.getNotification().extras.get(redColorAppSubTextKey).toString();
                String notificationText = sbn.getNotification().extras.get(redColorAppTextKey).toString();

                Log.i(TAG, "ID :" + sbn.getId() + "\t" + sbn.getNotification().tickerText + "\t" + sbn.getPackageName());
                Intent intent = new Intent(this, NotificationReceiver.class).setAction(NotificationReceiver.intentNotificationReceivedAction);
                intent.putExtra(intentExtraNotificationEventKey, "onNotificationPosted :" + sbn.getPackageName() + "\n" + notificationSubText + "\n" + notificationText +"\n");
                // send to notification receiver for handle the notification
                sendBroadcast(intent);
            }

        }
        Log.i(TAG,"NLService: onNotificationPosted()-end");
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        //  we don't care when notifications are removed
    }
}
