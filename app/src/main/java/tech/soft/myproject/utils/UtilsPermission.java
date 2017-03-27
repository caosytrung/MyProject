package tech.soft.myproject.utils;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by dee on 26/03/2017.
 */

public class UtilsPermission {
    public static boolean checkPermission(AppCompatActivity compatActivity ,
                                          int requestCode){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            return  true;
        }else if (ActivityCompat.
                checkSelfPermission(compatActivity, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(compatActivity,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},requestCode);
            return false;
        }
        return  true;

    }
}
