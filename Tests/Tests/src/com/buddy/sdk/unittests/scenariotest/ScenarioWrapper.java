
package com.buddy.sdk.unittests.scenariotest;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import android.util.Log;
import android.util.SparseArray;

import com.buddy.sdk.AuthenticatedUser;
import com.buddy.sdk.BuddyClient;
import com.buddy.sdk.Callbacks.OnCallback;
import com.buddy.sdk.FriendRequests;
import com.buddy.sdk.Friends;
import com.buddy.sdk.GameBoards;
import com.buddy.sdk.GameScore;
import com.buddy.sdk.GameScores;
import com.buddy.sdk.GameState;
import com.buddy.sdk.GameStates;
import com.buddy.sdk.Message;
import com.buddy.sdk.Messages;
import com.buddy.sdk.NotificationsAndroid;
import com.buddy.sdk.PhotoAlbum;
import com.buddy.sdk.PhotoAlbums;
import com.buddy.sdk.Picture;
import com.buddy.sdk.PicturePublic;
import com.buddy.sdk.Place;
import com.buddy.sdk.Places;
import com.buddy.sdk.RegisteredDeviceAndroid;
import com.buddy.sdk.User;
import com.buddy.sdk.VirtualAlbum;
import com.buddy.sdk.VirtualAlbums;
import com.buddy.sdk.responses.ListResponse;
import com.buddy.sdk.responses.Response;
import com.buddy.sdk.utils.Constants.UserGender;
import com.buddy.sdk.utils.Constants.UserStatus;

public class ScenarioWrapper {
    protected final static String TAG = "BuddySDK Scenario Tests";
    protected final static int SIGNAL_TIMEOUT = 60;

    private static Response<AuthenticatedUser> authResponse = null;
    private static Response<User> userResponse = null;
    private static ListResponse<User> userListResponse = null;
    private static Response<Boolean> boolResponse = null;
    private static Response<GameState> gameStateResponse = null;
    private static ListResponse<GameScore> gameScoreListResponse = null;
    private static Response<Map<String, GameState>> gameStateMapResponse = null;
    private static Response<Place> placeResponse = null;
    private static ListResponse<Place> placeListResponse = null;
    private static Response<SparseArray<String>> categoryResponse = null;
    private static ListResponse<Message> messageListResponse = null;
    private static ListResponse<RegisteredDeviceAndroid> deviceListResponse = null;
    private static Response<Map<String, Integer>> integerMapResponse = null;
    private static ListResponse<Integer> integerResponse = null;
    private static Response<Collection<PhotoAlbum>> albumListResponse = null;
    private static Response<VirtualAlbum> virtualAlbumResponse = null;
    private static Response<PhotoAlbum> albumResponse = null;
    private static Response<Picture> pictureResponse = null;

    public static Response<AuthenticatedUser> Login_Wrapper(final BuddyClient client,
            final String token, final String password, final Object state) {
        authResponse = null;
        final CountDownLatch signal = new CountDownLatch(1);

        Thread thread = new Thread(new Runnable() {
            public void run() {
                if (password == null) {
                    client.login(token, state, new OnCallback<Response<AuthenticatedUser>>() {
                        public void OnResponse(Response<AuthenticatedUser> response, Object state) {
                            if (response != null) {
                                authResponse = response;
                            }
                            signal.countDown();

                        }
                    });
                } else {
                    client.login(token, password, state,
                            new OnCallback<Response<AuthenticatedUser>>() {
                                public void OnResponse(Response<AuthenticatedUser> response,
                                        Object state) {
                                    if (response != null) {
                                        authResponse = response;
                                    }
                                    signal.countDown();

                                }
                            });
                }
            }
        });
        thread.start();

        try {
            signal.await(SIGNAL_TIMEOUT, TimeUnit.SECONDS);

        } catch (InterruptedException e1) {
            Log.d(TAG, "Exception - " + e1.getMessage());
        }

        return authResponse;
    }

