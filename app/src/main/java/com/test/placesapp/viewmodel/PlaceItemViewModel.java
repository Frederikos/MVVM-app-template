package com.test.placesapp.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;

import com.test.placesapp.R;
import com.test.placesapp.model.PlaceModel;

public class PlaceItemViewModel extends BaseObservable {

    private Context context;
    private PlaceModel placeModel;

    public PlaceItemViewModel(Context context, PlaceModel placeModel) {
        this.context = context;
        this.placeModel = placeModel;
    }

    public String getPlaceTitle() {
        return placeModel.title;
    }

    public String getFsValue() {
        return placeModel.rating.fsValue;
    }

    public String getCountVotes() {
        return String.format(context.getString(R.string.votes_string), placeModel.rating.count);
    }

    public String getDistance() {
        return String.format(context.getString(R.string.distance_string), placeModel.location.distance) + " " + placeModel.location.location;
    }

    public String getImageUrl() {
        return placeModel.imageUrl;
    }

}
