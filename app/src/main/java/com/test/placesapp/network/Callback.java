package com.test.placesapp.network;

import android.app.Activity;
import android.databinding.ObservableField;
import android.widget.Toast;

import com.test.placesapp.R;
import com.test.placesapp.utils.Utils;

import org.json.JSONObject;

import java.lang.ref.WeakReference;

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
    public void onResponse(Response<T> response) {
        Activity activity = activityReference.get();
        if (activity == null) return;

        if (isDataLoading != null) {
            isDataLoading.set(false);
        }

        if (!response.isSuccess()) {
            try {
                JSONObject errorResponse = new JSONObject(response.errorBody().string());
                Utils.showErrorInSnackBar(activity, errorResponse.getJSONObject("error").getString("message"));
            } catch (Exception e) {
                Timber.e(e.toString());
                Utils.showErrorInSnackBar(activity, activity.getString(R.string.network_error));
            }
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
