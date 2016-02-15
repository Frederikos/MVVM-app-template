package com.test.placesapp.network;

import com.test.placesapp.model.PlacesResponseModel;

import java.util.ArrayList;

import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

public class ApiClient {

    public static String PLACES_SERVICE_BASE_URL = "https://gist.githubusercontent.com/benigeri/1ba45a098aed0b21ae0c/raw/";

    private static final String PLACES_PAGE1_URL = "db28f872d6dd59c5766710abc685e01c25a0f020/places1.json";
    private static final String PLACES_PAGE2_URL = "1fccaeb4fefc105ed2d0430eea80ede57fe2a6e9/places2.json";
    private final ArrayList<String> placesPageUrls;

    public static void setServerEndPoint(String serverEndPoint) {
        PLACES_SERVICE_BASE_URL = serverEndPoint;
        instance = null;
    }

    private static ApiClient instance;
    private final PlacesService placesService;

    public static synchronized ApiClient getInstance() {
        if (instance == null) {
            instance = new ApiClient();
        }
        return instance;
    }

    private ApiClient() {
        placesPageUrls = new ArrayList<>();
        placesPageUrls.add(PLACES_PAGE1_URL);
        placesPageUrls.add(PLACES_PAGE2_URL);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PLACES_SERVICE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        placesService = retrofit.create(PlacesService.class);
    }

    public void getPlacesAsync(int pageNumber, final Callback<PlacesResponseModel> placesCallback) {
        if (pageNumber < 0 && pageNumber >= placesPageUrls.size()) return;
        placesService.getPlaces(placesPageUrls.get(pageNumber)).enqueue(placesCallback);
    }

}
