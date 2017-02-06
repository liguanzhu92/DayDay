package com.guanzhuli.dayday.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Guanzhu Li on 12/31/2016.
 */
public class DBHelper  extends SQLiteOpenHelper {
    public static final String TABLENAME = "dayday";
    public static final String ID = "_id";
    public static final String TITLE = "title";
    public static final String DATE = "date";
    public static final String THEME = "theme";
    public static final String REPEAT = "repeat";
    public static final String COVER = "cover";
    public static final String NOTIFICATION = "notification";
    public static final int VERSION = 1;
    // new a table to save custom category

    public DBHelper(Context context, String dbname) {
        super(context, dbname, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.e("sqlite", "create table");
        String createTable = " CREATE TABLE " + TABLENAME + "("
                + ID + " INTEGER PRIMARY KEY AUTO_INCREMENT,"
                + TITLE + " TEXT," + DATE + " TEXT,"
                + THEME + " TEXT," + REPEAT + " INTEGER,"
                + COVER + " INTEGER," + NOTIFICATION + "INTEGER)";
        sqLiteDatabase.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.v(DBHelper.class.getName(),
                "upgrading database from version "+ i + " to "
                        + i1 + ", which will destroy all old data");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLENAME);
        onCreate(sqLiteDatabase);
    }
}