    public static Response<AuthenticatedUser> CreateUser_Wrapper(final BuddyClient client,
            final String userName, final String userPassword, final UserGender userGender,
            final int userAge, final String userEmail, final UserStatus statusId,
            final Boolean fuzzLocation, final Boolean celebModeEnabled,
            final String applicationTag, final Object state) {
        authResponse = null;
        final CountDownLatch signal = new CountDownLatch(1);

        Thread thread = new Thread(new Runnable() {
            public void run() {
                try {
                    client.createUser(userName, userPassword, userGender, userAge, userEmail,
                            statusId, fuzzLocation, celebModeEnabled, applicationTag, state,
                            new OnCallback<Response<AuthenticatedUser>>() {
                                public void OnResponse(Response<AuthenticatedUser> response,
                                        Object state) {
                                    if (response != null) {
                                        authResponse = response;
                                    }
                                    signal.countDown();

                                }
                            });
                } catch (IllegalArgumentException ex) {
                    authResponse = new Response<AuthenticatedUser>();
                    authResponse.setThrowable(ex);
                    signal.countDown();
                }
            }
        });
        thread.start();

        try {
            signal.await(SIGNAL_TIMEOUT, TimeUnit.SECONDS);

        } catch (InterruptedException e1) {
            Log.d(TAG, "Exception - " + e1.getMessage());
        }

        return authResponse;
    }

    public static Response<AuthenticatedUser> CreateUser_Wrapper(final BuddyClient client,
            final String userName, final String userPassword) {
        return ScenarioWrapper.CreateUser_Wrapper(client, userName, userPassword, UserGender.any,
                0, "", UserStatus.Any, false, false, "", null);
    }

    public static Response<User> FindUser_Wrapper(final AuthenticatedUser user, final int id,
            final Object state) {
        userResponse = null;
        final CountDownLatch signal = new CountDownLatch(1);

        Thread thread = new Thread(new Runnable() {
            public void run() {
                user.findUser(id, state, new OnCallback<Response<User>>() {
                    public void OnResponse(Response<User> response, Object state) {
                        if (response != null) {
                            userResponse = response;
                        }
                        signal.countDown();

                    }
                });
            }
        });
        thread.start();

        try {
            signal.await(SIGNAL_TIMEOUT, TimeUnit.SECONDS);

        } catch (InterruptedException e1) {
            Log.d(TAG, "Exception - " + e1.getMessage());
        }

        return userResponse;
    }

    public static Response<Boolean> DeleteUser_Wrapper(final AuthenticatedUser user,
            final Object state) {
        boolResponse = null;
        final CountDownLatch signal = new CountDownLatch(1);

        Thread thread = new Thread(new Runnable() {
            public void run() {
                user.delete(state, new OnCallback<Response<Boolean>>() {
                    public void OnResponse(Response<Boolean> response, Object state) {
                        if (response != null) {
                            boolResponse = response;
                        }
                        signal.countDown();

                    }
                });
            }
        });
        thread.start();

        try {
            signal.await(SIGNAL_TIMEOUT, TimeUnit.SECONDS);

        } catch (InterruptedException e1) {
            Log.d(TAG, "Exception - " + e1.getMessage());
        }

        return boolResponse;
    }

    public static ListResponse<User> GetAll_Wrapper(final Friends friends, final Date afterDate,
            final Object state) {
        userListResponse = null;
        final CountDownLatch signal = new CountDownLatch(1);

        Thread thread = new Thread(new Runnable() {
            public void run() {
                friends.getAll(afterDate, state, new OnCallback<ListResponse<User>>() {
                    public void OnResponse(ListResponse<User> response, Object state) {
                        if (response != null) {
                            userListResponse = response;
                        }
                        signal.countDown();

                    }
                });
            }
        });
        thread.start();

        try {
            signal.await(SIGNAL_TIMEOUT, TimeUnit.SECONDS);

        } catch (InterruptedException e1) {
            Log.d(TAG, "Exception - " + e1.getMessage());
        }

        return userListResponse;
    }

    public static Response<Boolean> Remove_Wrapper(final Friends friends, final User user,
            final Object state) {
        boolResponse = null;
        final CountDownLatch signal = new CountDownLatch(1);

        Thread thread = new Thread(new Runnable() {
            public void run() {
                friends.remove(user, state, new OnCallback<Response<Boolean>>() {
                    public void OnResponse(Response<Boolean> response, Object state) {
                        if (response != null) {
                            boolResponse = response;
                        }
                        signal.countDown();

                    }
                });
            }
        });
        thread.start();

        try {
            signal.await(SIGNAL_TIMEOUT, TimeUnit.SECONDS);

        } catch (InterruptedException e1) {
            Log.d(TAG, "Exception - " + e1.getMessage());
        }

        return boolResponse;
    }

