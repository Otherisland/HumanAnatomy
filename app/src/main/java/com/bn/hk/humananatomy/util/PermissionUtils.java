package com.bn.hk.humananatomy.util;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import com.bn.hk.humananatomy.activity.WelcomeActivity;
import com.bn.hk.humananatomy.thread.StartThread;

import java.io.File;

public class PermissionUtils {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};


    /**
     * Checks if the app has permission to write to device storage
     * If the app does not has permission then the user will be prompted to
     * grant permissions
     *
     * @param activity
     */
    public static void verifyStoragePermissions(WelcomeActivity activity) {

        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);

            permission=ActivityCompat.checkSelfPermission(activity,
                    Manifest.permission.READ_EXTERNAL_STORAGE);
            if (permission !=PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                        REQUEST_EXTERNAL_STORAGE);
               // ActivityCompat.getPermissionCompatDelegate();
                ActivityCompat.getPermissionCompatDelegate();
                StartThread startThread=new StartThread(activity);
                startThread.run();
                System.out.println("          startThread 开始：：：：：");
            }

        }else{
            String fnn=activity.getExternalFilesDir(null)+"/"+"HumanAnatomy";
            File file = new File(fnn);
            if(!file.exists()) {
                activity.record(); // 如果文件夹不存在，创建,
            }
            activity.jump();
        }
    }
}

