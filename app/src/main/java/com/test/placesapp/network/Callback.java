package com.test.placesapp.network;

import android.app.Activity;
import android.databinding.ObservableField;

import com.test.placesapp.R;
import com.test.placesapp.utils.Utils;

import java.lang.ref.WeakReference;

import retrofit.Response;
import retrofit.Retrofit;

public abstract class Callback<T> implements retrofit.Callback<T> {

    private WeakReference<Activity> activityReference;
    private ObservableField<Boolean> isDataLoading;

    public Callback(Activity activity, ObservableField<Boolean> isDataLoading) {
        this.activityReference = new WeakReference<>(activity);
        this.isDataLoading = isDataLoading;
        if (isDataLoading != null) {
            isDataLoading.set(true);
        }
    }

    @Override
    public void onResponse(Response<T> response, Retrofit retrofit) {
        Activity activity = activityReference.get();
        if (activity == null) return;

        if (isDataLoading != null) {
            isDataLoading.set(false);
        }

        if (!response.isSuccess()) {
            Utils.showErrorInSnackBar(activity, activity.getString(R.string.network_error));
        }

        onDataLoaded(response.body());
    }

    @Override
    public void onFailure(Throwable t) {
        Activity activity = activityReference.get();
        if (activity == null) return;

        if (isDataLoading != null) {
            isDataLoading.set(false);
        }

        Utils.showErrorInSnackBar(activity, activity.getString(R.string.network_error));
        onDataLoaded(null);
    }

    public abstract void onDataLoaded(T result);
}
