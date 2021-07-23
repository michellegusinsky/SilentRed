package com.example.silentred.service;

import com.example.silentred.activities.MainActivity;

import java.sql.Time;

public class CountDownManager implements Runnable{
    //Changed to String time
    private String timeToRunAway="17";      //need to update from database
    private Time t= new Time(0,1,30);
    private String tt=t.toString();
    @Override
    public void run() {
        Integer time = new Integer(0);//=new Integer(timeToRunAway);
        String[] arr=tt.split(":",3);
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
    }
}
