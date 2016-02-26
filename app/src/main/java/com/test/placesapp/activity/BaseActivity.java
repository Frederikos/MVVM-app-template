package com.test.placesapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;

public class BaseActivity extends AppCompatActivity {

    protected int getToolbarHeight() {
        TypedValue tv = new TypedValue();
        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            return TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        } else {
            return 0;
        }
    }

}
