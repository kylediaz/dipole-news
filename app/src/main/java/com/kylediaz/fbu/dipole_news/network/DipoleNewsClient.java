package com.kylediaz.fbu.dipole_news.network;

import static com.parse.Parse.getApplicationContext;

import android.accounts.NetworkErrorException;
import android.util.Log;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.kylediaz.fbu.dipole_news.BuildConfig;
import com.kylediaz.fbu.dipole_news.db.AppDatabase;
import com.kylediaz.fbu.dipole_news.models.Article;
import com.kylediaz.fbu.dipole_news.models.Event;
import com.parse.ParseException;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;

import java.net.NoRouteToHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import okhttp3.Headers;

/**
 * For interfacing with remote servers
 */
public class DipoleNewsClient {

    private final static String TAG = DipoleNewsClient.class.toString();

    private final static String REST_URL = BuildConfig.DIPOLE_API_URL;

    private static DipoleNewsClient instance;

    private final AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "cache").build();
    private final AsyncHttpClient client = new AsyncHttpClient();

    public static DipoleNewsClient getInstance() {
        if (instance == null) {
            instance = new DipoleNewsClient();
        }
        return instance;
    }

    /**
     * @param callback accepts List\<Event\> on  successful fetch, null otherwise
     */
    public void getFeed(Consumer<List<Event>> callback) {
        String url = REST_URL + "/feed";
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                List<Event> events;
                try {
                    events = Event.fromJSONArray(json.jsonArray);
                } catch (JSONException e) {
                    events = null;
                    Log.e(TAG, "getFeed onSuccess: Unable to parse /feed JSON response", e);
                }
                callback.accept(events);
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.e(TAG, "getFeed onFailure: " + response, throwable);
                callback.accept(null);
            }
        });
    }

    public void getEvents(Consumer<List<Event>> callback, List<Integer> events) {
        if (events == null || events.size() == 0) {
            callback.accept(new ArrayList<>());
            return;
        }
        List<String> eventsAsStrings = events.stream().map(String::valueOf).collect(Collectors.toList());
        String url = REST_URL + "/events?id=" + String.join(",", eventsAsStrings);
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                List<Event> events;
                try {
                    events = Event.fromJSONArray(json.jsonArray);
                } catch (JSONException e) {
                    events = null;
                    Log.e(TAG, "getFeed onSuccess: Unable to parse /feed JSON response", e);
                }
                callback.accept(events);
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.e(TAG, "getFeed onFailure: " + response, throwable);
                callback.accept(null);
            }
        });
    }

    public void getArticles(Event event, Consumer<List<Article>> callback) {
        getArticles(event.getId(), callback);
    }

    public void getArticles(int eventID, Consumer<List<Article>> callback) {
        String PATH = "/articles/from-event";
        String url = REST_URL + PATH + "?id=" + eventID;
        long time = System.currentTimeMillis();
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "Time diff: " + (System.currentTimeMillis() - time));
                List<Article> articles;
                try {
                    articles = Article.fromJSONArray(json.jsonArray);
                } catch (JSONException e) {
                    articles = null;
                    Log.e(TAG, "getFeed onSuccess: Unable to parse " + PATH + " JSON response", e);
                }
                callback.accept(articles);
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.e(TAG, "getFeed onFailure: " + response, throwable);
                callback.accept(null);
            }
        });
    }

    public List<Article> getArticlesFromCache(int[] articleIDs) {
        return db.articleDao().findByIDs(articleIDs);
    }

    public Article getArticleFromCache(int articleID) {
        return db.articleDao().findByID(articleID);
    }

    // Parse interactions are done through here so the rest of the program doesn't have to deal with
    // the whole Parse/GC split

    public boolean userIsSignedIn() {
        return ParseUser.getCurrentUser() != null;
    }

    public void logOut() {
        ParseUser.logOut();
    }

    /**
     * @return List of event IDs of bookmarked events, null if no user is signed in
     */
    public void getBookmarkedEvents(Consumer<List<Event>> callback) {
        List<Integer> bookmarks = new ArrayList<>();
        ParseUser user = ParseUser.getCurrentUser();
        JSONArray bookmarksJSONArray = user.getJSONArray("bookmarks");
        if (bookmarksJSONArray == null) {
            user.put("bookmarks", new JSONArray());
        } else {
            for (int i = 0; i < bookmarksJSONArray.length(); i++) {
                try {
                    int newEvent = bookmarksJSONArray.getInt(i);
                    bookmarks.add(newEvent);
                } catch (JSONException e) {
                    Log.w(TAG, "Unable to parse " + bookmarksJSONArray.opt(i) + " from bookmarks");
                }
            }
        }
        DipoleNewsClient.getInstance().getEvents(callback, bookmarks);
    }

    /**
     * @return True if event was successfully added as a bookmark, false otherwise
     */
    public boolean addBookmark(int eventID) {
        ParseUser currentUser = null;
        try {
            currentUser = ParseUser.getCurrentUser().fetch();
        } catch (ParseException e) {
            return false;
        }
        if (currentUser == null) {
            throw new IllegalStateException("Can't add bookmark when no user is signed in");
        }
        List<Integer> bookmarks = currentUser.getList("bookmarks");
        bookmarks.add(new Integer(eventID));
        currentUser.put("bookmarks", bookmarks);
        return true;
    }

    /**
     * @return True if event was successfully removed as a bookmark, false otherwise
     */
    public boolean removeBookmark(int eventID) {
        ParseUser currentUser = null;
        try {
            currentUser = ParseUser.getCurrentUser().fetch();
        } catch (ParseException e) {
            return false;
        }
        if (currentUser == null) {
            throw new IllegalStateException("Can't remove bookmark when no user is signed in");
        }
        List<Integer> bookmarks = currentUser.getList("bookmarks");
        bookmarks.remove(new Integer(eventID));
        currentUser.put("bookmarks", bookmarks);
        return true;
    }

}
