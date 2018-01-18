package com.guanzhuli.dayday.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

/**
 * Created by Guanzhu Li on 1/17/2018.
 */

public class PermissionUtil {
    public static final int READ_EXST = 0x1;
    public static final int PICK_IMAGE = 0x2;

    public static void askForPermission(String permission, Integer requestCode, Context context) {
        if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, permission)) {
                ActivityCompat.requestPermissions((Activity) context, new String[]{permission}, requestCode);
            } else {
                ActivityCompat.requestPermissions((Activity) context, new String[]{permission}, requestCode);
            }
        } else {
            Toast.makeText( context, "" + permission + " is already granted.", Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean hasPermission(Context context, String permission){
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }
}