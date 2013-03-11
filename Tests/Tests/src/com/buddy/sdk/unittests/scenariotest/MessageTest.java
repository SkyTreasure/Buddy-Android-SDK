
package com.buddy.sdk.unittests.scenariotest;

import java.util.Date;
import java.util.List;

import com.buddy.sdk.AuthenticatedUser;
import com.buddy.sdk.BuddyClient;
import com.buddy.sdk.Message;
import com.buddy.sdk.responses.ListResponse;
import com.buddy.sdk.responses.Response;
import com.buddy.sdk.unittests.BaseUnitTest;
import com.buddy.sdk.utils.Constants;
import com.buddy.sdk.web.BuddyHttpClientFactory;

public class MessageTest extends BaseUnitTest {
    BuddyClient client = null;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        BuddyHttpClientFactory.setUnitTestMode(false);
        client = new BuddyClient(applicationName, applicationPassword, this.getInstrumentation().getContext());
    }

    public void testMessage() {
        Response<AuthenticatedUser> response = null;
        Response<Boolean> boolResponse = null;
        ListResponse<Message> messageListResponse = null;
        AuthenticatedUser user = null;
        AuthenticatedUser user2 = null;

        response = ScenarioWrapper.Login_Wrapper(client, testUserName, testUserPassword, null);
        assertNotNull(response);
        user = response.getResult();
        assertNotNull(user);
        assertEquals(testUserName, user.getName());

        // TODO should this be a different user?
        response = ScenarioWrapper.Login_Wrapper(client, testUserName, testUserPassword, null);
        assertNotNull(response);
        user2 = response.getResult();
        assertNotNull(user);
        assertEquals(testUserName, user.getName());

        Date date = new Date();
        String dateTicks = String.valueOf(date.getTime());

        boolResponse = ScenarioWrapper.Send_Wrapper(user.getMessages(), user2, dateTicks, null);
        assertNotNull(boolResponse);
        assertTrue(boolResponse.getResult());

        messageListResponse = ScenarioWrapper.GetReceived_Wrapper(user2.getMessages(),
                Constants.MinDate, null);
        assertNotNull(messageListResponse);
        List<Message> messageList = messageListResponse.getList();
        assertNotNull(messageList);
        assertTrue(messageList.size() > 0);

        boolean found = false;
        for (Message message : messageList) {
            if (message.getText().equals(dateTicks)) {
                found = true;
            }
        }
        assertTrue(found);
    }

}
