
package com.buddy.sdk.unittests;

import java.util.List;

import org.junit.Test;

import com.buddy.sdk.Callbacks.OnCallback;
import com.buddy.sdk.User;
import com.buddy.sdk.responses.ListResponse;
import com.buddy.sdk.responses.Response;
import com.buddy.sdk.utils.Constants;
import com.buddy.sdk.web.BuddyHttpClientFactory;

public class FriendsUnitTests extends BaseUnitTest {
    @Override
	public void setUp() throws Exception {
        createAuthenticatedUserAnd2ndUser();
    }

    @Test public void testFriendsRequestAccept() {
        String jsonResponse = readDataFromFileAsString("DataResponses/GenericSuccessResponse.json");
        BuddyHttpClientFactory.addDummyResponse(jsonResponse);

        testAuthUser.getFriends().getRequests()
                .accept(testUser, "", null, new OnCallback<Response<Boolean>>() {
                    public void OnResponse(Response<Boolean> response, Object state) {
                        assertNotNull(response);
                        assertTrue(response.getResult());
                    }

                });
    }

    @Test public void testFriendsRequestAdd() {
        String jsonResponse = readDataFromFileAsString("DataResponses/GenericSuccessResponse.json");
        BuddyHttpClientFactory.addDummyResponse(jsonResponse);

        testAuthUser.getFriends().getRequests()
                .add(testUser, "", null, new OnCallback<Response<Boolean>>() {
                    public void OnResponse(Response<Boolean> response, Object state) {
                        assertNotNull(response);
                        assertTrue(response.getResult());
                    }

                });
    }

    @Test public void testFriendsRequestDeny() {
        String jsonResponse = readDataFromFileAsString("DataResponses/GenericSuccessResponse.json");
        BuddyHttpClientFactory.addDummyResponse(jsonResponse);

        testAuthUser.getFriends().getRequests()
                .deny(testUser, null, new OnCallback<Response<Boolean>>() {
                    public void OnResponse(Response<Boolean> response, Object state) {
                        assertNotNull(response);
                        assertTrue(response.getResult());
                    }

                });
    }

    @Test public void testFriendsRequestGet() {
        String jsonResponse = readDataFromFileAsString("DataResponses/FriendsUnitTests-GetAllRequests.json");
        BuddyHttpClientFactory.addDummyResponse(jsonResponse);

        testAuthUser.getFriends().getRequests()
                .getAll(Constants.MinDate, null, new OnCallback<ListResponse<User>>() {
                    public void OnResponse(ListResponse<User> response, Object state) {
                        assertNotNull(response);

                        List<User> list = response.getList();
                        assertNotNull(list);

                        User user = list.get(0);
                        assertEquals(testUserIdInteger.compareTo(user.getId()), 0);
                    }

                });
    }

    @Test public void testFriendsGetList() {
        String jsonResponse = readDataFromFileAsString("DataResponses/FriendsUnitTests-GetAllFriends.json");
        BuddyHttpClientFactory.addDummyResponse(jsonResponse);

        testAuthUser.getFriends().getAll(Constants.MinDate, null,
                new OnCallback<ListResponse<User>>() {
                    public void OnResponse(ListResponse<User> response, Object state) {
                        assertNotNull(response);

                        List<User> list = response.getList();
                        assertNotNull(list);

                        User user = list.get(0);
                        assertEquals(testUserIdInteger.compareTo(user.getId()), 0);
                    }

                });
    }

    @Test public void testFriendsRemove() {
        String jsonResponse = readDataFromFileAsString("DataResponses/GenericSuccessResponse.json");
        BuddyHttpClientFactory.addDummyResponse(jsonResponse);

        testAuthUser.getFriends().remove(testUser, null, new OnCallback<Response<Boolean>>() {
            public void OnResponse(Response<Boolean> response, Object state) {
                assertNotNull(response);
                assertTrue(response.getResult());
            }

        });
    }
}
