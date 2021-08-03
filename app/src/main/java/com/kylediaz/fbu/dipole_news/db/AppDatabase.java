package com.kylediaz.fbu.dipole_news.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.kylediaz.fbu.dipole_news.db.dao.ArticleDao;
import com.kylediaz.fbu.dipole_news.models.Article;

@Database(entities = {Article.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract ArticleDao articleDao();
}
