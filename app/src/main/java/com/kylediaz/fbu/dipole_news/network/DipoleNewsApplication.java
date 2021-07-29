package com.kylediaz.fbu.dipole_news.network;

import android.app.Application;

import com.kylediaz.fbu.dipole_news.BuildConfig;
import com.kylediaz.fbu.dipole_news.models.Bookmark;
import com.parse.Parse;
import com.parse.ParseObject;

public class DipoleNewsApplication extends Application {

    private final static String PARSE_APPLICATION_ID = BuildConfig.PARSE_APPLICATION_ID;
    private final static String PARSE_CLIENT_KEY = BuildConfig.PARSE_CLIENT_KEY;

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG);

        ParseObject.registerSubclass(Bookmark.class);

        // set applicationId, and server server based on the values in the back4app settings.
        // any network interceptors must be added with the Configuration Builder given this syntax
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(PARSE_APPLICATION_ID) // should correspond to Application Id env variable
                .clientKey(PARSE_CLIENT_KEY)  // should correspond to Client key env variable
                .server("https://parseapi.back4app.com").build());
    }
}
