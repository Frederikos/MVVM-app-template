package com.test.placesapp.network;

import com.test.placesapp.model.PlacesResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface PlacesService {
    @GET
    Call<PlacesResponseModel> getPlaces(@Url String url);
}
