
package com.buddy.sdk.unittests.scenariotest;

import com.buddy.sdk.AuthenticatedUser;
import com.buddy.sdk.BuddyClient;
import com.buddy.sdk.User;
import com.buddy.sdk.responses.Response;
import com.buddy.sdk.unittests.BaseUnitTest;
import com.buddy.sdk.utils.Constants.UserGender;
import com.buddy.sdk.utils.Constants.UserStatus;
import com.buddy.sdk.web.BuddyHttpClientFactory;

public class UserTest extends BaseUnitTest {
    BuddyClient client = null;
    BuddyClient clientBadPass = null;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        BuddyHttpClientFactory.setUnitTestMode(false);
        client = new BuddyClient(applicationName, applicationPassword, this.getInstrumentation().getContext());
        clientBadPass = new BuddyClient(applicationName, "4A2E47F6-D72C-4EF1", this.getInstrumentation().getContext());
    }

    public void testAuth() {
        Response<AuthenticatedUser> response = null;
        AuthenticatedUser user = null;

        response = ScenarioWrapper.Login_Wrapper(client, testUserName, testUserPassword, null);
        assertNotNull(response);
        user = response.getResult();
        assertNotNull(user);
        assertEquals(testUserName, user.getName());

        response = ScenarioWrapper.Login_Wrapper(clientBadPass, testUserName, "test1", null);
        assertNotNull(response);
        user = response.getResult();
        assertNull(user);
        Throwable throwable = response.getThrowable();
        assertNotNull(throwable);
        assertEquals("WrongSocketLoginOrPass", throwable.getMessage());

        response = ScenarioWrapper.Login_Wrapper(client, "Test2", "test2", null);
        assertNotNull(response);
        user = response.getResult();
        assertNull(user);
        throwable = response.getThrowable();
        assertNotNull(throwable);
        assertEquals("SecurityFailedBadUserName", throwable.getMessage());

        response = ScenarioWrapper.Login_Wrapper(client, testToken,
                null, null);
        assertNotNull(response);
        user = response.getResult();
        assertNotNull(user);
        assertEquals(testUserName, user.getName());
        assertEquals(testToken, user.getToken());

        response = ScenarioWrapper.Login_Wrapper(client, "bad token", null, null);
        assertNotNull(response);
        user = response.getResult();
        assertNull(user);
    }

    public void testFindUser() {
        Response<AuthenticatedUser> response = null;
        Response<User> userResponse = null;

        AuthenticatedUser authUser = null;
        User user = null;

        response = ScenarioWrapper.Login_Wrapper(client, testUserName, testUserPassword, null);
        assertNotNull(response);
        authUser = response.getResult();
        assertNotNull(authUser);
        assertEquals(testUserName, authUser.getName());

        userResponse = ScenarioWrapper.FindUser_Wrapper(authUser, Integer.MAX_VALUE, "TEST1");
        assertNotNull(userResponse);
        user = userResponse.getResult();
        assertNull(user);
    }

    public void testCreateUser() {
        Response<AuthenticatedUser> response = null;

        response = ScenarioWrapper.CreateUser_Wrapper(client, "testUser", "testUser");
        assertNotNull(response);
        assertEquals("UserNameAlreadyInUse", response.getThrowable().getMessage());

        response = ScenarioWrapper.CreateUser_Wrapper(client, "testUser", "testUser",
                UserGender.any, -1, "", UserStatus.Any, false, false, "", null);
        assertNotNull(response);
        Throwable throwable = response.getThrowable();
        assertNotNull(throwable);
        assertTrue(throwable instanceof IllegalArgumentException);

        response = ScenarioWrapper.CreateUser_Wrapper(client, "testUser", "", UserGender.any, -1,
                "", UserStatus.Any, false, false, "", null);
        assertNotNull(response);
        throwable = response.getThrowable();
        assertNotNull(throwable);
        assertTrue(throwable instanceof IllegalArgumentException);

        response = ScenarioWrapper.CreateUser_Wrapper(client, "", "testUser", UserGender.any, -1,
                "", UserStatus.Any, false, false, "", null);
        assertNotNull(response);
        throwable = response.getThrowable();
        assertNotNull(throwable);
        assertTrue(throwable instanceof IllegalArgumentException);

        response = ScenarioWrapper.CreateUser_Wrapper(client, null, "testUser", UserGender.any, -1,
                "", UserStatus.Any, false, false, "", null);
        assertNotNull(response);
        throwable = response.getThrowable();
        assertNotNull(throwable);
        assertTrue(throwable instanceof IllegalArgumentException);

        response = ScenarioWrapper.CreateUser_Wrapper(client, "testUser", "testUser");
        assertNotNull(response);
        assertEquals("UserNameAlreadyInUse", response.getThrowable().getMessage());
    }
}
