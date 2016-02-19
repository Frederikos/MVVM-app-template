package com.test.placesapp.network;

import com.test.placesapp.model.PlacesResponseModel;

import java.util.ArrayList;

import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

public class ApiClient {

    private static String sBaseUrl = PlacesService.BASE_URL;
    private static final String PLACES_PAGE1_URL = "db28f872d6dd59c5766710abc685e01c25a0f020/places1.json";
    private static final String PLACES_PAGE2_URL = "1fccaeb4fefc105ed2d0430eea80ede57fe2a6e9/places2.json";
    private static ApiClient instance;

    private final ArrayList<String> placesPageUrls;
    private final PlacesService placesService;

    public static void setBaseUrl(String baseUrl) {
        sBaseUrl = baseUrl;
        instance = null;
    }

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
                .baseUrl(sBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        placesService = retrofit.create(PlacesService.class);
    }

    public void getPlacesAsync(int pageNumber, final Callback<PlacesResponseModel> placesCallback) {
        if (pageNumber < 0 && pageNumber >= placesPageUrls.size()) return;
        placesService.getPlaces(placesPageUrls.get(pageNumber)).enqueue(placesCallback);
    }

}
