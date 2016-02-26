package com.test.placesapp.functional;

import android.content.Intent;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;

import com.squareup.spoon.Spoon;
import com.test.placesapp.R;
import com.test.placesapp.activity.PlaceDetailsActivity;
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

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class PlacesListActivityTest {

    private static final String TAG = PlacesListActivityTest.class.getSimpleName();

    private MockWebServer mockWebServer;

    @Rule
    public ActivityTestRule<PlacesListActivity> activityRule = new ActivityTestRule<>(PlacesListActivity.class, true, false);

    @Before
    public void setUp() throws Exception {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        ApiClient.setBaseUrl(mockWebServer.url("/").toString());
    }

    @After
    public void shutDownServer() throws Exception {
        mockWebServer.shutdown();
    }

    @Test
    public void testPlacesShown() {
        initWithExpectedResponse();

        onView(withId(R.id.progress_bar)).check(matches(not(isDisplayed())));
        onView(withId(R.id.recycler_places)).check(matches(hasDescendant(withText(StubCheckValues.TITLE_FROM_PLACES))));
    }

    @Test
    public void testSwipeToRefresh() {
        initWithExpectedResponse();

        onView(withId(R.id.recycler_places)).check(matches(hasDescendant(withText(StubCheckValues.TITLE_FROM_PLACES))));

        //pull to refresh action
        onView(withId(R.id.recycler_places)).perform(swipeDown());
        Spoon.screenshot(activityRule.getActivity(), TAG);

        onView(withId(R.id.recycler_places)).check(matches(hasDescendant(withText(StubCheckValues.TITLE_FROM_PLACES2))));
    }

    @Test
    public void testPagination() {
        initWithExpectedResponse();

        onView(withId(R.id.recycler_places)).check(matches(hasDescendant(withText(StubCheckValues.TITLE_FROM_PLACES))));

        //half scroll to perform pagination loading
        onView(withId(R.id.recycler_places)).perform(RecyclerViewActions.scrollToPosition(StubCheckValues.PLACES_SECOND_PAGE / 2));
        Spoon.screenshot(activityRule.getActivity(), TAG);

        //scroll to paginated content
        onView(withId(R.id.recycler_places)).perform(RecyclerViewActions.scrollToPosition(StubCheckValues.PLACES_SECOND_PAGE));
        Spoon.screenshot(activityRule.getActivity(), TAG);

        onView(withId(R.id.recycler_places)).check(matches(hasDescendant(withText(StubCheckValues.TITLE_FROM_PLACES2))));
    }

    @Test
    public void testRetryShowIfNotLoaded() {
        initWithErrorResponse();

        onView(withId(R.id.btn_reload)).check(matches(isDisplayed()));
        onView(withText(StubCheckValues.ERROR_MESSAGE)).check(matches(isDisplayed()));
    }

    private void initWithExpectedResponse() {
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(TestAssets.getPlacesListResponse(getInstrumentation().getContext())));

        //second page or update content
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(TestAssets.getPlacesListResponse2(getInstrumentation().getContext())));

        activityRule.launchActivity(new Intent());
        Spoon.screenshot(activityRule.getActivity(), TAG);
    }

    private void initWithErrorResponse() {
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(404)
                .setBody(TestAssets.getErrorResponse(getInstrumentation().getContext())));

        activityRule.launchActivity(new Intent());
        Spoon.screenshot(activityRule.getActivity(), TAG);
    }

}
