/* Copyright (C) 2012 Buddy Platform, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0 
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.buddy.sdk.unittests;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.buddy.sdk.AppMetadata;
import com.buddy.sdk.BuddyClient;
import com.buddy.sdk.Callbacks.OnCallback;
import com.buddy.sdk.MetadataItem;
import com.buddy.sdk.MetadataSum;
import com.buddy.sdk.responses.ListResponse;
import com.buddy.sdk.responses.Response;
import com.buddy.sdk.web.BuddyHttpClientFactory;

public class AppMetadataUnitTests extends BaseUnitTest {
    private static final String testKeyList = "TestMetaKey;";
    private static final String testKey = "TestMetaKey";
    private static final String testValue = "23";

    private static BuddyClient testClient = null;
    private static AppMetadata testClientMeta = null;

    @Override
	public void setUp() throws Exception {
        super.setUp();

        BuddyHttpClientFactory.setUnitTestMode(true);
        
        testClient = new BuddyClient(applicationName, applicationPassword, this.getUnitTestContext(), "0.1", true);
        testClientMeta = testClient.getAppMeta();
    }

    @Test public void testSet() {
        String jsonResponse = readDataFromFileAsString("DataResponses/GenericSuccessResponse.json");
        BuddyHttpClientFactory.addDummyResponse(jsonResponse);

        testClientMeta.set(testKey, testValue, 0.0, 0.0, "", null,
                new OnCallback<Response<Boolean>>() {
                    public void OnResponse(Response<Boolean> response, Object state) {
                        assertNotNull(response);
                        assertTrue(response.getResult());
                    }

                });
    }

    @Test public void testGet() {
        String jsonResponse = readDataFromFileAsString("DataResponses/AppMetadataUnitTests-Get.json");
        BuddyHttpClientFactory.addDummyResponse(jsonResponse);

        testClientMeta.get(testKey, null, new OnCallback<Response<MetadataItem>>() {
            public void OnResponse(Response<MetadataItem> response, Object state) {
                assertNotNull(response);
                MetadataItem item = response.getResult();

                assertEquals("23", item.getValue());
            }

        });
    }

    @Test public void testSum() {
        String jsonResponse = readDataFromFileAsString("DataResponses/AppMetadataUnitTests-Sum.json");
        BuddyHttpClientFactory.addDummyResponse(jsonResponse);

        testClientMeta.sum(testKey, -1, 0.0, 0.0, -1, "", null,
                new OnCallback<Response<MetadataSum>>() {
                    public void OnResponse(Response<MetadataSum> response, Object state) {
                        assertNotNull(response);
                        MetadataSum item = response.getResult();

                        assertEquals(46.0, item.getTotal(), 0);
                    }

                });
    }

    @Test public void testBatchSum() {
        String jsonResponse = readDataFromFileAsString("DataResponses/AppMetadataUnitTests-BatchSum.json");
        BuddyHttpClientFactory.addDummyResponse(jsonResponse);

        testClientMeta.batchSum(testKeyList, "-1;", 0.0, 0.0, -1, "", null,
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
        String jsonResponse = readDataFromFileAsString("DataResponses/AppMetadataUnitTests-Search.json");
        BuddyHttpClientFactory.addDummyResponse(jsonResponse);

        testClientMeta.getAll(null, new OnCallback<Response<Map<String, MetadataItem>>>() {
            public void OnResponse(Response<Map<String, MetadataItem>> response, Object state) {
                assertNotNull(response);
                Map<String, MetadataItem> map = response.getResult();
                assertNotNull(map);
                MetadataItem item = map.get(testKey);
                assertEquals("23", item.getValue());
            }

        });
    }

    @Test public void testFindData() {
        String jsonResponse = readDataFromFileAsString("DataResponses/AppMetadataUnitTests-Search.json");
        BuddyHttpClientFactory.addDummyResponse(jsonResponse);

        testClientMeta.find(40075000, 0.0, 0.0, 10, "", "", -1, 0, 100, false, true, false, null,
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

    @Test public void testFindNearby() {
        String jsonResponse = readDataFromFileAsString("DataResponses/AppMetadataUnitTests-Search.json");
        BuddyHttpClientFactory.addDummyResponse(jsonResponse);

        testClientMeta.find(1000, 0.0, 0.0, 10, "", "", -1, 0, 100, false, true, false, null,
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

    @Test public void testDelete() {
        String jsonResponse = readDataFromFileAsString("DataResponses/GenericSuccessResponse.json");
        BuddyHttpClientFactory.addDummyResponse(jsonResponse);

        testClientMeta.delete(testKey, null, new OnCallback<Response<Boolean>>() {
            public void OnResponse(Response<Boolean> response, Object state) {
                assertNotNull(response);
                assertTrue(response.getResult());
            }

        });
    }

    @Test public void testDeleteAll() {
        String jsonResponse = readDataFromFileAsString("DataResponses/GenericSuccessResponse.json");
        BuddyHttpClientFactory.addDummyResponse(jsonResponse);

        testClientMeta.deleteAll(null, new OnCallback<Response<Boolean>>() {
            public void OnResponse(Response<Boolean> response, Object state) {
                assertNotNull(response);
                assertTrue(response.getResult());
            }

        });
    }
}
