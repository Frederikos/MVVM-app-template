package com.test.placesapp.activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.test.placesapp.R;
import com.test.placesapp.adapters.PlacesAdapter;
import com.test.placesapp.databinding.ActivityPlacesListBinding;
import com.test.placesapp.model.PlaceModel;
import com.test.placesapp.network.PlacesLoader;
import com.test.placesapp.utils.Utils;

import java.util.ArrayList;

public class PlacesListActivity extends AppCompatActivity {

    ActivityPlacesListBinding bind;
    PlacesAdapter placesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = DataBindingUtil.setContentView(this, R.layout.activity_places_list);
        bind.toolbar.setTitle(R.string.places_list);
        setupRecyclerView();
        loadPlaces();
    }

    //TODO make ViewModel to control RecycleView and ProgressBar.
    private void loadPlaces() {
        PlacesLoader.getInstance().getPlacesAsync(0, new PlacesLoader.LoadListener() {
            @Override
            public void onSuccess(ArrayList<PlaceModel> placeModels) {
                bind.progressBar.setVisibility(View.GONE);
                placesAdapter.setItems(placeModels);
            }

            @Override
            public void onFailed(String errorString) {
                bind.progressBar.setVisibility(View.GONE);
                Utils.showErrorInSnackBar(PlacesListActivity.this, errorString);
            }
        });
    }

    private void setupRecyclerView() {
        placesAdapter = new PlacesAdapter(this);
        bind.recyclerPlaces.setLayoutManager(new LinearLayoutManager(this));
        bind.recyclerPlaces.setHasFixedSize(true);
        bind.recyclerPlaces.setAdapter(placesAdapter);
    }


}
