package com.example.feed.domain.pagination

import com.example.common.pagination.BasePagingSource
import com.example.feed.domain.repository.FeedRepository
import com.example.model.dto.article.ArticleDto
import com.example.model.dto.mapper.toArticleDtoList
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class ArticlesPagingSource @Inject constructor(
    private val feedRepository: FeedRepository,
    private val query: String
) :
    BasePagingSource<ArticleDto>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleDto> {

        val position = params.key ?: 1
        return try {
            val response = feedRepository.getArticles(query)
            Timber.d(response.articles.size.toString())

            val articles = response.articles.toArticleDtoList()

            LoadResult.Page(
                data = articles,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (articles.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            Timber.d(exception.toString())

            return LoadResult.Error(exception)
        }

    }
}

