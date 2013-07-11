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

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.buddy.sdk.PhotoAlbumPublic;
import com.buddy.sdk.Callbacks.OnCallback;
import com.buddy.sdk.PicturePublic;

import com.buddy.sdk.responses.Response;

import com.buddy.sdk.utils.Constants;
import com.buddy.sdk.web.BuddyHttpClientFactory;
import com.buddy.sdk.PhotoAlbum;
import com.buddy.sdk.Picture;

public class PicturesUnitTests extends BaseUnitTest {
    private static int testAlbumId = 2196998;

    @Override
	public void setUp() throws Exception {
        super.setUp();

        createAuthenticatedUser();
    }

    @Test public void testPhotoAlbumCreate() {
        String jsonResponse = readDataFromFileAsString("DataResponses/PictureUnitTests-CreateAlbum.json");
        String jsonGetResponse = readDataFromFileAsString("DataResponses/PictureUnitTests-GetAlbum.json");
        BuddyHttpClientFactory.addDummyResponse(jsonResponse);
        BuddyHttpClientFactory.addDummyResponse(jsonGetResponse);

        testAuthUser.getPhotoAlbums().create(testAlbumName, testPublicAlbumBit,
                testAlbumApplicationTag, null, new OnCallback<Response<PhotoAlbum>>() {
                    public void OnResponse(Response<PhotoAlbum> response, Object state) {
                        PhotoAlbum album = response.getResult();
                        assertEquals(3182262, album.getAlbumId());
                    }

                });
    }

    @Test public void testPhotoAlbumDelete() {
        String jsonResponse = readDataFromFileAsString("DataResponses/PictureUnitTests-GetAlbum.json");
        String jsonDeleteResponse = readDataFromFileAsString("DataResponses/GenericSuccessResponse.json");
        BuddyHttpClientFactory.addDummyResponse(jsonResponse);
        BuddyHttpClientFactory.addDummyResponse(jsonDeleteResponse);

        testAuthUser.getPhotoAlbums().get(testAlbumId, null,
                new OnCallback<Response<PhotoAlbum>>() {

                    @Override
                    public void OnResponse(Response<PhotoAlbum> response, Object state) {
                        assertNotNull(response);

                        PhotoAlbum album = response.getResult();
                        album.delete(null, new OnCallback<Response<Boolean>>() {
                            public void OnResponse(Response<Boolean> response, Object state) {
                                assertNotNull(response);
                                assertTrue(response.getResult());
                            }

                        });
                    }

                });
    }

    @Test public void testPhotoAlbumGet() {
        String jsonResponse = readDataFromFileAsString("DataResponses/PictureUnitTests-GetAlbum.json");
        BuddyHttpClientFactory.addDummyResponse(jsonResponse);

        testAuthUser.getPhotoAlbums().get(testAlbumId, null,
                new OnCallback<Response<PhotoAlbum>>() {

                    @Override
                    public void OnResponse(Response<PhotoAlbum> response, Object state) {
                        assertNotNull(response);

                        PhotoAlbum album = response.getResult();
                        List<Picture> pictures = album.getPictures();
                        assertNotNull(pictures);

                        Picture pic = pictures.get(0);
                        assertEquals("Test Photo", pic.getComment());
                    }

                });
    }

    @Test public void testPhotoAlbumGetByName() {
        String jsonAlbumList = readDataFromFileAsString("DataResponses/PictureUnitTests-GetAlbumList.json");
        String jsonPhotoList = readDataFromFileAsString("DataResponses/PictureUnitTests-GetAlbumPhotos.json");

        BuddyHttpClientFactory.addDummyResponse(jsonAlbumList);
        BuddyHttpClientFactory.addDummyResponse(jsonPhotoList);

        testAuthUser.getPhotoAlbums().get(testAlbumName, null,
                new OnCallback<Response<PhotoAlbum>>() {
                    public void OnResponse(Response<PhotoAlbum> response, Object state) {
                        assertNotNull(response);

                        PhotoAlbum album = response.getResult();
                        List<Picture> pictures = album.getPictures();
                        assertNotNull(pictures);

                        Picture pic = pictures.get(0);
                        assertEquals(4541022, pic.getPhotoId());
                    }
                });
    }

