package com.guanzhuli.dayday;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.guanzhuli.dayday.model.DaysList;
import com.guanzhuli.dayday.model.Item;
import com.guanzhuli.dayday.utils.CheckCover;
import com.guanzhuli.dayday.utils.Constant;
import com.guanzhuli.dayday.utils.PermissionUtil;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.guanzhuli.dayday.utils.PermissionUtil.hasPermission;

public class NewDayActivity extends BaseActivity {
    private EditText mEditTitle;
    private ImageView mImageCategory,mImageDate, mImageRepeat;
    private TextView mTextCategory, mTextDate, mTextRepeat, mTextChooseBG;
    private ImageView mImageBackground;
    private Switch mSwitchCover, mSwitchNotification;
    private CalendarView mCalendarView;
    private Item mItem;
    private boolean mFlag;
    private int mPosition;
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("");
        setContentView(R.layout.activity_new_day);
        mContext = this;
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
        mTextCategory = (TextView) findViewById(R.id.new_day_category);
        mTextDate = (TextView) findViewById(R.id.new_day_event_date);
        mTextRepeat = (TextView) findViewById(R.id.new_day_repeat);
        mTextChooseBG = findViewById(R.id.new_day_change_bg);
        mSwitchCover = (Switch) findViewById(R.id.new_day_cover_switch);
        mSwitchNotification = (Switch) findViewById(R.id.new_day_notification_switch);
        mImageBackground = (ImageView) findViewById(R.id.new_day_bg);
        mCalendarView = (CalendarView) findViewById(R.id.new_day_calendar);
        mCalendarView.setVisibility(View.GONE);
    }

    private void setListener() {
        registerForContextMenu(mImageCategory);
        registerForContextMenu(mImageRepeat);
        mImageCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openContextMenu(view);
            }
        });
        mImageRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openContextMenu(view);
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

        mTextChooseBG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(NewDayActivity.this, "choose bg", Toast.LENGTH_LONG).show();
                if (!hasPermission(mContext, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    PermissionUtil.askForPermission(Manifest.permission.READ_EXTERNAL_STORAGE, Constant.READ_EXST, mContext);
                } else {
                    selectBackground();
                }
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

    private int convertRepeat(String s) {
        String[] repeatName = getResources().getStringArray(R.array.repeat_name);
        for (int i = 0; i < repeatName.length; i++) {
            if (s.equals(repeatName[i])) {
                return i;
            }
        }
        return -1;
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.new_day_edit_category) {
            int default_cate;
            default_cate = mItem.getThemeName() != null ? convertCategory(mItem.getThemeName()) : 0;
            getMenuInflater().inflate(R.menu.category_menu, menu);
            menu.getItem(default_cate).setChecked(true);
            menu.setHeaderTitle("Category");
            menu.setHeaderIcon(R.drawable.calendar_small);
            return;
        }
        if (v.getId() == R.id.new_day_edit_repeat) {
            int default_cate = mItem.getRepeat();
            getMenuInflater().inflate(R.menu.repeat_menu, menu);
            menu.getItem(default_cate).setChecked(true);
            menu.setHeaderTitle("Repeat");
            menu.setHeaderIcon(R.drawable.calendar_small);
            return;
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        String cate_string = null;
        String repeat_string = null;
        switch (item.getItemId()) {
            case R.id.category_anniversary:
                cate_string = item.getTitle().toString();
                break;
            case R.id.category_birthday:
                cate_string = item.getTitle().toString();
                break;
            case R.id.category_event:
                cate_string = item.getTitle().toString();
                break;
            case R.id.category_holiday:
                cate_string = item.getTitle().toString();
                break;
            case R.id.category_love:
                cate_string = item.getTitle().toString();
                break;
            case R.id.category_school:
                cate_string = item.getTitle().toString();
                break;
            case R.id.category_trip:
                cate_string = item.getTitle().toString();
                break;
            case R.id.repeat_none:
                repeat_string = item.getTitle().toString();
                break;
            case R.id.repeat_weekly:
                repeat_string = item.getTitle().toString();
                break;
            case R.id.repeat_bi:
                repeat_string = item.getTitle().toString();
                break;
            case R.id.repeat_month:
                repeat_string = item.getTitle().toString();
                break;
            case R.id.repeat_quarter:
                repeat_string = item.getTitle().toString();
                break;
            case R.id.repeat_semi:
                repeat_string = item.getTitle().toString();
                break;
            case R.id.repeat_annual:
                repeat_string = item.getTitle().toString();
                break;
            default:
                super.onContextItemSelected(item);
        }
        if (cate_string != null) {
            mTextCategory.setText(cate_string);
            mItem.setThemeName(cate_string);
            return true;
        }
        if (repeat_string != null){
            mTextRepeat.setText(repeat_string);
            mItem.setRepeat(convertRepeat(repeat_string));
        }
        return true;
    }

    private void selectBackground() {
        Intent selectImageIntent = new Intent(mContext, ImageViewActivity.class);
        startActivity(selectImageIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == RESULT_OK && requestCode == Constant.PICK_IMAGE ) {
                String image = data.getStringExtra(Constant.KEY_IMAGES);
                mImageBackground.setImageBitmap(BitmapFactory.decodeFile(image));
                // TODO: cache image in project
            }
        } catch (Exception ex) {

        }
    }
}
