
package com.buddy.sdk.unittests.scenariotest;

import java.util.Date;
import java.util.List;

import com.buddy.sdk.AuthenticatedUser;
import com.buddy.sdk.BuddyClient;
import com.buddy.sdk.User;
import com.buddy.sdk.responses.ListResponse;
import com.buddy.sdk.responses.Response;
import com.buddy.sdk.unittests.BaseUnitTest;
import com.buddy.sdk.utils.Constants;
import com.buddy.sdk.web.BuddyHttpClientFactory;

public class FriendsTest extends BaseUnitTest {
    BuddyClient client = null;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        BuddyHttpClientFactory.setUnitTestMode(false);
        client = new BuddyClient(applicationName, applicationPassword, this.getInstrumentation().getContext());
    }

    public void testAddRemoveFriends() {
        Response<AuthenticatedUser> response = null;
        Response<Boolean> boolResponse = null;
        ListResponse<User> userListResponse = null;

        String friend1Name = String.valueOf(new Date().getTime());
        String friend2Name = friend1Name + "_friend";

        // Create two users
        response = ScenarioWrapper.CreateUser_Wrapper(client, friend1Name, friend1Name);
        assertNotNull(response);
        AuthenticatedUser user = response.getResult();
        assertNotNull(user);

        response = ScenarioWrapper.CreateUser_Wrapper(client, friend2Name, friend2Name);
        assertNotNull(response);
        AuthenticatedUser user2 = response.getResult();
        assertNotNull(user2);

        // log them in
        response = ScenarioWrapper.Login_Wrapper(client, friend1Name, friend1Name, null);
        assertNotNull(response);
        user = response.getResult();
        assertNotNull(user);

        response = ScenarioWrapper.Login_Wrapper(client, friend2Name, friend2Name, null);
        assertNotNull(response);
        user2 = response.getResult();
        assertNotNull(user2);

        // get rid of any friends they have
        userListResponse = ScenarioWrapper.GetAll_Wrapper(user.getFriends(), Constants.MinDate,
                null);
        if (userListResponse != null && userListResponse.getList() != null
                && userListResponse.getList().size() > 0) {
            for (User userItem : userListResponse.getList()) {
                ScenarioWrapper.Remove_Wrapper(user.getFriends(), userItem, null);
            }
        }

        // deny!
        userListResponse = ScenarioWrapper.GetAll_Wrapper(user.getFriends().getRequests(),
                Constants.MinDate, null);
        if (userListResponse != null && userListResponse.getList() != null
                && userListResponse.getList().size() > 0) {
            for (User userItem : userListResponse.getList()) {
                ScenarioWrapper.Deny_Wrapper(user.getFriends().getRequests(), userItem, null);
            }
        }

        // check that no friends are left.
        //
        userListResponse = ScenarioWrapper.GetAll_Wrapper(user.getFriends(), Constants.MinDate,
                null);
        List<User> list = userListResponse.getList();
        assertTrue(list.size() == 0);

        // add a friend request
        boolResponse = ScenarioWrapper.Add_Wrapper(user2.getFriends().getRequests(), user, null);
        assertTrue(boolResponse.getResult());

        // verify the request

        userListResponse = ScenarioWrapper.GetAll_Wrapper(user.getFriends().getRequests(),
                Constants.MinDate, null);
        List<User> list2 = userListResponse.getList();
        assertTrue(list2.size() == 1);

        // delete the users.
        ScenarioWrapper.DeleteUser_Wrapper(user, null);
        ScenarioWrapper.DeleteUser_Wrapper(user2, null);
    }
}
