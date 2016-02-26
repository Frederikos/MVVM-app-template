package com.test.placesapp.viewmodel;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;

import com.test.placesapp.R;
import com.test.placesapp.activity.PlaceDetailsActivity;
import com.test.placesapp.databinding.ItemPlaceBinding;
import com.test.placesapp.model.PlacesResponseModel;

public class PlaceItemViewModel {

    private Activity activity;
    private PlacesResponseModel.PlaceModel placeModel;
    private ItemPlaceBinding binding;

    public PlaceItemViewModel(Activity activity, ItemPlaceBinding binding, PlacesResponseModel.PlaceModel placeModel) {
        this.activity = activity;
        this.placeModel = placeModel;
        this.binding = binding;
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
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, binding.ivPlaceImage, PlaceDetailsActivity.SHARED_ELEMENT_TRANSITION);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

}
