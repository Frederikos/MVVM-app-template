package com.test.placesapp;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.test.InstrumentationTestCase;

import com.test.placesapp.activity.PlacesListActivity;
import com.test.placesapp.network.RestClient;
import com.test.placesapp.utils.Stubs;

import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class PlacesListActivityTest extends InstrumentationTestCase {

    MockWebServer mockWebServer;

    @Rule
    public ActivityTestRule<PlacesListActivity> mActivityRule = new ActivityTestRule<>(PlacesListActivity.class, true, false);

    @Before
    public void setUp() throws Exception {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        RestClient.setServerEndPoint(mockWebServer.url("/").toString());
    }

    @After
    public void shutDownServer() throws Exception {
        mockWebServer.shutdown();
    }

    @Test
    public void testPlacesShown() throws Exception {
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(Stubs.PLACES_RESPONSE));

        Intent intent = new Intent();
        mActivityRule.launchActivity(intent);

        onView(withId(R.id.btn_reload)).check(matches(not(isDisplayed())));
        onView(withId(R.id.progress_bar)).check(matches(not(isDisplayed())));
        onView(withId(R.id.recycler_places))
                .check(matches(hasDescendant(withText(Stubs.STUB_PLACE_TITLE))));
    }

    @Test
    public void testRetryIfNotLoaded() throws Exception {
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(404)
                .setBody(Stubs.SERVER_ERROR));

        Intent intent = new Intent();
        mActivityRule.launchActivity(intent);

        onView(withId(R.id.btn_reload)).check(matches(isDisplayed()));
        onView(withId(R.id.progress_bar)).check(matches(not(isDisplayed())));
        onView(withText(Stubs.ERROR_MESSAGE)).check(matches(isDisplayed()));
    }

}
