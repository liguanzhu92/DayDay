package com.guanzhuli.dayday.model;

import com.guanzhuli.dayday.R;

/**
 * Created by Guanzhu Li on 2/5/2017.
 */
public interface Theme {
    int BackgroundResources();
    int IconResources();
}

class Anniversary implements Theme{
    @Override
    public int BackgroundResources() {
        return R.mipmap.anniversary;
    }

    @Override
    public int IconResources() {
        return R.drawable.ic_anniversary;
    }
}

class Love implements Theme{
    @Override
    public int BackgroundResources() {
        return R.mipmap.love;
    }

    @Override
    public int IconResources() {
        return R.drawable.ic_love;
    }
}

class Work implements Theme{
    @Override
    public int BackgroundResources() {
        return R.mipmap.work;
    }

    @Override
    public int IconResources() {
        return R.drawable.ic_work;
    }
}

class School implements Theme{
    @Override
    public int BackgroundResources() {
        return R.mipmap.school;
    }

    @Override
    public int IconResources() {
        return R.drawable.ic_school;
    }
}

class Birthday implements Theme{
    @Override
    public int BackgroundResources() {
        return R.mipmap.birthday;
    }

    @Override
    public int IconResources() {
        return R.drawable.ic_birthday;
    }
}

class Event implements Theme{
    @Override
    public int BackgroundResources() {
        return R.mipmap.event;
    }

    @Override
    public int IconResources() {
        return R.drawable.ic_event;
    }
}

class Holiday implements Theme{
    @Override
    public int BackgroundResources() {
        return R.mipmap.holiday;
    }

    @Override
    public int IconResources() {
        return R.drawable.ic_holiday;
    }
}

class Trip implements Theme{
    @Override
    public int BackgroundResources() {
        return R.mipmap.trip;
    }

    @Override
    public int IconResources() {
        return R.drawable.ic_trip;
    }
}