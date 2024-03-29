
package com.buddy.sdk.unittests;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.buddy.sdk.Callbacks.OnCallback;
import com.buddy.sdk.GamePlayer;
import com.buddy.sdk.GameScore;
import com.buddy.sdk.GameState;
import com.buddy.sdk.responses.Response;

import com.buddy.sdk.responses.ListResponse;
import com.buddy.sdk.web.BuddyHttpClientFactory;

public class GameUnitTests extends BaseUnitTest {

    @Override
	public void setUp() throws Exception {
        super.setUp();

        createAuthenticatedUser();
    }

    @Test public void testGameScoresAdd() {
        String jsonResponse = readDataFromFileAsString("DataResponses/GenericSuccessResponse.json");
        BuddyHttpClientFactory.addDummyResponse(jsonResponse);

        testAuthUser.getGameScores().add(testScoreValue, testScoreBoardName, testScoreRank,
                testGameLatitude, testGameLongitude, true, "", null,
                new OnCallback<Response<Boolean>>() {
                    public void OnResponse(Response<Boolean> response, Object state) {
                        assertNotNull(response);
                        assertTrue(response.getResult());
                    }

                });
    }

    @Test public void testGameScoresGetScoresUser() {
        String jsonResponse = readDataFromFileAsString("DataResponses/GameUnitTests-UserScores.json");
        BuddyHttpClientFactory.addDummyResponse(jsonResponse);

        testAuthUser.getGameScores().getAll(testRecordLimit, null,
                new OnCallback<ListResponse<GameScore>>() {
                    public void OnResponse(ListResponse<GameScore> response, Object state) {
                        assertNotNull(response);
                        assertTrue(response.isCompleted());

                        List<GameScore> list = response.getList();
                        GameScore score = list.get(0);
                        assertEquals(testUserIdInteger.compareTo(score.getUserID()), 0);
                    }

                });
    }

    @Test public void testGameScoresGetBoardHighScores() {
        String jsonResponse = readDataFromFileAsString("DataResponses/GameUnitTests-BoardHighScores.json");
        BuddyHttpClientFactory.addDummyResponse(jsonResponse);

        testClient.getGameBoards().getHighScores(testScoreBoardName, testRecordLimit, null,
                new OnCallback<ListResponse<GameScore>>() {
                    public void OnResponse(ListResponse<GameScore> response, Object state) {
                        assertNotNull(response);
                        assertTrue(response.isCompleted());

                        List<GameScore> list = response.getList();
                        GameScore score = list.get(0);
                        assertEquals(testUserIdInteger.compareTo(score.getUserID()), 0);
                    }

                });
    }

    @Test public void testGameSearchScores() {
        String jsonResponse = readDataFromFileAsString("DataResponses/GameUnitTests-SearchScores.json");
        BuddyHttpClientFactory.addDummyResponse(jsonResponse);

        testClient.getGameBoards().findScores(testAuthUser, testGameSearchDistance,
                testGameLatitude, testGameLongitude, testRecordLimit, "", -1, -1, "", null,
                new OnCallback<ListResponse<GameScore>>() {
                    public void OnResponse(ListResponse<GameScore> response, Object state) {
                        assertNotNull(response);
                        assertTrue(response.isCompleted());

                        List<GameScore> list = response.getList();
                        GameScore score = list.get(0);
                        assertEquals(testUserIdInteger.compareTo(score.getUserID()), 0);
                    }

                });
    }

    @Test public void testGameScoresDeleteAllScores() {
        String jsonResponse = readDataFromFileAsString("DataResponses/GenericSuccessResponse.json");
        BuddyHttpClientFactory.addDummyResponse(jsonResponse);

        testAuthUser.getGameScores().deleteAll(null, new OnCallback<Response<Boolean>>() {
            public void OnResponse(Response<Boolean> response, Object state) {
                assertNotNull(response);
                assertTrue(response.getResult());
            }

        });
    }

    // Game states
    @Test public void testGameStateAdd() {
        String jsonResponse = readDataFromFileAsString("DataResponses/GenericSuccessResponse.json");
        BuddyHttpClientFactory.addDummyResponse(jsonResponse);

        testAuthUser.getGameStates().add(testStateKey, testStateValue, "", null,
                new OnCallback<Response<Boolean>>() {
                    public void OnResponse(Response<Boolean> response, Object state) {
                        assertNotNull(response);
                        assertTrue(response.getResult());
                    }

                });
    }

    @Test public void testGameStateRemove() {
        String jsonResponse = readDataFromFileAsString("DataResponses/GenericSuccessResponse.json");
        BuddyHttpClientFactory.addDummyResponse(jsonResponse);

        testAuthUser.getGameStates().remove(testStateKey, null,
                new OnCallback<Response<Boolean>>() {
                    public void OnResponse(Response<Boolean> response, Object state) {
                        assertNotNull(response);
                        assertTrue(response.getResult());
                    }

                });
    }

