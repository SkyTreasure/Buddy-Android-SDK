
package com.buddy.sdk.unittests.scenariotest;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.buddy.sdk.AuthenticatedUser;
import com.buddy.sdk.BuddyClient;
import com.buddy.sdk.PhotoAlbum;
import com.buddy.sdk.Picture;
import com.buddy.sdk.PicturePublic;
import com.buddy.sdk.VirtualAlbum;
import com.buddy.sdk.responses.ListResponse;
import com.buddy.sdk.responses.Response;
import com.buddy.sdk.unittests.BaseUnitTest;
import com.buddy.sdk.utils.Constants;
import com.buddy.sdk.web.BuddyHttpClientFactory;

public class VirtualTest extends BaseUnitTest {
    BuddyClient client = null;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        BuddyHttpClientFactory.setUnitTestMode(false);
        client = new BuddyClient(applicationName, applicationPassword, this.getInstrumentation().getContext());
    }

    public void testAlbum() {
        Response<AuthenticatedUser> response = null;
        Response<Boolean> boolResponse = null;
        Response<VirtualAlbum> albumResponse = null;
        ListResponse<Integer> integerListResponse = null;
        Response<Collection<PhotoAlbum>> photoAlbumListResponse;

        AuthenticatedUser user = null;

        response = ScenarioWrapper.Login_Wrapper(client, testUserName, testUserPassword, null);
        assertNotNull(response);
        user = response.getResult();
        assertNotNull(user);
        assertEquals(testUserName, user.getName());

        List<Integer> albumIds = null;
        integerListResponse = ScenarioWrapper.GetMy_Wrapper(user.getVirtualAlbums(), null);
        assertNotNull(integerListResponse);
        albumIds = integerListResponse.getList();
        assertNotNull(albumIds);

        for (Integer a : albumIds) {
            VirtualAlbum album = null;
            albumResponse = ScenarioWrapper.Get_Wrapper(user.getVirtualAlbums(), a, null);
            assertNotNull(albumResponse);
            album = albumResponse.getResult();
            assertNotNull(album);

            boolResponse = ScenarioWrapper.Delete_Wrapper(album, null);
            assertNotNull(boolResponse);
            assertTrue(boolResponse.getResult());
        }

        VirtualAlbum newAlbum = null;
        albumResponse = ScenarioWrapper.Create_Wrapper(user.getVirtualAlbums(), "My Virtual Album",
                null);
        assertNotNull(albumResponse);
        newAlbum = albumResponse.getResult();
        assertNotNull(newAlbum);

        Picture testPicture = null;
        photoAlbumListResponse = ScenarioWrapper.GetAll_Wrapper(user.getPhotoAlbums(),
                Constants.MinDate, null);
        assertNotNull(photoAlbumListResponse);
        Collection<PhotoAlbum> albumList = photoAlbumListResponse.getResult();
        assertNotNull(albumList);
        Iterator<PhotoAlbum> iterator = albumList.iterator();
        while (iterator.hasNext()) {
            PhotoAlbum album = iterator.next();
            List<Picture> pictures = album.getPictures();
            assertNotNull(pictures);

            testPicture = pictures.get(0);
            break;
        }
        assertNotNull(testPicture);

        PicturePublic newAlbumPic = null;
        boolResponse = ScenarioWrapper.AddPicture_Wrapper(newAlbum, testPicture, null);
        assertNotNull(boolResponse);
        assertTrue(boolResponse.getResult());

        albumResponse = ScenarioWrapper.Get_Wrapper(user.getVirtualAlbums(), newAlbum.getId(),
                null);
        assertNotNull(albumResponse);
        VirtualAlbum updatedAlbum = albumResponse.getResult();
        assertNotNull(updatedAlbum);

        newAlbumPic = updatedAlbum.getPictures().get(0);

        boolResponse = ScenarioWrapper.Update_Wrapper(newAlbum, "new Name", "new tag", null);
        assertNotNull(boolResponse);
        assertTrue(boolResponse.getResult());

        boolResponse = ScenarioWrapper.UpdatePicture_Wrapper(newAlbum, newAlbumPic, "New Comment",
                "new apptag", null);
        assertNotNull(boolResponse);
        assertTrue(boolResponse.getResult());

        boolResponse = ScenarioWrapper.RemovePicture_Wrapper(newAlbum, newAlbumPic, null);
        assertNotNull(boolResponse);
        assertTrue(boolResponse.getResult());

        boolResponse = ScenarioWrapper.Delete_Wrapper(newAlbum, null);
        assertNotNull(boolResponse);
        assertTrue(boolResponse.getResult());
    }
}
