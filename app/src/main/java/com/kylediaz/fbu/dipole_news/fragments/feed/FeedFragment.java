package com.kylediaz.fbu.dipole_news.fragments.feed;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.kylediaz.fbu.dipole_news.adapters.EventAdapter;
import com.kylediaz.fbu.dipole_news.databinding.FragmentFeedBinding;
import com.kylediaz.fbu.dipole_news.models.Event;

public class FeedFragment extends Fragment {

    private static final String TAG = FeedFragment.class.toString();

    private FeedViewModel feedViewModel;
    private FragmentFeedBinding binding;

    private SwipeRefreshLayout swipeRefreshLayout;

    private Spinner sortingMethod;
    private ImageButton expandedView, compactView;

    private RecyclerView rvNewsFeed;

    private EventAdapter eventAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        feedViewModel = createViewModel();

        binding = FragmentFeedBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        sortingMethod = binding.sortingMethod;

        sortingMethod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sortEventsByID(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // TODO implement view switching
        expandedView = binding.btnExpandView;
        compactView = binding.btnCompactView;

        this.rvNewsFeed = binding.rvNewsFeed;

        eventAdapter = new EventAdapter(this.getContext(), feedViewModel.getEvents().getValue());

        feedViewModel.getEvents().observe(getViewLifecycleOwner(),
                events -> eventAdapter.notifyDataSetChanged());

        rvNewsFeed.setAdapter(eventAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(rvNewsFeed.getContext());
        rvNewsFeed.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvNewsFeed.getContext(),
                layoutManager.getOrientation());
        rvNewsFeed.addItemDecoration(dividerItemDecoration);

        this.swipeRefreshLayout = binding.swipeContainer;

        swipeRefreshLayout.setOnRefreshListener(() -> refresh());

        // Swipe to refresh is only enabled if recyclerview is at top
        rvNewsFeed.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                boolean isAtTop = layoutManager.findFirstCompletelyVisibleItemPosition() == 0;
                swipeRefreshLayout.setEnabled(isAtTop);
            }
        });

        return root;
    }

    private void refresh() {
        feedViewModel.loadEvents();
        eventAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

    /**
     * @param position Position of element in res/values/arrays.xml/feed_sort_methods
     */
    private void sortEventsByID(int position) {
        switch (position) {
            case 0:
                eventAdapter.sort(Event.MOST_ARTICLES_COMPARATOR);
                break;
            case 1:
                eventAdapter.sort(Event.LATEST_UPDATE_COMPARATOR);
                break;
            default: Log.w(TAG, "User selected unimplemented order " + position);
        }
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