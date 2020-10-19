package com.example.marikiti.testing;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Build;

import java.util.Objects;

public class InternetConnection {
    public static boolean checkConnection(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return ((ConnectivityManager) Objects.requireNonNull(context.getSystemService
                    (Context.CONNECTIVITY_SERVICE))).getActiveNetworkInfo() != null;
        }
        return false;
    }
}
