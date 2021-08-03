package com.kylediaz.fbu.dipole_news.fragments.bookmarks;

import androidx.lifecycle.ViewModelProvider;

import com.kylediaz.fbu.dipole_news.fragments.feed.FeedFragment;
import com.kylediaz.fbu.dipole_news.fragments.feed.FeedViewModel;

/*
 * The BookmarksFeedFragment is the same thing as the FeedFragment, but it changes which ViewModel
 * the superclass uses, which changes what events are loaded. This just loads the bookmarked events
 * instead of the main feed
 */
public class BookmarksFeedFragment extends FeedFragment {

    @Override
    protected FeedViewModel createViewModel() {
        return new ViewModelProvider(this).get(BookmarksFeedViewModel.class);
    }

}