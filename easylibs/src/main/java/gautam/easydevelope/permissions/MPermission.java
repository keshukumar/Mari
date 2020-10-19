package gautam.easydevelope.permissions;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;


public class MPermission implements ActivityCompat.OnRequestPermissionsResultCallback {
    static Context mContext;
    static Activity activity;
    static String[] currentPermission;


    public static boolean isAllPermissionGranted(Context context, Activity activity, String[] permissions, int requestCode) {
        MPermission.activity = activity;
        mContext = context;
        boolean isAllAllowed = false;
        MPermission.currentPermission = permissions;

       loop: for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(mContext, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                Log.w("Custom permission", "isAllPermissionGranted: checking for "+ permissions[i]);
                isAllAllowed = false;
                ActivityCompat.requestPermissions(activity, permissions, requestCode);
                break loop;
            } else {
                Log.w("Custom permission", "isAllPermissionGranted: success "+ permissions[i]);
                isAllAllowed = true;
            }
        }

        return isAllAllowed;

    }


    public static boolean isPermissionGranted(Context context, String permission) {
        if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0) {
            for (String permission : permissions) {

                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    continue;
                    //Do the stuff that requires permission...
                } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                        Log.w("Permission", "onRequestPermissionsResult: this permission id denied "+permission);
                    } else {
                        Toast.makeText(mContext, "This feature is not activate.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }


}
