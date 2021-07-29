package com.kylediaz.fbu.dipole_news.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.klinker.android.sliding.SlidingActivity;
import com.kylediaz.fbu.dipole_news.R;
import com.kylediaz.fbu.dipole_news.adapters.ArticleAdapter;
import com.kylediaz.fbu.dipole_news.models.Article;
import com.kylediaz.fbu.dipole_news.models.Event;
import com.kylediaz.fbu.dipole_news.network.DipoleNewsClient;

import java.util.ArrayList;
import java.util.List;

public class ArticleListActivity extends SlidingActivity {

    private final static String TAG = ArticleListActivity.class.toString();

    // Intent parameters
    /**
     * Intent parameter key for the event this activity will show the articles for
     */
    public final static String EVENT_KEY = "event";

    private List<Article> articles;

    private RecyclerView rvArticleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list);

        Intent intent = getIntent();
        if (intent.getBooleanExtra(SampleActivity.ARG_USE_EXPANSION, false)) {
            expandFromPoints(
                    intent.getIntExtra(SampleActivity.ARG_EXPANSION_LEFT_OFFSET, 0),
                    intent.getIntExtra(SampleActivity.ARG_EXPANSION_TOP_OFFSET, 0),
                    intent.getIntExtra(SampleActivity.ARG_EXPANSION_VIEW_WIDTH, 0),
                    intent.getIntExtra(SampleActivity.ARG_EXPANSION_VIEW_HEIGHT, 0)
            );
        }

        rvArticleList = findViewById(R.id.rvArticleList);
        rvArticleList.setLayoutManager(new LinearLayoutManager(this));

        articles = new ArrayList<>();
        ArticleAdapter adapter = new ArticleAdapter(this, articles, (view, article) -> {
            Intent i = new Intent(ArticleListActivity.this, ReadArticleActivity.class);
            i.putExtra(ReadArticleActivity.ARTICLE_KEY, article);
            ArticleListActivity.this.startActivity(i);
            overridePendingTransition(R.anim.slide_up, R.anim.no_anim);
        });
        rvArticleList.setAdapter(adapter);

        Event event = getIntent().getExtras().getParcelable(EVENT_KEY);

        DipoleNewsClient.getInstance().getArticles(event, articles -> {
            if (articles != null) {
                this.articles.addAll(articles);
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(this, "Unable to load articles", Toast.LENGTH_SHORT)
                        .show();
                finish();
            }
        });
    }

    @Override
    protected void configureScroller(MultiShrinkScroller scroller) {
        super.configureScroller(scroller);
        scroller.setIntermediateHeaderHeightRatio(1);
    }
}