package com.example.silentred.service;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Build;


import androidx.annotation.RequiresApi;

import com.example.silentred.activities.SettingFrag;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class FlashLightManager implements Runnable{
    // TODO: maybe needed to be changed in the future to interrupt
    private static boolean stopButtonClicked; // this is static member because we want to stop the blinking tread outside the object

    private final ArrayList<String> cameraWithFlashIds;
    private final CameraManager camManager;
    private long delayBlink=500;

    static final String fileName =("myPreferencesFile");

    public FlashLightManager(Context context){
        camManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        cameraWithFlashIds = new ArrayList<>();
        SettingFrag.sp =context.getSharedPreferences(fileName,MODE_PRIVATE);
        this.delayBlink /= SettingFrag.sp.getInt("flashPerSecond",3); // default is 3
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
        stopButtonClicked = false;
        boolean lightOn = false;
        while (!stopButtonClicked){ // TODO: change this to interrupt maybe
            if (lightOn){
                turnOff();
                lightOn = false;
            }
            else {
                turnOn();
                lightOn = true;
            }try{
                Thread.sleep(this.delayBlink);
            }catch (InterruptedException e){
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
