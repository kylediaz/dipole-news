    package com.kylediaz.fbu.dipole_news.activities;

import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.transition.Slide;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kylediaz.fbu.dipole_news.R;
import com.kylediaz.fbu.dipole_news.databinding.ActivityReadArticleBinding;
import com.kylediaz.fbu.dipole_news.databinding.FragmentHomeBinding;
import com.kylediaz.fbu.dipole_news.models.Article;

public class ReadArticleActivity extends AppCompatActivity {

    private final static String TAG = ReadArticleActivity.class.toString();

    /**
     * Intent parameter key for the article that will be displayed
     */
    public final static String ARTICLE_KEY = "article";

    private ActivityReadArticleBinding binding;

    private TextView tvArticleTitle;
    private ImageView ivArticleImage;
    private TextView tvArticleAuthor;
    private TextView tvArticlePublishDate;
    private TextView tvArticleContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setEnterTransition(new Slide(Gravity.TOP));
        getWindow().setExitTransition(new Slide(Gravity.BOTTOM));

        binding = ActivityReadArticleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        this.tvArticleTitle = binding.tvArticleTitle;
        this.ivArticleImage = binding.ivArticleImage;
        this.tvArticleAuthor = binding.tvArticleAuthor;
        this.tvArticlePublishDate = binding.tvArticlePublishDateTime;
        this.tvArticleContent = binding.tvArticleContent;

        Article article = getIntent().getParcelableExtra(ARTICLE_KEY);

        tvArticleTitle.setText(article.getTitle());
        Glide.with(this).load(article.getImageURL()).into(ivArticleImage);
        tvArticleAuthor.setText("By: " + article.getAuthor());
        tvArticlePublishDate.setText(article.getPublishedAt().toString());
        tvArticleContent.setText(article.getContent().trim());
    }
}