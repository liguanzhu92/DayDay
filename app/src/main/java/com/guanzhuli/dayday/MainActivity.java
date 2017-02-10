package com.guanzhuli.dayday;



import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import com.guanzhuli.dayday.controller.ORMHelper;
import com.guanzhuli.dayday.fragment.HomeFragment;
import com.guanzhuli.dayday.model.DaysList;
import com.guanzhuli.dayday.model.Item;

import java.sql.SQLException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private DaysList mDaysList = DaysList.getInstance();
    private ORMHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        Log.d("main", "create");
        mHelper = ORMHelper.getInstance(this);
        if(findViewById(R.id.container_main) != null) {
            HomeFragment fragment = new HomeFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container_main, fragment);
            ft.commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("main", "resume");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("main", "stop");
        for (int i = 0; i < mDaysList.size(); i++) {
            Item item = mDaysList.get(i);
            if (item.getID() == null) {
                try {
                    mHelper.getUserDao().create(item);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    mHelper.getUserDao().updateId(item, item.getID());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
