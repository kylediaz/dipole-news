package com.kylediaz.fbu.dipole_news.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Query;

import com.kylediaz.fbu.dipole_news.models.Article;

import java.util.List;

@Dao
public interface ArticleDao {

    @Query("SELECT * FROM article WHERE id = :id")
    Article findByID(int id);

    @Query("Select * FROM article WHERE id IN (:ids)")
    List<Article> findByIDs(int[] ids);

    @Delete
    void delete(Article article);

}
