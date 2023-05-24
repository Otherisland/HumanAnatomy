package com.bn.hk.humananatomy.thread;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import com.bn.hk.humananatomy.activity.WelcomeActivity;

public class StartThread extends Thread
{
    WelcomeActivity welcomeActivity;
    public StartThread(WelcomeActivity welcomeActivity)
    {
        this.welcomeActivity=welcomeActivity;
    }
    public void run()
    {
        while(true){
            int permission = ActivityCompat.checkSelfPermission(welcomeActivity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (permission == PackageManager.PERMISSION_GRANTED) {
                try{
                    Thread.sleep(1000);
                }catch (Exception e)
                {
                    e.printStackTrace();
                }

                welcomeActivity.record();
                break;
            }
            }
    }

}
