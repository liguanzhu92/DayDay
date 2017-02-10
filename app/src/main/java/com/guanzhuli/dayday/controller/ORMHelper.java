package com.guanzhuli.dayday.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.guanzhuli.dayday.model.Item;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by Guanzhu Li on 2/9/2017.
 */
public class ORMHelper extends OrmLiteSqliteOpenHelper {
    private static final String TABLE_NAME = "UserData.db";
    private static ORMHelper ourInstance = null;
    private Dao<Item, Integer> userDao;

    public ORMHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    public static synchronized ORMHelper getInstance(Context context) {
        if (ourInstance == null) {
            synchronized (ORMHelper.class)
            {
                if (ourInstance == null)
                    ourInstance = new ORMHelper(context);
            }
        }
        return ourInstance;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            // the class we are persisting with the DAO, and the class of the ID-column that will be used to identify a specific database row
            TableUtils.createTable(connectionSource, Item.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
        try {
            TableUtils.dropTable(connectionSource, Item.class, true);
            onCreate(sqLiteDatabase, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Dao<Item, Integer> getUserDao() throws SQLException {
        if (userDao == null)
        {
            userDao = getDao(Item.class);
        }
        return userDao;
    }

    @Override
    public void close() {
        super.close();
        userDao = null;
    }
}
