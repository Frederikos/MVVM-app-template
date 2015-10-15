package com.test.placesapp.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;

import com.test.placesapp.R;
import com.test.placesapp.model.PlacesResponseModel;

public class PlaceItemViewModel extends BaseObservable {

    private Context context;
    private PlacesResponseModel.PlaceModel placeModel;

    public PlaceItemViewModel(Context context, PlacesResponseModel.PlaceModel placeModel) {
        this.context = context;
        this.placeModel = placeModel;
    }

    public String getPlaceTitle() {
        return placeModel.getTitle();
    }

    public String getFsValue() {
        return String.valueOf(placeModel.getRating().getFsValue());
    }

    public String getCountVotes() {
        return String.format(context.getString(R.string.votes_string), placeModel.getRating().getCount());
    }

    public String getDistance() {
        return String.format(context.getString(R.string.distance_string), placeModel.getLocation().getDistance()) + " " + placeModel.getLocation().getLocation();
    }

    public String getImageUrl() {
        return placeModel.getImage();
    }

}
