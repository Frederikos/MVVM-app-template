package com.test.placesapp.utils;

import android.app.Activity;
import android.databinding.ObservableField;

import com.test.placesapp.network.Callback;

import java.io.IOException;

import retrofit2.Response;

import static junit.framework.Assert.fail;

public abstract class TestCallback<T> extends Callback<T> {

    public TestCallback(Activity activity, ObservableField<Boolean> isDataLoading) {
        super(activity, isDataLoading);
    }

    @Override
    public void onResponse(Response<T> response) {
        if (!response.isSuccess()) {
            try {
                fail(response.errorBody().string());
            } catch (IOException e) {
                fail("Could not read error message");
            }
        }
        onDataLoaded(response.body());
    }

    @Override
    public void onFailure(Throwable t) {
        fail("Network error");
    }
}
