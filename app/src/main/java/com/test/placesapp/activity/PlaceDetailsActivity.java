package com.test.placesapp.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.test.placesapp.R;
import com.test.placesapp.databinding.ActivityPlaceDetailsBinding;
import com.test.placesapp.model.PlacesResponseModel;

public class PlaceDetailsActivity extends AppCompatActivity {

    public static final String KEY_PLACE = "place";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityPlaceDetailsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_place_details);
        binding.setViewModel(new PlaceDetailsViewModel((PlacesResponseModel.PlaceModel) getIntent().getParcelableExtra(KEY_PLACE)));
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class PlaceDetailsViewModel {
        private PlacesResponseModel.PlaceModel placeModel;

        public PlaceDetailsViewModel(PlacesResponseModel.PlaceModel placeModel) {
            this.placeModel = placeModel;
        }

        public String getTitle() {
            return placeModel.getTitle();
        }

        public String getImageUrl() {
            return placeModel.getImage();
        }
    }

}
