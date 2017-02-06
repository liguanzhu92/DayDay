package com.guanzhuli.dayday;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

public class NewDayActivity extends AppCompatActivity {
    private EditText mEditTitle;
    private ImageView mImageCategory,mImageDate, mImageRepeat, mImageChooseBG;
    private TextView mTextCategory, mTextDate, mTextRepeat;
    private ImageView mImageBackground;
    private Switch mSwitchCover, mSwitchNotification;
    private CalendarView mCalendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("");
        setContentView(R.layout.activity_new_day);
        initialView();
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
    }

    private void setListener() {
        mImageCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(NewDayActivity.this, "choose category", Toast.LENGTH_LONG).show();
            }
        });
        mImageDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCalendarView.setVisibility(View.VISIBLE);
            }
        });
        mImageRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(NewDayActivity.this, "choose repeat", Toast.LENGTH_LONG).show();
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
                mCalendarView.setVisibility(View.GONE);
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
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