    @Test public void testPhotoAddWithWatermark() {
        String jsonResponse = readDataFromFileAsString("DataResponses/PictureUnitTests-GetAlbum.json");
        String jsonAddResponse = readDataFromFileAsString("DataResponses/PictureUnitTests-AddPhoto.json");
        String jsonGetResponse = readDataFromFileAsString("DataResponses/PictureUnitTests-GetPhoto.json");

        BuddyHttpClientFactory.addDummyResponse(jsonResponse);
        BuddyHttpClientFactory.addDummyResponse(jsonAddResponse);
        BuddyHttpClientFactory.addDummyResponse(jsonGetResponse);

        testAuthUser.getPhotoAlbums().get(testAlbumId, null,
                new OnCallback<Response<PhotoAlbum>>() {

                    @Override
                    public void OnResponse(Response<PhotoAlbum> response, Object state) {
                        assertNotNull(response);

                        PhotoAlbum album = response.getResult();

                        testImageBytes = readDataFromFileAsStringAsBytes("res/drawable-hdpi/ic_launcher.png");

                        // Need to create our Picture class
                        album.addPictureWithWatermark(testImageBytes, testPhotoComment,
                                testLatitude, testLongitude, testWatermarkMessage, null,
                                new OnCallback<Response<Picture>>() {

                                    @Override
                                    public void OnResponse(Response<Picture> response, Object state) {
                                        assertNotNull(response);

                                        Picture pic = response.getResult();
                                        assertEquals(6498650, pic.getPhotoId());
                                    }

                                });
                    }

                });
    }

    @Test public void testPhotoAdd() {
        String jsonResponse = readDataFromFileAsString("DataResponses/PictureUnitTests-GetAlbum.json");
        String jsonAddResponse = readDataFromFileAsString("DataResponses/PictureUnitTests-AddPhoto.json");
        String jsonGetResponse = readDataFromFileAsString("DataResponses/PictureUnitTests-GetPhoto.json");

        BuddyHttpClientFactory.addDummyResponse(jsonResponse);
        BuddyHttpClientFactory.addDummyResponse(jsonAddResponse);
        BuddyHttpClientFactory.addDummyResponse(jsonGetResponse);

        testAuthUser.getPhotoAlbums().get(testAlbumId, null,
                new OnCallback<Response<PhotoAlbum>>() {

                    @Override
                    public void OnResponse(Response<PhotoAlbum> response, Object state) {
                        assertNotNull(response);

                        PhotoAlbum album = response.getResult();

                        testImageBytes = readDataFromFileAsStringAsBytes("res/drawable-hdpi/ic_launcher.png");

                        // Need to create our Picture class
                        album.addPicture(testImageBytes, testPhotoComment, testLatitude,
                                testLongitude, null, new OnCallback<Response<Picture>>() {

                                    @Override
                                    public void OnResponse(Response<Picture> response, Object state) {
                                        assertNotNull(response);

                                        Picture pic = response.getResult();
                                        assertEquals(6498650, pic.getPhotoId());
                                    }

                                });
                    }

                });
    }

    @Test public void testPhotoAddIncorrectEscape() {
        String jsonResponse = readDataFromFileAsString("DataResponses/PictureUnitTests-GetAlbum.json");
        String jsonAddResponse = readDataFromFileAsString("DataResponses/GenericHTTP400Response.json");

        BuddyHttpClientFactory.addDummyResponse(jsonResponse);
        BuddyHttpClientFactory.addDummyResponse(jsonAddResponse);

        testAuthUser.getPhotoAlbums().get(testAlbumId, null,
                new OnCallback<Response<PhotoAlbum>>() {

                    @Override
                    public void OnResponse(Response<PhotoAlbum> response, Object state) {
                        assertNotNull(response);

                        PhotoAlbum album = response.getResult();

                        testImageBytes = readDataFromFileAsStringAsBytes("res/drawable-hdpi/ic_launcher.png");

                        // Need to create our Picture class
                        album.addPicture(testImageBytes, testPhotoComment, testLatitude,
                                testLongitude, null, new OnCallback<Response<Picture>>() {

                                    @Override
                                    public void OnResponse(Response<Picture> response, Object state) {
                                        assertNotNull(response);
                                        String errorMessage = response.getThrowable().getMessage();
                                        assertEquals("HTTP 400 Response", errorMessage);
                                    }

                                });
                    }

                });
    }

