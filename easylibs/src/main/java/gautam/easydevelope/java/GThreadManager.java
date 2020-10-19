package gautam.easydevelope.java;

import android.content.Context;

import java.util.HashMap;

/**
 * Created by gautam on 5/23/2017.
 */

public class GThreadManager {

    private static HashMap<String, GThread> threads;
    private Context  context;

    public GThreadManager(Context context) {
        threads = new HashMap<>();
        this.context = context;
    }

    public static void init(Context context){
        new GThreadManager(context);
    }

    public static void addGThread(String key, GThread gThread){
        threads.put(key, gThread);
    }


    public static void remove(String key){
        threads.remove(key);
    }

    public static int activeThreadCount(){
        return threads.size();
    }

}
