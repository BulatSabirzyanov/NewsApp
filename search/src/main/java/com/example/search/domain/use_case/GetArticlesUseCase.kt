package com.example.search.domain.use_case

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.common.use_case.FlowPagingUseCase
import com.example.model.dto.article.ArticleDto
import com.example.search.domain.pagination.ArticlesPagingSource
import com.example.search.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetArticlesUseCase @Inject constructor(
    private val repository: SearchRepository,
) :
    FlowPagingUseCase<ArticleDto>() {
    override fun execute(parameter: String?): Flow<PagingData<ArticleDto>> =
        Pager(
            config = PagingConfig(
                pageSize = 20
            ),
            pagingSourceFactory = { ArticlesPagingSource(repository, parameter ?: "") }
        ).flow
}
