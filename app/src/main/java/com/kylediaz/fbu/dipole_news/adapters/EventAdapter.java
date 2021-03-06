package com.kylediaz.fbu.dipole_news.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kylediaz.fbu.dipole_news.R;
import com.kylediaz.fbu.dipole_news.activities.ArticleListActivity;
import com.kylediaz.fbu.dipole_news.models.Event;
import com.kylediaz.fbu.dipole_news.network.DipoleNewsClient;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Adapter for events in the RecyclerView in FeedFragment/event feed
 */
public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

    private final Context context;
    private final List<Event> events;

    public EventAdapter(Context context, List<Event> events) {
        this.context = context;
        this.events = events;
    }

    @NonNull
    @Override
    public EventAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_event, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventAdapter.ViewHolder holder, int position) {
        Event event = events.get(position);
        holder.bind(event);
    }

    public void sort(Comparator<Event> comparator) {
        Collections.sort(events, comparator);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private Event event;

        private final TextView tvEventTitle;
        private final TextView tvEventArticleCount;
        private final ImageView ivBookmarkButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEventTitle = itemView.findViewById(R.id.tvEventTitle);
            tvEventArticleCount = itemView.findViewById(R.id.tvEventArticleCount);
            ivBookmarkButton = itemView.findViewById(R.id.ivBookmarkButton);

            itemView.setOnClickListener(arg0 -> {
                Intent i = new Intent(context, ArticleListActivity.class);
                i.putExtra("event", event);
                context.startActivity(i);
            });

            ivBookmarkButton.setOnClickListener(arg0 -> {
                DipoleNewsClient.getInstance().addBookmark(event.getId());
            });
        }

        public void bind(Event event) {
            this.event = event;
            tvEventTitle.setText(event.getTitle());
            tvEventArticleCount.setText(event.getArticles().length + " articles");
        }

    }

}
