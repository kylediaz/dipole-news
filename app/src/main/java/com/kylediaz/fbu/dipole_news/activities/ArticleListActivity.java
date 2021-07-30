package com.kylediaz.fbu.dipole_news.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.kylediaz.fbu.dipole_news.R;
import com.kylediaz.fbu.dipole_news.adapters.ArticleAdapter;
import com.kylediaz.fbu.dipole_news.adapters.ArticlePageAdapter;
import com.kylediaz.fbu.dipole_news.models.Article;
import com.kylediaz.fbu.dipole_news.models.Event;
import com.kylediaz.fbu.dipole_news.network.DipoleNewsClient;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.List;

public class ArticleListActivity extends FragmentActivity {

    /**
     * Intent parameter key for the event this activity will show the articles for
     */
    public final static String EVENT_KEY = "event";

    // Intent parameters
    private final static String TAG = ArticleListActivity.class.toString();
    private List<Article> articles;

    private SlidingUpPanelLayout slidingUpPanelLayout;

    private RecyclerView rvArticleList;
    private ArticleAdapter articleListAdapter;

    private ViewPager2 vpArticleReader;
    private ArticlePageAdapter articlePageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list);

        rvArticleList = findViewById(R.id.rvArticleList);
        vpArticleReader = findViewById(R.id.vpArticleReader);
        slidingUpPanelLayout = findViewById(R.id.slidingPanelLayout);

        rvArticleList.setLayoutManager(new LinearLayoutManager(this));

        articles = new ArrayList<>();

        articleListAdapter = new ArticleAdapter(this, articles, (position, view, article) -> {
            showArticle(position);
        });
        rvArticleList.setAdapter(articleListAdapter);
        articlePageAdapter = new ArticlePageAdapter(this, articles);
        vpArticleReader.setAdapter(articlePageAdapter);

        Event event = getIntent().getExtras().getParcelable(EVENT_KEY);
        loadArticles(event);
    }

    private void loadArticles(Event event) {
        DipoleNewsClient.getInstance().getArticles(event, articles -> {
            if (articles != null) {
                this.articles.addAll(articles);
                articleListAdapter.notifyDataSetChanged();
                articlePageAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(this, "Unable to load articles", Toast.LENGTH_SHORT)
                        .show();
                finish();
            }
        });
    }

    private void showArticle(int position) {
        vpArticleReader.setCurrentItem(position);
        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
    }

    @Override
    public void onBackPressed() {
        if (slidingUpPanelLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED) {
            slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        } else {
            super.onBackPressed();
        }
    }
}