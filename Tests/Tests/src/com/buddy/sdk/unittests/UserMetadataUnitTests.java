
package com.buddy.sdk.unittests;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.buddy.sdk.MetadataItem;
import com.buddy.sdk.MetadataSum;
import com.buddy.sdk.Callbacks.OnCallback;
import com.buddy.sdk.responses.ListResponse;
import com.buddy.sdk.responses.Response;
import com.buddy.sdk.web.BuddyHttpClientFactory;

public class UserMetadataUnitTests extends BaseUnitTest {
    private static final String testKeyList = "TestMetaKey;";
    private static final String testKey = "TestMetaKey";
    private static final String testValue = "23";

    @Override
	public void setUp() throws Exception {
        super.setUp();

        createAuthenticatedUser();
    }

    @Test public void testSet() {
        String jsonResponse = readDataFromFileAsString("DataResponses/GenericSuccessResponse.json");
        BuddyHttpClientFactory.addDummyResponse(jsonResponse);

        testAuthUser.getMetadata().set(testKey, testValue, 0.0, 0.0, "", null,
                new OnCallback<Response<Boolean>>() {
                    public void OnResponse(Response<Boolean> response, Object state) {
                        assertNotNull(response);
                        assertTrue(response.getResult());
                    }

                });
    }

    @Test public void testGet() {
        String jsonResponse = readDataFromFileAsString("DataResponses/UserMetadataUnitTests-Get.json");
        BuddyHttpClientFactory.addDummyResponse(jsonResponse);

        testAuthUser.getMetadata().get("TestMetaKey", null,
                new OnCallback<Response<MetadataItem>>() {
                    public void OnResponse(Response<MetadataItem> response, Object state) {
                        assertNotNull(response);
                        MetadataItem item = response.getResult();

                        assertEquals("23", item.getValue());
                    }

                });
    }

    @Test public void testSum() {
        String jsonResponse = readDataFromFileAsString("DataResponses/UserMetadataUnitTests-Sum.json");
        BuddyHttpClientFactory.addDummyResponse(jsonResponse);

        testAuthUser.getMetadata().sum(testKey, -1, 0.0, 0.0, -1, "", null,
                new OnCallback<Response<MetadataSum>>() {
                    public void OnResponse(Response<MetadataSum> response, Object state) {
                        assertNotNull(response);
                        MetadataSum item = response.getResult();

                        assertEquals(46.0, item.getTotal(), 0);
                    }

                });
    }

    @Test public void testBatchSum() {
        String jsonResponse = readDataFromFileAsString("DataResponses/UserMetadataUnitTests-BatchSum.json");
        BuddyHttpClientFactory.addDummyResponse(jsonResponse);

        testAuthUser.getMetadata().batchSum(testKeyList, "-1;", 0.0, 0.0, -1, "", null,
                new OnCallback<ListResponse<MetadataSum>>() {
                    public void OnResponse(ListResponse<MetadataSum> response, Object state) {
                        assertNotNull(response);
                        List<MetadataSum> list = response.getList();
                        assertNotNull(list);

                        MetadataSum sum = list.get(0);
                        assertEquals(46.0, sum.getTotal(), 0);
                    }

                });
    }

    @Test public void testGetAll() {
        String jsonResponse = readDataFromFileAsString("DataResponses/UserMetadataUnitTests-Search.json");
        BuddyHttpClientFactory.addDummyResponse(jsonResponse);

        testAuthUser.getMetadata().getAll(null,
                new OnCallback<Response<Map<String, MetadataItem>>>() {
                    public void OnResponse(Response<Map<String, MetadataItem>> response,
                            Object state) {
                        assertNotNull(response);
                        Map<String, MetadataItem> map = response.getResult();
                        assertNotNull(map);
                        MetadataItem item = map.get(testKey);
                        assertEquals("23", item.getValue());
                    }

                });
    }

    @Test public void testSearch() {
        String jsonResponse = readDataFromFileAsString("DataResponses/UserMetadataUnitTests-Search.json");
        BuddyHttpClientFactory.addDummyResponse(jsonResponse);

        testAuthUser.getMetadata().find(40075000, 0.0, 0.0, 10, "", "", -1, false, true,
                false, null, new OnCallback<Response<Map<String, MetadataItem>>>() {
                    public void OnResponse(Response<Map<String, MetadataItem>> response,
                            Object state) {
                        assertNotNull(response);
                        Map<String, MetadataItem> map = response.getResult();
                        assertNotNull(map);
                        MetadataItem item = map.get(testKey);
                        assertEquals("23", item.getValue());
                    }

                });
    }

    @Test public void testDelete() {
        String jsonResponse = readDataFromFileAsString("DataResponses/GenericSuccessResponse.json");
        BuddyHttpClientFactory.addDummyResponse(jsonResponse);

        testAuthUser.getMetadata().delete(testKey, null, new OnCallback<Response<Boolean>>() {
            public void OnResponse(Response<Boolean> response, Object state) {
                assertNotNull(response);
                assertTrue(response.getResult());
            }

        });
    }

    @Test public void testDeleteAll() {
        String jsonResponse = readDataFromFileAsString("DataResponses/GenericSuccessResponse.json");
        BuddyHttpClientFactory.addDummyResponse(jsonResponse);

        testAuthUser.getMetadata().deleteAll(null, new OnCallback<Response<Boolean>>() {
            public void OnResponse(Response<Boolean> response, Object state) {
                assertNotNull(response);
                assertTrue(response.getResult());
            }

        });
    }
    
    @Test public void testBatchSet() {
        String jsonResponse = readDataFromFileAsString("DataResponses/GenericSuccessResponse.json");
        BuddyHttpClientFactory.addDummyResponse(jsonResponse);

        testAuthUser.getMetadata().batchSet(testKeyList, "-1;", 0.0, 0.0, "", null,
                new OnCallback<Response<Boolean>>() {
                    public void OnResponse(Response<Boolean> response, Object state) {
                        assertNotNull(response);
                        assertTrue(response.getResult());
                    }

                });
    }
}
