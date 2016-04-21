package com.test.placesapp.network;

import android.app.Activity;
import android.databinding.ObservableField;
import android.support.annotation.Nullable;

import com.test.placesapp.R;
import com.test.placesapp.utils.UtilsUI;

import org.json.JSONObject;

import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Response;
import timber.log.Timber;

public abstract class Callback<T> implements retrofit2.Callback<T> {

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
    public void onResponse(Call<T> call, Response<T> response) {
        Activity activity = activityReference.get();
        if (activity == null) return;

        if (isDataLoading != null) {
            isDataLoading.set(false);
        }

        if (!response.isSuccessful()) {
            try {
                JSONObject errorResponse = new JSONObject(response.errorBody().string());
                UtilsUI.showErrorInSnackBar(activity, errorResponse.getJSONObject("error").getString("message"));
            } catch (Exception e) {
                Timber.e(e.toString());
                UtilsUI.showErrorInSnackBar(activity, activity.getString(R.string.network_error));
            }
        }

        onDataLoaded(response.body());
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        Activity activity = activityReference.get();
        if (activity == null) return;

        if (isDataLoading != null) {
            isDataLoading.set(false);
        }

        UtilsUI.showErrorInSnackBar(activity, activity.getString(R.string.network_error));
        onDataLoaded(null);
    }

    public abstract void onDataLoaded(@Nullable T result);
}
