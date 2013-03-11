
package com.buddy.sdk.unittests.scenariotest;

import java.util.List;

import android.util.SparseArray;

import com.buddy.sdk.AuthenticatedUser;
import com.buddy.sdk.BuddyClient;
import com.buddy.sdk.Place;
import com.buddy.sdk.responses.ListResponse;
import com.buddy.sdk.responses.Response;
import com.buddy.sdk.unittests.BaseUnitTest;
import com.buddy.sdk.web.BuddyHttpClientFactory;

public class LocationTest extends BaseUnitTest {
    BuddyClient client = null;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        BuddyHttpClientFactory.setUnitTestMode(false);
        client = new BuddyClient(applicationName, applicationPassword, this.getInstrumentation().getContext());
    }

    public void testSearchAndFlag() {
        Response<AuthenticatedUser> response = null;
        ListResponse<Place> placeListResponse = null;
        Response<Place> placeResponse = null;
        Response<SparseArray<String>> categoryResponse = null;
        AuthenticatedUser user = null;

        response = ScenarioWrapper.Login_Wrapper(client,testUserName, testUserPassword, null);
        assertNotNull(response);
        user = response.getResult();
        assertNotNull(user);
        assertEquals(testUserName, user.getName());

        placeListResponse = ScenarioWrapper
                .Find_Wrapper(user.getPlaces(), 9999999, 0, 0, 870, null);
        assertNotNull(placeListResponse);
        List<Place> places = placeListResponse.getList();
        assertNotNull(places);

        categoryResponse = ScenarioWrapper.GetCategories_Wrapper(user.getPlaces(), null);
        assertNotNull(categoryResponse);
        assertNotNull(categoryResponse.getResult());
        assertNotNull(categoryResponse.getResult().size() > 0);

        placeResponse = ScenarioWrapper.Get_Wrapper(user.getPlaces(), places.get(0).getId(), 10,
                10, null);
        assertNotNull(placeResponse);
        Place place = placeResponse.getResult();
        assertNotNull(place);
        assertEquals(place.getAddress(), places.get(0).getAddress());
        assertTrue(place.getDistanceInKilometers() > 0);
    }

}
