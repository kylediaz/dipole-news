package com.kylediaz.fbu.dipole_news.adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.transition.Slide;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kylediaz.fbu.dipole_news.R;
import com.kylediaz.fbu.dipole_news.activities.ReadArticleActivity;
import com.kylediaz.fbu.dipole_news.databinding.ActivityReadArticleBinding;
import com.kylediaz.fbu.dipole_news.models.Article;

import org.w3c.dom.Text;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {

    private final Activity context;
    private final List<Article> articles;

    public ArticleAdapter(Activity context, List<Article> articles) {
        this.context = context;
        this.articles = articles;
    }


    @NonNull
    @Override
    public ArticleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_article, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleAdapter.ViewHolder holder, int position) {
        Article article = articles.get(position);
        holder.bind(article);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private Article article;

        private TextView tvArticleSource;
        private TextView tvArticleTitle;
        private ImageView ivArticleImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvArticleSource = itemView.findViewById(R.id.tvArticleSource);
            tvArticleTitle = itemView.findViewById(R.id.tvArticleTitle);
            ivArticleImage = itemView.findViewById(R.id.ivArticleImage);

            itemView.setOnClickListener(arg0 -> {
                Intent i = new Intent(context, ReadArticleActivity.class);
                i.putExtra(ReadArticleActivity.ARTICLE_KEY, article);
                context.startActivity(i);
            });
        }

        public void bind(Article article) {
            this.article = article;

            tvArticleSource.setText(article.getSource());
            tvArticleTitle.setText(article.getTitle());
            Glide.with(context).load(article.getImageURL()).into(ivArticleImage);

            Log.d("ArticleAdapter", "Loading article " + article.getTitle() + " into RV");
        }

    }

}
