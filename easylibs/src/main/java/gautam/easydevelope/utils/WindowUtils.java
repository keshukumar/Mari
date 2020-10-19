package gautam.easydevelope.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by gautam on 2/16/2017.
 */

public class WindowUtils {
    private Context mContext;
    private WindowManager windowManager;
    private DisplayMetrics displayMetrics;

    private int windowWidth;
    private int windowHeight;


    public WindowUtils(Context mContext) {
        this.mContext = mContext;
        windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
    }

    public static WindowUtils with(Context context) {
        return new WindowUtils(context);
    }

    public int getWindowWidth() {
        windowWidth = displayMetrics.widthPixels;
        return windowWidth;
    }

    public int getWindowHeight() {
        windowHeight = displayMetrics.heightPixels;
        return windowHeight;
    }

    public void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) mContext.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getApplicationWindowToken(), 0);
    }

    public void hideKeyboardFromView(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) mContext.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
    }

    boolean result = false;

    public boolean isSoftKeyboardVisible(final Activity activity) {
        activity.getCurrentFocus().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                Rect r = new Rect();
                activity.getCurrentFocus().getWindowVisibleDisplayFrame(r);
                int screenHeight = activity.getCurrentFocus().getRootView().getHeight();

                // r.bottom is the position above soft keypad or device button.
                // if keypad is shown, the r.bottom is smaller than that before.
                int keypadHeight = screenHeight - r.bottom;

//                Log.d(TAG, "keypadHeight = " + keypadHeight);

                if (keypadHeight > screenHeight * 0.15) { // 0.15 ratio is perhaps enough to determine keypad height.
                    result = true;
                } else {
                    result = false;
                }
            }
        });
        return result;
    }

    public float dpToPx(float dp){
        Resources resources = mContext.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    public float pxToDp(float px){
        Resources resources = mContext.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }

}
