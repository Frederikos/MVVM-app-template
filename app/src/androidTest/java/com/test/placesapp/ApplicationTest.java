package com.test.placesapp;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.test.placesapp.network.RestClient;

import org.junit.Test;

import okhttp3.mockwebserver.MockWebServer;
import timber.log.Timber;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }
}