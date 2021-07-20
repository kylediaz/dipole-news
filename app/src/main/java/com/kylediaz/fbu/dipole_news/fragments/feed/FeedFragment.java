package com.kylediaz.fbu.dipole_news.fragments.feed;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceFragmentCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kylediaz.fbu.dipole_news.adapters.EventAdapter;
import com.kylediaz.fbu.dipole_news.databinding.FragmentHomeBinding;

public class FeedFragment extends Fragment {

    private FeedViewModel feedViewModel;
    private FragmentHomeBinding binding;

    private RecyclerView rvNewsFeed;

    private EventAdapter eventAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        feedViewModel = new ViewModelProvider(this).get(FeedViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        this.rvNewsFeed = binding.rvNewsFeed;

        eventAdapter = new EventAdapter(this.getContext(), feedViewModel.getEvents().getValue());

        feedViewModel.getEvents().observe(getViewLifecycleOwner(),
                events -> eventAdapter.notifyDataSetChanged());

        rvNewsFeed.setAdapter(eventAdapter);
        rvNewsFeed.setLayoutManager(new LinearLayoutManager(rvNewsFeed.getContext()));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}