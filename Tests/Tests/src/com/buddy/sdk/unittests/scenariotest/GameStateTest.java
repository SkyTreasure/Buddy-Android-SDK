
package com.buddy.sdk.unittests.scenariotest;

import java.util.Date;
import java.util.Map;

import com.buddy.sdk.AuthenticatedUser;
import com.buddy.sdk.BuddyClient;
import com.buddy.sdk.GameState;
import com.buddy.sdk.responses.Response;
import com.buddy.sdk.unittests.BaseUnitTest;
import com.buddy.sdk.web.BuddyHttpClientFactory;

public class GameStateTest extends BaseUnitTest {
    BuddyClient client = null;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        BuddyHttpClientFactory.setUnitTestMode(false);
        client = new BuddyClient(applicationName, applicationPassword, this.getInstrumentation().getContext());
    }

    public void testAddGetRemoveGameState() {
        Response<AuthenticatedUser> response = null;
        Response<Boolean> boolResponse = null;
        Response<GameState> stateResponse = null;
        AuthenticatedUser user = null;

        response = ScenarioWrapper.Login_Wrapper(client, testUserName, testUserPassword, null);
        assertNotNull(response);
        user = response.getResult();
        assertNotNull(user);
        assertEquals(testUserName, user.getName());

        Date date = new Date();
        String dateTicks = String.valueOf(date.getTime());

        boolResponse = ScenarioWrapper
                .Add_Wrapper(user.getGameStates(), "foo", dateTicks, "", null);
        assertNotNull(boolResponse);
        assertTrue(boolResponse.getResult());

        stateResponse = ScenarioWrapper.Get_Wrapper(user.getGameStates(), "foo", null);
        assertNotNull(stateResponse);
        GameState state = stateResponse.getResult();
        assertNotNull(state);

        assertEquals(state.getValue(), dateTicks);

        boolResponse = ScenarioWrapper.Remove_Wrapper(user.getGameStates(), "foo", null);
        assertNotNull(boolResponse);
        assertTrue(boolResponse.getResult());

        stateResponse = ScenarioWrapper.Get_Wrapper(user.getGameStates(), "foo", null);
        assertNotNull(stateResponse);
        assertNull(stateResponse.getResult());
    }

    public void testGetAllGameState() {
        Response<AuthenticatedUser> response = null;
        Response<Boolean> boolResponse = null;
        Response<Map<String, GameState>> gameStatesResponse = null;
        AuthenticatedUser user = null;

        response = ScenarioWrapper.Login_Wrapper(client, testUserName, testUserPassword, null);
        assertNotNull(response);
        user = response.getResult();
        assertNotNull(user);
        assertEquals(testUserName, user.getName());

        Date date = new Date();
        String dateTicks = String.valueOf(date.getTime());

        boolResponse = ScenarioWrapper.Add_Wrapper(user.getGameStates(), "foo1", dateTicks, "",
                null);
        assertNotNull(boolResponse);
        assertTrue(boolResponse.getResult());

        boolResponse = ScenarioWrapper.Add_Wrapper(user.getGameStates(), "foo2", dateTicks
                + dateTicks, "", null);
        assertNotNull(boolResponse);
        assertTrue(boolResponse.getResult());

        gameStatesResponse = ScenarioWrapper.GetAll_Wrapper(user.getGameStates(), null);
        assertNotNull(gameStatesResponse);
        Map<String, GameState> map = gameStatesResponse.getResult();

        assertTrue(map.size() >= 2);
        assertEquals(dateTicks, map.get("foo1").getValue());

        boolResponse = ScenarioWrapper.Remove_Wrapper(user.getGameStates(), "foo1", null);
        assertNotNull(boolResponse);
        assertTrue(boolResponse.getResult());
        boolResponse = ScenarioWrapper.Remove_Wrapper(user.getGameStates(), "foo2", null);
        assertNotNull(boolResponse);
        assertTrue(boolResponse.getResult());
    }
}
