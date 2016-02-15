package com.test.placesapp.functional;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.test.InstrumentationTestCase;

import com.test.placesapp.R;
import com.test.placesapp.activity.PlacesListActivity;
import com.test.placesapp.network.ApiClient;
import com.test.placesapp.utils.TestAssets;
import com.test.placesapp.utils.StubCheckValues;

import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.swipeDown;
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
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        ApiClient.setServerEndPoint(mockWebServer.url("/").toString());
    }

    @After
    public void shutDownServer() throws Exception {
        mockWebServer.shutdown();
    }

    @Test
    public void testPlacesShown() throws Exception {
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(TestAssets.getPlacesListResponse(getInstrumentation().getContext())));

        mActivityRule.launchActivity(new Intent());

        onView(withId(R.id.progress_bar)).check(matches(not(isDisplayed())));
        onView(withId(R.id.recycler_places))
                .check(matches(hasDescendant(withText(StubCheckValues.TITLE_FROM_PLACES))));
    }

    @Test
    public void testRetryShowIfNotLoaded() throws Exception {
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(404)
                .setBody(TestAssets.getErrorResponse(getInstrumentation().getContext())));

        mActivityRule.launchActivity(new Intent());

        onView(withId(R.id.btn_reload)).check(matches(isDisplayed()));
        onView(withText(StubCheckValues.ERROR_MESSAGE)).check(matches(isDisplayed()));
    }

    @Test
    public void testSwipeToRefresh() {
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(TestAssets.getPlacesListResponse(getInstrumentation().getContext())));

        //content after update
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(TestAssets.getPlacesListResponse2(getInstrumentation().getContext())));

        mActivityRule.launchActivity(new Intent());

        onView(withId(R.id.recycler_places))
                .check(matches(hasDescendant(withText(StubCheckValues.TITLE_FROM_PLACES))));

        onView(withId(R.id.recycler_places)).perform(swipeDown());

        onView(withId(R.id.recycler_places))
                .check(matches(hasDescendant(withText(StubCheckValues.TITLE_FROM_PLACES2))));
    }

    @Test
    public void testPagination() throws InterruptedException {
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(TestAssets.getPlacesListResponse(getInstrumentation().getContext())));

        //pagination content
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(TestAssets.getPlacesListResponse2(getInstrumentation().getContext())));

        mActivityRule.launchActivity(new Intent());

        onView(withId(R.id.recycler_places))
                .check(matches(hasDescendant(withText(StubCheckValues.TITLE_FROM_PLACES))));

        //half scroll to perform pagination loading
        onView(withId(R.id.recycler_places)).perform(RecyclerViewActions.scrollToPosition(StubCheckValues.PLACES_SECOND_PAGE / 2));

        //scroll to paginated content
        onView(withId(R.id.recycler_places)).perform(RecyclerViewActions.scrollToPosition(StubCheckValues.PLACES_SECOND_PAGE));

        onView(withId(R.id.recycler_places))
                .check(matches(hasDescendant(withText(StubCheckValues.TITLE_FROM_PLACES2))));
    }

}
