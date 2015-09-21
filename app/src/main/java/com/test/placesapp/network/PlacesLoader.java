package com.test.placesapp.network;

import android.os.AsyncTask;
import android.util.Log;

import com.test.placesapp.model.PlaceModel;
import com.test.placesapp.utils.Utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class PlacesLoader {

    public static final int COUNT_PER_PAGE = 8;

    static final String PAGE1_URL = "https://gist.githubusercontent.com/benigeri/1ba45a098aed0b21ae0c/raw/db28f872d6dd59c5766710abc685e01c25a0f020/places1.json";
    static final String PAGE2_URL = "https://gist.githubusercontent.com/benigeri/1ba45a098aed0b21ae0c/raw/1fccaeb4fefc105ed2d0430eea80ede57fe2a6e9/places2.json";

    static final int CONNECTION_TIMEOUT = 10000;

    private static PlacesLoader instance;
    private ArrayList<String> placesPageUrls;

    public static synchronized PlacesLoader getInstance() {
        if (instance == null) {
            instance = new PlacesLoader();
        }
        return instance;
    }

    private PlacesLoader() {
        placesPageUrls = new ArrayList<>();
        placesPageUrls.add(PAGE1_URL);
        placesPageUrls.add(PAGE2_URL);
    }

    public int getPageCount() {
        return placesPageUrls.size();
    }

    public void getPlacesAsync(final int pageNumber, final LoadListener loadListener) {
        new AsyncTask<Void, Void, ArrayList<PlaceModel>>() {
            @Override
            protected ArrayList<PlaceModel> doInBackground(Void... params) {
                return getPlaces(pageNumber);
            }

            @Override
            protected void onPostExecute(ArrayList<PlaceModel> placeModels) {
                if (placeModels == null) {
                    //TODO For now just show stub error.
                    loadListener.onFailed("Error loading content");
                } else {
                    loadListener.onSuccess(placeModels);
                }
            }
        }.execute();
    }

    private ArrayList<PlaceModel> getPlaces(int pageNumber) {
        if (pageNumber < 0 && pageNumber >= placesPageUrls.size()) return null;
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL(placesPageUrls.get(pageNumber));
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setAllowUserInteraction(false);
            httpURLConnection.setConnectTimeout(CONNECTION_TIMEOUT);
            httpURLConnection.setReadTimeout(CONNECTION_TIMEOUT);
            httpURLConnection.connect();

            int status = httpURLConnection.getResponseCode();
            switch (status) {
                case 200:
                case 201:
                    String responseString = Utils.readStreamAsString(httpURLConnection.getInputStream());
                    return PlaceModel.getPlacesFromString(responseString);
            }
        } catch (MalformedURLException ex) {
            Log.e(getClass().getSimpleName(), ex.toString());
        } catch (IOException ex) {
            Log.e(getClass().getSimpleName(), ex.toString());
        } finally {
            if (httpURLConnection != null) {
                try {
                    httpURLConnection.disconnect();
                } catch (Exception ex) {
                    Log.e(getClass().getSimpleName(), ex.toString());
                }
            }
        }
        return null;
    }

    public interface LoadListener {
        void onSuccess(ArrayList<PlaceModel> placeModels);

        void onFailed(String errorString);
    }

}
