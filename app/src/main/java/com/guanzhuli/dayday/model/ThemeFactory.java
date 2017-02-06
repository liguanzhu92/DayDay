package com.guanzhuli.dayday.model;

/**
 * Created by Guanzhu Li on 2/5/2017.
 */
public class ThemeFactory {
    public Theme getTheme(String themeName){
        if(themeName == null){
            return null;
        }
        if(themeName.equalsIgnoreCase("anniversary")){
            return new Anniversary();

        } else if(themeName.equalsIgnoreCase("birthday")){
            return new Birthday();
        } else if(themeName.equalsIgnoreCase("event")){
            return new Event();
        } else if(themeName.equalsIgnoreCase("holiday")){
            return new Holiday();
        } else if(themeName.equalsIgnoreCase("love")){
            return new Love();
        } else if(themeName.equalsIgnoreCase("school")){
            return new School();
        } else if(themeName.equalsIgnoreCase("trip")){
            return new Trip();
        } else if(themeName.equalsIgnoreCase("work")){
            return new Work();
        }
        return null;
    }
}
