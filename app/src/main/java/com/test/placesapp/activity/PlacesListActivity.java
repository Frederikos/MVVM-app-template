package com.test.placesapp.activity;

import android.databinding.BaseObservable;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.test.placesapp.R;
import com.test.placesapp.adapters.PlacesAdapter;
import com.test.placesapp.databinding.ActivityPlacesListBinding;
import com.test.placesapp.model.PlaceModel;
import com.test.placesapp.network.PlacesLoader;
import com.test.placesapp.utils.Utils;

import java.util.ArrayList;

public class PlacesListActivity extends AppCompatActivity {

    ActivityPlacesListBinding bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = DataBindingUtil.setContentView(this, R.layout.activity_places_list);
        bind.setViewModel(new PlacesActivityViewModel());
    }

    public class PlacesActivityViewModel extends BaseObservable {
        public final ObservableField<Boolean> isDataLoading = new ObservableField<>();
        public final String title = getString(R.string.places_list);

        PlacesAdapter placesAdapter;

        public PlacesActivityViewModel() {
            isDataLoading.set(true);
            setupViews();
            loadPlaces();
        }

        private void setupViews() {
            placesAdapter = new PlacesAdapter(PlacesListActivity.this);
            bind.recyclerPlaces.setLayoutManager(new LinearLayoutManager(PlacesListActivity.this));
            bind.recyclerPlaces.setHasFixedSize(true);
            bind.recyclerPlaces.setAdapter(placesAdapter);

            bind.swipeContainer.setColorSchemeResources(R.color.colorPrimary);
            bind.swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    loadPlaces();
                }
            });
        }

        private void loadPlaces() {
            PlacesLoader.getInstance().getPlacesAsync(0, new PlacesLoader.LoadListener() {
                @Override
                public void onSuccess(ArrayList<PlaceModel> placeModels) {
                    isDataLoading.set(false);
                    bind.swipeContainer.setRefreshing(false);
                    placesAdapter.setItems(placeModels);
                }

                @Override
                public void onFailed(String errorString) {
                    isDataLoading.set(false);
                    bind.swipeContainer.setRefreshing(false);
                    Utils.showErrorInSnackBar(PlacesListActivity.this, errorString);
                }
            });
        }
    }

}
