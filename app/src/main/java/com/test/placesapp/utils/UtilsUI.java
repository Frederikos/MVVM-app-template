package com.test.placesapp.utils;

import android.app.Activity;

import com.github.mrengineer13.snackbar.SnackBar;
import com.test.placesapp.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class UtilsUI {

    public static void showErrorInSnackBar(Activity activity, String errorMessage) {
        new SnackBar.Builder(activity)
                .withMessage(errorMessage)
                .withTextColorId(R.color.white)
                .withBackgroundColorId(R.color.red)
                .show();
    }

}