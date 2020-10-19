package com.example.marikiti.app;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;
import androidx.multidex.MultiDex;
import androidx.appcompat.app.AppCompatDelegate;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.marikiti.R;

import gautam.easydevelope.data.AppPrefs;
import gautam.easydevelope.java.GThreadManager;
import gautam.easydevelope.widget.GautamDialog;


public class Marikiti extends Application {

    public final String TAG = getClass().getSimpleName();

    private static Marikiti sInstance;
    private AppPrefs prefs;
    private RequestQueue mRequestQueue;
    private Context context;
    private static GautamDialog gautamDialog;

    public Context getContext() {
        return context;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // initialize the singleton
        sInstance = this;
        context = getApplicationContext();
        prefs = new AppPrefs(this);
        AppPrefs.init(this);
        GThreadManager.init(this);
        /*FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);*/
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        Log.d(TAG, "onCreate: ********************* Application Created ******************************");
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }


    public <T> void addToRequestQueue(Request<T> req) {
        // set the default tag if tag is empty
        req.setTag(TAG);
        // set max retries
        int maxRetries = 10;
        int socketTimeOut = 100 * 100 * 10;
        req.setRetryPolicy(new DefaultRetryPolicy(socketTimeOut, maxRetries, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        getRequestQueue().add(req);
    }

    /**
     * @return ApplicationController singleton instance
     */
    public static synchronized Marikiti getInstance() {
        return sInstance;
    }

    // Checking for all possible internet providers
    public boolean isConnectingToInternet() {

        ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }

    public AppPrefs getPrefs() {
        return prefs;
    }

    public void showNetworkError(Context context, final String errorMessage) {
        Log.w(TAG, "showNetworkError: network error dialog");
        if (gautamDialog != null)
            if (gautamDialog.isShowing()) return;
            else Log.w(TAG, "showNetworkError: dialog not showing creating new and showing");
        else Log.w(TAG, "showNetworkError: dialog is null  creating new ");
        gautamDialog = GautamDialog.with(context).addView(new GautamDialog.GViewAdder() {
            @Override
            public View defineView(LayoutInflater layoutInflater) {
                View view = layoutInflater.inflate(R.layout.dialog_connection_error, null);
                TextView tv_error_message = (TextView) view.findViewById(R.id.tv_error_message);
                if (errorMessage != null)
                    tv_error_message.setText(errorMessage);
                else tv_error_message.setText(getString(R.string.internet_error_message));
                return view;
            }
        }).setGravity(Gravity.CENTER).show();
    }
}