    @Test public void testPhotoAddIncorrectPhotoAlbumId() {
        String jsonResponse = readDataFromFileAsString("DataResponses/PictureUnitTests-GetAlbum.json");
        String jsonAddResponse = readDataFromFileAsString("DataResponses/PictureUnitTests-BadAlbumId.json");

        BuddyHttpClientFactory.addDummyResponse(jsonResponse);
        BuddyHttpClientFactory.addDummyResponse(jsonAddResponse);

        testAuthUser.getPhotoAlbums().get(testAlbumId, null,
                new OnCallback<Response<PhotoAlbum>>() {

                    @Override
                    public void OnResponse(Response<PhotoAlbum> response, Object state) {
                        assertNotNull(response);

                        PhotoAlbum album = response.getResult();

                        testImageBytes = readDataFromFileAsStringAsBytes("res/drawable-hdpi/ic_launcher.png");

                        // Need to create our Picture class
                        album.addPicture(testImageBytes, testPhotoComment, testLatitude,
                                testLongitude, null, new OnCallback<Response<Picture>>() {

                                    @Override
                                    public void OnResponse(Response<Picture> response, Object state) {
                                        assertNotNull(response);
                                        String errorMessage = response.getThrowable().getMessage();
                                        assertEquals("PhotoAlbumDoesNotExist", errorMessage);
                                    }

                                });
                    }

                });
    }

    @Test public void testPhotoSetAppTag() {
        String jsonPhotoList = readDataFromFileAsString("DataResponses/PictureUnitTests-GetAllPhotos.json");
        String jsonResponse = readDataFromFileAsString("DataResponses/GenericSuccessResponse.json");

        BuddyHttpClientFactory.addDummyResponse(jsonPhotoList);
        BuddyHttpClientFactory.addDummyResponse(jsonResponse);

        testAuthUser.getPhotoAlbums().getAll(null, null,
                new OnCallback<Response<Collection<PhotoAlbum>>>() {
                    public void OnResponse(Response<Collection<PhotoAlbum>> response, Object state) {
                        assertNotNull(response);

                        Collection<PhotoAlbum> list = response.getResult();
                        assertNotNull(list);

                        Iterator<PhotoAlbum> iterator = list.iterator();
                        while (iterator.hasNext()) {
                            PhotoAlbum album = iterator.next();
                            List<Picture> pictures = album.getPictures();
                            assertNotNull(pictures);

                            Picture pic = pictures.get(0);
                            pic.setAppTag(testPhotoIdTag, null,
                                    new OnCallback<Response<Boolean>>() {
                                        public void OnResponse(Response<Boolean> response,
                                                Object state) {
                                            assertNotNull(response);
                                            assertTrue(response.getResult());
                                        }

                                    });
                            break;
                        }
                    }
                });
    }

    @Test public void testPhotoAlbumGetAllPictures() {
        String jsonPhotoList = readDataFromFileAsString("DataResponses/PictureUnitTests-GetAllPhotos.json");

        BuddyHttpClientFactory.addDummyResponse(jsonPhotoList);

        testAuthUser.getPhotoAlbums().getAll(null, null,
                new OnCallback<Response<Collection<PhotoAlbum>>>() {
                    public void OnResponse(Response<Collection<PhotoAlbum>> response, Object state) {
                        assertNotNull(response);

                        Collection<PhotoAlbum> list = response.getResult();
                        assertNotNull(list);

                        Iterator<PhotoAlbum> iterator = list.iterator();
                        while (iterator.hasNext()) {
                            PhotoAlbum album = iterator.next();
                            List<Picture> pictures = album.getPictures();
                            assertNotNull(pictures);

                            Picture pic = pictures.get(0);
                            assertEquals("Test Photo", pic.getComment());
                            break;
                        }
                    }
                });
    }

