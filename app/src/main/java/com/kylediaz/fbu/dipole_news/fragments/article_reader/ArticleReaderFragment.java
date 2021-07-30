package com.kylediaz.fbu.dipole_news.fragments.article_reader;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.kylediaz.fbu.dipole_news.databinding.FragmentReadArticleBinding;
import com.kylediaz.fbu.dipole_news.models.Article;

public class ArticleReaderFragment extends Fragment {

    private static final String TAG = ArticleReaderFragment.class.toString();

    private Article article;

    private FragmentReadArticleBinding binding;

    private TextView tvArticleTitle;
    private ImageView ivArticleImage;
    private TextView tvArticleAuthor;
    private TextView tvArticlePublishDate;
    private TextView tvArticleContent;

    public ArticleReaderFragment(Article article) {
        this.article = article;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentReadArticleBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        this.tvArticleTitle = binding.tvArticleTitle;
        this.ivArticleImage = binding.ivArticleImage;
        this.tvArticleAuthor = binding.tvArticleAuthor;
        this.tvArticlePublishDate = binding.tvArticlePublishDateTime;
        this.tvArticleContent = binding.tvArticleContent;

        Log.d(TAG, String.valueOf(article));

        tvArticleTitle.setText(article.getTitle());
        Glide.with(this)
                .load(article.getImageURL())
                .into(ivArticleImage);
        tvArticleAuthor.setText("By: " + article.getAuthor());
        tvArticlePublishDate.setText(article.getPublishedAt().toString());
        tvArticleContent.setText(article.getContent().trim());

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
