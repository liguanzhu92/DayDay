package com.guanzhuli.dayday.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;
import com.guanzhuli.dayday.model.DaysList;
import com.guanzhuli.dayday.model.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guanzhu Li on 12/31/2016.
 */
public class DBManipulation {
    private DBHelper mDBHelper;
    private SQLiteDatabase mSQLiteDatabase;
    private Context mContext;
    private static String mDBName = "record"; // database schema name
    private static DBManipulation mInstance;

    public DBManipulation(Context context) {
        this.mContext = context;
        mDBHelper = new DBHelper(context, mDBName);
        mSQLiteDatabase = mDBHelper.getWritableDatabase();
    }

    public static DBManipulation getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DBManipulation(context);
        }
        return mInstance;
    }

    public void insert(Item item) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(mDBHelper.TITLE, item.getTitle());
        contentValues.put(mDBHelper.DATE, item.getDate());
        contentValues.put(mDBHelper.THEME, item.getThemeName());
        contentValues.put(mDBHelper.REPEAT, item.getRepeat());
        contentValues.put(mDBHelper.COVER, item.isCover());
        contentValues.put(mDBHelper.NOTIFICATION, item.isNotification());
        long i = mSQLiteDatabase.insert(mDBHelper.TABLENAME, null, contentValues);
        if (i > -1) {
            Toast.makeText(mContext, "Successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "Add Failed. Already existed", Toast.LENGTH_SHORT).show();
        }
    }


    public void update(String id, Item item) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(mDBHelper.TITLE, item.getTitle());
        contentValues.put(mDBHelper.DATE, item.getDate());
        contentValues.put(mDBHelper.THEME, item.getThemeName());
        contentValues.put(mDBHelper.REPEAT, item.getRepeat());
        contentValues.put(mDBHelper.COVER, item.isCover());
        contentValues.put(mDBHelper.NOTIFICATION, item.isNotification());
        int res = mSQLiteDatabase.update(mDBHelper.TABLENAME,
                contentValues, mDBHelper.ID + " = ?",
                new String[] {id});
        if (res > 0) {
            Toast.makeText(mContext, "Update successfully", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(mContext, "Update failed, this id does not exist", Toast.LENGTH_LONG).show();
        }
    }

    public void delete(String id) {
        int mark = mSQLiteDatabase.delete(mDBHelper.TABLENAME,mDBHelper.ID + " = ?", new String[] {id});
        if(mark > 0) {
            Toast.makeText(mContext, "remove successfully", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(mContext, "remove failed", Toast.LENGTH_LONG).show();
        }
    }

    public void deleteAll() {
        mSQLiteDatabase.execSQL("delete from "+ mDBHelper.TABLENAME);
    }


    public List<Item> selectAll() {
        List<Item> result = DaysList.getInstance();
        Cursor cursor = mSQLiteDatabase.rawQuery("select * from " + mDBHelper.TABLENAME, null);
        // make sure it start the first, before traverse the table
        cursor.moveToFirst();
        if (cursor.getCount() == 0) return result;
        // Cursor: like iteration, it has pointer!
        while (true) {
            Item item = new Item();
            int id = cursor.getInt(cursor.getColumnIndex(mDBHelper.ID));
            item.setID(id);
            String title = cursor.getString(cursor.getColumnIndex(mDBHelper.TITLE));
            item.setTitle(title);
            String date = cursor.getString(cursor.getColumnIndex(mDBHelper.DATE));
            item.setDate(date);
            String theme = cursor.getString(cursor.getColumnIndex(mDBHelper.THEME));
            item.setTheme(theme);
            int repeat = cursor.getInt(cursor.getColumnIndex(mDBHelper.REPEAT));
            item.setRepeat(repeat);
            int cover = cursor.getInt(cursor.getColumnIndex(mDBHelper.COVER));
            item.setCover(cover == 1 ? true : false);
            int notification = cursor.getInt(cursor.getColumnIndex(mDBHelper.NOTIFICATION));
            item.setNotification(notification == 1 ? true : false);
            result.add(item);
            if (cursor.moveToNext()) {
                continue;
            } else {
                break;
            }
        }
        return result;
    }
/*    public int getRecordNumber() {
        Cursor cursor = mSQLiteDatabase.rawQuery("select * from " + mDBHelper.TABLENAME, null);
        return cursor.getCount();
    }

    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }*/
}
