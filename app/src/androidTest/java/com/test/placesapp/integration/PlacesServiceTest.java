package com.test.placesapp.integration;

import com.test.placesapp.model.PlacesResponseModel;
import com.test.placesapp.network.ApiClient;
import com.test.placesapp.network.PlacesService;
import com.test.placesapp.utils.TestCallback;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

import static junit.framework.Assert.assertTrue;

public class PlacesServiceTest {

    @Test
    public void checkGetPlaces() throws InterruptedException {
        ApiClient.setBaseUrl(PlacesService.BASE_URL);
        final CountDownLatch signal = new CountDownLatch(1);
        ApiClient.getInstance().getPlacesAsync(0, new TestCallback<PlacesResponseModel>(null, null) {
            @Override
            public void onDataLoaded(PlacesResponseModel result) {
                assertTrue(result.getBlock().getItems().size() > 0);
                signal.countDown();
            }
        });
        signal.await();
    }

}
