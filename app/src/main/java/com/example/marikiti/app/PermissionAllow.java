package com.example.marikiti.app;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.util.Log;

import gautam.easydevelope.permissions.MPermission;


public class PermissionAllow {


    public static void allowAll(Context context, Activity activity) {
        final String TAG = "PermissionAllow";
        if (MPermission.isAllPermissionGranted(context, activity,
                new String[]{
                        Manifest.permission.RECEIVE_BOOT_COMPLETED,
                        Manifest.permission.CAMERA,
                        Manifest.permission.ACCESS_WIFI_STATE,
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.MODIFY_PHONE_STATE
                }, 1)) {
            Log.d(TAG, "allowAll() : success ");
//            Toast.makeText(context, "Audio Permission on", Toast.LENGTH_SHORT).show();
        } else {
            Log.e(TAG, "allowAll() : not success ");
//            Toast.makeText(context, "All Permission off", Toast.LENGTH_SHORT).show();

        }
    }


    public static boolean location(Context context, Activity activity) {
        final String TAG = "PermissionAllow";
        if (MPermission.isAllPermissionGranted(context, activity,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1)) {
            Log.w(TAG, "location: allowed" );
            return true;
        } else {
            Log.w(TAG, "location: not allowed" );
            return false;
        }
    }

    public static boolean cameraAndStorage(Context context, Activity activity) {
        final String TAG = "PermissionAllow";
        if (MPermission.isAllPermissionGranted(context, activity,
                new String[]{
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, 1)) {
            Log.w(TAG, "cameraAndStorage: allowed" );
            return true;
        } else {
            Log.w(TAG, "cameraAndStorage: not allowed" );
            return false;
        }
    }

    public static boolean network(Context context, Activity activity) {
        final String TAG = "PermissionAllow";
        if (MPermission.isAllPermissionGranted(context, activity,
                new String[]{
                        Manifest.permission.INTERNET,
                        Manifest.permission.ACCESS_NETWORK_STATE
                }, 1)) {
            Log.w(TAG, "network: allowed" );
            return true;
        } else {
            Log.w(TAG, "network: not allowed" );
            return false;
        }
    }

    public static boolean phoneCall(Context context, Activity activity) {
        final String TAG = "PermissionAllow";
        if (MPermission.isAllPermissionGranted(context, activity, new String[]{Manifest.permission.CALL_PHONE}, 1)) {
            Log.w(TAG, "phoneCall: allowed" );
            return true;
        } else {
            Log.w(TAG, "phoneCall: not allowed" );
            return false;
        }
    }
}
