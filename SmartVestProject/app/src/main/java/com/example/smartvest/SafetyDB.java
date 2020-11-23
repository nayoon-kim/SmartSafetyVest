package com.example.smartvest;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SafetyDB {
    private static final String WORKER_DB = "worker_info";
    private static final String MANAGER_DB = "manager_info";

    public static final String WORKER_ID = "worker_id";
    public static final String VEST_ID = "vest_id";
    public static final String DIRECTOR_ID = "director_id";
    public static final String FIELD_ID = "field_id";
    public static final String WARNING_SIGNAL = "warning_signal";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String LPG = "lpg";

    public static final String TEMPERATURE = "temperature";
    public static final String HUMIDITY = "humidity";

    private String DB_NAME = "";
    private String TABLE_NAME = "worker";
    private SQLiteDatabase safetyDB = null;

    public SafetyDB(Context context, String db_name) {
        if (db_name == WORKER_DB) {
            this.DB_NAME = WORKER_DB;
            this.TABLE_NAME = "worker";
        }
        else if (db_name == MANAGER_DB) {
            this.DB_NAME = MANAGER_DB;
            this.TABLE_NAME = "manager";
        }

        try {
            safetyDB = context.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null);
            safetyDB.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    WORKER_ID + " integer, " +
                    VEST_ID + " text, " +
                    DIRECTOR_ID + " integer, " +
                    FIELD_ID + " integer, " +
                    WARNING_SIGNAL + " integer, " +
                    LATITUDE + " real, " +
                    LONGITUDE + " real, " +
                    TEMPERATURE + " real, " +
                    HUMIDITY + " real)");
        } catch (SQLException e) {
            Log.e("db", e.getMessage());
        }
    }
    public void insert(Worker worker, String DB_NAME) {

    }
}