    public static ListResponse<User> GetAll_Wrapper(final FriendRequests friendRequest,
            final Date afterDate, final Object state) {
        userListResponse = null;
        final CountDownLatch signal = new CountDownLatch(1);

        Thread thread = new Thread(new Runnable() {
            public void run() {
                friendRequest.getAll(afterDate, state, new OnCallback<ListResponse<User>>() {
                    public void OnResponse(ListResponse<User> response, Object state) {
                        if (response != null) {
                            userListResponse = response;
                        }
                        signal.countDown();

                    }
                });
            }
        });
        thread.start();

        try {
            signal.await(SIGNAL_TIMEOUT, TimeUnit.SECONDS);

        } catch (InterruptedException e1) {
            Log.d(TAG, "Exception - " + e1.getMessage());
        }

        return userListResponse;
    }

    public static Response<Boolean> Deny_Wrapper(final FriendRequests friendRequests,
            final User user, final Object state) {
        boolResponse = null;
        final CountDownLatch signal = new CountDownLatch(1);

        Thread thread = new Thread(new Runnable() {
            public void run() {
                friendRequests.deny(user, state, new OnCallback<Response<Boolean>>() {
                    public void OnResponse(Response<Boolean> response, Object state) {
                        if (response != null) {
                            boolResponse = response;
                        }
                        signal.countDown();

                    }
                });
            }
        });
        thread.start();

        try {
            signal.await(SIGNAL_TIMEOUT, TimeUnit.SECONDS);

        } catch (InterruptedException e1) {
            Log.d(TAG, "Exception - " + e1.getMessage());
        }

        return boolResponse;
    }

    public static Response<Boolean> Add_Wrapper(final FriendRequests friendRequests,
            final User user, final Object state) {
        boolResponse = null;
        final CountDownLatch signal = new CountDownLatch(1);

        Thread thread = new Thread(new Runnable() {
            public void run() {
                friendRequests.add(user, new OnCallback<Response<Boolean>>() {
                    public void OnResponse(Response<Boolean> response, Object state) {
                        if (response != null) {
                            boolResponse = response;
                        }
                        signal.countDown();

                    }
                });
            }
        });
        thread.start();

        try {
            signal.await(SIGNAL_TIMEOUT, TimeUnit.SECONDS);

        } catch (InterruptedException e1) {
            Log.d(TAG, "Exception - " + e1.getMessage());
        }

        return boolResponse;
    }

    public static ListResponse<GameScore> GetHighScores_Wrapper(final GameBoards gameboards,
            final String boardName, final int recordLimit, Object state) {
        gameScoreListResponse = null;
        final CountDownLatch signal = new CountDownLatch(1);

        Thread thread = new Thread(new Runnable() {
            public void run() {
                gameboards.getHighScores(boardName, recordLimit, null,
                        new OnCallback<ListResponse<GameScore>>() {
                            public void OnResponse(ListResponse<GameScore> response, Object state) {
                                if (response != null) {
                                    gameScoreListResponse = response;
                                }
                                signal.countDown();

                            }
                        });
            }
        });
        thread.start();

        try {
            signal.await(SIGNAL_TIMEOUT, TimeUnit.SECONDS);

        } catch (InterruptedException e1) {
            Log.d(TAG, "Exception - " + e1.getMessage());
        }

        return gameScoreListResponse;
    }

    public static Response<Boolean> Add_Wrapper(final GameScores gameScores, final float score,
            final String board, final String rank, final String appTag, Object state) {
        boolResponse = null;
        final CountDownLatch signal = new CountDownLatch(1);

        Thread thread = new Thread(new Runnable() {
            public void run() {
                gameScores.add(score, board, rank, 0, 0, false, appTag, null,
                        new OnCallback<Response<Boolean>>() {
                            public void OnResponse(Response<Boolean> response, Object state) {
                                if (response != null) {
                                    boolResponse = response;
                                }
                                signal.countDown();

                            }
                        });
            }
        });
        thread.start();

        try {
            signal.await(SIGNAL_TIMEOUT, TimeUnit.SECONDS);

        } catch (InterruptedException e1) {
            Log.d(TAG, "Exception - " + e1.getMessage());
        }

        return boolResponse;
    }

    public static ListResponse<GameScore> GetAll_Wrapper(final GameScores gameScores,
            final int recordLimit, final Object state) {
        gameScoreListResponse = null;
        final CountDownLatch signal = new CountDownLatch(1);

        Thread thread = new Thread(new Runnable() {
            public void run() {
                gameScores.getAll(recordLimit, state, new OnCallback<ListResponse<GameScore>>() {
                    public void OnResponse(ListResponse<GameScore> response, Object state) {
                        if (response != null) {
                            gameScoreListResponse = response;
                        }
                        signal.countDown();

                    }
                });
            }
        });
        thread.start();

        try {
            signal.await(SIGNAL_TIMEOUT, TimeUnit.SECONDS);

        } catch (InterruptedException e1) {
            Log.d(TAG, "Exception - " + e1.getMessage());
        }

        return gameScoreListResponse;
    }

