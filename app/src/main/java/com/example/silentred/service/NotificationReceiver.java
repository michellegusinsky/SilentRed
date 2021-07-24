package com.example.silentred.service;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.example.silentred.activities.SettingFrag;

import static android.content.Context.MODE_PRIVATE;

public class NotificationReceiver extends BroadcastReceiver {

    public static final String intentNotificationReceivedAction = "com.example.silentred.NOTIFICATION_LISTENER";
    public static final String intentStopButtonClickedAction = "com.example.silentred.STOP_BUTTON_CLICKED";

    public static Boolean countDownEnable=true; // enable only 1 count down thread at a time

    static final String fileName =("myPreferencesFile");

    private FlashLightManager flashLightManager;
    private CountDownManager countDownManager;
    private String contactNumber;
    private String contactName;
    private String userName;

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
            if(countDownEnable) {
                countDownManager = new CountDownManager();
                Thread countDownThread = new Thread(countDownManager);
                countDownThread.start();
            }
            sendingSMS(context);

        }
        // stop button clicked received
        if(action.equals(NotificationReceiver.intentStopButtonClickedAction)) {
            flashLightManager.stopBlink();
        }

        Log.i(RedColorNotificationListenerService.TAG,"MainActivity.NotificationReceiver: onReceive()-end");
    }
    public void sendingSMS(Context context) {

        SettingFrag.sp = context.getSharedPreferences(fileName, MODE_PRIVATE);
        contactName = SettingFrag.sp.getString("EmergencyName", "");
        contactNumber = SettingFrag.sp.getString("EmergencyNumber", "");
        userName = SettingFrag.sp.getString("userName","");
        if (contactName == null || contactNumber == null || userName == null) return;
        if (contactName.isEmpty() || contactNumber.isEmpty() || userName.isEmpty()) return;


        if (ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(contactNumber, null, "hello " + contactName + ", you are the emergency contact of" + userName + "and "+userName+" need your help", null, null);

            Toast.makeText(context, "SMS massage has sent to emergency contact", Toast.LENGTH_SHORT).show();
        }
       else{
            System.out.println("#$%$%#^    there is no permission for sending sms");
        }
    }
}
