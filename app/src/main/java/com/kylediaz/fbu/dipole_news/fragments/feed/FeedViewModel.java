package com.kylediaz.fbu.dipole_news.fragments.feed;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kylediaz.fbu.dipole_news.models.Event;
import com.kylediaz.fbu.dipole_news.network.DipoleNewsClient;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class FeedViewModel extends ViewModel {

    private static final String TAG = FeedViewModel.class.toString();

    private final MutableLiveData<List<Event>> events;

    public FeedViewModel() {
        events = new MutableLiveData<List<Event>>();
        loadEvents();
    }

    public LiveData<List<Event>> getEvents() {
        return events;
    }

    public void loadEvents() {
        events.setValue(new ArrayList<>());
        loadData(events -> {
            this.events.getValue().addAll(events);
            this.events.setValue(this.events.getValue()); // Do nothing but notify observers
        });
    }

    /*
     * This method exists solely so it can be overrided by BookmarksFeedViewModel
     */
    protected void loadData(Consumer<List<Event>> consumer) {
        DipoleNewsClient.getInstance().getFeed(consumer);
    }

}