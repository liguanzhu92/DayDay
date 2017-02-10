package com.guanzhuli.dayday.utils;

import android.content.Context;
import com.guanzhuli.dayday.controller.ORMHelper;
import com.guanzhuli.dayday.model.DaysList;
import com.guanzhuli.dayday.model.Item;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Guanzhu Li on 2/9/2017.
 */
public class CheckCover {
    private DaysList mDaysList = DaysList.getInstance();
    private Context mContext;


    public CheckCover(Context context) {
        mContext = context;
    }

    public void removeCover() throws SQLException {
        ORMHelper mHelper = ORMHelper.getInstance(mContext);
        for (Item item : mDaysList) {
            item.setCover(false);
            mHelper.getUserDao().update(item);
        }
    }

    public int getCoverPosition() {
        int position = 0;
        for (Item item : mDaysList) {
            if (item.isCover()) {
                return position;
            }
            position++;
        }
        return 0;
    }
}
