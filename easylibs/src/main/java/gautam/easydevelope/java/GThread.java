package gautam.easydevelope.java;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;

/**
 * Created by gautam on 5/23/2017.
 */

public abstract class GThread implements Runnable {
    private final String TAG = "GThread";
    public abstract void runBack();
    public abstract void runUiOnSuccess();
    public String key = String.valueOf(System.identityHashCode(this));
    private Thread thread;
    private Context context;
    private Handler mainHandler;
    private Runnable myRunnable = new Runnable() {
        @Override
        public void run() {
            runUiOnSuccess();
            Log.d(TAG, "run: thread removed from GThreadManager : "+key);
            GThreadManager.remove(key);
        }
    };

    public GThread(Context context) {
        this.context = context;
        thread = new Thread(this);
        thread.start();
        mainHandler = new Handler(context.getMainLooper());
    }

    @Override
    public void run() {
        Log.d(TAG, "run: thread added to GThreadManager : "+key);
        GThreadManager.addGThread(key, this);
        runBack();
        mainHandler.post(myRunnable);
    }

}
