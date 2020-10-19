package gautam.easydevelope.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by gautam on 1/13/2017.
 */

public class AppPrefs {

    private Context mContext;
    private static final String NAME_PREFS = "app_preferences";
    private static SharedPreferences sharedPreferences;
    private static AppPrefs instance;

    public static AppPrefs init(Context mContext) {
        if (instance == null)
            return instance = new AppPrefs(mContext);
        return instance;
    }

    public AppPrefs(Context mContext) {
        this.mContext = mContext;
        sharedPreferences = mContext.getSharedPreferences(NAME_PREFS, Context.MODE_PRIVATE);
    }

    public static void putString(String key, String value) {
        instance.remove(key);
        sharedPreferences.edit().putString(key, value).commit();
    }

    public static void putBoolean(String key, boolean value) {
        remove(key);
        instance.sharedPreferences.edit().putBoolean(key, value).commit();
    }

    public static void putInt(String key, int value) {
        instance.remove(key);
        instance.sharedPreferences.edit().putInt(key, value).commit();
    }

    public static void putLong(String key, long value) {
        instance.remove(key);
        instance.sharedPreferences.edit().putLong(key, value).commit();
    }


    public static String getString(String key) {
        if (sharedPreferences.contains(key))
            return sharedPreferences.getString(key, null);
        return null;
    }

    public static boolean getBoolean(String key) {
        if (sharedPreferences.contains(key))
            return sharedPreferences.getBoolean(key, false);
        return false;
    }

    public static int getInt(String key) {
        if (sharedPreferences.contains(key))
            return sharedPreferences.getInt(key, 0);
        return 0;
    }

    public static long getLong(String key) {
        if (sharedPreferences.contains(key))
            return sharedPreferences.getLong(key, 0);
        return 0;
    }

    public static void remove(String key) {
        if (sharedPreferences.contains(key))
            sharedPreferences.edit().remove(key).commit();
    }


    /*
    *  get all preferences data with AppPrefs.getString(AppPrefs.Key.IS_LOGIN);
    *
    *  put all preferences with AppPrefs.putBoolean(AppPrefs.Key.IS_LOGIN, true);
    *
    *  remove AppPrefs.remove(AppPrefs.Key.IS_LOGIN);
    * */
}
