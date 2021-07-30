package com.kylediaz.fbu.dipole_news.adapters;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.kylediaz.fbu.dipole_news.fragments.article_reader.ArticleReaderFragment;
import com.kylediaz.fbu.dipole_news.models.Article;

import java.util.List;

/**
 * Adapter for viewing articles in the ViewPager in ArticleListActivity
 */
public class ArticlePageAdapter extends FragmentStateAdapter {

    private static final String TAG = ArticlePageAdapter.class.toString();

    private final List<Article> articles;

    public ArticlePageAdapter(FragmentActivity fragmentActivity, List<Article> articles) {
        super(fragmentActivity);
        this.articles = articles;
    }

    @NonNull
    @Override
    public androidx.fragment.app.Fragment createFragment(int position) {
        Log.d(TAG, "Creating fragment in position " + position);
        return new ArticleReaderFragment(articles.get(position));
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }
}