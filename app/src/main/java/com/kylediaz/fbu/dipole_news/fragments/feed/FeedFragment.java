package com.kylediaz.fbu.dipole_news.fragments.feed;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.kylediaz.fbu.dipole_news.adapters.EventAdapter;
import com.kylediaz.fbu.dipole_news.databinding.FragmentFeedBinding;

public class FeedFragment extends Fragment {

    private FeedViewModel feedViewModel;
    private FragmentFeedBinding binding;

    private SwipeRefreshLayout swipeRefreshLayout;

    private RecyclerView rvNewsFeed;

    private EventAdapter eventAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        feedViewModel = createViewModel();

        binding = FragmentFeedBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        this.rvNewsFeed = binding.rvNewsFeed;
        this.swipeRefreshLayout = binding.swipeContainer;

        eventAdapter = new EventAdapter(this.getContext(), feedViewModel.getEvents().getValue());

        feedViewModel.getEvents().observe(getViewLifecycleOwner(),
                events -> eventAdapter.notifyDataSetChanged());

        rvNewsFeed.setAdapter(eventAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(rvNewsFeed.getContext());
        rvNewsFeed.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvNewsFeed.getContext(),
                layoutManager.getOrientation());
        rvNewsFeed.addItemDecoration(dividerItemDecoration);

        swipeRefreshLayout.setOnRefreshListener(() -> refresh());

        return root;
    }

    private void refresh() {
        feedViewModel.loadEvents();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /*
     * This method exists solely to be overrided in BookmarksFeedFragment
     */
    protected FeedViewModel createViewModel() {
        return new ViewModelProvider(this).get(FeedViewModel.class);
    }
}