package com.sdut.mynewmedia.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;

public class PermisionUtils extends AppCompatActivity {

    private static List<String> permissionList = new ArrayList<>();
    private static final int PG = PackageManager.PERMISSION_GRANTED;
    private static boolean flag = true;


    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;

    /**
     * Checks if the app has permission to write to device storage
     * If the app does not has permission then the user will be prompted to
     * grant permissions
     *
     * @param activity
     */
    public static boolean verifyStoragePermissions(Activity activity) {

        // Check if we have write permission
        int permissionLocation = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int permissionStorage = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionState = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.READ_PHONE_STATE);
        int permissionCamear = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.CAMERA);

        if(permissionLocation != PG || permissionState != PG || permissionStorage != PG ||permissionCamear != PG){
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            permissionList.add(Manifest.permission.CAMERA);
        }
        if(!permissionList.isEmpty()){
            String [] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(activity, permissions, REQUEST_EXTERNAL_STORAGE);
            flag = false;
        }
        return flag;
    }

}