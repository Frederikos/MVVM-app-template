package com.test.placesapp.unit;

import android.content.Context;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.google.gson.Gson;
import com.test.placesapp.model.PlacesResponseModel;
import com.test.placesapp.utils.TestAssets;
import com.test.placesapp.viewmodel.PlaceItemViewModel;

import org.junit.Before;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static junit.framework.Assert.assertTrue;

public class PlaceItemViewModelTest {

    private PlaceItemViewModel placeItemViewModel;
    private PlacesResponseModel.PlaceModel placeModel;

    @Before
    public void setUp() throws Exception {
        Context applicationContext = getInstrumentation().getTargetContext().getApplicationContext();
        Context testContext = getInstrumentation().getContext();
        placeModel = new Gson().fromJson(TestAssets.getPlacesListResponse(testContext), PlacesResponseModel.class).getBlock().getItems().get(0);
        placeItemViewModel = new PlaceItemViewModel(applicationContext, placeModel);
    }

    @Test
    public void checkPlaceTitle() {
        assertTrue(placeItemViewModel.getPlaceTitle().equals(placeModel.getTitle()));
    }

    @Test
    public void checkFsValue() {
        assertTrue(placeItemViewModel.getFsValue().equals(String.valueOf(placeModel.getRating().getFsValue())));
    }

    @Test
    public void checkCountVotes() {
        assertTrue(placeItemViewModel.getCountVotes().equals("361 votes"));
    }

    @Test
    public void checkDistance() {
        Log.e("test", placeItemViewModel.getDistance());
        assertTrue(placeItemViewModel.getDistance().equals("0.36 mi * Kyiv, Kyiv City"));
    }

    @Test
    public void checkImageUrl() {
        assertTrue(placeItemViewModel.getImageUrl().equals(placeModel.getImage()));
    }

}