    public static ListResponse<GameScore> FindScores_Wrapper(final GameBoards gameboards,
            final User user, final Object state) {
        gameScoreListResponse = null;
        final CountDownLatch signal = new CountDownLatch(1);

        Thread thread = new Thread(new Runnable() {
            public void run() {
                gameboards.findScores(user, -1, 0, 0, 100, "", 999999, -1, "", state,
                        new OnCallback<ListResponse<GameScore>>() {
                            public void OnResponse(ListResponse<GameScore> response, Object state) {
                                if (response != null) {
                                    gameScoreListResponse = response;
                                }
                                signal.countDown();

                            }
                        });
            }
        });
        thread.start();

        try {
            signal.await(SIGNAL_TIMEOUT, TimeUnit.SECONDS);

        } catch (InterruptedException e1) {
            Log.d(TAG, "Exception - " + e1.getMessage());
        }

        return gameScoreListResponse;
    }

    public static Response<Boolean> Add_Wrapper(final GameStates states, final String key,
            final String value, final String appTag, final Object state) {
        boolResponse = null;
        final CountDownLatch signal = new CountDownLatch(1);

        Thread thread = new Thread(new Runnable() {
            public void run() {
                states.add(key, value, appTag, state, new OnCallback<Response<Boolean>>() {
                    public void OnResponse(Response<Boolean> response, Object state) {
                        if (response != null) {
                            boolResponse = response;
                        }
                        signal.countDown();

                    }
                });
            }
        });
        thread.start();

        try {
            signal.await(SIGNAL_TIMEOUT, TimeUnit.SECONDS);

        } catch (InterruptedException e1) {
            Log.d(TAG, "Exception - " + e1.getMessage());
        }

        return boolResponse;
    }

    public static Response<Boolean> Remove_Wrapper(final GameStates states, final String key,
            final Object state) {
        boolResponse = null;
        final CountDownLatch signal = new CountDownLatch(1);

        Thread thread = new Thread(new Runnable() {
            public void run() {
                states.remove(key, state, new OnCallback<Response<Boolean>>() {
                    public void OnResponse(Response<Boolean> response, Object state) {
                        if (response != null) {
                            boolResponse = response;
                        }
                        signal.countDown();

                    }
                });
            }
        });
        thread.start();

        try {
            signal.await(SIGNAL_TIMEOUT, TimeUnit.SECONDS);

        } catch (InterruptedException e1) {
            Log.d(TAG, "Exception - " + e1.getMessage());
        }

        return boolResponse;
    }

    public static Response<GameState> Get_Wrapper(final GameStates states, final String key,
            final Object state) {
        gameStateResponse = null;
        final CountDownLatch signal = new CountDownLatch(1);

        Thread thread = new Thread(new Runnable() {
            public void run() {
                states.get(key, state, new OnCallback<Response<GameState>>() {
                    public void OnResponse(Response<GameState> response, Object state) {
                        if (response != null) {
                            gameStateResponse = response;
                        }
                        signal.countDown();

                    }
                });
            }
        });
        thread.start();

        try {
            signal.await(SIGNAL_TIMEOUT, TimeUnit.SECONDS);

        } catch (InterruptedException e1) {
            Log.d(TAG, "Exception - " + e1.getMessage());
        }

        return gameStateResponse;
    }

    public static Response<Map<String, GameState>> GetAll_Wrapper(final GameStates states,
            final Object state) {
        gameStateMapResponse = null;
        final CountDownLatch signal = new CountDownLatch(1);

        Thread thread = new Thread(new Runnable() {
            public void run() {
                states.getAll(state, new OnCallback<Response<Map<String, GameState>>>() {
                    public void OnResponse(Response<Map<String, GameState>> response, Object state) {
                        if (response != null) {
                            gameStateMapResponse = response;
                        }
                        signal.countDown();

                    }
                });
            }
        });
        thread.start();

        try {
            signal.await(SIGNAL_TIMEOUT, TimeUnit.SECONDS);

        } catch (InterruptedException e1) {
            Log.d(TAG, "Exception - " + e1.getMessage());
        }

        return gameStateMapResponse;
    }

