package com.example.source_content.domain.pagination

import com.example.common.pagination.BasePagingSource
import com.example.model.dto.article.ArticleDto
import com.example.model.dto.mapper.toArticleDtoList
import com.example.source_content.domain.repository.SourceRepository
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class SourcePagingSource @Inject constructor(
    private val sourceRepository: SourceRepository,
    private val sourceId: String
) :
    BasePagingSource<ArticleDto>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleDto> {

        val position = params.key ?: 1
        return try {
            val response = sourceRepository.getArticlesBySource(sourceId)


            val articles = response.articles.toArticleDtoList()

            LoadResult.Page(
                data = articles,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (articles.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            Timber.d(exception.toString())
            // IOException for network failures.
            return LoadResult.Error(exception)
        }

    }
}
