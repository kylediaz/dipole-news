package com.kylediaz.fbu.dipole_news.fragments.feed;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kylediaz.fbu.dipole_news.models.Event;
import com.kylediaz.fbu.dipole_news.network.DipoleNewsClient;

import java.util.ArrayList;
import java.util.List;

public class FeedViewModel extends ViewModel {

    private MutableLiveData<List<Event>> events;

    public FeedViewModel() {
        events = new MutableLiveData<List<Event>>();
        loadEvents();
    }

    public LiveData<List<Event>> getEvents() {
        return events;
    }

    private void loadEvents() {
        events.setValue(new ArrayList<>());
        DipoleNewsClient.getInstance().getFeed(events -> {
            Log.d("FeedViewModel", events.toString());
            this.events.getValue().addAll(events);
            this.events.setValue(this.events.getValue()); // Do nothing but notify observers
        });
    }

}