    @Test public void testPhotoAlbumGetAllPicturesNoAlbums() {
        String jsonPhotoList = readDataFromFileAsString("DataResponses/PictureUnitTests-BadAlbumId.json");

        BuddyHttpClientFactory.addDummyResponse(jsonPhotoList);

        testAuthUser.getPhotoAlbums().getAll(null, null,
                new OnCallback<Response<Collection<PhotoAlbum>>>() {
                    public void OnResponse(Response<Collection<PhotoAlbum>> response, Object state) {
                        assertNotNull(response);

                        assertNotNull(response);
                        String errorMessage = response.getThrowable().getMessage();
                        assertEquals("PhotoAlbumDoesNotExist", errorMessage);
                    }
                });
    }

    @Test public void testPhotoAlbumGetAllPicturesNoResults() {
        String jsonPhotoList = readDataFromFileAsString("DataResponses/GenericEmptyResponse.json");

        BuddyHttpClientFactory.addDummyResponse(jsonPhotoList);

        testAuthUser.getPhotoAlbums().getAll(null, null,
                new OnCallback<Response<Collection<PhotoAlbum>>>() {
                    public void OnResponse(Response<Collection<PhotoAlbum>> response, Object state) {
                        assertNotNull(response);

                        Collection<PhotoAlbum> list = response.getResult();
                        assertNotNull(list);
                        assertTrue(list.size() == 0);
                    }
                });
    }

    @Test public void testAlbumDelete() {
        String jsonResponse = readDataFromFileAsString("DataResponses/PictureUnitTests-GetAlbum.json");
        String jsonDeleteResponse = readDataFromFileAsString("DataResponses/GenericSuccessResponse.json");
        BuddyHttpClientFactory.addDummyResponse(jsonResponse);
        BuddyHttpClientFactory.addDummyResponse(jsonDeleteResponse);

        testAuthUser.getPhotoAlbums().get(testAlbumId, null,
                new OnCallback<Response<PhotoAlbum>>() {

                    @Override
                    public void OnResponse(Response<PhotoAlbum> response, Object state) {
                        assertNotNull(response);

                        PhotoAlbum album = response.getResult();
                        album.delete(null, new OnCallback<Response<Boolean>>() {
                            public void OnResponse(Response<Boolean> response, Object state) {
                                assertNotNull(response);
                                assertTrue(response.getResult());
                            }

                        });
                    }

                });
    }

    @Test public void testSearchForAlbums() {
        String jsonPhotoList = readDataFromFileAsString("DataResponses/PictureUnitTests-SearchAlbum.json");

        BuddyHttpClientFactory.addDummyResponse(jsonPhotoList);

        testAuthUser.searchForAlbums(testSearchDistance, testSearchLatitude, testSearchLongitude,
                testMaxSearchRecords, null,
                new OnCallback<Response<Collection<PhotoAlbumPublic>>>() {
                    public void OnResponse(Response<Collection<PhotoAlbumPublic>> response,
                            Object state) {
                        assertNotNull(response);

                        Collection<PhotoAlbumPublic> list = response.getResult();
                        assertNotNull(list);

                        Iterator<PhotoAlbumPublic> iterator = list.iterator();
                        while (iterator.hasNext()) {
                            PhotoAlbumPublic album = iterator.next();
                            List<PicturePublic> pictures = album.getPublicPictures();
                            assertNotNull(pictures);

                            PicturePublic pic = pictures.get(0);
                            assertEquals(6078641, pic.getPhotoId());
                            break;
                        }
                    }
                });
    }

