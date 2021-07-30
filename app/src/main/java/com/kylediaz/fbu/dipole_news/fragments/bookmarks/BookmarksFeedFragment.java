package com.kylediaz.fbu.dipole_news.fragments.bookmarks;

import androidx.lifecycle.ViewModelProvider;

import com.kylediaz.fbu.dipole_news.fragments.feed.FeedFragment;
import com.kylediaz.fbu.dipole_news.fragments.feed.FeedViewModel;

public class BookmarksFeedFragment extends FeedFragment {

    @Override
    protected FeedViewModel createViewModel() {
        return new ViewModelProvider(this).get(BookmarksFeedViewModel.class);
    }
}