    public static ListResponse<Place> Find_Wrapper(final Places places,
            final int searchDistanceInMeters, final double latitude, final double longitude,
            final Integer categoryId, final Object state) {
        placeListResponse = null;
        final CountDownLatch signal = new CountDownLatch(1);

        Thread thread = new Thread(new Runnable() {
            public void run() {
                places.find(searchDistanceInMeters, latitude, longitude, 100, "", categoryId,
                        state, new OnCallback<ListResponse<Place>>() {
                            public void OnResponse(ListResponse<Place> response, Object state) {
                                if (response != null) {
                                    placeListResponse = response;
                                }
                                signal.countDown();

                            }
                        });
            }
        });
        thread.start();

        try {
            signal.await(SIGNAL_TIMEOUT, TimeUnit.SECONDS);

        } catch (InterruptedException e1) {
            Log.d(TAG, "Exception - " + e1.getMessage());
        }

        return placeListResponse;
    }

    public static Response<SparseArray<String>> GetCategories_Wrapper(final Places places,
            final Object state) {
        categoryResponse = null;
        final CountDownLatch signal = new CountDownLatch(1);

        Thread thread = new Thread(new Runnable() {
            public void run() {
                places.getCategories(state, new OnCallback<Response<SparseArray<String>>>() {
                    public void OnResponse(Response<SparseArray<String>> response, Object state) {
                        if (response != null) {
                            categoryResponse = response;
                        }
                        signal.countDown();

                    }
                });
            }
        });
        thread.start();

        try {
            signal.await(SIGNAL_TIMEOUT, TimeUnit.SECONDS);

        } catch (InterruptedException e1) {
            Log.d(TAG, "Exception - " + e1.getMessage());
        }

        return categoryResponse;
    }

    public static Response<Place> Get_Wrapper(final Places places, final int placeId,
            final double latitude, final double longitude, final Object state) {
        placeResponse = null;
        final CountDownLatch signal = new CountDownLatch(1);

        Thread thread = new Thread(new Runnable() {
            public void run() {
                places.get(placeId, latitude, longitude, state, new OnCallback<Response<Place>>() {
                    public void OnResponse(Response<Place> response, Object state) {
                        if (response != null) {
                            placeResponse = response;
                        }
                        signal.countDown();

                    }
                });
            }
        });
        thread.start();

        try {
            signal.await(SIGNAL_TIMEOUT, TimeUnit.SECONDS);

        } catch (InterruptedException e1) {
            Log.d(TAG, "Exception - " + e1.getMessage());
        }

        return placeResponse;

    }

    public static Response<Boolean> Send_Wrapper(final Messages messages, final User toUser,
            final String message, final Object state) {
        boolResponse = null;
        final CountDownLatch signal = new CountDownLatch(1);

        Thread thread = new Thread(new Runnable() {
            public void run() {
                messages.send(toUser, message, "", state, new OnCallback<Response<Boolean>>() {
                    public void OnResponse(Response<Boolean> response, Object state) {
                        if (response != null) {
                            boolResponse = response;
                        }
                        signal.countDown();

                    }
                });
            }
        });
        thread.start();

        try {
            signal.await(SIGNAL_TIMEOUT, TimeUnit.SECONDS);

        } catch (InterruptedException e1) {
            Log.d(TAG, "Exception - " + e1.getMessage());
        }

        return boolResponse;
    }

    public static ListResponse<Message> GetReceived_Wrapper(final Messages messages,
            final Date afterDate, final Object state) {
        messageListResponse = null;
        final CountDownLatch signal = new CountDownLatch(1);

        Thread thread = new Thread(new Runnable() {
            public void run() {
                messages.getReceived(afterDate, state, new OnCallback<ListResponse<Message>>() {
                    public void OnResponse(ListResponse<Message> response, Object state) {
                        if (response != null) {
                            messageListResponse = response;
                        }
                        signal.countDown();

                    }
                });
            }
        });
        thread.start();

        try {
            signal.await(SIGNAL_TIMEOUT, TimeUnit.SECONDS);

        } catch (InterruptedException e1) {
            Log.d(TAG, "Exception - " + e1.getMessage());
        }

        return messageListResponse;
    }

