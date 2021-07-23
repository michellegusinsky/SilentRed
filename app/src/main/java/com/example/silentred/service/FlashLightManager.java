package com.example.silentred.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.silentred.R;
import com.example.silentred.activities.SettingFrag;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class FlashLightManager implements Runnable{
    // TODO: maybe needed to be changed in the future to interrupt
    private static boolean stopButtonClicked; // this is static member because we want to stop the blinking tread outside the object

    private ArrayList<String> cameraWithFlashIds;
    private final CameraManager camManager;
    private long delayBlink=500;
    private Context context;

    static final String fileName =("myPreferencesFile");

    public FlashLightManager(Context context, long delayBlink){
        this.context=context;
        camManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        //this.delayBlink = delayBlink;
        cameraWithFlashIds = new ArrayList<>();
        SettingFrag.sp =context.getSharedPreferences(fileName,MODE_PRIVATE);
        this.delayBlink/=SettingFrag.sp.getInt("flashPerSecond",3);
        try {
            String[] cameraIds = camManager.getCameraIdList();
            for (String camera : cameraIds){
                // saves only the cameras with flash unit
                if(camManager.getCameraCharacteristics(camera).get(CameraCharacteristics.FLASH_INFO_AVAILABLE))
                {
                    cameraWithFlashIds.add(camera);
                }
            }
        }catch (CameraAccessException e) {
            e.printStackTrace();
            // TODO: Add dialog
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void turnOff(){
        try {
            for(String cameraId : cameraWithFlashIds){
                // turn off camera led light.
                camManager.setTorchMode(cameraId, false);
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void turnOn(){
        try {
            for(String cameraId : cameraWithFlashIds) {
                camManager.setTorchMode(cameraId, true);   //Turn ON
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void blink(){
        int i=1;
        stopButtonClicked = false;
        boolean lightOn = false;
        while (!stopButtonClicked){ // TODO: change this to interrupt maybe
            if (lightOn){
                turnOff();
                lightOn = false;
            }
            else {
                turnOn();
               System.out.println("-----   Blink   -----");
               System.out.println("      "+i+"       ");
               i++;
                lightOn = true;
            }try{
                Thread.sleep(this.delayBlink);
            }catch (InterruptedException e){
                // TODO: CHECK WHEN SLEEP THROWS EXCEPTION
                e.printStackTrace();
            }
        }

    }

    public static void stopBlink() {
        stopButtonClicked = true;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void run() {
        blink();
        turnOff();
    }
}
