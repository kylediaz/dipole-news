package com.kylediaz.fbu.dipole_news.fragments.bookmarks;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kylediaz.fbu.dipole_news.R;

/*
 * Fragment has no logic. This class only exists to be used in BookmarksFragment.onCreateView to
 * display a placeholder in case there is no user signed in.
 */
public class BookmarksPlaceholderFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bookmarks_placeholder, container, false);
    }
}