    public static Response<Boolean> RegisterDevice_Wrapper(
            final NotificationsAndroid notifications, final String registrationID,
            final String groupName, final Object state) {
        boolResponse = null;
        final CountDownLatch signal = new CountDownLatch(1);

        Thread thread = new Thread(new Runnable() {
            public void run() {
                notifications.registerDevice(registrationID, groupName, state,
                        new OnCallback<Response<Boolean>>() {
                            public void OnResponse(Response<Boolean> response, Object state) {
                                if (response != null) {
                                    boolResponse = response;
                                }
                                signal.countDown();

                            }
                        });
            }
        });
        thread.start();

        try {
            signal.await(SIGNAL_TIMEOUT, TimeUnit.SECONDS);

        } catch (InterruptedException e1) {
            Log.d(TAG, "Exception - " + e1.getMessage());
        }

        return boolResponse;
    }

    public static ListResponse<RegisteredDeviceAndroid> GetRegisteredDevices_Wrapper(
            final NotificationsAndroid notifications, final Object state) {
        deviceListResponse = null;
        final CountDownLatch signal = new CountDownLatch(1);

        Thread thread = new Thread(new Runnable() {
            public void run() {
                notifications.getRegisteredDevices("", 10, 1, state,
                        new OnCallback<ListResponse<RegisteredDeviceAndroid>>() {
                            public void OnResponse(ListResponse<RegisteredDeviceAndroid> response,
                                    Object state) {
                                if (response != null) {
                                    deviceListResponse = response;
                                }
                                signal.countDown();

                            }
                        });
            }
        });
        thread.start();

        try {
            signal.await(SIGNAL_TIMEOUT, TimeUnit.SECONDS);

        } catch (InterruptedException e1) {
            Log.d(TAG, "Exception - " + e1.getMessage());
        }

        return deviceListResponse;
    }

    public static Response<Map<String, Integer>> GetGroupsAsync_Wrapper(
            final NotificationsAndroid notifications, final Object state) {
        integerMapResponse = null;
        final CountDownLatch signal = new CountDownLatch(1);

        Thread thread = new Thread(new Runnable() {
            public void run() {
                notifications.getGroups(state, new OnCallback<Response<Map<String, Integer>>>() {
                    public void OnResponse(Response<Map<String, Integer>> response, Object state) {
                        if (response != null) {
                            integerMapResponse = response;
                        }
                        signal.countDown();

                    }
                });
            }
        });
        thread.start();

        try {
            signal.await(SIGNAL_TIMEOUT, TimeUnit.SECONDS);

        } catch (InterruptedException e1) {
            Log.d(TAG, "Exception - " + e1.getMessage());
        }

        return integerMapResponse;
    }

    public static Response<Boolean> UnregisterDevice_Wrapper(
            final NotificationsAndroid notifications, final Object state) {
        boolResponse = null;
        final CountDownLatch signal = new CountDownLatch(1);

        Thread thread = new Thread(new Runnable() {
            public void run() {
                notifications.unregisterDevice(state, new OnCallback<Response<Boolean>>() {
                    public void OnResponse(Response<Boolean> response, Object state) {
                        if (response != null) {
                            boolResponse = response;
                        }
                        signal.countDown();

                    }
                });
            }
        });
        thread.start();

        try {
            signal.await(SIGNAL_TIMEOUT, TimeUnit.SECONDS);

        } catch (InterruptedException e1) {
            Log.d(TAG, "Exception - " + e1.getMessage());
        }

        return boolResponse;
    }

    public static Response<Boolean> SendRawMessage_Wrapper(
            final NotificationsAndroid notifications, final String rawMessage, final Integer senderUserId) {
        boolResponse = null;
        final CountDownLatch signal = new CountDownLatch(1);

        Thread thread = new Thread(new Runnable() {
            public void run() {
                notifications.sendRawMessage(rawMessage, senderUserId, new OnCallback<Response<Boolean>>() {
                    public void OnResponse(Response<Boolean> response, Object state) {
                        if (response != null) {
                            boolResponse = response;
                        }
                        signal.countDown();

                    }
                });
            }
        });
        thread.start();

        try {
            signal.await(SIGNAL_TIMEOUT, TimeUnit.SECONDS);

        } catch (InterruptedException e1) {
            Log.d(TAG, "Exception - " + e1.getMessage());
        }

        return boolResponse;
    }

