package com.test.placesapp.adapters;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.placesapp.R;
import com.test.placesapp.databinding.ItemPlaceBinding;
import com.test.placesapp.model.PlaceModel;
import com.test.placesapp.network.PlacesLoader;
import com.test.placesapp.utils.Utils;
import com.test.placesapp.viewmodel.PlaceItemViewModel;

import java.util.ArrayList;
import java.util.List;

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.BindingHolder> {
    private List<PlaceModel> places;
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
        placeBinding.setViewModel(new PlaceItemViewModel(activity, places.get(position)));
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    public void setItems(List<PlaceModel> places) {
        isReachedBottom = false;
        this.places = places;
        notifyDataSetChanged();
    }

    protected void performPagination(int position) {
        if (!isReachedBottom && !isWaitingForResponse &&
                getItemCount() - position < PlacesLoader.COUNT_PER_PAGE) {
            requestNextPage(getItemCount());
        }
    }

    //This is just stub implementation for 2 pages. So we ignore offset parameter.
    private void requestNextPage(int offset) {
        isWaitingForResponse = true;

        PlacesLoader.getInstance().getPlacesAsync(1, new PlacesLoader.LoadListener() {
            @Override
            public void onSuccess(ArrayList<PlaceModel> placeModels) {
                isWaitingForResponse = false;
                isReachedBottom = true;
                places.addAll(placeModels);
                notifyItemRangeInserted(getItemCount(), places.size());
            }

            @Override
            public void onFailed(String errorString) {
                isWaitingForResponse = false;
                Utils.showErrorInSnackBar(activity, errorString);
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

