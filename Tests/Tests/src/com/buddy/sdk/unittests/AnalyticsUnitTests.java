
package com.buddy.sdk.unittests;

import org.junit.Test;

import com.buddy.sdk.Callbacks.OnCallback;
import com.buddy.sdk.responses.Response;
import com.buddy.sdk.web.BuddyHttpClientFactory;

public class AnalyticsUnitTests extends BaseUnitTest {
    @Override
	public void setUp() throws Exception {
        super.setUp();

        createAuthenticatedUser();
    }

    @Test public void testStartSession() {
        String jsonValue = readDataFromFileAsString("DataResponses/AnalyticsUnitTests-StartSession.json");

        BuddyHttpClientFactory.addDummyResponse(jsonValue);

        testClient.startSession(testAuthUser, "Test Session", "", null,
                new OnCallback<Response<String>>() {
                    public void OnResponse(Response<String> response, Object state) {
                        assertNotNull(response);

                        String id = response.getResult();

                        assertEquals("617564", id);
                    }
                });
    }

    @Test public void testStartSessionOverload() {
        String jsonValue = readDataFromFileAsString("DataResponses/AnalyticsUnitTests-StartSession.json");

        BuddyHttpClientFactory.addDummyResponse(jsonValue);

        testClient.startSession(testAuthUser, "Test Session", new OnCallback<Response<String>>() {
            public void OnResponse(Response<String> response, Object state) {
                assertNotNull(response);

                String id = response.getResult();

                assertEquals("617564", id);
            }
        });
    }

    @Test public void testEndSession() {
        String jsonResponse = readDataFromFileAsString("DataResponses/GenericSuccessResponse.json");
        BuddyHttpClientFactory.addDummyResponse(jsonResponse);

        testClient.endSession(testAuthUser, "617564", "", null,
                new OnCallback<Response<Boolean>>() {
                    public void OnResponse(Response<Boolean> response, Object state) {
                        assertNotNull(response);
                        assertTrue(response.getResult());
                    }

                });
    }

    @Test public void testEndSessionOverload() {
        String jsonResponse = readDataFromFileAsString("DataResponses/GenericSuccessResponse.json");
        BuddyHttpClientFactory.addDummyResponse(jsonResponse);

        testClient.endSession(testAuthUser, "617564", new OnCallback<Response<Boolean>>() {
            public void OnResponse(Response<Boolean> response, Object state) {
                assertNotNull(response);
                assertTrue(response.getResult());
            }

        });
    }

    @Test public void testSessionRecordMetric() {
        String jsonResponse = readDataFromFileAsString("DataResponses/GenericSuccessResponse.json");
        BuddyHttpClientFactory.addDummyResponse(jsonResponse);

        testClient.recordSessionMetric(testAuthUser, "617564", "Test metric", "Test value", "",
                null, new OnCallback<Response<Boolean>>() {
                    public void OnResponse(Response<Boolean> response, Object state) {
                        assertNotNull(response);
                        assertTrue(response.getResult());
                    }

                });
    }

    @Test public void testSessionRecordMetricOverload() {
        String jsonResponse = readDataFromFileAsString("DataResponses/GenericSuccessResponse.json");
        BuddyHttpClientFactory.addDummyResponse(jsonResponse);

        testClient.recordSessionMetric(testAuthUser, "617564", "Test metric", "Test value",
                new OnCallback<Response<Boolean>>() {
                    public void OnResponse(Response<Boolean> response, Object state) {
                        assertNotNull(response);
                        assertTrue(response.getResult());
                    }

                });
    }

    @Test public void testCrashRecordsAdd() {
        String jsonResponse = readDataFromFileAsString("DataResponses/GenericSuccessResponse.json");
        BuddyHttpClientFactory.addDummyResponse(jsonResponse);

        testClient.getDevice().recordCrash("TestMethod", "4.0.3", "Android", testAuthUser, "1.0", "", 0,
                0, "", null, new OnCallback<Response<Boolean>>() {
                    public void OnResponse(Response<Boolean> response, Object state) {
                        assertNotNull(response);
                        assertTrue(response.getResult());
                    }

                });
    }

    @Test public void testDeviceInformationAdd() {
        String jsonResponse = readDataFromFileAsString("DataResponses/GenericSuccessResponse.json");
        BuddyHttpClientFactory.addDummyResponse(jsonResponse);

        testClient.getDevice().recordInformation("4.0.3", "Android", testAuthUser, "1.0", 0.0, 0.0, "", null,
                new OnCallback<Response<Boolean>>() {
                    public void OnResponse(Response<Boolean> response, Object state) {
                        assertNotNull(response);
                        assertTrue(response.getResult());
                    }

                });
    }
}
