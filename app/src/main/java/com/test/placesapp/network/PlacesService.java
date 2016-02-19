package com.test.placesapp.network;

import com.test.placesapp.model.PlacesResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface PlacesService {
    String BASE_URL = "https://gist.githubusercontent.com/benigeri/1ba45a098aed0b21ae0c/raw/";

    @GET
    Call<PlacesResponseModel> getPlaces(@Url String url);
}
