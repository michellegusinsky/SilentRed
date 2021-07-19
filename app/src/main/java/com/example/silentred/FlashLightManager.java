package com.example.silentred;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class FlashLightManager implements Runnable{
    // TODO: maybe needed to be changed in the future to interrupt
    private static boolean stopButtonClicked; // this is static member because we want to stop the blinking tread outside the object

    private ArrayList<String> cameraWithFlashIds;
    private final CameraManager camManager;
    private long delayBlink;

    public FlashLightManager(Context context, long delayBlink){
        camManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        this.delayBlink = delayBlink;
        cameraWithFlashIds = new ArrayList<>();
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
                Thread.sleep(delayBlink);
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