    public static ListResponse<Integer> GetMy_Wrapper(final VirtualAlbums album, final Object state) {
        integerResponse = null;
        final CountDownLatch signal = new CountDownLatch(1);

        Thread thread = new Thread(new Runnable() {
            public void run() {
                album.getMy(state, new OnCallback<ListResponse<Integer>>() {
                    public void OnResponse(ListResponse<Integer> response, Object state) {
                        if (response != null) {
                            integerResponse = response;
                        }
                        signal.countDown();

                    }
                });
            }
        });
        thread.start();

        try {
            signal.await(SIGNAL_TIMEOUT, TimeUnit.SECONDS);

        } catch (InterruptedException e1) {
            Log.d(TAG, "Exception - " + e1.getMessage());
        }

        return integerResponse;
    }

    public static Response<Boolean> Delete_Wrapper(final VirtualAlbum album, final Object state) {
        boolResponse = null;
        final CountDownLatch signal = new CountDownLatch(1);

        Thread thread = new Thread(new Runnable() {
            public void run() {
                album.delete(state, new OnCallback<Response<Boolean>>() {
                    public void OnResponse(Response<Boolean> response, Object state) {
                        if (response != null) {
                            boolResponse = response;
                        }
                        signal.countDown();

                    }
                });
            }
        });
        thread.start();

        try {
            signal.await(SIGNAL_TIMEOUT, TimeUnit.SECONDS);

        } catch (InterruptedException e1) {
            Log.d(TAG, "Exception - " + e1.getMessage());
        }

        return boolResponse;
    }

    public static Response<VirtualAlbum> Get_Wrapper(final VirtualAlbums albums, final int id,
            final Object state) {
        virtualAlbumResponse = null;
        final CountDownLatch signal = new CountDownLatch(1);

        Thread thread = new Thread(new Runnable() {
            public void run() {
                albums.get(id, state, new OnCallback<Response<VirtualAlbum>>() {
                    public void OnResponse(Response<VirtualAlbum> response, Object state) {
                        if (response != null) {
                            virtualAlbumResponse = response;
                        }
                        signal.countDown();

                    }
                });
            }
        });
        thread.start();

        try {
            signal.await(SIGNAL_TIMEOUT, TimeUnit.SECONDS);

        } catch (InterruptedException e1) {
            Log.d(TAG, "Exception - " + e1.getMessage());
        }

        return virtualAlbumResponse;
    }

    public static Response<VirtualAlbum> Create_Wrapper(final VirtualAlbums album,
            final String name, final Object state) {
        virtualAlbumResponse = null;
        final CountDownLatch signal = new CountDownLatch(1);

        Thread thread = new Thread(new Runnable() {
            public void run() {
                album.create(name, "", state, new OnCallback<Response<VirtualAlbum>>() {
                    public void OnResponse(Response<VirtualAlbum> response, Object state) {
                        if (response != null) {
                            virtualAlbumResponse = response;
                        }
                        signal.countDown();

                    }
                });
            }
        });
        thread.start();

        try {
            signal.await(SIGNAL_TIMEOUT, TimeUnit.SECONDS);

        } catch (InterruptedException e1) {
            Log.d(TAG, "Exception - " + e1.getMessage());
        }

        return virtualAlbumResponse;
    }

    public static Response<Collection<PhotoAlbum>> GetAll_Wrapper(final PhotoAlbums albums,
            final Date afterDate, final Object state) {
        albumListResponse = null;
        final CountDownLatch signal = new CountDownLatch(1);

        Thread thread = new Thread(new Runnable() {
            public void run() {
                albums.getAll(afterDate, state, new OnCallback<Response<Collection<PhotoAlbum>>>() {
                    public void OnResponse(Response<Collection<PhotoAlbum>> response, Object state) {
                        if (response != null) {
                            albumListResponse = response;
                        }
                        signal.countDown();

                    }
                });
            }
        });
        thread.start();

        try {
            signal.await(SIGNAL_TIMEOUT, TimeUnit.SECONDS);

        } catch (InterruptedException e1) {
            Log.d(TAG, "Exception - " + e1.getMessage());
        }

        return albumListResponse;
    }

    public static Response<Boolean> AddPicture_Wrapper(final VirtualAlbum album,
            final PicturePublic pic, final Object state) {
        boolResponse = null;
        final CountDownLatch signal = new CountDownLatch(1);

        Thread thread = new Thread(new Runnable() {
            public void run() {
                album.addPicture(pic, state, new OnCallback<Response<Boolean>>() {
                    public void OnResponse(Response<Boolean> response, Object state) {
                        if (response != null) {
                            boolResponse = response;
                        }
                        signal.countDown();

                    }
                });
            }
        });
        thread.start();

        try {
            signal.await(SIGNAL_TIMEOUT, TimeUnit.SECONDS);

        } catch (InterruptedException e1) {
            Log.d(TAG, "Exception - " + e1.getMessage());
        }

        return boolResponse;
    }

