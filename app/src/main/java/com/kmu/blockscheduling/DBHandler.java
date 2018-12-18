package com.kmu.blockscheduling;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "schedule.db";
    private static final int DATABASE_VERSION = 2;

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE schedules (id INTEGER PRIMARY KEY" +
                " AUTOINCREMENT, title TEXT, rating TEXT, endDate TEXT);");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS schedules");
        onCreate(db);
    }

    public boolean insertSchedule(String title, String rating, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("title", title);
        values.put("rating", rating);
        values.put("endDate", date);

        db.insert("schedules", null, values);
        return true;
    }

    public Cursor getData(Integer id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from schedules where id=" + id + "", null);
        return res;
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, "schedules");
        return numRows;
    }

    public boolean updateSchedule(Integer id, String title, String rating, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("title", title);
        values.put("rating", rating);
        values.put("endDate", date);

        db.update("schedules", values, "id=?", new String[]{Integer.toString(id)});
        return true;
    }

    public Integer deleteSchedule(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("schedules",
                "id = ? ",
                new String[]{Integer.toString(id)});
    }

    public ArrayList getAllSchedules() {
        ArrayList array_list = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from schedules", null);
        res.moveToFirst();
        try {
            while (!res.isAfterLast()) {
                array_list.add(res.getString(res.getColumnIndex("id")) + " " +
                        res.getString(res.getColumnIndex("title")) + " " +
                        res.getString(res.getColumnIndex("rating")) + " " +
                        res.getString(res.getColumnIndex("endDate")));
                res.moveToNext();
            }
        }
        catch (Exception e){}
        return array_list;
    }
}
