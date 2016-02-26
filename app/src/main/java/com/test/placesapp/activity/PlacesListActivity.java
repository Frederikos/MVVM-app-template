package com.test.placesapp.activity;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.test.placesapp.R;
import com.test.placesapp.adapters.PlacesAdapter;
import com.test.placesapp.databinding.ActivityPlacesListBinding;
import com.test.placesapp.model.PlacesResponseModel;
import com.test.placesapp.network.Callback;
import com.test.placesapp.network.ApiClient;

public class PlacesListActivity extends BaseActivity {

    ActivityPlacesListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_places_list);
        binding.setViewModel(new PlacesActivityViewModel());
    }

    public class PlacesActivityViewModel {
        public final ObservableField<Boolean> isDataLoading = new ObservableField<>();
        public final String title = getString(R.string.places_list);

        PlacesAdapter placesAdapter;

        public PlacesActivityViewModel() {
            isDataLoading.set(true);
            setupViews();
            loadPlaces();
        }

        public void onReloadClick(View view) {
            binding.btnReload.setVisibility(View.GONE);
            loadPlaces();
        }

        private void setupViews() {
            placesAdapter = new PlacesAdapter(PlacesListActivity.this);

            binding.recyclerPlaces.setLayoutManager(new LinearLayoutManager(PlacesListActivity.this));
            binding.recyclerPlaces.setAdapter(placesAdapter);

            binding.swipeContainer.setColorSchemeResources(R.color.colorPrimary);
            binding.swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    loadPlaces();
                }
            });
        }

        private void loadPlaces() {
            ObservableField<Boolean> loadingObservable = placesAdapter.getItemCount() == 0 ? isDataLoading : null;
            ApiClient.getInstance().getPlacesAsync(0, new Callback<PlacesResponseModel>(PlacesListActivity.this, loadingObservable) {
                @Override
                public void onDataLoaded(PlacesResponseModel result) {
                    if (result != null) {
                        binding.swipeContainer.setRefreshing(false);
                        placesAdapter.setItems(result.getBlock().getItems());
                    } else {
                        binding.btnReload.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
    }

}
