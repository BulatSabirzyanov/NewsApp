package com.example.article_details.ui

import com.example.model.local.article.ArticleEntity

sealed class ArticleDetailsEvent {
    object popUp : ArticleDetailsEvent()
    data class checkArticleAvilability(val article: String) : ArticleDetailsEvent()
    data class updateArticleFavoriteState(val article: ArticleEntity) : ArticleDetailsEvent()
}
