package com.test.placesapp.utils;

import android.app.Activity;

import com.github.mrengineer13.snackbar.SnackBar;
import com.test.placesapp.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Utils {

    public static void showErrorInSnackBar(Activity activity, String errorMessage) {
        new SnackBar.Builder(activity)
                .withMessage(errorMessage)
                .withTextColorId(R.color.white)
                .withBackgroundColorId(R.color.red)
                .show();
    }

    public static String readStreamAsString(InputStream inputStream) throws IOException {
        return new String(readFully(inputStream), "UTF-8");
    }

    private static byte[] readFully(InputStream inputStream) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            baos.write(buffer, 0, length);
        }
        return baos.toByteArray();
    }

}