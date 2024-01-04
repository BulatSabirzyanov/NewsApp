package com.example.article_details.data.repository

import com.example.database.dao.ArticleFavoriteDao
import com.example.model.local.article.ArticleEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class ArticleDetailsRepoImplTest {

    private lateinit var repo: ArticleDetailsRepoImpl
    private val dao: ArticleFavoriteDao = mockk()

    @Before
    fun setup() {
        repo = ArticleDetailsRepoImpl(dao)
    }

    @Test
    fun `isArticleExist should return true when article exists`() = runBlocking {

        val articleTitle = "Sample Article"
        coEvery { dao.isArticleExists(articleTitle) } returns true


        val result = repo.isArticleExist(articleTitle)


        assert(result)
    }


    @Test
    fun `isArticleExist should return false when article does not exist`() = runBlocking {

        val articleTitle = "Non-Existent Article"
        coEvery { dao.isArticleExists(articleTitle) } returns false


        val result = repo.isArticleExist(articleTitle)


        assert(!result)
    }

    @Test
    fun `addArticle should insert the article into the database`() = runBlocking {

        val articleEntity = ArticleEntity("", "", "", "", "", "", "")
        coEvery { dao.insertArticle(articleEntity) } returns Unit


        repo.addArticle(articleEntity)



        coVerify { dao.insertArticle(articleEntity) }
    }

    @Test
    fun `deleteFavorite should delete the favorite article from the database`() = runBlocking {

        val articleTitle = "Favorite Article"
        coEvery { dao.deleteFavorite(articleTitle) } returns Unit


        repo.deleteFavorite(articleTitle)

        coVerify { dao.deleteFavorite(articleTitle) }
    }


}