    @Test public void testGameStateGet() {
        String jsonResponse = readDataFromFileAsString("DataResponses/GameUnitTests-GetState.json");
        BuddyHttpClientFactory.addDummyResponse(jsonResponse);

        testAuthUser.getGameStates().get(testStateKey, null, new OnCallback<Response<GameState>>() {
            public void OnResponse(Response<GameState> response, Object state) {
                assertNotNull(response);
                assertTrue(response.isCompleted());

                GameState gameState = response.getResult();
                assertEquals("Test Key", gameState.getKey());
            }

        });
    }

    @Test public void testGameStateGetAll() {
        String jsonResponse = readDataFromFileAsString("DataResponses/GameUnitTests-GetState.json");
        BuddyHttpClientFactory.addDummyResponse(jsonResponse);

        testAuthUser.getGameStates().getAll(null,
                new OnCallback<Response<Map<String, GameState>>>() {
                    public void OnResponse(Response<Map<String, GameState>> response, Object state) {
                        assertNotNull(response);
                        assertTrue(response.isCompleted());

                        Map<String, GameState> list = response.getResult();
                        GameState gameState = list.get("Test Key");
                        assertEquals("Test State Value", gameState.getValue());
                    }

                });
    }

    @Test public void testGameStateUpdate() {
        String jsonResponse = readDataFromFileAsString("DataResponses/GenericSuccessResponse.json");
        BuddyHttpClientFactory.addDummyResponse(jsonResponse);

        testAuthUser.getGameStates().update(testStateKey, testStateValue, "", null,
                new OnCallback<Response<Boolean>>() {
                    public void OnResponse(Response<Boolean> response, Object state) {
                        assertNotNull(response);
                        assertTrue(response.getResult());
                    }

                });
    }

    // Game Players
    @Test public void testGamePlayerAdd() {
        String jsonResponse = readDataFromFileAsString("DataResponses/GenericSuccessResponse.json");
        BuddyHttpClientFactory.addDummyResponse(jsonResponse);

        testAuthUser.getGamePlayers().add(testPlayerName, testPlayerBoard, testPlayerRank,
                testGameLatitude, testGameLongitude, "", null, new OnCallback<Response<Boolean>>() {
                    public void OnResponse(Response<Boolean> response, Object state) {
                        assertNotNull(response);
                        assertTrue(response.getResult());
                    }

                });
    }

    @Test public void testGamePlayerUpdate() {
        String jsonResponse = readDataFromFileAsString("DataResponses/GenericSuccessResponse.json");
        BuddyHttpClientFactory.addDummyResponse(jsonResponse);

        testAuthUser.getGamePlayers().update(testPlayerName, testPlayerBoard, "", testGameLatitude,
                testGameLongitude, "", null, new OnCallback<Response<Boolean>>() {
                    public void OnResponse(Response<Boolean> response, Object state) {
                        assertNotNull(response);
                        assertTrue(response.getResult());
                    }

                });
    }

    @Test public void testGamePlayerDelete() {
        String jsonResponse = readDataFromFileAsString("DataResponses/GenericSuccessResponse.json");
        BuddyHttpClientFactory.addDummyResponse(jsonResponse);

        testAuthUser.getGamePlayers().delete(null, new OnCallback<Response<Boolean>>() {
            public void OnResponse(Response<Boolean> response, Object state) {
                assertNotNull(response);
                assertTrue(response.getResult());
            }

        });
    }

    @Test public void testGamePlayerGetInfo() {
        String jsonResponse = readDataFromFileAsString("DataResponses/GameUnitTests-PlayerInfo.json");
        BuddyHttpClientFactory.addDummyResponse(jsonResponse);

        testAuthUser.getGamePlayers().getInfo(null, new OnCallback<Response<GamePlayer>>() {
            public void OnResponse(Response<GamePlayer> response, Object state) {
                assertNotNull(response);
                assertTrue(response.isCompleted());

                GamePlayer player = response.getResult();
                assertEquals("Test Player Board", player.getBoardName());
                assertEquals("best", player.getRank());
            }

        });
    }

    @Test public void testGamePlayerSearch() {
        String jsonResponse = readDataFromFileAsString("DataResponses/GameUnitTests-SearchPlayers.json");
        BuddyHttpClientFactory.addDummyResponse(jsonResponse);

        testAuthUser.getGamePlayers().find(testGameSearchDistance, testGameLatitude,
                testGameLongitude, testRecordLimit, testPlayerBoard, -1, -1, "", "", null,
                new OnCallback<ListResponse<GamePlayer>>() {
                    public void OnResponse(ListResponse<GamePlayer> response, Object state) {
                        assertNotNull(response);
                        assertTrue(response.isCompleted());

                        List<GamePlayer> list = response.getList();
                        GamePlayer player = list.get(0);
                        assertEquals("Test Player", player.getName());
                        assertEquals("best", player.getRank());
                    }

                });
    }
}
