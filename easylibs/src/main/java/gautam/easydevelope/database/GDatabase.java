package gautam.easydevelope.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nawab Sahab on 3/25/2017.
 */

public class GDatabase<T> extends SQLiteOpenHelper {
    private Context gContext;
    private Class<? super T> rawType;
    private final String TAG = getClass().getSimpleName();
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "g_database";
    private final String COL_ID = "id";
    private final String COL_UNIQUE_KEY = "unique_key";
    private final String COL_DATA = "data";

    public GDatabase(Context context, Class<? super T> className) {
        super(context, DB_NAME, null, DB_VERSION);
        gContext = context;
        rawType = className;
        Log.w(TAG, "GDatabase: class name " + rawType.getSimpleName());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    private String getCreateTableQuery(String tableName) {
        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + tableName + "("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_UNIQUE_KEY + " TEXT,"
                + COL_DATA + " TEXT"
                + ")";
        return CREATE_TABLE;
    }

    private String getTableName() {
        return rawType.getSimpleName();
    }

    public void insertData(String uniqueKey, T model) {
        long result = 0;
        String tableName = getTableName();
        SQLiteDatabase db = this.getWritableDatabase();
//        create table
        db.execSQL(getCreateTableQuery(tableName));
//        insert data
        ContentValues values = new ContentValues();
        values.put(COL_UNIQUE_KEY, uniqueKey);
        values.put(COL_DATA, objectToJsonString(model));
        result = db.insert(tableName, null, values);
        db.close();
        Log.d(TAG, "*******************insertRecord: successful ***********************" + result);
    }

    public void insertAndUpdateData(String uniqueKey, T model) {
        long result = 0;
        String tableName = getTableName();
        SQLiteDatabase db = this.getWritableDatabase();
        //        create table
        db.execSQL(getCreateTableQuery(tableName));
        // values
        ContentValues values = new ContentValues();
        values.put(COL_UNIQUE_KEY, uniqueKey);
        values.put(COL_DATA, objectToJsonString(model));

        if (getDataCount(db, uniqueKey) > 0) {
//        update data
            result = db.update(tableName, values, COL_UNIQUE_KEY + "=?", new String[]{uniqueKey});
            Log.d(TAG, "*******************insertRecord: update ***********************" + result);
        } else {
//        insert data
            result = db.insert(tableName, null, values);
            Log.d(TAG, "*******************insertRecord: successful ***********************" + result);
        }

        db.close();
    }

    public int getDataCount(SQLiteDatabase db, String uniqueKey) {
        int dataCount = 0;
        try {
            String TABLE_NAME = getTableName();
            String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE " + COL_UNIQUE_KEY + "='" + uniqueKey + "'";
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor != null && !cursor.isClosed()) {
                dataCount = cursor.getCount();
                cursor.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Log.w(TAG, "getDataCount: no such column " + e.getMessage().contains("no such column"));
        }
        return dataCount;
    }


    private String objectToJsonString(T model) {
        Gson gson = new Gson();
        Type type = new TypeToken<T>() {
        }.getType();
        String string = gson.toJson(model, type);
        Log.w(TAG, "objectToJsonString: converted " + string);
        return string;
    }


    private T stringToObject(String data) {
        Gson gson = new Gson();
        Type type = new TypeToken<T>() {
        }.getType();
        T t = (T) gson.fromJson(data, rawType);
        return t;
    }

    public T getData(String uniqueKey) {
        T t = null;
        try {
            String TABLE_NAME = getTableName();
            String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE " + COL_UNIQUE_KEY + "='" + uniqueKey + "'";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    t = stringToObject(cursor.getString(cursor.getColumnIndex(COL_DATA)));
                    Log.d(TAG, "getData: " + cursor.getString(cursor.getColumnIndex(COL_DATA)));
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
            Log.w(TAG, "getData: no such table " + e.getMessage().contains("no such table"));
        }
        return t;
    }

    public List<T> getDataList(String uniqueKey) {
        List<T> ts = new ArrayList<>();
        T t = null;
        String TABLE_NAME = getTableName();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE " + COL_UNIQUE_KEY + "=" + uniqueKey;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                t = stringToObject(cursor.getString(cursor.getColumnIndex(COL_DATA)));
                Log.d(TAG, "getData: " + cursor.getString(cursor.getColumnIndex(COL_DATA)));
                ts.add(t);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return ts;
    }

    public List<T> getDataAllList() {
        List<T> ts = new ArrayList<>();
        T t = null;
        String TABLE_NAME = getTableName();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                t = stringToObject(cursor.getString(cursor.getColumnIndex(COL_DATA)));
                Log.d(TAG, "getData: " + cursor.getString(cursor.getColumnIndex(COL_DATA)));
                ts.add(t);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return ts;
    }

//    public boolean delete(Booking booking){
//        SQLiteDatabase db = this.getWritableDatabase();
//        String deleteQuery = "DELETE FROM "+TBL_CART+" WHERE "+COL_PACKAGE_ID+" = '"+booking.getPackageId()+"';";
////        db.delete(TABLE_CONTACTS, KEY_ID + " = ?", new String[] { String.valueOf(dataModel.getId()) });
//        try {
//            db.execSQL(deleteQuery);
//            db.close();
//            Log.d(TAG, "deleteRowData: deleted successfully from id = "+deleteQuery);
//            return true;
//        } catch (SQLiteException e){
//            e.printStackTrace();
//            return false;
//        }
//    }

//    public boolean updateBooking(Booking booking) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
////        values.put(COL_PACKAGE_NAME, booking.getPackageName());
////        values.put(COL_PACKAGE_IMAGE, booking.getPackageImage());
////        values.put(COL_PICK_UP_TIME, booking.getPickUpTime());
//        values.put(COL_DATE_OF_TOUR, booking.getDateOfTour());
//        values.put(COL_NO_OF_ADULT, booking.getNoOfAdult());
//        values.put(COL_NO_OF_CHILD, booking.getNoOfChild());
////        values.put(COL_ADULT_AMOUNT, booking.getAdultAmount());
////        values.put(COL_CHILD_AMOUNT, booking.getChildAmount());
////        values.put(COL_CURRENCY, booking.getCurrency());
//        values.put(COL_HOTEL_NAME, booking.getHotelName());
//        values.put(COL_HOTEL_NUMBER, booking.getHotelNumber());
//        values.put(COL_HOTEL_ADDRESS, booking.getHotelAddress());
//        values.put(COL_TOTAL_AMOUNT, booking.getTotalAmount());
//        int result = db.update(TBL_CART, values, COL_PACKAGE_ID+"="+booking.getPackageId(), null);
//        Log.d(TAG, "updateCategory: update query " + result);
//
//
//        db.close();
//        return true;
//    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS " + TBL_CART);
//        db.execSQL("DROP TABLE IF EXISTS " + TBL_CART);
//        Log.d(TAG, "onUpgrade: old table droped");
    }
}
