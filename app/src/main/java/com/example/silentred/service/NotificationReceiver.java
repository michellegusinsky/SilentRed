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
import static com.example.silentred.common.Constants.intentNotificationReceivedAction;
import static com.example.silentred.common.Constants.intentStopButtonClickedAction;
import static com.example.silentred.common.Constants.preferencesFileName;

public class NotificationReceiver extends BroadcastReceiver {



    public static Boolean countDownEnable = true; // enable only 1 count down thread at a time

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(RedColorNotificationListenerService.TAG,"MainActivity.NotificationReceiver: onReceive()-start");

        String action = intent.getAction();
        // notification received
        if (action.equals(intentNotificationReceivedAction)) {
            // turn on camera led light
            FlashLightManager flashLightManager = new FlashLightManager(context);
            Thread flashTread = new Thread(flashLightManager);
            flashTread.start();
            if ( countDownEnable ) { // this checked to avoid more than 1 count down clock
                CountDownManager countDownManager = new CountDownManager(context);
                Thread countDownThread = new Thread(countDownManager);
                countDownThread.start();
            }
            sendingSMS(context);

        }
        // stop button clicked received
        if(action.equals(intentStopButtonClickedAction)) {
            FlashLightManager.stopBlink();
        }

        Log.i(RedColorNotificationListenerService.TAG,"MainActivity.NotificationReceiver: onReceive()-end");
    }

    private void sendingSMS(Context context) {

        SettingFrag.sp = context.getSharedPreferences(preferencesFileName, MODE_PRIVATE);
        String contactName = SettingFrag.sp.getString("EmergencyName", "");
        String contactNumber = SettingFrag.sp.getString("EmergencyNumber", "");
        String userName = SettingFrag.sp.getString("userName", "");

        if (contactName == null || contactNumber == null || userName == null) return;
        if (contactName.isEmpty() || contactNumber.isEmpty() || userName.isEmpty()) return;


        if (ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {

            String smsContent =  "hello " + contactName +
                    " , you are the emergency contact of " +
                    userName + " and "+ userName +" need your help";

            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(contactNumber, null, smsContent, null, null);

            Toast.makeText(context, "SMS massage has sent to emergency contact", Toast.LENGTH_SHORT).show();
        }
       else{
            Log.i(RedColorNotificationListenerService.TAG,"there is no permission for sending sms");
        }
    }
}
