
package com.buddy.sdk.unittests.scenariotest;

import java.util.List;

import com.buddy.sdk.AuthenticatedUser;
import com.buddy.sdk.BuddyClient;
import com.buddy.sdk.GameScore;
import com.buddy.sdk.responses.ListResponse;
import com.buddy.sdk.responses.Response;
import com.buddy.sdk.unittests.BaseUnitTest;
import com.buddy.sdk.web.BuddyHttpClientFactory;

public class GamesTest extends BaseUnitTest {
    BuddyClient client = null;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        BuddyHttpClientFactory.setUnitTestMode(false);
        client = new BuddyClient(applicationName, applicationPassword, this.getInstrumentation().getContext());
    }

    public void testBoards() {
        Response<AuthenticatedUser> response = null;
        Response<Boolean> boolResponse = null;
        ListResponse<GameScore> gameScoreResponse = null;
        AuthenticatedUser user = null;
        AuthenticatedUser user2 = null;

        // log them in
        response = ScenarioWrapper.Login_Wrapper(client, testUserName, testUserPassword, null);
        assertNotNull(response);
        user = response.getResult();
        assertNotNull(user);
        assertEquals(testUserName, user.getName());

        gameScoreResponse = ScenarioWrapper.GetHighScores_Wrapper(client.getGameBoards(),
                "Test Board", 100, null);
        assertNotNull(gameScoreResponse);
        assertNotNull(gameScoreResponse.getList());

        // add some high scores
        boolResponse = ScenarioWrapper.Add_Wrapper(user.getGameScores(), 100, "Test Board",
                "Master", "MyTag", null);
        assertNotNull(boolResponse);
        assertTrue(boolResponse.getResult());

        // look for the one we added
        gameScoreResponse = ScenarioWrapper.GetAll_Wrapper(user.getGameScores(), 100, null);
        assertNotNull(gameScoreResponse);
        List<GameScore> gameScoreList = gameScoreResponse.getList();
        assertNotNull(gameScoreList);
        assertEquals("MyTag", gameScoreList.get(0).getAppTag());

        // find all scores
        gameScoreResponse = ScenarioWrapper.FindScores_Wrapper(client.getGameBoards(), user, null);
        assertNotNull(gameScoreResponse);
        assertNotNull(gameScoreResponse.getList());

        // TODO should these users be different?
        response = ScenarioWrapper.Login_Wrapper(client, testUserName, testUserPassword, null);
        assertNotNull(response);
        user2 = response.getResult();
        assertNotNull(testUserName);
        assertEquals(testUserName, user2.getName());

        // add some more scores
        boolResponse = ScenarioWrapper.Add_Wrapper(user2.getGameScores(), 10, "Test Board", "", "",
                null);
        assertNotNull(boolResponse);
        assertTrue(boolResponse.getResult());

        boolResponse = ScenarioWrapper.Add_Wrapper(user2.getGameScores(), 10, "Test Board", "", "",
                null);
        assertNotNull(boolResponse);
        assertTrue(boolResponse.getResult());

        boolResponse = ScenarioWrapper.Add_Wrapper(user2.getGameScores(), 15, "Test Board", "", "",
                null);
        assertNotNull(boolResponse);
        assertTrue(boolResponse.getResult());

        // make sure we see them
        gameScoreResponse = ScenarioWrapper.GetAll_Wrapper(user.getGameScores(), 100, null);
        assertNotNull(gameScoreResponse);
        assertNotNull(gameScoreResponse.getList());
        assertTrue(gameScoreResponse.getList().size() > 1);

        gameScoreResponse = ScenarioWrapper.FindScores_Wrapper(client.getGameBoards(), user2, null);
        assertNotNull(gameScoreResponse);
        assertNotNull(gameScoreResponse.getList());
        assertTrue(gameScoreResponse.getList().size() >= 3);
    }
}
