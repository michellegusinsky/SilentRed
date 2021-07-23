package com.example.silentred.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import androidx.annotation.RequiresApi;

public class NotificationReceiver extends BroadcastReceiver {
    public static final String intentNotificationReceivedAction = "com.example.silentred.NOTIFICATION_LISTENER";
    public static final String intentStopButtonClickedAction = "com.example.silentred.STOP_BUTTON_CLICKED";
    private FlashLightManager flashLightManager;
    private CountDownManager countDownManager;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(RedColorNotificationListenerService.TAG,"MainActivity.NotificationReceiver: onReceive()-start");
        String action = intent.getAction();
        // notification received
        if (action.equals(NotificationReceiver.intentNotificationReceivedAction)) {
            // turn on camera led light
            flashLightManager = new FlashLightManager(context, 50); // TODO: get the delayBlink according to user settings
            Thread flashTread = new Thread(flashLightManager);
            flashTread.start();
            countDownManager = new CountDownManager();
            Thread countDownThread = new Thread(countDownManager);
            countDownThread.start();
        }
        // stop button clicked received
        if(action.equals(NotificationReceiver.intentStopButtonClickedAction)) {
            flashLightManager.stopBlink();
        }

        Log.i(RedColorNotificationListenerService.TAG,"MainActivity.NotificationReceiver: onReceive()-end");
    }
}
