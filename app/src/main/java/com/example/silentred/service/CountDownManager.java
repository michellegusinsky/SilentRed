package com.example.silentred.service;

import com.example.silentred.activities.MainActivity;

public class CountDownManager implements Runnable{
    private String timeToRunAway="17";      //need to update from database

    @Override
    public void run() {
        Integer time =new Integer(timeToRunAway);
        startConnDown(time);
    }
    public void startConnDown(Integer time)
    {
        while(time>0)
        {
            MainActivity.countDownBtn.setText(time.toString());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            time=time-1;
        }
        MainActivity.countDownBtn.setText("RUN!");
    }
}
