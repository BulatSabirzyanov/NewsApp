package com.example.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.model.local.article.ArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleFavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(articleEntity: ArticleEntity)

    @Query("DELETE FROM articles WHERE title = :title")
    suspend fun deleteFavorite(title: String)

    @Query("SELECT * FROM articles")
    fun getAllFavoriteArticles(): Flow<List<ArticleEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM articles WHERE title = :title LIMIT 1)")
    suspend fun isArticleExists(title: String): Boolean
}
