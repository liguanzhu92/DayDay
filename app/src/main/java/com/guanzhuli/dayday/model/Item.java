package com.guanzhuli.dayday.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Guanzhu Li on 2/5/2017.
 */
@DatabaseTable(tableName = "days")
public class Item {
    @DatabaseField(generatedId=true)
    private Integer mID;
    @DatabaseField(columnName="title")
    private String mTitle;
    @DatabaseField(columnName="date")
    private String mDate; // format: "1992/09/02"
    @DatabaseField(columnName="repeat")
    private int mRepeat;
    private boolean mBefore;
    @DatabaseField(columnName="cover")
    private boolean mCover;
    @DatabaseField(columnName="notification")
    private boolean mNotification;
    private long mInterval;
    private Theme mTheme;
    @DatabaseField(columnName="themename")
    private String mThemeName;

    public Integer getID() {
        return mID;
    }

    public void setID(int ID) {
        mID = ID;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
        setInterval();
    }

    public int getRepeat() {
        return mRepeat;
    }

    public void setRepeat(int repeat) {
        mRepeat = repeat;
    }

    public boolean isBefore() {
        return mBefore;
    }

    public long getInterval() {
        return mInterval;
    }

    public void setInterval() {
        // String strThatDay = "1985/08/25";
        String strThatDay = mDate;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date d = null;
        try {
            d = formatter.parse(strThatDay);//catch exception
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Calendar thatDay = Calendar.getInstance();
        thatDay.setTime(d);
        Calendar today = Calendar.getInstance();
        long diff;
        if(thatDay.before(today)) {
            mBefore = true;
            diff = today.getTimeInMillis() - thatDay.getTimeInMillis(); //result in millis
        } else {
            mBefore = false;
            diff = thatDay.getTimeInMillis() - today.getTimeInMillis() ; //result in millis
        }
        long days = diff / (24 * 60 * 60 * 1000);
        mInterval = days;
    }

    public Theme getTheme() {
        return mTheme;
    }
    public void setTheme() {
        ThemeFactory themeFactory = new ThemeFactory();
        mTheme = themeFactory.getTheme(mThemeName);
    }

    public void setThemeName(String themeName) {
        mThemeName = themeName;
        setTheme();
    }

    public String getThemeName() {
        return mThemeName;
    }

    public boolean isCover() {
        return mCover;
    }

    public void setCover(boolean cover) {
        mCover = cover;
    }

    public boolean isNotification() {
        return mNotification;
    }

    public void setNotification(boolean notification) {
        mNotification = notification;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ID: " + mID + "\n");
        stringBuilder.append("Title: " + mTitle + "\n");
        stringBuilder.append("Date: " + mDate + "\n");
        stringBuilder.append("Interval: " + String.valueOf(mInterval) + "\n");
        stringBuilder.append("Repeat: " + String.valueOf(mRepeat) + "\n");
        stringBuilder.append("Theme: " + mThemeName + "\n");
        stringBuilder.append("Before: " + String.valueOf(mBefore) + "\n");
        stringBuilder.append("Cover: " + String.valueOf(mCover) + "\n");
        stringBuilder.append("Notification: " + String.valueOf(mNotification) + "\n");
        return stringBuilder.toString();
    }
}
