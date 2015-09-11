package com.test.placesapp.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.placesapp.R;
import com.test.placesapp.databinding.ItemPlaceBinding;
import com.test.placesapp.model.PlaceModel;
import com.test.placesapp.viewmodel.PlaceItemViewModel;

import java.util.ArrayList;
import java.util.List;

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.BindingHolder> {
    private List<PlaceModel> places;
    private Context context;

    public PlacesAdapter(Context context) {
        this.context = context;
        places = new ArrayList<>();
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BindingHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_place, parent, false));
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        ItemPlaceBinding placeBinding = holder.getBinding();
        placeBinding.setViewModel(new PlaceItemViewModel(context, places.get(position)));
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    public void setItems(List<PlaceModel> places) {
        this.places = places;
        notifyDataSetChanged();
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

