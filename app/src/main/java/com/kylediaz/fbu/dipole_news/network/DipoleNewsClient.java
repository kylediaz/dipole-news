package com.kylediaz.fbu.dipole_news.network;

import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.kylediaz.fbu.dipole_news.BuildConfig;
import com.kylediaz.fbu.dipole_news.models.Article;
import com.kylediaz.fbu.dipole_news.models.Event;

import org.json.JSONException;

import java.util.List;
import java.util.function.Consumer;

import okhttp3.Headers;

public class DipoleNewsClient {

    private final static String TAG = DipoleNewsClient.class.toString();
    private final static String REST_URL = BuildConfig.DIPOLE_API_URL;
    private static DipoleNewsClient instance;
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

}
