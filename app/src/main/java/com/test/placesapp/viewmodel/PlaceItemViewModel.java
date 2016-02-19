package com.test.placesapp.viewmodel;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.test.placesapp.R;
import com.test.placesapp.activity.PlaceDetailsActivity;
import com.test.placesapp.model.PlacesResponseModel;

public class PlaceItemViewModel {

    private Activity activity;
    private PlacesResponseModel.PlaceModel placeModel;

    public PlaceItemViewModel(Activity activity, PlacesResponseModel.PlaceModel placeModel) {
        this.activity = activity;
        this.placeModel = placeModel;
    }

    public String getPlaceTitle() {
        return placeModel.getTitle();
    }

    public String getFsValue() {
        return String.valueOf(placeModel.getRating().getFsValue());
    }

    public String getCountVotes() {
        return String.format(activity.getString(R.string.votes_string), placeModel.getRating().getCount());
    }

    public String getDistance() {
        return String.format(activity.getString(R.string.distance_string), placeModel.getLocation().getDistance()) + " " + placeModel.getLocation().getLocation();
    }

    public String getImageUrl() {
        return placeModel.getImage();
    }

    public void onClick(View view) {
        Intent intent = new Intent(activity, PlaceDetailsActivity.class);
        intent.putExtra(PlaceDetailsActivity.KEY_PLACE, placeModel);
        activity.startActivity(intent);
    }

}