    @Test public void testPicturesGetFilterList() {
        String jsonPhotoList = readDataFromFileAsString("DataResponses/PictureUnitTests-GetAllPhotos.json");
        String jsonResponse = readDataFromFileAsString("DataResponses/PictureUnitTests-GetFilters.json");

        BuddyHttpClientFactory.addDummyResponse(jsonPhotoList);
        BuddyHttpClientFactory.addDummyResponse(jsonResponse);

        testAuthUser.getPhotoAlbums().getAll(Constants.MinDate, null,
                new OnCallback<Response<Collection<PhotoAlbum>>>() {
                    public void OnResponse(Response<Collection<PhotoAlbum>> response, Object state) {
                        assertNotNull(response);

                        Collection<PhotoAlbum> list = response.getResult();
                        assertNotNull(list);

                        Iterator<PhotoAlbum> iterator = list.iterator();
                        while (iterator.hasNext()) {
                            PhotoAlbum album = iterator.next();
                            List<Picture> pictures = album.getPictures();
                            assertNotNull(pictures);

                            Picture pic = pictures.get(0);
                            pic.supportedFilters(null,
                                    new OnCallback<Response<Map<String, String>>>() {

                                        public void OnResponse(
                                                Response<Map<String, String>> response, Object state) {
                                            Map<String, String> map = response.getResult();
                                            assertNotNull(map);

                                            String value = map.get("Meme Generator");
                                            assertTrue(value.contains("Text Top"));
                                        }
                                    });
                            break;
                        }
                    }
                });
    }

    @Test public void testPicturesApplyFilter() {
        String jsonPhotoList = readDataFromFileAsString("DataResponses/PictureUnitTests-GetAllPhotos.json");
        String jsonResponse = readDataFromFileAsString("DataResponses/PictureUnitTests-ApplyFilter.json");
        String jsonGetResponse = readDataFromFileAsString("DataResponses/PictureUnitTests-GetPhoto.json");

        BuddyHttpClientFactory.addDummyResponse(jsonPhotoList);
        BuddyHttpClientFactory.addDummyResponse(jsonResponse);
        BuddyHttpClientFactory.addDummyResponse(jsonGetResponse);

        testAuthUser.getPhotoAlbums().getAll(Constants.MinDate, null,
                new OnCallback<Response<Collection<PhotoAlbum>>>() {
                    public void OnResponse(Response<Collection<PhotoAlbum>> response, Object state) {
                        assertNotNull(response);

                        Collection<PhotoAlbum> list = response.getResult();
                        assertNotNull(list);

                        Iterator<PhotoAlbum> iterator = list.iterator();
                        while (iterator.hasNext()) {
                            PhotoAlbum album = iterator.next();
                            List<Picture> pictures = album.getPictures();
                            assertNotNull(pictures);

                            Picture pic = pictures.get(0);
                            pic.applyFilter("Basic Operations", "Crop Left=20;Crop Right=30", null,
                                    new OnCallback<Response<Picture>>() {

                                        public void OnResponse(Response<Picture> response,
                                                Object state) {
                                            Picture picture = response.getResult();
                                            assertEquals(6498650, picture.getPhotoId());
                                        }
                                    });
                            break;
                        }
                    }
                });
    }
    
    @Test public void testPhotoDelete() {
        String jsonPhotoList = readDataFromFileAsString("DataResponses/PictureUnitTests-GetAllPhotos.json");
        String jsonResponse = readDataFromFileAsString("DataResponses/GenericSuccessResponse.json");

        BuddyHttpClientFactory.addDummyResponse(jsonPhotoList);
        BuddyHttpClientFactory.addDummyResponse(jsonResponse);

        testAuthUser.getPhotoAlbums().getAll(null, null,
                new OnCallback<Response<Collection<PhotoAlbum>>>() {
                    public void OnResponse(Response<Collection<PhotoAlbum>> response, Object state) {
                        assertNotNull(response);

                        Collection<PhotoAlbum> list = response.getResult();
                        assertNotNull(list);

                        Iterator<PhotoAlbum> iterator = list.iterator();
                        while (iterator.hasNext()) {
                            PhotoAlbum album = iterator.next();
                            List<Picture> pictures = album.getPictures();
                            assertNotNull(pictures);

                            Picture pic = pictures.get(0);
                            pic.delete(null, new OnCallback<Response<Boolean>>() {
                                public void OnResponse(Response<Boolean> response, Object state) {
                                    assertNotNull(response);
                                    assertTrue(response.getResult());
                                }

                            });
                            break;
                        }
                    }
                });
    }
}