    public static Response<Boolean> Update_Wrapper(final VirtualAlbum album, final String name,
            final String appTag, final Object state) {
        boolResponse = null;
        final CountDownLatch signal = new CountDownLatch(1);

        Thread thread = new Thread(new Runnable() {
            public void run() {
                album.update(name, appTag, state, new OnCallback<Response<Boolean>>() {
                    public void OnResponse(Response<Boolean> response, Object state) {
                        if (response != null) {
                            boolResponse = response;
                        }
                        signal.countDown();

                    }
                });
            }
        });
        thread.start();

        try {
            signal.await(SIGNAL_TIMEOUT, TimeUnit.SECONDS);

        } catch (InterruptedException e1) {
            Log.d(TAG, "Exception - " + e1.getMessage());
        }

        return boolResponse;
    }

    public static Response<Boolean> UpdatePicture_Wrapper(final VirtualAlbum album,
            final PicturePublic picture, final String newComment, final String newAppTag,
            final Object state) {
        boolResponse = null;
        final CountDownLatch signal = new CountDownLatch(1);

        Thread thread = new Thread(new Runnable() {
            public void run() {
                album.updatePicture(picture, newComment, newAppTag, state,
                        new OnCallback<Response<Boolean>>() {
                            public void OnResponse(Response<Boolean> response, Object state) {
                                if (response != null) {
                                    boolResponse = response;
                                }
                                signal.countDown();

                            }
                        });
            }
        });
        thread.start();

        try {
            signal.await(SIGNAL_TIMEOUT, TimeUnit.SECONDS);

        } catch (InterruptedException e1) {
            Log.d(TAG, "Exception - " + e1.getMessage());
        }

        return boolResponse;
    }

    public static Response<Boolean> RemovePicture_Wrapper(final VirtualAlbum album,
            final PicturePublic picture, final Object state) {
        boolResponse = null;
        final CountDownLatch signal = new CountDownLatch(1);

        Thread thread = new Thread(new Runnable() {
            public void run() {
                album.removePicture(picture, state, new OnCallback<Response<Boolean>>() {
                    public void OnResponse(Response<Boolean> response, Object state) {
                        if (response != null) {
                            boolResponse = response;
                        }
                        signal.countDown();

                    }
                });
            }
        });
        thread.start();

        try {
            signal.await(SIGNAL_TIMEOUT, TimeUnit.SECONDS);

        } catch (InterruptedException e1) {
            Log.d(TAG, "Exception - " + e1.getMessage());
        }

        return boolResponse;
    }

    public static Response<PhotoAlbum> Create_Wrapper(final PhotoAlbums photoAlbums,
            final String name, final Object state) {
        albumResponse = null;
        final CountDownLatch signal = new CountDownLatch(1);

        Thread thread = new Thread(new Runnable() {
            public void run() {
                photoAlbums.create(name, false, "", state, new OnCallback<Response<PhotoAlbum>>() {
                    public void OnResponse(Response<PhotoAlbum> response, Object state) {
                        if (response != null) {
                            albumResponse = response;
                        }
                        signal.countDown();

                    }
                });
            }
        });
        thread.start();

        try {
            signal.await(SIGNAL_TIMEOUT, TimeUnit.SECONDS);

        } catch (InterruptedException e1) {
            Log.d(TAG, "Exception - " + e1.getMessage());
        }

        return albumResponse;
    }

    public static Response<Picture> AddPictureWithWatermark_Wrapper(final PhotoAlbum photoAlbum,
            final byte[] blob, final String comment, final double latitude, final double longitude,
            String appTag, final String watermark, final Object state) {
        pictureResponse = null;
        final CountDownLatch signal = new CountDownLatch(1);

        Thread thread = new Thread(new Runnable() {
            public void run() {
                photoAlbum.addPictureWithWatermark(blob, comment, (float) latitude,
                        (float) longitude, watermark, state, new OnCallback<Response<Picture>>() {
                            public void OnResponse(Response<Picture> response, Object state) {
                                if (response != null) {
                                    pictureResponse = response;
                                }
                                signal.countDown();

                            }
                        });
            }
        });
        thread.start();

        try {
            signal.await(SIGNAL_TIMEOUT, TimeUnit.SECONDS);

        } catch (InterruptedException e1) {
            Log.d(TAG, "Exception - " + e1.getMessage());
        }

        return pictureResponse;
    }
}
