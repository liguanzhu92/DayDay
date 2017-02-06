package com.guanzhuli.dayday.model;

import java.util.ArrayList;

/**
 * Created by Guanzhu Li on 2/5/2017.
 */
public class DaysList extends ArrayList<Item> {
    private static DaysList ourInstance = new DaysList();

    public static DaysList getInstance() {
        return ourInstance;
    }

    private DaysList() {
    }
}
