package com.kylediaz.fbu.dipole_news.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kylediaz.fbu.dipole_news.R;
import com.kylediaz.fbu.dipole_news.models.Article;

import java.util.List;

/**
 * Adapter for articles in the RecyclerView in ArticleListActivity
 */
public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {

    private final Activity context;
    private final List<Article> articles;
    private final OnClickListener onClickListener;
    public ArticleAdapter(Activity context, List<Article> articles, OnClickListener onClickListener) {
        this.context = context;
        this.articles = articles;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ArticleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_article, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleAdapter.ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public interface OnClickListener {
        void onClick(int position, View view, Article article);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private Article article;
        private int position;

        private final TextView tvArticleSource;
        private final TextView tvArticleTitle;
        private final ImageView ivArticleImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvArticleSource = itemView.findViewById(R.id.tvArticleSource);
            tvArticleTitle = itemView.findViewById(R.id.tvArticleTitle);
            ivArticleImage = itemView.findViewById(R.id.ivArticleImage);

            itemView.setOnClickListener(arg0 -> {
                onClickListener.onClick(position, itemView, article);
            });
        }

        public void bind(int position) {
            this.article = articles.get(position);
            this.position = position;

            tvArticleSource.setText(article.getPublisher());
            tvArticleTitle.setText(article.getTitle());
            Glide.with(context).load(article.getImageURL()).into(ivArticleImage);
        }

    }

}
