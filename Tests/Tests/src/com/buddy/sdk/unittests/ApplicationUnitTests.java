
package com.buddy.sdk.unittests;

import java.util.List;

import org.junit.Test;

import com.buddy.sdk.ApplicationStatistics;
import com.buddy.sdk.BuddyClient;
import com.buddy.sdk.Callbacks.OnCallback;
import com.buddy.sdk.User;
import com.buddy.sdk.responses.ListResponse;
import com.buddy.sdk.web.BuddyHttpClientFactory;

public class ApplicationUnitTests extends BaseUnitTest {
    @Test public void testGetUserEmails() {
        String jsonValue = readDataFromFileAsString("DataResponses/ApplicationUnitTests-GetUserEmails.json");

        BuddyHttpClientFactory.addDummyResponse(jsonValue);

        BuddyHttpClientFactory.setUnitTestMode(true);

        BuddyClient client = new BuddyClient(applicationName, applicationPassword, this.getUnitTestContext(), "0.1", true);

        client.getUserEmails(testFirstRow, testLastRow, null,
                new OnCallback<ListResponse<String>>() {
                    public void OnResponse(ListResponse<String> response, Object state) {
                        assertNotNull(response);
                        List<String> list = response.getList();
                        assertNotNull(list);
                        assertTrue(list.size() == 10);

                        String stringItem = list.get(9);
                        assertEquals("emai", stringItem);
                    }
                });
    }

    @Test public void testGetUserProfiles() {
        String jsonValue = readDataFromFileAsString("DataResponses/ApplicationUnitTests-GetUserProfiles.json");

        BuddyHttpClientFactory.addDummyResponse(jsonValue);

        BuddyHttpClientFactory.setUnitTestMode(true);

        BuddyClient client = new BuddyClient(applicationName, applicationPassword, this.getUnitTestContext(), "0.1", true);

        client.getUserProfiles(testFirstRow, testLastRow, null,
                new OnCallback<ListResponse<User>>() {
                    public void OnResponse(ListResponse<User> response, Object state) {
                        assertNotNull(response);
                        List<User> list = response.getList();
                        assertNotNull(list);

                        User user = list.get(1);
                        assertEquals(21, user.getAge());
                    }
                });
    }

    @Test public void testGetApplicationStatistics() {
        String jsonValue = readDataFromFileAsString("DataResponses/ApplicationUnitTests-GetAppStats.json");

        BuddyHttpClientFactory.addDummyResponse(jsonValue);

        BuddyHttpClientFactory.setUnitTestMode(true);

        BuddyClient client = new BuddyClient(applicationName, applicationPassword, this.getUnitTestContext(), "0.1", true);

        client.getApplicationStatistics(null,
                new OnCallback<ListResponse<ApplicationStatistics>>() {
                    public void OnResponse(ListResponse<ApplicationStatistics> response,
                            Object state) {
                        assertNotNull(response);
                        List<ApplicationStatistics> list = response.getList();
                        assertNotNull(list);

                        ApplicationStatistics appStats = list.get(0);
                        assertEquals("31", appStats.getTotalFriends());
                    }
                });
    }
}
