package com.buddy.sdk.unittests.scenariotest;

import java.util.Date;
import java.util.Map;

import com.buddy.sdk.AuthenticatedUser;
import com.buddy.sdk.BuddyClient;
import com.buddy.sdk.RegisteredDeviceAndroid;
import com.buddy.sdk.responses.ListResponse;
import com.buddy.sdk.responses.Response;
import com.buddy.sdk.unittests.BaseUnitTest;
import com.buddy.sdk.web.BuddyHttpClientFactory;

public class PushTest extends BaseUnitTest {
    BuddyClient client = null;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        BuddyHttpClientFactory.setUnitTestMode(false);
        client = new BuddyClient(applicationName, applicationPassword, this.getInstrumentation().getContext());
    }

    public void testRegister() {
        Response<AuthenticatedUser> response = null;
        Response<Boolean> boolResponse = null;
        ListResponse<RegisteredDeviceAndroid> deviceListResponse = null;
        Response<Map<String, Integer>> integerMapResponse = null;
        AuthenticatedUser user = null;
        AuthenticatedUser user2 = null;

        response = ScenarioWrapper.Login_Wrapper(client, testUserName, testUserPassword, null);
        assertNotNull(response);
        user = response.getResult();
        assertNotNull(user);
        assertEquals(testUserName, user.getName());

        boolResponse = ScenarioWrapper.RegisterDevice_Wrapper(user.getPushNotifications(),
                "01234567890", "", null);
        assertNotNull(boolResponse);
        assertTrue(boolResponse.getResult());

        // should this user be different?
        response = ScenarioWrapper.Login_Wrapper(client, testUserName, testUserPassword, null);
        assertNotNull(response);
        user2 = response.getResult();
        assertNotNull(user);
        assertEquals(testUserName, user.getName());

        Date date = new Date();
        String groupName = "Test Group: " + String.valueOf(date.getTime());

        boolResponse = ScenarioWrapper.RegisterDevice_Wrapper(user2.getPushNotifications(),
                "01234567890", groupName, null);
        assertNotNull(boolResponse);
        assertTrue(boolResponse.getResult());

        deviceListResponse = ScenarioWrapper.GetRegisteredDevices_Wrapper(user.getPushNotifications(),
                null);
        assertNotNull(deviceListResponse);
        assertTrue(deviceListResponse.getList().size() >= 1);

        integerMapResponse = ScenarioWrapper.GetGroupsAsync_Wrapper(user.getPushNotifications(), null);
        assertNotNull(integerMapResponse);
        assertTrue(integerMapResponse.getResult().size() >= 1);

        boolResponse = ScenarioWrapper.UnregisterDevice_Wrapper(user.getPushNotifications(), null);
        assertNotNull(boolResponse);
        assertTrue(boolResponse.getResult());

        boolResponse = ScenarioWrapper.UnregisterDevice_Wrapper(user2.getPushNotifications(), null);
        assertNotNull(boolResponse);
        assertTrue(boolResponse.getResult());

        deviceListResponse = ScenarioWrapper.GetRegisteredDevices_Wrapper(user.getPushNotifications(),
                null);
        assertNotNull(deviceListResponse);
        assertTrue(deviceListResponse.getList().size() == 0);
    }
}
