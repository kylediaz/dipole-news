package com.kylediaz.fbu.dipole_news.network;

import android.app.Application;
import android.util.Log;

import com.kylediaz.fbu.dipole_news.BuildConfig;
import com.kylediaz.fbu.dipole_news.models.Bookmark;
import com.parse.Parse;
import com.parse.ParseObject;

public class DipoleNewsApplication extends Application {

    private final static String TAG = DipoleNewsApplication.class.toString();

    private final static String PARSE_APPLICATION_ID = BuildConfig.PARSE_APPLICATION_ID;
    private final static String PARSE_CLIENT_KEY = BuildConfig.PARSE_CLIENT_KEY;

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG);

        ParseObject.registerSubclass(Bookmark.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(PARSE_APPLICATION_ID)
                .clientKey(PARSE_CLIENT_KEY)
                .server("https://parseapi.back4app.com").build());
    }
}
