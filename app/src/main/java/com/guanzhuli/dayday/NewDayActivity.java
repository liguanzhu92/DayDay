package com.guanzhuli.dayday;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.guanzhuli.dayday.controller.ORMHelper;
import com.guanzhuli.dayday.model.DaysList;
import com.guanzhuli.dayday.model.Item;
import com.guanzhuli.dayday.utils.CheckCover;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NewDayActivity extends AppCompatActivity {
    private EditText mEditTitle;
    private ImageView mImageCategory,mImageDate, mImageRepeat, mImageChooseBG;
    private TextView mTextCategory, mTextDate, mTextRepeat;
    private ImageView mImageBackground;
    private Switch mSwitchCover, mSwitchNotification;
    private CalendarView mCalendarView;
    private RadioGroup mRadioGroupCategory, mRadioGroupRepeat;
    private Item mItem;
    private boolean mFlag;
    private int mPosition;
    private Context mContext;
    private ORMHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("");
        setContentView(R.layout.activity_new_day);
        mContext = this;
        mHelper = ORMHelper.getInstance(mContext);
        initialView();
        Intent intent = getIntent();
        mFlag = intent.getBooleanExtra("add",false);
        if (!mFlag) {
            mPosition = intent.getIntExtra("position",-1);
            setContent();
        } else {
            mItem = new Item();
            mItem.setThemeName("Anniversary");
            mTextCategory.setText("Anniversary");
            mItem.setRepeat(0);
            mTextRepeat.setText("None");
            Calendar cal = Calendar.getInstance();
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            String date = dateFormat.format(cal.getTime());
            mItem.setDate(date);
            mTextDate.setText(date);
        }
        setListener();
    }

    private void initialView() {
        mEditTitle = (EditText) findViewById(R.id.new_day_title);
        mImageCategory = (ImageView) findViewById(R.id.new_day_edit_category);
        mImageDate = (ImageView) findViewById(R.id.new_day_edit_date);
        mImageRepeat = (ImageView) findViewById(R.id.new_day_edit_repeat);
        mImageChooseBG = (ImageView) findViewById(R.id.new_day_edit_bg);
        mTextCategory = (TextView) findViewById(R.id.new_day_category);
        mTextDate = (TextView) findViewById(R.id.new_day_event_date);
        mTextRepeat = (TextView) findViewById(R.id.new_day_repeat);
        mSwitchCover = (Switch) findViewById(R.id.new_day_cover_switch);
        mSwitchNotification = (Switch) findViewById(R.id.new_day_notification_switch);
        mImageBackground = (ImageView) findViewById(R.id.new_day_bg);
        mCalendarView = (CalendarView) findViewById(R.id.new_day_calendar);
        mCalendarView.setVisibility(View.GONE);
        mRadioGroupCategory = (RadioGroup) findViewById(R.id.new_day_category_select);
        mRadioGroupCategory.setVisibility(View.GONE);
        mRadioGroupRepeat = (RadioGroup) findViewById(R.id.new_day_repeat_select);
        mRadioGroupRepeat.setVisibility(View.GONE);
    }

    private void setListener() {
        mImageCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRadioGroupCategory.setVisibility(View.VISIBLE);
                createCategoryButton();
            }
        });
        mImageDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCalendarView.setVisibility(View.VISIBLE);
                if (!mFlag) {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
                    Date d = null;
                    try {
                        d = formatter.parse(mItem.getDate());//catch exception
                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    Calendar thatDay = Calendar.getInstance();
                    thatDay.setTime(d);
                    mCalendarView.setDate(thatDay.getTimeInMillis());
                }
            }
        });
        mImageRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(NewDayActivity.this, "choose repeat", Toast.LENGTH_LONG).show();
                mRadioGroupRepeat.setVisibility(View.VISIBLE);
                createRepeatButton();
            }
        });
        mImageChooseBG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(NewDayActivity.this, "choose bg", Toast.LENGTH_LONG).show();
            }
        });
        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2) {
                Toast.makeText(NewDayActivity.this, "set date", Toast.LENGTH_LONG).show();
                i1++;
                String date = String.valueOf(i) + "/" + i1 + "/" + i2;
                mItem.setDate(date);
                mTextDate.setText(date);
                mCalendarView.setVisibility(View.GONE);
            }
        });
    }

    private void createRepeatButton() {
        mRadioGroupRepeat.removeAllViews();
        final String[] btnName = getResources().getStringArray(R.array.repeat_name);
        for (int i = 0; i < btnName.length; i++) {
            RadioButton radioButton = new RadioButton(this);
            if(i == mItem.getRepeat()) {
                radioButton.setChecked(true);
            }
            radioButton.setText(btnName[i]);
            radioButton.setId(i);
            mRadioGroupRepeat.addView(radioButton);
        }
        mRadioGroupRepeat.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                mTextRepeat.setText(btnName[i]);
                mItem.setRepeat(i);
                mRadioGroupRepeat.setVisibility(View.GONE);
            }
        });
    }

    private void createCategoryButton() {
        mRadioGroupCategory.removeAllViews();
        final String[] btnName = getResources().getStringArray(R.array.theme_category);
        for (int i = 0; i < btnName.length; i++) {
            RadioButton radioButton = new RadioButton(this);
            if(i == convertCategory(mItem.getThemeName())) {
                radioButton.setChecked(true);
            }
            radioButton.setText(btnName[i]);
            radioButton.setId(i);
            mRadioGroupCategory.addView(radioButton);
        }
        mRadioGroupCategory.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                mTextCategory.setText(btnName[i]);
                mItem.setThemeName(btnName[i]);
                mRadioGroupCategory.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.newday_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.new_day_confirm) {
            Toast.makeText(NewDayActivity.this, "save date", Toast.LENGTH_LONG).show();
            mItem.setTitle(mEditTitle.getText().toString());
            mItem.setCover(mSwitchCover.isChecked());
            if (mSwitchCover.isChecked()) {
                try {
                    new CheckCover(mContext).removeCover();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            mItem.setNotification(mSwitchNotification.isChecked());
            if (!mFlag) {
                // edit mode
                DaysList.getInstance().add(mPosition, mItem);
                try {
                    mHelper.getUserDao().update(mItem);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                DaysList.getInstance().add(mItem);
                try {
                    mHelper.getUserDao().create(mItem);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        finish();
        return true;
    }

    private void setContent() {
        mItem = DaysList.getInstance().get(mPosition);
        mEditTitle.setText(mItem.getTitle());
        mTextCategory.setText(mItem.getThemeName());
        mTextRepeat.setText(convertRepeat(mItem.getRepeat()));
        mTextDate.setText(mItem.getDate());
        mSwitchCover.setChecked(mItem.isCover());
        mSwitchNotification.setChecked(mItem.isNotification());
        DaysList.getInstance().remove(mPosition);
    }

    private String convertRepeat(int i) {
        String[] repeatName = getResources().getStringArray(R.array.repeat_name);
        return  repeatName[i];
    }

    private int convertCategory(String s) {
        String[] categoryName = getResources().getStringArray(R.array.theme_category);
        for (int i = 0; i < categoryName.length; i++) {
            if (s.equals(categoryName[i])) {
                return i;
            }
        }
        return -1;
    }
}
