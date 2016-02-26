package com.test.placesapp.functional;

import android.content.Context;
import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.google.gson.Gson;
import com.squareup.spoon.Spoon;
import com.test.placesapp.R;
import com.test.placesapp.activity.PlaceDetailsActivity;
import com.test.placesapp.model.PlacesResponseModel;
import com.test.placesapp.utils.StubCheckValues;
import com.test.placesapp.utils.TestAssets;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class PlaceDetailsActivityTest {

    private static final String TAG = PlaceDetailsActivity.class.getSimpleName();

    @Rule
    public ActivityTestRule<PlaceDetailsActivity> activityRule =
            new ActivityTestRule<PlaceDetailsActivity>(PlaceDetailsActivity.class) {
                @Override
                protected Intent getActivityIntent() {
                    Intent startIntent = new Intent(getInstrumentation().getTargetContext(), PlaceDetailsActivity.class);
                    Context testContext = getInstrumentation().getContext();
                    PlacesResponseModel.PlaceModel placeModel = new Gson().fromJson(TestAssets.getPlacesListResponse(testContext), PlacesResponseModel.class).getBlock().getItems().get(0);
                    startIntent.putExtra(PlaceDetailsActivity.KEY_PLACE, placeModel);
                    return startIntent;
                }
            };

    @Test
    public void testPlaceDetailsInfo() {
        onView(withText(StubCheckValues.TITLE_FROM_PLACES)).check(matches(isDisplayed()));
        Spoon.screenshot(activityRule.getActivity(), TAG);

        //scrolled down state
        onView(withId(R.id.main_scroll_view)).perform(swipeUp());
        Spoon.screenshot(activityRule.getActivity(), TAG);
    }

}
