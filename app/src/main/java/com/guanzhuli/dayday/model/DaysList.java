package com.guanzhuli.dayday.model;

import java.util.ArrayList;

/**
 * Created by Guanzhu Li on 2/5/2017.
 */
public class DaysList extends ArrayList<Item> {
    private static DaysList ourInstance = null;
    public static DaysList getInstance() {
        if (ourInstance == null) {
            ourInstance = new DaysList();
        }
        return ourInstance;
    }

    private DaysList() {
    }
}
