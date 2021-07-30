package com.kylediaz.fbu.dipole_news.fragments.bookmarks;

import android.util.Log;

import com.kylediaz.fbu.dipole_news.fragments.feed.FeedViewModel;
import com.kylediaz.fbu.dipole_news.models.Event;
import com.kylediaz.fbu.dipole_news.network.DipoleNewsClient;

import java.net.NoRouteToHostException;
import java.util.List;
import java.util.function.Consumer;

/*
 * View model for BookmarksFeedFragment, NOT BookmarksFragment
 */
public class BookmarksFeedViewModel extends FeedViewModel {

    private final static String TAG = BookmarksFeedViewModel.class.toString();

    @Override
    protected void loadData(Consumer<List<Event>> consumer) {
        try {
            DipoleNewsClient.getInstance().getBookmarkedEvents(consumer);
        } catch (NoRouteToHostException e) {
            Log.e(TAG, "Error getting bookmarked events", e);
            consumer.accept(null);
        }
    }

}