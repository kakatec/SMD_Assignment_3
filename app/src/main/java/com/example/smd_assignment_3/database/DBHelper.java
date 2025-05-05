package com.example.smd_assignment_3.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "tasks.db";
    private static final int DATABASE_VERSION = 1;

    // Task Table
    public static final String TABLE_TASKS = "tasks";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_DATETIME = "datetime";
    public static final String COLUMN_STATUS = "status";

    public static final String TABLE_NOTIFICATIONS = "notifications";
    public static final String COLUMN_MSG = "message";
    public static final String COLUMN_NDATETIME = "datetime";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }




    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create task table
        String CREATE_TASKS_TABLE = "CREATE TABLE " + TABLE_TASKS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TITLE + " TEXT, "
                + COLUMN_DESCRIPTION + " TEXT, "
                + COLUMN_DATETIME + " INTEGER, "  // changed to INTEGER
                + COLUMN_STATUS + " INTEGER)";

        db.execSQL(CREATE_TASKS_TABLE);

        String CREATE_NOTIF_TABLE = "CREATE TABLE " + TABLE_NOTIFICATIONS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_MSG + " TEXT, "
                + COLUMN_NDATETIME + " TEXT)";
        db.execSQL(CREATE_NOTIF_TABLE);

    }
    // Method to add a notification
    public boolean insertNotification(String message, String datetime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MSG, message);
        values.put(COLUMN_NDATETIME, datetime);
        long result = db.insert(TABLE_NOTIFICATIONS, null, values);
        db.close();
        return result != -1;
    }

    // Get all notifications
    public Cursor getAllNotifications() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NOTIFICATIONS + " ORDER BY " + COLUMN_NDATETIME + " DESC";
        return db.rawQuery(query, null);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older tables if exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        // Create tables again
        onCreate(db);
    }

    // Add a new task
    public boolean addTask(String title, String description, String datetime, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        try {
            // Convert datetime string to timestamp
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            long timestamp = sdf.parse(datetime).getTime();

            values.put(COLUMN_TITLE, title);
            values.put(COLUMN_DESCRIPTION, description);
            values.put(COLUMN_DATETIME, timestamp);
            values.put(COLUMN_STATUS, status);

            db.insert(TABLE_TASKS, null, values);
            db.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }


    // Get all tasks (Future tasks)
    public Cursor getUpcomingTasks() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_TASKS + " WHERE " + COLUMN_DATETIME + " > ? ORDER BY " + COLUMN_DATETIME + " ASC";
        return db.rawQuery(query, new String[]{String.valueOf(System.currentTimeMillis())});
    }

    public Cursor getPastTasks() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_TASKS + " WHERE " + COLUMN_DATETIME + " < ? ORDER BY " + COLUMN_DATETIME + " DESC";
        return db.rawQuery(query, new String[]{String.valueOf(System.currentTimeMillis())});
    }



    // Update task status (Completed or Pending)
    public void updateTaskStatus(int taskId, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_STATUS, status);
        db.update(TABLE_TASKS, values, COLUMN_ID + " = ?", new String[]{String.valueOf(taskId)});
        db.close();
    }
}
