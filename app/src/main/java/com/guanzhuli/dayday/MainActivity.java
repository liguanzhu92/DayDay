package com.guanzhuli.dayday;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // set button: nav to setting activity
        Intent mInHome = new Intent(MainActivity.this, SettingsActivity.class);
        MainActivity.this.startActivity(mInHome);
    }
}
