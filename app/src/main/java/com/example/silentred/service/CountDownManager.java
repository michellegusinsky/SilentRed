package com.example.silentred.service;

import android.content.Context;

import com.example.silentred.activities.MainActivity;
import com.example.silentred.activities.SettingFrag;
import com.example.silentred.model.Area;
import com.example.silentred.xml.LoadAreasXML;

import java.util.ArrayList;

public class CountDownManager implements Runnable{
    private Context context;
    public CountDownManager(Context context) {
        this.context=context;
    }

    @Override
    public void run() {

        NotificationReceiver.countDownEnable=false;
        Integer time = new Integer(0);//=new Integer(timeToRunAway);
        String[] arr=getTime().split(":",3);
        int state=0;
        for (String a : arr){
            switch(state){
                case 0:
                    time+=new Integer(a);
                    time*=360;
                    state++;
                    break;
                case 1:
                    time+=new Integer(a);
                    time*=60;
                    state++;
                    break;
                case 2:
                    time+=new Integer(a);
                    state++;
                    break;
            }
        }
        startConnDown(time);
    }
    public void startConnDown(Integer time)
    {
        int ttime=time;
        int hr,mn,sc;
        while(ttime>0)
        {
            hr=ttime/360;
            mn=(ttime-hr)/60;
            sc=(ttime-hr)%60;
            MainActivity.countDownBtn.setText(hr+":"+mn+":"+sc);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ttime=ttime-1;
        }
        MainActivity.countDownBtn.setText("RUN!");
        NotificationReceiver.countDownEnable=true;
    }

    public String getTime()
    {
        ArrayList<Area> arrayArea = LoadAreasXML.parseAreas(context);
        ArrayList<String> arreyTime=new ArrayList<String>();
        //String[] barArea=arrayArea.toArray(new String[arrayArea.size()]);
        int i=0;
        for(Area area:arrayArea){
            arreyTime.add(area.getTime());
        }
        String[] barArea=arreyTime.toArray(new String[arreyTime.size()]);
        return barArea[SettingFrag.sp.getInt("AreaPos",0)];
    }
}
