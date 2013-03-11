
package com.buddy.sdk.unittests.scenariotest;

import android.graphics.drawable.Drawable;

import com.buddy.sdk.AuthenticatedUser;
import com.buddy.sdk.BuddyClient;
import com.buddy.sdk.PhotoAlbum;
import com.buddy.sdk.Picture;
import com.buddy.sdk.responses.Response;
import com.buddy.sdk.unittests.BaseUnitTest;
import com.buddy.sdk.unittests.R;
import com.buddy.sdk.unittests.utils.Utils;
import com.buddy.sdk.web.BuddyHttpClientFactory;

public class OtherTest extends BaseUnitTest {
    BuddyClient client = null;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        BuddyHttpClientFactory.setUnitTestMode(false);
        client = new BuddyClient(applicationName, applicationPassword, this.getInstrumentation().getContext());
    }

    public void testAlbumsWithWaterMark() {
        Response<AuthenticatedUser> response = null;
        Response<Picture> picResponse = null;
        Response<PhotoAlbum> albumResponse = null;
        AuthenticatedUser user = null;

        byte[] imageData = null;

        response = ScenarioWrapper.Login_Wrapper(client, testUserName, testUserPassword, null);
        assertNotNull(response);
        user = response.getResult();
        assertNotNull(user);
        assertEquals(testUserName, user.getName());

        PhotoAlbum album = null;
        albumResponse = ScenarioWrapper.Create_Wrapper(user.getPhotoAlbums(), "My test album 2",
                null);
        assertNotNull(albumResponse);
        album = albumResponse.getResult();
        assertNotNull(album);

        Drawable image = getInstrumentation().getTargetContext().getResources()
                .getDrawable(R.drawable.ic_launcher);
        imageData = Utils.getEncodedImageBytes(image);

        Picture pic = null;
        picResponse = ScenarioWrapper.AddPictureWithWatermark_Wrapper(album, imageData,
                "my cool picture", 0.0, 0.0, "", "Test Watermark", null);
        assertNotNull(picResponse);
        pic = picResponse.getResult();
        assertNotNull(pic);
    }
}
