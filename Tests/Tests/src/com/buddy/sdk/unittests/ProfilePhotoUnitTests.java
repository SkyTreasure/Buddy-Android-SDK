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

import org.junit.Test;

import com.buddy.sdk.Callbacks.OnCallback;
import com.buddy.sdk.PicturePublic;
import com.buddy.sdk.responses.Response;

import com.buddy.sdk.responses.ListResponse;
import com.buddy.sdk.web.BuddyHttpClientFactory;

public class ProfilePhotoUnitTests extends BaseUnitTest {
    @Override
	public void setUp() throws Exception {
        super.setUp();

        createAuthenticatedUser();
    }

    @Test public void testAddProfilePhoto() {
        String jsonResponse = readDataFromFileAsString("DataResponses/GenericSuccessResponse.json");
        BuddyHttpClientFactory.addDummyResponse(jsonResponse);

        // Need to encode an image
        byte[] byteData = readDataFromFileAsStringAsBytes("res/drawable-hdpi/ic_launcher.png");

        testAuthUser.addProfilePhoto(byteData, "", null, new OnCallback<Response<Boolean>>() {
            public void OnResponse(Response<Boolean> response, Object state) {
                assertNotNull(response);
                assertTrue(response.getResult());
            }

        });
    }

    @Test public void testGetAllProfilePhotos() {
        String jsonResponse = readDataFromFileAsString("DataResponses/ProfilePhotoUnitTests-GetAllProfilePhotos.json");
        BuddyHttpClientFactory.addDummyResponse(jsonResponse);

        testAuthUser.getProfilePhotos(null, new OnCallback<ListResponse<PicturePublic>>() {

            @Override
            public void OnResponse(ListResponse<PicturePublic> response, Object state) {
                assertNotNull(response);
                assertEquals(1, response.getList().size());

                PicturePublic pic = response.getList().get(0);

                assertEquals(2003108, pic.getPhotoId());
                assertEquals("TEST PICTURE", pic.getComment());
            }

        });
    }

    @Test public void testDeleteProfilePhoto() {
        String jsonResponse = readDataFromFileAsString("DataResponses/ProfilePhotoUnitTests-GetAllProfilePhotos.json");
        String jsonAddResponse = readDataFromFileAsString("DataResponses/GenericSuccessResponse.json");
        BuddyHttpClientFactory.addDummyResponse(jsonResponse);
        BuddyHttpClientFactory.addDummyResponse(jsonAddResponse);

        testAuthUser.getProfilePhotos(null, new OnCallback<ListResponse<PicturePublic>>() {

            @Override
            public void OnResponse(ListResponse<PicturePublic> response, Object state) {
                assertNotNull(response);
                assertEquals(1, response.getList().size());

                PicturePublic pic = response.getList().get(0);

                testAuthUser.deleteProfilePhoto(pic, null, new OnCallback<Response<Boolean>>() {
                    public void OnResponse(Response<Boolean> response, Object state) {
                        assertNotNull(response);
                        assertTrue(response.getResult());
                    }

                });
            }

        });
    }

    @Test public void testSetProfilePhoto() {
        String jsonResponse = readDataFromFileAsString("DataResponses/ProfilePhotoUnitTests-GetAllProfilePhotos.json");
        String jsonAddResponse = readDataFromFileAsString("DataResponses/GenericSuccessResponse.json");
        BuddyHttpClientFactory.addDummyResponse(jsonResponse);
        BuddyHttpClientFactory.addDummyResponse(jsonAddResponse);

        testAuthUser.getProfilePhotos(null, new OnCallback<ListResponse<PicturePublic>>() {

            @Override
            public void OnResponse(ListResponse<PicturePublic> response, Object state) {
                assertNotNull(response);
                assertEquals(1, response.getList().size());

                PicturePublic pic = response.getList().get(0);

                testAuthUser.setProfilePhoto(pic, null, new OnCallback<Response<Boolean>>() {
                    public void OnResponse(Response<Boolean> response, Object state) {
                        assertNotNull(response);
                        assertTrue(response.getResult());
                    }

                });
            }

        });
    }

}
