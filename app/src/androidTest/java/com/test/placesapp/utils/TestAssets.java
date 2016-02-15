package com.test.placesapp.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TestAssets {

    public static String getErrorResponse(Context context) {
        return getStringFromFile(context, "error_response.json");
    }

    public static String getPlacesListResponse(Context context) {
        return getStringFromFile(context, "places_list_response.json");
    }

    public static String getPlacesListResponse2(Context context) {
        return getStringFromFile(context, "places_list_response2.json");
    }

    private static String getStringFromFile(Context context, String filePath) {
        String result = null;
        try {
            InputStream is = context.getResources().getAssets().open(filePath);
            result = convertStreamToString(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static String convertStreamToString(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }
}

