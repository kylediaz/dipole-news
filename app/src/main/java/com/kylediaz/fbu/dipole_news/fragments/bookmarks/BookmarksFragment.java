package com.kylediaz.fbu.dipole_news.fragments.bookmarks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.kylediaz.fbu.dipole_news.R;
import com.kylediaz.fbu.dipole_news.databinding.FragmentBookmarksBinding;
import com.kylediaz.fbu.dipole_news.fragments.feed.FeedFragment;
import com.kylediaz.fbu.dipole_news.network.DipoleNewsClient;

public class BookmarksFragment extends Fragment {

    private FragmentBookmarksBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBookmarksBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        if (DipoleNewsClient.getInstance().userIsSignedIn()) {
            switchToFragment(BookmarksFeedFragment.class);
        } else {
            switchToFragment(BookmarksPlaceholderFragment.class);
        }

        return root;
    }

    private void switchToFragment(Class<? extends Fragment> c) {
        getParentFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fragment_container_view, c, null)
                .commit();
    }

}