package com.test.placesapp.adapters;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.placesapp.R;
import com.test.placesapp.databinding.ItemPlaceBinding;
import com.test.placesapp.model.PlacesResponseModel;
import com.test.placesapp.network.Callback;
import com.test.placesapp.network.ApiClient;
import com.test.placesapp.viewmodel.PlaceItemViewModel;

import java.util.ArrayList;
import java.util.List;

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.BindingHolder> {
    private static final int COUNT_BEFORE_PAGINATION = 4;

    private List<PlacesResponseModel.PlaceModel> places;
    private Activity activity;
    private boolean isReachedBottom;
    private boolean isWaitingForResponse;

    public PlacesAdapter(Activity activity) {
        this.activity = activity;
        places = new ArrayList<>();
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BindingHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_place, parent, false));
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        performPagination(position);
        ItemPlaceBinding placeBinding = holder.getBinding();
        placeBinding.setViewModel(new PlaceItemViewModel(activity, placeBinding, places.get(position)));
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    public void setItems(List<PlacesResponseModel.PlaceModel> places) {
        isReachedBottom = false;
        this.places = places;
        notifyDataSetChanged();
    }

    protected void performPagination(int position) {
        if (!isReachedBottom && !isWaitingForResponse &&
                getItemCount() - position < COUNT_BEFORE_PAGINATION) {
            requestNextPage(getItemCount());
        }
    }

    //This is just stub implementation for 2 pages. So we ignore offset parameter.
    private void requestNextPage(int offset) {
        isWaitingForResponse = true;

        ApiClient.getInstance().getPlacesAsync(1, new Callback<PlacesResponseModel>(activity, null) {
            @Override
            public void onDataLoaded(PlacesResponseModel result) {
                isWaitingForResponse = false;
                isReachedBottom = true;
                if (result != null) {
                    List<PlacesResponseModel.PlaceModel> newPlaces = result.getBlock().getItems();
                    places.addAll(newPlaces);
                    notifyItemRangeInserted(getItemCount(), newPlaces.size());
                }
            }
        });
    }

    public static class BindingHolder extends RecyclerView.ViewHolder {
        private ItemPlaceBinding binding;

        public BindingHolder(View rowView) {
            super(rowView);
            binding = DataBindingUtil.bind(rowView);
        }

        public ItemPlaceBinding getBinding() {
            return binding;
        }
    }

}

